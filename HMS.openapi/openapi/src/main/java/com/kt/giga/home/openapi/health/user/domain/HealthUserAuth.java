package com.kt.giga.home.openapi.health.user.domain;

import java.util.List;

public class HealthUserAuth {

	private String authToken;
	private String initAuthToken;
	private String deviceId;
	private String newAppVer;
	private String appUpOption;
	private String noticeOption;
	private String noticeUrl;
	private String joinGuideUrl;
	private String privacyPolicyUrl;
	private String welcomeGuideUrl;
	private String coachGuideUrl;
	private String mbrYn;
	//private String noticePmUrl;
	private String termsYn;
	private String[] welcomeGuideUrlList;
	private String[] coachGuideUrlList;
	private String noticePopMoreYn;
	private String noticePmUrl;
	private String noticePopUrl;
	private String notciePmOption;
	
	public String getNotciePmOption() {
		return notciePmOption;
	}
	public void setNotciePmOption(String notciePmOption) {
		this.notciePmOption = notciePmOption;
	}
	public String getInitAuthToken() {
		return initAuthToken;
	}
	public void setInitAuthToken(String initAuthToken) {
		this.initAuthToken = initAuthToken;
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
	public String getNoticePopMoreYn() {
		return noticePopMoreYn;
	}
	public void setNoticePopMoreYn(String noticePopMoreYn) {
		this.noticePopMoreYn = noticePopMoreYn;
	}
	public String getNoticePmUrl() {
		return noticePmUrl;
	}
	public void setNoticePmUrl(String noticePmUrl) {
		this.noticePmUrl = noticePmUrl;
	}
	public String getNoticePopUrl() {
		return noticePopUrl;
	}
	public void setNoticePopUrl(String noticePopUrl) {
		this.noticePopUrl = noticePopUrl;
	}
	public String getTermsYn() {
		return termsYn;
	}
	public void setTermsYn(String termsYn) {
		this.termsYn = termsYn;
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
/*	public String getNoticeOption() {
		return noticeOption;
	}
	public void setNoticeOption(String noticeOption) {
		this.noticeOption = noticeOption;
	}*/
/*	public String getNoticeUrl() {
		return noticeUrl;
	}
	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}*/
	public String getJoinGuideUrl() {
		return joinGuideUrl;
	}
	public void setJoinGuideUrl(String joinGuideUrl) {
		this.joinGuideUrl = joinGuideUrl;
	}
/*	public String getWelcomeGuideUrl() {
		return welcomeGuideUrl;
	}
	public void setWelcomeGuideUrl(String welcomeGuideUrl) {
		this.welcomeGuideUrl = welcomeGuideUrl;
	}
	public String getCoachGuideUrl() {
		return coachGuideUrl;
	}
	public void setCoachGuideUrl(String coachGuideUrl) {
		this.coachGuideUrl = coachGuideUrl;
	}*/
	public String getMbrYn() {
		return mbrYn;
	}
	public void setMbrYn(String mbrYn) {
		this.mbrYn = mbrYn;
	}
/*	public String getNoticePmUrl() {
		return noticePmUrl;
	}
	public void setNoticePmUrl(String noticePmUrl) {
		this.noticePmUrl = noticePmUrl;
	}
*/
	public String getPrivacyPolicyUrl() {
		return privacyPolicyUrl;
	}
	public void setPrivacyPolicyUrl(String privacyPolicyUrl) {
		this.privacyPolicyUrl = privacyPolicyUrl;
	}
	
}
