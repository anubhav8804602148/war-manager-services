package com.war.manager.client.gateway.config;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

public class CorsFilter implements WebFilter{

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		if(exchange.getRequest().getURI().toString().startsWith("http://localhost")) {
			HttpHeaders headers = exchange.getResponse().getHeaders();
			HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
			headers.setAccessControlAllowHeaders(requestHeaders.getAccessControlRequestHeaders());
			headers.setAccessControlAllowMethods(Arrays.asList(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE));
			headers.setAccessControlAllowOrigin(requestHeaders.getOrigin());
		}
		return chain.filter(exchange);
	}

}
