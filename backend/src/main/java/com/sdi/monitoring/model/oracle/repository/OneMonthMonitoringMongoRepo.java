package com.sdi.monitoring.model.oracle.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdi.monitoring.model.oracle.entity.OneMonthMonitoringEntity;

public interface OneMonthMonitoringMongoRepo extends MongoRepository<OneMonthMonitoringEntity, String>{

}
