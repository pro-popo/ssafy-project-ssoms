package com.sdi.monitoring.model.user.entity;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document("user")
public class UserVisitTimesEntity {
	private List<String> time;

	public UserVisitTimesEntity() {}

	public UserVisitTimesEntity(List<String> time) {
		this.time = time;
	}
	
	public void addTime(String date) {
		if(this.time == null) {
			this.time = new LinkedList<>();
		}else if(this.time.size() >= 10) {
			this.time.remove(0);
		}
		this.time.add(date);
	}
}
