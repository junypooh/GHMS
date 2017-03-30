package com.kt.giga.home.openapi.domain;

public class HomeProvision {
	private String saId;            // 홈 모니터링 서비스 계약 ID
	private String intfCode;        // ICIS에서 전달된 상품코드
	private String prodCode;        // 세부상품 구분 코드
	private String prodDtlCode;     // 오더 번호
	private String woNo;            // 청약 신청일시(접수 기준)
	private String insDate;         // 고객ID (ICIS 원천고객ID)
	private String custid;          // 고객 이름
	private String custName;        // 홈 CCTV 단말 모델명
	private String cctvModelName;   // 홈 CCTV 단말 모델코드(펌웨어 업그래이드 확인)
	private String cctvModelCd;     // 홈 모니터링 카메라 단말 SAID
	private String cctvSaid;        // 홈 CCTV 단말 MAC 주소
	private String cctvMac;         // 홈 CCTV 단말 MAC 주소
	private String cctvSerial;      // 홈 CCTV 단말 SERIAL 넘버
	private String cctvSecret;      // 단말 인증을 위한 비밀번호
	private String cctvCode;        // 홈 모니터링 서비스 서버 처리 유형(청약상태)
	private String cctvLoc;         // 청약시 고객이 지정한 카메라 설치위치
	private String idmsInsDate;     // IDMS 오더 입력 시간
	private String dataReset;       // 롤백 여부
	private String rollbackState;   // 롤백 이전의 청약 상태
	
	public String getSaId() {
		return saId;
	}
	public void setSaId(String saId) {
		this.saId = saId;
	}
	public String getIntfCode() {
		return intfCode;
	}
	public void setIntfCode(String intfCode) {
		this.intfCode = intfCode;
	}
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getProdDtlCode() {
		return prodDtlCode;
	}
	public void setProdDtlCode(String prodDtlCode) {
		this.prodDtlCode = prodDtlCode;
	}
	public String getWoNo() {
		return woNo;
	}
	public void setWoNo(String woNo) {
		this.woNo = woNo;
	}
	public String getInsDate() {
		return insDate;
	}
	public void setInsDate(String insDate) {
		this.insDate = insDate;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCctvModelName() {
		return cctvModelName;
	}
	public void setCctvModelName(String cctvModelName) {
		this.cctvModelName = cctvModelName;
	}
	public String getCctvModelCd() {
		return cctvModelCd;
	}
	public void setCctvModelCd(String cctvModelCd) {
		this.cctvModelCd = cctvModelCd;
	}
	public String getCctvSaid() {
		return cctvSaid;
	}
	public void setCctvSaid(String cctvSaid) {
		this.cctvSaid = cctvSaid;
	}
	public String getCctvMac() {
		return cctvMac;
	}
	public void setCctvMac(String cctvMac) {
		this.cctvMac = cctvMac;
	}
	public String getCctvSerial() {
		return cctvSerial;
	}
	public void setCctvSerial(String cctvSerial) {
		this.cctvSerial = cctvSerial;
	}
	public String getCctvSecret() {
		return cctvSecret;
	}
	public void setCctvSecret(String cctvSecret) {
		this.cctvSecret = cctvSecret;
	}
	public String getCctvCode() {
		return cctvCode;
	}
	public void setCctvCode(String cctvCode) {
		this.cctvCode = cctvCode;
	}
	public String getCctvLoc() {
		return cctvLoc;
	}
	public void setCctvLoc(String cctvLoc) {
		this.cctvLoc = cctvLoc;
	}
	public String getIdmsInsDate() {
		return idmsInsDate;
	}
	public void setIdmsInsDate(String idmsInsDate) {
		this.idmsInsDate = idmsInsDate;
	}
	public String getDataReset() {
		return dataReset;
	}
	public void setDataReset(String dataReset) {
		this.dataReset = dataReset;
	}
	public String getRollbackState() {
		return rollbackState;
	}
	public void setRollbackState(String rollbackState) {
		this.rollbackState = rollbackState;
	}

	
}
