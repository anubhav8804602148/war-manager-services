package com.war.manager.client.gateway.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {

	private long userId;
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private String authToken;
	
}
