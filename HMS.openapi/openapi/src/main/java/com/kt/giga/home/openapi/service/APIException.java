package com.kt.giga.home.openapi.service;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
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
	private String errorCode;
	private String detailedErrorCode;
	public APIException(String msg, HttpStatus status)	{
		super(msg);
		this.status = status;
	}

	public APIException(String msg, HttpStatus status, String errorCode)	{
		super(msg);
		this.status = status;
		this.errorCode = errorCode;
	}

	public APIException(String msg, HttpStatus status, String errorCode, String detailedErrorCode) {
		super(msg);
		this.status = status;
		this.errorCode = errorCode;
		this.detailedErrorCode = detailedErrorCode;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getJSONErrorMessage()	{

		HashMap<String, String> msg = new HashMap<String, String>();

		ObjectMapper mapper = new ObjectMapper();
		msg.put("code", String.valueOf(getStatus().value()));
		msg.put("msg", getMessage());

		if(StringUtils.isNoneBlank(errorCode)) {
			msg.put("errorCode", errorCode);
		}

		if(StringUtils.isNoneBlank(detailedErrorCode)) {
			msg.put("detailedErrorCode", detailedErrorCode);
		}

		String errMsg = "";
		try {
			errMsg = mapper.writeValueAsString(msg);
		} catch(Exception e) {

		}

		return errMsg;
	}
}
