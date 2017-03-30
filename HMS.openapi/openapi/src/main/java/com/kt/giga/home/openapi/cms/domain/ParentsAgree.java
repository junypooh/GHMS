package com.kt.giga.home.openapi.cms.domain;


/**
 * 14세 미만 법정대리인 동의 클래스
 * 
 * @author 조상현
 *
 */
public class ParentsAgree {
	private int 	pk_parents_agree;	// 법정대리인 동의 pk
	private String 	mbr_seq;        	// 고객 번호
	private String 	fd_agree_yn;    	// 동의 여부 YN
	private String 	fd_agree_date;   	// 동의 날짜
	private String 	fd_parents_name;	// 법정대리인 이름
	private String 	fd_parents_mobile;	// 법정대리인 휴대폰 번호
	private String 	fk_cp_code; 	  	// 서비스 구분 코드
	
	
	/**
	 * @return 법정대리인 동의 pk
	 */
	public int getPk_parents_agree() {
		return pk_parents_agree;
	}
	/**
	 * @param 법정대리인 동의 pk
	 */
	public void setPk_parents_agree(int pk_parents_agree) {
		this.pk_parents_agree = pk_parents_agree;
	}
	/**
	 * @return 고객 번호
	 */
	public String getMbr_seq() {
		return mbr_seq;
	}
	/**
	 * @param 고객 번호
	 */
	public void setMbr_seq(String mbr_seq) {
		this.mbr_seq = mbr_seq;
	}
	/**
	 * @return 동의 여부 YN
	 */
	public String getFd_agree_yn() {
		return fd_agree_yn;
	}
	/**
	 * @param 동의 여부 YN
	 */
	public void setFd_agree_yn(String fd_agree_yn) {
		this.fd_agree_yn = fd_agree_yn;
	}
	/**
	 * @return 동의 날짜
	 */
	public String getFd_agree_date() {
		return fd_agree_date;
	}
	/**
	 * @param 동의 날짜
	 */
	public void setFd_agree_date(String fd_agree_date) {
		this.fd_agree_date = fd_agree_date;
	}
	/**
	 * @return 법정대리인 이름
	 */
	public String getFd_parents_name() {
		return fd_parents_name;
	}
	/**
	 * @param 법정대리인 이름
	 */
	public void setFd_parents_name(String fd_parents_name) {
		this.fd_parents_name = fd_parents_name;
	}
	/**
	 * @return 법정대리인 휴대폰 번호
	 */
	public String getFd_parents_mobile() {
		return fd_parents_mobile;
	}
	/**
	 * @param 법정대리인 휴대폰 번호
	 */
	public void setFd_parents_mobile(String fd_parents_mobile) {
		this.fd_parents_mobile = fd_parents_mobile;
	}
	/**
	 * @return 서비스 구분 코드
	 */
	public String getFk_cp_code() {
		return fk_cp_code;
	}
	/**
	 * @param 서비스 구분 코드
	 */
	public void setFk_cp_code(String fk_cp_code) {
		this.fk_cp_code = fk_cp_code;
	}
}
