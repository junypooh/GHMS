/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.token.AuthToken;
import com.kt.giga.home.openapi.ghms.user.dao.InitDao;
import com.kt.giga.home.openapi.ghms.user.domain.vo.AppInitVo;
import com.kt.giga.home.openapi.ghms.util.properties.APIEnv;


/**
 * 앱 초기 실행 서비스
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 2. 2.
 */

@Service
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
     * 앱 초기 실행 프라퍼티 맵
     */
    private HashMap<String, HashMap<String, String>> initProps = new HashMap<String, HashMap<String, String>>();
    

    /**
     * 앱 초기 실행 처리 메쏘드 
     * 
     * @param user-agent    App 구분 (AND, IOS)
     * @param unionSvcCode  마스터구분(M,S,T)+지역코드+서비스테마코드+단위서비스코드
     * @param deviceId      단말(앱) 디바이스 아이디 
     * @param telNo         전화번호
     * @param appVer        단말에 설치된 앱 버전
     * @param pnsRegId      PNS 등록 아이디
     * @param screenSize    스마트폰 단말 사이즈
     * @return              AppInit 객체
     */
    public AppInitVo init(String userAgent, String unionSvcCode, String deviceId, String telNo, String appVer, String pnsRegId, String screenSize) {
        
        String unitSvcCd = CommonConstants.UNIT_SVC_CD;
        
        if(unionSvcCode.length() == 10){
            unitSvcCd = unionSvcCode.substring(7, 10);
        }

        String newAppVer                        = getProperty(unitSvcCd, "init.newAppVer");                                               // 최신 앱 버젼
        String appUpOption                      = StringUtils.defaultIfBlank(getProperty(unitSvcCd, "init.appUpOption"), "0");            // 최신 앱 버전 업데이트 옵션. 0:업데이트불필요, 1:강제, 2:선택
        String popupNoticeUrl                   = getProperty(unitSvcCd, "init.popupNoticeUrl");                                          // 팝업 공지사항 URL
        String pnsImgUrl                        = getProperty(unitSvcCd, "init.pnsImgUrl");                                               // PNS 이미지의 베이스 URL
        String noticeUrl	                    = getProperty(unitSvcCd, "init.noticeUrl");                                               // 공지사항 URL
        String noticeOption	                    = getProperty(unitSvcCd, "init.noticeOption");                                            // 공지사항 옵션
        String joinGuideUrl                     = getProperty(unitSvcCd, "init.joinGuideUrl");                                            // 가입안내 URL
        String privacyPolicyUrl                 = getProperty(unitSvcCd, "init.privacyPolicyUrl");                                        // 개인정보 취급방침 URL
        String[] welcomeGuideUrlList            = splitVersionedProperty(unitSvcCd, appVer, "init.welcomeGuideUrlList");                  // 웰컴가이드 URL 리스트
        String[] coachGuideUrlList              = splitVersionedProperty(unitSvcCd, appVer, "init.coachGuideUrlList." + screenSize);      // 코치가이드 URL 리스트
        String[] deviceGuideUrlList             = splitVersionedProperty(unitSvcCd, appVer, "init.deviceGuideUrlList");                   // 디바이스(게이트웨이, 도어록, 가스밸브, 침입감지센서) 팁 URL 리스트
        
        
        // 현재 앱의 설치 버전이 이미 최신이면 버전 업그레이드 불필요
        if(StringUtils.equals(appVer, newAppVer)) {
            appUpOption = "0";
        }

        AuthToken authToken = AuthToken.encodeAuthToken(userAgent, deviceId, telNo, pnsRegId, "", "", unionSvcCode, appVer, "");
        
        AppInitVo appInit = new AppInitVo();
        appInit.setAuthToken(authToken.getToken());
        appInit.setNewAppVer(newAppVer);
        appInit.setAppUpOption(appUpOption);        
        appInit.setPnsImgUrl(pnsImgUrl);
        appInit.setPopupNoticeUrl(popupNoticeUrl);
        appInit.setNoticeUrl(noticeUrl);
        appInit.setNoticeOption(noticeOption);
        appInit.setPrivacyPolicyUrl(privacyPolicyUrl);
        appInit.setJoinGuideUrl(joinGuideUrl);
        appInit.setWelcomeGuideUrlList(welcomeGuideUrlList);
        appInit.setCoachGuideUrlList(coachGuideUrlList);
        appInit.setDeviceGuideUrlList(deviceGuideUrlList);
        
        return appInit;
    }
    
    /**
     * 초기 실행 프라퍼티 조회 메쏘드
     * 
     * @param unitSvcCd     단위서비스코드. 001:홈모니터링, 002:홈헬스케어, 003:홈매니저
     * @param name          이름
     * @return              프라퍼티 값
     */
    public String getProperty(String unitSvcCd, String name) {
        return getProps(unitSvcCd).get(name);
    }
    
    /**
     * 초기 실행 프라퍼티 리스트 조회 메쏘드
     *  
     * @param unitSvcCd     단위서비스코드. 001:홈모니터링, 002:홈헬스케어, 003:홈매니저
     * @param name          이름
     * @param delimiter     구분자
     * @return              프라퍼티 값
     */
    public String[] splitProperty(String unitSvcCd, String name, String delimiter) {
        String property = getProperty(unitSvcCd, name);
        if(StringUtils.isEmpty(property)) {
            return null;
        } else {
            return StringUtils.split(property, delimiter);
        }
    }
    
    /**
     * 초기 실행 프라퍼티 리스트 조회 메쏘드. 파이프로 구분하여 split 함
     *  
     * @param unitSvcCd     단위서비스코드. 001:홈모니터링, 002:홈헬스케어, 003:홈매니저
     * @param name          이름
     * @return              프라퍼티 값
     */
    public String[] splitProperty(String unitSvcCd, String name) {
        return splitProperty(unitSvcCd, name, "|");
    }
    
    /**
     * 버전별 초기 실행 프라퍼티 리스트 조회 메쏘드
     *  
     * @param unitSvcCd     단위서비스코드. 001:홈모니터링, 002:홈헬스케어, 003:홈매니저
     * @param version       버전
     * @param name          이름
     * @param delimiter     구분자
     * @return              프라퍼티 값
     */
    public String[] splitVersionedProperty(String unitSvcCd, String version, String name, String delimiter) {
        
        String[] propertyList = splitProperty(unitSvcCd, name + "." + version, delimiter);
        if(ArrayUtils.isEmpty(propertyList)) {
            propertyList = splitProperty(unitSvcCd, name, delimiter);
        }
        
        return propertyList;
    } 
    
    /**
     * 버전별 초기 실행 프라퍼티 리스트 조회 메쏘드. 파이프로 구분하여 split 함
     *  
     * @param unitSvcCd     단위서비스코드. 001:홈모니터링, 002:홈헬스케어, 003:홈매니저
     * @param version       버전
     * @param name          이름
     * @return              프라퍼티 값
     */
    public String[] splitVersionedProperty(String unitSvcCd, String version, String name) {
        return splitVersionedProperty(unitSvcCd, version, name, "|");
    }
    
    /**
     * 초기 실행 맵 리터 메쏘드
     * 
     * @param unitSvcCd     단위서비스코드. 001:홈모니터링, 002:홈헬스케어, 003:홈매니저 
     * @return              초기 실행 맵
     */
    public HashMap<String, String> getProps(String unitSvcCd) {
        HashMap<String, String> props = initProps.get(unitSvcCd);
        if(props == null) {
            props = new HashMap<String, String>();
            initProps.put(unitSvcCd, props);
            ArrayList<HashMap<String, String>> propList = initDao.getInitProps(unitSvcCd);
            for(HashMap<String, String> prop : propList) {
                props.put(prop.get("name"), prop.get("value"));
            }
        }
        return props;
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

}
