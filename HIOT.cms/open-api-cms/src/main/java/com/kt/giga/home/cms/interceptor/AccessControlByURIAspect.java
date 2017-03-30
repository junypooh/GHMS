package com.kt.giga.home.cms.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.kt.giga.home.cms.common.domain.LoginInfo;
import com.kt.giga.home.cms.manager.domain.Menu;
import com.kt.giga.home.cms.manager.service.MenuService;

@Aspect
@Order(20)
@Component
public class AccessControlByURIAspect {
	
	@Autowired 
	private HttpServletRequest request;	
	
	@Autowired
	private MenuService menuService;
	
	@Around("execution(java.util.Map *..*controller.*.*(..))")
	public Object accessControlMapHandler(ProceedingJoinPoint joinPoint) throws Throwable {
		return this.accessControlHandler(joinPoint, "Map");
	}
	
	@Around("execution(java.lang.String *..*controller.*.*(..))")
	public Object accessControlStringHandler(ProceedingJoinPoint joinPoint) throws Throwable {
		return this.accessControlHandler(joinPoint, "String");
	}
	
	@Around("execution(org.springframework.web.servlet.ModelAndView *..*controller.*.*(..))")
	public Object accessControlModelAndViewHandler(ProceedingJoinPoint joinPoint) throws Throwable {
		return this.accessControlHandler(joinPoint, "ModelAndView");
	}
	
	public Object accessControlHandler(ProceedingJoinPoint joinPoint, String returnType) throws Throwable {
		
		boolean result = false;		
		Object responeObj = null;
		Object proceedObj = null;		
		
		String requestURI = request.getRequestURI();
		
		if(!requestURI.equals("/changePw") && !requestURI.equals("/preview") && !requestURI.equals("/login") && !requestURI.equals("/authSMS") && !requestURI.equals("/logout") && !requestURI.equals("/test") && !requestURI.equals("/cms/test")) {
			
			LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");		
			
			try {
				
				List<Menu> menuList = menuService.getListByID(loginInfo.getId());
				
				for(Menu menu : menuList) {
					
					if(menu.getUrl() != null && !menu.getUrl().equals("")) {
						//System.out.println("requestURI = " + requestURI);
						//System.out.println("menu.getUrl() = " + menu.getUrl());
						if(requestURI.equals(menu.getUrl())) {
							result = true;
						}
					}
				}
				
				if(result) {
					request.setAttribute("aclList", menuList);
				}
				
			} catch(Exception e) {
				result = false;
			}
			
			if(returnType.equals("String")) {
				responeObj = "/permission";
			} else if(returnType.equals("ModelAndView")) {
				responeObj = "/permission";
			} else {
				Map<String, Object> ajaxObj = new HashMap<>();
				ajaxObj.put("code", 403);
				ajaxObj.put("msg", "접근권한 없음");
				responeObj = ajaxObj;
			}
		} else {
			result = true;
		}
		
		
		proceedObj = (result) ? joinPoint.proceed() : responeObj;
		return proceedObj;
	}	

}
