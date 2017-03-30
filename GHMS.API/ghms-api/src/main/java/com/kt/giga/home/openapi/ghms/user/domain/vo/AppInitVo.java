package com.kt.giga.home.openapi.ghms.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 앱 초기 실행 VO
 * 
 * @author 
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class AppInitVo {
	
	/**
	 * 초기 인증 토큰
	 */
	private String authToken;
	
	/**
	 * 앱 단말 디바이스 아이디
	 */
	private String deviceId;
	
	/**
	 * 최신 앱 버전
	 */
	private String newAppVer;
	
	/**
	 * 최신 앱 버전 업데이트 옵션. 0:업데이트불필요, 1:강제, 2:선택
	 */
	private String appUpOption;
	
	/**
	 * 팝업 공지사항 URL
	 */
	private String popupNoticeUrl;
	
	/**
	 * PNS 이미지의 베이스 URL. TODO 사용하지 않으면 삭제 필요
	 */
	private String pnsImgUrl;
	
	/**
	 * 공지사항 URL 
	 */
	private String noticeUrl;
	
	/**
	 * 공지사항 옵션
	 */
	private String noticeOption;
	
	/**
	 * 개인정보 취급방침 URL
	 */
	private String privacyPolicyUrl;

	/**
	 * 가입 안내 URL
	 */
	private String joinGuideUrl;
	
	/**
	 * 웰컴 가이드 URL 리스트
	 */
	private String[] welcomeGuideUrlList;
	
	/**
	 * 코치 가이드 URL 리스트
	 */
	private String[] coachGuideUrlList;
	
	/**
	 * 디바이스(게이트웨이, 도어록, 가스밸브, 침입감지센서) 팁 URL 리스트
	 */
	
	private String[] deviceGuideUrlList;
	
    public String getPopupNoticeUrl() {
        return popupNoticeUrl;
    }
    
    public void setPopupNoticeUrl(String popupNoticeUrl) {
        this.popupNoticeUrl = popupNoticeUrl;
    }

    public String getJoinGuideUrl() {
		return joinGuideUrl;
	}

	public void setJoinGuideUrl(String joinGuideUrl) {
		this.joinGuideUrl = joinGuideUrl;
	}

	public String[] getWelcomeGuideUrlList() {
		return welcomeGuideUrlList;
	}

	public void setWelcomeGuideUrlList(String[] welcomeGuideUrlList) {
		this.welcomeGuideUrlList = welcomeGuideUrlList;
	}

	public String[] getCoachGuideUrlList() {
		return coachGuideUrlList;
	}

	public void setCoachGuideUrlList(String[] coachGuideUrlList) {
		this.coachGuideUrlList = coachGuideUrlList;
	}

	public String getAuthToken() {
		return authToken;
	}
	
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getNewAppVer() {
		return newAppVer;
	}
	
	public void setNewAppVer(String newAppVer) {
		this.newAppVer = newAppVer;
	}
	
	public String getAppUpOption() {
		return appUpOption;
	}
	
	public void setAppUpOption(String appUpOption) {
		this.appUpOption = appUpOption;
	}

	public String getPrivacyPolicyUrl() {
		return privacyPolicyUrl;
	}

	public void setPrivacyPolicyUrl(String privacyPolicyUrl) {
		this.privacyPolicyUrl = privacyPolicyUrl;
	}

	public String getPnsImgUrl() {
		return pnsImgUrl;
	}

	public void setPnsImgUrl(String pnsImgUrl) {
		this.pnsImgUrl = pnsImgUrl;
	}
	
    public String[] getDeviceGuideUrlList() {
        return deviceGuideUrlList;
    }
    
    public void setDeviceGuideUrlList(String[] deviceGuideUrlList) {
        this.deviceGuideUrlList = deviceGuideUrlList;
    }

	/**
	 * @return TODO
	 */
	public String getNoticeUrl() {
		return noticeUrl;
	}

	/**
	 * @param noticeUrl TODO
	 */
	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	/**
	 * @return TODO
	 */
	public String getNoticeOption() {
		return noticeOption;
	}

	/**
	 * @param noticeOption TODO
	 */
	public void setNoticeOption(String noticeOption) {
		this.noticeOption = noticeOption;
	}
	
	
	
}
