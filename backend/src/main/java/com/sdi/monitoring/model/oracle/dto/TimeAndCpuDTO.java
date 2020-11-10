package com.sdi.monitoring.model.oracle.dto;

import lombok.Data;

@Data
public class TimeAndCpuDTO {
	private String time;
	private double databaseCpuTimeRatio;

	public TimeAndCpuDTO() {}

	public TimeAndCpuDTO(String time, double databaseCpuTimeRatio) {
		this.time = time;
		this.databaseCpuTimeRatio = databaseCpuTimeRatio;
	}

}
