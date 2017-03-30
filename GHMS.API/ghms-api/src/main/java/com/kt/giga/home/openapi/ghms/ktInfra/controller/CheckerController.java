/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.ktInfra.service.CheckerService;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;


/**
 * 테스트 용
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 30.
 */
@Controller
@RequestMapping("checker")
public class CheckerController {
	
	@Autowired
	private CheckerService checkerService;
	
	@RequestMapping(value={"/test"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
	public Map<String, Object> test(@RequestBody Map<String, Object> req) throws APIException {		
		Map<String, Object> res = (Map<String, Object>)checkerService.test(req);
		
		return res;
	}

}
