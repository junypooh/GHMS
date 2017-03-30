package com.kt.giga.home.cms.common.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.*;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kt.giga.home.cms.util.ObjectConverter;

@Service
public class OpenAPICallServiceImpl implements OpenAPICallService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private APIEnv apiEnv;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public void updateOpenAPIInit() throws Exception  {
		updateOpenAPIInit("001"); // 홈 캠
		updateOpenAPIInit("002"); // 홈 피트니스
		updateOpenAPIInit("003"); // 홈 매니저
	}

	@Override
	public void updateOpenAPIInit(String svcCode) throws Exception {
		
		String jsonStr = "";
		Map<String, Object> result = null;
		
		try {
			String[] serverList = apiEnv.getProperty("openapi.serverList").split("\\|");
			String syncInit = apiEnv.getProperty("openapi.cms.syncInit");
			String reload = "";
			
			switch(svcCode){
				case "001" : reload = apiEnv.getProperty("openapi.hcam.user.init.reload"); break;
				case "002" : break;
				case "003" : reload = apiEnv.getProperty("openapi.ghms.user.init.reload"); break;
				default : 
			}
			//String reload = apiEnv.getProperty("openapi.hcam.user.init.reload");
			
			result = restTemplate.getForObject(syncInit + "?svcCode=", Map.class);
			jsonStr = ObjectConverter.toJSONString(result, Include.ALWAYS);
			
			logger.info("======================= CMS syncInit Call =======================");
			logger.info("\n" + jsonStr);
			logger.info("=================================================================");			
			
			if(!reload.equals("")){
				for(String server : serverList) {
					try {
						result = restTemplate.getForObject(server + reload + "?svcCode=", Map.class);
						jsonStr = ObjectConverter.toJSONString(result, Include.ALWAYS);		
						
						logger.info("======================= hcam reload Call =======================");
						logger.info("\n" + jsonStr);
						logger.info("=================================================================");	
					} catch(ResourceAccessException e) {
						logger.warn(e.getMessage());
					}
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
