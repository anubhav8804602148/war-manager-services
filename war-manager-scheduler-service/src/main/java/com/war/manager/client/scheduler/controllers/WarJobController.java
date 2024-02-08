package com.war.manager.client.scheduler.controllers;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/war-jobs")
public class WarJobController {

	@Autowired
	Scheduler scheduler;
	
	@GetMapping("/fetchAll")
	public String fetchAllWarJobs() throws SchedulerException {
		return "Success"+scheduler.isStarted()+scheduler.getCurrentlyExecutingJobs()+scheduler.getCalendarNames();
	}
	
}
