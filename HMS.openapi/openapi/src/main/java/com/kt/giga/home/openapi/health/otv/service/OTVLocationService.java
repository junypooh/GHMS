package com.kt.giga.home.openapi.health.otv.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.giga.home.openapi.health.otv.dao.OTVLocationDao;
import com.kt.giga.home.openapi.health.user.service.HealthUserService;
import com.kt.giga.home.openapi.health.util.HealthSvcCode;
import com.kt.giga.home.openapi.service.APIException;
import com.kt.giga.home.openapi.util.AuthToken;

/**
 * OTV App. 위치 서비스
 * @author jnam
 *
 */
@Service
public class OTVLocationService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OTVLocationDao otvLocDao;
	
	@Autowired
	private HealthUserService healthUserService;
	
	HealthSvcCode healthSvcCode	= new HealthSvcCode();
	
	public Map<String, Object> selectOtvConf(Map<String, Object> input) throws APIException, Exception{
		
		String svcCode = (String)input.get("svcCode");
		
		input.put("dstrCd", healthSvcCode.GetDstrCd(svcCode));
		input.put("svcThemeCd", healthSvcCode.GetSvcThemeCd(svcCode));
		input.put("unitSvcCode", healthSvcCode.GetUnitSvcCd(svcCode));		
		
		return otvLocDao.selectOtvConf(input);
	}

	public void updateOtvConf(String token, Map<String, Object> input) throws Exception{
		
		AuthToken authToken = healthUserService.checkInitAuthToken(token);
		String svcCode = authToken.getSvcCode();
		
		input.put("dstrCd", healthSvcCode.GetDstrCd(svcCode));
		input.put("svcThemeCd", healthSvcCode.GetSvcThemeCd(svcCode));
		input.put("unitSvcCode", healthSvcCode.GetUnitSvcCd(svcCode));
		
		Object x = input.get("x");
		if(x instanceof String){
			input.put("x", Integer.parseInt((String)x));
		}
		
		Object y = input.get("y");
		if(y instanceof String){
			input.put("y", Integer.parseInt((String)y));
		}

		otvLocDao.updateOtvConf(input);
	}
	
	public void insertOtvConf(Map<String, Object> input) throws Exception{
	
		String svcCode = (String)input.get("svcCode");
		
		input.put("dstrCd", healthSvcCode.GetDstrCd(svcCode));
		input.put("svcThemeCd", healthSvcCode.GetSvcThemeCd(svcCode));
		input.put("unitSvcCode", healthSvcCode.GetUnitSvcCd(svcCode));		
		
		Object x = input.get("x");
		if(x instanceof String){
			input.put("x", Integer.parseInt((String)x));
		}
		
		Object y = input.get("y");
		if(y instanceof String){
			input.put("y", Integer.parseInt((String)y));
		}
		
		otvLocDao.insertOtvConf(input);
	}
	
	public void deleteOtvConf(String token, Map<String, Object> input) throws Exception{
		healthUserService.checkInitAuthToken(token);
		AuthToken authToken = healthUserService.checkInitAuthToken(token);
		String svcCode = authToken.getSvcCode();
		
		input.put("dstrCd", healthSvcCode.GetDstrCd(svcCode));
		input.put("svcThemeCd", healthSvcCode.GetSvcThemeCd(svcCode));
		input.put("unitSvcCode", healthSvcCode.GetUnitSvcCd(svcCode));	
				
		otvLocDao.deleteOtvConf(input);
	}
	
}
