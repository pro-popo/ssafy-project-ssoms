package com.sdi.monitoring.model.oracle.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SchemaQueryEntity {
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
