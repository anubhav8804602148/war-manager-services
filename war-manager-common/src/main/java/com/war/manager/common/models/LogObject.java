package com.war.manager.common.models;

import java.sql.Timestamp;

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

}
