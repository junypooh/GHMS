package com.kt.giga.home.openapi.cms.service;

import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kt.giga.home.openapi.cms.dao.VersionAppDao;
import com.kt.giga.home.openapi.cms.dao.VersionFrmwrDao;
import com.kt.giga.home.openapi.cms.domain.VersionApp;
import com.kt.giga.home.openapi.cms.domain.VersionFrmwr;
import com.kt.giga.home.openapi.service.APIException;

/**
 * 버전관리 서비스
 * 
 * @author 조상현
 *
 */
@Service
public class VersionService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private VersionAppDao versionAppDao;
	
	@Autowired
	private VersionFrmwrDao versionFrmwrDao;
	
//	@Autowired
//	private TransactionService transactionService;
	
	/**
	 * 
	 * @param cpCode 홈모니터링 서비스 코드, os 구분코드, 단말형태 구분코드
	 * @return App 최신버전 조회
	 * @throws Exception
	 */
	public JSONObject getVerAppLast(Map<String, Object> map) throws Exception {

		JSONObject jsonObj 					= new JSONObject();

		try {
			VersionApp versionApp				= versionAppDao.getVerAppLast(map);

			if(versionApp != null){
				jsonObj.put("verNum"			, versionApp.getFd_ver_num()				);	// 최신 버전
				jsonObj.put("verMemo"			, versionApp.getFd_ver_memo()				);	// 버전 설명
				jsonObj.put("verMandatoryYn"	, versionApp.getFd_mandatory_yn()			);	// 업데이트 필수여부 YN
			}
			
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return jsonObj;
	}


	/**
	 * 
	 * @param cpCode 홈모니터링 서비스 코드
	 * @return 펌웨어 최신버전 조회
	 * @throws Exception
	 */
	public JSONObject getVerFrmwrLast(Map<String, Object> map) throws Exception {

		JSONObject jsonObj 					= new JSONObject();

		try {
			VersionFrmwr versionFrmwr		= versionFrmwrDao.getVerFrmwrLast(map);

			if(versionFrmwr != null){
				jsonObj.put("verNum"			, versionFrmwr.getFd_ver_num()				);	// 최신 버전
				jsonObj.put("verMemo"			, versionFrmwr.getFd_ver_title()			);	// 버전 설명
				jsonObj.put("verMandatoryYn"	, versionFrmwr.getFd_mandatory_yn()			);	// 업데이트 필수여부 YN
				jsonObj.put("modelName"			, versionFrmwr.getFd_model_name()			);	// 모델명
			}
			
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return jsonObj;
	}


}

