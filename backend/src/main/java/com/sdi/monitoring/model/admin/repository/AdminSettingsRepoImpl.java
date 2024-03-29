package com.sdi.monitoring.model.admin.repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.sdi.monitoring.model.oracle.dto.OracleDBSettingsDTO;
import com.sdi.monitoring.util.AES256Cipher;
import com.sdi.monitoring.util.DBUtil;

@Repository
public class AdminSettingsRepoImpl implements AdminSettingsRepo{
	@Value("${SetupPath}")
	String SetupPath;
	
	@Override
	public JSONObject getAll() throws Exception {
		JSONParser parser = new JSONParser();
		FileReader file = new FileReader(SetupPath+"/settings.json");
		JSONObject obj = (JSONObject)parser.parse(file);
		file.close();
		return obj;
	}

	@Override
	public boolean setOracleDB(OracleDBSettingsDTO OracleData) throws Exception {
		JSONParser parser = new JSONParser();
		FileReader readfile = new FileReader(SetupPath+"/settings.json");
		
		JSONObject obj = (JSONObject)parser.parse(readfile);
		obj.remove("oracleDB");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("oracleURL", OracleData.getOracleURL());
		jsonObject.put("oracleID", OracleData.getOracleID());
		jsonObject.put("oraclePassword", AES256Cipher.AESEncode(OracleData.getOraclePassword()));
		jsonObject.put("oracleSID", OracleData.getOracleSID());
		obj.put("oracleDB",jsonObject);
		FileWriter file = new FileWriter(SetupPath+"/settings.json");
		file.write(obj.toJSONString());
		file.flush();
		file.close();
		readfile.close();
		return true;
	}

	@Override
	public boolean setSchema(JSONArray jlist) throws Exception{
		if(jlist.size() == 0)
			return false;
		
		JSONParser parser = new JSONParser();
		FileReader readfile = new FileReader(SetupPath+"/settings.json");
		JSONObject obj = (JSONObject)parser.parse(readfile);
		obj.remove("schema");
		obj.put("schema", jlist);
		FileWriter file = new FileWriter(SetupPath+"/settings.json");
		file.write(obj.toJSONString());
		file.flush();
		file.close();
		readfile.close();
		return true;
	}

	@Override
	public boolean checkConnection(OracleDBSettingsDTO OracleData) throws Exception {
		Connection con = DBUtil.checkConnection(OracleData);
		if(con == null) {
			return false;
		}
		con.close();
		return true;
	}
	
}
