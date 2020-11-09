package com.sdi.monitoring.model.oracle.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdi.monitoring.model.oracle.entity.SevenDaysMonitoringEntity;

public interface SevenDaysMonitoringMongoRepo extends MongoRepository<SevenDaysMonitoringEntity, String>{

}
