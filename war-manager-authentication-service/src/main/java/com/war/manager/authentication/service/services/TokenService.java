package com.war.manager.authentication.service.services;

import org.springframework.stereotype.Service;

import com.war.manager.authentication.service.models.UserEntity;

@Service
public interface TokenService {

	public String generateAuthToken(UserEntity user);

	public boolean isValidToken(UserEntity user);

	public boolean makeTokenInvalid(UserEntity user);

}
