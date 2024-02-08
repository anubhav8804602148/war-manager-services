package com.war.manager.authentication.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.war.manager.authentication.service.services.TokenService;

@RestControllerAdvice
@RestController
@RequestMapping("/token")
public class TokenController {

	@Autowired
	TokenService tokenService;
	
	
	@GetMapping("/cleanupLoggedOutToken")
	public void cleanupLoggedOutToken() {
		tokenService.cleanupLoggedOutToken();
	}
}
