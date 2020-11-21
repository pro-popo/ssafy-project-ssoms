package com.sdi.monitoring.model.oracle.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Document("outlierdata")
public class OutlierDataEntity {
	private String time;
	private double databaseCpuTimeRatio;
	public OutlierDataEntity() {}
}