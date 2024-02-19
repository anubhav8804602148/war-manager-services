package com.war.manager.authentication.service.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import com.war.manager.authentication.service.models.UserDetailsEntity;

@Service
public interface UserDetailsService {

	public List<UserDetailsEntity> findAllUserDetails();

	public UserDetailsEntity findUserDetailsByUsername(String username);

	public UserDetailsEntity createNewUserDetails(UserDetailsEntity userDetailsEntity);

	public UserDetailsEntity updateUserDetails(UserDetailsEntity userDetailsEntity);

	public UserDetailsEntity findUserDetailsByRequest(ServerWebExchange exchange);

}
