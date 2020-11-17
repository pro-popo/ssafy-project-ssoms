package com.sdi.monitoring.controller.admin;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sdi.monitoring.domain.SuccessResponse;
import com.sdi.monitoring.model.admin.dto.DeleteForm;
import com.sdi.monitoring.model.admin.service.AdminService;
import com.sdi.monitoring.model.oracle.dto.OracleDBSettingsDTO;
import com.sdi.monitoring.model.oracle.service.OracleSchedulingService;
import com.sdi.monitoring.model.user.dto.UserDTO;
import com.sdi.monitoring.model.user.dto.UserPrimitiveDTO;
import com.sdi.monitoring.model.user.dto.UserUpdateAdminDTO;

@CrossOrigin("*")
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
		JSONParser parser = new JSONParser();
		JSONArray jlist = null;
		try {
			jlist = (JSONArray)parser.parse(userID);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		result.status = true;
		boolean save = adminService.setSettingsSchema(jlist);
		result.result = save ? "saveSuccess" : "saveFail";
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
	
	@PostMapping("/settings/schema/check")
	public ResponseEntity checkSettingsSchema(@RequestBody Map<String, String> map) {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		String addSchema = map.get("userID").toUpperCase();
		boolean duplicateCheck = adminService.checkDuplicateSchema(addSchema);
		result.status = true;
		if(duplicateCheck) {
			result.result = "duplicate";
		}else if(!duplicateCheck) {
			boolean isSchemaExistence = adminService.checkSchemaExistence(addSchema);
			result.result = isSchemaExistence ? "success" : "notExist";
		}
		
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/settings/schema/all")
	public ResponseEntity findAllSchemaList() {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		List<String> allSchemaList = adminService.findAllSchema();
		result.status = true;
		if(allSchemaList.isEmpty() || allSchemaList == null) {
			result.result = "empty";
		}else {
			result.result = "success";
			Map<String, Object> map = new HashMap<>();
			map.put("schemaList", allSchemaList);
			result.map = map;
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
	
	@PostMapping("/delete")
	public ResponseEntity deleteUser(@RequestBody DeleteForm form) {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		int isDelete = adminService.deleteUser(form);
		result.status = true;
		if(isDelete == 0) {//사용자 이메일 틀림
			result.result = "wrong_user_email";
		}else if(isDelete == 1) {//관리자 이메일 틀림
			result.result = "wrong_admin_email";
		}else if(isDelete == 2) {//관리자 비번 틀림
			result.result = "wrong_admin_pw";
		}else if(isDelete == 3) {//성공
			result.result = "success";
		}
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
}
