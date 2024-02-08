package com.war.manager.authentication.service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.war.manager.authentication.service.models.TokenEntity;

import jakarta.transaction.Transactional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

	@Query(value = "SELECT * FROM TOKEN_ENTITY WHERE TOKEN IN (:token)", nativeQuery = true)
	List<TokenEntity> findBlackListedTokenByValue(@Param("token") String token);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM TOKEN_ENTITY WHERE EXPIRY_TIMESTAMP<NOW()", nativeQuery = true)
	void cleanupLoggedOutToken();

}
