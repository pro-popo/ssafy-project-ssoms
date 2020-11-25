package com.sdi.monitoring.model.oracle.service;

public interface OracleSchedulingService {
	public boolean hasScheduler();
	public boolean start();
	public boolean stop();
	public void oracleGetDataScheduler();
}
