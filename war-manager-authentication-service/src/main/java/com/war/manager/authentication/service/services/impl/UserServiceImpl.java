package com.war.manager.authentication.service.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.war.manager.authentication.service.models.UserEntity;
import com.war.manager.authentication.service.repositories.UserRepository;
import com.war.manager.authentication.service.services.PasswordService;
import com.war.manager.authentication.service.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	PasswordService passwordService;

	@Autowired
	UserRepository userRepository;

	@Override
	public UserEntity findUserByUsername(String username) {
		return userRepository.findUserEntityByUsername(username);
	}

	@Override
	public UserEntity findUserByEmail(String email) {
		return userRepository.findUserEntityByEmail(email);
	}

	@Override
	public UserEntity createNewUser(UserEntity user) {
		if (user == null)
			return null;
		user.setPassword(passwordService.encodePassword(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public List<UserEntity> fetchAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public UserEntity updateUser(UserEntity user) {
		if (user == null)
			return null;
		user.setPassword(passwordService.encodePassword(user.getPassword()));
		return userRepository.save(user);
	}

}
