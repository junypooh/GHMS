package com.kt.giga.home.openapi.controller;

import java.util.HashMap;

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

import com.kt.giga.home.openapi.domain.NewAuthToken;
import com.kt.giga.home.openapi.domain.UserAuth;
import com.kt.giga.home.openapi.service.UserServiceOld;

/**
 * 초기실행, 인증/로그인, 토큰갱신등 회원관련 처리 컨트롤러
 *
 * @author
 *
 */
@RequestMapping("/old")
@Controller("oldController")
public class UserControllerOld {

	private static final String PRODUCES_JSON = "application/json; charset=UTF-8";

	@Autowired
	private UserServiceOld userService;

	// 앱 초기 실행 - InitController 로 분리
	/*
	@RequestMapping(value={"/app/init"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public AppInit init(
			@RequestParam(value="deviceId",		required=true) String deviceId,
			@RequestParam(value="telNo",		required=true) String telNo,
			@RequestParam(value="appVer",		required=true) String appVer,
			@RequestParam(value="pnsRegId",		required=true) String pnsRegId,
			@RequestParam(value="svcCode",		required=true) String svcCode) throws Exception {

		return userService.init(deviceId, telNo, appVer, pnsRegId, svcCode);
	}
	*/

	// 사용자 인증, 로그인
	@RequestMapping(value={"/user/authentication2"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public UserAuth auth(
			@RequestHeader(value="authToken")	String authToken,
			@RequestParam(value="userId",		required=true) String userId,
			@RequestParam(value="passwd",		required=true) String passwd) throws Exception {

		return userService.auth(authToken, userId, passwd);
	}

/*	// 사용자 인증, 로그인
	@RequestMapping(value={"/user/helthAuthentication"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public UserAuth helthAuth(
			@RequestHeader(value="authToken")	String authToken) throws Exception {

		return userService.helthAuth(authToken);
	}*/

	// 인증토큰 재발급
	@RequestMapping(value={"/user/authentication/manager"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public NewAuthToken refreshToken(@RequestHeader(value="authToken") String authToken) throws Exception {

		return userService.refreshAuthToken(authToken);
	}

/*	// 인증토큰 재발급
	@RequestMapping(value={"/access"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public AppUser userJoinList(@RequestHeader(value="authToken") String authToken) throws Exception {

		return userService.userJoinList(authToken);
	}	*/

	// 홈 카메라 닉네임 설정
	@RequestMapping(value={"/devices/spot"}, method=RequestMethod.PUT, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String setDeviceName(
			@RequestHeader(value="authToken")	String authToken,
			@RequestBody						HashMap<String, String> params) throws Exception {

		String spotDevId = params.get("spotDevId");
		String devNm = params.get("devNm");
		userService.setDeviceName(authToken, spotDevId, devNm);

		return "{}";
	}


}
