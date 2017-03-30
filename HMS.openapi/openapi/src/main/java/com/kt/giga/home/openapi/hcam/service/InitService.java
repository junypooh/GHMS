package com.kt.giga.home.openapi.hcam.service;

import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.common.AuthToken;
import com.kt.giga.home.openapi.hcam.dao.InitDao;
import com.kt.giga.home.openapi.hcam.dao.UserDao;
import com.kt.giga.home.openapi.hcam.domain.AppInit;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 앱 초기 실행 서비스
 *
 * @author
 *
 */
@Service("HCam.InitService")
public class InitService {

	/**
	 * 로거
	 */
	private Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * OpenAPI 환경 프라퍼티
	 */
	@Autowired
	private APIEnv env;

	/**
	 * 앱 초기 실행 매퍼 인터페이스
	 */
	@Autowired
	private InitDao initDao;

	/**
	 * 사용자 매퍼 인터페이스
	 */
	@Autowired
	private UserDao userDao;

	/**
	 * 사용자 서비스
	 */
	@Autowired
	private UserService userService;

	/**
	 * 앱 초기 실행 프라퍼티 맵
	 */
	private HashMap<String, HashMap<String, String>> initProps = new HashMap<String, HashMap<String, String>>();

	/**
	 * 앱 초기 실행 처리 메쏘드
	 *
	 * @param svcCode		서비스 코드 3자리
	 * @param deviceId		단말(앱) 디바이스 아이디
	 * @param telNo			전화번호
	 * @param appVer		단말에 설치된 앱 버전
	 * @param pnsRegId		PNS 등록 아이디
	 * @param screenSize	스마트폰 단말 사이즈
	 * @return				AppInit 객체
	 */
	public AppInit init(String svcCode, String deviceId, String telNo, String appVer, String pnsRegId, String screenSize) {

		String newAppVer						= getProperty(svcCode, "init.newAppVer");
		String appUpOption						= StringUtils.defaultIfBlank(getProperty(svcCode, "init.appUpOption"), "0");
		String noticeOption						= StringUtils.defaultIfBlank(getProperty(svcCode, "init.noticeOption"), "0");
		String popupNoticeOption				= StringUtils.defaultIfBlank(getProperty(svcCode, "init.popupNoticeOption"), "0");
		String[] ecServerList					= env.splitProperty("ec.serverRealList");
		String ecPort							= env.getProperty("ec.camera.port");
		String pnsImgUrl						= getProperty(svcCode, "init.pnsImgUrl");
		String noticeUrl						= getProperty(svcCode, "init.noticeUrl");
		String popupNoticeUrl					= getProperty(svcCode, "init.popupNoticeUrl");
		String privacyPolicyUrl					= getProperty(svcCode, "init.privacyPolicyUrl");
		String joinGuideUrl						= getProperty(svcCode, "init.joinGuideUrl");
		String ucloudJoinGuideUrl				= getProperty(svcCode, "init.ucloudJoinGuideUrl");
		String[] welcomeGuideUrlList			= splitVersionedProperty(svcCode, appVer, "init.welcomeGuideUrlList");
		String[] coachGuideUrlList				= splitVersionedProperty(svcCode, appVer, "init.coachGuideUrlList." + screenSize);
		String[] camGuideUrlList				= splitVersionedProperty(svcCode, appVer, "init.camGuideUrlList");

		// 현재 앱의 설치 버전이 이미 최신이면 버전 업그레이드 불필요
		if(StringUtils.equals(appVer, newAppVer)) {
			appUpOption = "0";
		}

		AuthToken authToken = AuthToken.encodeAuthToken(deviceId, telNo, pnsRegId, "", "", svcCode);

		AppInit appInit = new AppInit();
		appInit.setAuthToken(authToken.getToken());
		appInit.setDeviceId(deviceId);
		appInit.setNewAppVer(newAppVer);
		appInit.setAppUpOption(appUpOption);
		appInit.setNoticeOption(noticeOption);
		appInit.setPopupNoticeOption(popupNoticeOption);
		appInit.setEcServerList(ecServerList);
		appInit.setEcPort(ecPort);
		appInit.setPnsImgUrl(pnsImgUrl);
		appInit.setNoticeUrl(noticeUrl);
		appInit.setPopupNoticeUrl(popupNoticeUrl);
		appInit.setPrivacyPolicyUrl(privacyPolicyUrl);
		appInit.setJoinGuideUrl(joinGuideUrl);
		appInit.setUcloudJoinGuideUrl(ucloudJoinGuideUrl);
		appInit.setWelcomeGuideUrlList(welcomeGuideUrlList);
		appInit.setCoachGuideUrlList(coachGuideUrlList);
		appInit.setCamGuideUrlList(camGuideUrlList);

		return appInit;
	}

	/**
	 * 앱 초기실행 프라퍼티 리로딩 메쏘드
	 *
	 * @param svcCode		서비스 코드
	 * @return				리로딩된 프라퍼티 맵
	 */
	public HashMap<String, String> reloadProperties(String svcCode) {

		log.debug("## reload init properties ##");
		initProps.remove(svcCode);
		return getProps(svcCode);
	}

	/**
	 * 최신 앱 버전 정보 조회 메쏘드
	 *
	 * @param authToken		인증토큰
	 * @return				최신 앱 버전 정보
	 * @throws Exception
	 */
	public AppInit getNewAppVersion(String authToken) throws Exception {
		AuthToken loginAuthToken = userService.checkLoginAuthToken(authToken);
		String svcCode = loginAuthToken.getSvcCode();

		AppInit appInit = new AppInit();
		appInit.setNewAppVer(getProperty(svcCode, "init.newAppVer"));

		return appInit;
	}

	/**
	 * 초기 실행 프라퍼티 조회 메쏘드
	 *
	 * @param svcCode		서비스코드. 001:홈모니터링, 002:홈헬스케어
	 * @param name			이름
	 * @return				프라퍼티 값
	 */
	public String getProperty(String svcCode, String name) {
		return getProps(svcCode).get(name);
	}

	/**
	 * 초기 실행 프라퍼티 리스트 조회 메쏘드
	 *
	 * @param svcCode		서비스코드. 001:홈모니터링, 002:홈헬스케어
	 * @param name			이름
	 * @param delimiter		구분자
	 * @return				프라퍼티 값
	 */
	public String[] splitProperty(String svcCode, String name, String delimiter) {
		String property = getProperty(svcCode, name);
		if(StringUtils.isEmpty(property)) {
			return null;
		} else {
			return StringUtils.split(property, delimiter);
		}
	}

	/**
	 * 초기 실행 프라퍼티 리스트 조회 메쏘드. 파이프로 구분하여 split 함
	 *
	 * @param svcCode		서비스코드. 001:홈모니터링, 002:홈헬스케어
	 * @param name			이름
	 * @return				프라퍼티 값
	 */
	public String[] splitProperty(String svcCode, String name) {
		return splitProperty(svcCode, name, "|");
	}

	/**
	 * 버전별 초기 실행 프라퍼티 리스트 조회 메쏘드
	 *
	 * @param svcCode		서비스코드. 001:홈모니터링, 002:홈헬스케어
	 * @param version		버전
	 * @param name			이름
	 * @param delimiter		구분자
	 * @return				프라퍼티 값
	 */
	public String[] splitVersionedProperty(String svcCode, String version, String name, String delimiter) {

		String[] propertyList = splitProperty(svcCode, name + "." + version, delimiter);
		if(ArrayUtils.isEmpty(propertyList)) {
			propertyList = splitProperty(svcCode, name, delimiter);
		}

		return propertyList;
	}

	/**
	 * 버전별 초기 실행 프라퍼티 리스트 조회 메쏘드. 파이프로 구분하여 split 함
	 *
	 * @param svcCode		서비스코드. 001:홈모니터링, 002:홈헬스케어
	 * @param version		버전
	 * @param name			이름
	 * @return				프라퍼티 값
	 */
	public String[] splitVersionedProperty(String svcCode, String version, String name) {
		return splitVersionedProperty(svcCode, version, name, "|");
	}

	/**
	 * 초기 실행 맵 리터 메쏘드
	 *
	 * @param svcCode	서비스코드. 001:홈모니터링, 002:홈헬스케어
	 * @return 			초기 실행 맵
	 */
	public HashMap<String, String> getProps(String svcCode) {
		HashMap<String, String> props = initProps.get(svcCode);
		if(props == null) {
            props = new HashMap<>();
            initProps.put(svcCode, props);
			ArrayList<HashMap<String, String>> propList = initDao.getInitProps(svcCode);
			for(HashMap<String, String> prop : propList) {
				props.put(prop.get("name"), prop.get("value"));
			}
		}
		return props;
	}
}
