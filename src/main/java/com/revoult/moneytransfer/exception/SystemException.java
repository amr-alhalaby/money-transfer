package com.revoult.moneytransfer.exception;

import javax.ws.rs.core.Response.Status;

public class SystemException extends ServiceException {


	public SystemException(String errorCode) {
		super(errorCode);
	}

	public SystemException(String errorCode, String message) {
		super(errorCode, message);
	}

	public SystemException(String errorCode, String message, Throwable throwable) {
		super(message,message, throwable);
		
	}
	
	public Status getStatus() {
		return Status.INTERNAL_SERVER_ERROR;
	}

}
