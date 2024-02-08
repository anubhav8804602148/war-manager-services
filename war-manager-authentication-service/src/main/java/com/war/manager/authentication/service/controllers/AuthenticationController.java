package com.war.manager.authentication.service.controllers;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
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
	private static final String AUTHENTICATION_COOKIE = "authenticationCookie";
	private static Gson gson = new Gson();
	private static Base64.Encoder encoder = Base64.getEncoder();
	
	@PostMapping("/login")
	public ResponseEntity<UserEntity> login(@RequestBody UserEntity user, ServerWebExchange exchange) throws BadCredentialException {
		UserEntity loggedInUser = authService.login(user);
		ServerHttpResponse response = exchange.getResponse();
		ResponseCookieBuilder cookieBuilder = ResponseCookie.from(AUTHENTICATION_COOKIE, encoder.encodeToString(gson.toJson(user).getBytes()));
		cookieBuilder.httpOnly(true);
		cookieBuilder.secure(true);
		response.addCookie(cookieBuilder.build());
		return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
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
