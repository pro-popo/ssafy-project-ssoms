package com.sdi.monitoring.model.oracle.dto;

import lombok.Data;

@Data
public class SchemaQueryDTO {
	private String sqlId;
	private String parsingSchemaName;
	private String sql;
	private long executions;
	private double bufferGets;
	private double diskReads;
	private double rowsProcessed;
	private double cpuTimeAvg;
	private double elapsedTimeAvg;
}
