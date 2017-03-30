package com.kt.giga.home.cms.manager.service;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import com.kt.giga.home.cms.util.*;
import com.kt.giga.home.cms.manager.dao.*;

@Service
public class CMSLogServiceImpl implements CMSLogService
{
	@Autowired
	private CMSLogDao cmsLogDao;
	
	@Autowired 
	private CMSLogParamService cmsLogParamService;
	
	@Override
	public Map<String, Object> get(int pk_cms_log)
	{
		Map<String, Object> cmsLog = cmsLogDao.get(pk_cms_log);
		return cmsLog;
	}	

	@Override
	public int getCount(Map<String, Object> searchInfo)
	{
		int cmsLogCount = cmsLogDao.getCount(searchInfo);
		return cmsLogCount;
	}

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo)
	{
		List<Map<String, Object>> cmsLogList = cmsLogDao.getList(searchInfo); 
		return cmsLogList;
	}	
	
	@Override
	@Transactional
	public void register(Map<String, Object> cmsLog, Map<String, String[]> cmsLogParams) throws UnsupportedEncodingException
	{
		cmsLogDao.register(cmsLog);
		int fk_cms_log = (Integer)cmsLog.get("pk_cms_log");
		List<Map<String, Object>> cmsLogParamList = new ArrayList<Map<String, Object>>();
		
		for(Map.Entry<String, String[]> param : cmsLogParams.entrySet())
		{
			int fd_param_type = 0; // 일반 파라메터
			
			if(param.getValue().length > 1)
			{
				fd_param_type = 2; // 값이 배열인 파라메터 
			}
			else if(param.getValue()[0].getBytes("euc-kr").length > 4000)
			{
				fd_param_type = 1; // 단일 값이지만 데이터가 4KByte를 초과하는 파라메터
			}
			
			if(fd_param_type == 0)
			{
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("fk_cms_log", fk_cms_log);
				map.put("fd_param_name", param.getKey());
				map.put("fd_param_value", param.getValue()[0]);
				map.put("fd_param_index", 0);
				map.put("fd_param_type", 0);
				
				cmsLogParamList.add(map);
			}
			else
			{
				int fd_param_index = 0;
				
				if(fd_param_type == 1)
				{
					List<String> paramSplit = ByteUtils.splitByte(param.getValue()[0], 4000);
					
					for(String ps : paramSplit)
					{
						Map<String, Object> map = new HashMap<String, Object>();
						
						map.put("fk_cms_log", fk_cms_log);
						map.put("fd_param_name", param.getKey());
						map.put("fd_param_value", ps);
						map.put("fd_param_index", fd_param_index);
						map.put("fd_param_type", fd_param_type);		
						
						cmsLogParamList.add(map);
						
						fd_param_index++;
					}
				}
				else
				{
					for(String ps : param.getValue())
					{
						Map<String, Object> map = new HashMap<String, Object>();
						
						map.put("fk_cms_log", fk_cms_log);
						map.put("fd_param_name", param.getKey());
						map.put("fd_param_value", ps);
						map.put("fd_param_index", fd_param_index);
						map.put("fd_param_type", fd_param_type);		
						
						cmsLogParamList.add(map);
						
						fd_param_index++;						
					}
				}
			}
		}
		
		for(Map<String, Object> cmsLogParam : cmsLogParamList)
		{
			cmsLogParamService.register(cmsLogParam);
		}
		
	}
}
