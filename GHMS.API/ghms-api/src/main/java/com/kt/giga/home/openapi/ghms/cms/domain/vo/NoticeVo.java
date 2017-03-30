/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.cms.domain.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 6.
 */
@JsonInclude(Include.NON_EMPTY)
public class NoticeVo {
	
	private int totCnt;
	
	private List<NoticeDtl> noticeDtlList;

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
	public List<NoticeDtl> getNoticeDtlList() {
		return noticeDtlList;
	}

	/**
	 * @param noticeDtlList TODO
	 */
	public void setNoticeDtlList(List<NoticeDtl> noticeDtlList) {
		this.noticeDtlList = noticeDtlList;
	}

}
