package com.kt.giga.home.cms.interceptor;

import java.util.*;

import javax.servlet.http.*;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.core.annotation.*;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.manager.domain.*;
import com.kt.giga.home.cms.manager.service.*;

@Aspect
@Order(30)
@Component
public class AccessControlListAspect {

	@Autowired 
	private HttpServletRequest request;	
	
	@Autowired
	private MenuService menuService;	
	
	@Before("execution(java.lang.String *..*controller.*.*(..))")
	public void accessControlListHandler() {
		
		String queryString 	= request.getQueryString();
		String requestURI 	= request.getRequestURI();	
		String refererURL	= request.getHeader("Referer");
		
		String aclMainMenu = null, aclSubMenu = null;
		List<Menu> aclMainMenuList = new ArrayList<Menu>();
		List<Menu> aclSubMenuList = new ArrayList<Menu>();
		
		if(!requestURI.equals("/changePw") && !requestURI.equals("/preview") && !requestURI.equals("/login") && !requestURI.equals("/authSMS") && !requestURI.equals("/logout") && !requestURI.equals("/test") && !requestURI.equals("/cms/test")) {
		
			List<Menu> aclList = (List<Menu>)request.getAttribute("aclList");
			
			for(Menu acl : aclList) {
				if(!acl.getUpMenu().equals("0000") && acl.getUrl().equals(requestURI)) {
					aclMainMenu = acl.getUpMenu();
				} 
				
				if(acl.getUpMenu().equals("0000") && acl.getUseYN().equals("Y")) {
					aclMainMenuList.add(acl);
				}
			}
			
			for(Menu acl : aclList) {
				if(acl.getUpMenu().equals(aclMainMenu) && acl.getUseYN().equals("Y")) {
					aclSubMenuList.add(acl);
					if(acl.getUrl().equals(requestURI)) {
						aclSubMenu = acl.getMenu();
					}
				}
			}
			
			if(aclSubMenu == null && refererURL != null) {
				for(Menu acl : aclSubMenuList) {
					if(refererURL.indexOf(acl.getUrl()) > 0) {
						aclSubMenu = acl.getMenu();
					}
				}
			}
			
			MenuCompare compare = new MenuCompare();
			Collections.sort(aclMainMenuList, compare);
			Collections.sort(aclSubMenuList, compare);
			
			request.setAttribute("aclMainMenuList", aclMainMenuList);
			request.setAttribute("aclSubMenuList", aclSubMenuList);
			request.setAttribute("aclMainMenu", aclMainMenu);
			request.setAttribute("aclSubMenu", aclSubMenu);
		}
	}
	
	public class MenuCompare implements Comparator<Menu> {
		@Override
		public int compare(Menu obj1, Menu obj2) {
			int result = -1;
			
			if(obj1.getSortNo() < obj2.getSortNo()) {
				result = -1;
			} else if(obj1.getSortNo() > obj2.getSortNo()) {
				result = 1;
			} else if(obj1.getSortNo() == obj2.getSortNo()) {
				result = 0;
			}
			
			return result;
		}
	}	
}
