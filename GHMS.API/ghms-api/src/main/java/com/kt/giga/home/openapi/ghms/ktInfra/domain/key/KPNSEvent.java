package com.kt.giga.home.openapi.ghms.ktInfra.domain.key;


public class KPNSEvent {
	private long svc_tgt_seq;
	private long spot_dev_seq;
	private long mbr_seq;
	private String pns_reg_id;
	private String tel_no;
	private String dstr_cd;
	private String svc_theme_cd;
	private String unit_svc_cd;
	private String setup_cd;
	private long msg_seq; 
	private String msg_id;
	private String up_msg_id;
	private String msg_trm_sbst;
	private String msg_trm_fail_txn;
	private String msg_trm_forward_yn;
	private String msg_rcv_dt;
	private String msg_rcv_fail_txn;
	private String msg_rcv_yn;
	private int msg_retry_seq;

	public long getSvc_tgt_seq() {
		return svc_tgt_seq;
	}

	public void setSvc_tgt_seq(long svc_tgt_seq) {
		this.svc_tgt_seq = svc_tgt_seq;
	}

	public long getSpot_dev_seq() {
		return spot_dev_seq;
	}

	public void setSpot_dev_seq(long spot_dev_seq) {
		this.spot_dev_seq = spot_dev_seq;
	}

	public long getMbr_seq() {
		return mbr_seq;
	}

	public void setMbr_seq(long mbr_seq) {
		this.mbr_seq = mbr_seq;
	}

	public String getPns_reg_id() {
		return pns_reg_id;
	}

	public void setPns_reg_id(String pns_reg_id) {
		this.pns_reg_id = pns_reg_id;
	}

	public String getTel_no() {
		return tel_no;
	}

	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}

	public String getDstr_cd() {
		return dstr_cd;
	}

	public void setDstr_cd(String dstr_cd) {
		this.dstr_cd = dstr_cd;
	}

	public String getSvc_theme_cd() {
		return svc_theme_cd;
	}

	public void setSvc_theme_cd(String svc_theme_cd) {
		this.svc_theme_cd = svc_theme_cd;
	}

	public String getUnit_svc_cd() {
		return unit_svc_cd;
	}

	public void setUnit_svc_cd(String unit_svc_cd) {
		this.unit_svc_cd = unit_svc_cd;
	}

	public String getSetup_cd() {
		return setup_cd;
	}

	public void setSetup_cd(String setup_cd) {
		this.setup_cd = setup_cd;
	}

	public long getMsg_seq() {
		return msg_seq;
	}

	public void setMsg_seq(long msg_seq) {
		this.msg_seq = msg_seq;
	}

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}

	public String getUp_msg_id() {
		return up_msg_id;
	}

	public void setUp_msg_id(String up_msg_id) {
		this.up_msg_id = up_msg_id;
	}

	public String getMsg_trm_sbst() {
		return msg_trm_sbst;
	}

	public void setMsg_trm_sbst(String msg_trm_sbst) {
		this.msg_trm_sbst = msg_trm_sbst;
	}

	public String getMsg_trm_fail_txn() {
		return msg_trm_fail_txn;
	}

	public void setMsg_trm_fail_txn(String msg_trm_fail_txn) {
		this.msg_trm_fail_txn = msg_trm_fail_txn;
	}

	public String getMsg_trm_forward_yn() {
		return msg_trm_forward_yn;
	}

	public void setMsg_trm_forward_yn(String msg_trm_forward_yn) {
		this.msg_trm_forward_yn = msg_trm_forward_yn;
	}

	public String getMsg_rcv_dt() {
		return msg_rcv_dt;
	}

	public void setMsg_rcv_dt(String msg_rcv_dt) {
		this.msg_rcv_dt = msg_rcv_dt;
	}

	public String getMsg_rcv_fail_txn() {
		return msg_rcv_fail_txn;
	}

	public void setMsg_rcv_fail_txn(String msg_rcv_fail_txn) {
		this.msg_rcv_fail_txn = msg_rcv_fail_txn;
	}

	public String getMsg_rcv_yn() {
		return msg_rcv_yn;
	}

	public void setMsg_rcv_yn(String msg_rcv_yn) {
		this.msg_rcv_yn = msg_rcv_yn;
	}

	public int getMsg_retry_seq() {
		return msg_retry_seq;
	}

	public void setMsg_retry_seq(int msg_retry_seq) {
		this.msg_retry_seq = msg_retry_seq;
	}
}
