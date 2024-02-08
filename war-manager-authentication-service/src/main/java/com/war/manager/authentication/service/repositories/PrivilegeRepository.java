package com.war.manager.authentication.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.war.manager.authentication.service.models.PrivilegeEntity;

public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Long>{

}
