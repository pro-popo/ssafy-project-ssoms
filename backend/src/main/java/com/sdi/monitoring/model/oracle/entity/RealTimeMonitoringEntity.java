package com.sdi.monitoring.model.oracle.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
@AllArgsConstructor
@Document("realtimedata")
public class RealTimeMonitoringEntity {
	private String time;
	private OracleStatusEntity oracleStatus;
	private List<SchemaInfoEntity> schemas;
	private List<SchemaStasticsEntity> allSchemaStastics;
	private List<SchemaQueryEntity> allSchemaQueryInfo;
	
	public RealTimeMonitoringEntity() {}
}
