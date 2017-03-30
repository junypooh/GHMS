package com.kt.giga.home.openapi.cms.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.cms.service.VersionService;
import com.kt.giga.home.openapi.common.AuthToken;
import com.kt.giga.home.openapi.health.util.HealthSvcCode;
import com.kt.giga.home.openapi.service.UserServiceOld;

/**
 * 버전관리 컨트롤러
 * 
 * @author 조상현
 *
 */
@Controller
@RequestMapping("/")
public class VersionController {

	private static final String PRODUCES_JSON = "application/json; charset=UTF-8";
	
	@Autowired
	private UserServiceOld userService;
	
	@Autowired
	private VersionService versionService;
	
	HealthSvcCode healthSvcCode	= new HealthSvcCode();
	
//	private AuthToken checkLoginAuthToken(String authToken) throws Exception {
//		
//		AuthToken loginAuthToken = AuthToken.decodeAuthToken(authToken);
//		
//		if(!loginAuthToken.isValidLoginToken()){
//			throw new APIException("Invalid InitAuthToken", HttpStatus.UNAUTHORIZED);
//		}
//
//		return loginAuthToken;
//	}
	
	// App 최신버전 조회
	@RequestMapping(value={"/verAppLast"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject getVerAppLast(
			@RequestHeader(value="authToken", required=true) String authToken
	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		AuthToken loginAuthToken 	= userService.checkLoginAuthToken(authToken);
		
		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"		, healthSvcCode.GetUnitSvcCd(loginAuthToken.getSvcCode())	);	// 서비스 코드
		map.put("osCode"		, "1701" 						);	//OS 구분코드 (안드로이드 : 1701)
		map.put("phoneTypeCode"	, "1801" 						);	//단말형태 구분코드 (모바일 : 1801)

		JSONObject versionApp	= versionService.getVerAppLast(map);
		
		return versionApp;
	}

	// 펌웨어 최신버전 조회
	@RequestMapping(value={"/verFrmwrLast"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject getVerFrmwrLast(
			@RequestHeader(value="authToken", required=true) String authToken
	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		AuthToken loginAuthToken 	= userService.checkLoginAuthToken(authToken);
		
		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"		, healthSvcCode.GetUnitSvcCd(loginAuthToken.getSvcCode())	);	// 서비스 코드

		JSONObject versionFrmwr	= versionService.getVerFrmwrLast(map);
		
		return versionFrmwr;
	}

}
