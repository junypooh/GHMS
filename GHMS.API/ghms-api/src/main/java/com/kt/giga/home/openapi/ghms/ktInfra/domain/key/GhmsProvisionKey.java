/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.domain.key;

/**
 * 청약 정보 연동 KEY
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 3. 10.
 */
public class GhmsProvisionKey {
    /** 홈 매니저 서비스 계약 ID */
    private String saId;
    /** ICIS에서 전달된 상품코드 */
    private String intfCode;
    /** 세부상품 구분 코드 */
    private String prodCode;
    /** 오더 번호 */
    private String prodDtlCode;
    /** 청약 신청일시(접수 기준) */
    private String woNo;
    /** 고객ID (ICIS 원천고객ID) */
    private String insDate;
    /** 고객 이름 */
    private String custid;
    /** 게이트웨이 단말 모델명 */
    private String custName;
    /** 게이트웨이 단말 모델코드(펌웨어 업그레이드 확인) */
    private String gwModelName;
    /** 홈 매니저 게이트웨이 단말 SAID */
    private String gwModelCd;
    /** 게이드웨이 단말 SAID */
    private String gwSaid;
    /** 게이트웨이 단말 MAC 주소 */
    private String gwMac;
    /** 게이트웨이 단말 SERIAL 넘버 */
    private String gwSerial;
    /** 게이트웨이 단말 인증을 위한 비밀번호 */
    private String gwSecret;
    /** 홈매니저 서비스 서버 처리 유형(청약상태) */
    private String gwCode;
    /** IDMS 오더 입력 시간 */
    private String idmsInsDate;
    /** 롤백 여부 */
    private String dataReset;
    /** 롤백 이전의 청약 상태 */
    private String rollbackState;
    /** 서버스번호 */
    private String serviceNo;
    
    private String devNm;
    
    /**
     * @return TODO
     */
    public String getSaId() {
        return saId;
    }
    /**
     * @return TODO
     */
    public String getIntfCode() {
        return intfCode;
    }
    /**
     * @return TODO
     */
    public String getProdCode() {
        return prodCode;
    }
    /**
     * @return TODO
     */
    public String getProdDtlCode() {
        return prodDtlCode;
    }
    /**
     * @return TODO
     */
    public String getWoNo() {
        return woNo;
    }
    /**
     * @return TODO
     */
    public String getInsDate() {
        return insDate;
    }
    /**
     * @return TODO
     */
    public String getCustid() {
        return custid;
    }
    /**
     * @return TODO
     */
    public String getCustName() {
        return custName;
    }
    /**
     * @return TODO
     */
    public String getGwModelName() {
        return gwModelName;
    }
    /**
     * @return TODO
     */
    public String getGwModelCd() {
        return gwModelCd;
    }
    /**
     * @return TODO
     */
    public String getGwSaid() {
        return gwSaid;
    }
    /**
     * @return TODO
     */
    public String getGwMac() {
        return gwMac;
    }
    /**
     * @return TODO
     */
    public String getGwSerial() {
        return gwSerial;
    }
    /**
     * @return TODO
     */
    public String getGwSecret() {
        return gwSecret;
    }
    /**
     * @return TODO
     */
    public String getGwCode() {
        return gwCode;
    }
    /**
     * @return TODO
     */
    public String getIdmsInsDate() {
        return idmsInsDate;
    }
    /**
     * @return TODO
     */
    public String getDataReset() {
        return dataReset;
    }
    /**
     * @return TODO
     */
    public String getRollbackState() {
        return rollbackState;
    }
    /**
     * @param saId TODO
     */
    public void setSaId(String saId) {
        this.saId = saId;
    }
    /**
     * @param intfCode TODO
     */
    public void setIntfCode(String intfCode) {
        this.intfCode = intfCode;
    }
    /**
     * @param prodCode TODO
     */
    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }
    /**
     * @param prodDtlCode TODO
     */
    public void setProdDtlCode(String prodDtlCode) {
        this.prodDtlCode = prodDtlCode;
    }
    /**
     * @param woNo TODO
     */
    public void setWoNo(String woNo) {
        this.woNo = woNo;
    }
    /**
     * @param insDate TODO
     */
    public void setInsDate(String insDate) {
        this.insDate = insDate;
    }
    /**
     * @param custid TODO
     */
    public void setCustid(String custid) {
        this.custid = custid;
    }
    /**
     * @param custName TODO
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }
    /**
     * @param gwModelName TODO
     */
    public void setGwModelName(String gwModelName) {
        this.gwModelName = gwModelName;
    }
    /**
     * @param gwModelCd TODO
     */
    public void setGwModelCd(String gwModelCd) {
        this.gwModelCd = gwModelCd;
    }
    /**
     * @param gwSaid TODO
     */
    public void setGwSaid(String gwSaid) {
        this.gwSaid = gwSaid;
    }
    /**
     * @param gwMac TODO
     */
    public void setGwMac(String gwMac) {
        this.gwMac = gwMac;
    }
    /**
     * @param gwSerial TODO
     */
    public void setGwSerial(String gwSerial) {
        this.gwSerial = gwSerial;
    }
    /**
     * @param gwSecret TODO
     */
    public void setGwSecret(String gwSecret) {
        this.gwSecret = gwSecret;
    }
    /**
     * @param gwCode TODO
     */
    public void setGwCode(String gwCode) {
        this.gwCode = gwCode;
    }
    /**
     * @param idmsInsDate TODO
     */
    public void setIdmsInsDate(String idmsInsDate) {
        this.idmsInsDate = idmsInsDate;
    }
    /**
     * @param dataReset TODO
     */
    public void setDataReset(String dataReset) {
        this.dataReset = dataReset;
    }
    /**
     * @param rollbackState TODO
     */
    public void setRollbackState(String rollbackState) {
        this.rollbackState = rollbackState;
    }
	/**
	 * @return TODO
	 */
	public String getServiceNo() {
		return serviceNo;
	}
	/**
	 * @param serviceNo TODO
	 */
	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}
	public String getDevNm() {
		return devNm;
	}
	public void setDevNm(String devNm) {
		this.devNm = devNm;
	}
    
}
