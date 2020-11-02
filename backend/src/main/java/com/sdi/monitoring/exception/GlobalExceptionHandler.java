package com.sdi.monitoring.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sdi.monitoring.domain.ExceptionResponse;
import com.sdi.monitoring.util.HttpsConnection;

@RestControllerAdvice
public class GlobalExceptionHandler {
	

	@Autowired
	HttpsConnection httpConnection;

	@ExceptionHandler(UnAuthorizationException.class)
	protected ResponseEntity UnAuthorizationException(UnAuthorizationException e) {
		ResponseEntity response = null;
		
		final ExceptionResponse result = new ExceptionResponse();
		
		result.code = ErrorCode.Unauthorized_Account.getCode();
		result.msg = ErrorCode.Unauthorized_Account.getMessage() + " : " + e.getMessage();
		result.status = ErrorCode.Unauthorized_Account.getStatus();
		response = new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
//		httpConnection.postRequest(e, ErrorCode.Unauthorized_Account.getMessage(), e.getMessage());
		
		return response;
	}
	
	@ExceptionHandler(InternalServerException.class)
	protected ResponseEntity InternalServerException(InternalServerException e) {
		ResponseEntity response = null;
		
		final ExceptionResponse result = new ExceptionResponse();
		
		result.code = ErrorCode.InternalServer.getCode();
		result.msg = ErrorCode.InternalServer.getMessage() + " : " + e.getMessage();
		result.status = ErrorCode.InternalServer.getStatus();
		response = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
//		httpConnection.postRequest(e, ErrorCode.InternalServer.getMessage(), e.getMessage());
		
		return response;
	}
	
	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity BadRequestException(BadRequestException e) {
		ResponseEntity response = null;
		
		final ExceptionResponse result = new ExceptionResponse();
		
		result.code = ErrorCode.BadRequest.getCode();
		result.msg = ErrorCode.BadRequest.getMessage() + " : " + e.getMessage();
		result.status = ErrorCode.BadRequest.getStatus();
		response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
//		httpConnection.postRequest(e, ErrorCode.BadRequest.getMessage(), e.getMessage());
		
		return response;
	}
}
