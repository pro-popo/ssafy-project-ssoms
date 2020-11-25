package com.sdi.monitoring.model.oracle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdi.monitoring.model.oracle.entity.SixHoursMonitoringEntity;

public interface SixHoursMonitoringMongoRepo extends MongoRepository<SixHoursMonitoringEntity, String>{
	List<SixHoursMonitoringEntity> findByTimeBetween(String timeGT, String timeLT);
	Optional<SixHoursMonitoringEntity> findByTime(String time);
//	Long deleteByTimeBetween(String timeGT, String timeLT);
}
