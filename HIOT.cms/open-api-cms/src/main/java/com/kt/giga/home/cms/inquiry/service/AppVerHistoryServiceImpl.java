package com.kt.giga.home.cms.inquiry.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import com.kt.giga.home.cms.inquiry.dao.*;

@Service
public class AppVerHistoryServiceImpl implements AppVerHistoryService {
	
	@Autowired
	private AppVerHistoryDao appVerHistoryDao;
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return appVerHistoryDao.getCount(searchInfo);
	}

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return appVerHistoryDao.getList(searchInfo);
	}
	
}
