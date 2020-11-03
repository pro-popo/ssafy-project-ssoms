package com.sdi.monitoring.model.admin.repository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sdi.monitoring.model.oracle.dto.OracleDBSettingsDTO;

public interface AdminSettingsRepo {
	public JSONObject getAll() throws Exception;
	public boolean setOracleDB(OracleDBSettingsDTO OracleData) throws Exception;
	public boolean setSchema(JSONArray jlist) throws Exception;
	public boolean checkConnection(OracleDBSettingsDTO OracleData) throws Exception;
}
