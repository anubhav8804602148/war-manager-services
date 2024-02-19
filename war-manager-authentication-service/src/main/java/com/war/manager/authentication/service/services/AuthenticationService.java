package com.war.manager.authentication.service.services;

import org.springframework.stereotype.Service;

import com.war.manager.authentication.service.exceptions.AccountNotFoundException;
import com.war.manager.authentication.service.exceptions.BadCredentialException;
import com.war.manager.authentication.service.exceptions.UserDetailsNotFoundException;
import com.war.manager.authentication.service.models.HttpUserRequest;
import com.war.manager.authentication.service.models.UserEntity;

@Service
public interface AuthenticationService {

	public UserEntity login(UserEntity user) throws BadCredentialException;
	public UserEntity logout(UserEntity user);
	public UserEntity validateLogin(UserEntity user);
	public boolean checkUserPrivilegeForRequest(HttpUserRequest request) throws UserDetailsNotFoundException, AccountNotFoundException;

}
