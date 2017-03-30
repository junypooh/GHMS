package com.kt.giga.home.infra.domain.idms;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.kt.giga.home.infra.domain.commons.HttpRestObjectRequest;

@JsonPropertyOrder({
	"saId",
	"custid",
	"historySeq",
	"intfCode",
	"prodCode",
	"prodDtlCode",
	"woNo",
	"insDate",
	"custName",
	"serviceNo",
	"cctvModelName",
	"cctvModelCd",
	"cctvSaid",
	"cctvMac",
	"cctvSerial",
	"cctvSecret",
	"cctvCode",
	"cctvLoc",
	"idmsInsDate",
	"dataReset",	
	"rollbackState",
	"infraInsDate"
})
public class SubscriptionInfoRequest extends HttpRestObjectRequest {

	private String saId;
	private String custid;
	private int historySeq;
	private String intfCode;
	private String prodCode;
	private String prodDtlCode;
	private String woNo;
	private String insDate;
	private String custName;
	private String serviceNo;
	private String cctvModelName;
	private String cctvModelCd;
	private String cctvSaid;
	private String cctvMac;
	private String cctvSerial;
	private String cctvSecret;
	private String cctvCode;
	private String cctvLoc;
	private String idmsInsDate;
	private String dataReset;	
	private String rollbackState;
	private String infraInsDate;
	private String custState;

	public String getSaId() {
		return saId;
	}

	public void setSaId(String saId) {
		this.saId = saId;
	}
	
	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public int getHistorySeq() {
		return historySeq;
	}

	public void setHistorySeq(int historySeq) {
		this.historySeq = historySeq;
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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getServiceNo() {
		return serviceNo;
	}

	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
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

	public String getInfraInsDate() {
		return infraInsDate;
	}

	public void setInfraInsDate(String infraInsDate) {
		this.infraInsDate = infraInsDate;
	}

	public String getCustState() {
		return custState;
	}

	public void setCustState(String custState) {
		this.custState = custState;
	}	
}
