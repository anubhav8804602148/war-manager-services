package com.war.manager.authentication.service.services;

import org.springframework.stereotype.Service;

import com.war.manager.authentication.service.models.AccountEntity;

@Service
public interface AccountService {

	AccountEntity createNewAccount(AccountEntity accountEntity);

	AccountEntity updateAccount(AccountEntity account);

	
}
