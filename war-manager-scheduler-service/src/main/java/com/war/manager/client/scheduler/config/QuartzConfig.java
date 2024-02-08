package com.war.manager.client.scheduler.config;

import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.war.manager.client.scheduler.entities.CronEntity;
import com.war.manager.client.scheduler.jobs.ExpiredTokenCLeanupJob;
import com.war.manager.client.scheduler.jobs.JobNames;
import com.war.manager.client.scheduler.repositories.CronRepository;

@Configuration
public class QuartzConfig {

	@Autowired
	CronRepository cronRepository;

	@Bean
	@Primary
	JobDetailFactoryBean dynamicCronJobDetail() {
		JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
		jobDetailFactoryBean.setJobClass(ExpiredTokenCLeanupJob.class);
		jobDetailFactoryBean.setName(JobNames.EXPIRED_TOKEN_CLEANUP_JOB);
		jobDetailFactoryBean.setDurability(true);
		return jobDetailFactoryBean;
	}

	@Bean
	@Primary
	CronTriggerFactoryBean dynamicCronTrigger(JobDetail dynamicCronJobDetail) {
		CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
		triggerFactoryBean.setJobDetail(dynamicCronJobDetail);
		List<CronEntity> cronJob = cronRepository.findCronEntityByJobName(JobNames.EXPIRED_TOKEN_CLEANUP_JOB);
		if (!cronJob.isEmpty()) {
			triggerFactoryBean.setCronExpression(cronJob.get(0).getCronExpression());
		} else {
			throw new IllegalArgumentException("No cron expression found in the database.");
		}
		return triggerFactoryBean;
	}

	@Bean
	@Primary
	SchedulerFactoryBean schedulerFactoryBean(Trigger dynamicCronTrigger, JobDetail jobDetail, DataSource datasource) {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setTriggers(dynamicCronTrigger);
		schedulerFactoryBean.setJobDetails(jobDetail);
		schedulerFactoryBean.setDataSource(datasource);
		schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
		schedulerFactoryBean.setAutoStartup(true);		
		schedulerFactoryBean.setQuartzProperties(quartzProperties());
		return schedulerFactoryBean;
	}

	private Properties quartzProperties() {
		Properties properties = new Properties();
		properties.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
		return properties;
	}
}
