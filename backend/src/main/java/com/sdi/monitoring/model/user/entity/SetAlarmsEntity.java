package com.sdi.monitoring.model.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "set_alarms")
public class SetAlarmsEntity {
	@Id
	@Column(nullable = false, name = "email")
	private String email;
	@Column(nullable = false, name = "cpu_90_percent")
	private boolean cpu90percent;
	@Column(nullable = false, name = "cpu_80_percent")
	private boolean cpu80percent;
	@Column(nullable = false, name = "cpu_70_percent")
	private boolean cpu70percent;
	@Column(nullable = false, name = "memory_90_percent")
	private boolean memory90percent;
	@Column(nullable = false, name = "memory_80_percent")
	private boolean memory80percent;
	@Column(nullable = false, name = "memory_70_percent")
	private boolean memory70percent;
}
