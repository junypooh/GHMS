/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.common.aspect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 2.
 */
@Component
@Aspect
public class ProfilingAspect {

	private Logger log = LoggerFactory.getLogger(getClass());
	private HashMap<Long, String> transactionMap = new HashMap<Long, String>();
	private SimpleDateFormat tsFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   
	public ProfilingAspect(){
		log.info("Initializing Profiling Aspect... ");
	}
	
	@Before("execution(* com.kt.giga.home.openapi.ghms..controller.*(..)) || execution(* com.kt.giga.home.openapi.ghms..service.*(..))")
    public void beforeTargetMethod(JoinPoint thisJoinPoint) {
		
		long threadId = Thread.currentThread().getId();
		String className = thisJoinPoint.getTarget().getClass().getName();
		
		if(StringUtils.endsWith(className, "Controller")) {
			String transactionId = String.valueOf(System.currentTimeMillis());
			transactionMap.put(threadId, transactionId);
		}
    }	
	
	@Around("execution(* com.kt.giga.home.openapi.ghms..controller.*(..)) || execution(* com.kt.giga.home.openapi.ghms..service.*(..))")
	public Object profileMethods(ProceedingJoinPoint thisJoinPoint) throws Throwable {
		
		long threadId = Thread.currentThread().getId();
		String className = thisJoinPoint.getTarget().getClass().getName();
		String methodName = thisJoinPoint.getSignature().getName();
		
		StringBuffer argsStringBuffer = new StringBuffer();       
 
		Object[] args = thisJoinPoint.getArgs();
		if(ArrayUtils.isNotEmpty(args)) {
			for (int i = 0; i < args.length; i++) {
				argsStringBuffer.append(args[i]).append(",");
			}
			
			if (args.length > 0) {
				argsStringBuffer.deleteCharAt(argsStringBuffer.length() - 1);
			}
		}

        long l = System.currentTimeMillis();
        Object ret = null;
		try {
			ret = thisJoinPoint.proceed();	
		} catch(Throwable t) {
			throw t;
		} finally {
			long elapsedTime = System.currentTimeMillis() - l;
			String dateTime = tsFormat.format(new Date(System.currentTimeMillis()));
			log.info("## Profiling {} {} {} {} {}.{} {}", dateTime, threadId, transactionMap.get(threadId), elapsedTime, className, methodName, argsStringBuffer.toString());
		}

        return ret;
    }

}
