package com.war.manager.client.loggin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WarManagerLoggingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarManagerLoggingServiceApplication.class, args);
	}

}
