package com.sdi.monitoring.model.oracle.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Document("realtimedata")
public class OracleStatusEntity {
	private double databaseCpuTimeRatio;
	private double databaseWaitTimeRatio;
	private double activeSerialSessions;
	private double dbBlockGetsPerUserCall;
	private double logicalReadsPerUserCall;
	private double redoGeneratedPerSec;
	private double executionsPerSec;
	private double totalParseCountPerSec;
	private double openCursorsPerSec;
	private double userCommitsPerSec;
	private double physicalReadsPerSec;
	private double physicalWritesPerSec;
	private double responseTimePerTxn;
}
