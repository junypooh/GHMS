/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.cms.inquiry.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 8. 11.
 */
public interface ManagerDeviceDao {
	
	List<Map<String, Object>> selectSpotDevSeqBySvcTgtSeq(Long svcTgtSeq);

	/**
	 * @return
	 */
	List<Map<String, Object>> selectSnsnTagCdList();
	
	String selectMbrId(Long mbrSeq);
	
	List<Map<String, Object>> selectEventList();

}
