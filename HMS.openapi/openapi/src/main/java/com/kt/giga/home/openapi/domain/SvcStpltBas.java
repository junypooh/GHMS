package com.kt.giga.home.openapi.domain;


/**
 * 서비스약관 기본 클래스
 * 
 * @author 조상현
 *
 */
public class SvcStpltBas {
//	stpltSeq	
//	stpltVer	
//	stpltTitleNm
//	stpltSbst	
//	mandAgreeYn	
//	stpltAgreeYn

	private int		stplt_seq;			// 약관일련번호		// int
	private String	stplt_title_nm;		// 약관제목명
	private String	stplt_sbst;			// 약관내용
	private String	mand_agree_yn;		// 필수동의여부
	
	
	/**
	 * @return 약관일련번호
	 */
	public int getStplt_seq() {
		return stplt_seq;
	}
	/**
	 * @param 약관일련번호
	 */
	public void setStplt_seq(int stplt_seq) {
		this.stplt_seq = stplt_seq;
	}
	/**
	 * @return 약관제목명
	 */
	public String getStplt_title_nm() {
		return stplt_title_nm;
	}
	/**
	 * @param 약관제목명
	 */
	public void setStplt_title_nm(String stplt_title_nm) {
		this.stplt_title_nm = stplt_title_nm;
	}
	/**
	 * @return 약관내용
	 */
	public String getStplt_sbst() {
		return stplt_sbst;
	}
	/**
	 * @param 약관내용
	 */
	public void setStplt_sbst(String stplt_sbst) {
		this.stplt_sbst = stplt_sbst;
	}
	/**
	 * @return 필수동의여부
	 */
	public String getMand_agree_yn() {
		return mand_agree_yn;
	}
	/**
	 * @param 필수동의여부
	 */
	public void setMand_agree_yn(String mand_agree_yn) {
		this.mand_agree_yn = mand_agree_yn;
	}
	
	
}
