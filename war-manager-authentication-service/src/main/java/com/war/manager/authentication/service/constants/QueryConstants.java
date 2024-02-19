package com.war.manager.authentication.service.constants;

public class QueryConstants {

	public static final String FIND_USER_DETAILS_BY_USERNAME = "SELECT * FROM USER_DETAILS_ENTITY UDE WHERE USER_USER_ID IN ("
			+ "SELECT UE.USER_ID FROM USER_ENTITY UE WHERE UE.USERNAME = ?1)";
	

}
