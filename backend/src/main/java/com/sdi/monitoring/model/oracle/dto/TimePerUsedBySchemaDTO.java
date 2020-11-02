package com.sdi.monitoring.model.oracle.dto;

import lombok.Data;

@Data
public class TimePerUsedBySchemaDTO {
	private String parsingSchemaName;
	private String module;
	private String lastActiveTime;
	private Long executions;
	private double timePerSec;
	private double timeRatio;
	private String sql;
}
