package com.kt.giga.home.infra.domain.ucems;

import javax.xml.bind.annotation.XmlElement;

public class ResultInfo {
	private String resultCode;	
	private String resultMsg;
	private String requestDT;
	private String newVersion;
	private String stateCode;
	private String stateDesc;
	private String startDate;

	public String getResultCode() {
		return resultCode;
	}

	@XmlElement(name="ResultCode")
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	@XmlElement(name="ResultMsg")
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getRequestDT() {
		return requestDT;
	}

	@XmlElement(name="RequestDT")
	public void setRequestDT(String requestDT) {
		this.requestDT = requestDT;
	}

	public String getNewVersion() {
		return newVersion;
	}

	@XmlElement(name="NewVersion")
	public void setNewVersion(String newVersion) {
		this.newVersion = newVersion;
	}

	public String getStateCode() {
		return stateCode;
	}

	@XmlElement(name="StateCode")
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateDesc() {
		return stateDesc;
	}

	@XmlElement(name="StateDesc")
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}

	public String getStartDate() {
		return startDate;
	}

	@XmlElement(name="StartDate")
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}

