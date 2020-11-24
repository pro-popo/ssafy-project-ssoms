package com.sdi.monitoring.model.mongo.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdi.monitoring.model.oracle.repository.RealTimeMonitoringMongoRepo;
import com.sdi.monitoring.util.MongoScheduler;

@Service
public class MongoSchedulingServiceImpl implements MongoSchedulingService{
	
	@Autowired
	MongoScheduler mongoScheduler;
	
	@Autowired
	private RealTimeMonitoringMongoRepo realTimeMonitoringMongoRepo;

	@Override
	public boolean start() {
		return mongoScheduler.startScheduler();
	}

	@Override
	public boolean stop() {
		return mongoScheduler.stopScheduler();
	}

	@Override
	public void mongoRemoveDataScheduler() {
		realTimeMonitoringMongoRepo.deleteByTimeBetween(findBeforeThreeMonthAndOneDayDate(), findBeforeThreeMonthDate());
	}
	
	private String findBeforeThreeMonthDate() {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(Calendar.MONTH, -3);
		return transFormat.format(calendar.getTime());
	}
	
	private String findBeforeThreeMonthAndOneDayDate() {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(Calendar.MONTH, -3);
		calendar.add(Calendar.DATE, -1);
		return transFormat.format(calendar.getTime());
	}

}
