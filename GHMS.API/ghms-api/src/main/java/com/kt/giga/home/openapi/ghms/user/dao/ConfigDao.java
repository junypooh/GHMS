/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.dao;

import java.util.List;

import com.kt.giga.home.openapi.ghms.user.domain.key.ConfigKey;
import com.kt.giga.home.openapi.ghms.user.domain.vo.ConfigVo;

/**
 * 알림 설정 매퍼 인터페이스
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 12.
 */
public interface ConfigDao {
	
	/**
	 * 알림 수신 설정 조회
	 * @param key
	 * @return List<ConfigVo>
	 */
	public List<ConfigVo> selectAlarmlist(ConfigKey key);
	
	/**
	 * 알림 수신 설정 수정
	 * @param key
	 */
	public void updateAlarm(ConfigKey key);
}
