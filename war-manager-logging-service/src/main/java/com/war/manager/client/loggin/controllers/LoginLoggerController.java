package com.war.manager.client.loggin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.war.manager.client.loggin.entities.LogEntity;
import com.war.manager.client.loggin.models.LogObject;
import com.war.manager.client.loggin.services.LoginLoggerService;

@RestController
@RequestMapping("/login")
public class LoginLoggerController {

	@Autowired
	LoginLoggerService loginLoggerService;
	
	@PostMapping("/pushLogs")
	public String pushLogs(@RequestBody LogObject log) {
		loginLoggerService.saveNewLog(new LogEntity(log));
		return "success";
	}
}
