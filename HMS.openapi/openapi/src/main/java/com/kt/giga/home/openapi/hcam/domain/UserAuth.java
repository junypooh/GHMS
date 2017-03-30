package com.kt.giga.home.openapi.hcam.domain;

/**
 * 사용자 인증 클래스
 * 
 * @author 
 *
 */
public class UserAuth {
	
	/**
	 * 로그인 인증 토큰
	 */
	private String newAuthToken;
	
	/**
	 * 공지 옵션 - 현재 버전에서 사용하지 않음
	 */
	private String noticeOption;
	
	/**
	 * 약관동의 필요 여부 ('Y','N')
	 */
	private String termsYn;
	
	/**
	 * 14세 미만 사용자 법정 대리인 동의 필요 여부. 0:불필요, 1:필요 
	 */
	private String kidsCheckOption;
	
	/**
	 * 14세 미만 사용자 법정 대리인 동의 필요 여부. 0:불필요, 1:필요 
	 */
	private String loginResult;
	
	/**
	 * 사용자 나이
	 */
	private String age;
	
	/**
	 * 사용자의 현재 청약 여부. 'Y':청약상태, 'N':청약상태 아님
	 */
	private String subsYn;
	
	/**
	 * 전화번호 등록 여부. 'Y':등록, 'N':미등록
	 */
	private String telYn;


    private boolean isInternetOnlyID;
	
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

	public String getTelYn() {
		return telYn;
	}

	public void setTelYn(String telYn) {
		this.telYn = telYn;
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

    public boolean isInternetOnlyID() {

        return isInternetOnlyID;
    }

    public void setInternetOnlyID(boolean isInternetOnlyID) {

        this.isInternetOnlyID = isInternetOnlyID;
    }
}
