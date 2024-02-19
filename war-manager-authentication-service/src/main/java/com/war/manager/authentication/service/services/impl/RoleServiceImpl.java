package com.war.manager.authentication.service.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.war.manager.authentication.service.exceptions.AccountNotFoundException;
import com.war.manager.authentication.service.exceptions.UserDetailsNotFoundException;
import com.war.manager.authentication.service.models.AccountEntity;
import com.war.manager.authentication.service.models.RoleEntity;
import com.war.manager.authentication.service.models.UserDetailsEntity;
import com.war.manager.authentication.service.repositories.RoleRepository;
import com.war.manager.authentication.service.services.RoleService;
import com.war.manager.authentication.service.services.UserDetailsService;
import com.war.manager.authentication.service.services.UserService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserService userService;
	
	@Override
	public List<RoleEntity> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public List<RoleEntity> getRolesForUsername(String username) throws UserDetailsNotFoundException, AccountNotFoundException{
		UserDetailsEntity userDetails = userDetailsService.findUserDetailsByUsername(username);
		if(userDetails==null) throw new UserDetailsNotFoundException();
		AccountEntity account = userDetails.getAccount();
		if(account==null) throw new AccountNotFoundException();
		return account.getRoles();
	}

	@Override
	public List<RoleEntity> getRolesByAccountId(long accountId) {
		// TODO
		return null;
	}

	@Override
	public RoleEntity getRoleByRoleName(String roleName) {
		return roleRepository.getRoleEntityByRoleName(roleName);
	}

	@Override
	public RoleEntity createNewRole(RoleEntity roleEntity) {
		if(roleEntity==null) return null;
		return roleRepository.save(roleEntity);
	}

	@Override
	public RoleEntity updateRole(RoleEntity roleEntity) {
		if(roleEntity==null) return null;
		return roleRepository.save(roleEntity);
	}

	@Override
	public boolean deleteRoleByRoleId(long roleId) {
		roleRepository.deleteById(roleId);
		return true;
	}

}
