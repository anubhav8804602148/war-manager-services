package com.war.manager.client.loggin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.war.manager.client.loggin.entities.LogEntity;
import com.war.manager.client.loggin.repositories.LogEntityRepository;

@Service
public class LoginLoggerService {

	@Autowired
	LogEntityRepository logEntityRepository;

	public void saveNewLog(LogEntity logEntity) {
		logEntityRepository.save(logEntity);
	}
}
