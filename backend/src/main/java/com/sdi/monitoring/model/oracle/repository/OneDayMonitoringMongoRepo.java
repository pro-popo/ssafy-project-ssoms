package com.sdi.monitoring.model.oracle.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdi.monitoring.model.oracle.entity.OneDayMonitoringEntity;

public interface OneDayMonitoringMongoRepo extends MongoRepository<OneDayMonitoringEntity, String>{

}
