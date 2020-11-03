package com.sdi.monitoring.model.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.sdi.monitoring.model.user.entity.SetAlarms;
import com.sdi.monitoring.model.user.entity.UserEntity;
import com.sdi.monitoring.model.user.entity.UserVisitTimes;

@Repository
public class UserRepoImpl implements UserRepo{
	@Autowired
    private MongoTemplate mongoTemplate;
	
	@Override
	public void insertUser(UserEntity userEntity) {
    	mongoTemplate.insert(userEntity);
	}
	
	
}
