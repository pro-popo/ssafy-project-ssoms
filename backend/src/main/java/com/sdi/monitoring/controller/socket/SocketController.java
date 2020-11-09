package com.sdi.monitoring.controller.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdi.monitoring.domain.SuccessResponse;
import com.sdi.monitoring.model.oracle.service.OracleSchedulingService;

@CrossOrigin("*")
@RequestMapping("/socket")
@RestController
public class SocketController {
	
	@Autowired
	private OracleSchedulingService oss;
	
	@GetMapping("/test")
	public ResponseEntity test() {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();

		oss.start();
		result.status = true;
		result.result = "success";
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
}
