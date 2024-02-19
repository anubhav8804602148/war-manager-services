package com.war.manager.authentication.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.war.manager.authentication.service.models.PrivilegeEntity;

public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Long>{

	PrivilegeEntity findByPrivilegeName(String privilegeName);

	boolean deleteByPrivilegeName(String privilegeName);

	@Query(value="SELECT PRIVILEGE FROM PATH_PRIVILEGE_MAP where PATH=?1", nativeQuery=true)
	String getRequiredPrivilege(String path);

}
