package com.sdi.monitoring.exception;

public class InternalServerException extends RuntimeException {
	public InternalServerException(String msg) {
		super(msg);
	}
}
