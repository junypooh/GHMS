/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.domain.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 게이트웨이 VO
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 3. 11.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotDeviceGw {

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
	
	/** 현장장치아이디 */
	private String spotDevId;
	
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
	
	/** 서비스대상유형코드 */
	@JsonIgnore
	private String svcTgtTypeCd;
	
	/** 현장장치 그룹 리스트 */
	private List<SpotDeviceGroup> spotDevGroupList;

	/**
	 * @return TODO
	 */
	public long getSvcTgtSeq() {
		return svcTgtSeq;
	}

	/**
	 * @param svcTgtSeq TODO
	 */
	public void setSvcTgtSeq(long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}

	/**
	 * @return TODO
	 */
	public long getSpotDevSeq() {
		return spotDevSeq;
	}

	/**
	 * @param spotDevSeq TODO
	 */
	public void setSpotDevSeq(long spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}

	/**
	 * @return TODO
	 */
	public long getUpSvcTgtSeq() {
		return upSvcTgtSeq;
	}

	/**
	 * @param upSvcTgtSeq TODO
	 */
	public void setUpSvcTgtSeq(long upSvcTgtSeq) {
		this.upSvcTgtSeq = upSvcTgtSeq;
	}

	/**
	 * @return TODO
	 */
	public String getUpGwCnctId() {
		return upGwCnctId;
	}

	/**
	 * @param upGwCnctId TODO
	 */
	public void setUpGwCnctId(String upGwCnctId) {
		this.upGwCnctId = upGwCnctId;
	}

	/**
	 * @return TODO
	 */
	public String getUpSpotDevId() {
		return upSpotDevId;
	}

	/**
	 * @param upSpotDevId TODO
	 */
	public void setUpSpotDevId(String upSpotDevId) {
		this.upSpotDevId = upSpotDevId;
	}

	/**
	 * @return TODO
	 */
	public String getSpotDevId() {
		return spotDevId;
	}

	/**
	 * @param spotDevId TODO
	 */
	public void setSpotDevId(String spotDevId) {
		this.spotDevId = spotDevId;
	}

	/**
	 * @return TODO
	 */
	public String getUnitSvcCd() {
		return unitSvcCd;
	}

	/**
	 * @param unitSvcCd TODO
	 */
	public void setUnitSvcCd(String unitSvcCd) {
		this.unitSvcCd = unitSvcCd;
	}

	/**
	 * @return TODO
	 */
	public String getSpotDevNm() {
		return spotDevNm;
	}

	/**
	 * @param spotDevNm TODO
	 */
	public void setSpotDevNm(String spotDevNm) {
		this.spotDevNm = spotDevNm;
	}

	/**
	 * @return TODO
	 */
	public String getSubsSttusCd() {
		return subsSttusCd;
	}

	/**
	 * @param subsSttusCd TODO
	 */
	public void setSubsSttusCd(String subsSttusCd) {
		this.subsSttusCd = subsSttusCd;
	}

	/**
	 * @return TODO
	 */
	public String getMakrNm() {
		return makrNm;
	}

	/**
	 * @param makrNm TODO
	 */
	public void setMakrNm(String makrNm) {
		this.makrNm = makrNm;
	}

	/**
	 * @return TODO
	 */
	public String getModelNm() {
		return modelNm;
	}

	/**
	 * @param modelNm TODO
	 */
	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}

	/**
	 * @return TODO
	 */
	public String getProdSeq() {
		return prodSeq;
	}

	/**
	 * @param prodSeq TODO
	 */
	public void setProdSeq(String prodSeq) {
		this.prodSeq = prodSeq;
	}

	/**
	 * @return TODO
	 */
	public String getRegSeq() {
		return regSeq;
	}

	/**
	 * @param regSeq TODO
	 */
	public void setRegSeq(String regSeq) {
		this.regSeq = regSeq;
	}

	/**
	 * @return TODO
	 */
	public String getFrmwrVerNo() {
		return frmwrVerNo;
	}

	/**
	 * @param frmwrVerNo TODO
	 */
	public void setFrmwrVerNo(String frmwrVerNo) {
		this.frmwrVerNo = frmwrVerNo;
	}

	/**
	 * @return TODO
	 */
	public String getOprtSttusCd() {
		return oprtSttusCd;
	}

	/**
	 * @param oprtSttusCd TODO
	 */
	public void setOprtSttusCd(String oprtSttusCd) {
		this.oprtSttusCd = oprtSttusCd;
	}

	/**
	 * @return TODO
	 */
	public String getDevSttusCd() {
		return devSttusCd;
	}

	/**
	 * @param devSttusCd TODO
	 */
	public void setDevSttusCd(String devSttusCd) {
		this.devSttusCd = devSttusCd;
	}

	/**
	 * @return TODO
	 */
	public String getSvcContId() {
		return svcContId;
	}

	/**
	 * @param svcContId TODO
	 */
	public void setSvcContId(String svcContId) {
		this.svcContId = svcContId;
	}

	/**
	 * @return TODO
	 */
	public String getTmpDevYn() {
		return tmpDevYn;
	}

	/**
	 * @param tmpDevYn TODO
	 */
	public void setTmpDevYn(String tmpDevYn) {
		this.tmpDevYn = tmpDevYn;
	}

	/**
	 * @return TODO
	 */
	public String getIsMaster() {
		return isMaster;
	}

	/**
	 * @param isMaster TODO
	 */
	public void setIsMaster(String isMaster) {
		this.isMaster = isMaster;
	}

	/**
	 * @return TODO
	 */
	public String getGwSerial() {
		return gwSerial;
	}

	/**
	 * @param gwSerial TODO
	 */
	public void setGwSerial(String gwSerial) {
		this.gwSerial = gwSerial;
	}

	/**
	 * @return TODO
	 */
	public String getGwMac() {
		return gwMac;
	}

	/**
	 * @param gwMac TODO
	 */
	public void setGwMac(String gwMac) {
		this.gwMac = gwMac;
	}

	/**
	 * @return TODO
	 */
	public String getSvcTgtTypeCd() {
		return svcTgtTypeCd;
	}

	/**
	 * @param svcTgtTypeCd TODO
	 */
	public void setSvcTgtTypeCd(String svcTgtTypeCd) {
		this.svcTgtTypeCd = svcTgtTypeCd;
	}

	/**
	 * @return TODO
	 */
	public List<SpotDeviceGroup> getSpotDevGroupList() {
		return spotDevGroupList;
	}

	/**
	 * @param spotDevGroupList TODO
	 */
	public void setSpotDevGroupList(List<SpotDeviceGroup> spotDevGroupList) {
		this.spotDevGroupList = spotDevGroupList;
	}

}
