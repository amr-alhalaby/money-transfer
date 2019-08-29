package com.revoult.moneytransfer.exception.mapper;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.revoult.moneytransfer.constant.ErrorCodes;
import com.revoult.moneytransfer.dto.Error;
import com.revoult.moneytransfer.util.Util;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Provider
public class WebExceptionMapper implements ExceptionMapper<WebApplicationException> {
	static final Logger logger = Logger.getLogger(WebExceptionMapper.class);

	public Response toResponse(WebApplicationException e) {	
		logger.error("Error: ",e);		

		return Response.status(e.getResponse().getStatus())
				.entity(new Error(ErrorCodes.GENERAL_ERROR, Util.getErrorMessage(ErrorCodes.GENERAL_ERROR),e.getMessage()))
				.type(MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	
}
