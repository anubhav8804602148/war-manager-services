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
import com.war.manager.authentication.service.exceptions.BadCredentialException;
import com.war.manager.authentication.service.models.UserEntity;
import com.war.manager.authentication.service.services.AuthenticationService;

@RestController
@RestControllerAdvice
@RequestMapping("/authentication")
public class AuthenticationController {

	@Autowired
	AuthenticationService authService;
	private static final String AUTHENTICATION_HEADER = "authenticationHeader";
	private static Gson gson = new Gson();
	private static Base64.Encoder encoder = Base64.getEncoder();
	
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
	public ResponseEntity<UserEntity> logout(@RequestBody UserEntity user){
		return new ResponseEntity<>(authService.logout(user), HttpStatus.OK);
	}
}
