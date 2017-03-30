/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.PushType;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.PushInfoRequest;
import com.kt.giga.home.openapi.ghms.ktInfra.service.KPNSService;
import com.kt.giga.home.openapi.ghms.ktInfra.service.KTInfraService;
import com.kt.giga.home.openapi.ghms.user.domain.vo.UserVo;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 5. 19.
 */
@Controller
@RequestMapping("kch")
public class PushController {
	
    private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KPNSService kPNSService;
    
    @Autowired
    private KTInfraService ktService;

	@RequestMapping(value={"push"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> gateway(
			@RequestParam(value="pnsRegId")						String pnsRegId,
			@RequestParam(value="svcTgtSeq")					String svcTgtSeq,
			@RequestParam(value="spotDevSeq")					String spotDevSeq,
			@RequestParam(value="eventId", required=false)		String eventId,
			@RequestParam(value="devNm")						String devNm,
			HttpServletResponse response) throws APIException {
		
		boolean history = true;
		if(eventId == null || "".equals(eventId)) {
			eventId = PushType.DEVICE_INITIAL.getEventId();
			history = false;
		}
        
        Map<String, String> data = new HashMap<String, String>();
        data.put("eventId", eventId);
        data.put("spotDevSeq", spotDevSeq);
        data.put("svcTgtSeq", svcTgtSeq);
        data.put("devNm", devNm);
        data.put("msg", "사용중인 홈매니저 허브 \"" + devNm + "\" 이/가 공장초기화 되었습니다.\n등록 홈매니저 허브 목록을 확인하여 주시기 바랍니다.");
        
        PushInfoRequest req = new PushInfoRequest();
        
        req.setRegistrationId(pnsRegId);
        req.setData(data);
        kPNSService.push(req, history);
		
		return new HashMap<String, Object>();
	}

	@RequestMapping(value={"sdp"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, String> sdp(
			@RequestParam(value="userId")						String userId,
			@RequestParam(value="passwd")						String passwd,
			HttpServletResponse response) throws APIException {
		
		Map<String, String> authResultMap = ktService.sendLoginRequest(userId, passwd);

		String authResultCode = authResultMap.get("resultCode");

		if(StringUtils.equals(authResultCode, "1")) {

			String credentialId = authResultMap.get("credentialId");
			
            log.info("#SDP# credentialId : mbrId[{}], credentialId[{}]", userId, credentialId);
            
			UserVo user = ktService.sendSubsRequest(userId, credentialId);	
	        
	        String[] custIdList = user.getCustIdList();
	        
	        if(!ArrayUtils.isEmpty(custIdList)) {

	            for(String custId : custIdList) {
	                log.info("#SDP# SVC_TGT_ID : mbrId[{}], custId[{}]", userId, custId);
	            }	
	        }
		}
		
		return authResultMap;
	}

}
