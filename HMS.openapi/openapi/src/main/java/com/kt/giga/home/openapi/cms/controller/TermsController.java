package com.kt.giga.home.openapi.cms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kt.giga.home.openapi.cms.service.KmcService;
import com.kt.giga.home.openapi.cms.service.TermsService;
import com.kt.giga.home.openapi.common.AuthToken;
import com.kt.giga.home.openapi.hcam.service.UserService;
import com.kt.giga.home.openapi.health.util.HealthSvcCode;
import com.kt.giga.home.openapi.service.UserServiceOld;

/**
 * 약관 관련 처리 컨트롤러
 *
 * @author 조상현
 *
 */
@Controller
@RequestMapping("/")
public class TermsController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String PRODUCES_JSON = "application/json; charset=UTF-8";

//	@Autowired
//	private UserServiceOld userService;

	@Autowired
	private UserService userService;

	@Autowired
	private TermsService termsService;

	@Autowired
	private KmcService kmcService;

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

	// 약관 및 개인정보 취급 방침 조회
	@RequestMapping(value={"/terms"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONArray getTermsList(
			@RequestHeader(value="authToken", required=true) String authToken
	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		AuthToken loginAuthToken 	= userService.checkLoginAuthToken(authToken);

		System.out.println("getSvcCode ========================== " + loginAuthToken.getSvcCode().toString() );

		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"	, healthSvcCode.GetUnitSvcCd(loginAuthToken.getSvcCode())	);	// 서비스 코드
		map.put("mbr_seq"	, loginAuthToken.getUserNo()	);	//고객번호
//		map.put("userId"	, loginAuthToken.getUserId() 	);	//접속 회원 ID

		JSONArray termsList	= termsService.getTermsList(map);

		return termsList;
	}

	//  약관 및 개인정보 취급 방침 동의 등록
	@RequestMapping(value={"/terms/agreement"}, method=RequestMethod.PUT, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String setTermsAgree(
			@RequestHeader(value="authToken", required=true) String authToken
			,@RequestBody					List<HashMap<String, Object>> agreeList //약관id, 동의YN
	) throws Exception {

		//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		AuthToken loginAuthToken 	= userService.checkLoginAuthToken(authToken);

		try{
			logger.debug("================ /terms/agreement controller ================");

			logger.debug("cpCode ================ " + healthSvcCode.GetUnitSvcCd(loginAuthToken.getSvcCode()) );
			logger.debug("mbr_seq ================ " + loginAuthToken.getUserNo().toString() );
			logger.debug("mbr_seq long ================ " + loginAuthToken.getUserNoLong() );
			logger.debug("agreeList ================ " + agreeList);

		}catch(Exception e){
			//todo
		}
		
		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"	, healthSvcCode.GetUnitSvcCd(loginAuthToken.getSvcCode())	);	// 서비스 코드
		map.put("mbr_seq"	, loginAuthToken.getUserNo()	);	//고객번호
//		map.put("userId"	, loginAuthToken.getUserId() 	);	//접속 회원 ID (mbr_seq)
		map.put("agreeList"	, agreeList 					);

		termsService.setTermsAgree(map);

		return "{}";
	}
	
	//  약관 및 개인정보 취급 방침 동의 철회
	@RequestMapping(value={"/terms/retract"}, method=RequestMethod.PUT, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject setTermsRetract(
			@RequestHeader(value="authToken", required=true) String authToken
	) throws Exception {

		//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		AuthToken loginAuthToken 	= userService.checkLoginAuthToken(authToken);
		
		JSONObject jsonObj 	= new JSONObject();

		try{
			logger.debug("================ /terms/retract ================");

			logger.debug("cpCode ================ " + healthSvcCode.GetUnitSvcCd(loginAuthToken.getSvcCode()) );
			logger.debug("mbr_seq ================ " + loginAuthToken.getUserNo().toString() );
			logger.debug("mbr_seq long ================ " + loginAuthToken.getUserNoLong() );

		}catch(Exception e){
			//todo
		}
		
		try{
			//입력 파라메타를 db 조회 대상으로 치환
			Map<String, Object> map 	= new HashMap<String, Object>();
			map.put("cpCode"	, healthSvcCode.GetUnitSvcCd(loginAuthToken.getSvcCode())	);	// 서비스 코드
			map.put("mbr_seq"	, loginAuthToken.getUserNo()	);	//고객번호
//			map.put("userId"	, loginAuthToken.getUserId() 	);	//접속 회원 ID (mbr_seq)

			termsService.setTermsRetract(map);
			
			// 앱 접속 정보 삭제
			userService.deleteAppUser(loginAuthToken.getSvcCode(), loginAuthToken.getUserNoLong(), loginAuthToken.getDeviceId());
			
			jsonObj.put("resultCode"	, "0"	);
		
		}catch (Exception e){
			jsonObj.put("resultCode"	, "-1"	);
		}

		return jsonObj;
	}

//	//  14세 미만 보호자 동의 등록
//	@RequestMapping(value={"/terms/parentsAgree"}, method=RequestMethod.PUT, produces=PRODUCES_JSON)
//	@ResponseStatus(HttpStatus.OK)
//	@ResponseBody
//	public String setParentsAgree(
//			@RequestHeader(value="authToken", required=true) String authToken
//			,@RequestBody					HashMap<String, Object> agreeInfo //보호자 동의 정보
//	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
//
//		//입력 파라메타를 db 조회 대상으로 치환
//		Map<String, Object> map 	= new HashMap<String, Object>();
//		map.put("cpCode"	, loginAuthToken.getSvcCode()	);	//홈모니터링 서비스 코드
//		map.put("mbr_seq"	, loginAuthToken.getUserNo() 	);	//고객번호
//		map.put("userId"	, loginAuthToken.getUserId() 	);	//접속 회원 ID
//		map.put("agreeInfo"	, agreeInfo 					);
//
//		termsService.setParentsAgree(map);
//
//		return "{}";
//	}

	// 법정대리인 본인확인 및 인증번호 발송
	@RequestMapping(value={"/terms/identityReq"}, method=RequestMethod.POST, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject identityReq(
			@RequestHeader(value="authToken", required=true) String authToken
			,@RequestBody					HashMap<String, Object> identityInfo //본인인증 인증번호 발송 정보
	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		AuthToken loginAuthToken 	= userService.checkLoginAuthToken(authToken);

		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"		, healthSvcCode.GetUnitSvcCd(loginAuthToken.getSvcCode())	);	// 서비스 코드
		map.put("mbr_seq"		, loginAuthToken.getUserNo()	);	//고객번호
//		map.put("userId"		, loginAuthToken.getUserId() 	);	//접속 회원 ID
		map.put("identityInfo"	, identityInfo 					);

		JSONObject jsonObj		= kmcService.identityReq(map);

		return jsonObj;
	}

	// 본인확인 인증번호 검증 ,14세 미만 법정대리인 동의 등록
	@RequestMapping(value={"/terms/identityAudit"}, method=RequestMethod.POST, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject identityAudit(
			@RequestHeader(value="authToken", required=true) String authToken
			,@RequestBody					HashMap<String, Object> identityInfo //단말에서 SMS 수신받은 정보
	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		AuthToken loginAuthToken 	= userService.checkLoginAuthToken(authToken);

		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"		, healthSvcCode.GetUnitSvcCd(loginAuthToken.getSvcCode())	);	// 서비스 코드
		map.put("mbr_seq"		, loginAuthToken.getUserNo()	);	//고객번호
//		map.put("userId"		, loginAuthToken.getUserId() 	);	//접속 회원 ID
		map.put("identityInfo"	, identityInfo 					);

		JSONObject jsonObj		= kmcService.identityAudit(map);

		return jsonObj;
	}

}
