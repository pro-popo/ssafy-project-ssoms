package com.sdi.monitoring.model.user.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document("user")
public class UserInfo {
	private String pw;
	private String employeeId;
	private boolean admin;
	private boolean isReportActivate;
	private int reportTime;
	private String phoneNumber;
	private String name;

	public UserInfo() {
	}

	public UserInfo(String pw, String employeeId, boolean admin, boolean isReportActivate, int reportTime,
			String phoneNumber, String name) {
		this.pw = pw;
		this.employeeId = employeeId;
		this.admin = admin;
		this.isReportActivate = isReportActivate;
		this.reportTime = reportTime;
		this.phoneNumber = phoneNumber;
		this.name = name;
	}

}
