package com.sdi.monitoring.model.oracle.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sdi.monitoring.model.oracle.dto.OracleStatusDTO;
import com.sdi.monitoring.model.oracle.dto.SchemaInfoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
