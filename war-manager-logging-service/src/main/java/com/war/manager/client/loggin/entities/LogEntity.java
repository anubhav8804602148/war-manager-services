package com.war.manager.client.loggin.entities;

import java.sql.Timestamp;

import com.war.manager.client.loggin.models.LogObject;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String log;
	private String loggingApp;
	private Timestamp logTimestamp;
	private String logLevel;
	

	public LogEntity(LogObject logObject) {
		this.id = logObject.getId();
		this.log = logObject.getLog();
		this.loggingApp = logObject.getLoggingApp();
		this.logTimestamp = logObject.getLogTimestamp();
		this.logLevel = logObject.getLogLevel();
	}
}
