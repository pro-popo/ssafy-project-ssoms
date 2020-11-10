package com.sdi.monitoring.controller.oracle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdi.monitoring.domain.SuccessResponse;
import com.sdi.monitoring.model.oracle.dto.RealTimeMonitoringDTO;
import com.sdi.monitoring.model.oracle.dto.TimeAndCpuDTO;
import com.sdi.monitoring.model.oracle.service.OracleDataService;

@RequestMapping("/data")
@RestController
public class OracleDataController {

	@Autowired
	private OracleDataService oracleDataService;

	@GetMapping("/realtime")
	public ResponseEntity realTimeData() {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		List<RealTimeMonitoringDTO> realTimeMonitoringDTOList = oracleDataService.findDataForRealTime();
		if(realTimeMonitoringDTOList.isEmpty() || realTimeMonitoringDTOList == null) {
			result.result = "empty";
		}else {
			Map<String, Object> map = new HashMap<>();
			map.put("realTimeMonitoringList", realTimeMonitoringDTOList);
			result.result = "success";
			result.map = map;
		}
		result.status = true;
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

	@GetMapping("/past/{startDate}/{endDate}")
	public ResponseEntity pastData(@PathVariable(value = "startDate") String startDate
									,@PathVariable(value = "endDate") String endDate) {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		List<TimeAndCpuDTO> timeAndCpuDTOList = oracleDataService.findTimeAndCpuDTO(startDate, endDate);
		if(timeAndCpuDTOList.isEmpty()) {
			result.result = "empty";
		}else {
			Map<String, Object> map = new HashMap<>();
			map.put("timeAndCpuList", timeAndCpuDTOList);
			result.result = "success";
			result.map = map;
		}
		result.status = true;
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

	@GetMapping("/past/{date}")
	public ResponseEntity pastFullData(@PathVariable(value = "date") String date) {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		// 12개 가져오는 로직
		RealTimeMonitoringDTO realTimeMonitoringDTO = oracleDataService.findDataByTime(date);
		if(realTimeMonitoringDTO == null) {
			result.result = "notExist";
		}else {
			Map<String, Object> map = new HashMap<>();
			map.put("realTimeMonitoring", realTimeMonitoringDTO);
			result.result = "success";
			result.map = map;
		}
		result.status = true;
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
}
