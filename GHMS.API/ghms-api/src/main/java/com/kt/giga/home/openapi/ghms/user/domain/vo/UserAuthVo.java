package com.kt.giga.home.openapi.ghms.user.domain.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 사용자 인증 VO
 * 
 * @author 
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class UserAuthVo {
	
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
	 * 1:성공, 2:다중로그인, 3:로그인 실패
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
     * 서비스대상접속설정내역 여부('Y':존재, 'N':미존재)
     */
    private String svcSetupYn;
    
    /**
     * 모든 허브 서비스 중지 여부 ('Y','N')
     */
    private String stopServiceYn;
    
    /**
     * 임의 고객 여부 ('Y','N')
     */
    private String anyCustYn;
    
    /**
     * AP 청약 3개 이상 고객 여부 ('Y','N')
     */
    private String apSubsMoreYn;
    
    /** 샌싱태그 리스트 */
    private List<SnsnTagBasVo> snsnTagBasVo;
    
    public String getSvcSetupYn() {
        return svcSetupYn;
    }
    
    public void setSvcSetupYn(String svcSetupYn) {
        this.svcSetupYn = svcSetupYn;
    }

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

	public String getStopServiceYn() {
		return stopServiceYn;
	}

	public void setStopServiceYn(String stopServiceYn) {
		this.stopServiceYn = stopServiceYn;
	}
	
	public String getAnyCustYn() {
		return anyCustYn;
	}

	public void setAnyCustYn(String anyCustYn) {
		this.anyCustYn = anyCustYn;
	}

	public String getApSubsMoreYn() {
		return apSubsMoreYn;
	}

	public void setApSubsMoreYn(String apSubsMoreYn) {
		this.apSubsMoreYn = apSubsMoreYn;
	}

	public List<SnsnTagBasVo> getSnsnTagBasVo() {
		return snsnTagBasVo;
	}

	public void setSnsnTagBasVo(List<SnsnTagBasVo> snsnTagBasVo) {
		this.snsnTagBasVo = snsnTagBasVo;
	}

}
