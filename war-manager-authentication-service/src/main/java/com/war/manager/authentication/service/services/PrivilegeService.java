package com.war.manager.authentication.service.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import com.war.manager.authentication.service.exceptions.AccountNotFoundException;
import com.war.manager.authentication.service.exceptions.UserDetailsNotFoundException;
import com.war.manager.authentication.service.models.PrivilegeEntity;

@Service
public interface PrivilegeService {

	public List<PrivilegeEntity> getAllPrivileges();
	public List<PrivilegeEntity> getPrivilegesForRole(String roleName);
	public List<PrivilegeEntity> getPrivilegesForUsername(String username) throws UserDetailsNotFoundException, AccountNotFoundException;
	public PrivilegeEntity getPrivilegesByName(String privilegeName);
	public PrivilegeEntity createNewPrivilege(PrivilegeEntity privilegeEntity);
	public PrivilegeEntity updatePrivilegeName(PrivilegeEntity privilegeEntity);
	public boolean removePrivilegeByName(String privilegeName);
	public boolean checkPrivilegeExists(String privilegeName, ServerWebExchange exchange) throws UserDetailsNotFoundException, AccountNotFoundException;
	public String getRequiredPrivilege(String path);
	
}
