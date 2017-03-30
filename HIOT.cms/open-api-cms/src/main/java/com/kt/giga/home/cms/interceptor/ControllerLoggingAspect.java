package com.kt.giga.home.cms.interceptor;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.*;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.kt.giga.home.cms.common.domain.LoginInfo;
import com.kt.giga.home.cms.manager.service.*;
import com.kt.giga.home.cms.manager.domain.*;

@Aspect
@Order(50)
@Component
public class ControllerLoggingAspect
{
	@Autowired
	private HttpSession session;
	
	@Autowired
	private HttpServletRequest requestI;
	
	@Autowired
	private MenuService menuService;		
	
	@Autowired
	private CMSLogService cmsLogService;
	
	@Before("execution(* *..*controller.*.*(..))")
	public void controllerLoggingHandler(JoinPoint joinPoint) throws Throwable
	{		
		HttpServletRequest requestM = null;
		
		for(Object obj : joinPoint.getArgs())
		{
			if(obj instanceof HttpServletRequest)
			{
				requestM = (HttpServletRequest)obj;
			}
		}		
		
		HttpServletRequest request = requestM != null ? requestM : requestI;
		
		String requestURI 	= request.getRequestURI() != null ? request.getRequestURI().replaceAll("//", "/") : "";
		String queryString 	= request.getQueryString();
		
		/*if(!requestURI.equals("/common/login.do") && !requestURI.equals("/common/reqAccount.do") && !requestURI.equals("/common/adminIdCheck.do") && !requestURI.equals("/common/logout.do"))*/
		if(!requestURI.equals("/changePw") && !requestURI.equals("/preview") && !requestURI.equals("/login") && !requestURI.equals("/authSMS") && !requestURI.equals("/logout") && !requestURI.equals("/test") && !requestURI.equals("/cms/test"))
		{
			boolean result = true;
			List<String> blockURIList = new ArrayList<String>();
			String[] blockURIArray = request.getServletContext().getInitParameter("blockURIByLogging").split(",");				
			
			for(String blockURI : blockURIArray) 
			{ 
				blockURIList.add(blockURI.trim()); 
			}
			
			for(String blockURI : blockURIList)
			{
				if(requestURI.equals(blockURI))
				{
					result = false;
				}
			}				
			
			SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMddHHmmss");
			
			LoginInfo loginInfo = (LoginInfo)request.getAttribute("loginInfo");
			
			Menu nowMainMenuInfo = null, nowSubMenuInfo = null;
			List<Menu> menusInfo = (List<Menu>)request.getAttribute("aclList");
			//List<Menu> menusInfo = (List<Menu>)session.getAttribute("aclList");
			
			/*
			if(menusInfo == null){
				System.out.println("menusInfo null!");
			}else{
				System.out.println("menusInfo not null!");
			}
			*/
			
			for(Menu cmsMenu : menusInfo)
			{
				if(requestURI.equals(cmsMenu.getUrl()) && !cmsMenu.getUpMenu().equals("0000"))
				{
					nowSubMenuInfo = cmsMenu;
					//System.out.println("queryString = null");
					//System.out.println("nowSubMenuInfo.getUpMenu() = " + nowSubMenuInfo.getUpMenu());
				}
			}
			
			if(queryString != null && !queryString.equals(""))
			{
				requestURI = requestURI + "?" + queryString;
				
				for(Menu cmsMenu : menusInfo)
				{
					if(requestURI.equals(cmsMenu.getUrl()) && !cmsMenu.getUpMenu().equals("0000"))
					{
						nowSubMenuInfo = cmsMenu;
						//System.out.println("queryString = " + queryString);
						//System.out.println("nowSubMenuInfo.getUpMenu() = " + nowSubMenuInfo.getUpMenu());
					}
				}				
			}
			
			nowMainMenuInfo = menuService.get(nowSubMenuInfo.getUpMenu()); //캐시 적용 포인트
			/*
			System.out.println("\n\n");
			System.out.println("#####################################################");
			System.out.println("# nowMainMenuInfo.getMenu() = " + nowMainMenuInfo.getMenu());
			System.out.println("# nowMainMenuInfo.getName() = " + nowMainMenuInfo.getName());
			System.out.println("# nowSubMenuInfo.getMenu() = " + nowSubMenuInfo.getMenu());
			System.out.println("# nowSubMenuInfo.getName() = " + nowSubMenuInfo.getName());
			System.out.println("#####################################################");
			System.out.println("\n\n");
			*/
			SiteMapPath siteMapPath = new SiteMapPath(nowMainMenuInfo.getMenu(), nowMainMenuInfo.getName(), nowSubMenuInfo.getMenu(), nowSubMenuInfo.getName());
			
			Map<String, Object> cmsLog = new HashMap<String, Object>();
			
			cmsLog.put("fd_admin_id"		, loginInfo.getId());
			cmsLog.put("fd_cms_main_menu"	, siteMapPath.getMainMenuCode());
			cmsLog.put("fd_cms_sub_menu"	, siteMapPath.getSubMenuCode());
			cmsLog.put("fd_class_name"		, joinPoint.getTarget().getClass().getName());
			cmsLog.put("fd_method_name"		, joinPoint.getSignature().getName());
			cmsLog.put("fd_access_ip"		, request.getRemoteAddr());
			cmsLog.put("fd_reg_date"		, ft.format(new Date()));
			
			Map<String, String[]> cmsLogParams = request.getParameterMap();
			
			/*
			System.out.println("\n\n");
			System.out.println("#####################################################");
			System.out.println("# cmsLog = " + cmsLog);
			System.out.println("# cmsLogParams = " + cmsLogParams);
			System.out.println("#####################################################");
			System.out.println("\n\n");
			*/
			
			if(result/* && !request.getRemoteAddr().equals("127.0.0.1")*/)
			{
				cmsLogService.register(cmsLog, cmsLogParams);
			}
			
			request.setAttribute("siteMapPath", siteMapPath);
		}
	}
}
