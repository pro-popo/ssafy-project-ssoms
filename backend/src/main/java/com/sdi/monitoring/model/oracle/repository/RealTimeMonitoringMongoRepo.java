package com.sdi.monitoring.model.oracle.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdi.monitoring.model.oracle.entity.RealTimeMonitoringEntity;

public interface RealTimeMonitoringMongoRepo extends MongoRepository<RealTimeMonitoringEntity, String>{

}
