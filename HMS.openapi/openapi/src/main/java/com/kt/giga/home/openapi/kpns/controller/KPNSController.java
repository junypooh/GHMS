package com.kt.giga.home.openapi.kpns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.kpns.domain.PushReportRequest;
import com.kt.giga.home.openapi.kpns.service.KPNSService;

/**
 * pns발송요청에 대한 수신 여부 처리 컨트롤러
 * 
 * @author 
 *
 */
@Controller("HCam.KPNSController")
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
	@RequestMapping(value={"/kpns/report"}, method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void report(@RequestBody PushReportRequest req) throws Exception {		
		service.report(req);
	}
}
