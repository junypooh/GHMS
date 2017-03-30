package com.kt.giga.home.openapi.health.user.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.kt.giga.home.openapi.cms.dao.CodeDao;
import com.kt.giga.home.openapi.cms.service.NoticeService;
import com.kt.giga.home.openapi.cms.service.TermsService;
import com.kt.giga.home.openapi.cms.service.VersionService;
import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.common.CipherUtils;
import com.kt.giga.home.openapi.health.exercise.service.ExerciseService;
import com.kt.giga.home.openapi.health.user.dao.HealthUserDao;
import com.kt.giga.home.openapi.health.user.domain.HealthUser;
import com.kt.giga.home.openapi.health.user.domain.HealthUserAuth;
import com.kt.giga.home.openapi.service.APIException;
import com.kt.giga.home.openapi.util.AuthToken;

@Service
public class HealthUserService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VersionService versionService;

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private HealthUserDao helthUserDao;

	@Autowired
	private CodeDao codeDao;

	@Autowired
	private APIEnv apiEnv;

	@Autowired
	private ExerciseService exerciseService;

	@Autowired
	private TermsService termsService;

	static String dstrCd = "001";     //지역코드
	static String svcThemeCd = "HIT"; //서비스테마코드
	private HashMap<String, HashMap<String, String>> initProps = new HashMap<String, HashMap<String, String>>();

	public HealthUserAuth init(String deviceId, String telNo, String appVer,
			String pnsRegId, String svcCode, String screenSize
			) throws Exception {

		HealthUserAuth helthUserAuth = new HealthUserAuth();

		String newAppVer = "";
		String appUpOption = "";
		String termsYn = "N";
		String mbrYn = "Y";
		String joinGuideUrl = "";
		String privacyPolicyUrl = "";
		String noticePmUrl = "";
		String noticePopMoreYn = "";
		String noticePopUrl = "";
		String[] welcomeGuideUrlList;
		String[] coachGuideUrlList;
		dstrCd = svcCode.substring(0, 3);
		svcThemeCd = svcCode.substring(3, 6);
		String unitSvcCd = svcCode.substring(6);
		String notciePmOption = "";

		Map<String, Object> pmMap 	= new HashMap<String, Object>();
		pmMap.put("cpCode"			, unitSvcCd);	// 서비스 코드

		JSONObject noticePmObj	= noticeService.getNoticePmList(pmMap);

		if(!noticePmObj.get("totalCnt").toString().equals("0")){
			JSONArray list = (JSONArray)noticePmObj.get("list");
			JSONObject res = (JSONObject)list.get(0);
			noticePmUrl = res.get("noticeUrl").toString();
			notciePmOption = res.get("noticeStart").toString() + "|"
								+ res.get("noticeEnd").toString();
		}
		log.info("noticePmUrl>>>> " + noticePmUrl);
		log.info("notciePmOption>>>> " + notciePmOption);

		// 케쉬에서 취득 하는 것을 디비에서 직접 가저오도록 수정
/*		newAppVer =  props.get("init.newAppVer");
		appUpOption = getInitProperty(unitSvcCd, "init.appUpOption");
		noticePopMoreYn = getInitProperty(unitSvcCd, "init.noticePopMoreYn");
		noticePopUrl = getInitProperty(unitSvcCd, "init.noticePopUrl");
		joinGuideUrl = getInitProperty(unitSvcCd, "init.joinGuideUrl");
		privacyPolicyUrl = getInitProperty(unitSvcCd, "init.privacyPolicyUrl");
		welcomeGuideUrlList = splitInitProperty(unitSvcCd, "init.welcomeGuideUrlList");
		coachGuideUrlList = splitInitProperty(unitSvcCd, "init.coachGuideUrlList." + screenSize);*/

		ArrayList<HashMap<String, String>> propsList = new ArrayList<HashMap<String, String>>();
		propsList = helthUserDao.getHomesvcProps(unitSvcCd);

		Map<String, String> props = new HashMap<String, String>();
		for(HashMap<String , String> prop : propsList){
			props.put(prop.get("name"), prop.get("value"));
		}
		log.info("props>>>>>>>>>>>>>>> " + props);

		newAppVer =  props.get("init.newAppVer");
		appUpOption = props.get("init.appUpOption");
		noticePopMoreYn =  props.get("init.noticePopMoreYn");
		noticePopUrl =  props.get("init.noticePopUrl");
		joinGuideUrl =  props.get("init.joinGuideUrl");
		privacyPolicyUrl =  props.get("init.privacyPolicyUrl");
		welcomeGuideUrlList = StringUtils.split(props.get("init.welcomeGuideUrlList." + appVer), "|");
		coachGuideUrlList = StringUtils.split(props.get("init.coachGuideUrlList."+ screenSize + "." + appVer), "|");

		try{
			int chkUser = helthUserDao.getUserCnt(telNo);
			log.info("chkUser>>> " +chkUser);
			AuthToken authTokenInit = AuthToken.encodeAuthToken(deviceId, telNo, pnsRegId, "", "", svcCode);
			if(chkUser < 1){
				helthUserAuth.setInitAuthToken(authTokenInit.getToken());
				helthUserAuth.setAuthToken("");
				helthUserAuth.setDeviceId(deviceId);
				helthUserAuth.setNewAppVer(newAppVer);
				helthUserAuth.setAppUpOption(appUpOption);
				helthUserAuth.setTermsYn("");
				helthUserAuth.setNoticePopUrl(noticePopUrl);
				helthUserAuth.setNoticePopMoreYn(noticePopMoreYn);
				helthUserAuth.setNoticePmUrl(noticePmUrl);
				helthUserAuth.setJoinGuideUrl(joinGuideUrl);
				helthUserAuth.setPrivacyPolicyUrl(privacyPolicyUrl);
				helthUserAuth.setWelcomeGuideUrlList(welcomeGuideUrlList);
				helthUserAuth.setCoachGuideUrlList(coachGuideUrlList);
				helthUserAuth.setMbrYn("N");
				helthUserAuth.setNotciePmOption(notciePmOption);
				return helthUserAuth;
			}

			int seq = helthUserDao.getUserMbrsSeq(telNo);
			log.info("seq>>> " +seq);
			String mbrSeq = Integer.toString(seq);
			log.info("mbrSeq>>> " +mbrSeq);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mbrSeq", seq);
			map.put("svcCode", unitSvcCd);

			int chkTerm = helthUserDao.getTermsYn(map);
			log.info("chkTerm>>> " +chkTerm);
			if(chkTerm > 0){
				termsYn = "Y";
			}
			log.info("mbrSeq>>> " +mbrSeq);

			//////////////////////////////////////////////////////////////////////
			// deviceId는 회원가입시 저장된 값을 고정값으로 사용한다(변경되어선 안됨).UUID, 헬스케어에선 특별히 사용하지 않음.
			//////////////////////////////////////////////////////////////////////
			map.put("unitSvcCd", unitSvcCd);
			map.put("dstrCd", dstrCd);
			map.put("svcThemeCd", svcThemeCd);

			deviceId = helthUserDao.getSvcTgtConnBasDeviceId(map);

			AuthToken authToken = AuthToken.encodeAuthToken(deviceId, telNo, pnsRegId, mbrSeq, mbrSeq, svcCode);
			AuthToken token = checkLoginAuthToken(authToken.getToken());
			log.info("## token length {} ", token.getToken().length());

			Map<String, Object> updateMap = new HashMap<String, Object>();
			updateMap.put("pnsRegId", pnsRegId);
			updateMap.put("telNo", telNo);
			updateMap.put("appVer", appVer);
			updateMap.put("deviceId", deviceId);
			updateMap.put("seq", seq);
			updateMap.put("unitSvcCd", unitSvcCd);
			updateMap.put("dstrCd", dstrCd);
			updateMap.put("svcThemeCd", svcThemeCd);

			helthUserDao.updateSvcTgtConnBas(updateMap);

			Map<String, Object> athnTxnMap = new HashMap<String, Object>();
			athnTxnMap.put("connTermlId", deviceId);
			athnTxnMap.put("mbrSeq", seq);
			athnTxnMap.put("dstrCd", dstrCd);
			athnTxnMap.put("svcThemeCd", svcThemeCd);
			athnTxnMap.put("unitSvcCd", unitSvcCd);
			athnTxnMap.put("athnToknVal", authToken.getToken());

			int athnTxnCnt = helthUserDao.cntSvcTgtConnAthnTxn(athnTxnMap);
			// 서비스대상접속인증내역 - svc_tgt_conn_bas fk오류남.
			if(athnTxnCnt > 0){
				helthUserDao.updateSvcTgtConnAthnTxn(athnTxnMap);
			}else{
				helthUserDao.insertSvcTgtConnAthnTxn(athnTxnMap);
			}

			helthUserAuth.setAuthToken(authToken.getToken());
			helthUserAuth.setInitAuthToken("");
			helthUserAuth.setDeviceId(deviceId);
			helthUserAuth.setNewAppVer(newAppVer);
			helthUserAuth.setAppUpOption(appUpOption);
			helthUserAuth.setTermsYn(termsYn);
			helthUserAuth.setNoticePopUrl(noticePopUrl);
			helthUserAuth.setNoticePopMoreYn(noticePopMoreYn);
			helthUserAuth.setNoticePmUrl(noticePmUrl);
			helthUserAuth.setJoinGuideUrl(joinGuideUrl);
			helthUserAuth.setPrivacyPolicyUrl(privacyPolicyUrl);
			helthUserAuth.setWelcomeGuideUrlList(welcomeGuideUrlList);
			helthUserAuth.setCoachGuideUrlList(coachGuideUrlList);
			helthUserAuth.setMbrYn(mbrYn);
			helthUserAuth.setNotciePmOption(notciePmOption);


		}catch (DataAccessException e){
			log.error(e.getMessage(), e);
			throw new APIException("Init Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return helthUserAuth;
	}

	@Transactional
	public Map<String, Object> mbrJoin(String deviceId, String telNo, String appVer,
			String pnsRegId, String svcCode, String pinNo, List<HashMap<String, String>> agreeList) throws Exception {


		dstrCd = svcCode.substring(0, 3);
		svcThemeCd = svcCode.substring(3, 6);
		String unitSvcCd = svcCode.substring(6);
		log.info("agreeList>>> " + agreeList.toString());
		Map<String, Object> map = new HashMap<String, Object>();

		try{
			int chkUser = helthUserDao.getUserCnt(telNo);
			if(chkUser > 0){
				map.put("mbrRegSttus", "2");
				return map;
			}

			HealthUser user = new HealthUser();

			int mbrsSeq = helthUserDao.getMbrSeq();
			int svcTgtSeq = helthUserDao.getSvcTgtSeq();
			int spotDevSeq = helthUserDao.getSpotDevSeq(svcTgtSeq);
			String devTypeCd = "0043";
			int devModelSeq =  helthUserDao.getDevModelSeq(devTypeCd);

			log.info("telNo>>> " + telNo);
			log.info("mbrsSeq>>> " + mbrsSeq);
			log.info("svcTgtSeq>>> " + svcTgtSeq);
			log.info("spotDevSeq>>> " + spotDevSeq);

			user.setMbrsSeq(mbrsSeq);
			user.setMbrSeq(mbrsSeq);
			user.setSvcTgtSeq(svcTgtSeq);
			user.setSpotDevSeq(spotDevSeq);
			user.setMbrsId(telNo);
			user.setDstrCd(dstrCd);
			user.setSvcThemeCd(svcThemeCd);
			user.setUnitSvcCd(unitSvcCd);
			user.setConnTermlId(deviceId);
			user.setPnsRegId(pnsRegId);
			user.setAppVer(appVer);
			user.setTelNo(telNo);
			user.setDevUuId(UUID.randomUUID().toString());
			user.setPhysDevYn("N");
			user.setSvcSttusCd("01");
			user.setDevModelSeq(devModelSeq);
			user.setSpotDevId(telNo);

			helthUserDao.insertMbrsBas(user);
			helthUserDao.insertSvcMbrsRel(user);
			//helthUserDao.insertSvcTgtAccsBas(user);
			helthUserDao.insertSvcTgtConnBas(user);
			helthUserDao.insertSvcTgtBas(user);
			helthUserDao.insertSpotDevBas(user);

			Map<String, Object> proMap = new HashMap<String, Object>();
			proMap.put("mbrSeq", mbrsSeq);
			proMap.put("genderVal", this.getEncryptByAES("남"));
			proMap.put("hightVal", this.getEncryptByAES("178.0"));
			proMap.put("ageVal", this.getEncryptByAES("30"));
			proMap.put("weightVal", this.getEncryptByAES("75.0"));

			helthUserDao.insertHealthProfTxn(proMap);

			List<String> snsnTagCd  = helthUserDao.getSnsnTagCd(devModelSeq);
			log.info("snsnTagCd>>>>>>>>>> " +snsnTagCd);

			for(int i=0; i < snsnTagCd.size(); i++){
				Map<String, Object> tagMap = new HashMap<String, Object>();
				tagMap.put("svcTgtSeq", svcTgtSeq);
				tagMap.put("spotDevSeq", spotDevSeq);
				tagMap.put("snsnTagCd", snsnTagCd.get(i));

				helthUserDao.insertSpotDevBySnsnTagRel(tagMap);
			}

			// 약관 동의 등록
			Map<String, Object> trerMap 	= new HashMap<String, Object>();
			trerMap.put("cpCode"	, unitSvcCd);	//홈모니터링 서비스 코드
			trerMap.put("mbr_seq"	, mbrsSeq);	//접속 회원 ID (mbr_seq)
			trerMap.put("agreeList"	, agreeList);
			//log.info("agreeList2222>>> " + map.get("agreeList"));
			termsService.setTermsAgree(trerMap);


			// EC 장치 조회
			String svcCd = "002"; // 단위서비스코드
			String svcTgtId = telNo; // 서비스대상 아이디(인증토큰의 전화번호)

/*			String mbrSeq = Integer.toString(mbrsSeq);
			AuthToken authToken = AuthToken.encodeAuthToken(deviceId, telNo, pnsRegId, mbrSeq, mbrSeq, svcCode);
			String joinAuthToken = authToken.getToken().toString();
			log.info("joinAuthToken>>>>>>>>>>>>>>>>> " + joinAuthToken);*/

			Map<String, Object> exercise = new HashMap<String, Object>();

			exercise.put("crud", "insert");
			exercise.put("unitSvcCd", svcCd);
			exercise.put("svcTgtId", svcTgtId);
			exercise.put("pinNo", pinNo);

			exerciseService.update(exercise);
			log.info("resultCode----- > " + exercise.get("resultCode"));


			if(!exercise.get("resultCode").equals("1")){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				map.put("mbrRegSttus", "3");
				return map;
			}

		}catch (DataAccessException e){
			log.error(e.getMessage(), e);
			map.put("mbrRegSttus", "4");
			return map;
		}
		map.put("mbrRegSttus", "1");
		return map;
	}

	public Map<String, Object> withdraw(String telNo, String svcCode) throws Exception {


		Map<String, Object> map = new HashMap<String, Object>();

		try{
			int chkUser = helthUserDao.getUserCnt(telNo);
			if(chkUser < 1){
				map.put("result", "3");
				return map;
			}

			dstrCd = svcCode.substring(0, 3);
			svcThemeCd = svcCode.substring(3, 6);
			String unitSvcCd = svcCode.substring(6);

//			String unitSvcCd = "002"; // 단위서비스코드 9자리 모두 필요함
			String svcTgtId = telNo; // 서비스대상 아이디(인증토큰의 전화번호)

			Map<String, Object> exercise = new HashMap<String, Object>();

			exercise.put("crud", "delete");
			exercise.put("unitSvcCd", unitSvcCd);
			exercise.put("svcTgtId", svcTgtId);

			exerciseService.update(exercise);
			log.info("resultCode----- > " + exercise.get("resultCode"));
			if(!exercise.get("resultCode").equals("1")){
				map.put("result", "4");
				return map;
			}
			int mbrsSeq = helthUserDao.getUserMbrsSeq(telNo);
			log.info("mbrsSeq>>>>>>>>>>>>>> " + mbrsSeq);

			HealthUser user = new HealthUser();
			user.setMbrsSeq(mbrsSeq);
			user.setSvcSttusCd("03");
			user.setOprtSttusCd("0003");

			helthUserDao.updateSvcMbrRelMemWihtdraw(user);
			helthUserDao.updateSvcTgtBasMemWihtdraw(user);

			Map<String, Object> removeMap = new HashMap<String, Object>();
			removeMap.put("mbrSeq", mbrsSeq);
			removeMap.put("dstrCd", dstrCd);
			removeMap.put("svcThemeCd", svcThemeCd);
			removeMap.put("unitSvcCd", unitSvcCd);
			// otv tv app. 위치정보 삭제 - 페어링 기본 테이블에 없을 경우만.
			helthUserDao.removeApplLoSetupTxn(removeMap);
			helthUserDao.removeOtvPairingBas(removeMap);
			helthUserDao.removeHealthcareProfTxn(mbrsSeq);

		}catch (DataAccessException e){
			log.error(e.getMessage(), e);
			map.put("result", "2");
			return map;
			//throw new APIException("Init Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		map.put("result", "1");
		return map;
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
	 * 초기 설정 프라퍼티 조회 메쏘드
	 *
	 * @param svcCode		서비스코드. 001:홈모니터링, 002:홈헬스케어
	 * @param name			이름
	 * @return				프라퍼티 값
	 */
	private String getInitProperty(String unitSvcCd, String name) {
		return getInitProps(unitSvcCd).get(name);
	}

	/**
	 * 초기 설정 프라퍼티 리스트 조회 메쏘드
	 *
	 * @param svcCode		서비스코드. 001:홈모니터링, 002:홈헬스케어
	 * @param name			이름
	 * @param delimiter		구분자
	 * @return				프라퍼티 값
	 */
	private String[] splitInitProperty(String unitSvcCd, String name, String delimiter) {
		String property = getInitProperty(unitSvcCd, name);
		if(StringUtils.isEmpty(property)) {
			return null;
		} else {
			return StringUtils.split(property, delimiter);
		}
	}

	/**
	 * 초기 설정 프라퍼티 리스트 조회 메쏘드. 파이프로 구분하여 split 함
	 *
	 * @param svcCode		서비스코드. 001:홈모니터링, 002:홈헬스케어
	 * @param name			이름
	 * @return
	 */
	private String[] splitInitProperty(String unitSvcCd, String name) {
		return splitInitProperty(unitSvcCd, name, "|");
	}


	/**
	 * 초기 설정 맵 리터 메쏘드
	 *
	 * @param svcCode	서비스코드. 001:홈모니터링, 002:홈헬스케어
	 * @return 			초기 설정 맵
	 */
	private HashMap<String, String> getInitProps(String unitSvcCd) {
		HashMap<String, String> props = initProps.get(unitSvcCd);
		if(props == null) {
			props = new HashMap<String, String>();
			initProps.put(unitSvcCd, props);
			ArrayList<HashMap<String, String>> propList = helthUserDao.getInitProps(unitSvcCd);

			log.info("propList>>>>>>> "+propList);

			for(HashMap<String, String> prop : propList) {
				props.put(prop.get("name"), prop.get("value"));
			}
		}
		return props;
	}

	private String getEncryptByAES(String plainText) throws UnsupportedEncodingException, Exception {
		byte[] key 	= apiEnv.getProperty("profile.encrypt.key").getBytes("UTF-8");
		byte[] iv	= apiEnv.getProperty("profile.encrypt.iv").getBytes("UTF-8");
		return CipherUtils.encryptAESCTRNoPaddingHex(key, iv, plainText);
	}
}
