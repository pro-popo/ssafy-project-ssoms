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
@Table(name = "user_visit_times")
public class UserVisitTimes {
	@Id
	@Column(nullable = false, name = "email")
	private String email;
	@Column(nullable = false, name = "time")
	private String time;
}
