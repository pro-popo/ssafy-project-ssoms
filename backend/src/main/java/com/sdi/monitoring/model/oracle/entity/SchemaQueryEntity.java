package com.sdi.monitoring.model.oracle.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Document("realtimedata")
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
