package com.war.manager.authentication.service.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import com.war.manager.authentication.service.models.UserDetailsEntity;
import com.war.manager.authentication.service.repositories.UserDetailsRepository;
import com.war.manager.authentication.service.services.AccountService;
import com.war.manager.authentication.service.services.ContactService;
import com.war.manager.authentication.service.services.UserDetailsService;
import com.war.manager.authentication.service.services.UserService;
import com.war.manager.common.filters.AuthenticationFilter;
import com.war.manager.common.models.UserEntity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserDetailsRepository userDetailsRepository;

	@Autowired
	AccountService accountService;

	@Autowired
	UserService userService;

	@Autowired
	ContactService contactService;

	@Override
	public List<UserDetailsEntity> findAllUserDetails() {
		return userDetailsRepository.findAll();
	}

	@Override
	public UserDetailsEntity findUserDetailsByUsername(String username) {
		return userDetailsRepository.findAllByUsername(username);
	}

	@Override
	public UserDetailsEntity createNewUserDetails(UserDetailsEntity userDetailsEntity) {
		if (userDetailsEntity == null)
			return null;
		userDetailsEntity.setAccount(accountService.updateAccount(userDetailsEntity.getAccount()));
		userDetailsEntity.setUser(userService.updateUser(userDetailsEntity.getUser()));
		userDetailsEntity.setContact(contactService.updateContact(userDetailsEntity.getContact()));
		userDetailsEntity.setBackupContact(contactService.updateContact(userDetailsEntity.getBackupContact()));
		return userDetailsRepository.save(userDetailsEntity);
	}

	@Override
	public UserDetailsEntity updateUserDetails(UserDetailsEntity userDetailsEntity) {
		if (userDetailsEntity == null)
			return null;
		userDetailsEntity.setAccount(accountService.updateAccount(userDetailsEntity.getAccount()));
		userDetailsEntity.setUser(userService.updateUser(userDetailsEntity.getUser()));
		userDetailsEntity.setContact(contactService.updateContact(userDetailsEntity.getContact()));
		userDetailsEntity.setBackupContact(contactService.updateContact(userDetailsEntity.getBackupContact()));

		return userDetailsRepository.save(userDetailsEntity);
	}

	@Override
	public UserDetailsEntity findUserDetailsByRequest(ServerWebExchange exchange) {
		UserEntity user = new com.war.manager.common.services.UserService().getUserFromAuthenticationHeaders(exchange
				.getRequest().getHeaders().getOrDefault(AuthenticationFilter.AUTHENTICATION_HEADER, new ArrayList<>()));
		if(user==null) return null;
		return findUserDetailsByUsername(user.getUsername());
	}

}
