package com.sdi.monitoring.model.user.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdi.monitoring.model.oracle.dto.OracleDBSettingsDTO;
import com.sdi.monitoring.model.user.repository.AdminSettingsRepo;

@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	private AdminSettingsRepo settingRepo;
	
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

}
