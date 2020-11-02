package com.sdi.monitoring.model.oracle.dto;

import lombok.Data;

@Data
public class AmountUsedBySchemaDTO {
	private String parsingSchemaName;
	private String module;
	private String lastActiveTime;
	private Long executions;
	private double amount;
	private double amountRatio;
	private String sql;
}
