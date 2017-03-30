package com.kt.giga.home.cms.interceptor;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.core.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.util.*;
import com.kt.giga.home.cms.common.domain.*;
import com.kt.giga.home.cms.common.service.*;
import com.kt.giga.home.cms.manager.service.*;

@Aspect
@Order(10)
@Component
public class AuthValidationAspect {
	
	@Autowired
	private APIEnv apiEnv;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ManagerSvcService managerSvcService;
	
	private String getDecryptByAES(String plainText) throws UnsupportedEncodingException, Exception {
		byte[] key 	= apiEnv.getProperty("cms.encrypt.key").getBytes("UTF-8");
		byte[] iv	= apiEnv.getProperty("cms.encrypt.iv").getBytes("UTF-8");
		return CipherUtils.decryptAESCTRNoPaddingHex(key, iv, plainText);
	}		
	
	@SuppressWarnings("unchecked")
	@Around("execution(* *..*controller.*.*(..))")
	public Object authValidationHandler(ProceedingJoinPoint joinPoint) throws Throwable {
		
		boolean result = false;		
		Object proceedObj = null;
		
		String requestURI = request.getRequestURI();		
		
		try {
			if(!requestURI.equals("/changePw") && !requestURI.equals("/preview") && !requestURI.equals("/login") && !requestURI.equals("/authSMS") && !requestURI.equals("/logout") && !requestURI.equals("/test") && !requestURI.equals("/cms/test")) {
				
				String encryptAuth = "";
				
				if(request.getCookies() != null) {
					for(Cookie cookie : request.getCookies()) {
						if(cookie.getName().equals("auth"))	{
							encryptAuth = cookie.getValue();
						}
					}
				}	
				
				if(!encryptAuth.equals("")) {
					String decryptAuth = this.getDecryptByAES(encryptAuth);
					Map<String, String> obj = ObjectConverter.toJSON(decryptAuth);
					
					LoginInfo loginInfo = new LoginInfo();
					
					loginInfo.setId(obj.get("id"));
					loginInfo.setName(obj.get("name"));
					loginInfo.setMobile(obj.get("mobile"));
					loginInfo.setEmail(obj.get("email"));
					loginInfo.setRoleName(obj.get("roleName"));
					loginInfo.setRemoteAddress(obj.get("remoteAddress"));
					loginInfo.setSvcList(managerSvcService.getListByID(obj.get("id")));
					
					request.setAttribute("loginInfo", loginInfo);
					result = true;
				}
			} else {
				result = true;
			}			
		} catch(Exception e) {}
		
		proceedObj = (result) ? joinPoint.proceed() : "/authorization";
		return proceedObj;
	}

}
