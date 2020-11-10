package com.sdi.monitoring.model.oracle.dto;

import lombok.Data;

@Data
public class TimeAndCpuDTO {
	private String time;
	private String databaseCpuTimeRatio;
}
