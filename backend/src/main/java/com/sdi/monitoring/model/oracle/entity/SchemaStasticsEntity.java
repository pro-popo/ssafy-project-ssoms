package com.sdi.monitoring.model.oracle.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Document("realtimedata")
public class SchemaStasticsEntity {
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
	private double elapsedTimeTot;
}
