package com.kt.giga.home.openapi.cms.domain;


/**
 * 약관 동의 클래스
 * 
 * @author 조상현
 *
 */
public class TermsAgree {
	private int 	pk_terms_agree;		// 약관동의 pk
	private long 	mbr_seq;        	// 고객 번호
	private int 	fk_terms;        	// 약관 목록 fk
	private String 	fd_agree_yn;    	// 동의 여부 YN
	private String 	fd_agree_date;   	// 동의 날짜
	private String 	fk_cp_code; 	  	// 서비스 구분 코드
	private String 	fk_terms_code; 	  	// 약관 구분 코드
	private String 	fd_retract_date; 	// 약관동의 취소 날짜
	
	
	/**
	 * @return 약관동의 pk
	 */
	public int getPk_terms_agree() {
		return pk_terms_agree;
	}
	/**
	 * @param 약관동의 pk
	 */
	public void setPk_terms_agree(int pk_terms_agree) {
		this.pk_terms_agree = pk_terms_agree;
	}
	/**
	 * @return 고객 번호
	 */
	public long getMbr_seq() {
		return mbr_seq;
	}
	/**
	 * @param 고객 번호
	 */
	public void setMbr_seq(long mbr_seq) {
		this.mbr_seq = mbr_seq;
	}
	/**
	 * @return 약관 목록 fk
	 */
	public int getFk_terms() {
		return fk_terms;
	}
	/**
	 * @param 약관 목록 fk
	 */
	public void setFk_terms(int fk_terms) {
		this.fk_terms = fk_terms;
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
	/**
	 * @return 약관 구분 코드
	 */
	public String getFk_terms_code() {
		return fk_terms_code;
	}
	/**
	 * @param 약관 구분 코드
	 */
	public void setFk_terms_code(String fk_terms_code) {
		this.fk_terms_code = fk_terms_code;
	}
	/**
	 * @return 약관동의 취소날짜
	 */
	public String getFd_retract_date() {
		return fd_retract_date;
	}
	/**
	 * @param 약관동의 취소날짜
	 */
	public void setFd_retract_date(String fd_retract_date) {
		this.fd_retract_date = fd_retract_date;
	}
	
}
