package com.kt.giga.home.openapi.hcam.domain;

/**
 * 앱 초기 실행 클래스
 * 
 * @author 
 *
 */
public class AppInit {
	
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
	 * 공지사항 옵션. 0:공지없음, 1:공지있음(강제), 2:공지있음(선택)
	 */
	private String noticeOption;
	
	/**
	 * 팝업 공지사항 옵션. 0:공지없음, 1:공지있음(강제), 2:공지있음(선택)
	 */
	private String popupNoticeOption;
	
	/**
	 * 홈 카메라 접속 EC 서버(아이피) 리스트
	 */
	private String[] ecServerList;
	
	/**
	 * 홈 카메라 접속 EC 포트
	 */
	private String ecPort;
	
	/**
	 * PNS 이미지의 베이스 URL. TODO 사용하지 않으면 삭제 필요
	 */
	private String pnsImgUrl;
	
	/**
	 * 공지사항 URL
	 */
	private String noticeUrl;
	
	/**
	 * 팝업 공지사항 URL
	 */
	private String popupNoticeUrl;
	
	/**
	 * 개인정보 취급방침 URL
	 */
	private String privacyPolicyUrl;

	/**
	 * 가입 안내 URL
	 */
	private String joinGuideUrl;
	
	/**
	 * Ucloud 가입 안내 URL
	 */
	private String ucloudJoinGuideUrl;
	
	/**
	 * 웰컴 가이드 URL 리스트
	 */
	private String[] welcomeGuideUrlList;
	
	/**
	 * 코치 가이드 URL 리스트
	 */
	private String[] coachGuideUrlList;
	
	/**
	 * 카메라 팁 URL 리스트
	 */
	private String[] camGuideUrlList;
	
	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public String getJoinGuideUrl() {
		return joinGuideUrl;
	}

	public void setJoinGuideUrl(String joinGuideUrl) {
		this.joinGuideUrl = joinGuideUrl;
	}

	public String getUcloudJoinGuideUrl() {
		return ucloudJoinGuideUrl;
	}

	public void setUcloudJoinGuideUrl(String ucloudJoinGuideUrl) {
		this.ucloudJoinGuideUrl = ucloudJoinGuideUrl;
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
	
	public String[] getCamGuideUrlList() {
		return camGuideUrlList;
	}

	public void setCamGuideUrlList(String[] camGuideUrlList) {
		this.camGuideUrlList = camGuideUrlList;
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
	
	public String getNoticeOption() {
		return noticeOption;
	}
	
	public void setNoticeOption(String noticeOption) {
		this.noticeOption = noticeOption;
	}

	public String getPrivacyPolicyUrl() {
		return privacyPolicyUrl;
	}

	public void setPrivacyPolicyUrl(String privacyPolicyUrl) {
		this.privacyPolicyUrl = privacyPolicyUrl;
	}

	public String[] getEcServerList() {
		return ecServerList;
	}

	public void setEcServerList(String[] ecServerList) {
		this.ecServerList = ecServerList;
	}

	public String getEcPort() {
		return ecPort;
	}

	public void setEcPort(String ecPort) {
		this.ecPort = ecPort;
	}

	public String getPnsImgUrl() {
		return pnsImgUrl;
	}

	public void setPnsImgUrl(String pnsImgUrl) {
		this.pnsImgUrl = pnsImgUrl;
	}

	public String getPopupNoticeOption() {
		return popupNoticeOption;
	}

	public void setPopupNoticeOption(String popupNoticeOption) {
		this.popupNoticeOption = popupNoticeOption;
	}

	public String getPopupNoticeUrl() {
		return popupNoticeUrl;
	}

	public void setPopupNoticeUrl(String popupNoticeUrl) {
		this.popupNoticeUrl = popupNoticeUrl;
	}
	
	
}
