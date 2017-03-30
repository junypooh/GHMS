package com.kt.giga.home.openapi.health.device.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.openapi.util.*;
import com.kt.giga.home.openapi.health.user.service.*;
import com.kt.giga.home.openapi.health.device.dao.*;

/**
 * 헬스케어 센서 등록 서비스
 * @author 김용성
 *
 */
@Service
public class DeviceSensorService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DeviceSensorDao deviceSensorDao;
	
	@Autowired
	private HealthUserService healthUserService;	
	
	/**
	 * 센서 등록
	 * @param device 센서정보
	 * @throws APIException
	 * @throws Exception
	 */
	public void register(Map<String, Object> device) throws APIException, Exception {
		
		Validation.checkParameter(device, "spotDevId", "devNm");
		
		int spotDevRegSttus = 0;
		
		if(deviceSensorDao.get(device) == null) {
			
			try {
				
				device.put("devUUID", UUID.randomUUID().toString());
				deviceSensorDao.register(device);
				spotDevRegSttus = 1;
				
			} catch(Exception ex) {
				log.error(ex.getMessage());
				spotDevRegSttus = 2;
			}
			
		} else {
			spotDevRegSttus = 3;
		}
		
		device.put("spotDevRegSttus", spotDevRegSttus);
	}
	
	/**
	 * 센서 등록
	 * @param authToken 인증토큰
	 * @param device 센서정보
	 * @throws APIException
	 * @throws Exception
	 */
	public void register(String authToken, Map<String, Object> device) throws APIException, Exception  {
		
		AuthToken token = healthUserService.checkLoginAuthToken(authToken);
		
		String unitSvcCd 	= token.getSvcCode();
		String svcTgtId		= token.getTelNo();
		
		if(unitSvcCd != null && unitSvcCd.length() == 9) {
			unitSvcCd = unitSvcCd.substring(6);
		}		
		
		device.put("unitSvcCd"	, unitSvcCd);
		device.put("svcTgtId"	, svcTgtId);
		
		this.register(device);
	}
}
