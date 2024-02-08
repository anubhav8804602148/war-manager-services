package com.war.manager.authentication.service.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.war.manager.authentication.service.exceptions.BadCredentialException;

@RestController
@RestControllerAdvice
@RequestMapping
public class ExceptionController {

	@ExceptionHandler(BadCredentialException.class)
	public Map<String, String> badCredentialMessage(){
		Map<String, String> userMessage = new HashMap<>();
		userMessage.put("message", "Bad Credentials");
		return userMessage;
	}
}
