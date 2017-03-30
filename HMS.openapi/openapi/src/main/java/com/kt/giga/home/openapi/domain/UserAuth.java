package com.kt.giga.home.openapi.domain;

public class UserAuth {
	
	private String newAuthToken;
	private String noticeOption;
	private String termsYn;
	private String kidsCheckOption;
	private String loginResult;
	private String age;
	private String subsYn;
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSubsYn() {
		return subsYn;
	}

	public void setSubsYn(String subsYn) {
		this.subsYn = subsYn;
	}

	public String getTermsYn() {
		return termsYn;
	}

	public void setTermsYn(String termsYn) {
		this.termsYn = termsYn;
	}

	public String getLoginResult() {
		return loginResult;
	}

	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}

	public String getNewAuthToken() {
		return newAuthToken;
	}

	public void setNewAuthToken(String newAuthToken) {
		this.newAuthToken = newAuthToken;
	}

	public String getNoticeOption() {
		return noticeOption;
	}

	public void setNoticeOption(String noticeOption) {
		this.noticeOption = noticeOption;
	}

	public String getKidsCheckOption() {
		return kidsCheckOption;
	}

	public void setKidsCheckOption(String kidsCheckOption) {
		this.kidsCheckOption = kidsCheckOption;
	}

}
