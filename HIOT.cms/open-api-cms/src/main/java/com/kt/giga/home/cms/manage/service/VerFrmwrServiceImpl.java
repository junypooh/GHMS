package com.kt.giga.home.cms.manage.service;

import java.io.IOException;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.common.service.OpenAPICallService;
import com.kt.giga.home.cms.manage.dao.*;

@Service
public class VerFrmwrServiceImpl implements VerFrmwrService {

	@Autowired
	private VerFrmwrDao verFrmwrDao;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return verFrmwrDao.getCount(searchInfo);
	}

	@Override
	public Map<String, Object> get(Map<String, Object> searchInfo) {
		searchInfo.put("pk", Integer.valueOf(searchInfo.get("pk").toString()));
		return verFrmwrDao.get(searchInfo);
	}	

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return verFrmwrDao.getList(searchInfo);
	}

	@Override
	public void register(Map<String, Object> verFrmwr) {
		verFrmwrDao.register(verFrmwr);
		
		/*try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	*/
	}

	@Override
	public void modify(Map<String, Object> verFrmwr) {
		verFrmwr.put("pk", Integer.valueOf(verFrmwr.get("pk").toString()));
		verFrmwrDao.modify(verFrmwr);
		
		/*try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	*/
	}
	
	@Override
	public String checkVerFrmwrFormData(Map<String, Object> searchInfo){
		String rtn = "Error";
		try {
			
			//등록할 버전
			int chkVer = Integer.parseInt(searchInfo.get("verNum").toString().replace(".", ""));
			
			//마지막 등록된 버전 - 없을경우 0
			String tempVer = verFrmwrDao.getLastVer(searchInfo);
			int lastVer = tempVer=="" || tempVer==null ? -1 : Integer.parseInt(tempVer.replace(".", ""));
			
			if(chkVer < lastVer){
				//등록할 버전이 마지막 등록한 버전보다 하위 버전일경우
				rtn = "lowVer";
			}else if(chkVer == lastVer){
				//등록할 버전과 마지막 버전이 같을경우
				rtn = "equalVer";
			}else{
				rtn = "success";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rtn;
	}

	@Override
	@Transactional
	public void remove(Map<String, Object> verFrmwr) throws IllegalStateException, IOException {
		verFrmwrDao.remove(verFrmwr);
		/*try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	*/
	}

}
