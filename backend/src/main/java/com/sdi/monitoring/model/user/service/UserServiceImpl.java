package com.sdi.monitoring.model.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdi.monitoring.exception.BadRequestException;
import com.sdi.monitoring.exception.UnAuthorizationException;
import com.sdi.monitoring.model.user.dto.UserDTO;
import com.sdi.monitoring.model.user.dto.UserPrimitiveDTO;
import com.sdi.monitoring.model.user.dto.UserUpdateDTO;
import com.sdi.monitoring.model.user.entity.UserEntity;
import com.sdi.monitoring.model.user.repository.UserRepo;
import com.sdi.monitoring.util.Mapper;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private Mapper mapper;
	
//	@Override
//	public boolean isAdminCheck(String email) {
//		Optional<UserEntity> optional = userRepo.findUserByEmail(email);
//		if(!optional.isPresent())
//			throw new BadRequestException("isAdminCheck method throw Exception(BadRequestException exception)\n" + "This User is not found : " + email);
//			
//		return optional.get().isAdmin();
//	}
//
//
//	@Override
//	public UserDTO getUserProfile(String email) {
////		if(!email.equals((String)httpServletRequest.getSession().getAttribute("loginSession")))
////			throw new UnAuthorizationException("getUserProfile method throw Exception(UnAuthorizationException)\n" + "missmatch with session : " + email);
//		
//		Optional<UserEntity> optional = userRepo.findUserByEmail(email);
//		if(!optional.isPresent())
//			throw new BadRequestException("isAdminCheck method throw Exception(BadRequestException exception)\n" + "This User is not found : " + email);
//
//		return EntityToDTO(optional.get());
//	}
//
//	@Override
//	public boolean updateUser(UserUpdateDTO userUpdateDTO) {
//			
//		Optional<UserEntity> optional = userRepo.findUserByEmail(userUpdateDTO.getEmail());
//		if(!optional.isPresent())
//			return false;
//		
//		if(!cmpPasswordWithEncryptionPassword(userUpdateDTO.getPw(), optional.get().getPw()))
//			return false;
//		
//		if(userUpdateDTO.getNewPw()!=null) userUpdateDTO.setPw(userUpdateDTO.getNewPw());
//		
//		userRepo.save(userEntityBuilderToUpdate(userUpdateDTO));
//		return true;
//	}
//
//	@Override
//	public boolean deleteUser(UserPrimitiveDTO userPrimitiveDTO) {
//		Optional<UserEntity> optional = userRepo.findUserByEmail(userPrimitiveDTO.getEmail());
//		if(!optional.isPresent())
//			return false;
//		
//		if(!cmpPasswordWithEncryptionPassword(userPrimitiveDTO.getPw(), optional.get().getPw()))
//			return false;
//		
//		userRepo.deleteById(userPrimitiveDTO.getEmail());
//		return true;
//	}
//	
//	public List<UserDTO> EntityListToDTOList(List<UserEntity> userEntityList){
//		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
//		for (UserEntity userEntity : userEntityList) {
//			userDTOList.add(mapper.convertToDTO(userEntity, UserDTO.class));
//		}
//		
//		return userDTOList; 
//	}
//	
//	public UserDTO EntityToDTO(UserEntity userEntity) {
//		return mapper.convertToDTO(userEntity, UserDTO.class);
//	}
//	
//	public UserEntity userEntityBuilderToUpdate(UserUpdateDTO userUpdateDTO) {
//		return UserEntity.builder()
//				.email(userUpdateDTO.getEmail())
//				.pw(encryptionPassword(userUpdateDTO.getPw()))
//				.employeeId(userUpdateDTO.getEmployeeId())
//				.phoneNumber(userUpdateDTO.getPhoneNumber())
//				.name(userUpdateDTO.getName())
//				.build();
//	}
//	
//	public String encryptionPassword(String pw) {
//		return BCrypt.hashpw(pw, BCrypt.gensalt());
//	}
//	
//	public boolean cmpPasswordWithEncryptionPassword(String cmp1, String cmp2) {
//		return BCrypt.checkpw(cmp1, cmp2);
//	}
}
