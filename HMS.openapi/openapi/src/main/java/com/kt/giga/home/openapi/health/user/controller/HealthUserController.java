package com.kt.giga.home.openapi.health.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.common.AuthToken;
import com.kt.giga.home.openapi.health.user.dao.HealthUserDao;
import com.kt.giga.home.openapi.health.user.domain.HealthUserAuth;
import com.kt.giga.home.openapi.health.user.service.HealthUserService;
import com.kt.giga.home.openapi.service.APIException;

/**
 * 초기실행, 회원가입, 토큰갱신등 회원관련 처리 컨트롤러
 * 
 * @author 한동희
 *
 */
@Controller
public class HealthUserController {

	private static final String PRODUCES_JSON = "application/json; charset=UTF-8";

	@Autowired
	private HealthUserService helthUserService;
	
	@Autowired
	private HealthUserDao helthUserDao;
	
	// 앱 초기 실행
	@RequestMapping(value={"/user/app/init"}, method=RequestMethod.GET,  produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public HealthUserAuth init(
			@RequestParam(value="deviceId",		required=true) String deviceId,
			@RequestParam(value="telNo",		required=true) String telNo,
			@RequestParam(value="appVer",		required=true) String appVer,
			@RequestParam(value="pnsRegId",		required=true) String pnsRegId,
			@RequestParam(value="svcCode",		required=true) String svcCode,
			@RequestParam(value="screenSize",	required=true) String screenSize
			) throws Exception { //약관id, 동의YN
		
		
		return helthUserService.init(deviceId, telNo, appVer, pnsRegId, svcCode,screenSize);
		
	}
	
	// 앱 초기 실행
	@RequestMapping(value={"/user/registration"}, method=RequestMethod.POST, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> mbrJoin(
//			@RequestBody  HashMap<String, String> params) throws Exception {
			@RequestBody					JSONObject params
	) throws Exception {
		
		System.out.println(params);
		
		String deviceId 	                    = params.get("deviceId"	).toString();
		String telNo 		                    = params.get("telNo"	).toString();
		String appVer 		                    = params.get("appVer"	).toString();
		String pnsRegId 	                    = params.get("pnsRegId"	).toString();
		String svcCode 		                    = params.get("svcCode"	).toString();
		String pinNo 		                    = params.get("pinNo"	).toString();
		List<HashMap<String, String>> agreeList = (List<HashMap<String, String>>) params.get("termsList"); //약관id, 동의YN
		
		return helthUserService.mbrJoin(deviceId, telNo, appVer, pnsRegId, svcCode, pinNo, agreeList);
		
	}
	
	// íìíí´
	@RequestMapping(value={"/user/leave"}, method=RequestMethod.DELETE, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> widtdraw(
			@RequestHeader(value="authToken", required=true) String authToken
			) throws Exception {
		
		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		String telNo = loginAuthToken.getTelNo();
		String svcCode = loginAuthToken.getSvcCode();
		System.out.println("telNo-----------> " +telNo);
		return helthUserService.withdraw(telNo, svcCode);
		
	}
	
	private AuthToken checkLoginAuthToken(String authToken) throws Exception {
		
		AuthToken loginAuthToken = AuthToken.decodeAuthToken(authToken);
		
		if(!loginAuthToken.isValidLoginToken()){
			throw new APIException("Invalid InitAuthToken", HttpStatus.UNAUTHORIZED);
		}

		return loginAuthToken;
	}
}
