package com.war.manager.common.filters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
import com.war.manager.common.models.HttpUserRequest;
import com.war.manager.common.models.LogObject;
import com.war.manager.common.models.UserEntity;
import com.war.manager.common.services.UserService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Order(PriorityOrdered.HIGHEST_PRECEDENCE)
@Slf4j
public class AuthenticationFilter implements WebFilter {

	public static final String AUTHENTICATION_HEADER = "authenticationHeader";
	public static final String URL_PUSH_LOGS = "http://localhost:9999/login/pushLogs";
	public static final String URL_VALIDATE_LOGIN = "http://localhost:10002/war-manager-authentication-service/authentication/validateLogin";
	public static final String URL_PRIVILEGE_CHECK = "http://localhost:10002/war-manager-authentication-service/authentication/checkUserPrivilegeForRequest";
	private static WebClient webClient = WebClient.create();
	private static final String APP_NAME = "war-manager-common";
	private static Gson gson = new Gson();
	private static final List<String> whiteListedHosts = new ArrayList<>();
	private static final String ALLOWED_ORIGIN;
	private static UserService userService = new UserService();
	
	static {
		try (BufferedReader reader = new BufferedReader(
				new FileReader(new File("D:\\my_projects\\white-listed-hosts.config")))) {
			String line = "";
			while ((line = reader.readLine()) != null) {
				whiteListedHosts.add(line.trim());
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		ALLOWED_ORIGIN = whiteListedHosts.stream().reduce("", (h1, h2) -> h1 + h2);
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		UserEntity user = userService.getUserFromAuthenticationHeaders(request.getHeaders().get(AUTHENTICATION_HEADER));
		Mono<Void> filter;
		if (isPreflightRequest(exchange)) {
			filter = allowAllLocalRequestsAndSubmit(exchange, chain);
		} else if (user == null) {
			filter = unauthorizedResponse(exchange, chain, user);
		} else {
			filter = nonNullValidateUserLogin(user, exchange, chain);
			filter = checkUserPrivilegeForRequest(chain, exchange, user);
		}		
		return filter;
	}


	private Mono<Void> checkUserPrivilegeForRequest(WebFilterChain chain, ServerWebExchange exchange, UserEntity user) {
		return webClient
				.post()
				.uri(URL_PRIVILEGE_CHECK)
				.bodyValue(new HttpUserRequest(user.getUsername(), exchange.getRequest().getURI().getPath()))
				.retrieve()
				.bodyToMono(Boolean.class)
				.flatMap(validResponse -> {
					log.info("Validation Message: {}", validResponse);
					if(Boolean.TRUE.equals(validResponse)) {
						return chain.filter(exchange);
					}
					return unauthorizedResponse(exchange, chain, user);
				})
				.onErrorResume(error -> {
					log.error(error.getMessage());
					return exchange.getResponse().setComplete();
				});
	}


	private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, WebFilterChain chain, UserEntity user) {
		String message = "You do not have access to view this page. Please check with <a href='mailTo:anubhav8804602148@gmail.com'>admin</a>";
		logFailedUserLogin(user);
		DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
		byte[] htmlBytes = message.getBytes();
		exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(401));
		exchange.getResponse().getHeaders().setContentLength(htmlBytes.length);
		exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_HTML);
		return exchange.getResponse().writeWith(Mono.just(bufferFactory.wrap(htmlBytes))).then(chain.filter(exchange));
	}

	private boolean isPreflightRequest(ServerWebExchange exchange) {
		HttpMethod method = exchange.getRequest().getHeaders().getAccessControlRequestMethod();
		return method != null && !Strings.isBlank(method.toString());
	}

	private Mono<Void> allowAllLocalRequestsAndSubmit(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpResponse response = exchange.getResponse();
		HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
		response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
				joinStrings(requestHeaders.getAccessControlRequestHeaders()));
		setAccessControlAllowedOrigin(response.getHeaders());
		response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,
				requestHeaders.getAccessControlRequestMethod().toString());
		response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "authenticationHeader");

		return response.setComplete();
	}

	private void setAccessControlAllowedOrigin(HttpHeaders headers) {
		headers.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);
		if (Strings.isBlank(headers.getAccessControlAllowOrigin())) {
			headers.setAccessControlAllowOrigin(ALLOWED_ORIGIN);
		}
	}

	private String joinStrings(List<String> stringList) {
		return stringList.stream().reduce("", (s1, s2) -> s1 + "," + s2);
	}

	private void logFailedUserLogin(UserEntity user) {
		webClient.post().uri(URL_PUSH_LOGS).contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new LogObject(0, gson.toJson(user), APP_NAME, Timestamp.from(Instant.now()), "LOGIN_FAILED"))
				.retrieve().bodyToMono(String.class).subscribe();
	}

	private void logSuccessLogin(UserEntity user) {
		webClient.post().uri(URL_PUSH_LOGS).contentType(MediaType.APPLICATION_JSON)
				.bodyValue(
						new LogObject(0, gson.toJson(user), APP_NAME, Timestamp.from(Instant.now()), "LOGIN_SUCCESS"))
				.retrieve().bodyToMono(String.class).subscribe();
	}

	private Mono<Void> nonNullValidateUserLogin(UserEntity user, ServerWebExchange exchange, WebFilterChain chain) {
		if (user != null)
			return webClient.post().uri(URL_VALIDATE_LOGIN).bodyValue(user).retrieve().bodyToMono(UserEntity.class)
					.flatMap(authUser -> handleValidatedUserResponse(authUser, exchange, chain))
					.onErrorResume(error -> {
						log.error(error.getMessage());
						return exchange.getResponse().setComplete();
					});
		exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(400));
		Mono<Void> setComplete = exchange.getResponse().setComplete();
		return setComplete;
	}

	private Mono<Void> handleValidatedUserResponse(UserEntity user, ServerWebExchange exchange, WebFilterChain chain) {
		if (user.getAuthToken() != null && !user.getAuthToken().isBlank()) {
			exchange.getResponse().getHeaders().add(AUTHENTICATION_HEADER,
					exchange.getRequest().getHeaders().get(AUTHENTICATION_HEADER).get(0));
			logSuccessLogin(user);
			return chain.filter(exchange);
		} else {
			return unauthorizedResponse(exchange, chain, user);
		}
	}

}
