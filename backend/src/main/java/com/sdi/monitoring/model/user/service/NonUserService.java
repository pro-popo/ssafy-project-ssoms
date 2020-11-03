package com.sdi.monitoring.model.user.service;

import com.sdi.monitoring.model.user.dto.UserPrimitiveDTO;
import com.sdi.monitoring.model.user.dto.UserSignUpDTO;

public interface NonUserService {
	public boolean hasAdmin();
	
	public boolean emailDuplicateCheckForSignUp(String email);

	public void signUp(UserSignUpDTO userSignUpDTO);

//	public boolean login(UserPrimitiveDTO userPrimitiveDTO);
}
