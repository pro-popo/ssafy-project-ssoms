package com.sdi.monitoring.model.oracle.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Document("realtimedata")
public class UsedBySchemaEntity {
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
