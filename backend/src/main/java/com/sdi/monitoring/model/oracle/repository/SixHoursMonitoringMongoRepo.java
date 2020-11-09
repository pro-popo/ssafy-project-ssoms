package com.sdi.monitoring.model.oracle.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdi.monitoring.model.oracle.entity.SixHoursMonitoringEntity;

public interface SixHoursMonitoringMongoRepo extends MongoRepository<SixHoursMonitoringEntity, String>{

}
