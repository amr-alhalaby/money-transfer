package com.revoult.moneytransfer.exception.mapper;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.revoult.moneytransfer.dto.Error;
import com.revoult.moneytransfer.exception.ServiceException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {
	static final Logger logger = Logger.getLogger(ServiceExceptionMapper.class);

	public Response toResponse(ServiceException e) {
		logger.error("Error: ",e);		
		return Response.status(e.getStatus())
				.entity(new Error(e.getErrorCode(), e.getReason(),e.getMessage()))
				.type(MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	
}
