package com.kt.giga.home.openapi.cms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.cms.service.InitSyncService;

/**
 * 초기화 관련 처리 컨트롤러
 * 
 * @author 조상현
 *
 */
@Controller
@RequestMapping("/")
public class InitSyncController {

	private static final String PRODUCES_JSON = "application/json; charset=UTF-8";
	
	@Autowired
	private InitSyncService initSyncService;

	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    Date date 			= new Date();
    String now 			= df.format(date);
	
	// 앱Init 동기화
	@RequestMapping(value={"/syncInit"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String syncInit(

	) throws Exception {

		//앱Init 동기화
		initSyncService.syncInit( "001" );	
		initSyncService.syncInit( "002" );	
		initSyncService.syncInit( "003" );	

		return "{}";
	}
			
}
