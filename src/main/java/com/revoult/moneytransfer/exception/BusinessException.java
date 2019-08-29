package com.revoult.moneytransfer.exception;

import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.revoult.moneytransfer.constant.ErrorCodes;
import com.revoult.moneytransfer.dto.Error;
import com.revoult.moneytransfer.util.Util;

public class BusinessException extends ServiceException {


	public BusinessException(String errorCode) {
		super(errorCode);
	}

	public BusinessException(String errorCode, String message) {
		super(errorCode, message);
	}

	public BusinessException(String errorCode, String message, Throwable throwable) {
		super(message,message, throwable);

	}
	
	public Status getStatus() {
		return Status.CONFLICT;
	}


}
