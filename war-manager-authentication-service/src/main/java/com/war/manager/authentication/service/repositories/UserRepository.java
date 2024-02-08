package com.war.manager.authentication.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.war.manager.authentication.service.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

	UserEntity findUserEntityByUsername(String username);

	UserEntity findUserEntityByEmail(String email);
}
