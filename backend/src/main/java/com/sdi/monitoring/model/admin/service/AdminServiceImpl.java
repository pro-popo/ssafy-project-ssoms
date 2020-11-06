package com.sdi.monitoring.model.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdi.monitoring.model.admin.repository.AdminSettingsRepo;
import com.sdi.monitoring.model.oracle.dto.OracleDBSettingsDTO;
import com.sdi.monitoring.model.user.dto.UserDTO;
import com.sdi.monitoring.model.user.entity.UserEntity;
import com.sdi.monitoring.model.user.repository.UserMongoRepo;
import com.sdi.monitoring.util.Mapper;

@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	private Mapper mapper;
	
	@Autowired
	private AdminSettingsRepo settingRepo;
	
	@Autowired
	private UserMongoRepo userMongoRepo;
	
//	@Override
//	public boolean changeAdmin(String prevAdmin, String nextAdmin) {
//		if(userRepo.changeAuthorityToUser(prevAdmin) != 1)
//			return false;
//		
//		if(userRepo.changeAuthorityToAdmin(nextAdmin) != 1)
//			return false; 
//		
//		// false 던지는 곳이 진짜 계정이 없어서 던지는건지 db에 이상 있는건지 확인하는 로직 추가 필요
//		return true;
//	}
	
	@Override
	public List<UserDTO> getAllUserList() {
		return EntityListToDTOList(userMongoRepo.findAll()); // 아무것도 없을 때 test
	}

	@Override
	public List<String> getUserVisitedHistory(String email) {
		return null;
	}
	
	@Override
	public JSONObject getSettings() {
		JSONObject jo = null;
		try {
			jo = settingRepo.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	@Override
	public boolean setSettingsOracleDB(OracleDBSettingsDTO OracleData) {
		boolean result = false;
		try {
			result = settingRepo.setOracleDB(OracleData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean setSettingsSchema(JSONArray jlist) {
		boolean result = false;
		try {
			result = settingRepo.setSchema(jlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean checkConnection(OracleDBSettingsDTO OracleData) {
		boolean result = false;
		try {
			result = settingRepo.checkConnection(OracleData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private List<UserDTO> EntityListToDTOList(List<UserEntity> userEntityList){
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		for (UserEntity userEntity : userEntityList) {
			UserDTO userDTO = mapper.convertToDTO(userEntity, UserDTO.class);
			userDTOList.add(userDTO);
		}
		
		return userDTOList; 
	}

}
