package com.kt.giga.home.openapi.health.paring.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
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

import com.kt.giga.home.openapi.health.paring.service.PairingService;
import com.kt.giga.home.openapi.health.user.service.HealthUserService;
import com.kt.giga.home.openapi.health.util.HealthSvcCode;
import com.kt.giga.home.openapi.util.AuthToken;

/**
 * 페어링 관련 처리 컨트롤러
 * 
 * @author 조상현
 *
 */
@Controller
@RequestMapping("/")
public class PairingController {

	private static final String PRODUCES_JSON = "application/json; charset=UTF-8";
	
	@Autowired
	private PairingService pairingService;
	
	@Autowired
	private HealthUserService healthUserService;
	
	// 조회 및 휴대폰 인증 요청
	@RequestMapping(value={"/devices/pairing/auth"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject getPairingAuth(
//			@RequestHeader(value="authToken", required=true) String authToken
			@RequestParam(value="svcCode", required=true) String  svcCode 	// 서비스 구분 코드
			,@RequestParam(value="telNo", required=true) String telNo 		// 휴대폰 번호
			,@RequestParam(value="said", required=true) String said 		// 올레TV 계약 ID

	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		HealthSvcCode healthSvcCode	= new HealthSvcCode();
		
		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"	, healthSvcCode.GetUnitSvcCd(svcCode)	);
		map.put("telNo"		, telNo					 				);
		map.put("said"		, said					 				);
		
		JSONObject jsonObj	= pairingService.getPairingAuth(map);
		
		return jsonObj;
	}
	
	// 페어링 - 등록 요청
	@RequestMapping(value={"/devices/pairing/registration"}, method=RequestMethod.POST, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject getPairingAudit(
//			@RequestHeader(value="authToken", required=true) String authToken
//			@RequestBody					HashMap<String, Object> jsonInfo //svcCode, telNo, said, appId, transacId, otpCode
			@RequestBody					JSONObject params

	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		HealthSvcCode healthSvcCode	= new HealthSvcCode();
		
		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"	,  healthSvcCode.GetUnitSvcCd(	params.get("svcCode").toString()	));
		map.put("telNo"		,  params.get("telNo"	).toString()	);
		map.put("said"		,  params.get("said"	).toString()	);
		map.put("appId"		,  params.get("appId"	).toString()	);
		map.put("transacId"	,  params.get("transacId").toString()	);
		map.put("otpCode"	,  params.get("otpCode"	).toString()	);

		JSONObject jsonObj	= pairingService.getPairingAudit(map);
		
		return jsonObj;
	}
	// 페어링 - 등록 요청(동일기능 get메소드 지원)
	@RequestMapping(value={"/devices/pairing/registration/get"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject getPairingAuditGet(
			@RequestParam 		HashMap<String, Object> params
			
	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);
		HealthSvcCode healthSvcCode	= new HealthSvcCode();
		
		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"	,  healthSvcCode.GetUnitSvcCd(	params.get("svcCode").toString()	));
		map.put("telNo"		,  params.get("telNo"	).toString()	);
		map.put("said"		,  params.get("said"	).toString()	);
		map.put("appId"		,  params.get("appId"	).toString()	);
		map.put("transacId"	,  params.get("transacId").toString()	);
		map.put("otpCode"	,  params.get("otpCode"	).toString()	);

		JSONObject jsonObj	= pairingService.getPairingAudit(map);
		
		return jsonObj;
	}

	
	// 페어링 - 올레 TV 이름 수정
	@RequestMapping(value={"/devices/pairing"}, method=RequestMethod.PUT, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject modifyPairing(
			@RequestHeader(value="authToken", required=true) String authToken	//svcCode, telNo
			,@RequestBody					HashMap<String, Object> jsonInfo 	//said, stbNickNm

	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);//mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, deviceId, telNo 있다고 가정
		AuthToken auth 				= healthUserService.checkLoginAuthToken(authToken);
		String svcCode				= auth.getSvcCode();
		HealthSvcCode healthSvcCode	= new HealthSvcCode();

		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("otv_svc_cont_id"	, jsonInfo.get("said")					);	// OTV 서비스 계약 아이디
		map.put("mbr_seq"			, auth.getUserNo() 						);	// 회원 일련번호
//		map.put("mbr_seq"			, auth.getUserId() 						);	// 회원 일련번호
		map.put("dstr_cd"			, healthSvcCode.GetDstrCd(svcCode) 		);	// 지역코드
		map.put("svc_theme_cd"		, healthSvcCode.GetSvcThemeCd(svcCode)	);	// 서비스 테마코드
		map.put("unit_svc_cd"		, healthSvcCode.GetUnitSvcCd(svcCode)	);	// 단위 서비스 코드
		
//		map.put("telNo"				, loginAuthToken.getTelNo()	 			);	// 전화번호
		map.put("stb_nick_nm"		, jsonInfo.get("stbNickNm")				);	// STB(셋탑박스) 닉네임
		
		JSONObject jsonObj			= pairingService.modifyPairing(map);
		
		return jsonObj;
	}
	
	// 페어링 - 페어링된 STB 리스트 조회
	@RequestMapping(value={"/devices/pairing/stb"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject getPairingStbList(
			@RequestHeader(value="authToken", required=true) String authToken	//svcCode, telNo

	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);//mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, telNo 있다고 가정
		AuthToken auth 				= healthUserService.checkLoginAuthToken(authToken);
		String svcCode				= auth.getSvcCode();
		HealthSvcCode healthSvcCode	= new HealthSvcCode();

		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("mbr_seq"			, auth.getUserNo()			 			);	// 회원 일련번호
//		map.put("mbr_seq"			, auth.getUserId()			 			);	// 회원 일련번호
		map.put("dstr_cd"			, healthSvcCode.GetDstrCd(svcCode) 		);	// 지역코드
		map.put("svc_theme_cd"		, healthSvcCode.GetSvcThemeCd(svcCode)	);	// 서비스 테마코드
		map.put("unit_svc_cd"		, healthSvcCode.GetUnitSvcCd(svcCode)	);	// 단위 서비스 코드
		map.put("tel_no"			, auth.getTelNo()	);	// 전화번호
		
		JSONObject jsonObj			= pairingService.getPairingStbList(map);
		
		return jsonObj;
	}
	
	// 페어링 - 페어링된 App 리스트 조회
	@RequestMapping(value={"/devices/pairing/app"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public JSONObject getPairingAppList(
			@RequestParam(value="svcCode", required=true) String  svcCode 	// 서비스 구분 코드
			,@RequestParam(value="telNo", required=false, defaultValue="") String telNo 		// 휴대폰 번호
			,@RequestParam(value="said", required=true) String said 		// 올레TV 계약 ID

	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);//mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, telNo 있다고 가정
		HealthSvcCode healthSvcCode	= new HealthSvcCode();

		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"		, healthSvcCode.GetUnitSvcCd(svcCode)	);
		map.put("dstr_cd"		, healthSvcCode.GetDstrCd(svcCode)		);
		map.put("svc_theme_cd"	, healthSvcCode.GetSvcThemeCd(svcCode)	);
		map.put("telNo"			, telNo					 				);
		map.put("said"			, said					 				);
		
		JSONObject jsonObj			= pairingService.getPairingAppList(map);
		
		return jsonObj;
	}
	
	// 페어링 - 페어링된 STB 삭제
	@RequestMapping(value={"/devices/pairing/stb"}, method=RequestMethod.DELETE, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delPairingStb(
			@RequestHeader(value="authToken", required=true) String authToken	// svcCode, telNo
			,@RequestParam(value="said", required=true) String said 			// 올레TV 계약 ID

	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);//mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, telNo 있다고 가정
		AuthToken auth 				= healthUserService.checkLoginAuthToken(authToken);
		String svcCode				= auth.getSvcCode();
		HealthSvcCode healthSvcCode	= new HealthSvcCode();

		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("mbr_seq"			, auth.getUserNo()			 			);	// 회원 일련번호
//		map.put("mbr_seq"			, auth.getUserId()			 			);	// 회원 일련번호
		map.put("dstr_cd"			, healthSvcCode.GetDstrCd(svcCode) 		);	// 지역코드
		map.put("svc_theme_cd"		, healthSvcCode.GetSvcThemeCd(svcCode)	);	// 서비스 테마코드
		map.put("unit_svc_cd"		, healthSvcCode.GetUnitSvcCd(svcCode)	);	// 단위 서비스 코드
		map.put("otv_svc_cont_id"	, said									);	// 계약아이디
		map.put("telNo"				, auth.getTelNo()						);	// 전화번호
		
		pairingService.delPairingStb(map);
		
	}
	
	// 페어링 - 페어링된 App 삭제
	@RequestMapping(value={"/devices/pairing/app"}, method=RequestMethod.DELETE, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delPairingApp(
			@RequestParam(value="svcCode", required=true) String  svcCode 	// 서비스 구분 코드
			,@RequestParam(value="telNo", required=true) String telNo 		// 휴대폰 번호
			,@RequestParam(value="said", required=true) String said 		// 올레TV 계약 ID

	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);//mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, telNo 있다고 가정
		HealthSvcCode healthSvcCode	= new HealthSvcCode();

		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"		, healthSvcCode.GetUnitSvcCd(svcCode)	);
		map.put("dstr_cd"		, healthSvcCode.GetDstrCd(svcCode)		);
		map.put("svc_theme_cd"	, healthSvcCode.GetSvcThemeCd(svcCode)	);
		map.put("telNo"			, telNo					 				);
		map.put("said"			, said					 				);
		
		pairingService.delPairingApp(map);
		
	}

	// 페어링 - 페어링된 App 삭제 (동일기능 get메소드 지원)
	@RequestMapping(value={"/devices/pairing/app/delete"}, method=RequestMethod.GET, produces=PRODUCES_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delPairingAppGet(
			@RequestParam(value="svcCode", required=true) String  svcCode 	// 서비스 구분 코드
			,@RequestParam(value="telNo", required=true) String telNo 		// 휴대폰 번호
			,@RequestParam(value="said", required=true) String said 		// 올레TV 계약 ID

	) throws Exception {
//		AuthToken loginAuthToken 	= checkLoginAuthToken(authToken);//mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, telNo 있다고 가정
		HealthSvcCode healthSvcCode	= new HealthSvcCode();

		//입력 파라메타를 db 조회 대상으로 치환
		Map<String, Object> map 	= new HashMap<String, Object>();
		map.put("cpCode"		, healthSvcCode.GetUnitSvcCd(svcCode)	);
		map.put("dstr_cd"		, healthSvcCode.GetDstrCd(svcCode)		);
		map.put("svc_theme_cd"	, healthSvcCode.GetSvcThemeCd(svcCode)	);
		map.put("telNo"			, telNo					 				);
		map.put("said"			, said					 				);
		
		pairingService.delPairingApp(map);
		
	}

}
