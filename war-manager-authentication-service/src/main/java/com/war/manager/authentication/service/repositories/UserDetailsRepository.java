package com.war.manager.authentication.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.war.manager.authentication.service.constants.QueryConstants;
import com.war.manager.authentication.service.models.UserDetailsEntity;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Long>{

	@Query(value=QueryConstants.FIND_USER_DETAILS_BY_USERNAME, nativeQuery = true)
	UserDetailsEntity findAllByUsername(String username);

}
