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
 * @since 2015. 2. 6.
 */
@JsonInclude(Include.NON_EMPTY)
public class EventHistoryDtl {
	
	/** 이벤트시퀀스 */
	private String eventSeq;
	
	/** 방문시간 */
	private String eventTime;
	
	/** 방문날짜 */
	private String eventDate;
	
	/** 이벤트 코드 */
	private String eventCode;
	
	/** 이벤트 결과 */
	private String eventResult;
	
	/** 사용자이미지url */
	private String userImgUrl;
	
	/**
	 * @return TODO
	 */
	public String getEventSeq() {
		return eventSeq;
	}

	/**
	 * @param eventSeq TODO
	 */
	public void setEventSeq(String eventSeq) {
		this.eventSeq = eventSeq;
	}

	/**
	 * @return TODO
	 */
	public String getEventTime() {
		return eventTime;
	}

	/**
	 * @param eventTime TODO
	 */
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	/**
	 * @return TODO
	 */
	public String getEventDate() {
		return eventDate;
	}

	/**
	 * @param eventDate TODO
	 */
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	/**
	 * @return TODO
	 */
	public String getEventCode() {
		return eventCode;
	}

	/**
	 * @param eventCode TODO
	 */
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	/**
	 * @return TODO
	 */
	public String getEventResult() {
		return eventResult;
	}

	/**
	 * @param eventResult TODO
	 */
	public void setEventResult(String eventResult) {
		this.eventResult = eventResult;
	}

	/**
	 * @return TODO
	 */
	public String getUserImgUrl() {
		return userImgUrl;
	}

	/**
	 * @param userImgUrl TODO
	 */
	public void setUserImgUrl(String userImgUrl) {
		this.userImgUrl = userImgUrl;
	}

	/**
	 * @return TODO
	 */
	public String getUserNo() {
		return userNo;
	}

	/**
	 * @param userNo TODO
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	/** 사용자 일련번호 */
	private String userNo;

}
