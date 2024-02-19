package com.war.manager.authentication.service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.war.manager.authentication.service.models.AccountEntity;
import com.war.manager.authentication.service.repositories.AccountRepository;
import com.war.manager.authentication.service.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	AccountRepository accountRepository;

	@Override
	public AccountEntity createNewAccount(AccountEntity accountEntity) {
		if(accountEntity==null) return null;
		return accountRepository.save(accountEntity);
	}

	@Override
	public AccountEntity updateAccount(AccountEntity account) {
		if(account==null) return null;
		return accountRepository.save(account);
	}
}
