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
public class IotControlKey {
	
	/** 모드설정 코드 */
	private String modeCode;
	
	/** APP 에서 사용하는 필드(API 서버는 무시) */
	private String snsnTagCd;

	/** APP 에서 사용하는 필드(API 서버는 무시) */
	private String spotDevSeq;

	/** 현장장치전달데이터리스트 */
	private List<SpotDevCnvyData> spotDevCnvyDatas;
	
	/**
	 * @return TODO
	 */
	public String getModeCode() {
		return modeCode;
	}

	/**
	 * @param modeCode TODO
	 */
	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
	}

	public String getSnsnTagCd() {
		return snsnTagCd;
	}

	public void setSnsnTagCd(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}

	public String getSpotDevSeq() {
		return spotDevSeq;
	}

	public void setSpotDevSeq(String spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}

	/**
	 * @return TODO
	 */
	public List<SpotDevCnvyData> getSpotDevCnvyDatas() {
		return spotDevCnvyDatas;
	}

	/**
	 * @param spotDevCnvyDatas TODO
	 */
	public void setSpotDevCnvyDatas(List<SpotDevCnvyData> spotDevCnvyDatas) {
		this.spotDevCnvyDatas = spotDevCnvyDatas;
	}
}
