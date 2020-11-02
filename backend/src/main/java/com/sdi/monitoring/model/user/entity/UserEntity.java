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
@Table(name = "user")
public class UserEntity {
	@Id
	@Column(nullable = false, name = "email")
	private String email;
	@Column(nullable = false, name = "pw")
	private String pw;
	@Column(nullable = false, name = "employee_id")
	private String employeeId;
	@Column(nullable = false, name = "is_admin")
	private boolean admin;
	@Column(nullable = false, name = "is_report_activate")
	private boolean isReportActivate;
	@Column(name = "report_time")
	private int reportTime;
	@Column(nullable = false, name = "phone_number")
	private String phoneNumber;
	@Column(nullable = false, name = "name")
	private String name;
}
