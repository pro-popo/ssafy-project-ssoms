package com.sdi.monitoring.model.user.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Data;

@Data
@Document("user")
public class UserEntity {
	@Id
	private String email;
	@NotNull(message = "user's pw not be null")
	private String pw;
	@NotNull(message = "user's employeeId not be null")
	private String employeeId;
	@NotNull(message = "user's admin not be null")
	private boolean admin;
	@NotNull(message = "user's isReportActivate not be null")
	private boolean isReportActivate;
	@NotNull(message = "user's reportTime not be null")
	private int reportTime;
	@NotNull(message = "user's phoneNumber not be null")
	private String phoneNumber;
	@NotNull(message = "user's name not be null")
	private String name;
	
	public UserEntity() {}
	
	public UserEntity(String email, @NotNull(message = "user's pw not be null") String pw,
									@NotNull(message = "user's employeeId not be null") String employeeId,
									@NotNull(message = "user's admin not be null") boolean admin,
									@NotNull(message = "user's isReportActivate not be null") boolean isReportActivate,
									@NotNull(message = "user's reportTime not be null") int reportTime,
									@NotNull(message = "user's phoneNumber not be null") String phoneNumber,
									@NotNull(message = "user's name not be null") String name) {
		this.email = email;
		this.pw = pw;
		this.employeeId = employeeId;
		this.admin = admin;
		this.isReportActivate = isReportActivate;
		this.reportTime = reportTime;
		this.phoneNumber = phoneNumber;
		this.name = name;
	}
}
