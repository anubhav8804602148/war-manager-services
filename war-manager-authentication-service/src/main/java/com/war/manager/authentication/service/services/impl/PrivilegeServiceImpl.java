package com.war.manager.authentication.service.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import com.war.manager.authentication.service.exceptions.AccountNotFoundException;
import com.war.manager.authentication.service.exceptions.UserDetailsNotFoundException;
import com.war.manager.authentication.service.models.PrivilegeEntity;
import com.war.manager.authentication.service.repositories.PrivilegeRepository;
import com.war.manager.authentication.service.services.PrivilegeService;
import com.war.manager.authentication.service.services.RoleService;
import com.war.manager.common.filters.AuthenticationFilter;
import com.war.manager.common.models.UserEntity;
import com.war.manager.common.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	PrivilegeRepository privilegeRepository;
	
	@Autowired
	RoleService roleService;
	
	UserService userCommonService = new UserService();

	@Override
	public List<PrivilegeEntity> getAllPrivileges() {
		return privilegeRepository.findAll();
	}

	@Override
	public List<PrivilegeEntity> getPrivilegesForRole(String roleName) {
		return roleService.getRoleByRoleName(roleName).getPrivileges();
	}

	@Override
	public List<PrivilegeEntity> getPrivilegesForUsername(String username) throws UserDetailsNotFoundException, AccountNotFoundException {
		return roleService
				.getRolesForUsername(username)
				.parallelStream()
				.flatMap(role -> role.getPrivileges().stream())
				.toList();
	}

	@Override
	public PrivilegeEntity getPrivilegesByName(String privilegeName) {
		return privilegeRepository.findByPrivilegeName(privilegeName);
	}

	@Override
	public PrivilegeEntity createNewPrivilege(PrivilegeEntity privilegeEntity) {
		if(privilegeEntity==null) return null;
		return privilegeRepository.save(privilegeEntity);
	}

	@Override
	public PrivilegeEntity updatePrivilegeName(PrivilegeEntity privilegeEntity) {
		if(privilegeEntity==null) return null;
		return privilegeRepository.save(privilegeEntity);
	}

	@Override
	public boolean removePrivilegeByName(String privilegeName) {
		return privilegeRepository.deleteByPrivilegeName(privilegeName);
	}

	@Override
	public boolean checkPrivilegeExists(String privilegeName, ServerWebExchange exchange) throws UserDetailsNotFoundException, AccountNotFoundException {
		UserEntity user = userCommonService.getUserFromAuthenticationHeaders(exchange.getRequest().getHeaders().getOrDefault(AuthenticationFilter.AUTHENTICATION_HEADER, new ArrayList<>()));
		if(user!=null) {
			return getPrivilegesForUsername(user.getUsername()).stream().anyMatch(priv -> priv.getPrivilegeName().equals(privilegeName));
		}
		return false;
	}

	@Override
	public String getRequiredPrivilege(String path) {
		String priv = privilegeRepository.getRequiredPrivilege(path);
		log.info("Path: {} Priv: {}", path, priv);
		return priv;
	}	
	
}
