package com.sdi.monitoring.model.oracle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdi.monitoring.model.oracle.entity.OneHourMonitoringEntity;

public interface OneHourMonitoringMongoRepo extends MongoRepository<OneHourMonitoringEntity, String>{
	List<OneHourMonitoringEntity> findByTimeBetween(String timeGT, String timeLT);
	Optional<OneHourMonitoringEntity> findByTime(String time);
	Long deleteByTimeBetween(String timeGT, String timeLT);
}
