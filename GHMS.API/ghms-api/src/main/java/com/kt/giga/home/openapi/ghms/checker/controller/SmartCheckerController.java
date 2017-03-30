/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.checker.controller;

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

import com.google.gson.Gson;
import com.kt.giga.home.openapi.ghms.checker.domain.key.GetIoTEquipConnInfo;
import com.kt.giga.home.openapi.ghms.checker.domain.key.TimeSettingKey;
import com.kt.giga.home.openapi.ghms.checker.domain.vo.GetIoTEquipConnInfoResponse;
import com.kt.giga.home.openapi.ghms.checker.domain.vo.GetIoTEquipConnInfoResult;
import com.kt.giga.home.openapi.ghms.checker.domain.vo.SmartChkResultVo;
import com.kt.giga.home.openapi.ghms.checker.domain.vo.TimeSettingVo;
import com.kt.giga.home.openapi.ghms.checker.service.SmartCheckerService;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.token.AuthToken;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;

/**
 * 스마트체커 컨트롤러
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 1. 26.
 */
@Controller
@RequestMapping("smartChecker")
public class SmartCheckerController {

	/** Logger */
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SmartCheckerService smartCheckerService;
	
	/**
	 * AP 맥 등록 (청약 생성)
	 * 1. 체커를 통해 MAC의 SA_ID 값을 가져온다.
	 * 2. SDP를 통해 AP(허브) 상품 부가상품인 SA_ID 값을 가져온다.
	 * 3. 위 두 SA_ID까 일치 할 경우 청약을 생성한다. 
	 * @param authToken
	 * @param key
	 * @param response
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value={"service"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SmartChkResultVo servicePost(
			@RequestHeader(value="authToken")					String authToken,
			@RequestBody	TimeSettingKey key,
			HttpServletResponse response) throws APIException {

		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return smartCheckerService.insertService(token, key.getApMacAddress());
	}

	/**
	 * 인터넷 시간 제한 설정 조회
	 * @param authToken
	 * @param mbrId
	 * @param svcTgtSeq
	 * @param apMacAddress
	 * @param hostMacAddress
	 * @param response
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value={"timeSetting"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public TimeSettingVo timeSettingGet(
			@RequestHeader(value="authToken")					String authToken,
			@RequestParam(value="svcTgtSeq") 					String svcTgtSeq,
			@RequestParam(value="apMacAddress")					String apMacAddress,
			@RequestParam(value="hostMacAddress")				String hostMacAddress,
			HttpServletResponse response) throws APIException {

		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return smartCheckerService.getInternetAccessControl(token, svcTgtSeq, apMacAddress, hostMacAddress);
	}

	/**
	 * 인터넷 시간 제한 설정
	 * @param authToken
	 * @param key
	 * @param response
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value={"timeSetting"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SmartChkResultVo timeSettingPost(
			@RequestHeader(value="authToken")					String authToken,
			@RequestBody	TimeSettingKey key,
			HttpServletResponse response) throws APIException {

		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return smartCheckerService.setInternetAccessControl(token, key);
	}

	/**
	 * 인터넷 시간 제한 복구
	 * @param authToken
	 * @param key
	 * @param response
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value={"timeSetting"}, method=RequestMethod.PUT, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SmartChkResultVo timeSettingPut(
			@RequestHeader(value="authToken")					String authToken,
			@RequestBody	TimeSettingKey key,
			HttpServletResponse response) throws APIException {

		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return smartCheckerService.setInternetAccessControlRecovery(token, key.getApMacAddress());
	}

	/**
	 * 인터넷 시간 관리 > 관리단말 추가
	 * @param authToken
	 * @param key
	 * @param response
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value={"timeSetting/device"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SmartChkResultVo timeSettingDevicePost(
			@RequestHeader(value="authToken")					String authToken,
			@RequestBody	TimeSettingKey key,
			HttpServletResponse response) throws APIException {

		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return smartCheckerService.setDeviceManager(token, key);
	}

	/**
	 * 인터넷 시간 관리 > 관리단말 삭제
	 * @param authToken
	 * @param key
	 * @param response
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value={"timeSetting/device"}, method=RequestMethod.DELETE, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SmartChkResultVo timeSettingDeviceDelete(
			@RequestHeader(value="authToken")					String authToken,
			@RequestParam(value="svcTgtSeq") 					Long svcTgtSeq,
			@RequestParam(value="spotDevSeq")					Long spotDevSeq,
			@RequestParam(value="hostMacAddress")				String hostMacAddress,
			HttpServletResponse response) throws APIException {

		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return smartCheckerService.deleteDeviceManager(token, svcTgtSeq, spotDevSeq, hostMacAddress);
	}

	/**
	 * 비밀번호 인증
	 * @param authToken
	 * @param key
	 * @param response
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value={"password"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SmartChkResultVo passwordPost(
			@RequestHeader(value="authToken")					String authToken,
			@RequestBody	TimeSettingKey key,
			HttpServletResponse response) throws APIException {

		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return null;
	}

	/**
	 * NAS 상태 조회
	 * @param authToken
	 * @param key
	 * @param response
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value={"nas"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SmartChkResultVo nasGet(
			@RequestHeader(value="authToken")					String authToken,
			@RequestParam(value="svcTgtSeq") 					String svcTgtSeq,
			@RequestParam(value="apMacAddress")					String apMacAddress,
			HttpServletResponse response) throws APIException {

		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return smartCheckerService.getNASStateInfo(token, apMacAddress);
	}

	/**
	 * PC 상태 조회
	 * @param authToken
	 * @param key
	 * @param response
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value={"pc"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SmartChkResultVo pcGet(
			@RequestHeader(value="authToken")					String authToken,
			@RequestParam(value="svcTgtSeq") 					String svcTgtSeq,
			@RequestParam(value="apMacAddress")					String apMacAddress,
			@RequestParam(value="pcMacAddress")					String pcMacAddress,
			HttpServletResponse response) throws APIException {

		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return smartCheckerService.getPCOnOffState(token, apMacAddress, pcMacAddress);
	}

	/**
	 * PC 원격 켜기
	 * @param authToken
	 * @param key
	 * @param response
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value={"pc"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SmartChkResultVo pcPost(
			@RequestHeader(value="authToken")					String authToken,
			@RequestBody	TimeSettingKey key,
			HttpServletResponse response) throws APIException {

		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return smartCheckerService.setWakeOnLan(token, key.getApMacAddress(), key.getPcMacAddress());
	}

	/**
	 * checker -> infra -> openapi -> infra -> checker
	 * 홈 IoT 서비스단말 연결상태 요청
	 * 
	 * @param GetIoTEquipConnInfo
	 * @return	GetIoTEquipConnInfoResult
	 * @throws APIException
	 */
	@RequestMapping(value={"getIoTEquipConnInfo"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public GetIoTEquipConnInfoResult getIoTEquipConnInfo(
			@RequestBody		GetIoTEquipConnInfo key
			) throws APIException {
		
		Gson gson = new Gson();
		String json = gson.toJson(key, GetIoTEquipConnInfo.class);
		
		log.debug("getIoTEquipConnInfo : {}", json);
		
		return smartCheckerService.getIoTEquipConnInfo(key);
	}

	/**
	 * checker -> infra -> openapi -> infra -> checker
	 * 홈 IoT 서비스단말 연결상태 조회
	 * 
	 * @param GetIoTEquipConnInfo
	 * @return	GetIoTEquipConnInfoResponse
	 * @throws APIException
	 */
	@RequestMapping(value={"getIoTEquipConnInfoResponse"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public GetIoTEquipConnInfoResponse getIoTEquipConnInfoResponse(
			@RequestBody		GetIoTEquipConnInfo key
			) throws APIException {
		
		Gson gson = new Gson();
		String json = gson.toJson(key, GetIoTEquipConnInfo.class);
		
		log.debug("getIoTEquipConnInfoResponse : {}", json);
		
		return smartCheckerService.getIoTEquipConnInfoResponse(key);
	}
}
