package com.war.manager.authentication.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.war.manager.authentication.service.models.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long>{

}
