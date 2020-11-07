package com.sdi.monitoring.model.user.dto;

import lombok.Data;

@Data
public class UserDTO {
	private String email;
	private UserInfoDTO info;
	private SetAlarmsDTO alarms;
	private UserVisitTimesDTO visit;
}
