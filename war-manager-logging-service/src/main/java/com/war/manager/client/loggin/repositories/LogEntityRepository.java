package com.war.manager.client.loggin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.war.manager.client.loggin.entities.LogEntity;

public interface LogEntityRepository extends JpaRepository<LogEntity, Long>{

	
}
