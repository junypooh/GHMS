/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 2.
 */
@ControllerAdvice
public class APIExceptionController {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(value = {APIException.class})
	protected ResponseEntity<String> handleAPIException(APIException e) {
		
		log.warn(e.getMessage(), e);
		return new ResponseEntity<String>(e.getJSONErrorMessage(), e.getStatus());
    }
	
	@ExceptionHandler(value = {Exception.class})
	protected ResponseEntity<String> handleException(Exception e) {
		log.warn(e.getMessage(), e);
		return new ResponseEntity<String>(
				new APIException("UnknownException", HttpStatus.INTERNAL_SERVER_ERROR).getJSONErrorMessage(), 
				HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
