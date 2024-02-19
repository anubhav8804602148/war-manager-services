package com.war.manager.authentication.service.services.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

import javax.crypto.SecretKey;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.war.manager.authentication.service.models.TokenEntity;
import com.war.manager.authentication.service.models.UserEntity;
import com.war.manager.authentication.service.repositories.TokenRepository;
import com.war.manager.authentication.service.services.PasswordService;
import com.war.manager.authentication.service.services.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

	@Value("${auth.jwt.signingKey}")
	private String signingKey;

	@Value("${auth.jwt.expirationTime}")
	private long expirationTime;

	@Autowired
	PasswordService passwordService;
	
	@Autowired
	TokenRepository tokenRepository;
	
	private SecretKey signingKey() {
		return Keys.hmacShaKeyFor(signingKey.getBytes());
	}

	@Override
	public String generateAuthToken(UserEntity user) {
		if (passwordService.validatePassword(user)) {
			return Jwts
					.builder()
					.claim("claim", user.getPassword())
					.issuedAt(new Date(System.currentTimeMillis()))
					.expiration(new Date(System.currentTimeMillis() + expirationTime))
					.subject(user.getUsername())
					.signWith(signingKey(), SIG.HS512).compact();
		}
			
		return null;
	}

	@Override
	public boolean isValidToken(UserEntity user){
		if(Strings.isBlank(user.getAuthToken())) return false;
		try {
			Claims payload = Jwts
					.parser()
					.verifyWith(signingKey())
					.build()
					.parseSignedClaims(user.getAuthToken())
					.getPayload();
			return !isExpired(payload) && !blackListed(user.getAuthToken());
		}
		catch(ExpiredJwtException expiredException) {
			log.error("Auth token expired");
			return false;
		}
		
	}

	private boolean blackListed(String token) {
		return !Strings.isBlank(token) && !tokenRepository.findBlackListedTokenByValue(token).isEmpty();
	}
	
	private boolean isExpired(Claims claim) {
		return claim.getExpiration().before(new Date(System.currentTimeMillis()));
	}

	@Override
	public boolean makeTokenInvalid(UserEntity user) {
		tokenRepository.save(new TokenEntity(0, user.getAuthToken(), user.getUsername(), false, Timestamp.from(Instant.now()), getExpiryTimeFromToken(user.getAuthToken())));
		return true;
	}
	
	public Timestamp getExpiryTimeFromToken(String token) {
		return Timestamp.from(Jwts
				.parser()
				.verifyWith(signingKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getExpiration()
				.toInstant());
	}

	@Override
	public void cleanupLoggedOutToken() {
		tokenRepository.cleanupLoggedOutToken();
	}

}
