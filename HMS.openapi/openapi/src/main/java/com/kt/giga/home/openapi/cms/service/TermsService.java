package com.kt.giga.home.openapi.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kt.giga.home.openapi.cms.dao.CodeDao;
import com.kt.giga.home.openapi.cms.dao.ParentsAgreeDao;
import com.kt.giga.home.openapi.cms.dao.TermsAgreeDao;
import com.kt.giga.home.openapi.cms.dao.TermsDao;
import com.kt.giga.home.openapi.cms.domain.Code;
import com.kt.giga.home.openapi.cms.domain.Terms;
import com.kt.giga.home.openapi.cms.domain.TermsAgree;
import com.kt.giga.home.openapi.service.APIException;

/**
 * 약관처리 관련 처리 서비스
 * 
 * @author 조상현
 *
 */
@Service
public class TermsService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CodeDao codeDao;

	@Autowired
	private TermsDao termsDao;
	
	@Autowired
	private TermsAgreeDao termsAgreeDao;
	
	@Autowired
	private ParentsAgreeDao parentsAgreeDao;
	
//	@Autowired
//	private TransactionService transactionService;
	
	/**
	 * 
	 * @param cpCode 홈모니터링 서비스 코드
	 * @return 약관 리스트 조회
	 * @throws Exception
	 */
	public JSONArray getTermsList(Map<String, Object> map) throws Exception {

		JSONArray jsonArray 			= new JSONArray();
		Code code						= codeDao.getCode("1401");	//약관 url 코드 : 1401
		try {
//			map.put("mbr_seq", Integer.parseInt( map.get("mbr_seq").toString()) );
			map.put("mbr_seq", map.get("mbr_seq").toString() );
			List<Terms> termsList		= termsDao.getTermsList(map);
			
			for(Terms termsCol : termsList){
					
					JSONObject jsonObj 			= new JSONObject();

					jsonObj.put("termsListId"		, termsCol.getPk_terms()				);	// 약관 pk
					jsonObj.put("termsCode"			, termsCol.getFk_terms_code()			);	// 약관 구분 코드
					jsonObj.put("termsVer"			, termsCol.getFd_terms_ver()			);	// 약관 버전
					jsonObj.put("termsTitle"		, termsCol.getFd_terms_title()			);	// 약관 제목
					jsonObj.put("termsUrl"			, code.getFd_name() +"?uid="+ termsCol.getPk_terms() + "&cpCode=" + map.get("cpCode").toString() );	// 약관 내용 url
					jsonObj.put("termsStart"		, termsCol.getFd_terms_start()			);	// 약관 시행일
					jsonObj.put("termsMandatoryYn"	, termsCol.getFd_terms_mandatory_yn()	);	// 약관동의 필수여부 YN
					jsonObj.put("termsAgreeYn"		, termsCol.getFd_agree_yn()				);	// 약관동의 여부
					
					jsonArray.add(jsonObj);
			}
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return jsonArray;
	}

	/**
	 * 
	 * @param 약관id 및 동의YN
	 * @return  약관 및 개인정보 취급 방침 동의 등록
	 * @throws Exception
	 */
	public void setTermsAgree(Map<String, Object> map) throws Exception {
		
		try {
			List<HashMap<String,String>> agreeList	= (List<HashMap<String,String>>) map.get("agreeList");
			
			for (HashMap<String,String> agreeCol : agreeList) {
				TermsAgree termsAgree 		= new TermsAgree();
				
				termsAgree.setFk_terms(		 Integer.parseInt( agreeCol.get("termsListId")	));
				termsAgree.setFd_agree_yn(	agreeCol.get("agreeYn")							);
				termsAgree.setMbr_seq(		Long.parseLong(map.get("mbr_seq").toString())					);
				termsAgree.setFk_cp_code(	map.get("cpCode").toString()					);

				termsAgreeDao.setTermsAgree(termsAgree);
			} 
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 
	 * @param cpCode ,mbr_seq
	 * @return  약관동의 취소
	 * @throws Exception
	 */
	public void setTermsRetract(Map<String, Object> map) throws Exception {
		
		try {
			
				TermsAgree termsAgree 		= new TermsAgree();
				
				termsAgree.setMbr_seq(		Long.parseLong(map.get("mbr_seq").toString())					);
				termsAgree.setFk_cp_code(	map.get("cpCode").toString()					);

				termsAgreeDao.setTermsRetract(termsAgree);

		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 
	 * @param 보호자 동의YN
	 * @return  14세 미만 보호자 동의 등록
	 * @throws Exception
	 */
//	public void setParentsAgree(Map<String, Object> map) throws Exception {
//		
//		try {
//				ParentsAgree parentsAgree 			= new ParentsAgree();
//				Map<String, Object> agreeInfo		= (Map<String, Object>) map.get("agreeInfo");
//				
//				parentsAgree.setFd_parents_name( 	agreeInfo.get("parents_name").toString()	);
//				parentsAgree.setFd_parents_mobile(	agreeInfo.get("parents_mobile").toString()	);
//				parentsAgree.setFd_agree_yn(		agreeInfo.get("agreeYn").toString()			);
//				parentsAgree.setMbr_seq(			map.get("userId").toString()				);
//
//				parentsAgreeDao.setParentsAgree(parentsAgree);
//
//		} catch(DataAccessException e) {
//
//			log.error(e.getMessage(), e);
//			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

}

