package com.kt.giga.home.openapi.domain;

/**
 * 서비스대상기본 클래스
 * 
 * @author 조상현
 *
 */
public class SvcTgtBas {
	private int		svc_tgt_seq;		// 서비스대상일련번호	// int
	private String	mbr_seq;			// 회원 일련번호
	private Integer mbrs_seq;           // 회원 일련번호 변경전
	private String	dstr_cd;			// 지역코드
	private String	svc_theme_cd;		// 서비스테마코드
	private String	unit_svc_cd;		// 단위서비스코드
	private String	svc_cont_id;		// 서비스계약아이디(청약said)
	private String	svc_tgt_id;			// 서비스대상아이디
	private String	svc_tgt_type_cd;	// 서비스대상유형코드
	private String	svc_tgt_nm;			// 서비스대상명
	private String	oprt_sttus_cd;		// 운영상태코드
	private String	dtl_info_yn;		// 상세정보여부
	private String	old_zipcd;			// 구우편번호
	private String	old_adr1;			// 구주소1
	private String	old_adr2;			// 구주소2
	private String	new_bldg_mgt_no;	// 신규건물관리번호
	private String	new_zipcd;			// 신규우편번호
	private String	new_adr1;			// 신규주소1
	private String	new_adr2;			// 신규주소2
	private String	del_yn;				// 삭제여부
	private String	cretr_id;			// 생성자아이디
	private String	cret_dt;			// 생성일시
	private String	amdr_id;			// 수정자아이디
	private String	amd_dt;				// 수정일시
	private String	org_cd;				// 조직코드

	public Integer getMbrs_seq() {
		return mbrs_seq;
	}
	public void setMbrs_seq(Integer mbrs_seq) {
		this.mbrs_seq = mbrs_seq;
	}
	/**
	 * @return 서비스대상일련번호
	 */
	public int getSvc_tgt_seq() {
		return svc_tgt_seq;
	}
	/**
	 * @param 서비스대상일련번호
	 */
	public void setSvc_tgt_seq(int svc_tgt_seq) {
		this.svc_tgt_seq = svc_tgt_seq;
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
	 * @return 서비스테마코드
	 */
	public String getSvc_theme_cd() {
		return svc_theme_cd;
	}
	/**
	 * @param 서비스테마코드
	 */
	public void setSvc_theme_cd(String svc_theme_cd) {
		this.svc_theme_cd = svc_theme_cd;
	}
	/**
	 * @return 단위서비스코드
	 */
	public String getUnit_svc_cd() {
		return unit_svc_cd;
	}
	/**
	 * @param 단위서비스코드
	 */
	public void setUnit_svc_cd(String unit_svc_cd) {
		this.unit_svc_cd = unit_svc_cd;
	}
	/**
	 * @return 서비스계약아이디(청약said)
	 */
	public String getSvc_cont_id() {
		return svc_cont_id;
	}
	/**
	 * @param 서비스계약아이디(청약said)
	 */
	public void setSvc_cont_id(String svc_cont_id) {
		this.svc_cont_id = svc_cont_id;
	}
	/**
	 * @return 서비스대상아이디
	 */
	public String getSvc_tgt_id() {
		return svc_tgt_id;
	}
	/**
	 * @param 서비스대상아이디
	 */
	public void setSvc_tgt_id(String svc_tgt_id) {
		this.svc_tgt_id = svc_tgt_id;
	}
	/**
	 * @return 서비스대상유형코드
	 */
	public String getSvc_tgt_type_cd() {
		return svc_tgt_type_cd;
	}
	/**
	 * @param 서비스대상유형코드
	 */
	public void setSvc_tgt_type_cd(String svc_tgt_type_cd) {
		this.svc_tgt_type_cd = svc_tgt_type_cd;
	}
	/**
	 * @return 서비스대상명
	 */
	public String getSvc_tgt_nm() {
		return svc_tgt_nm;
	}
	/**
	 * @param 서비스대상명
	 */
	public void setSvc_tgt_nm(String svc_tgt_nm) {
		this.svc_tgt_nm = svc_tgt_nm;
	}
	/**
	 * @return 운영상태코드
	 */
	public String getOprt_sttus_cd() {
		return oprt_sttus_cd;
	}
	/**
	 * @param 운영상태코드
	 */
	public void setOprt_sttus_cd(String oprt_sttus_cd) {
		this.oprt_sttus_cd = oprt_sttus_cd;
	}
	/**
	 * @return 상세정보여부
	 */
	public String getDtl_info_yn() {
		return dtl_info_yn;
	}
	/**
	 * @param 상세정보여부
	 */
	public void setDtl_info_yn(String dtl_info_yn) {
		this.dtl_info_yn = dtl_info_yn;
	}
	/**
	 * @return 구우편번호
	 */
	public String getOld_zipcd() {
		return old_zipcd;
	}
	/**
	 * @param 구우편번호
	 */
	public void setOld_zipcd(String old_zipcd) {
		this.old_zipcd = old_zipcd;
	}
	/**
	 * @return 구주소1
	 */
	public String getOld_adr1() {
		return old_adr1;
	}
	/**
	 * @param 구주소1
	 */
	public void setOld_adr1(String old_adr1) {
		this.old_adr1 = old_adr1;
	}
	/**
	 * @return 구주소2
	 */
	public String getOld_adr2() {
		return old_adr2;
	}
	/**
	 * @param 구주소2
	 */
	public void setOld_adr2(String old_adr2) {
		this.old_adr2 = old_adr2;
	}
	/**
	 * @return 신규건물관리번호
	 */
	public String getNew_bldg_mgt_no() {
		return new_bldg_mgt_no;
	}
	/**
	 * @param 신규건물관리번호
	 */
	public void setNew_bldg_mgt_no(String new_bldg_mgt_no) {
		this.new_bldg_mgt_no = new_bldg_mgt_no;
	}
	/**
	 * @return 신규우편번호
	 */
	public String getNew_zipcd() {
		return new_zipcd;
	}
	/**
	 * @param 신규우편번호
	 */
	public void setNew_zipcd(String new_zipcd) {
		this.new_zipcd = new_zipcd;
	}
	/**
	 * @return 신규주소1
	 */
	public String getNew_adr1() {
		return new_adr1;
	}
	/**
	 * @param 신규주소1
	 */
	public void setNew_adr1(String new_adr1) {
		this.new_adr1 = new_adr1;
	}
	/**
	 * @return 신규주소2
	 */
	public String getNew_adr2() {
		return new_adr2;
	}
	/**
	 * @param 신규주소2
	 */
	public void setNew_adr2(String new_adr2) {
		this.new_adr2 = new_adr2;
	}
	/**
	 * @return 삭제여부
	 */
	public String getDel_yn() {
		return del_yn;
	}
	/**
	 * @param 삭제여부
	 */
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	/**
	 * @return 생성자아이디
	 */
	public String getCretr_id() {
		return cretr_id;
	}
	/**
	 * @param 생성자아이디
	 */
	public void setCretr_id(String cretr_id) {
		this.cretr_id = cretr_id;
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
	 * @return 수정자아이디
	 */
	public String getAmdr_id() {
		return amdr_id;
	}
	/**
	 * @param 수정자아이디
	 */
	public void setAmdr_id(String amdr_id) {
		this.amdr_id = amdr_id;
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
	/**
	 * @return 조직코드
	 */
	public String getOrg_cd() {
		return org_cd;
	}
	/**
	 * @param 조직코드
	 */
	public void setOrg_cd(String org_cd) {
		this.org_cd = org_cd;
	}

}
