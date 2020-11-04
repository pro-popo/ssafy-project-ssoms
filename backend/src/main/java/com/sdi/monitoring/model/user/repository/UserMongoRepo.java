package com.sdi.monitoring.model.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sdi.monitoring.model.user.dto.UserSignUpDTO;
import com.sdi.monitoring.model.user.entity.UserEntity;

public interface UserMongoRepo extends MongoRepository<UserEntity, String>{
//	@Query(value = "{'_id': ?0}", fields = "{note: {'content' : false}}")
//	NoteEntity findNoteDetailList(int groupNo);
//
//	@Query(value = "{'_id': ?0}", fields = "{note : {$elemMatch : {_id : ?1}}}")
//	NoteEntity findNoteDetailContent(int groupNo, int noteNo);
	
	UserEntity findUserByEmail(String email);
	
	void deleteByEmail(String email);
	
	@Query(value = "{'info.admin' : {$eq : true}}")
	UserEntity findAdmin();
	
}
