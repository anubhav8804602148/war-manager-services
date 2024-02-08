package com.war.manager.client.gateway.config;

import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class RequestResponseFilter implements WebFilter{

	WebClient webClient = WebClient.create();
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		webClient.post().uri("http://localhost:9999/login/pushLogs").bodyValue(new HashMap<>()).retrieve().bodyToMono(String.class).subscribe();
		return chain.filter(exchange);
	}

}
