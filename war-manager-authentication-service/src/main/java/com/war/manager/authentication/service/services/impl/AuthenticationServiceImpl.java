package com.war.manager.authentication.service.services.impl;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.war.manager.authentication.service.exceptions.AccountNotFoundException;
import com.war.manager.authentication.service.exceptions.BadCredentialException;
import com.war.manager.authentication.service.exceptions.UserDetailsNotFoundException;
import com.war.manager.authentication.service.models.HttpUserRequest;
import com.war.manager.authentication.service.models.UserEntity;
import com.war.manager.authentication.service.services.AuthenticationService;
import com.war.manager.authentication.service.services.PrivilegeService;
import com.war.manager.authentication.service.services.TokenService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	TokenService tokenService;
	
	@Autowired
	PrivilegeService privilegeService;
	
	
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

	@Override
	public boolean checkUserPrivilegeForRequest(HttpUserRequest request) throws UserDetailsNotFoundException, AccountNotFoundException {
		String requiredPrivilege = privilegeService.getRequiredPrivilege(request.getPath());
		if(Strings.isBlank(requiredPrivilege)) return true;
		return privilegeService.getPrivilegesForUsername(request.getRequestor())
				.parallelStream()
				.map(priv -> priv.getPrivilegeName())
				.anyMatch(privName -> privName.equals(requiredPrivilege));
	}

}
