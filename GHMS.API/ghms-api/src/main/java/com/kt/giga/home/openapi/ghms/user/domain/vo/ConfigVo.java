/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.vo;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 12.
 */
public class ConfigVo {
	
	private long devUUID;
	
	private String setUpVal;
	
	private String devNm;
	
	private String devModelNm;

	/**
	 * @return TODO
	 */
	public long getDevUUID() {
		return devUUID;
	}

	/**
	 * @param devUUID TODO
	 */
	public void setDevUUID(long devUUID) {
		this.devUUID = devUUID;
	}

	/**
	 * @return TODO
	 */
	public String getSetUpVal() {
		return setUpVal;
	}

	/**
	 * @param setUpVal TODO
	 */
	public void setSetUpVal(String setUpVal) {
		this.setUpVal = setUpVal;
	}

	/**
	 * @return TODO
	 */
	public String getDevNm() {
		return devNm;
	}

	/**
	 * @param devNm TODO
	 */
	public void setDevNm(String devNm) {
		this.devNm = devNm;
	}

	/**
	 * @return TODO
	 */
	public String getDevModelNm() {
		return devModelNm;
	}

	/**
	 * @param devModelNm TODO
	 */
	public void setDevModelNm(String devModelNm) {
		this.devModelNm = devModelNm;
	}

}
