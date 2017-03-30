/**
 * <PRE>
 *  Project GWCommAgent2
 *  Package com.kt.smcp.gw.ca.query.spotdevbas.vo
 * </PRE>
 * @brief
 * @file SpotDevBasQueryVO.java
 * @date 2014. 1. 23. 오후 6:38:08
 * @author byw
 *  변경이력
 *        이름     : 일자          : 근거자료   : 변경내용
 *       ------------------------------------
 *        byw  : 2014. 1. 23.       :            : 신규 개발.
 *
 * Copyright © 2013 kt corp. all rights reserved.
 */
package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.query;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * <PRE>
 *  ClassName SpotDevBasQueryVO
 * </PRE>
 * @brief
 * @version 1.0
 * @date 2014. 1. 23. 오후 6:38:08
 * @author byw
 */

@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotDevBasQueryVO {
	/** 응대코드 */
	private String rpyCode;
	/** 응대메시지 */
	private String rpyMsg;
	/** 현장장치기본정보 */
	private SpotDevBasVO spotDevBasVO;


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * @return the rpyCode
	 */
	public String getRpyCode() {
		return rpyCode;
	}
	/**
	 * @param rpyCode the rpyCode to set
	 */
	public void setRpyCode(String rpyCode) {
		this.rpyCode = rpyCode;
	}
	/**
	 * @return the rpyMsg
	 */
	public String getRpyMsg() {
		return rpyMsg;
	}
	/**
	 * @param rpyMsg the rpyMsg to set
	 */
	public void setRpyMsg(String rpyMsg) {
		this.rpyMsg = rpyMsg;
	}
	/**
	 * @return the spotDevBasVO
	 */
	public SpotDevBasVO getSpotDevBasVO() {
		return spotDevBasVO;
	}
	/**
	 * @param spotDevBasVO the spotDevBasVO to set
	 */
	public void setSpotDevBasVO(SpotDevBasVO spotDevBasVO) {
		this.spotDevBasVO = spotDevBasVO;
	}
}
