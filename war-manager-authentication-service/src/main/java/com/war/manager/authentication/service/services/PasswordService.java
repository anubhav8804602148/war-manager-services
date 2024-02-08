package com.war.manager.authentication.service.services;

import org.springframework.stereotype.Service;

import com.war.manager.authentication.service.models.UserEntity;

@Service
public interface PasswordService {

	public String encodePassword(String password);

	public boolean validatePassword(String username, String password);

	public boolean validatePassword(UserEntity user);
}
