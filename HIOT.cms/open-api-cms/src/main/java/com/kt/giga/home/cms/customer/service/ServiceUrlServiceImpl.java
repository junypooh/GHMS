package com.kt.giga.home.cms.customer.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.common.service.OpenAPICallService;
import com.kt.giga.home.cms.customer.dao.*;

@Service
public class ServiceUrlServiceImpl implements ServiceUrlService {

	@Autowired
	private ServiceUrlDao serviceUrlDao;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	@Override
	public String getUrl(Map<String, Object> service){
		return serviceUrlDao.getUrl(service);
	}
	
	@Override
	public void modify(Map<String, Object> service) {
		serviceUrlDao.modify(service);
		
		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
