package com.sdi.monitoring.model.oracle.dto;

import lombok.Data;

@Data
public class OracleDBSettingsDTO {
	private String oracleURL;
	private String oracleID;
	private String oraclePassword;
	private String oracleEdition;
}
