package com.kt.giga.home.infra.domain.silk;

import com.kt.giga.home.infra.domain.commons.HttpRestObjectRequest;

public class OllehRtrmmb extends HttpRestObjectRequest {

	private int seq;
	private String prFlag;
	private String triggerDate;
	private String receivedDate;
	private int procRslt;			// 1:최초등록시, 2:연동완료, 3:연동실패
	private String virtlContId;
	private String credentialId;
	private String loginId;
	private String rtrmmbDt;	
	private String loginIdTypeCd;	// 01:olleh, 02:qook, 03:show

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getPrFlag() {
		return prFlag;
	}

	public void setPrFlag(String prFlag) {
		this.prFlag = prFlag;
	}

	public String getTriggerDate() {
		return triggerDate;
	}

	public void setTriggerDate(String triggerDate) {
		this.triggerDate = triggerDate;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}

	public int getProcRslt() {
		return procRslt;
	}

	public void setProcRslt(int procRslt) {
		this.procRslt = procRslt;
	}

	public String getVirtlContId() {
		return virtlContId;
	}

	public void setVirtlContId(String virtlContId) {
		this.virtlContId = virtlContId;
	}

	public String getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getRtrmmbDt() {
		return rtrmmbDt;
	}

	public void setRtrmmbDt(String rtrmmbDt) {
		this.rtrmmbDt = rtrmmbDt;
	}

	public String getLoginIdTypeCd() {
		return loginIdTypeCd;
	}

	public void setLoginIdTypeCd(String loginIdTypeCd) {
		this.loginIdTypeCd = loginIdTypeCd;
	}	
}
