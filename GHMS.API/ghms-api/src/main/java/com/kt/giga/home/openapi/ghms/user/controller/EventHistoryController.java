/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.controller;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.token.AuthToken;
import com.kt.giga.home.openapi.ghms.kafka.service.HbaseControlHistoryService;
import com.kt.giga.home.openapi.ghms.user.domain.vo.EventHistoryVo;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;

/**
 * 디바이스 이벤트 정보 컨트롤러
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 1. 29.
 */
@Controller
@RequestMapping("user")
public class EventHistoryController {

	/** Logger */
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired HbaseControlHistoryService hbaseControlHistoryService;
	
	/**
	 * 디바이스 이벤트 정보 조회
	 * 
	 * @param authToken		인증 토큰
	 * @param serviceNo		서비스일련번호
	 * @param reqSeq		요청시퀀스
	 * @param limitCnt		요청시퀀스부터 불러올 갯수 (디폴트:가장 최근)
	 * @return				EventHistoryVo
	 * @throws APIException
	 */
	@RequestMapping(value={"eventhistory"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> eventhistory(
			@RequestHeader(value="authToken")	String authToken,
			@RequestParam(value="serviceNo")	Long svcTgtSeq,
			@RequestParam(value="reqSeq", required=false)		String startRowKey,
			@RequestParam(value="devUUID", required=false)		Long devUuid,
			@RequestParam(value="limitCnt")		Integer limitCnt,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		return hbaseControlHistoryService.findList(limitCnt, svcTgtSeq, devUuid);
	}

}
