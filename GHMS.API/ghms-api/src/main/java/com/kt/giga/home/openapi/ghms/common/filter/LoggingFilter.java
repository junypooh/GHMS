/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.common.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.isrsal.logging.RequestWrapper;
import com.github.isrsal.logging.ResponseWrapper;
import com.kt.giga.home.openapi.ghms.util.json.JsonUtils;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 4. 22.
 */
public class LoggingFilter extends OncePerRequestFilter {

	private Logger log = LoggerFactory.getLogger(this.getClass());
    private AtomicLong id = new AtomicLong(1);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        if(log.isInfoEnabled()){
            long requestId = id.incrementAndGet();
            request = new RequestWrapper(requestId, request);
            response = new ResponseWrapper(requestId, response);
        }
        
        try {
            filterChain.doFilter(request, response);
        } finally {
            if(log.isInfoEnabled()){
            	logRequest(request);
                logResponse(request, (ResponseWrapper)response);
            }
        }
    }

    private void logRequest(final HttpServletRequest request) {
    	if("get".equalsIgnoreCase(request.getMethod()) || "delete".equalsIgnoreCase(request.getMethod())) {
			String query = request.getQueryString() == null ? "" : "?" + request.getQueryString();
			if(log.isDebugEnabled()) {
				log.debug("# Request " + request.getMethod() + " : " + request.getRequestURL() + query);	
			}
		} else {
			if(request instanceof RequestWrapper){
				RequestWrapper requestWrapper = (RequestWrapper) request;
				if(log.isDebugEnabled()) {
					log.debug("# Request " + request.getMethod() + " : " + request.getRequestURL() + "\n" + JsonUtils.toPrettyJson(new String(requestWrapper.toByteArray())));
				}
			}			
		}
    }

    private void logResponse(final HttpServletRequest request, final ResponseWrapper response) {
    	try {
    		if(log.isInfoEnabled() && response.toByteArray() != null) {
    			if(log.isDebugEnabled()) {
    				log.debug("# Response " + request.getMethod() + " : " + request.getRequestURL() + "\n" + JsonUtils.toPrettyJson(new String(response.toByteArray(), response.getCharacterEncoding())));
    			}
    		}
		} catch (UnsupportedEncodingException e) {
			logger.warn("Failed to parse response payload", e);
		}
    }

}
