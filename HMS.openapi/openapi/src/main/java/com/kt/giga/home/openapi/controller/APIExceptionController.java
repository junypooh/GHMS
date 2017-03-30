package com.kt.giga.home.openapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kt.giga.home.openapi.service.APIException;

/**
 * 글로벌 Exception 처리 컨트롤러
 *
 * @author
 *
 */
@ControllerAdvice
public class APIExceptionController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(value = {APIException.class})
	protected ResponseEntity<String> handleAPIException(APIException e) {

		ResponseEntity<String> responseEntity;
		if (e.getStatus() == HttpStatus.UNAUTHORIZED) {
			HttpHeaders responseHeaders = new  HttpHeaders();
			responseHeaders.add("WWW-Authenticate", "Basic realm=\"fake\"");
			responseEntity = new ResponseEntity<String>(e.getJSONErrorMessage(), responseHeaders ,e.getStatus());
		} else {
			responseEntity = new ResponseEntity<String>(e.getJSONErrorMessage(), e.getStatus());
		}
		log.warn(e.getMessage(), e);
		return responseEntity;
    }

	@ExceptionHandler(value = {Exception.class})
	protected ResponseEntity<String> handleException(Exception e) {
		log.warn(e.getMessage(), e);
		return new ResponseEntity<String>(
				new APIException("UnknownException", HttpStatus.INTERNAL_SERVER_ERROR).getJSONErrorMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
