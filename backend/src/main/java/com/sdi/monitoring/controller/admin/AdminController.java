//package com.sdi.monitoring.controller.admin;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.google.gson.Gson;
//import com.sdi.monitoring.domain.SuccessResponse;
//import com.sdi.monitoring.model.admin.service.AdminService;
//import com.sdi.monitoring.model.oracle.dto.OracleDBSettingsDTO;
//import com.sdi.monitoring.model.user.dto.UserDTO;
//import com.sdi.monitoring.model.user.dto.UserUpdateAdminDTO;
//import com.sdi.monitoring.model.user.service.UserService;
//
//@RequestMapping("/admin")
//@RestController
//public class AdminController {
//	
//	@Autowired
//	private AdminService adminService;
//	
//	// 이거 권한 맞는지 확인하는 로직 필요함
//	@PutMapping("/change")
//	public ResponseEntity changeAdmin(@RequestBody(required = true) UserUpdateAdminDTO userUpdateAdminDTO) {
//		ResponseEntity response = null;
//		final SuccessResponse result = new SuccessResponse();
//		boolean checkCanChangeAdmin = adminService.changeAdmin(userUpdateAdminDTO.getPrevAdmin(),
//				userUpdateAdminDTO.getNextAdmin());
//		result.status = true;
//		result.result = checkCanChangeAdmin ? "success" : "fail";
//		response = new ResponseEntity<>(result, HttpStatus.OK);
//		return response;
//	}
//
//	
//	
//	@GetMapping("/settings/OracleDB")
//	public ResponseEntity getSettingsOracleDB() {
//		ResponseEntity response = null;
//		final SuccessResponse result = new SuccessResponse();
//		
//		JSONObject settings = adminService.getSettings();
//		if(settings == null) {
//			result.status = true;
//			result.result = "fail";
//		}else {
//			Map<String, Object> map = new HashMap<>();
//			map.put("oracleDB", settings.get("oracleDB"));
//			result.status = true;
//			result.result = "success";
//			result.map = map;
//		}
//		response = new ResponseEntity<>(result, HttpStatus.OK);
//		return response;
//	}
//	
//	@PostMapping("/settings/OracleDB/check")
//	public ResponseEntity checkConOracleDB(@RequestBody(required = true) OracleDBSettingsDTO OracleData) {
//		ResponseEntity response = null;
//		final SuccessResponse result = new SuccessResponse();
//		boolean check = adminService.checkConnection(OracleData);
//				
//		result.status = true;
//		result.result = check ? "success" : "fail";
//		response = new ResponseEntity<>(result, HttpStatus.OK);
//		return response;
//	}
//	
//	@PostMapping("/settings/OracleDB/save")
//	public ResponseEntity setSettingsOracleDB(@RequestBody(required = true) OracleDBSettingsDTO OracleData) {
//		ResponseEntity response = null;
//		final SuccessResponse result = new SuccessResponse();
//		
//		boolean save = adminService.setSettingsOracleDB(OracleData);
//	
//		result.status = true;
//		result.result = save ? "success" : "fail";
//		response = new ResponseEntity<>(result, HttpStatus.OK);
//		return response;
//	}
//	
//	@GetMapping("/settings/Schema")
//	public ResponseEntity getSettingsSchema() {
//		ResponseEntity response = null;
//		final SuccessResponse result = new SuccessResponse();
//		
//		JSONObject settings = adminService.getSettings();
//		if(settings == null) {
//			result.status = true;
//			result.result = "fail";
//		}else {
//			Map<String, Object> map = new HashMap<>();
//			map.put("schema", settings.get("schema"));
//			result.status = true;
//			result.result = "success";
//			result.map = map;
//		}
//		response = new ResponseEntity<>(result, HttpStatus.OK);
//		return response;
//	}
//	
//	@PostMapping("/settings/Schema/save")
//	public ResponseEntity setSettingsSchema(@RequestBody String userID) {
//		ResponseEntity response = null;
//		final SuccessResponse result = new SuccessResponse();
//		JSONParser parser = new JSONParser();
//		JSONArray jlist = null;
//		try {
//			jlist = (JSONArray)parser.parse(userID);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		boolean save = adminService.setSettingsSchema(jlist);
//		result.status = true;
//		result.result = save ? "success" : "fail";
//		response = new ResponseEntity<>(result, HttpStatus.OK);
//		return response;
//	}
//}
