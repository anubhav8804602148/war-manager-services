package com.war.manager.authentication.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.war.manager.authentication.service.models.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>{

}
