package com.sdi.monitoring.model.oracle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdi.monitoring.model.oracle.entity.SevenDaysMonitoringEntity;

public interface SevenDaysMonitoringMongoRepo extends MongoRepository<SevenDaysMonitoringEntity, String>{
	List<SevenDaysMonitoringEntity> findByTimeBetween(String timeGT, String timeLT);
	Optional<SevenDaysMonitoringEntity> findByTime(String time);
}
