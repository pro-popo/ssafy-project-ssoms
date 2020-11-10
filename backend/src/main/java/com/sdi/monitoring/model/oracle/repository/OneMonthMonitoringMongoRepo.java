package com.sdi.monitoring.model.oracle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdi.monitoring.model.oracle.entity.OneMonthMonitoringEntity;

public interface OneMonthMonitoringMongoRepo extends MongoRepository<OneMonthMonitoringEntity, String>{
	List<OneMonthMonitoringEntity> findByTimeBetween(String timeGT, String timeLT);
	Optional<OneMonthMonitoringEntity> findByTime(String time);
}
