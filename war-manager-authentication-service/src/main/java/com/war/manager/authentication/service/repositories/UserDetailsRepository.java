package com.war.manager.authentication.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.war.manager.authentication.service.models.UserDetailsEntity;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Long>{

}
