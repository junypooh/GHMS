/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.controller;

import java.util.List;

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
import com.kt.giga.home.openapi.ghms.user.domain.key.MasterUserKey;
import com.kt.giga.home.openapi.ghms.user.domain.vo.MasterUserVo;
import com.kt.giga.home.openapi.ghms.user.service.MyPageService;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;

/**
 * 마이페이지 컨트롤러
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 1. 29.
 */
@Controller
@RequestMapping("user/mypage")
public class MyPageController {

	/** Logger */
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MyPageService myPageService;

	/**
	 * 비상연락망 조회
	 * 
	 * @param authToken		인증 토큰
	 * @param serviceNo		서비스일련번호
	 * @return				List<MasterUserVo>
	 * @throws APIException
	 */
	@RequestMapping(value={"emcontacts"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<MasterUserVo> emcontactslist(
			@RequestHeader(value="authToken")	String authToken,
			@RequestParam(value="serviceNo")	long serviceNo,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		MasterUserKey key = new MasterUserKey();
		key.setServiceNo(serviceNo);
		return myPageService.selectEmcontacts(key);
	}

	/**
	 * 비상연락망 등록
	 * 
	 * @param authToken		인증 토큰
	 * @param key			MasterUserKey
	 * @return				BaseVo
	 * @throws APIException
	 */
	@RequestMapping(value={"emcontacts"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo emcontactsRegist(
			@RequestHeader(value="authToken")	String authToken,
			@RequestBody						MasterUserKey key,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());

		return myPageService.insertEmcontacts(key);
	}

	/**
	 * 비상연락망 수정(알림 여부)
	 * 
	 * @param authToken		인증 토큰
	 * @param key			MasterUserKey
	 * @return				BaseVo
	 * @throws APIException
	 */
	@RequestMapping(value={"emcontacts"}, method=RequestMethod.PUT, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo emcontactsAlarm(
			@RequestHeader(value="authToken")	String authToken,
			@RequestBody						MasterUserKey key,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());

		return myPageService.updateEmcontacts(key);
	}

	/**
	 * 비상연락망 삭제
	 * 
	 * @param authToken		인증 토큰
	 * @param key			MasterUserKey
	 * @return				BaseVo
	 * @throws APIException
	 */
	@RequestMapping(value={"emcontacts"}, method=RequestMethod.DELETE, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo emcontactsDelete(
			@RequestHeader(value="authToken")	String authToken,
			@RequestParam(value="serviceNo")	long serviceNo,
			@RequestParam(value="seq")			int seq,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());

		MasterUserKey key = new MasterUserKey();
		key.setSeq(seq);
		key.setServiceNo(serviceNo);
		return myPageService.deleteEmcontacts(key);
	}

}
