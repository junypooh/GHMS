/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.sdp;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.domain.commons.HttpSoapObjectResponse;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 3.
 */
@XmlRootElement(name="getSpecificSubscpnInfoResponse", namespace="http://kt.com/sdp")
public class SpecificSubscpnInfoResponse extends HttpSoapObjectResponse {

	private String resultCode;	
	private String resultMsg;
	private List<SpecificSubscpnInfo> subscpnInfos;
	
	public String getResultCode() {
		return resultCode;
	}
	@XmlElement(name="returnCode", namespace="http://kt.com/sdp")
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	@XmlElement(name="returnDesc", namespace="http://kt.com/sdp")
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public List<SpecificSubscpnInfo> getSubscpnInfos() {
		return subscpnInfos;
	}
	
	@XmlElementWrapper(name="ListofSubscription", namespace="http://kt.com/sdp")
	@XmlElementRefs({
		@XmlElementRef(name="item", namespace="http://kt.com/sdp_SpecificSubs", type=SpecificSubscpnInfo.class)
	})
	public void setSubscpnInfos(List<SpecificSubscpnInfo> subscpnInfos) {
		this.subscpnInfos = subscpnInfos;
	}
	
}
