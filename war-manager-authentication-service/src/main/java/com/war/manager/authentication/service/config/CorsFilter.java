package com.war.manager.authentication.service.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class CorsFilter implements WebFilter {

	private static Logger logger = LogManager.getLogger();
	private static final List<String> whiteListedHosts = new ArrayList<>();
	private static final String ALLOWED_ORIGIN;
	static {
		try (BufferedReader reader = new BufferedReader(
				new FileReader(new File("D:\\my_projects\\white-listed-hosts.config")))) {
			String line = "";
			while ((line = reader.readLine()) != null) {
				whiteListedHosts.add(line.trim());
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		ALLOWED_ORIGIN = whiteListedHosts.stream().reduce("", (h1, h2) -> h1 + h2);
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		if (exchange.getRequest().getURI().toString().startsWith("http://localhost:10000")) {
			HttpHeaders headers = exchange.getResponse().getHeaders();
			HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
			headers.setAccessControlAllowHeaders(requestHeaders.getAccessControlRequestHeaders());
			headers.setAccessControlAllowMethods(
					Arrays.asList(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE));
			setAccessControlAllowedOrigin(headers);
			headers.setAccessControlExposeHeaders(List.of("authenticationHeader"));
		}
		return chain.filter(exchange);
	}

	private void setAccessControlAllowedOrigin(HttpHeaders headers) {
		headers.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);
		if(Strings.isBlank(headers.getAccessControlAllowOrigin())){
			headers.setAccessControlAllowOrigin(ALLOWED_ORIGIN);
		}
	}
}
