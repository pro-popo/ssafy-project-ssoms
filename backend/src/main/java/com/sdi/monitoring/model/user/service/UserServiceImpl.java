package com.sdi.monitoring.model.user.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdi.monitoring.exception.BadRequestException;
import com.sdi.monitoring.model.user.dto.UserDTO;
import com.sdi.monitoring.model.user.dto.UserPrimitiveDTO;
import com.sdi.monitoring.model.user.dto.UserUpdateDTO;
import com.sdi.monitoring.model.user.entity.UserEntity;
import com.sdi.monitoring.model.user.repository.UserMongoRepo;
import com.sdi.monitoring.util.Mapper;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMongoRepo userMongoRepo;
	
	@Autowired
	private Mapper mapper;
	
	@Override
	public boolean isAdminCheck(String email) {
		UserEntity userEntity = null;
		userEntity = userMongoRepo.findUserByEmail(email);
		
		if(userEntity == null) {
			throw new BadRequestException("isAdminCheck method throw Exception(BadRequestException exception)\n" + "This User is not found : " + email);
		}
		
		return userEntity.getInfo().isAdmin();
	}
	
	@Override
	public UserDTO getUserProfile(String email) {
		UserEntity userEntity = null;
		userEntity = userMongoRepo.findUserByEmail(email);
		if(userEntity == null) {
			throw new BadRequestException("getUserProfile method throw Exception(BadRequestException exception)\n" + "This User is not found : " + email);
		}
		
		return EntityToDTO(userEntity);
	}

	@Override
	public boolean updateUser(UserUpdateDTO userUpdateDTO) {
		UserEntity userEntity = null;
		userEntity = userMongoRepo.findUserByEmail(userUpdateDTO.getEmail());
		
		if(userEntity == null)
			return false;
		
		if(!cmpPasswordWithEncryptionPassword(userUpdateDTO.getPw(), userEntity.getInfo().getPw()))
			return false;
		
		if(userUpdateDTO.getNewPw()!=null) userUpdateDTO.setPw(userUpdateDTO.getNewPw());
		
		userMongoRepo.save(userEntityBuilderToUpdate(userEntity, userUpdateDTO));
		return true;
	}

	@Override
	public boolean deleteUser(UserPrimitiveDTO userPrimitiveDTO) {
		UserEntity userEntity = null;
		userEntity = userMongoRepo.findUserByEmail(userPrimitiveDTO.getEmail());
		
		if(userEntity == null)
			return false;
		
		if(!cmpPasswordWithEncryptionPassword(userPrimitiveDTO.getPw(), userEntity.getInfo().getPw()))
			return false;
		
		userMongoRepo.deleteByEmail(userPrimitiveDTO.getEmail());
		return true;
	}
//	
	
	public UserDTO EntityToDTO(UserEntity userEntity) {
		UserDTO userDTO = mapper.convertToDTO(userEntity.getInfo(), UserDTO.class);
		userDTO.setEmail(userEntity.getEmail());
		return userDTO;
	}
	
	private UserEntity userEntityBuilderToUpdate(UserEntity userEntity, UserUpdateDTO userUpdateDTO) {
		userEntity.getInfo().setPw(encryptionPassword(userUpdateDTO.getPw()));
		userEntity.getInfo().setEmployeeId(userUpdateDTO.getEmployeeId());
		userEntity.getInfo().setPhoneNumber(userUpdateDTO.getPhoneNumber());
		userEntity.getInfo().setName(userUpdateDTO.getName());
		
		return UserEntity.builder()
				.email(userUpdateDTO.getEmail())
				.info(userEntity.getInfo())
				.alarms(userEntity.getAlarms())
				.visit(userEntity.getVisit())
				.build();
	}
	
	private String encryptionPassword(String pw) {
		return BCrypt.hashpw(pw, BCrypt.gensalt());
	}
	
	private boolean cmpPasswordWithEncryptionPassword(String cmp1, String cmp2) {
		return BCrypt.checkpw(cmp1, cmp2);
	}
}
