package com.sdi.monitoring.exception;

public class BadRequestException extends RuntimeException {
	public BadRequestException(String msg) {
		super(msg);
	}
}
