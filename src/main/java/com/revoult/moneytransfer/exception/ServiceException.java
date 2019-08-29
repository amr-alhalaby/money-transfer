package com.revoult.moneytransfer.exception;

import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.revoult.moneytransfer.constant.ErrorCodes;
import com.revoult.moneytransfer.util.Util;

public abstract class ServiceException extends RuntimeException {

	static final Logger logger = Logger.getLogger(ServiceException.class);

	private final String reason;
	private final Status status;
	private final String errorCode;

	public ServiceException(String errorCode) {
		this(errorCode, null, null);
	}

	public ServiceException(String errorCode, String message) {
		this(errorCode, message, null);
	}

	public ServiceException(String errorCode, String message, Throwable throwable) {
		super(message, throwable);
		
		String customMsg = Util.getErrorMessage(errorCode);
		if (customMsg == null) {
			logger.error("null message for error code: " + errorCode);
			this.errorCode = ErrorCodes.GENERAL_ERROR;
			reason = Util.getErrorMessage(ErrorCodes.GENERAL_ERROR);
			status = Status.INTERNAL_SERVER_ERROR;
		} else {
			reason=customMsg;
			this.errorCode=errorCode;
			this.status=getStatus();

		}
	}



	public String getReason() {
		return reason;
	}

	public Status getStatus() {
		return status;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
