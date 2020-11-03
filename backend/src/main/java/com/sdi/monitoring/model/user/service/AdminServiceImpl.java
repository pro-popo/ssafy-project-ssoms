package com.sdi.monitoring.model.user.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdi.monitoring.model.oracle.dto.OracleDBSettingsDTO;
import com.sdi.monitoring.model.user.repository.AdminSettingsRepo;
import com.sdi.monitoring.model.user.repository.UserRepo;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminSettingsRepo settingRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public boolean changeAdmin(String prevAdmin, String nextAdmin) {
		// prevAdmin이 Admin 인지 확인
//		if(!isAdminCheck(prevAdmin))
//			throw new UnAuthorizationException("changeAdmin method throw Exception(UnAuthorization exception)" + "This User is not Admin : " + prevAdmin);
		
		if(userRepo.changeAuthorityToUser(prevAdmin) != 1)
			return false;
		
		if(userRepo.changeAuthorityToAdmin(nextAdmin) != 1)
			return false; 
		
		// false 던지는 곳이 진짜 계정이 없어서 던지는건지 db에 이상 있는건지 확인하는 로직 추가 필요
		return true;
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

}
