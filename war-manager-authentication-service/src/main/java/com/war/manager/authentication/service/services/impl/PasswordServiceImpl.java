package com.war.manager.authentication.service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.war.manager.authentication.service.models.UserEntity;
import com.war.manager.authentication.service.repositories.UserRepository;
import com.war.manager.authentication.service.services.PasswordService;

@Service
public class PasswordServiceImpl implements PasswordService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}
	
	public boolean validatePassword(String username, String password) {
		UserEntity user = userRepository.findUserEntityByUsername(username);
		return user!=null && user.getPassword()!=null && password!=null && passwordEncoder.matches(password, user.getPassword());
	}
	
	public boolean validatePassword(UserEntity user) {
		return validatePassword(user.getUsername(), user.getPassword());
	}
}
