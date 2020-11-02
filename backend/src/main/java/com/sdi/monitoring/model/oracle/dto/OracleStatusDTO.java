package com.sdi.monitoring.model.oracle.dto;

import lombok.Data;

@Data
public class OracleStatusDTO {
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
}
