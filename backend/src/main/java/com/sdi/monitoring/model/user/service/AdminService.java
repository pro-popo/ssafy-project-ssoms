package com.sdi.monitoring.model.user.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sdi.monitoring.model.oracle.dto.OracleDBSettingsDTO;

public interface AdminService {
	public boolean changeAdmin(String prevAdmin, String nextAdmin);

	public JSONObject getSettings();

	public boolean setSettingsOracleDB(OracleDBSettingsDTO OracleData);

	public boolean setSettingsSchema(JSONArray jlist);

	public boolean checkConnection(OracleDBSettingsDTO OracleData);
}
