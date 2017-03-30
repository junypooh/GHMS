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
 * 현장장치 그룹 VO
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 3. 11.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotDeviceGroup {
	
	/** 현장장치 그룹 코드 */
	private String spotGroupCd;
	
	/** 현장장치 그룹 이름 */
	private String spotGroupNm;

	/** 현장장치 */
	private List<SpotDevice> spotDevs;

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
	public List<SpotDevice> getSpotDevs() {
		return spotDevs;
	}

	/**
	 * @param spotDevs TODO
	 */
	public void setSpotDevs(List<SpotDevice> spotDevs) {
		this.spotDevs = spotDevs;
	}

}
