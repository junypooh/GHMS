package com.kt.giga.home.openapi.health.profile.service;

import java.io.*;
import java.util.*;

import org.slf4j.*;

import org.json.simple.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.fasterxml.jackson.annotation.JsonInclude.*;
import com.kt.giga.home.openapi.util.*;
import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.common.CipherUtils;
import com.kt.giga.home.openapi.health.user.service.*;
import com.kt.giga.home.openapi.health.profile.dao.*;


@Service
public class ProfileService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private APIEnv apiEnv;

	@Autowired
	private ProfileDao profileDao;
	
	@Autowired
	private HealthUserService healthUserService;
	
	private String getEncryptByAES(String plainText) throws UnsupportedEncodingException, Exception {
		byte[] key 	= apiEnv.getProperty("profile.encrypt.key").getBytes("UTF-8");
		byte[] iv	= apiEnv.getProperty("profile.encrypt.iv").getBytes("UTF-8");
		return CipherUtils.encryptAESCTRNoPaddingHex(key, iv, plainText);
	}
	
	private String getDecryptByAES(String plainText) throws UnsupportedEncodingException, Exception {
		byte[] key 	= apiEnv.getProperty("profile.encrypt.key").getBytes("UTF-8");
		byte[] iv	= apiEnv.getProperty("profile.encrypt.iv").getBytes("UTF-8");
		return CipherUtils.decryptAESCTRNoPaddingHex(key, iv, plainText);
	}	
	
	private int getMbrSeq(String mbrId) {
		
		int mbrsSeq = profileDao.getMbrSeq(mbrId);
		return mbrsSeq;
		
	}
	
	/**
	 * 프로필 조회
	 * @param authToken 인증토큰
	 * @return 프로필
	 * @throws APIException
	 * @throws Exception
	 */
	public Map<String, Object> get(String authToken) throws APIException, Exception {
		
		AuthToken token = healthUserService.checkLoginAuthToken(authToken);
		
		int mbrSeq = this.getMbrSeq(token.getTelNo());
		
		Map<String, Object> profile = profileDao.get(mbrSeq);
		
		JSONObject json = ObjectConverter.toJSON(profile, Include.ALWAYS);
		
		if(profile != null) {
			profile.remove("mbrSeq");
			
			String gender 	= profile.get("gender").toString();
			String age		= profile.get("age").toString();
			String hight	= profile.get("hight").toString();
			String weight	= profile.get("weight").toString();	
			
			profile.put("gender"	, this.getDecryptByAES(gender));
			profile.put("age"		, this.getDecryptByAES(age));
			profile.put("hight"		, this.getDecryptByAES(hight));
			profile.put("weight"	, this.getDecryptByAES(weight));
			profile.put("profSchResult"	, 1);
		} else {
			profile = new HashMap<>();
			profile.put("nickNm"		, "");
			profile.put("gender"		, "");
			profile.put("age"			, "");
			profile.put("hight"			, "");
			profile.put("weight"		, "");
			profile.put("profSchResult"	, 2);			
		}
		
		return profile;		
	}
	
	/**
	 * 프로필 등록
	 * @param profile 프로필
	 * @throws APIException
	 * @throws Exception
	 */	
	public void register(Map<String, Object> profile) throws APIException, Exception {
		
		int mbrSeq = this.getMbrSeq(profile.get("mbrId").toString());
		
		String gender 	= profile.get("gender")	!= null ? profile.get("gender").toString() : ""; 
		String age		= profile.get("age") 	!= null ? profile.get("age").toString() : "";
		String hight	= profile.get("hight") 	!= null ? profile.get("hight").toString() : "";
		String weight	= profile.get("weight") != null ? profile.get("weight").toString() : "";			
		
		profile.put("gender"	, this.getEncryptByAES(gender));
		profile.put("age"		, this.getEncryptByAES(age));
		profile.put("hight"		, this.getEncryptByAES(hight));
		profile.put("weight"	, this.getEncryptByAES(weight));
		
		if(profileDao.get(mbrSeq) == null) {
			profile.put("mbrSeq", mbrSeq);
			profileDao.register(profile);	
			profile.clear();
			profile.put("profRegResult", 1);
		} else {
			profile.clear();
			profile.put("profRegResult", 2);
		}
	}
	
	/**
	 * 프로필 등록
	 * @param authToken 인증토큰
	 * @param profile 프로필
	 * @throws APIException
	 * @throws Exception
	 */
	public void register(String authToken, Map<String, Object> profile) throws APIException, Exception {
		AuthToken token = healthUserService.checkLoginAuthToken(authToken);
		profile.put("mbrId", token.getTelNo());
		this.register(profile);
	}
	
	/**
	 * 프로필 수정
	 * @param profile 프로필
	 * @throws APIException
	 * @throws Exception
	 */
	public void modify(Map<String, Object> profile) throws APIException, Exception {
		
		Validation.checkParameter(profile, "gender", "age", "hight", "weight");
		
		String gender 	= profile.get("gender")	!= null ? profile.get("gender").toString() : ""; 
		String age		= profile.get("age") 	!= null ? profile.get("age").toString() : "";
		String hight	= profile.get("hight") 	!= null ? profile.get("hight").toString() : "";
		String weight	= profile.get("weight") != null ? profile.get("weight").toString() : "";			
		
		profile.put("gender"	, this.getEncryptByAES(gender));
		profile.put("age"		, this.getEncryptByAES(age));
		profile.put("hight"		, this.getEncryptByAES(hight));
		profile.put("weight"	, this.getEncryptByAES(weight));		
		
		if(profileDao.get((int)profile.get("mbrSeq")) != null) {
			profileDao.modify(profile);	
			profile.clear();
			profile.put("profMdfResult", 1);
		} else {
			profile.clear();
			profile.put("profMdfResult", 2);
		}
	}
	
	/**
	 * 프로필 수정
	 * @param authToken 인증토큰
	 * @param profile 프로필
	 * @throws APIException
	 * @throws Exception
	 */
	public void modify(String authToken, Map<String, Object> profile) throws APIException, Exception {
		
		AuthToken token = healthUserService.checkLoginAuthToken(authToken);
		
		int mbrSeq = this.getMbrSeq(token.getTelNo());		
		
		profile.put("mbrSeq", mbrSeq);		
		
		this.modify(profile);		
	}
}
