package com.sdi.monitoring.model.oracle.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Document("onedaydata")
public class OneDayMonitoringEntity {
	private String time;
	private OracleStatusEntity oracleStatus;
	private List<SchemaInfoEntity> schemas;
	private List<SchemaStasticsEntity> allSchemaStastics;
	private List<SchemaQueryEntity> allSchemaQueryInfo;

	public OneDayMonitoringEntity() {}
}
