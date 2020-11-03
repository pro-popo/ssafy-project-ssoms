package com.sdi.monitoring.model.user.entity;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document("user")
public class UserEntity {
	@Id
	private String email;
	private UserInfo info;
	private SetAlarms alarms;
	private UserVisitTimes visit;

	public UserEntity() {}

	public UserEntity(String email, UserInfo info, SetAlarms alarms,
			UserVisitTimes visit) {
		this.email = email;
		this.info = info;
		this.alarms = alarms;
		this.visit = visit;
	}
}
