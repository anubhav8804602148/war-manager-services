package com.war.manager.authentication.service.services;

import org.springframework.stereotype.Service;

import com.war.manager.authentication.service.models.ContactEntity;

@Service
public interface ContactService {

	ContactEntity updateContact(ContactEntity contact);

}
