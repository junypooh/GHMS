/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.sdp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 3.
 */
@XmlRootElement(name="item", namespace="http://kt.com/sdp_SpecificSubs")
public class SpecificSubscpnInfoSSV {
	
	/** 부가상품코드 */
	private String vasCd;
	/** 부가상품명 */
	private String vasName;
	/** 서비스시작일자 */
	private String startDate;
	
	public String getVasCd() {
		return vasCd;
	}
	@XmlElement(name="Vas_Cd", namespace="http://kt.com/sdp_SpecificSubs")
	public void setVasCd(String vasCd) {
		this.vasCd = vasCd;
	}
	public String getVasName() {
		return vasName;
	}
	@XmlElement(name="Vas_Name", namespace="http://kt.com/sdp_SpecificSubs")
	public void setVasName(String vasName) {
		this.vasName = vasName;
	}
	public String getStartDate() {
		return startDate;
	}
	@XmlElement(name="Start_Date", namespace="http://kt.com/sdp_SpecificSubs")
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

}
