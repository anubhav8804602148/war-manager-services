package com.war.manager.client.scheduler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class RequestResponseLoggingFilter implements WebFilter{

	@Autowired
	WebClient webClient;
	
    public RequestResponseLoggingFilter(WebClient webClient) {
        this.webClient = webClient;
    }
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		webClient
			.get()
			.uri("http://localhost:9999/login/pushLogs")
			.retrieve()
			.bodyToMono(String.class)
			.subscribe();
		return chain.filter(exchange);
	}

}
