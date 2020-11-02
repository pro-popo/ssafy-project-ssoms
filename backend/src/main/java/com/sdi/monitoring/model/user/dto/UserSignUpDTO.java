package com.sdi.monitoring.model.user.dto;

import lombok.Data;

@Data
public class UserSignUpDTO {
	private String email;
	private String name;
	private String pw;
	private String employeeId;
	private String phoneNumber;
	private boolean admin;
}
