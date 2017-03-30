package com.kt.giga.home.cms.customer.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.common.service.OpenAPICallService;
import com.kt.giga.home.cms.customer.dao.*;

@Service
public class PmNoticeServiceImpl implements PmNoticeService {

	@Autowired
	private PmNoticeDao pmNoticeDao;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return pmNoticeDao.getCount(searchInfo);
	}
	
	@Override
	public 	int checkDateCount(Map<String, Object> notice){
		
		if(notice.get("pk") != null){
			notice.put("pk", Integer.valueOf(notice.get("pk").toString()));			
		}
		int rtn = 200;	// return default : 200 
	
		if(pmNoticeDao.checkDateCount(notice) > 0){
			rtn = 201; // 날짜가 중복 되는 수가 존재 할때 overlap 판정
		}
		
		return rtn;
	}

	@Override
	public Map<String, Object> get(Map<String, Object> searchInfo) {
		searchInfo.put("pk", Integer.valueOf(searchInfo.get("pk").toString()));
		return pmNoticeDao.get(searchInfo);
	}	

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return pmNoticeDao.getList(searchInfo);
	}
	
	@Override
	public void updateOpenYN(Map<String, Object> notice) {
		notice.put("pk", Integer.valueOf(notice.get("pk").toString()));
		pmNoticeDao.updateOpenYN(notice);
		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	

	}

	@Override
	public void remove(Map<String, Object> notice) {
		pmNoticeDao.remove(notice);
		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void register(Map<String, Object> notice) {
		pmNoticeDao.register(notice);
		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void modify(Map<String, Object> notice) {
		notice.put("pk", Integer.valueOf(notice.get("pk").toString()));
		pmNoticeDao.modify(notice);			
		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
