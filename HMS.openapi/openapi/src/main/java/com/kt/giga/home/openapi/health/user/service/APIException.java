package com.kt.giga.home.openapi.health.user.service;

import java.util.HashMap;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * API 범용 Exception.
 * 
 * @author 
 *
 */
public class APIException extends Exception {
	
	private static final long serialVersionUID = -3169893738322461257L;
	private HttpStatus status;
	public APIException(String msg, HttpStatus status)	{
		super(msg);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}
	
	public String getJSONErrorMessage()	{
		
		HashMap<String, String> msg = new HashMap<String, String>();
		
		ObjectMapper mapper = new ObjectMapper();
		msg.put("code", String.valueOf(getStatus().value()));
		msg.put("msg", getMessage());
		
		String errMsg = "";
		try {
			errMsg = mapper.writeValueAsString(msg);
		} catch(Exception e) {
			
		}
		
		return errMsg;
	}	
}
