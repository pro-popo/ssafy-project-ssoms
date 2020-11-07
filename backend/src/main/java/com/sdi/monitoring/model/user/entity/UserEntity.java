package com.sdi.monitoring.model.user.entity;


import java.util.LinkedList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document("user")
public class UserEntity {
	@Id
	private String email;
	private UserInfoEntity info;
	private SetAlarmsEntity alarms;
	private UserVisitTimesEntity visit;

	public UserEntity() {}

	public UserEntity(String email, UserInfoEntity info, SetAlarmsEntity alarms,
			UserVisitTimesEntity visit) {
		this.email = email;
		this.info = info;
		this.alarms = alarms;
		this.visit = visit;
	}
}
