/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kt.giga.home.openapi.ghms.common.domain.vo.BaseVo;
import com.kt.giga.home.openapi.ghms.common.token.AuthToken;
import com.kt.giga.home.openapi.ghms.user.dao.ConfigDao;
import com.kt.giga.home.openapi.ghms.user.domain.key.ConfigKey;
import com.kt.giga.home.openapi.ghms.user.domain.key.DeviceKey;
import com.kt.giga.home.openapi.ghms.user.domain.key.DeviceMasterKey;
import com.kt.giga.home.openapi.ghms.user.domain.vo.ConfigVo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.DeviceMasterVo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.DeviceVo;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;
import com.kt.giga.home.openapi.ghms.util.properties.APIEnv;

/**
 * 알림 수신 설정 서비스
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 11.
 */
@Service
@Transactional(
        propagation = Propagation.REQUIRED,
        rollbackFor = {
                Exception.class,
                RuntimeException.class,
                SQLException.class
        }
)
public class ConfigService {

	/** Logger */
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ConfigDao configDao;
	
	@Autowired
	private APIEnv env;
	
	/**
	 * 알림 수신 설정 조회
	 * @param token
	 * @param serviceNo
	 * @return DeviceMasterVo
	 * @throws APIException
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public DeviceMasterVo selectAlarmlist(AuthToken token, long serviceNo) throws APIException {
		
		// DB 조회용 key setting.
		ConfigKey key = new ConfigKey();
		key.setDeviceId(token.getDeviceId());
		key.setUserNo(token.getUserNoLong());
		key.setDstrCd(token.getDstrCd());
		key.setSvcThemeCd(token.getSvcThemeCd());
		key.setUnitSvcCd(token.getUnitSvcCd());
		key.setGroupSetupCd(env.getProperty("group.setup.cd"));
		key.setSetupCd("001");
		key.setServiceNo(serviceNo);
//		key.setUserType(token.getUserType());
		
		// 알림 설정 조회
		List<ConfigVo> alarmlist = configDao.selectAlarmlist(key);
		
		// DeviceMasterVo 생성
		DeviceMasterVo deviceMasterVo = new DeviceMasterVo();
		deviceMasterVo.setSvcTgtSeq(serviceNo);
		
		List<DeviceVo> deviceList = new ArrayList<DeviceVo>();
		
		for(ConfigVo vo : alarmlist) {
			
			DeviceVo deviceVo = new DeviceVo();
			deviceVo.setDevUUID(Long.toString(vo.getDevUUID()));
			deviceVo.setDevNm(vo.getDevNm());
			deviceVo.setDevModelNm(vo.getDevModelNm());
			deviceVo.setCntrCode("001");
			deviceVo.setCntrValue(vo.getSetUpVal());
			
			deviceList.add(deviceVo);
			
		}
		
		deviceMasterVo.setDeviceList(deviceList);
		
		return deviceMasterVo;
	}
	
	public BaseVo updateAlarm(AuthToken token, DeviceMasterKey key) throws APIException {

		BaseVo vo = new BaseVo();
		try {
		    
		    List<DeviceKey> deviceList = key.getDeviceList();
		    
		    for(DeviceKey devKey : deviceList) {
		        // DB 쿼리용 key setting.
	            ConfigKey configKey = new ConfigKey();
	            configKey.setDeviceId(token.getDeviceId());
	            configKey.setUserNo(token.getUserNoLong());
	            configKey.setDstrCd(token.getDstrCd());
	            configKey.setSvcThemeCd(token.getSvcThemeCd());
	            configKey.setUnitSvcCd(token.getUnitSvcCd());
	            configKey.setGroupSetupCd(env.getProperty("group.setup.cd"));
	            configKey.setSetupCd(devKey.getCntrCode());
	            configKey.setServiceNo(key.getSvcTgtSeq());
	            configKey.setDevUUID(devKey.getDevUUID());
	            configKey.setSetupVal(devKey.getCntrValue());
	            
	            configDao.updateAlarm(configKey);
	            
		    }
			vo.setResultCode("0");	
		} catch (Exception ex) {
			vo.setResultCode("-1");
		}
		
		return vo;
		
	}

}
