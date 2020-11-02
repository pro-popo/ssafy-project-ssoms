package com.sdi.monitoring.model.user.service;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdi.monitoring.model.user.dto.UserPrimitiveDTO;
import com.sdi.monitoring.model.user.dto.UserSignUpDTO;
import com.sdi.monitoring.model.user.entity.UserEntity;
import com.sdi.monitoring.model.user.repository.UserRepo;

@Service
public class NonUserServiceImpl implements NonUserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public boolean hasAdmin() {
		if(userRepo.hasAdminCheck() > 0)
			return true;
		
		return false;
	}
	
	@Override
	public boolean emailDuplicateCheckForSignUp(String email) {
		Optional<UserEntity> optional= userRepo.findUserByEmail(email);
		if(optional.isPresent()) {
			return false;
		}
		
		return true;
	}

	@Override
	public void signUp(UserSignUpDTO userSignUpDTO) {
		userRepo.save(userEntityBuilderToSignUp(userSignUpDTO));
	}

	@Override
	public boolean login(UserPrimitiveDTO userPrimitiveDTO) {
		Optional<UserEntity> optional = userRepo.findUserByEmail(userPrimitiveDTO.getEmail());
		if(!optional.isPresent())
			return false;
		
		return cmpPasswordWithEncryptionPassword(userPrimitiveDTO.getPw(), optional.get().getPw());
	}
	
	public UserEntity userEntityBuilderToSignUp(UserSignUpDTO userSignUpDTO) {
		System.out.println(userSignUpDTO.isAdmin());
		return UserEntity.builder()
				.email(userSignUpDTO.getEmail())
				.pw(encryptionPassword(userSignUpDTO.getPw()))
				.employeeId(userSignUpDTO.getEmployeeId())
				.admin(userSignUpDTO.isAdmin())
				.isReportActivate(false)
				.reportTime(-1)
				.phoneNumber(userSignUpDTO.getPhoneNumber())
				.name(userSignUpDTO.getName())
				.build();
	}
	
	public String encryptionPassword(String pw) {
		return BCrypt.hashpw(pw, BCrypt.gensalt());
	}
	
	public boolean cmpPasswordWithEncryptionPassword(String cmp1, String cmp2) {
		return BCrypt.checkpw(cmp1, cmp2);
	}
}
