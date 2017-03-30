/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.dao.checker;

import java.util.List;

import com.kt.giga.home.infra.domain.checker.CheckerKey;
import com.kt.giga.home.infra.domain.checker.GetOllehIDResult;
import com.kt.giga.home.infra.domain.checker.IoTDeviceInfo;
import com.kt.giga.home.infra.domain.checker.IoTGWInfo;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 29.
 */
public interface CheckerDao {
	
	/**
	 * 홈 IoT 허브 리스트 조회
	 * @param key
	 * @return
	 */
	public List<IoTGWInfo> getIotGwInfo(CheckerKey key);
	
	/**
	 * 홈 IoT 하위 단말 리스트 조회
	 * @param key
	 * @return
	 */
	public List<IoTDeviceInfo> getIotDeviceInfo(CheckerKey key);
	
	/**
	 * Olleh통합ID 조회
	 * @param key
	 * @return GetOllehIDResult
	 */
	public List<GetOllehIDResult> getOllehID(CheckerKey key);

}
