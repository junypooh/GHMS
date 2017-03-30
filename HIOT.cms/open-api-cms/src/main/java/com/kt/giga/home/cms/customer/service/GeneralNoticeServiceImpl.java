package com.kt.giga.home.cms.customer.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.common.service.OpenAPICallService;
import com.kt.giga.home.cms.customer.dao.*;

@Service
public class GeneralNoticeServiceImpl implements GeneralNoticeService {

	@Autowired
	private GeneralNoticeDao generalNoticeDao;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return generalNoticeDao.getCount(searchInfo);
	}
	
	@Override
	public int getPreorderCount(String cpCode) {
		return generalNoticeDao.getPreorderCount(cpCode);
	}	
	
	@Override
	public Map<String, Object> get(Map<String, Object> searchInfo) {
		searchInfo.put("pk", Integer.valueOf(searchInfo.get("pk").toString()));
		return generalNoticeDao.get(searchInfo);
	}	

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return generalNoticeDao.getList(searchInfo);
	}

	@Override
	public void updateOpenYN(Map<String, Object> notice) {
		notice.put("pk", Integer.valueOf(notice.get("pk").toString()));
		generalNoticeDao.updateOpenYN(notice);
		
		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void updatePreorderYN(Map<String, Object> notice) {
		
		int preorderCount = this.getPreorderCount(notice.get("cpCode").toString());
		
		notice.put("pk", Integer.valueOf(notice.get("pk").toString()));
		
		if(preorderCount < 3 || (preorderCount == 3 && notice.get("preorderYN").toString().equals("N"))) {
			generalNoticeDao.updatePreorderYN(notice);
			notice.put("over", false);
		} else {
			notice.put("over", true); 
		}
		
		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void remove(Map<String, Object> notice) {
		generalNoticeDao.remove(notice);
		
		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void register(Map<String, Object> notice) {
		generalNoticeDao.register(notice);
		
		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void modify(Map<String, Object> notice) {
		notice.put("pk", Integer.valueOf(notice.get("pk").toString()));
		generalNoticeDao.modify(notice);
		
		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
