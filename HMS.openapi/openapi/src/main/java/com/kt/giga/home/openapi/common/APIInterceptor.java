package com.kt.giga.home.openapi.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class APIInterceptor extends HandlerInterceptorAdapter {
	private static final String API_TIME_KEY = "apiTime";
	@Autowired
	private APIEnv env;
	private Logger log = LoggerFactory.getLogger(getClass());

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute(API_TIME_KEY, System.currentTimeMillis());
		if("get".equalsIgnoreCase(request.getMethod()) || "delete".equalsIgnoreCase(request.getMethod())) {
			String query = request.getQueryString() == null ? "" : "?" + request.getQueryString();
			log.info("# Request : {}", request.getRequestURL() + query);
		} else {
			log.info("# Request : {}", request.getRequestURL());			
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("# Response : {} - {}", request.getRequestURL(), getAPIDurationTime(request) + " ms");
	}

	private long getAPIDurationTime(HttpServletRequest request) {
		long l = (Long) request.getAttribute(API_TIME_KEY);
		return System.currentTimeMillis() - l;
	}
}
