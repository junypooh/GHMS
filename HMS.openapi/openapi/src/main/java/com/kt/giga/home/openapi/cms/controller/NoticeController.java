package com.kt.giga.home.openapi.cms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.cms.service.InitSyncService;
import com.kt.giga.home.openapi.cms.service.NoticeService;
import com.kt.giga.home.openapi.common.AuthToken;
import com.kt.giga.home.openapi.health.util.HealthSvcCode;
import com.kt.giga.home.openapi.service.UserServiceOld;

/**
 * 공지사항 관련 처리 컨트롤러
 * 
 * @author 조상현
 *
 */
@Controller
@RequestMapping("/")
public class NoticeController {

	private static final String PRODUCES_JSON = "application/json; charset=UTF-8";
	
	@Autowired
	private UserServiceOld userService;
	
	@Autowired
	private NoticeService noticeService;

	@Autowired
	private InitSyncService initSyncService;

	HealthSvcCode healthSvcCode	= new HealthSvcCode();
	
//	private AuthToken checkLoginAuthToken(String authToken) throws Exception {
//		
//		AuthToken loginAuthToken = AuthToken.decodeAuthToken(authToken);
//		
//		if(!loginAuthToken.isValidLoginToken()){
//			throw new APIException("Invalid InitAuthToken", HttpStatus.UNAUTHORIZED);
//		}
//
//		return loginAuthToken;
//	}
	
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    Date date 			= new Date();
    String now 			= df.format(date);
	
	// 일반공지 리스트 조회
	@RequestMapping(value={"/notice"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject getNoticeList(
			@RequestHeader(value="authToken", required=true) String authToken
			,@RequestParam(value="startSeq", required=false, defaultValue="0") String startSeq 	//검색 요청할 시작 인덱스
			,@RequestParam(value="limitCnt", required=false, defaultValue="0") String limitCnt	//start_seq 부터 불러올 개수 (요청 값 없을 시 전체 검색)

	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		AuthToken loginAuthToken 	= userService.checkLoginAuthToken(authToken);
		
		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		
		map.put("cpCode"	, healthSvcCode.GetUnitSvcCd(loginAuthToken.getSvcCode())	);	// 서비스 코드
		map.put("startSeq"	, startSeq					 	);
		map.put("limitCnt"	, limitCnt					 	);
		
		JSONObject noticeObj	= noticeService.getNoticeList(map);

		return noticeObj;
	}

	// FAQ 리스트 조회
	@RequestMapping(value={"/faq"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject getFaqList(
			@RequestHeader(value="authToken", required=true) String authToken
			,@RequestParam(value="startSeq", required=false, defaultValue="0") String startSeq 	//검색 요청할 시작 인덱스
			,@RequestParam(value="limitCnt", required=false, defaultValue="0") String limitCnt	//start_seq 부터 불러올 개수 (요청 값 없을 시 전체 검색)

	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		AuthToken loginAuthToken 	= userService.checkLoginAuthToken(authToken);
		
		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"	, healthSvcCode.GetUnitSvcCd(loginAuthToken.getSvcCode())	);	// 서비스 코드
		map.put("startSeq"	, startSeq					 	);
		map.put("limitCnt"	, limitCnt					 	);
		
		JSONObject faqObj	= noticeService.getFaqList(map);
		
		return faqObj;
	}

	// PM공지 리스트 조회
	@RequestMapping(value={"/noticePm"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject getNoticePmList(
			@RequestHeader(value="authToken", required=true) String authToken
	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		AuthToken loginAuthToken 	= userService.checkLoginAuthToken(authToken);
		
		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"			, healthSvcCode.GetUnitSvcCd(loginAuthToken.getSvcCode())	);	// 서비스 코드
//		map.put("now"				, now	);	//현재시간 (현재시간을 넣으면 시작/종료시간 조건비교함)
		
		JSONObject noticePmObj	= noticeService.getNoticePmList(map);
		
		return noticePmObj;
	}
	
	//팝업공지 리스트 조회
	@RequestMapping(value={"/noticePopup"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject getNoticePopupList(
			@RequestHeader(value="authToken", required=true) String authToken
	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		AuthToken loginAuthToken 	= userService.checkLoginAuthToken(authToken);
		
		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"			, healthSvcCode.GetUnitSvcCd(loginAuthToken.getSvcCode())	);	// 서비스 코드
		
		JSONObject noticePopupObj	= noticeService.getNoticePopupList(map);
		
		return noticePopupObj;
	}
		
	//코치가이드 조회
	@RequestMapping(value={"/guideCoach"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject getGuideCoach(
			@RequestHeader(value="authToken", required=true) String authToken
			,@RequestParam(value="verNum", required=true) String verNum 		//단말의 현재 App 버전
	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		AuthToken loginAuthToken 	= userService.checkLoginAuthToken(authToken);
		
		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"			, healthSvcCode.GetUnitSvcCd(loginAuthToken.getSvcCode())	);	// 서비스 코드
		map.put("fd_ver_num"		, verNum						);	
		
		JSONObject guideObj	= noticeService.getGuideCoach(map);
		
		return guideObj;
	}
		
	//웰컴가이드 조회
	@RequestMapping(value={"/guideWelcome"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject getGuideWelcome(
			@RequestHeader(value="authToken", required=true) String authToken
			,@RequestParam(value="verNum", required=true) String verNum 		//단말의 현재 App 버전
	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		AuthToken loginAuthToken 	= userService.checkLoginAuthToken(authToken);
		
		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"			, healthSvcCode.GetUnitSvcCd(loginAuthToken.getSvcCode())	);	// 서비스 코드
		map.put("fd_ver_num"		, verNum						);	
		
		JSONObject guideObj	= noticeService.getGuideWelcome(map);
		
		return guideObj;
	}
	
	//카메라팁 조회
	@RequestMapping(value={"/guideCamera"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject getGuideCamera(
			@RequestHeader(value="authToken", required=true) String authToken
			,@RequestParam(value="verNum", required=true) String verNum 		//단말의 현재 App 버전
	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		AuthToken loginAuthToken 	= userService.checkLoginAuthToken(authToken);
		
		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"			, healthSvcCode.GetUnitSvcCd(loginAuthToken.getSvcCode())	);	// 서비스 코드
		map.put("fd_ver_num"		, verNum						);	
		
		JSONObject guideObj	= noticeService.getGuideCamera(map);
		
		return guideObj;
	}
		
		
}
