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
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 15.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Device {
	
	/** 현장장치 그룹 코드 */
	private String spotGroupCd;
	
	/** 현장장치 그룹 이름 */
	private String spotGroupNm;

	/** 서비스대상일련번호 */
	private long svcTgtSeq;

	/** 현장장치일련번호 */
	private long spotDevSeq;
	
	/** 게이트웨이연결아이디 */
	private String gwCnctId;

	/** 상위현장장치아이디 */
	private String upSpotDevId;
	
	/** 현장장치명  */
	@JsonInclude(Include.ALWAYS)
	private String spotDevNm;
	
	/** 현장장치 MAC Address  */
	private String spotDevMac;
	
	/** on/Off 상태 */
	private String onOff;
	
	/** 관리단말여부 */
	private String mngDevYn;
	
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
	
	/** 시청채널 */
	private String channelName;

	/** 현장장치상세 */
	@JsonInclude(Include.ALWAYS)
	private List<SpotDevDtl> spotDevDtls;
	
	/** 일반설정데이터(80) */
	@JsonInclude(Include.ALWAYS)
	private List<SpotDeviceSnsnData> genlSetupDatas;

	public String getSpotGroupCd() {
		return spotGroupCd;
	}

	public void setSpotGroupCd(String spotGroupCd) {
		this.spotGroupCd = spotGroupCd;
	}

	public String getSpotGroupNm() {
		return spotGroupNm;
	}

	public void setSpotGroupNm(String spotGroupNm) {
		this.spotGroupNm = spotGroupNm;
	}

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

	public String getGwCnctId() {
		return gwCnctId;
	}

	public void setGwCnctId(String gwCnctId) {
		this.gwCnctId = gwCnctId;
	}

	public String getUpSpotDevId() {
		return upSpotDevId;
	}

	public void setUpSpotDevId(String upSpotDevId) {
		this.upSpotDevId = upSpotDevId;
	}

	public String getSpotDevNm() {
		return spotDevNm;
	}

	public void setSpotDevNm(String spotDevNm) {
		this.spotDevNm = spotDevNm;
	}

	public String getSpotDevMac() {
		return spotDevMac;
	}

	public void setSpotDevMac(String spotDevMac) {
		this.spotDevMac = spotDevMac;
	}

	public String getOnOff() {
		return onOff;
	}

	public void setOnOff(String onOff) {
		this.onOff = onOff;
	}

	public String getMngDevYn() {
		return mngDevYn;
	}

	public void setMngDevYn(String mngDevYn) {
		this.mngDevYn = mngDevYn;
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

	public String getSpotDevId() {
		return spotDevId;
	}

	public void setSpotDevId(String spotDevId) {
		this.spotDevId = spotDevId;
	}

	public long getUpSvcTgtSeq() {
		return upSvcTgtSeq;
	}

	public void setUpSvcTgtSeq(long upSvcTgtSeq) {
		this.upSvcTgtSeq = upSvcTgtSeq;
	}

	public long getUpSpotDevSeq() {
		return upSpotDevSeq;
	}

	public void setUpSpotDevSeq(long upSpotDevSeq) {
		this.upSpotDevSeq = upSpotDevSeq;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public List<SpotDevDtl> getSpotDevDtls() {
		return spotDevDtls;
	}

	public void setSpotDevDtls(List<SpotDevDtl> spotDevDtls) {
		this.spotDevDtls = spotDevDtls;
	}

	public List<SpotDeviceSnsnData> getGenlSetupDatas() {
		return genlSetupDatas;
	}

	public void setGenlSetupDatas(List<SpotDeviceSnsnData> genlSetupDatas) {
		this.genlSetupDatas = genlSetupDatas;
	}

}
