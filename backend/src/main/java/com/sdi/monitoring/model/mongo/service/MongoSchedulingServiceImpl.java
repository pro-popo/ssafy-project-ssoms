package com.sdi.monitoring.model.mongo.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.sdi.monitoring.util.MongoScheduler;

public class MongoSchedulingServiceImpl implements MongoSchedulingService{
	
	@Autowired
	MongoScheduler mongoScheduler;

	@Override
	public boolean start() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stop() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mongoRemoveDataScheduler() {
		// TODO Auto-generated method stub
		
	}

}
