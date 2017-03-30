package com.kt.giga.home.cms.inquiry.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.inquiry.dao.*;

@Service
public class ParentsAgreeServiceImpl implements ParentsAgreeService {
	
	@Autowired
	private ParentsAgreeDao parentsAgreeDao;

	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return parentsAgreeDao.getCount(searchInfo);
	}

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return parentsAgreeDao.getList(searchInfo);
	}

}
