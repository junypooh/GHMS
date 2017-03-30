package com.kt.giga.home.openapi.cms.service;

import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;

import com.kt.giga.home.openapi.util.*;
import com.kt.giga.home.openapi.service.*;
import com.kt.giga.home.openapi.cms.dao.*;

@Service
public class AppLogService {

	@Autowired
	private AppLogDao appLogDao;
	
	public void set(Map<String, Object> appLog) throws APIException, Exception {
		
		Validation.checkParameter(appLog, "deviceId", "telNo", "appVer", "svcCode", "menuCode");
		
		String encodeTelNo = "", decodeTelNo = "";
		String menuCode = "", firstChar = "", devUUID="";
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String logTime = df.format(new Date());
		
		try {
			if(appLog.get("devUUID") != null){
				devUUID = appLog.get("devUUID").toString();
				//devUUID = Base64Coder.decodeString(devUUID);
				appLog.put("dev_uuid", devUUID);
			}else{
				appLog.put("dev_uuid", "");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new APIException("잘못된 devUUID.", HttpStatus.BAD_REQUEST);
		}
		

		try {
			encodeTelNo = appLog.get("telNo").toString();
			decodeTelNo = Base64Coder.decodeString(encodeTelNo);
			appLog.put("decodeTelNo", decodeTelNo);
		} catch(Exception e) {
			throw new APIException("잘못된 telNo 규격", HttpStatus.BAD_REQUEST);
		}
		
		try {
			menuCode = appLog.get("menuCode").toString().toUpperCase();
			firstChar = menuCode.substring(0, 1);
			
			if(menuCode.length() > 2 && menuCode.length() < 5) {
				if(firstChar.equals("P") && menuCode.length() != 3) {
					throw new Exception("잘못된 menuCode 규격");
				}
				
				if(firstChar.equals("C") && menuCode.length() != 4) {
					throw new Exception("잘못된 menuCode 규격");
				}	
				
				appLog.put("menuCode", menuCode);
				
			} else {
				throw new Exception("잘못된 menuCode 규격");
			}
			
		} catch(Exception e) {
			throw new APIException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		appLog.put("logTime", logTime);
		
		appLogDao.set(appLog);
	}
}
