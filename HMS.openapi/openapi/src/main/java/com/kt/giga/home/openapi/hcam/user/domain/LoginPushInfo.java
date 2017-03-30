package com.kt.giga.home.openapi.hcam.user.domain;

public class LoginPushInfo {
	private String pnsRegId;
	private String telNo;
	private long svcTgtSeq;
	
	public String getPnsRegId() {
		return pnsRegId;
	}
	public void setPnsRegId(String pnsRegId) {
		this.pnsRegId = pnsRegId;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public long getSvcTgtSeq() {
		return svcTgtSeq;
	}
	public void setSvcTgtSeq(long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}
	
	
}
