package com.sdi.monitoring.controller.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdi.monitoring.domain.SuccessResponse;
import com.sdi.monitoring.model.oracle.dto.RealTimeMonitoringDTO;
import com.sdi.monitoring.model.oracle.service.OracleDataService;

@RequestMapping("/data")
@RestController
public class OracleDataController {
	
	@Autowired
	private OracleDataService oracleDataService;
	
	@GetMapping("/realtime")
	public ResponseEntity sample() {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		// 12개 가져오는 로직
		List<RealTimeMonitoringDTO> realTimeMonitoringDTOList = oracleDataService.findDataForRearTime();
		Map<String, Object> map = new HashMap<>();
		map.put("realTimeMonitoringList", realTimeMonitoringDTOList);
		result.status = true;
		result.result = "success";
		result.map = map;
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
}
