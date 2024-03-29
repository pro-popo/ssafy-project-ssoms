package com.sdi.monitoring.model.oracle.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OracleStatusEntity {
	private double databaseCpuTimeRatio;
	private double databaseWaitTimeRatio;
	private double activeSerialSessions;
	private double dbBlockGetsPerSec;
	private double logicalReadsPerSec;
	private double redoGeneratedPerSec;
	private double executionsPerSec;
	private double totalParseCountPerSec;
	private double openCursorsPerSec;
	private double userCommitsPerSec;
	private double physicalReadsPerSec;
	private double physicalWritesPerSec;
	private double responseTimePerTxn;
}
