package com.sdi.monitoring.model.user.entity;

import java.util.List;

import javax.persistence.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("user_visit_times")
public class UserVisitTimes {
	private List<String> time;

	public UserVisitTimes() {}

	public UserVisitTimes(List<String> time) {
		this.time = time;
	}

}
