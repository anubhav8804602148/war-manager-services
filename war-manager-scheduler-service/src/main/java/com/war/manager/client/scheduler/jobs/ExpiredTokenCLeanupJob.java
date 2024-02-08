package com.war.manager.client.scheduler.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.reactive.function.client.WebClient;

public class ExpiredTokenCLeanupJob implements Job{

	private WebClient client = WebClient.create();
	private static final String URL_CLEANUP_LOGGED_OUT_TOKEN = "http://localhost:10000/war-manager-authentication-service/token/cleanupLoggedOutToken";
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		client.get().uri(URL_CLEANUP_LOGGED_OUT_TOKEN).retrieve().bodyToMono(String.class).subscribe();
	}

}
