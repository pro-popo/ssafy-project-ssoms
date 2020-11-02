package com.sdi.monitoring.model.user.service;

import java.util.List;

import com.sdi.monitoring.model.user.dto.UserDTO;
import com.sdi.monitoring.model.user.dto.UserPrimitiveDTO;
import com.sdi.monitoring.model.user.dto.UserSignUpDTO;
import com.sdi.monitoring.model.user.dto.UserUpdateDTO;
import com.sdi.monitoring.model.user.entity.UserEntity;

public interface UserService {

	public boolean isAdminCheck(String email);

	public boolean changeAdmin(String prevAdmin, String nextAdmin);

	public List<UserDTO> getAllUserList();

	public List<String> getUserVisitedHistory(String email);

	public UserDTO getUserProfile(String email);

	public boolean updateUser(UserUpdateDTO userUpdateDTO);

	public boolean deleteUser(UserPrimitiveDTO userPrimitiveDTO);

}
