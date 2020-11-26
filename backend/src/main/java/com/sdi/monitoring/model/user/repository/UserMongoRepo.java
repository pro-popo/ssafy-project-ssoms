package com.sdi.monitoring.model.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sdi.monitoring.model.user.entity.UserEntity;

public interface UserMongoRepo extends MongoRepository<UserEntity, String>{
	UserEntity findUserByEmail(String email);
	
	void deleteByEmail(String email);
	
	@Query(value = "{'info.admin' : {$eq : true}}")
	UserEntity findAdmin();
	
}
