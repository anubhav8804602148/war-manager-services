package com.war.manager.client.scheduler.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/war-jobs")
public class WarJobController {

	@GetMapping("/fetchAll")
	public String fetchAllWarJobs() {
		return "Success";
	}
	
}
