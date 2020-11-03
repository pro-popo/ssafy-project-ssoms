package com.sdi.monitoring.model.user.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document("user")
public class UserVisitTimes {
	private List<String> time;

	public UserVisitTimes() {}

	public UserVisitTimes(List<String> time) {
		this.time = time;
	}

}
