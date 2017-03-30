/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.domain.vo.BaseVo;
import com.kt.giga.home.openapi.ghms.common.token.AuthToken;
import com.kt.giga.home.openapi.ghms.devices.domain.key.DeviceCtrStatusKey;
import com.kt.giga.home.openapi.ghms.devices.domain.key.DeviceSpotKey;
import com.kt.giga.home.openapi.ghms.devices.domain.key.IotControlKey;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.NewSpotDeviceMasterVo;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.SpotDeviceMasterVo;
import com.kt.giga.home.openapi.ghms.devices.service.DevicesService;
import com.kt.giga.home.openapi.ghms.devices.service.DevicesUpgradeService;
import com.kt.giga.home.openapi.ghms.user.domain.key.DeviceMasterKey;
import com.kt.giga.home.openapi.ghms.user.domain.vo.DeviceMasterVo;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;


/**
 * 제어 컨트롤러
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 1. 26.
 */
@Controller
@RequestMapping("devices")
public class DevicesController {

	/** Logger */
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DevicesService devicesService;
	
	@Autowired
	private DevicesUpgradeService devicesUpgradeService;

	/**
	 * 게이트웨이 정보 조회
	 * 
	 * @param authToken		인증 토큰
	 * @param gwUUID		게이트웨이 식별자
	 * @return				SpotDevRetvReslt
	 * @throws APIException
	 */
	@RequestMapping(value={"gateway"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> gateway(
			@RequestHeader(value="authToken")					String authToken,
			@RequestParam(value="serviceNo", required=false) 	String serviceNo,
			@RequestParam(value="gwUUID", required=false) 		String spotDevSeq,
			@RequestParam(value="getCmdYn")						String getCmdYn,
			@RequestParam(value="getCheckerYn", required=false)	String getCheckerYn,
			HttpServletResponse response) throws APIException {

		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		if(StringUtils.isEmpty(serviceNo))
			serviceNo = "0";
		if(StringUtils.isEmpty(spotDevSeq))
			spotDevSeq = "0";
		if(StringUtils.isEmpty(getCheckerYn))
			getCheckerYn = "Y";
		
		// 트랜젝션 아이디
		Map<String, Object> map = devicesService.selectSpotGateWayListRequest(token, Long.parseLong(serviceNo), Long.parseLong(spotDevSeq), getCmdYn, getCheckerYn);
		
		return map;
	}

	/**
	 * 디바이스 상태 조회
	 * @param authToken		인증 토큰
	 * @param svcTgtSeq		서비스일련번호
	 * @param devType		장치 타입
	 * @param spotDevSeq	장치일련번호
	 * @param snsnCd	         조회센싱코드
	 * @return Map
	 * @throws APIException
	 */
	@RequestMapping(value={"status"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> status(
			@RequestHeader(value="authToken")	String authToken,
			@RequestParam(value="svcTgtSeq") 	Long svcTgtSeq,
			@RequestParam(value="devType")		String devType,
			@RequestParam(value="spotDevSeq")	Long spotDevSeq,
			@RequestParam(value="snsnCd")		String snsnCd,
			@RequestParam(value="command", required=false)		String command,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());

		return devicesService.requestDeviceStatusToKafka(svcTgtSeq, devType, spotDevSeq, snsnCd, command);
		
	}
	
	/**
	 * 디바이스 상태 조회
	 * @param authToken		인증 토큰
	 * @param svcTgtSeq		서비스일련번호
	 * @param eventId		이벤트 ID(AP 접속:001HIT003E0015, AP 접속해제:001HIT003E0014)
	 * @return Map
	 * @throws APIException
	 */
	@RequestMapping(value={"ap/status"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void apStatus(
			@RequestHeader(value="authToken")	String authToken,
			@RequestParam(value="svcTgtSeq") 	Long svcTgtSeq,
			@RequestParam(value="eventId")		String eventId,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		devicesService.sendApStatusToKafka(svcTgtSeq, eventId);
		
	}
	
	/**
	 * 제어 트랜잭션 상태 조회
	 * 
	 * @param authToken		인증 토큰
	 * @param transacId		제어 트랜잭션 아이디
	 * @return				SpotDevRetvReslt
	 * @throws APIException
	 */
	@RequestMapping(value={"control/status"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SpotDeviceMasterVo controlStatus(
			@RequestHeader(value="authToken")					String authToken,
			@RequestBody	DeviceCtrStatusKey key,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return devicesService.selectSpotGateWayList(token, key);
	}

	/**
	 * 제어 트랜잭션 상태 조회(IFTTT 용)
	 * 
	 * @param authToken		인증 토큰
	 * @param transacId		제어 트랜잭션 아이디
	 * @return				SpotDevRetvReslt
	 * @throws APIException
	 */
	@RequestMapping(value={"control/status2"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public NewSpotDeviceMasterVo controlStatus2(
			@RequestHeader(value="authToken")					String authToken,
			@RequestBody	DeviceCtrStatusKey key,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return devicesService.selectSpotGateWayList2(token, key);
	}

	/**
	 * 제어 트랜잭션 상태 조회(고도화용)
	 * 
	 * @param authToken		인증 토큰
	 * @param transacId		제어 트랜잭션 아이디
	 * @return				SpotDevRetvReslt
	 * @throws APIException
	 */
	@RequestMapping(value={"control/status/upgrade"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public com.kt.giga.home.openapi.ghms.devices.domain.vo.DeviceMasterVo upgradeControlStatus(
			@RequestHeader(value="authToken")					String authToken,
			@RequestBody	DeviceCtrStatusKey key,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return devicesUpgradeService.selectSpotGateWayList(token, key);
	}

	/**
	 * 제어 트랜잭션 상태 조회
	 * 
	 * @param authToken		인증 토큰
	 * @param transacId		제어 트랜잭션 아이디
	 * @return				SpotDevRetvReslt
	 * @throws APIException
	 */
	@RequestMapping(value={"control/status/kt"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SpotDeviceMasterVo controlStatusKT(
			@RequestBody	DeviceCtrStatusKey key,
			HttpServletResponse response) throws APIException {
		
		AuthToken token = AuthToken.encodeAuthToken(null, null, null, null, null, null, "T001HIT003", null, null);
		
		key.setGwUUID(1);
		key.setGetCmdYn("N");
		
		return devicesService.selectSpotGateWayList(token, key);
	}

	/**
	 * 디바이스 동작 제어
	 * 
	 * @param authToken		인증 토큰
	 * @param itgCnvyData	ControlVo
	 * @return				Map<String, Object>
	 * @throws APIException
	 */
	@RequestMapping(value={"action"}, method=RequestMethod.PUT, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> action(
			@RequestHeader(value="authToken")	String authToken,
			@RequestBody						IotControlKey iotControlKey,
			HttpServletResponse response) throws APIException {
		
		Gson gson = new Gson();
		String json = gson.toJson(iotControlKey, IotControlKey.class);
		
		log.debug("iotControlKey : {}", json);
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return devicesService.spotDeviceAction(token, iotControlKey);
	}

	/**
	 *  Wifi 품질정보 요청(CMS 용)
	 * 
	 * @param authToken		인증 토큰
	 * @param itgCnvyData	ControlVo
	 * @return				Map<String, Object>
	 * @throws APIException
	 */
	@RequestMapping(value={"cms/wifi"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String cmsWifi(
			@RequestBody		Map<String, Object> param
			) throws APIException {
		
		Gson gson = new Gson();
		String json = gson.toJson(param, Map.class);
		
		log.debug("cms/wifi : {}", json);
		
		devicesUpgradeService.setCmsWifiLog(param);
		
		return "{\"CMS\":\"O\"}";
	}

	/**
	 *  Z-Wave 장치 연결상태 조회(CMS 용)
	 * 
	 * @param authToken		인증 토큰
	 * @param itgCnvyData	ControlVo
	 * @return				Map<String, Object>
	 * @throws APIException
	 */
	@RequestMapping(value={"cms/status"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String cmsStatus(
			@RequestBody		Map<String, Object> param
			) throws APIException {
		
		Gson gson = new Gson();
		String json = gson.toJson(param, Map.class);
		
		log.debug("cms/Status : {}", json);
		
		devicesUpgradeService.setCmsSatusLog(param);
		
		return "{\"CMS\":\"O\"}";
	}

	/**
	 * 모드 설정 변경
	 * 
	 * @param authToken		인증 토큰
	 * @param mode			modeCode 일반 : 00, 외출 : 01, 자택 : 02
	 * @return				BaseVo
	 * @throws APIException
	 */
	@RequestMapping(value={"mode"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo modePost(
			@RequestHeader(value="authToken")	String authToken,
			@RequestBody						Map<String, Object> mode,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());

		return devicesService.insertModeStatus(token, Long.parseLong(mode.get("serviceNo").toString()), mode.get("modeCode").toString());
	}

	/**
	 * 모드 상태 코드 조회
	 * 
	 * @param authToken		인증 토큰
	 * @param serviceNo		서비스일련번호
	 * @return String
	 * @throws APIException
	 */
	@RequestMapping(value={"mode"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DeviceMasterVo modeGet(
			@RequestHeader(value="authToken")				String authToken,
			@RequestParam(value="modeCode", required=false) String modeCode,
			@RequestParam(value="serviceNo") 				long serviceNo,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return devicesService.selectModeStatusCode(token, modeCode, serviceNo);
	}

	/**
	 * 모드 편집
	 * 
	 * @param authToken		인증 토큰
	 * @param key			DeviceMasterKey
	 * @return				BaseVo
	 * @throws APIException
	 */
	@RequestMapping(value={"mode"}, method=RequestMethod.PUT, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo modeEdit(
			@RequestHeader(value="authToken")	String authToken,
			@RequestBody						DeviceMasterKey key,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return devicesService.updateModeStatusSetupTxn(token, key);
	}

	/**
	 * 펌웨어 최신버전 조회 (실시간)
	 * 
	 * @param authToken		인증 토큰
	 * @param gwUUID		게이트웨이 식별자
	 * @return				Map<String, Object>
	 * @throws APIException
	 */
	@RequestMapping(value={"ucems/firmver"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, String> ucemsFirmver(
			@RequestHeader(value="authToken")	String authToken,
			@RequestParam(value="serviceNo") 	long serviceNo,
			@RequestParam(value="gwUUID")		long spotDevSeq,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		Map<String, String> map = devicesService.selectUcemsFirmverVersion(token, serviceNo, spotDevSeq);

		return map;
	}

	/**
	 * 펌웨어 업그레이드 요청
	 * 
	 * @param authToken		인증 토큰
	 * @param gwUUID		게이트웨이 식별자
	 * @return				BaseVo
	 * @throws APIException
	 */
	@RequestMapping(value={"ucems/firmup"}, method=RequestMethod.PUT, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String ucemsFirmup(
			@RequestHeader(value="authToken")	String authToken,
			@RequestBody						Map<String, String> mode,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		String spotDevSeq = mode.get("gwUUID");
		String serviceNo = mode.get("serviceNo");
		
		if(StringUtils.isEmpty(spotDevSeq) || StringUtils.isEmpty(serviceNo)) {
			throw new APIException("Not Present Parameter [serviceNo : "+ serviceNo + "] or [gwUUID : " + spotDevSeq + "]", HttpStatus.BAD_REQUEST);
		}
		
		devicesService.setFirmwareUpgradeControlToUCEMS(token, Long.parseLong(serviceNo), Long.parseLong(spotDevSeq));
		return "{}";
	}

	/**
	 * 펌웨어 업그레이드 상태 조회 (실시간)
	 * 
	 * @param authToken		인증 토큰
	 * @param gwUUID		게이트웨이 식별자
	 * @return				BaseVo
	 * @throws APIException
	 */
	@Deprecated
	@RequestMapping(value={"ucems/firmup/status"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo ucemsFirmupStatus(
			@RequestHeader(value="authToken")	String authToken,
			@RequestParam(value="serviceNo") 	long serviceNo,
			@RequestParam(value="gwUUID")		String spotDevSeq,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		// To-Do junypooh 펌웨어 업그레이드 상태 조회 (실시간) 로직 추가
		// Dummy Data Setting
		BaseVo vo = new BaseVo();
		vo.setResultCode("01");
		vo.setStateCode("100");

		return vo;
	}

	/**
	 * 장치 닉네임 설정
	 * 
	 * @param authToken		인증 토큰
	 * @param gwUUID		게이트웨이 식별자
	 * @return				BaseVo
	 * @throws APIException
	 */
	@RequestMapping(value={"spot"}, method=RequestMethod.PUT, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseVo spot(
			@RequestHeader(value="authToken")	String authToken,
			@RequestBody						DeviceSpotKey key,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());

		return devicesService.updateSpotDeviceNickName(token.getUserNoLong(), key.getServiceNo(), key.getDevUUID(), key.getDevNm());
	}
	
}
