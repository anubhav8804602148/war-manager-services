package com.war.manager.common.filters;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
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

import reactor.core.publisher.Mono;


@Component
@Order(PriorityOrdered.HIGHEST_PRECEDENCE)
public class AuthenticationFilter implements WebFilter{

	private static final String AUTHENTICATION_COOKIE = "authenticationCookie";
	private static final String URL_PUSH_LOGS = "http://localhost:9999/login/pushLogs";
	private static final String URL_VALIDATE_LOGIN = "http://localhost:10002/war-manager-authentication-service/authentication/validateLogin";
	private static WebClient webClient = WebClient.create();
	private static final String APP_NAME = "war-manager-common";
	private static Gson gson = new Gson();
	private static Base64.Decoder decoder = Base64.getDecoder();
	private static Base64.Encoder encoder = Base64.getEncoder();
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		UserEntity user = getUserFromAuthenticationCookies(request.getCookies().get(AUTHENTICATION_COOKIE));
		if(nonNullValidateUserLogin(user)) {
			ResponseCookieBuilder cookieBuilder = ResponseCookie.from(AUTHENTICATION_COOKIE, encoder.encodeToString(gson.toJson(user).getBytes()));
			cookieBuilder.httpOnly(true);
			cookieBuilder.secure(true);
			response.addCookie(cookieBuilder.build());
			logSuccessLogin(user);
			return chain.filter(exchange);
		}
		else {
			logFailedUserLogin(user);
			response.setStatusCode(HttpStatusCode.valueOf(401));
			return exchange.getResponse().setComplete();
		}
	}

	private void logFailedUserLogin(UserEntity user) {
		webClient.post().uri(URL_PUSH_LOGS).bodyValue(new LogObject(0, gson.toJson(user), APP_NAME, Timestamp.from(Instant.now()), "LOGIN_FAILED")).retrieve().bodyToMono(String.class).subscribe();
	}

	private void logSuccessLogin(UserEntity user) {
		webClient.post().uri(URL_PUSH_LOGS).bodyValue(new LogObject(0, gson.toJson(user), APP_NAME, Timestamp.from(Instant.now()), "LOGIN_SUCCESS")).retrieve().bodyToMono(String.class).subscribe();
	}

	private boolean nonNullValidateUserLogin(final UserEntity user) {
		if(user==null) return false;
		try {
			webClient
				.post()
				.uri(URL_VALIDATE_LOGIN)
				.bodyValue(user)
				.retrieve()
				.bodyToMono(UserEntity.class)
				.subscribe(authUser -> user.setAuthToken(user.getAuthToken()));
		}
		catch(Exception ex) {
			user.setAuthToken(null);
		}
		return user.getAuthToken()!=null && !user.getAuthToken().isBlank();
	}

	private UserEntity getUserFromAuthenticationCookies(List<HttpCookie> cookies) {
		if(cookies==null || cookies.isEmpty() || cookies.get(0)==null || cookies.get(0).getValue()==null || cookies.get(0).getValue().isBlank()) return null;
		try{
			return gson.fromJson(new String(decoder.decode(cookies.get(0).getValue())), UserEntity.class);
		}
		catch(Exception ex) {
			return null;
		}
	}

}