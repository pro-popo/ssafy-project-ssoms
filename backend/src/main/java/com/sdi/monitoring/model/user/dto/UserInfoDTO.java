package com.sdi.monitoring.model.user.dto;

import lombok.Data;

@Data
public class UserInfoDTO {
	private String employeeId;
	private boolean admin;
	private boolean isReportActivate;
	private int reportTime;
	private String phoneNumber;
	private String name;
}
