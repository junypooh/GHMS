/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 6.
 */
@JsonInclude(Include.NON_EMPTY)
public class EventHistoryVo {
	
	private int totCnt;
	
	private List<EventHistoryDtl> eventHistoryDtlList;

	/**
	 * @return TODO
	 */
	public int getTotCnt() {
		return totCnt;
	}

	/**
	 * @param totCnt TODO
	 */
	public void setTotCnt(int totCnt) {
		this.totCnt = totCnt;
	}

	/**
	 * @return TODO
	 */
	public List<EventHistoryDtl> getEventHistoryDtlList() {
		return eventHistoryDtlList;
	}

	/**
	 * @param eventHistoryDtlList TODO
	 */
	public void setEventHistoryDtlList(List<EventHistoryDtl> eventHistoryDtlList) {
		this.eventHistoryDtlList = eventHistoryDtlList;
	}

}
