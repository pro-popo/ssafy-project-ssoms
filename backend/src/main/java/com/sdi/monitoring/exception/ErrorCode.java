package com.sdi.monitoring.exception;

public enum ErrorCode {
	// Common
	InternalServer(500, "U001", "Internal Server Exception"),
    Unauthorized_Account(401, "A001", "Unauthorized"),
    BadRequest(400, "A002", "BadRequest");
	
    private final String code;
    private final String message;
    private final int status;

    private ErrorCode(int status, String code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}
}
