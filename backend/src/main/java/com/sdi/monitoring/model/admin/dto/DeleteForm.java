package com.sdi.monitoring.model.admin.dto;

import lombok.Data;

@Data
public class DeleteForm {
	private String adminemail;
	private String pw;
	private String deleteemail;
}
