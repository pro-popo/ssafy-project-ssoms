package com.sdi.monitoring.model.oracle.dto;

import java.util.List;

import lombok.Data;

@Data
public class RealTimeMonitoringDTO implements Comparable<RealTimeMonitoringDTO>{
	private String time;
	private OracleStatusDTO oracleStatus;
	private List<SchemaInfoDTO> Schemas;
	private List<SchemaStasticsDTO> allSchemaStastics;
	private List<SchemaQueryDTO> allSchemaQueryInfo;
	
	@Override
	public int compareTo(RealTimeMonitoringDTO o) {
		return this.time.compareTo(o.time);
	}
}
