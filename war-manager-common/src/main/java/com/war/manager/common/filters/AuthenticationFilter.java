package com.war.manager.common.filters;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.google.gson.Gson;
import com.war.manager.common.models.LogObject;
import com.war.manager.common.models.UserEntity;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@Component
@Order(PriorityOrdered.HIGHEST_PRECEDENCE)
@Slf4j
public class AuthenticationFilter implements WebFilter{

	private static final String AUTHENTICATION_HEADER = "authenticationHeader";
	private static final String URL_PUSH_LOGS = "http://localhost:9999/login/pushLogs";
	private static final String URL_VALIDATE_LOGIN = "http://localhost:10002/war-manager-authentication-service/authentication/validateLogin";
	private static WebClient webClient = WebClient.create();
	private static final String APP_NAME = "war-manager-common";
	private static Gson gson = new Gson();
	private static Base64.Decoder decoder = Base64.getDecoder();
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		UserEntity user = getUserFromAuthenticationHeaders(request.getHeaders().get(AUTHENTICATION_HEADER));
		return nonNullValidateUserLogin(user, exchange, chain);
	}

	private void logFailedUserLogin(UserEntity user) {
		webClient.post().uri(URL_PUSH_LOGS).contentType(MediaType.APPLICATION_JSON).bodyValue(new LogObject(0, gson.toJson(user), APP_NAME, Timestamp.from(Instant.now()), "LOGIN_FAILED")).retrieve().bodyToMono(String.class).subscribe();
	}

	private void logSuccessLogin(UserEntity user) {
		webClient.post().uri(URL_PUSH_LOGS).contentType(MediaType.APPLICATION_JSON).bodyValue(new LogObject(0, gson.toJson(user), APP_NAME, Timestamp.from(Instant.now()), "LOGIN_SUCCESS")).retrieve().bodyToMono(String.class).subscribe();
	}

	private Mono<Void> nonNullValidateUserLogin(UserEntity user, ServerWebExchange exchange, WebFilterChain chain) {
		return webClient
			.post()
			.uri(URL_VALIDATE_LOGIN)
			.bodyValue(user)
			.retrieve()
			.bodyToMono(UserEntity.class)
			.flatMap(authUser -> handleValidatedUserResponse(authUser, exchange, chain))
			.onErrorResume(error -> exchange.getResponse().setComplete());
	}

	private Mono<Void> handleValidatedUserResponse(UserEntity user, ServerWebExchange exchange, WebFilterChain chain) {
		if(user.getAuthToken()!=null && !user.getAuthToken().isBlank()) {
			exchange.getResponse().getHeaders().add(AUTHENTICATION_HEADER, exchange.getRequest().getHeaders().get(AUTHENTICATION_HEADER).get(0));
			logSuccessLogin(user);
			return chain.filter(exchange);
		}
		else {
			logFailedUserLogin(user);
			exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(401));
			return exchange.getResponse().setComplete();
		}
	}

	private UserEntity getUserFromAuthenticationHeaders(List<String> authenticationHeaders) {
		if(authenticationHeaders==null || authenticationHeaders.isEmpty() || authenticationHeaders.get(0)==null || authenticationHeaders.get(0).isBlank()) return null;
		try{
			return gson.fromJson(new String(decoder.decode(authenticationHeaders.get(0))), UserEntity.class);
		}
		catch(Exception ex) {
			log.error("Error while parsing auth header {}", ex.getMessage());
			return null;
		}
	}

}
