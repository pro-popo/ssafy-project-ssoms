package com.sdi.monitoring.model.user.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document("user")
public class UserInfoEntity {
	private String pw;
	private String employeeId;
	private boolean admin;
	private boolean isReportActivate;
	private int reportTime;
	private String phoneNumber;
	private String name;

	public UserInfoEntity() {
	}

	public UserInfoEntity(String pw, String employeeId, boolean admin, boolean isReportActivate, int reportTime,
			String phoneNumber, String name) {
		this.pw = pw;
		this.employeeId = employeeId;
		this.admin = admin;
		this.isReportActivate = isReportActivate;
		this.reportTime = reportTime;
		this.phoneNumber = phoneNumber;
		this.name = name;
	}
	
	public void ChangeAdmin() {
		System.out.println(this.admin);
		this.admin = !this.admin;
		System.out.println(this.admin);
	}

}
