package com.war.manager.authentication.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.war.manager.authentication.service.models.ContactEntity;

public interface ContactRepository extends JpaRepository<ContactEntity, Long>{

}
