package com.sdi.monitoring.model.oracle.service;

import org.springframework.scheduling.annotation.Scheduled;

public interface OracleSchedulingService {
	public void start();
	public void stop();
	public void sampleMethod();
}
