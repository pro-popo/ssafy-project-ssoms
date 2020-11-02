package com.sdi.monitoring.model.oracle.dto;

import lombok.Data;

@Data
public class SchemaStasticsDTO {
	private String parsingSchemaName;
	private long sqlCnt;
	private long executions;
	private double bufferGetsAvg;
	private double diskReadsAvg;
	private double rowsProcessedAvg;
	private double cpuTimeAvg;
	private double elapsedTimeAvg;
	private double cpuTimeMax;
	private double elapsedTimeMax;
	private double cpuTimeTot;
	private double elasedTimeTot;
}
