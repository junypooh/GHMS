package com.kt.giga.home.openapi.domain;


/**
 * 실시간상태데이터갱신내역 클래스
 * 
 * @author 조상현
 *
 */
public class RtimeSttusDataUpdTxn {
	private int		svc_tgt_seq;		// 서비스대상일련번호	// int
	private int		spot_dev_seq;		// 현장장치일련번호		// int
	private String	snsn_tag_dtl_cd;	// 센싱태그상세코드
	private String	upd_dt;				// 갱신일시
	private int		msr_val;			// 측정값				// int
	
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
	 * @return 현장장치일련번호
	 */
	public int getSpot_dev_seq() {
		return spot_dev_seq;
	}
	/**
	 * @param 현장장치일련번호
	 */
	public void setSpot_dev_seq(int spot_dev_seq) {
		this.spot_dev_seq = spot_dev_seq;
	}
	/**
	 * @return 센싱태그상세코드
	 */
	public String getSnsn_tag_dtl_cd() {
		return snsn_tag_dtl_cd;
	}
	/**
	 * @param 센싱태그상세코드
	 */
	public void setSnsn_tag_dtl_cd(String snsn_tag_dtl_cd) {
		this.snsn_tag_dtl_cd = snsn_tag_dtl_cd;
	}
	/**
	 * @return 갱신일시
	 */
	public String getUpd_dt() {
		return upd_dt;
	}
	/**
	 * @param 갱신일시
	 */
	public void setUpd_dt(String upd_dt) {
		this.upd_dt = upd_dt;
	}
	/**
	 * @return 측정값
	 */
	public int getMsr_val() {
		return msr_val;
	}
	/**
	 * @param 측정값
	 */
	public void setMsr_val(int msr_val) {
		this.msr_val = msr_val;
	}
}
