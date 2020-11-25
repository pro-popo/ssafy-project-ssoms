package com.sdi.monitoring.model.mongo.service;

public interface MongoSchedulingService {
	public boolean start();
	public boolean stop();
	public void mongoRemoveDataScheduler();
}
