/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.kt.giga.home.infra.util.JsonUtils;

/**
 * AP 인터넷 이용제한 목록 조회 Response
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 4.
 */
@XmlRootElement(name="GetInternetAccessControlResult", namespace="http://tempuri.org/")
public class GetInternetAccessControlResult {
	
    /** 처리결과 코드 */
    private String resultCode;

    /** 처리결과 메시지 */
    private String resultMessage;
    
    /** 인터넷 이용제한 사용 여부 */
    private String internetAccessControlEnable;
    
    /** 인터넷 이용제한 룰 목록 */
    private List<InternetAccessRule> internetAccessRules;

	public String getResultCode() {
		return resultCode;
	}

	@XmlElement(name="ResultCode", namespace="http://tempuri.org/")
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	@XmlElement(name="ResultMessage", namespace="http://tempuri.org/")
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getInternetAccessControlEnable() {
		return internetAccessControlEnable;
	}

	@XmlElement(name="InternetAccessControlEnable", namespace="http://tempuri.org/")
	public void setInternetAccessControlEnable(String internetAccessControlEnable) {
		this.internetAccessControlEnable = internetAccessControlEnable;
	}

	public List<InternetAccessRule> getInternetAccessRules() {
		return internetAccessRules;
	}

	@XmlElementWrapper(name="InternetAccessRules", namespace="http://tempuri.org/")
	@XmlElementRefs({
		@XmlElementRef(name="InternetAccessRule", namespace="http://tempuri.org/", type=InternetAccessRule.class)
	})
	public void setInternetAccessRules(List<InternetAccessRule> internetAccessRules) {
		this.internetAccessRules = internetAccessRules;
	}
/*	
	public static void main(String[] args) throws Exception {
		
		GetInternetAccessControlResult rt = new GetInternetAccessControlResult();
		List<InternetAccessRule> list = new ArrayList<InternetAccessRule>();
		rt.setResultCode("T");
		rt.setResultMessage("성공");
		rt.setInternetAccessControlEnable("1");
		rt.setInternetAccessRules(list);
		
		InternetAccessRule rule = new InternetAccessRule();
		rule.setIndex("1");
		rule.setInternetAccessRuleEnable("1");
		rule.setInternetAccessRuleName("아들 공부하는 시간");
		rule.setInternetAccessControlMac("ac:22:0b:77:3f:66".toUpperCase());
		rule.setInternetAccessDate("MON.TUE,WED,SUN");
		rule.setInternetAccessStartTime("0600");
		rule.setInternetAccessEndTime("0800");
		
		list.add(rule);
		
		
		System.out.println(JsonUtils.toPrettyJson(rt));
	}
*/
}
