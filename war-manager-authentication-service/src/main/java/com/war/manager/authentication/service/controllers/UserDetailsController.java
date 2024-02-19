package com.war.manager.authentication.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

import com.war.manager.authentication.service.models.UserDetailsEntity;
import com.war.manager.authentication.service.services.UserDetailsService;

@RestController
@RestControllerAdvice
@RequestMapping("/user-details")
public class UserDetailsController {

	@Autowired
	UserDetailsService userDetailsService;
	
	@PostMapping("/getUserDetailsForUser")
	public ResponseEntity<UserDetailsEntity> getUserDetailsForUser(ServerWebExchange exchange){
		return ResponseEntity.ok(userDetailsService.findUserDetailsByRequest(exchange));
	}
	
	@GetMapping("/getAllUserDetails")
	public ResponseEntity<List<UserDetailsEntity>> getAllUserDetails(){
		return ResponseEntity.ok(userDetailsService.findAllUserDetails());
	}
	
	@PostMapping("/updateUserDetails")
	public ResponseEntity<UserDetailsEntity> updateUserDetails(@RequestBody UserDetailsEntity userDetailsEntity){
		return ResponseEntity.ok(userDetailsService.updateUserDetails(userDetailsEntity));
	}
}
