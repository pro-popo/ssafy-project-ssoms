package com.sdi.monitoring.model.user.entity;

import javax.persistence.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("set_alarms")
public class SetAlarmsEntity {
	private boolean cpu90percent;
	private boolean cpu80percent;
	private boolean cpu70percent;
	private boolean memory90percent;
	private boolean memory80percent;
	private boolean memory70percent;

	public SetAlarmsEntity() {}
	
	public SetAlarmsEntity(boolean cpu90percent, boolean cpu80percent, boolean cpu70percent, boolean memory90percent,
			boolean memory80percent, boolean memory70percent) {
		this.cpu90percent = cpu90percent;
		this.cpu80percent = cpu80percent;
		this.cpu70percent = cpu70percent;
		this.memory90percent = memory90percent;
		this.memory80percent = memory80percent;
		this.memory70percent = memory70percent;
	}
}
