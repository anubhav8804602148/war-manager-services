package com.war.manager.client.arms.supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@ComponentScan(basePackages = {"com.war.manager.common", "com.war.manager.client.arms.supplier"})
public class WarManagerArmsSupplierServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarManagerArmsSupplierServiceApplication.class, args);
	}

}
