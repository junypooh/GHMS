package com.kt.giga.home.cms.inquiry.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.inquiry.dao.*;
import com.kt.giga.home.cms.common.service.*;

@Service
public class TermsAgreeServiceImpl implements TermsAgreeService {
	
	@Autowired
	private CodeService codeService; 
	
	@Autowired
	private TermsAgreeDao termsAgreeDao;

	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return termsAgreeDao.getCount(searchInfo);
	}

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return termsAgreeDao.getList(searchInfo);
	}

}
