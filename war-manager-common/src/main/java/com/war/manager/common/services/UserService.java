package com.war.manager.common.services;

import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.war.manager.common.models.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	static Gson gson = new Gson();	
	private static Base64.Decoder decoder = Base64.getDecoder();
	
	public UserEntity getUserFromAuthenticationHeaders(List<String> authenticationHeaders) {
		if (authenticationHeaders == null || authenticationHeaders.isEmpty() || authenticationHeaders.get(0) == null
				|| authenticationHeaders.get(0).isBlank())
			return null;
		try {
			return gson.fromJson(new String(decoder.decode(authenticationHeaders.get(0))), UserEntity.class);
		} catch (Exception ex) {
			log.error("Error while parsing auth header {}", ex.getMessage());
			return null;
		}
	}
}
