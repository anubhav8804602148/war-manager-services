package com.war.manager.client.scheduler.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.reactive.function.client.WebClient;

public class ExpiredTokenCLeanupJob extends QuartzJobBean{

	private WebClient client = WebClient.create();
	private static final String URL_CLEANUP_LOGGED_OUT_TOKEN = "http://localhost:10000/war-manager-authentication-service/token/cleanupLoggedOutToken";
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		client.get().uri(URL_CLEANUP_LOGGED_OUT_TOKEN).retrieve().bodyToMono(String.class).subscribe();
	}

}
