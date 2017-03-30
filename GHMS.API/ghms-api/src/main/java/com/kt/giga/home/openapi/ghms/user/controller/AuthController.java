/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.domain.vo.BaseVo;
import com.kt.giga.home.openapi.ghms.common.token.AuthToken;
import com.kt.giga.home.openapi.ghms.user.domain.key.LoginKey;
import com.kt.giga.home.openapi.ghms.user.domain.vo.UserAuthVo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.UserVo;
import com.kt.giga.home.openapi.ghms.user.service.UserService;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;



/**
 * 사용자 인증, 로그인, 토큰갱신 처리 컨트롤러
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 1. 29.
 */
@Controller
@RequestMapping("user")
public class AuthController {
    
    /**
     * 사용자 서비스
     */
    @Autowired
    private UserService userService;

    /**
     * 사용자 인증, 로그인 API 매핑 메쏘드
     * 
     * @param authToken     초기 인증 토큰
     * @param userId        사용자 아이디
     * @param passwd        사용자 패스워드
     * @return              사용자 인증 객체
     * @throws APIException
     */
    @RequestMapping(value={"authentication"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserAuthVo auth(
            @RequestHeader(value="authToken")   String authToken,
            @RequestBody LoginKey key) throws APIException {

		if(StringUtils.isBlank(authToken) || StringUtils.isBlank(key.getUserId()) || StringUtils.isBlank(key.getPasswd())) {
			throw new APIException(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST);
		}

        return userService.auth(authToken, key.getUserId(), key.getPasswd(), key.getForceYn());
    }

    /**
     * 사용자 인증, 로그인 API 매핑 메쏘드(고도화용)
     * 
     * @param authToken     초기 인증 토큰
     * @param userId        사용자 아이디
     * @param passwd        사용자 패스워드
     * @return              사용자 인증 객체
     * @throws APIException
     */
    @RequestMapping(value={"authentication/upgrade"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserAuthVo upgradeAuth(
            @RequestHeader(value="authToken")   String authToken,
            @RequestBody LoginKey key) throws APIException {

		if(StringUtils.isBlank(authToken) || StringUtils.isBlank(key.getUserId()) || StringUtils.isBlank(key.getPasswd()) || StringUtils.isBlank(key.getWifiMacAddr())) {
			throw new APIException(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST);
		}

        return userService.authUpgrade(authToken, key.getUserId(), key.getPasswd(), key.getForceYn(), key.getWifiMacAddr());
    }

    /**
     * 임의 고객 인증
     * 
     * @param authToken     초기 인증 토큰
     * @param userId        사용자 아이디
     * @param passwd        사용자 패스워드
     * @return              사용자 인증 객체
     * @throws APIException
     */
    @RequestMapping(value={"anycustomer"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseVo anycustomer(
            @RequestHeader(value="authToken")   String authToken,
            @RequestBody LoginKey key,
            HttpServletResponse response) throws APIException {

		if(StringUtils.isBlank(authToken) || StringUtils.isBlank(key.getServiceNo()) || StringUtils.isBlank(key.getBirthDay())
				|| StringUtils.isBlank(key.getLunarSolarCd()) || StringUtils.isBlank(key.getGender())) {
			throw new APIException(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST);
		}
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());

        return userService.anyCustomerCheck(token, key);
    }
    
    /**
     * 인증토큰 재발급 API 매핑 메쏘드
     * 
     * @param authToken     로그인 인증 토큰
     * @return              사용자 객체
     * @throws APIException
     */
    @RequestMapping(value={"authentication/manager"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserVo refreshToken(@RequestHeader(value="authToken") String authToken) throws APIException {
        
        return userService.refreshAuthToken(authToken);
    }
    

}
