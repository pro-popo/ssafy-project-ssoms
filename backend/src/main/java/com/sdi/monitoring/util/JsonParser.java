package com.sdi.monitoring.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sdi.monitoring.model.oracle.dto.OracleHostInfo;

@Component
public class JsonParser {
	private static String SetupPath;

	@Value("${SetupPath}")
	public void setSetupPath(String path) {
		SetupPath = path;
	}

	public static OracleHostInfo getHostInfo() {
		try {
			JSONParser parser = new JSONParser();
			FileReader readfile = new FileReader(SetupPath + "/settings.json");
			JSONObject obj;
			obj = (JSONObject) parser.parse(readfile);
			OracleHostInfo oracleHostInfo = null;
			JSONObject jsonObject = (JSONObject) obj.get("oracleDB");
			String url = (String) jsonObject.get("oracleURL");
			String sid = (String) jsonObject.get("oracleSID");
			String id = (String) jsonObject.get("oracleID");
			String pw = (String) jsonObject.get("oraclePassword");

			oracleHostInfo = new OracleHostInfo(url, sid, id, pw);
			return oracleHostInfo;
		} catch (JSONException | IOException | ParseException e) {
			e.printStackTrace();
//			throw new InternalServerException("Cannot parse oracle json file");
		}
		return null;
	}

	public static List<String> getSchemaInfo() {
		try {
			List<String> schemaList = new LinkedList<>();
			JSONParser parser = new JSONParser();
			FileReader readfile = new FileReader(SetupPath + "/settings.json");
			JSONObject obj;
			obj = (JSONObject) parser.parse(readfile);
			OracleHostInfo oracleHostInfo = null;
			JSONArray jsonArray = (JSONArray) obj.get("schema");
			for(int i = 0 ; i < jsonArray.size() ; i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				schemaList.add((String)jsonObject.get("userID"));
			}
			return schemaList;
		} catch (JSONException | IOException | ParseException e) {
			e.printStackTrace();
//			throw new InternalServerException("Cannot parse oracle json file");
		}
		return null;
	}
}
