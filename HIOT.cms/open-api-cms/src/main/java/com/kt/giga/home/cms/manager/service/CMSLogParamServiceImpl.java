package com.kt.giga.home.cms.manager.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.giga.home.cms.manager.dao.*;

@Service
public class CMSLogParamServiceImpl implements CMSLogParamService
{
	@Autowired
	private CMSLogParamDao cmsLogParamDao;
	
	@Override
	public List<Map<String, Object>> getList(int fk_cms_log)
	{
		List<Map<String, Object>> logParamList = cmsLogParamDao.getList(fk_cms_log);
		return logParamList;
	}
	
	@Override
	public void register(Map<String, Object> cmsLogParam)
	{
		cmsLogParamDao.register(cmsLogParam);
	}
}
