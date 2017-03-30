/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.dao;

import java.util.Map;

import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.KPNSEvent;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 27.
 */
public interface KPNSDao {

	/**
	 * PNS 이력 저장
	 * @param KPNSEvent
	 */
	public void insertMsgTrmForwardTxn(KPNSEvent event);

	/**
	 * 사용자 정보 조회
	 * @param pnsRegId
	 */
	public Map<String, Object> selectMbrInfoByPnsId(String pnsRegId);

	/**
	 * PNS 이력 수정
	 * @param KPNSEvent
	 */
	public void updateMsgTrmForwardTxn(KPNSEvent event);

	/**
	 * PNS 이력 조회
	 * @param KPNSEvent
	 */
	public KPNSEvent selectMsgTrmForwardTxnByMsgId(KPNSEvent event);

}
