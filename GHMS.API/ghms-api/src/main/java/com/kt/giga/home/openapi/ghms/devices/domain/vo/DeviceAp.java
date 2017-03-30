/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.domain.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 15.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceAp {

	/** 서비스대상일련번호 */
	private long svcTgtSeq;
	
	/** 현장장치일련번호 */
	private long spotDevSeq;
	
	/** 상위서비스대상일련번호 */
	private long upSvcTgtSeq;
	
	/** 상위게이트웨이연결아이디 */
	private String upGwCnctId;
	
	/** 상위현장장치아이디 */
	private String upSpotDevId;
	
	/** 단위서비스코드 */
	private String unitSvcCd;
	
	/**현장장치명  */
	@JsonInclude(Include.ALWAYS)
	private String spotDevNm;

	/**청약상태코드  */
	private String subsSttusCd;
	
	/** 제조사명 */
	private String makrNm;
	
	/** 모델명 */
	private String modelNm;
	
	/** 상품일련번호 */
	private String prodSeq;
	
	/** 등록일련번호 */
	private String regSeq;

	/** 펌웨어버전번호 */
	private String frmwrVerNo;
	
	private String oprtSttusCd;
	
	private String devSttusCd;
	
	private String svcContId;
	
	/** 임시장치여부 */
	private String tmpDevYn;

	/** 마스터여부(마스터 : M 슬레이브 : S) */
	private String isMaster;
	
	/** GW 시리얼 번호 */
	private String gwSerial;
	
	/** GW MacAddr */
	private String gwMac;
	
	/** on/Off 상태 */
	private String onOff;

	/** 현장장치 */
	private List<Device> spotDevs;

	public long getSvcTgtSeq() {
		return svcTgtSeq;
	}

	public void setSvcTgtSeq(long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}

	public long getSpotDevSeq() {
		return spotDevSeq;
	}

	public void setSpotDevSeq(long spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}

	public long getUpSvcTgtSeq() {
		return upSvcTgtSeq;
	}

	public void setUpSvcTgtSeq(long upSvcTgtSeq) {
		this.upSvcTgtSeq = upSvcTgtSeq;
	}

	public String getUpGwCnctId() {
		return upGwCnctId;
	}

	public void setUpGwCnctId(String upGwCnctId) {
		this.upGwCnctId = upGwCnctId;
	}

	public String getUpSpotDevId() {
		return upSpotDevId;
	}

	public void setUpSpotDevId(String upSpotDevId) {
		this.upSpotDevId = upSpotDevId;
	}

	public String getUnitSvcCd() {
		return unitSvcCd;
	}

	public void setUnitSvcCd(String unitSvcCd) {
		this.unitSvcCd = unitSvcCd;
	}

	public String getSpotDevNm() {
		return spotDevNm;
	}

	public void setSpotDevNm(String spotDevNm) {
		this.spotDevNm = spotDevNm;
	}

	public String getSubsSttusCd() {
		return subsSttusCd;
	}

	public void setSubsSttusCd(String subsSttusCd) {
		this.subsSttusCd = subsSttusCd;
	}

	public String getMakrNm() {
		return makrNm;
	}

	public void setMakrNm(String makrNm) {
		this.makrNm = makrNm;
	}

	public String getModelNm() {
		return modelNm;
	}

	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}

	public String getProdSeq() {
		return prodSeq;
	}

	public void setProdSeq(String prodSeq) {
		this.prodSeq = prodSeq;
	}

	public String getRegSeq() {
		return regSeq;
	}

	public void setRegSeq(String regSeq) {
		this.regSeq = regSeq;
	}

	public String getFrmwrVerNo() {
		return frmwrVerNo;
	}

	public void setFrmwrVerNo(String frmwrVerNo) {
		this.frmwrVerNo = frmwrVerNo;
	}

	public String getOprtSttusCd() {
		return oprtSttusCd;
	}

	public void setOprtSttusCd(String oprtSttusCd) {
		this.oprtSttusCd = oprtSttusCd;
	}

	public String getDevSttusCd() {
		return devSttusCd;
	}

	public void setDevSttusCd(String devSttusCd) {
		this.devSttusCd = devSttusCd;
	}

	public String getSvcContId() {
		return svcContId;
	}

	public void setSvcContId(String svcContId) {
		this.svcContId = svcContId;
	}

	public String getTmpDevYn() {
		return tmpDevYn;
	}

	public void setTmpDevYn(String tmpDevYn) {
		this.tmpDevYn = tmpDevYn;
	}

	public String getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(String isMaster) {
		this.isMaster = isMaster;
	}

	public String getGwSerial() {
		return gwSerial;
	}

	public void setGwSerial(String gwSerial) {
		this.gwSerial = gwSerial;
	}

	public String getGwMac() {
		return gwMac;
	}

	public void setGwMac(String gwMac) {
		this.gwMac = gwMac;
	}

	public String getOnOff() {
		return onOff;
	}

	public void setOnOff(String onOff) {
		this.onOff = onOff;
	}

	public List<Device> getSpotDevs() {
		return spotDevs;
	}

	public void setSpotDevs(List<Device> spotDevs) {
		this.spotDevs = spotDevs;
	}

}
