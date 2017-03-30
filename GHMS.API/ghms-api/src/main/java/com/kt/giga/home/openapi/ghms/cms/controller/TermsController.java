/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.cms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.ghms.cms.domain.vo.TermsVo;
import com.kt.giga.home.openapi.ghms.cms.service.TermsService;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.domain.vo.BaseVo;
import com.kt.giga.home.openapi.ghms.common.token.AuthToken;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;


/**
 * 약관 관련 처리 컨트롤러
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 1. 29.
 */
@Controller
@RequestMapping("cms")
public class TermsController {
    
    @Autowired
    private TermsService termsService;
    
    /**
     * 약관 및 개인정보 취급 방침 조회
     * 
     * @param authToken
     * @param response
     * @return
     * @throws APIException
     */
    @RequestMapping(value={"/terms"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<TermsVo> getTermsList(
            @RequestHeader(value="authToken", required=true) String authToken,
            HttpServletResponse response
    ) throws APIException {
        
        // 토큰 체크
        AuthToken token = AuthToken.checkLoginAuthToken(authToken);
        // 갱신된 토큰을 response header에 다시 넣어준다.
        response.setHeader("authToken", token.getToken());
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cpCode", token.getUnitSvcCd());       // 단위서비스코드
        map.put("mbr_seq", token.getUserNo());         // 회원일련번호
        
        List<TermsVo> termsList = termsService.selectTermsList(map);
        
        return termsList;
    }

    /**
     * 약관 및 개인정보 취급 방침 동의 등록
     * 
     * @param authToken
     * @param agreeList     약관id, 동의YN
     * @return
     * @throws APIException
     */
    @RequestMapping(value={"/terms/agreement"}, method=RequestMethod.PUT, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseVo setTermsAgree(
            @RequestHeader(value="authToken", required=true) String authToken,
            @RequestBody                   List<Map<String, Object>> agreeList,
            HttpServletResponse response
    ) throws APIException {
        
        // 토큰 체크
        AuthToken token = AuthToken.checkLoginAuthToken(authToken);
        // 갱신된 토큰을 response header에 다시 넣어준다.
        response.setHeader("authToken", token.getToken());
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cpCode"    , token.getUnitSvcCd() );  // 단위 서비스 코드
        map.put("mbr_seq"   , token.getUserNo()    );  // 회원 일련 번호
        map.put("agreeList" , agreeList            );
        
        BaseVo vo = termsService.insertTermsAgree(map);
        
        return vo;
    }

    /**
     * 14세 미만 법정대리인 본인인증 요청
     * 
     * @param authToken
     * @param identityInfo      본인인증 인증번호 발송 정보
     * @return
     * @throws APIException
     */
    @RequestMapping(value={"/terms/identityReq"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TermsVo identityReq(
            @RequestHeader(value="authToken", required=true) String authToken,
            @RequestBody                   HashMap<String, Object> identityInfo,
            HttpServletResponse response
    ) throws APIException {
        
        // 토큰 체크
        AuthToken token = AuthToken.checkLoginAuthToken(authToken);
        // 갱신된 토큰을 response header에 다시 넣어준다.
        response.setHeader("authToken", token.getToken());
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cpCode"        , token.getUnitSvcCd() );  // 서비스 코드
        map.put("mbr_seq"       , token.getUserNo()    );  //고객번호
        map.put("identityInfo"  , identityInfo         );  
        
        
        TermsVo vo = null;
		try {
			vo = termsService.identityReq(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return vo;
    }

    // 14세 미만 법정대리인 본인인증 검증
    /**
     * @param authToken
     * @param identityInfo      단말에서 SMS 수신받은 정보
     * @return
     * @throws APIException
     */
    @RequestMapping(value={"/terms/identityAudit"}, method=RequestMethod.POST, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TermsVo identityAudit(
            @RequestHeader(value="authToken", required=true) String authToken,
            @RequestBody                   HashMap<String, Object> identityInfo,
            HttpServletResponse response
    ) throws APIException {
        
        // 토큰 체크
        AuthToken token = AuthToken.checkLoginAuthToken(authToken);
        // 갱신된 토큰을 response header에 다시 넣어준다.
        response.setHeader("authToken", token.getToken());
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cpCode"        , token.getUnitSvcCd() );  // 서비스 코드
        map.put("mbr_seq"       , token.getUserNo()    );  //고객번호
        map.put("identityInfo"  , identityInfo         );  
        
        TermsVo vo = null;
		try {
			vo = termsService.identityAudit(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return vo;
    }
	
	//  약관 및 개인정보 취급 방침 동의 철회
	@RequestMapping(value={"/terms/retract"}, method=RequestMethod.PUT, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject setTermsRetract(
			@RequestHeader(value="authToken", required=true) String authToken,
            HttpServletResponse response
	) throws APIException {

        AuthToken token = AuthToken.checkLoginAuthToken(authToken);
        // 갱신된 토큰을 response header에 다시 넣어준다.
        response.setHeader("authToken", token.getToken());
		
		JSONObject jsonObj 	= new JSONObject();
		
		try{
			//입력 파라메타를 db 조회 대상으로 치환
			Map<String, String> map 	= new HashMap<String, String>();
			map.put("cpCode"	, token.getUnitSvcCd()	);	// 서비스 코드
			map.put("mbr_seq"	, token.getUserNo()	);	//고객번호

			termsService.setTermsRetract(map);
			
			// 앱 접속 정보 삭제
//			userService.deleteAppUser(loginAuthToken.getSvcCode(), loginAuthToken.getUserNoLong(), loginAuthToken.getDeviceId());
			jsonObj.put("resultCode"	, "0"	);
		}catch (Exception e){
			jsonObj.put("resultCode"	, "-1"	);
		}

		return jsonObj;
	}

}
