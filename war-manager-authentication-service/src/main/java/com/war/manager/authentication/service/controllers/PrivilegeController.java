package com.war.manager.authentication.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

import com.war.manager.authentication.service.exceptions.AccountNotFoundException;
import com.war.manager.authentication.service.exceptions.UserDetailsNotFoundException;
import com.war.manager.authentication.service.models.PrivilegeEntity;
import com.war.manager.authentication.service.services.PrivilegeService;

@RestController
@RestControllerAdvice
@RequestMapping("/privilege")
public class PrivilegeController {

	@Autowired
	PrivilegeService privilegeService;

	@GetMapping("/getAllPrivileges")
	public ResponseEntity<List<PrivilegeEntity>> getAllPrivileges() {
		return ResponseEntity.ok(privilegeService.getAllPrivileges());
	}

	@PostMapping("/createNewPrivilege")
	public ResponseEntity<PrivilegeEntity> createNewPrivilege(@RequestBody PrivilegeEntity privilege) {
		return ResponseEntity.ok(privilegeService.createNewPrivilege(privilege));
	}

	@GetMapping("/getPrivilegeByName/{privilegeName}")
	public ResponseEntity<PrivilegeEntity> getPrivilegeByName(@PathVariable("privilegeName") String privilegeName) {
		return ResponseEntity.ok(privilegeService.getPrivilegesByName(privilegeName));
	}

	@GetMapping("/getPrivilegesByRoleName/{roleName}")
	public ResponseEntity<List<PrivilegeEntity>> getPrivilegesByRoleName(@PathVariable("roleName") String roleName) {
		return ResponseEntity.ok(privilegeService.getPrivilegesForRole(roleName));
	}

	@GetMapping("/getPrivilegesByUsername/{username}")
	public ResponseEntity<List<PrivilegeEntity>> getPrivilegesByUsername(@PathVariable("username") String username) throws UserDetailsNotFoundException, AccountNotFoundException {
		return ResponseEntity.ok(privilegeService.getPrivilegesForUsername(username));
	}

	@DeleteMapping("/deletePrivilegeByName/{privilegeName}")
	public ResponseEntity<PrivilegeEntity> deletePrivilegeByName(@PathVariable("privilegeName") String privilegeName) {
		privilegeService.removePrivilegeByName(privilegeName);
		return ResponseEntity.ok(privilegeService.getPrivilegesByName(privilegeName));
	}

	@PostMapping("/updatePrivilegeName")
	public ResponseEntity<PrivilegeEntity> updatePrivilegeName(@RequestBody PrivilegeEntity privilegeEntity) {
		return ResponseEntity.ok(privilegeService.updatePrivilegeName(privilegeEntity));
	}
	
	@GetMapping("/checkPrivilegeExists/{privilegeName}")
	public ResponseEntity<Boolean> checkPrivilegeExists(@PathVariable("privilegeName") String privilegeName, ServerWebExchange exchange) throws UserDetailsNotFoundException, AccountNotFoundException{
		try {
			return ResponseEntity.ok(privilegeService.checkPrivilegeExists(privilegeName, exchange));
		}
		catch(UserDetailsNotFoundException | AccountNotFoundException ex) {
			return ResponseEntity.ok(false);
		}
	}
}
