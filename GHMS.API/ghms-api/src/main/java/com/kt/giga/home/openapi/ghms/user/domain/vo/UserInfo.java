/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 12.
 */
@JsonInclude(Include.NON_EMPTY)
public class UserInfo {
	
	private String mbrId;
	
	private String pnsRegId;
	
	private String telNo;
	
	private String mbrNickNm;

	public String getMbrId() {
		return mbrId;
	}

	public void setMbrId(String mbrId) {
		this.mbrId = mbrId;
	}

	public String getPnsRegId() {
		return pnsRegId;
	}

	public void setPnsRegId(String pnsRegId) {
		this.pnsRegId = pnsRegId;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getMbrNickNm() {
		return mbrNickNm;
	}

	public void setMbrNickNm(String mbrNickNm) {
		this.mbrNickNm = mbrNickNm;
	}
	
	

}
