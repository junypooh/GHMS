/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.ghms.cms.domain.vo.CmsVo;
import com.kt.giga.home.openapi.ghms.cms.service.CmsService;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.token.AuthToken;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 1. 29.
 */
@Controller
@RequestMapping("cms")
public class CmsController {
    
    @Autowired
    private CmsService cmsService;
    
    
    /**
     * App 최신버전 조회
     * 
     * @param authToken
     * @param response
     * @return
     * @throws APIException
     */
    @RequestMapping(value={"/verAppLast"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CmsVo getVerAppLast(
            @RequestHeader(value="authToken", required=true) String authToken,
            HttpServletResponse response
    ) throws APIException {
        
        // 토큰 체크
        AuthToken token = AuthToken.checkLoginAuthToken(authToken);
        // 갱신된 토큰을 response header에 다시 넣어준다.
        response.setHeader("authToken", token.getToken());
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cpCode"        , token.getUnitSvcCd()  );  // 서비스 코드
        map.put("osCode"        , "1701"                );  // OS 구분코드 (안드로이드 : 1701)
        map.put("phoneTypeCode" , "1801"                );  // 단말형태 구분코드 (모바일 : 1801)
        
        CmsVo vo = cmsService.selectVerAppLast(map);
        
        return vo;
    }

    /**
     * 펌웨어 최신버전 조회
     * 
     * @param authToken
     * @param response
     * @return
     * @throws APIException
     */
    @RequestMapping(value={"/verFrmwrLast"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CmsVo getVerFrmwrLast(
            @RequestHeader(value="authToken", required=true) String authToken,
            HttpServletResponse response
    ) throws APIException {
        
        // 토큰 체크
        AuthToken token = AuthToken.checkLoginAuthToken(authToken);
        // 갱신된 토큰을 response header에 다시 넣어준다.
        response.setHeader("authToken", token.getToken());
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cpCode", token.getUnitSvcCd());          // 서비스 코드
        
        CmsVo vo = cmsService.selectVerFrmwrLast(map);
        
        return vo;
    }
    
    /**
     * 코치가이드 조회
     * 
     * @param authToken
     * @param verNum        단말의 현재 App 버전
     * @return
     * @throws APIException
     */
    @Deprecated
    @RequestMapping(value={"/guideCoach"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CmsVo> getGuideCoach(
            @RequestHeader(value="authToken", required=true) String authToken,
            @RequestParam(value="verNum", required=true) String verNum,
            HttpServletResponse response
    ) throws APIException {
        
        // 토큰 체크
        AuthToken token = AuthToken.checkLoginAuthToken(authToken);
        // 갱신된 토큰을 response header에 다시 넣어준다.
        response.setHeader("authToken", token.getToken());
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cpCode"    , token.getUnitSvcCd());
        map.put("fd_ver_num", verNum);
        
        List<CmsVo> list = cmsService.selectGuideCoach(map);
        
        return list;
    }
    
    /**
     * 웰컴가이드 조회
     * 
     * @param authToken
     * @param verNum        단말의 현재 App 버전
     * @return
     * @throws APIException
     */
    @Deprecated
    @RequestMapping(value={"/guideWelcome"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CmsVo getGuideWelcome(
            @RequestHeader(value="authToken", required=true) String authToken,
            @RequestParam(value="verNum", required=true) String verNum,
            HttpServletResponse response
    ) throws APIException {
        
        // 토큰 체크
        AuthToken token = AuthToken.checkLoginAuthToken(authToken);
        // 갱신된 토큰을 response header에 다시 넣어준다.
        response.setHeader("authToken", token.getToken());
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cpCode"    , token.getUnitSvcCd());
        map.put("fd_ver_num", verNum);
        
        CmsVo vo = cmsService.selectGuideWelcome(map);
        
        return vo;
    }
    
    /**
     * 디바이스 팁
     * (게이트웨이, 도어락, 가스벨브, 침입감지센서)
     * 
     * @param authToken
     * @param verNum        단말의 현재 App 버전
     * @param response
     * @return
     * @throws APIException
     */
    @Deprecated
    @RequestMapping(value={"/guideDevice"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CmsVo> getGuideDeviceTip(
            @RequestHeader(value="authToken", required=true) String authToken,
            @RequestParam(value="verNum", required=true) String verNum,
            HttpServletResponse response
    ) throws APIException {
        
        // 토큰 체크
        AuthToken token = AuthToken.checkLoginAuthToken(authToken);
        // 갱신된 토큰을 response header에 다시 넣어준다.
        response.setHeader("authToken", token.getToken());
        
        List<CmsVo> list = new ArrayList<CmsVo>();
        CmsVo vo = new CmsVo();
        vo.setVerNum(verNum);
        vo.setImgUrl_1("imgUrl_1");
        vo.setImgUrl_2("imgUrl_2");
        vo.setImgUrl_3("imgUrl_3");
        
        list.add(vo);
        
        return list;
    }
}
