package com.kt.giga.home.openapi.cms.domain;


/**
 * 기초코드 클래스
 * 
 * @author 조상현
 *
 */

public class Code {
	private String	pk_code;				// 기초코드
	private String	fd_up_code;				// 상위코드
	private String	fd_name;				// 코드이름
	private String	fd_use_yn;				// 코드 사용여부 YN
	private String	fd_memo;				// 코드 설명
	
	
	/**
	 * @return 기초코드
	 */
	public String getPk_code() {
		return pk_code;
	}
	/**
	 * @param 기초코드
	 */
	public void setPk_code(String pk_code) {
		this.pk_code = pk_code;
	}
	/**
	 * @return 상위코드
	 */
	public String getFd_up_code() {
		return fd_up_code;
	}
	/**
	 * @param 상위코드
	 */
	public void setFd_up_code(String fd_up_code) {
		this.fd_up_code = fd_up_code;
	}
	/**
	 * @return 코드이름
	 */
	public String getFd_name() {
		return fd_name;
	}
	/**
	 * @param 코드이름
	 */
	public void setFd_name(String fd_name) {
		this.fd_name = fd_name;
	}
	/**
	 * @return 코드 사용여부 YN
	 */
	public String getFd_use_yn() {
		return fd_use_yn;
	}
	/**
	 * @param 코드 사용여부 YN
	 */
	public void setFd_use_yn(String fd_use_yn) {
		this.fd_use_yn = fd_use_yn;
	}
	/**
	 * @return 코드 설명
	 */
	public String getFd_memo() {
		return fd_memo;
	}
	/**
	 * @param 코드 설명
	 */
	public void setFd_memo(String fd_memo) {
		this.fd_memo = fd_memo;
	}


}
