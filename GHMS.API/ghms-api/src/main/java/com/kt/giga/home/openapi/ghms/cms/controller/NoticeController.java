/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.cms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.ghms.cms.domain.vo.NoticeVo;
import com.kt.giga.home.openapi.ghms.cms.service.NoticeService;
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
public class NoticeController {

	/** Logger */
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private NoticeService noticeService;  

	/**
	 * 일반공지 목록 조회
	 * 
	 * @param authToken		인증 토큰
	 * @param startSeq		검색 요청할 시작 인덱스
	 * @param limitCnt		start_seq 부터 불러올 갯수 (디폴트:전체)
	 * @return				DeviceMasterVo
	 * @throws APIException
	 */
	@RequestMapping(value={"notice"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public NoticeVo notice(
			@RequestHeader(value="authToken")	String authToken,
			@RequestParam(value="startSeq",		required=true) String startSeq,
			@RequestParam(value="limitCnt",		required=true) String limitCnt,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("cpCode"    , token.getUnitSvcCd());  // 단위 서비스 코드
        map.put("startSeq"  , startSeq            );
        map.put("limitCnt"  , limitCnt            );
        
        NoticeVo vo = noticeService.selectNoticeList(map);

		return vo;
	}

	/**
	 * PM 공지 목록 조회
	 * 
	 * @param authToken		인증 토큰
	 * @return				DeviceMasterVo
	 * @throws APIException
	 */
	@RequestMapping(value={"noticePm"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public NoticeVo noticePm(
			@RequestHeader(value="authToken")	String authToken,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cpCode", token.getUnitSvcCd());
		
		NoticeVo vo = noticeService.selectNoitcePmList(map);

		return vo;
	}

	/**
	 * 팝업 공지 목록 조회
	 * 
	 * @param authToken		인증 토큰
	 * @return				DeviceMasterVo
	 * @throws APIException
	 */
	@RequestMapping(value={"noticePopup"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public NoticeVo noticePopup(
			@RequestHeader(value="authToken")	String authToken,
			HttpServletResponse response) throws APIException {
		
		// 토큰 체크
		AuthToken token = AuthToken.checkLoginAuthToken(authToken);
		// 갱신된 토큰을 response header에 다시 넣어준다.
		response.setHeader("authToken", token.getToken());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cpCode", token.getUnitSvcCd());
		
		NoticeVo vo = noticeService.selectNoticePopupList(map);

		return vo;
	}
    
    /**
     * FAQ 리스트 조회
     * 
     * @param authToken
     * @param startSeq      검색 요청할 시작 인덱스
     * @param limitCnt      start_seq 부터 불러올 갯수 (defult : 전체)
     * @return
     * @throws APIException
     */
    @RequestMapping(value={"/faq"}, method=RequestMethod.GET, produces=CommonConstants.PRODUCES_JSON)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public NoticeVo getFaqList(
            @RequestHeader(value="authToken", required=true) String authToken,
            @RequestParam(value="startSeq", required=false, defaultValue="0") String startSeq,
            @RequestParam(value="limitCnt", required=false, defaultValue="0") String limitCnt,
            HttpServletResponse response
    ) throws APIException {
        
        // 토큰 체크
        AuthToken token = AuthToken.checkLoginAuthToken(authToken);
        // 갱신된 토큰을 response header에 다시 넣어준다.
        response.setHeader("authToken", token.getToken());
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cpCode", token.getUnitSvcCd());
        map.put("startSeq", startSeq);
        map.put("limitCnt", limitCnt);
        
        NoticeVo vo = noticeService.selectFaqList(map);
        
        return vo;
    }

}
