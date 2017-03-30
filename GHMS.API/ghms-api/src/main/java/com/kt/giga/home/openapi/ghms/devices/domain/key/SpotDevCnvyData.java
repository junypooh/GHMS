/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.domain.key;

import java.util.List;


/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 3. 30.
 */
public class SpotDevCnvyData {

    /** 외부시스템(앱)과 연동에 사용하는 장치 유니크 아이디 */
    private String devUUID;
    
    private String svcTgtSeq;
    
    private String spotDevSeq;
    
    private String upSpotDevId;
    
    private String spotDevId;
    
    private String gwCnctId;
    
    private String kafkaIfYn;
    
    private List<CnvyRowData> cnvyRowDatas;

	/**
	 * @return TODO
	 */
	public String getDevUUID() {
		return devUUID;
	}

	/**
	 * @param devUUID TODO
	 */
	public void setDevUUID(String devUUID) {
		this.devUUID = devUUID;
	}

	/**
	 * @return TODO
	 */
	public String getSvcTgtSeq() {
		return svcTgtSeq;
	}

	/**
	 * @param svcTgtSeq TODO
	 */
	public void setSvcTgtSeq(String svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}

	/**
	 * @return TODO
	 */
	public String getSpotDevSeq() {
		return spotDevSeq;
	}

	/**
	 * @param spotDevSeq TODO
	 */
	public void setSpotDevSeq(String spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
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
	public String getKafkaIfYn() {
		return kafkaIfYn;
	}

	/**
	 * @param kafkaIfYn TODO
	 */
	public void setKafkaIfYn(String kafkaIfYn) {
		this.kafkaIfYn = kafkaIfYn;
	}

	/**
	 * @return TODO
	 */
	public List<CnvyRowData> getCnvyRowDatas() {
		return cnvyRowDatas;
	}

	/**
	 * @param cnvyRowDatas TODO
	 */
	public void setCnvyRowDatas(List<CnvyRowData> cnvyRowDatas) {
		this.cnvyRowDatas = cnvyRowDatas;
	}

}
