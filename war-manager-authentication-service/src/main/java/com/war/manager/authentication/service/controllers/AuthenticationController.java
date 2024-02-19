package com.war.manager.authentication.service.controllers;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

import com.google.gson.Gson;
import com.war.manager.authentication.service.exceptions.AccountNotFoundException;
import com.war.manager.authentication.service.exceptions.BadCredentialException;
import com.war.manager.authentication.service.exceptions.UserDetailsNotFoundException;
import com.war.manager.authentication.service.models.HttpUserRequest;
import com.war.manager.authentication.service.models.UserEntity;
import com.war.manager.authentication.service.services.AuthenticationService;
import com.war.manager.authentication.service.services.PrivilegeService;

@RestController
@RestControllerAdvice
@RequestMapping("/authentication")
public class AuthenticationController {

	@Autowired
	AuthenticationService authService;
	
	@Autowired
	PrivilegeService privilegeService;
	
	private static final String AUTHENTICATION_HEADER = "authenticationHeader";
	private static Gson gson = new Gson();
	private static Base64.Encoder encoder = Base64.getEncoder();
	private static Base64.Decoder decoder = Base64.getDecoder();
	
	@PostMapping("/login")
	public ResponseEntity<UserEntity> login(@RequestBody UserEntity user, ServerWebExchange exchange) throws BadCredentialException {
		user = authService.login(user);
		exchange
			.getResponse()
			.getHeaders()
			.add(AUTHENTICATION_HEADER, encoder.encodeToString(gson.toJson(user).getBytes()));
		return new ResponseEntity<>(authService.login(user), HttpStatus.OK);
	}

	@PostMapping("/validateLogin")
	public ResponseEntity<UserEntity> validateLogin(@RequestBody UserEntity user) {
		return new ResponseEntity<>(authService.validateLogin(user), HttpStatus.OK);
	}
	
	@GetMapping("/logout")
	public ResponseEntity<UserEntity> logout(ServerWebExchange exchange){
		UserEntity user = new UserEntity();
		user.setAuthToken(gson.fromJson(new String(decoder.decode(exchange.getRequest().getHeaders().getFirst(AUTHENTICATION_HEADER))), UserEntity.class).getAuthToken());
		return new ResponseEntity<>(authService.logout(user), HttpStatus.OK);
	}
	
	@PostMapping("/checkUserPrivilegeForRequest")
	public ResponseEntity<Boolean> checkUserPrivilegeForRequest(@RequestBody HttpUserRequest request){
		try {
			return ResponseEntity.ok(authService.checkUserPrivilegeForRequest(request));
		} catch (UserDetailsNotFoundException | AccountNotFoundException e) {
			return ResponseEntity.ok(false);
		}
	}
}
