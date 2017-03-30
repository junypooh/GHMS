/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.PushReportRequest;
import com.kt.giga.home.openapi.ghms.ktInfra.service.KPNSService;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 27.
 */
@Controller
public class KPNSController {

	@Autowired
	private KPNSService service;

	/**
	 * pns 수신여부 업데이트 메쏘드
	 * 
	 * @param authToken		초기 인증 토큰
	 * @param userId		사용자 아이디
	 * @param passwd		사용자 패스워드
	 * @return				사용자 인증 객체
	 * @throws Exception
	 */
//	@RequestMapping(value={"", "/"}, method=RequestMethod.POST)
	@RequestMapping(value={"kpns/report"}, method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void report(@RequestBody PushReportRequest req) throws Exception {		
		service.report(req);
	}

}
