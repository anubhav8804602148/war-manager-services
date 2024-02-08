package com.war.manager.client.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WarManagerSchedulerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarManagerSchedulerServiceApplication.class, args);
	}

}
