/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.common.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kt.giga.home.openapi.ghms.util.properties.APIEnv;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 2.
 */
@Component
public class APIInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private APIEnv env;

	private Logger log = LoggerFactory.getLogger(getClass());

	private static final String API_TIME_KEY = "apiTime";

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute(API_TIME_KEY, System.currentTimeMillis());
		if("get".equalsIgnoreCase(request.getMethod()) || "delete".equalsIgnoreCase(request.getMethod())) {
			String query = request.getQueryString() == null ? "" : "?" + request.getQueryString();
			log.info("###############  APIInterceptor preHandle  #################");
			log.info("# Request : {} {}", request.getMethod(), request.getRequestURL() + query);
			log.debug("# AuthToken : {}", request.getHeader("authToken") );
			log.info("############################################################");
		} else {
			log.info("###############  APIInterceptor preHandle  #################");
			log.info("# Request : {} {}", request.getMethod(), request.getRequestURL());
			log.debug("# AuthToken : {}", request.getHeader("authToken") );
			log.info("############################################################");
		}
		
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("############  APIInterceptor afterCompletion  ##############");
		log.info("# Response : {} - {}", request.getRequestURL(), getAPIDurationTime(request) + " ms");
		log.info("####################  APIInterceptor  ######################");
	}

	private long getAPIDurationTime(HttpServletRequest request) {
		long l = (Long) request.getAttribute(API_TIME_KEY);
		return System.currentTimeMillis() - l;
	}

}
