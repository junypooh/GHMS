package com.kt.giga.home.openapi.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.giga.home.openapi.common.AuthToken;
import com.kt.giga.home.openapi.dao.UserDaoOld;
import com.kt.giga.home.openapi.domain.AppInit;
import com.kt.giga.home.openapi.domain.AppUser;
import com.kt.giga.home.openapi.domain.NewAuthToken;
import com.kt.giga.home.openapi.domain.UserAuth;

/**
 * 초기실행, 인증/로그인, 토큰갱신등 회원관련 처리 서비스
 *
 * @author
 *
 */
@Service("oldUserService")
public class UserServiceOld {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDaoOld userDao;


	static String dstrCd = "001";     //지역코드
	static String svcThemeCd = "HIT"; //서비스테마코드
	// 4. TODO 공지사항 옵션 하드코딩. 향후 CMS 연동 필요
	String noticeOption = "0";

	// 5. TODO 약관 동의여부 옵션 하드코딩. 향후 CMS 연동 필요
	String termsYn = "N";

	// 6. TODO 보호자 동의여부 옵션 하드코딩. 향후 CMS 연동 필요
	String kidsCheckOption = "0";

	// 7. TODO 청약 상태 조회
	String subsYn = "Y";


	/**
	 * 앱 초기실행 처리 메쏘드
	 *
	 * @param deviceId		단말(앱) 디바이스 아이디
	 * @param telNo			전화번호
	 * @param appVer		단말에 설치된 앱 버전
	 * @param pnsRegId		PNS 등록 아이디
	 * @return				AppInit 객체
	 */
	public AppInit init(String deviceId, String telNo, String appVer, String pnsRegId, String svcCode)
			throws Exception {
		// TODO 앱 버전 처리 하드코딩. 향후 CMS 연동 필요
		String newAppVer = "1.0";
		String appUpOption = StringUtils.equals(appVer, newAppVer) ? "0" : "1";

		// TODO 공지사항 옵션 하드코딩. 향후 CMS 연동 필요
		String noticeOption = "1";

		/*//공지사항 조회 나중에 정책 정의 후 수정
		Map<String, Object> map 	= new HashMap<String, Object>();
		//map.put("cpCode"	, "002" 						);	//홈모니터링 서비스 코드
		map.put("cpCode"	, svcCode 						);	//홈모니터링 서비스 코드
		map.put("startSeq"	, "0"					 	);
		map.put("limitCnt"	, "1"					 	);

		try {
			List<Notice> noticeList		= noticeDao.getNoticeList(map);

			if(noticeList.size() > 0){
				noticeOption = "1";
				noticeUrl = noticeList.get(0).getFd_contents();
				System.out.println("noticeUrl>>>> " + noticeUrl);
			}
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
*/

		// 공지사항 URL 향후 연동
		String noticeUrl = "http://www.kthcorp.com/공지사항";

		// 가입안내 URL 향후 연동
		String joinGuideUrl = "http://www.kthcorp.com/가입안내";

		// 웰컴강이드 URL 향후 연동
		String welcomeGuideUrl = "http://www.kthcorp.com/웰컴가이드";

		// 유클라우드 URL 향후 연동
		String ucloudJoinGuideUrl = "http://www.kthcorp.com/유클아우드가입안내";

		// 코치가이드 URL 향후 연동
		String coachGuideUrl = "http://www.kthcorp.com/코치가이드";

		AuthToken authToken = AuthToken.encodeAuthToken(deviceId, telNo, pnsRegId, "", "", svcCode);

		AppInit appInit = new AppInit();
		appInit.setAuthToken(authToken.getToken());
		appInit.setDeviceId(deviceId);
		appInit.setNewAppVer(newAppVer);
		appInit.setAppUpOption(appUpOption);
		appInit.setNoticeOption(noticeOption);
		appInit.setJoinGuideUrl(joinGuideUrl);
		appInit.setNoticeUrl(noticeUrl);
		appInit.setWelcomeGuideUrl(welcomeGuideUrl);
		appInit.setUcloudJoinGuideUrl(ucloudJoinGuideUrl);
		appInit.setCoachGuideUrl(coachGuideUrl);

		return appInit;
	}

	/**
	 * 아이디/패스워드 인증 메쏘드
	 *
	 * @param authToken		앱 초기실행시 발급한 인증 토큰
	 * @param userId		아이디
	 * @param passwd		패스워드
	 * @return				UserAuh 객체
	 * @throws Exception
	 */
	public UserAuth auth(String authToken, String userId, String passwd) throws Exception {
		log.info("------------------------------AUTH LogIn------------------------------");
		log.info("userId>> " + userId + " passwd>> " + passwd);
		log.info("authToken>> " + authToken );

		// 1. AuthToken 디코딩
		AuthToken initAuthToken = checkInitAuthToken(authToken);

/*		// 2. TODO KT인프라 연동 서버 SDP 인증 처리, 인증 실패한 경우 바로 익셉션 처리 --> 회원정보 체크 부분에서 처리
		checkSDPAuth(userId, passwd);*/

		// 3. 회원정보 체크, 신규 회원의 경우 회원 테이블에 등록
		AppUser userBase = checkUserBase(userId, passwd, initAuthToken);

		System.out.println("svcCode>> " + userBase.getSvcCode());
		System.out.println("mbrsSeq>> " + Integer.toString(userBase.getMbrsSeq()));

		AppUser appUser = new AppUser();
		appUser.setStrMbrsSeq(Integer.toString(userBase.getMbrsSeq()));
		appUser.setSvcCode(userBase.getSvcCode());

		JSONArray termsList	= userDao.getTermsYn(appUser);
		System.out.println("termsList>> " + termsList.size());
		if(termsList.size() > 0){
			termsYn = "Y";
		}

		int intAge = Integer.parseInt(userBase.getAge());
		if(intAge < 14){
			int chkParAgr = userDao.getParentsAgree(appUser);
			System.out.println(" chkParAre>> " + chkParAgr);
			if(chkParAgr < 1){
				kidsCheckOption = "1";
			}
		}

		// 8. TODO 동시접속 로그인 처리
		AuthToken newAuthToken = login(userBase);

		UserAuth userAuth = new UserAuth();

		userAuth.setNewAuthToken(newAuthToken == null ? "" : newAuthToken.getToken());
		userAuth.setNoticeOption(noticeOption);
		userAuth.setTermsYn(termsYn);
		userAuth.setKidsCheckOption(kidsCheckOption);
		userAuth.setLoginResult(newAuthToken == null ? "2" : "1");
		userAuth.setAge(userBase.getAge());
		userAuth.setSubsYn(subsYn);


		return userAuth;
	}

	/**
	 * 아이디/패스워드 인증 메쏘드
	 *
	 * @param authToken		앱 초기실행시 발급한 인증 토큰
	 * @param telNo		         전화번호
	 * @return				UserAuh 객체
	 * @throws Exception
	 */
	public UserAuth helthAuth(String authToken) throws Exception {
		log.info("------------------------------helthAuth LogIn------------------------------");

		AuthToken initAuthToken = checkInitAuthToken(authToken);
		String connTermlId = initAuthToken.getDeviceId();
		String telNo = initAuthToken.getTelNo();
		String pnsRegId = initAuthToken.getPnsRegId();
		String unitSvcCd = "002";
		String svcCode = initAuthToken.getSvcCode();
		// 약관 동의여부 옵션 하드코딩. 향후 CMS 연동 필요
		String termsYn = "N";
		String loginResult = "1";
		// TODO 공지사항 옵션 하드코딩. 향후 CMS 연동 필요
		String noticeOption = "0";

		UserAuth userAuth = new UserAuth();
		AppUser appUserBase = userDao.getUserBase(telNo);

		AuthToken loginAuthToken = AuthToken.encodeAuthToken(connTermlId, telNo, pnsRegId, "", "", svcCode);

		try{
			if(appUserBase == null){
				appUserBase = new AppUser();

				int mbrsSeq = userDao.getMbrsSeq();
				appUserBase.setMbrsSeq(mbrsSeq);
				appUserBase.setCredentialId("");
				appUserBase.setUserId(telNo);
				appUserBase.setDstrCd(dstrCd);
				appUserBase.setSvcThemeCd(svcThemeCd);
				appUserBase.setUnitSvcCd(unitSvcCd);
				appUserBase.setPnsRegId(pnsRegId);
				appUserBase.setConnTermlId(connTermlId);

				userDao.insertMbrsBas(appUserBase);
				userDao.insertSvcMbrsRel(appUserBase);
				userDao.insertSvcTgtAccsBas(appUserBase);
			}else{
				appUserBase.setUserId(telNo);
				appUserBase.setDstrCd(dstrCd);
				appUserBase.setSvcThemeCd(svcThemeCd);
				appUserBase.setUnitSvcCd(unitSvcCd);
				appUserBase.setPnsRegId(pnsRegId);
				appUserBase.setConnTermlId(connTermlId);
			}
		}catch (DataAccessException e){
			log.error(e.getMessage(), e);

			userAuth.setTermsYn("");
			userAuth.setNewAuthToken("");
			userAuth.setLoginResult("2");
			userAuth.setNoticeOption("");
			userAuth.setTermsYn(termsYn);

			throw new APIException("Init Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		userAuth.setNewAuthToken(loginAuthToken == null ? "" : loginAuthToken.getToken());
		userAuth.setLoginResult(loginResult);
		userAuth.setNoticeOption(noticeOption);
		userAuth.setLoginResult(loginAuthToken == null ? "0" : "1");
		userAuth.setTermsYn(termsYn);

		return userAuth;
	}


	/**
	 * 인증토큰 갱신 메쏘드
	 *
	 * @param authToken		인증토큰
	 * @return				신규 발급한 인증 토큰
	 * @throws Exception
	 */
	public NewAuthToken refreshAuthToken(String authToken) throws Exception {

		AuthToken loginAuthToken = checkLoginAuthToken(authToken);
		AuthToken newLoginAuthToken = AuthToken.refresh(authToken);

		NewAuthToken newAuthToken = new NewAuthToken();
		newAuthToken.setNewAuthToken(newLoginAuthToken.getToken());

		String userId = loginAuthToken.getUserId();
		String deviceId = loginAuthToken.getDeviceId();
		AppUser appUser = userDao.getAppUser(userId, deviceId);
		appUser.setAuthToken(newLoginAuthToken.getToken());
		userDao.updateAppUserToken(appUser);

		return newAuthToken;
	}

	/**
	 * 디바이스(카메라) 이름 변경 메쏘드
	 *
	 * @param authToken		인증토큰
	 * @param spotDevId		디바이스 아이디
	 * @param devNm			디바이스 이름
	 * @throws Exception
	 */
	public void setDeviceName(String authToken, String spotDevId, String devNm) throws Exception {
		checkLoginAuthToken(authToken);
		userDao.updateDeviceName(spotDevId, devNm);
	}

	/**
	 * 앱 초기 실행에서 발급한 인증토큰 유효성을 체크하는 메쏘드
	 *
	 * @param authToken		인증토큰 스트링
	 * @return				인증토큰 객체
	 * @throws Exception
	 */
	public AuthToken checkInitAuthToken(String authToken) throws Exception {
		log.info("## initAuthToken : {} > ", authToken);
		AuthToken initAuthToken = AuthToken.decodeAuthToken(authToken);
		if(!initAuthToken.isValidInitToken())
			throw new APIException("Invalid InitAuthToken", HttpStatus.UNAUTHORIZED);

		return initAuthToken;
	}

	/**
	 * 아이디/패스워드 인증 후 발급한 로그인 인증토큰 유효성을 체크하는 메쏘드
	 *
	 * @param authToken		인증 토큰 스트링
	 * @return				인증 토큰 객체
	 * @throws Exception
	 */
	public AuthToken checkLoginAuthToken(String authToken) throws Exception {
		log.info("## loginAuthToken : {}", authToken);
		AuthToken loginAuthToken = AuthToken.decodeAuthToken(authToken);
		if(!loginAuthToken.isValidLoginToken())
			throw new APIException("Invalid InitAuthToken", HttpStatus.UNAUTHORIZED);

		return loginAuthToken;
	}

	/**
	 * 기본 회원 정보를 체크하고 신규회원인 경우 회원등록 처리
	 *
	 * @param initAuthToken
	 * @return
	 */
	private AppUser checkUserBase(String userId, String passwd, AuthToken initAuthToken) throws Exception {

		String connTermlId = initAuthToken.getDeviceId();
		String telNo = initAuthToken.getTelNo();
		String pnsRegId = initAuthToken.getPnsRegId();
		String svcCode = initAuthToken.getSvcCode();
		String unitSvcCd = "001";
		String credentialId = "";
		String custId = "";
		String bir = "";
		String age = "";
		int intAge = 0;

		AppUser appUserBase = new AppUser();
		// SDP API 연동
		try{

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			Map<String, String> crdMap = new HashMap<String, String>();
			crdMap.put("userId", userId);
			crdMap.put("pw", passwd);

			ObjectMapper jackson = new ObjectMapper();
			String crdParam = jackson.writeValueAsString(crdMap);
			HttpEntity<String> crdRequest = new HttpEntity<String>(crdParam, headers);

			String crdUrl = "http://211.42.137.222:8080/infra/sdp/login";
			String crdRes = restTemplate.postForObject(crdUrl, crdRequest, String.class);


			ObjectMapper om = new ObjectMapper();

			JsonNode crdM = om.readTree(crdRes);

			String resultCode = crdM.path("resultCode").toString().replaceAll("\"", "");
			System.out.println("resultCode>> " + resultCode);
			// 확인 후 나중에 추가
			/*if(resultCode.equals("0")){
				// 에러코드 추가
				// return 에러
			}*/

			JsonNode credentials = crdM.path("credentials");

			credentialId = credentials.get(0).get("credentialId").toString();
			credentialId = credentialId.replaceAll("\"", "");
			System.out.println("crdM>> " + crdM);
			System.out.println("credentialId>> " + credentialId);

			Map<String, String> custMap = new HashMap<String, String>();
			custMap.put("userName", userId);
			custMap.put("credtId", credentialId);
			/*custMap.put("userName", "ohc05");
			custMap.put("credtId", "149711908");*/

			String custParam = jackson.writeValueAsString(custMap);
			HttpEntity<String> custRequest = new HttpEntity<String>(custParam, headers);

			String custUrl = "http://211.42.137.222:8080/infra/sdp/subscription";
			String custRest = restTemplate.postForObject(custUrl, custRequest, String.class);

			Map<String, Object> map = null;
			//Map<String, String> partyMap1 = null;
			map = om.readValue(custRest, Map.class);
			ArrayList partyList = (ArrayList) map.get("party");
			System.out.println("map>> " + map);
			for( String key : map.keySet() ){

	            System.out.println( String.format("키 : %s, 값 : %s", key, map.get(key)) );
	        }


			/*			List mapList = new ArrayList(map.values());
			for(int i=0; i < mapList.size(); i++){

			}*/

			//System.out.println("mapList>> " + mapList);

			JsonNode custM = om.readTree(custRest);
			System.out.println("custM>> " + custM);

			JsonNode jn = om.readValue(custRest, JsonNode.class);

			JsonNode party = jn.get("party");
			JsonNode parMap = party.get(0).get("partyMap");

			bir = party.path(0).path("birthDate").toString().replaceAll("\"", "");
			custId = parMap.get(0).get("sourceSystemBindId").toString().replaceAll("\"", "");

			SimpleDateFormat fom = new SimpleDateFormat("yyyymmdd");
			Date chkAge = fom.parse(bir);
			intAge = getAgeFromBirthday(chkAge);
			age = Integer.toString(intAge);
			System.out.println("age>> " + age);
			System.out.println("custId>> " + custId);

		}catch(DataAccessException e){
			log.error(e.getMessage(), e);
			throw new APIException("SDP Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}


		try{
			appUserBase = userDao.getUserBase(userId);

			// 신규 회원인 경우
			if(appUserBase == null) {
				int mbrsSeq = userDao.getMbrsSeq();

				appUserBase = new AppUser();
				appUserBase.setMbrsSeq(mbrsSeq);
				appUserBase.setUserId(userId);
				appUserBase.setCredentialId(credentialId);
				appUserBase.setDstrCd(dstrCd);
				appUserBase.setSvcThemeCd(svcThemeCd);
				appUserBase.setUnitSvcCd(unitSvcCd);
				appUserBase.setPnsRegId(pnsRegId);
				appUserBase.setConnTermlId(connTermlId);
				appUserBase.setTelNo(telNo);

				userDao.insertMbrsBas(appUserBase);
				userDao.insertSvcMbrsRel(appUserBase);
				userDao.insertSvcTgtAccsBas(appUserBase);
				// 추후 정의되면 연동
				//userDao.insertSvcAccsHst(appUserBase);
				userDao.insertSvcTgtAccsAthnTxn(appUserBase);

				int chkCustId = userDao.chkCustId(custId);

				if(chkCustId > 0){
					userDao.updateSvcTgtBas(mbrsSeq);
				}

			}
		}catch(DataAccessException e){
			log.error(e.getMessage(), e);
			throw new APIException("DB Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		appUserBase.setTelNo(telNo);
		appUserBase.setPnsRegId(pnsRegId);
		appUserBase.setConnTermlId(connTermlId);
		appUserBase.setAge(age);
		appUserBase.setUserId(userId);
		appUserBase.setSvcCode(svcCode);
		appUserBase.setDstrCd(dstrCd);
		appUserBase.setSvcThemeCd(svcThemeCd);
		appUserBase.setUnitSvcCd(unitSvcCd);

		return appUserBase;
	}

	/**
	 * 로그인 메쏘드
	 *
	 * @param deviceId		단말(앱) 디바이스 아이디
	 * @param pnsRegId		PNS 등록 아이디
	 * @param userId		아이디
	 * @param telNo			전화번호
	 * @return
	 */
	private AuthToken login(AppUser userBase) throws Exception {

		// TODO 로그인 만료시간 하드코딩. 향후 CMS 연동 필요
		int expireTimeMinute = 60;
		userBase.setExpireTimeMinute(expireTimeMinute);

		// TODO 동시 로그인 최대값 하드코딩. 향후 CMS 연동 필요
		int loginMaxCount = 5;

		String userId = userBase.getUserId();
		String connTermlId = userBase.getConnTermlId();
		String telNo = userBase.getTelNo();
		String svcCode = userBase.getSvcCode();

		int loginCount = userDao.getOnlineAppUserCount(userBase);
		System.out.println("loginCount>> " + loginCount);
		try{
			if(loginCount < loginMaxCount) {
				AuthToken loginAuthToken = AuthToken.encodeAuthToken(connTermlId, telNo, "", "", userId, svcCode);
				System.out.println("loginAuthToken>> " + loginAuthToken);
				userBase.setAthnToknVal(loginAuthToken.getToken());
				userDao.updateSvcTgtAccsAthnTxn(userBase);
				userDao.updateSvcTgtAccsBas(userBase);
				// 추후 정의되면 연동
				//userDao.updateSvcAccsHst(userBase);

				return loginAuthToken;
			} else {
				return null;
			}
		}catch(DataAccessException e){
			log.error(e.getMessage(), e);
			throw new APIException("DB Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	/**
	 * 나이 구하는 메쏘드
	 *
	 * @param birthday		생년월일
	 * @throws Exception
	 * return 나이
	 */
	public static int getAgeFromBirthday(Date birthday) {
	    Calendar birth = new GregorianCalendar();
	    Calendar today = new GregorianCalendar();

	    birth.setTime(birthday);
	    today.setTime(new Date());

	    int factor = 0;
	    if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
	        factor = -1;
	    }
	    return today.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + factor;
	}



}
