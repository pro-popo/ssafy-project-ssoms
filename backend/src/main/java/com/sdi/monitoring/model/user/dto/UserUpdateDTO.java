package com.sdi.monitoring.model.user.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
	private String email;
	private String pw;
	private String newPw;
	private String employeeId;
	private String name;
	private String phoneNumber;
}
