package com.sdi.monitoring.model.user.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdi.monitoring.model.user.dto.UserPrimitiveDTO;
import com.sdi.monitoring.model.user.dto.UserSignUpDTO;
import com.sdi.monitoring.model.user.entity.SetAlarmsEntity;
import com.sdi.monitoring.model.user.entity.UserEntity;
import com.sdi.monitoring.model.user.entity.UserInfoEntity;
import com.sdi.monitoring.model.user.entity.UserVisitTimesEntity;
import com.sdi.monitoring.model.user.repository.UserMongoRepo;

@Service
public class NonUserServiceImpl implements NonUserService{
	
	@Autowired
	private UserMongoRepo userMongoRepo;
	
	@Override
	public boolean hasAdmin() {
		if(userMongoRepo.findAdmin() != null)
			return true;
		
		return false;
	}
	
	@Override
	public boolean emailDuplicateCheckForSignUp(String email) {
		UserEntity userEntity = null;
		userEntity = userMongoRepo.findUserByEmail(email);
		if(userEntity != null) {
			return false;
		}
		
		return true;
	}

	@Override
	public void signUp(UserSignUpDTO userSignUpDTO) {
		userMongoRepo.save(userEntityBuilder(userSignUpDTO));
	}

	@Override
	public boolean login(UserPrimitiveDTO userPrimitiveDTO) {
		UserEntity userEntity = null;
		userEntity = userMongoRepo.findUserByEmail(userPrimitiveDTO.getEmail());
		
		if(userEntity == null)
			return false;
		System.out.println(userEntity.toString());
		return cmpPasswordWithEncryptionPassword(userPrimitiveDTO.getPw(), userEntity.getInfo().getPw());
	}
//	
	private UserEntity userEntityBuilder(UserSignUpDTO userSignUpDTO) {
		return UserEntity.builder()
				.email(userSignUpDTO.getEmail())
				.info(userInfoEntityBuilder(userSignUpDTO))
				.visit(userVisitTimesEntityBuilder())
				.alarms(setAlarmsEntityBuilder())
				.build();
	}
	
	private UserInfoEntity userInfoEntityBuilder(UserSignUpDTO userSignUpDTO) {
		return UserInfoEntity.builder()
				.pw(encryptionPassword(userSignUpDTO.getPw()))
				.employeeId(userSignUpDTO.getEmployeeId())
				.admin(userSignUpDTO.isAdmin())
				.isReportActivate(false)
				.reportTime(-1)
				.phoneNumber(userSignUpDTO.getPhoneNumber())
				.name(userSignUpDTO.getName())
				.build();
	}
	
	private UserVisitTimesEntity userVisitTimesEntityBuilder() {
		return UserVisitTimesEntity.builder()
				.time(null)
				.build();
	}
	
	private SetAlarmsEntity setAlarmsEntityBuilder() {
		return SetAlarmsEntity.builder()
				.cpu70percent(false)
				.cpu80percent(false)
				.cpu90percent(false)
				.memory70percent(false)
				.memory70percent(false)
				.memory70percent(false)
				.build();
	}
	
	private String encryptionPassword(String pw) {
		return BCrypt.hashpw(pw, BCrypt.gensalt());
	}
	
	private boolean cmpPasswordWithEncryptionPassword(String cmp1, String cmp2) {
		return BCrypt.checkpw(cmp1, cmp2);
	}
}
