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
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevDtl;

/**
 * 현장장치 VO
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 3. 11.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewSpotDevice {
	
	/** 현장장치 그룹 코드 */
	private String spotGroupCd;
	
	/** 현장장치 그룹 이름 */
	private String spotGroupNm;

	/** 현장장치일련번호 */
	private long spotDevSeq;
	
	/** 게이트웨이연결아이디 */
	private String gwCnctId;

	/** 상위현장장치아이디 */
	private String upSpotDevId;
	
	/**현장장치명  */
	@JsonInclude(Include.ALWAYS)
	private String spotDevNm;
	
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

	/** 현장장치아이디 */
	private String spotDevId;
	
	@JsonIgnore
	private long upSvcTgtSeq;

	@JsonIgnore
	private long upSpotDevSeq;

	/** 현장장치상세 */
	@JsonInclude(Include.ALWAYS)
	private List<SpotDevDtl> spotDevDtls;
	
	/** 일반설정데이터(80) */
	@JsonInclude(Include.ALWAYS)
	private List<SpotDeviceSnsnData> genlSetupDatas;

	/**
	 * @return TODO
	 */
	public String getSpotGroupCd() {
		return spotGroupCd;
	}

	/**
	 * @param spotGroupCd TODO
	 */
	public void setSpotGroupCd(String spotGroupCd) {
		this.spotGroupCd = spotGroupCd;
	}

	/**
	 * @return TODO
	 */
	public String getSpotGroupNm() {
		return spotGroupNm;
	}

	/**
	 * @param spotGroupNm TODO
	 */
	public void setSpotGroupNm(String spotGroupNm) {
		this.spotGroupNm = spotGroupNm;
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
	public String getGwCnctId() {
		return gwCnctId;
	}

	/**
	 * @param gwCnctId TODO
	 */
	public void setGwCnctId(String gwCnctId) {
		this.gwCnctId = gwCnctId;
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
	public long getUpSpotDevSeq() {
		return upSpotDevSeq;
	}

	/**
	 * @param upSpotDevSeq TODO
	 */
	public void setUpSpotDevSeq(long upSpotDevSeq) {
		this.upSpotDevSeq = upSpotDevSeq;
	}

	/**
	 * @return TODO
	 */
	public List<SpotDevDtl> getSpotDevDtls() {
		return spotDevDtls;
	}

	/**
	 * @param spotDevDtls TODO
	 */
	public void setSpotDevDtls(List<SpotDevDtl> spotDevDtls) {
		this.spotDevDtls = spotDevDtls;
	}

	/**
	 * @return TODO
	 */
	public List<SpotDeviceSnsnData> getGenlSetupDatas() {
		return genlSetupDatas;
	}

	/**
	 * @param genlSetupDatas TODO
	 */
	public void setGenlSetupDatas(List<SpotDeviceSnsnData> genlSetupDatas) {
		this.genlSetupDatas = genlSetupDatas;
	}
}
