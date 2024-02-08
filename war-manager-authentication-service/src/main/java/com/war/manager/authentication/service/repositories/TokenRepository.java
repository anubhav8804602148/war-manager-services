package com.war.manager.authentication.service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.war.manager.authentication.service.models.TokenEntity;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

	@Query(value = "SELECT * FROM TOKEN_ENTITY WHERE TOKEN IN (:subject)", nativeQuery = true)
	List<TokenEntity> findBlackListedTokenByValue(@Param("subject") String subject);

}
