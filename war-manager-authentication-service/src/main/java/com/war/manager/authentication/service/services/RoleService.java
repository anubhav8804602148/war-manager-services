package com.war.manager.authentication.service.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.war.manager.authentication.service.exceptions.AccountNotFoundException;
import com.war.manager.authentication.service.exceptions.UserDetailsNotFoundException;
import com.war.manager.authentication.service.models.RoleEntity;

@Service
public interface RoleService {

	public List<RoleEntity> getAllRoles();
	public List<RoleEntity> getRolesForUsername(String username) throws UserDetailsNotFoundException, AccountNotFoundException;
	public List<RoleEntity> getRolesByAccountId(long accountId);
	public RoleEntity getRoleByRoleName(String roleName);
	public RoleEntity createNewRole(RoleEntity roleEntity);
	public RoleEntity updateRole(RoleEntity roleEntity);
	public boolean deleteRoleByRoleId(long roleId);
	
}
