package com.kt.giga.home.openapi.domain;

/**
 * 서비스대상 접속기본 클래스
 * 
 * @author 조상현
 *
 */
public class SvcTgtConnBas {
	private String	conn_terml_id;    // 접속단말 아이디	
	private String	mbr_seq;          // 회원 일련번호	
	private String	dstr_cd;          // 지역 코드	
	private String	svc_theme_cd;     // 서비스 테마코드	
	private String	unit_svc_cd;      // 단위서비스 코드	
	private String	svc_tgt_conn_nm;  // 서비스대상 접속명
	private String	pns_reg_id;       // PNS 등록아이디	
	private String	appl_ver;         // 애플리케이션 버전
	private String	tel_no;           // 전화번호	
	
	/**
	 * @return 접속단말 아이디	
	 */
	public String getConn_terml_id() {
		return conn_terml_id;
	}
	/**
	 * @param 접속단말 아이디	
	 */
	public void setConn_terml_id(String conn_terml_id) {
		this.conn_terml_id = conn_terml_id;
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
	 * @return 지역 코드
	 */
	public String getDstr_cd() {
		return dstr_cd;
	}
	/**
	 * @param 지역 코드
	 */
	public void setDstr_cd(String dstr_cd) {
		this.dstr_cd = dstr_cd;
	}
	/**
	 * @return 서비스 테마코드
	 */
	public String getSvc_theme_cd() {
		return svc_theme_cd;
	}
	/**
	 * @param 서비스 테마코드
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
	 * @return 서비스대상 접속명
	 */
	public String getSvc_tgt_conn_nm() {
		return svc_tgt_conn_nm;
	}
	/**
	 * @param 서비스대상 접속명
	 */
	public void setSvc_tgt_conn_nm(String svc_tgt_conn_nm) {
		this.svc_tgt_conn_nm = svc_tgt_conn_nm;
	}
	/**
	 * @return PNS 등록아이디
	 */
	public String getPns_reg_id() {
		return pns_reg_id;
	}
	/**
	 * @param PNS 등록아이디
	 */
	public void setPns_reg_id(String pns_reg_id) {
		this.pns_reg_id = pns_reg_id;
	}
	/**
	 * @return 애플리케이션 버전
	 */
	public String getAppl_ver() {
		return appl_ver;
	}
	/**
	 * @param 애플리케이션 버전
	 */
	public void setAppl_ver(String appl_ver) {
		this.appl_ver = appl_ver;
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

}
