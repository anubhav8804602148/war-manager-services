package com.war.manager.authentication.service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.war.manager.authentication.service.exceptions.BadCredentialException;
import com.war.manager.authentication.service.models.UserEntity;
import com.war.manager.authentication.service.services.AuthenticationService;
import com.war.manager.authentication.service.services.TokenService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	TokenService tokenService;
	
	
	@Override
	public UserEntity login(UserEntity user) throws BadCredentialException{
		if(!tokenService.isValidToken(user)) {
			user.setAuthToken(tokenService.generateAuthToken(user));
		}
		if(!tokenService.isValidToken(user)) {
			throw new BadCredentialException();
		}
		return user;
	}

	@Override
	public UserEntity logout(UserEntity user) {
		tokenService.makeTokenInvalid(user);
		return user;
	}

	@Override
	public UserEntity validateLogin(UserEntity user) {
		if(!tokenService.isValidToken(user)) {
			user.setAuthToken(null);
		}
		return user;
	}

}
