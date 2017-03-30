package com.kt.giga.home.openapi.hcam.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 사용자 로그인 클래스
 * 
 * @author 
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class Login {
	
	/**
	 * 올레 아이디
	 */
	private String userId;
	
	/**
	 * 비밀번호
	 */
	private String passwd;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}
