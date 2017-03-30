/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kt.giga.home.openapi.ghms.checker.domain.key.TimeSettingKey;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.Device;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.DeviceGw;

/**
 * 제어 Dao
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 16.
 */
public interface DevicesUpgradeDao {
	
	/**
	 * 게이트웨이 정보 조회
	 * 
	 * @param mbrSeq
	 * @param dstrCd
	 * @param svcThemeCd
	 * @param unitSvcCd
	 * @param gwUUID
	 * @return
	 */
	public List<DeviceGw> selectSpotGateWayList(
	        @Param("mbrSeq")        	long mbrSeq,
	        @Param("dstrCd")        	String dstrCd,
	        @Param("svcThemeCd")        String svcThemeCd,
	        @Param("unitSvcCd")			String unitSvcCd,
	        @Param("svcTgtSeq")        	long svcTgtSeq,
	        @Param("spotDevSeq")			long spotDevSeq,
	        @Param("devTypeCd")			String devTypeCd
			);

	/**
	 * 현장장치 정보 조회
	 * 
	 * @param spotGroupCd
	 * @param gateWaySpotDevSeq
	 * @return
	 */
	public List<Device> selectSpotDevList(
	        @Param("svcTgtSeq")        	long svcTgtSeq,
	        @Param("gateWaySpotDevSeq")	Long gateWaySpotDevSeq,
	        @Param("spotDevSeq")		Long spotDevSeq,
	        @Param("mbrSeq")        	long mbrSeq,
	        @Param("devTypeCd")			String devTypeCd
			);

	/**
	 * 현장장치 정보 조회
	 * 
	 * @param spotGroupCd
	 * @param gateWaySpotDevSeq
	 * @return
	 */
	public List<Device> selectSpotDevListFilter(
	        @Param("svcTgtSeq")        	long svcTgtSeq,
	        @Param("gateWaySpotDevSeq")	Long gateWaySpotDevSeq,
	        @Param("spotDevSeq")		Long spotDevSeq,
	        @Param("mbrSeq")        	long mbrSeq,
	        @Param("devTypeCd")			String devTypeCd,
	        @Param("macAddrArray")		String[] macAddrArray
			);

	/**
	 * AP 하위 현장장치 정보 조회
	 * 
	 * @param key
	 * @return
	 */
	public Device selectSpotDevBasByDevMac(TimeSettingKey key);

	/**
	 * GW 현장장치 정보 조회
	 * 
	 * @param key
	 * @return
	 */
	public Device selectSpotDevBasByGwMac(
	        @Param("mbrSeq")        	long mbrSeq,
	        @Param("dstrCd")        	String dstrCd,
	        @Param("svcThemeCd")        String svcThemeCd,
	        @Param("unitSvcCd")			String unitSvcCd,
	        @Param("gwMacAddress")		String gwMacAddress
	        );

}
