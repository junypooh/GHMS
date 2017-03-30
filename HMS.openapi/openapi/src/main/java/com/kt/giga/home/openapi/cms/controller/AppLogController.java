package com.kt.giga.home.openapi.cms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.cms.service.AppLogService;
import com.kt.giga.home.openapi.common.AuthToken;
import com.kt.giga.home.openapi.service.UserServiceOld;

@Controller
@RequestMapping("/appLog")
public class AppLogController {

	private static final String PRODUCES_JSON = "application/json; charset=UTF-8";
	
	@Autowired
	private UserServiceOld userService;
	
	@Autowired
	private AppLogService appLogService;
	
	@ResponseBody	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/set", method=RequestMethod.GET, produces=PRODUCES_JSON)
	public Map<String, Object> set(
			@RequestHeader(value="authToken", required=false) String authToken
			,@RequestParam Map<String, Object> appLog
		) throws Exception  {
		
		Map<String, Object> result = new HashMap<>();
		
		appLog.put("auth_token", authToken);
		try{
			AuthToken loginAuthToken 	= userService.checkLoginAuthToken(authToken);
			appLog.put("mbr_seq"	, loginAuthToken.getUserNo().toString());
			appLog.put("mbr_id"		, loginAuthToken.getUserId().toString());
			
		}catch(Exception e){
			//todo		
		}
		
		appLogService.set(appLog);
		
		result.put("code"		, 200);
		result.put("msg"		, "SUCCESS");
		result.put("telNo"		, appLog.get("telNo").toString());
		result.put("logTime"	, appLog.get("logTime").toString());
		
		return result;
	}
}
