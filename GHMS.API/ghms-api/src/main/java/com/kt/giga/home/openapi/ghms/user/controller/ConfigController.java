/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.domain.vo.BaseVo;
import com.kt.giga.home.openapi.ghms.common.token.AuthToken;
import com.kt.giga.home.openapi.ghms.user.domain.key.DeviceMasterKey;
import com.kt.giga.home.openapi.ghms.user.domain.vo.DeviceMasterVo;
import com.kt.giga.home.openapi.ghms.user.service.ConfigService;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;

/**
 * 알림 수신 설정 컨트롤러
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 1. 29.
 */
@Controller
@RequestMapping("user/config")
public class ConfigController {

	/** Logger */
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ConfigService configService;

	/**
	 * 알림 수신 설정 조회
	 * 
	 * @param authToken		인증 토큰
	 * @param serviceNo		서비스일련번호
	 * @return				DeviceMasterVo
	 * @throws APIException
	 */
	@RequestMapping(value={"alarm"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DeviceMasterVo alarmlist(
			@RequestHeader(value="authToken")	String authToken,
			@RequestParam(value="serviceNo")	long serviceNo,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		DeviceMasterVo vo = configService.selectAlarmlist(token, serviceNo);

		return vo;
	}

	/**
	 * 알림 수신 설정
	 * 
	 * @param authToken		인증 토큰
	 * @param key			DeviceMasterKey
	 * @return				BaseVo
	 * @throws APIException
	 */
	@RequestMapping(value={"alarm"}, method=RequestMethod.PUT, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo alarmUpdate(
			@RequestHeader(value="authToken")	String authToken,
			@RequestBody						DeviceMasterKey key,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());

		return configService.updateAlarm(token, key);
	}

}
