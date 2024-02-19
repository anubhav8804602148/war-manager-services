package com.war.manager.map.layer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@ComponentScan(basePackages = {"com.war.manager.common", "com.war.manager.map.layer"})
public class WarManagerMapLayerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarManagerMapLayerServiceApplication.class, args);
	}

}
