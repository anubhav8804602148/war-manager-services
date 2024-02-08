package com.war.manager.client.scheduler.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.war.manager.client.scheduler.entities.CronEntity;

public interface CronRepository extends JpaRepository<CronEntity, Long>{

	List<CronEntity> findCronEntityByJobName(String jobName);

}
