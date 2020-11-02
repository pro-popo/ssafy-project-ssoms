package com.sdi.monitoring.model.user.dto;

import lombok.Data;

@Data
public class UserDTO {
	private String email;
	private String employeeId;
	private boolean admin;
	private String phoneNumber;
	private String name;
}
