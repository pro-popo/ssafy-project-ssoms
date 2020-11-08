package com.sdi.monitoring.controller.admin;

import static org.hamcrest.CoreMatchers.is;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdi.monitoring.domain.SuccessResponse;
import com.sdi.monitoring.model.admin.service.AdminService;
import com.sdi.monitoring.model.oracle.dto.OracleDBSettingsDTO;
import com.sdi.monitoring.model.oracle.service.OracleSchedulingService;
import com.sdi.monitoring.model.user.dto.UserDTO;
import com.sdi.monitoring.model.user.dto.UserUpdateAdminDTO;

@RequestMapping("/admin")
@RestController
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private OracleSchedulingService oracleScedulingService;
	
	// 이거 권한 맞는지 확인하는 로직 필요함
	@PutMapping("/change")
	public ResponseEntity changeAdmin(@RequestBody(required = true) UserUpdateAdminDTO userUpdateAdminDTO) {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		boolean checkCanChangeAdmin = adminService.changeAdmin(userUpdateAdminDTO.getPrevAdmin(),
				userUpdateAdminDTO.getNextAdmin());
		result.status = true;
		result.result = checkCanChangeAdmin ? "success" : "fail";
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

	// 리스트 null 반환될때 체크
	@GetMapping("/alluser")
	public ResponseEntity getAllUserList() {
		System.out.println("========== alluser entered... ==========");
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		List<UserDTO> userDTOList = adminService.getAllUserList();
		Map<String, Object> map = new HashMap<>();
		map.put("userList", userDTOList);
		result.status = true;
		result.result = "success";
		result.map = map;
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/settings/oracledb")
	public ResponseEntity getSettingsOracleDB() {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		
		JSONObject settings = adminService.getSettings();
		if(settings == null) {
			result.status = true;
			result.result = "fail";
		}else {
			Map<String, Object> map = new HashMap<>();
			map.put("oracleDB", settings.get("oracleDB"));
			result.status = true;
			result.result = "success";
			result.map = map;
		}
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
	
	@PostMapping("/settings/oracledb/check")
	public ResponseEntity checkConOracleDB(@RequestBody(required = true) OracleDBSettingsDTO OracleData) {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		boolean check = adminService.checkConnection(OracleData);
				
		result.status = true;
		result.result = check ? "success" : "fail";
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
	
	@PostMapping("/settings/oracledb/save")
	public ResponseEntity setSettingsOracleDB(@RequestBody(required = true) OracleDBSettingsDTO OracleData) {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		
		boolean save = adminService.setSettingsOracleDB(OracleData);
	
		result.status = true;
		result.result = save ? "success" : "fail";
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/settings/schema")
	public ResponseEntity getSettingsSchema() {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		
		JSONObject settings = adminService.getSettings();
		if(settings == null) {
			result.status = true;
			result.result = "fail";
		}else {
			Map<String, Object> map = new HashMap<>();
			map.put("schema", settings.get("schema"));
			result.status = true;
			result.result = "success";
			result.map = map;
		}
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
	
	@PostMapping("/settings/schema/save")
	public ResponseEntity setSettingsSchema(@RequestBody String userID) {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		boolean duplicateCheck = adminService.checkDuplicateSchema(userID);
		result.status = true;
		if(duplicateCheck) {
			result.result = "duplicate";
		}else if(!duplicateCheck) {
			boolean isSchemaExistence = adminService.checkSchemaExistence(userID);
			if(isSchemaExistence) {
				JSONParser parser = new JSONParser();
				JSONArray jlist = null;
				try {
					jlist = (JSONArray)parser.parse(userID);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				boolean save = adminService.setSettingsSchema(jlist);
				result.status = true;
				result.result = save ? "saveSuccess" : "notExist";
			}else if(!isSchemaExistence) {
				result.result = "saveFail";
			}
		}
		
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/realtime/start")
	public ResponseEntity startRealTime() {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		boolean ret = oracleScedulingService.start();
		result.status = true;
		result.result = ret ? "success" : "fail";
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/realtime/stop")
	public ResponseEntity stopRealTime() {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		boolean ret = oracleScedulingService.stop();
		result.status = true;
		result.result = ret ? "success" : "fail";
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/realtime/status")
	public ResponseEntity statusRealTime() {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		boolean ret = oracleScedulingService.hasScheduler();
		result.status = true;
		result.result = ret ? "running" : "end";
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
	
}
