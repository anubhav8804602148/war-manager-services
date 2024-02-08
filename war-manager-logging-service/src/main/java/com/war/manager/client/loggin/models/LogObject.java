package com.war.manager.client.loggin.models;

import java.sql.Timestamp;

import com.war.manager.client.loggin.entities.LogEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogObject {
	
	private long id;
	private String log;
	private String loggingApp;
	private Timestamp logTimestamp;
	private String logLevel;
	
	public LogEntity getLogEntity(LogObject logObject) {
		LogEntity logEntity = new LogEntity();
		logEntity.setId(id);
		logEntity.setLog(log);
		logEntity.setLoggingApp(loggingApp);
		logEntity.setLogTimestamp(logTimestamp);
		logEntity.setLogLevel(logLevel);
		return logEntity;
	}
}
