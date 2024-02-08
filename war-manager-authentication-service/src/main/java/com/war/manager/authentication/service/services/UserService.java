package com.war.manager.authentication.service.services;

import org.springframework.stereotype.Service;

import com.war.manager.authentication.service.models.UserEntity;

@Service
public interface UserService {

	
	public UserEntity findUserByUsername(String username);
	
	public UserEntity findUserByEmail(String email);

	public UserEntity createNewUser(UserEntity user);
	
}
