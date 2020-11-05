package com.sdi.monitoring.model.oracle.dto;

import lombok.Data;

@Data
public class UsedBySchemaDTO {
	private String sqlId;
	private String parsingSchemaName;
	private String module;
	private String lastActiveTime;
	private Long executions;
	private double cpuTimePerSec; 
	private double cpuTimeRatio;
	private double elapsedTimePerSec;
	private double elapsedTimeRatio;
	private double bufferGets;
	private double bufferGetsRatio;
	private String sql;
}
