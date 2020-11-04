package com.sdi.monitoring.model.user.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdi.monitoring.model.user.dto.UserPrimitiveDTO;
import com.sdi.monitoring.model.user.dto.UserSignUpDTO;
import com.sdi.monitoring.model.user.entity.SetAlarms;
import com.sdi.monitoring.model.user.entity.UserEntity;
import com.sdi.monitoring.model.user.entity.UserInfo;
import com.sdi.monitoring.model.user.entity.UserVisitTimes;
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
	public UserEntity userEntityBuilder(UserSignUpDTO userSignUpDTO) {
		return UserEntity.builder()
				.email(userSignUpDTO.getEmail())
				.info(userInfoBuilder(userSignUpDTO))
				.visit(userVisitTimesBuilder())
				.alarms(setAlarmsBuilder())
				.build();
	}
	
	public UserInfo userInfoBuilder(UserSignUpDTO userSignUpDTO) {
		return UserInfo.builder()
				.pw(encryptionPassword(userSignUpDTO.getPw()))
				.employeeId(userSignUpDTO.getEmployeeId())
				.admin(userSignUpDTO.isAdmin())
				.isReportActivate(false)
				.reportTime(-1)
				.phoneNumber(userSignUpDTO.getPhoneNumber())
				.name(userSignUpDTO.getName())
				.build();
	}
	
	public UserVisitTimes userVisitTimesBuilder() {
		return UserVisitTimes.builder()
				.time(null)
				.build();
	}
	
	public SetAlarms setAlarmsBuilder() {
		return SetAlarms.builder()
				.cpu70percent(false)
				.cpu80percent(false)
				.cpu90percent(false)
				.memory70percent(false)
				.memory70percent(false)
				.memory70percent(false)
				.build();
	}
	
	public String encryptionPassword(String pw) {
		return BCrypt.hashpw(pw, BCrypt.gensalt());
	}
	
	public boolean cmpPasswordWithEncryptionPassword(String cmp1, String cmp2) {
		return BCrypt.checkpw(cmp1, cmp2);
	}
}
