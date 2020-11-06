package com.sdi.monitoring.model.user.dto;

import lombok.Data;

@Data
public class SetAlarmsDTO {
	private boolean cpu90percent;
	private boolean cpu80percent;
	private boolean cpu70percent;
	private boolean memory90percent;
	private boolean memory80percent;
	private boolean memory70percent;
}
