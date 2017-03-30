package com.kt.giga.home.cms.inquiry.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.giga.home.cms.inquiry.dao.PnsHistoryDao;

@Service
public class MemberStatusServiceImpl implements MemberStatusService{
	@Autowired
	private PnsHistoryDao pnsHistoryDao;
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return pnsHistoryDao.getCount(searchInfo);
	}	
	
	@Override
	public Map<String, Object> get(Map<String, Object> searchInfo) {
		searchInfo.put("pk", Integer.valueOf(searchInfo.get("pk").toString()));
		return pnsHistoryDao.get(searchInfo);
	}	

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return pnsHistoryDao.getList(searchInfo);
	}
}
