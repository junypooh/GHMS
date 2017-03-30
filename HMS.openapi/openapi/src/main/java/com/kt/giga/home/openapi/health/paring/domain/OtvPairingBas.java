package com.kt.giga.home.openapi.health.paring.domain;


/**
 * OtvPairingBas(OTV 페어링 기본) 클래스
 * 
 * @author 조상현
 *
 */

public class OtvPairingBas {
	private String	otv_svc_cont_id;		// OTV서비스 계약아이디
	private String	mbr_seq;				// 회원 일련번호
	private String	dstr_cd;				// 지역코드
	private String	svc_theme_cd;			// 서비스테마 코드
	private String	unit_svc_cd;			// 단위서비스 코드
	private String	tel_no;					// 전화번호
	private String	otv_app_id;				// OTV 앱아이디
	private String	stb_nick_nm;			// STB 닉네임
	private String	cret_dt;				// 생성일시
	private String	amd_dt;					// 수정일시
	
	/**
	 * @return OTV서비스 계약아이디
	 */
	public String getOtv_svc_cont_id() {
		return otv_svc_cont_id;
	}
	/**
	 * @param OTV서비스 계약아이디
	 */
	public void setOtv_svc_cont_id(String otv_svc_cont_id) {
		this.otv_svc_cont_id = otv_svc_cont_id;
	}
	/**
	 * @return 회원 일련번호
	 */
	public String getMbr_seq() {
		return mbr_seq;
	}
	/**
	 * @param 회원 일련번호
	 */
	public void setMbr_seq(String mbr_seq) {
		this.mbr_seq = mbr_seq;
	}
	/**
	 * @return 지역코드
	 */
	public String getDstr_cd() {
		return dstr_cd;
	}
	/**
	 * @param 지역코드
	 */
	public void setDstr_cd(String dstr_cd) {
		this.dstr_cd = dstr_cd;
	}
	/**
	 * @return 서비스테마 코드
	 */
	public String getSvc_theme_cd() {
		return svc_theme_cd;
	}
	/**
	 * @param 서비스테마 코드
	 */
	public void setSvc_theme_cd(String svc_theme_cd) {
		this.svc_theme_cd = svc_theme_cd;
	}
	/**
	 * @return 단위서비스 코드
	 */
	public String getUnit_svc_cd() {
		return unit_svc_cd;
	}
	/**
	 * @param 단위서비스 코드
	 */
	public void setUnit_svc_cd(String unit_svc_cd) {
		this.unit_svc_cd = unit_svc_cd;
	}
	/**
	 * @return 전화번호
	 */
	public String getTel_no() {
		return tel_no;
	}
	/**
	 * @param 전화번호
	 */
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}
	/**
	 * @return OTV 앱아이디
	 */
	public String getOtv_app_id() {
		return otv_app_id;
	}
	/**
	 * @param OTV 앱아이디
	 */
	public void setOtv_app_id(String otv_app_id) {
		this.otv_app_id = otv_app_id;
	}
	/**
	 * @return STB 닉네임
	 */
	public String getStb_nick_nm() {
		return stb_nick_nm;
	}
	/**
	 * @param STB 닉네임
	 */
	public void setStb_nick_nm(String stb_nick_nm) {
		this.stb_nick_nm = stb_nick_nm;
	}
	/**
	 * @return 생성일시
	 */
	public String getCret_dt() {
		return cret_dt;
	}
	/**
	 * @param 생성일시
	 */
	public void setCret_dt(String cret_dt) {
		this.cret_dt = cret_dt;
	}
	/**
	 * @return 수정일시
	 */
	public String getAmd_dt() {
		return amd_dt;
	}
	/**
	 * @param 수정일시
	 */
	public void setAmd_dt(String amd_dt) {
		this.amd_dt = amd_dt;
	}
	
	
}
