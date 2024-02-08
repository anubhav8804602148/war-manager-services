package com.war.manager.common.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class LoggerConfig {
	
	@Bean
	@Primary
	Logger logger() {
		return LogManager.getLogger();
	}
}
