package com.kt.giga.home.infra.domain.idms;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.kt.giga.home.infra.domain.commons.HttpRestObjectRequest;

@JsonPropertyOrder({
	"saId",
	"custid",
	"managerHisSeq",
	"intfCode",
	"prodCode",
	"prodDtlCode",
	"woNo",
	"insDate",
	"custName",
	"serviceNo",
	"gwModelName",
	"gwModelCd",
	"gwSaid",
	"gwMac",
	"gwSerial",
	"gwSecret",
	"gwCode",
	"idmsInsDate",
	"dataReset",	
	"rollbackState",
	"infraInsDate"
})
public class SubscriptionManagerInfoRequest extends HttpRestObjectRequest {

	private String saId;
	private String custid;
	private int managerHisSeq;
	private String intfCode;
	private String prodCode;
	private String prodDtlCode;
	private String woNo;
	private String insDate;
	private String custName;
	private String serviceNo;
	private String gwModelName;
	private String gwModelCd;
	private String gwSaid;
	private String gwMac;
	private String gwSerial;
	private String gwSecret;
	private String gwCode;
//	private String gwLoc;
	private String idmsInsDate;
	private String dataReset;	
	private String rollbackState;
	private String infraInsDate;
	private String custState;
	
	
    public String getSaId() {
        return saId;
    }
    public String getCustid() {
        return custid;
    }
    public int getManagerHisSeq() {
        return managerHisSeq;
    }
    public String getIntfCode() {
        return intfCode;
    }
    public String getProdCode() {
        return prodCode;
    }
    public String getProdDtlCode() {
        return prodDtlCode;
    }
    public String getWoNo() {
        return woNo;
    }
    public String getInsDate() {
        return insDate;
    }
    public String getCustName() {
        return custName;
    }
    public String getServiceNo() {
        return serviceNo;
    }
    public String getGwModelName() {
        return gwModelName;
    }
    public String getGwModelCd() {
        return gwModelCd;
    }
    public String getGwSaid() {
        return gwSaid;
    }
    public String getGwMac() {
        return gwMac;
    }
    public String getGwSerial() {
        return gwSerial;
    }
    public String getGwCode() {
        return gwCode;
    }
    public String getIdmsInsDate() {
        return idmsInsDate;
    }
    public String getDataReset() {
        return dataReset;
    }
    public String getRollbackState() {
        return rollbackState;
    }
    public String getInfraInsDate() {
        return infraInsDate;
    }
    public String getCustState() {
        return custState;
    }
    public void setSaId(String saId) {
        this.saId = saId;
    }
    public void setCustid(String custid) {
        this.custid = custid;
    }
    public void setManagerHisSeq(int managerHisSeq) {
        this.managerHisSeq = managerHisSeq;
    }
    public void setIntfCode(String intfCode) {
        this.intfCode = intfCode;
    }
    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }
    public void setProdDtlCode(String prodDtlCode) {
        this.prodDtlCode = prodDtlCode;
    }
    public void setWoNo(String woNo) {
        this.woNo = woNo;
    }
    public void setInsDate(String insDate) {
        this.insDate = insDate;
    }
    public void setCustName(String custName) {
        this.custName = custName;
    }
    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }
    public void setGwModelName(String gwModelName) {
        this.gwModelName = gwModelName;
    }
    public void setGwModelCd(String gwModelCd) {
        this.gwModelCd = gwModelCd;
    }
    public void setGwSaid(String gwSaid) {
        this.gwSaid = gwSaid;
    }
    public void setGwMac(String gwMac) {
        this.gwMac = gwMac;
    }
    public void setGwSerial(String gwSerial) {
        this.gwSerial = gwSerial;
    }
    public void setGwCode(String gwCode) {
        this.gwCode = gwCode;
    }
    public void setIdmsInsDate(String idmsInsDate) {
        this.idmsInsDate = idmsInsDate;
    }
    public void setDataReset(String dataReset) {
        this.dataReset = dataReset;
    }
    public void setRollbackState(String rollbackState) {
        this.rollbackState = rollbackState;
    }
    public void setInfraInsDate(String infraInsDate) {
        this.infraInsDate = infraInsDate;
    }
    public void setCustState(String custState) {
        this.custState = custState;
    }
	public String getGwSecret() {
		return gwSecret;
	}
	public void setGwSecret(String gwSecret) {
		this.gwSecret = gwSecret;
	}
	
	
}
