package com.war.manager.authentication.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.war.manager.authentication.service.models.UserEntity;
import com.war.manager.authentication.service.services.UserService;

@RestController
@RestControllerAdvice
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/registerNewUser")
	public ResponseEntity<UserEntity> registerNewUser(@RequestBody UserEntity user){
		return new ResponseEntity<>(userService.createNewUser(user), HttpStatus.OK);
	}
	
	@GetMapping("/fetchAllUsers")
	public ResponseEntity<List<UserEntity>> fetchAllUsers(){
		return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
	}
}
