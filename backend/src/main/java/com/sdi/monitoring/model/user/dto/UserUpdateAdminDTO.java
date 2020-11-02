package com.sdi.monitoring.model.user.dto;

import lombok.Data;

@Data
public class UserUpdateAdminDTO {
	private String prevAdmin;
	private String nextAdmin;
}
