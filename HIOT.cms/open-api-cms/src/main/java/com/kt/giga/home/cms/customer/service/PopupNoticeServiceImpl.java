package com.kt.giga.home.cms.customer.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.customer.dao.*;
import com.kt.giga.home.cms.common.service.*;

@Service
public class PopupNoticeServiceImpl implements PopupNoticeService {
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	@Autowired
	private PopupNoticeDao popupNoticeDao;
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return popupNoticeDao.getCount(searchInfo);
	}

	@Override
	public 	int checkDateCount(Map<String, Object> notice){
		
		if(notice.get("pk") != null){
			notice.put("pk", Integer.valueOf(notice.get("pk").toString()));			
		}
		
		int rtn = 200;	// return default : 200 
	
		if(popupNoticeDao.checkDateCount(notice) > 0){
			rtn = 201; // 날짜가 중복 되는 수가 존재 할때 overlap 판정
		}
		
		return rtn;
	}
	
	@Override
	public Map<String, Object> get(Map<String, Object> searchInfo) {
		searchInfo.put("pk", Integer.valueOf(searchInfo.get("pk").toString()));
		return popupNoticeDao.get(searchInfo);
	}	

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return popupNoticeDao.getList(searchInfo);
	}

	@Override
	public void updateOpenYN(Map<String, Object> notice) {
		notice.put("pk", Integer.valueOf(notice.get("pk").toString()));
		popupNoticeDao.updateOpenYN(notice);

		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remove(Map<String, Object> notice) {
		popupNoticeDao.remove(notice);
		
		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void register(Map<String, Object> notice) {
		
		popupNoticeDao.register(notice);
		
		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void modify(Map<String, Object> notice) {
		notice.put("pk", Integer.valueOf(notice.get("pk").toString()));
		popupNoticeDao.modify(notice);
		
		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
