package com.kt.giga.home.openapi.hcam.domain;

/**
 * 제어 트랜잭션 클래
 * 
 * @author 
 *
 */
public class ControlTransaction {
	
	/**
	 * 제어 트랜잭션 아이디
	 */
	private String transactionId;
	
	/**
	 * 단위 서비스 코드
	 */
	private String unitSvcCode;
	
	/**
	 * 서비스 대상 일련번호	
	 */
	private long svcTgtSeq;
	
	/**
	 * 현장 장치 일련번호
	 */
	private long spotDevSeq;
	
	/**
	 * 센싱 태그 코드
	 */
	private String snsnTagCd;
	
	/**
	 * 실시간 제어 여부
	 */
	private String rtimeControlYn;
	
	/**
	 * 제어 발생자 아이디
	 */
	private String occrId;
	
	/**
	 * 제어 처리 상태 코드
	 */
	private String statusCode;
	
	/**
	 * 제어 값
	 */
	private String controlValue;
	
	/**
	 * 제어 종료 여부
	 */
	private String fnsYn; 
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getUnitSvcCode() {
		return unitSvcCode;
	}
	public void setUnitSvcCode(String unitSvcCode) {
		this.unitSvcCode = unitSvcCode;
	}
	public long getSpotDevSeq() {
		return spotDevSeq;
	}
	public void setSpotDevSeq(long spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}
	public long getSvcTgtSeq() {
		return svcTgtSeq;
	}
	public void setSvcTgtSeq(long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}
	public String getSnsnTagCd() {
		return snsnTagCd;
	}
	public void setSnsnTagCd(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}
	public String getRtimeControlYn() {
		return rtimeControlYn;
	}
	public void setRtimeControlYn(String rtimeControlYn) {
		this.rtimeControlYn = rtimeControlYn;
	}
	public String getOccrId() {
		return occrId;
	}
	public void setOccrId(String occrId) {
		this.occrId = occrId;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getControlValue() {
		return controlValue;
	}
	public void setControlValue(String controlValue) {
		this.controlValue = controlValue;
	}
	public String getFnsYn() {
		return fnsYn;
	}
	public void setFnsYn(String fnsYn) {
		this.fnsYn = fnsYn;
	}
	

//	  contl_occ_id character varying(20) NOT NULL, -- 제어발생이력 제어발생아이디
//	  contl_transac_id character varying(50), -- 제어발생이력 제어트랜잭션아이디
//	  sttn_contl_id character varying(20) NOT NULL, -- 제어발생이력 상황제어아이디
//	  stat_evet_cd character varying(4) NOT NULL, -- 제어발생이력 상황이벤트코드
//	  unit_svc_cd character varying(3) NOT NULL, -- 제어발생이력 단위서비스코드
//	  rtime_contl_yn character varying(1), -- 제어발생이력 실시간제어여부
//	  spot_dev_seq numeric(19,0) NOT NULL, -- 제어발생이력 현장장치일련번호
//	  svc_tgt_seq numeric(10,0) NOT NULL, -- 제어발생이력 서비스대상일련번호
//	  snsn_tag_dtl_cd character varying(8) NOT NULL, -- 제어발생이력 센싱태그상세코드
//	  contl_occr_id character varying(20), -- 제어발생이력 제어발생자아이디
//	  contl_trt_sttus_cd character varying(4) NOT NULL, -- 제어발생이력 제어처리상태코드
//	  contl_val character varying(128), -- 제어발생이력 제어값
//	  occ_dt timestamp without time zone, -- 제어발생이력 발생일시
//	  fns_dt timestamp without time zone, -- 제어발생이력 종료일시
//	  fns_yn character varying(1), -- 제어발생이력 종료여부

	
}
