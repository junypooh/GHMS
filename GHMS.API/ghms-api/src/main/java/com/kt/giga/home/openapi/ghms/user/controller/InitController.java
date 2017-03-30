/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.controller;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
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
import com.kt.giga.home.openapi.ghms.common.domain.vo.AppEncryptorVo;
import com.kt.giga.home.openapi.ghms.user.domain.key.AppInitKey;
import com.kt.giga.home.openapi.ghms.user.domain.vo.AppInitVo;
import com.kt.giga.home.openapi.ghms.user.service.InitService;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 1. 26.
 */
@Controller
@RequestMapping("user")
public class InitController {
    
    /**
     * 앱 초기 실행 서비스
     */
    @Autowired
    private InitService initService;
    
    @Autowired
    private AppEncryptorVo appEncryptorVo;

    /**
     * 앱 초기 실행 API 매핑 메쏘드
     *  
     * @param userAgent	    App 구분 (AND, IOS)
     * @param AppInitKey
     * @return              앱 초기 실행 객체
     * @throws APIException
     */
    @RequestMapping(value={"/init"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AppInitVo init(
            @RequestHeader(value="userAgent", 	required=true)   String userAgent,
            @RequestBody AppInitKey key) throws APIException {

		if(StringUtils.isBlank(userAgent) || StringUtils.isBlank(key.getDeviceId()) || StringUtils.isBlank(key.getTelNo())
				|| StringUtils.isBlank(key.getAppVer()) || StringUtils.isBlank(key.getPnsRegId())) {
			throw new APIException(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST);
		}
        
        return initService.init(userAgent, key.getUnionSvcCode(), key.getDeviceId(), key.getTelNo(), key.getAppVer(), key.getPnsRegId(), key.getScreenSize());
    }

	/**
	 * 앱 초기 실행 프라퍼티 리로딩 API 매핑 메쏘드.
	 * 프라퍼티 메모리 갱신이 필요한 시점에 CMS 또는 외부 시스템에 의해 호출됨
	 *
	 * @param svcCode		서비스 코드
	 * @return				리로딩된 프라퍼티 맵
	 * @throws Exception
	 */
	@RequestMapping(value={"/init/reload"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public HashMap<String, String> reloadProperties(@RequestParam(value="svcCode", required=false) String svcCode) throws Exception {

		svcCode = org.apache.commons.lang3.StringUtils.defaultIfBlank(svcCode, "003");
		return initService.reloadProperties(svcCode);
	}

	/**
	 * 앱 암호화 키 제공 API
	 *
	 * @return				AppEncryptorVo
	 * @throws Exception
	 */
	@RequestMapping(value={"/encryptor/key"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public AppEncryptorVo encryptorKey() throws Exception {

		return appEncryptorVo;
	}
	
}
