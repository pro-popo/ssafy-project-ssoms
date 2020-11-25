package com.sdi.monitoring.model.oracle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sdi.monitoring.model.oracle.entity.RealTimeMonitoringEntity;

public interface RealTimeMonitoringMongoRepo extends MongoRepository<RealTimeMonitoringEntity, String>{
	List<RealTimeMonitoringEntity> findByTimeBetween(String timeGT, String timeLT);
	Optional<RealTimeMonitoringEntity> findByTime(String time);
	Long deleteByTimeBetween(String timeGT, String timeLT);
	
	@Query(value = "{time : ?0}", exists = true)
	boolean existsByTime(String time);
}
