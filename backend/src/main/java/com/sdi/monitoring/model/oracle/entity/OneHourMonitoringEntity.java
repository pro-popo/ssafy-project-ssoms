package com.sdi.monitoring.model.oracle.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Document("onehourdata")
public class OneHourMonitoringEntity {
	private String time;
	private OracleStatusEntity oracleStatus;
	private List<SchemaInfoEntity> schemas;
	private List<SchemaStasticsEntity> allSchemaStastics;
	private List<SchemaQueryEntity> allSchemaQueryInfo;
	
	public OneHourMonitoringEntity() {}
}
