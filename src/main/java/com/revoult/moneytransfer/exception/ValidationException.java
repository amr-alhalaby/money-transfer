package com.revoult.moneytransfer.exception;

import javax.ws.rs.core.Response.Status;

public class ValidationException extends ServiceException {


	public ValidationException(String errorCode) {
		super(errorCode);
	}

	public ValidationException(String errorCode, String message) {
		super(errorCode, message);
	}

	public ValidationException(String errorCode, String message, Throwable throwable) {
		super(message,message, throwable);

	}

	public Status getStatus() {
		return Status.BAD_REQUEST;
	}

}
