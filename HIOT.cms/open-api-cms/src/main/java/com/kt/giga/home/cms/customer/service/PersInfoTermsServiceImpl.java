package com.kt.giga.home.cms.customer.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.common.service.OpenAPICallService;
import com.kt.giga.home.cms.customer.dao.*;

@Service
public class PersInfoTermsServiceImpl implements PersInfoTermsService {

	@Autowired
	private PersInfoTermsDao persInfoTermsDao;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return persInfoTermsDao.getCount(searchInfo);
	}
	
	@Override
	public Map<String, Object> get(Map<String, Object> searchInfo) {
		searchInfo.put("pk", Integer.valueOf(searchInfo.get("pk").toString()));
		return persInfoTermsDao.get(searchInfo);
	}	

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return persInfoTermsDao.getList(searchInfo);
	}
	
	
	@Override
	public String checkPersInfoFormData(Map<String, Object> searchInfo) {
		String rtn = "";
		String lastVerStr = "";
		int chkVer, lastVer;
		boolean chkProcess = false;
		
		try {
			//체크할 버전
			String[] chkVerArryStr = searchInfo.get("privacyVer").toString().split("\\.");
			
			//마지막 등록된 버전
			lastVerStr = persInfoTermsDao.getLastVer(searchInfo);
			
			if(lastVerStr != null){
				String[] lastVerArryStr = lastVerStr.split("\\.");
				for(int i=0 ; i<chkVerArryStr.length ; i++){
					chkVer = Integer.parseInt(chkVerArryStr[i]);
					lastVer = Integer.parseInt(lastVerArryStr[i]);
					
					if(chkVer > lastVer){
						chkProcess = true;
						break;
					}else if(chkVer < lastVer){
						chkProcess = false;
						break;
					}
				}
			}else{
				chkProcess = true;
			}
			
			if(chkProcess){
				rtn = "success";
			}else{
				rtn = "fail";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			rtn = "fail";
		}
		return rtn;
	}
	
	@Override
	public void remove(Map<String, Object> terms) {
		persInfoTermsDao.remove(terms);
		
		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void register(Map<String, Object> terms) {
		persInfoTermsDao.register(terms);
		
		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void modify(Map<String, Object> terms) {
		terms.put("pk", Integer.valueOf(terms.get("pk").toString()));
		persInfoTermsDao.modify(terms);
		
		try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
