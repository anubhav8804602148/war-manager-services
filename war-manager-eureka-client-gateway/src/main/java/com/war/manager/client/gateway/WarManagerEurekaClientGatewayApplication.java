package com.war.manager.client.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WarManagerEurekaClientGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarManagerEurekaClientGatewayApplication.class, args);
	}

}
