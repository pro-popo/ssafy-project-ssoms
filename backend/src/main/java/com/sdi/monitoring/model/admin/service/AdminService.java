package com.sdi.monitoring.model.admin.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sdi.monitoring.model.admin.dto.DeleteForm;
import com.sdi.monitoring.model.oracle.dto.OracleDBSettingsDTO;
import com.sdi.monitoring.model.user.dto.UserDTO;
import com.sdi.monitoring.model.user.dto.UserPrimitiveDTO;

public interface AdminService {
	public boolean changeAdmin(String prevAdmin, String nextAdmin);

	public List<UserDTO> getAllUserList();

	public List<String> getUserVisitedHistory(String email);
	
	public JSONObject getSettings();

	public boolean setSettingsOracleDB(OracleDBSettingsDTO OracleData);

	public boolean setSettingsSchema(JSONArray jlist);

	public boolean checkConnection(OracleDBSettingsDTO OracleData);
	
	public boolean checkDuplicateSchema(String schemaName);
	
	public boolean checkSchemaExistence(String schemaName);
	
	public List<String> findAllSchema();

	public int deleteUser(DeleteForm form);
}
