package com.kt.giga.home.openapi.health.paring.domain;


/**
 * MsgTrmTxn(메시지발송 내역) 클래스
 * 
 * @author 조상현
 *
 */

public class MsgTrmTxn {
	private String	conn_terml_id;			// 접속단말 아이디
	private String	mbr_seq;				// 회원 일련번호
	private String	dstr_cd;				// 지역코드
	private String	svc_theme_cd;			// 서비스테마 코드
	private String	unit_svc_cd;			// 단위서비스 코드
	private String	msg_trm_sbst;			// 메시지전송 내용
	private String	msg_trm_key_val;		// 메시지전송 키값
	private String	msg_trm_dt;				// 메시지전송 일시
	
	
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
	 * @return 메시지전송 내용
	 */
	public String getMsg_trm_sbst() {
		return msg_trm_sbst;
	}
	/**
	 * @param 메시지전송 내용
	 */
	public void setMsg_trm_sbst(String msg_trm_sbst) {
		this.msg_trm_sbst = msg_trm_sbst;
	}
	/**
	 * @return 메시지전송 키값
	 */
	public String getMsg_trm_key_val() {
		return msg_trm_key_val;
	}
	/**
	 * @param 메시지전송 키값
	 */
	public void setMsg_trm_key_val(String msg_trm_key_val) {
		this.msg_trm_key_val = msg_trm_key_val;
	}
	/**
	 * @return 메시지전송 일시
	 */
	public String getMsg_trm_dt() {
		return msg_trm_dt;
	}
	/**
	 * @param 메시지전송 일시
	 */
	public void setMsg_trm_dt(String msg_trm_dt) {
		this.msg_trm_dt = msg_trm_dt;
	}

}
