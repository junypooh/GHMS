/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.checker.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kt.giga.home.openapi.ghms.checker.domain.vo.IoTDeviceInfo;
import com.kt.giga.home.openapi.ghms.devices.domain.key.SpotDevBasKey;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.DeviceGw;



/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 20.
 */
public interface SmartCheckerDao {
	
	/**
	 * 홈 IoT 허브 리스트 조회
	 * @param key
	 * @return
	 */
	public DeviceGw getIotInfoSvcContId(
	        @Param("svcContId")        	String svcContId,
	        @Param("svcTgtTypeCd")		String svcTgtTypeCd
	        );

	/**
	 * 하위 장치 조회
	 * @param key
	 * @return
	 */
	public List<IoTDeviceInfo> getIotDeviceInfo(SpotDevBasKey key);
}
