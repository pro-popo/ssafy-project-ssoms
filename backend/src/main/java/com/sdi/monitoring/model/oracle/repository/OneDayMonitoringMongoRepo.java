package com.sdi.monitoring.model.oracle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdi.monitoring.model.oracle.entity.OneDayMonitoringEntity;

public interface OneDayMonitoringMongoRepo extends MongoRepository<OneDayMonitoringEntity, String>{
	List<OneDayMonitoringEntity> findByTimeBetween(String timeGT, String timeLT);
	Optional<OneDayMonitoringEntity> findByTime(String time);
}
