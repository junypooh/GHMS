package com.kt.giga.home.openapi.cms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kt.giga.home.openapi.cms.dao.ParentsAgreeDao;
import com.kt.giga.home.openapi.cms.dao.TermsAgreeDao;
import com.kt.giga.home.openapi.cms.dao.TermsDao;
import com.kt.giga.home.openapi.cms.domain.ParentsAgree;
import com.kt.giga.home.openapi.cms.domain.Terms;
import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.service.APIException;

/**
 * kmc 본인인증 관련 처리 서비스
 * 
 * @author 조상현
 *
 */
@Service
public class KmcService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TermsDao termsDao;
	
	@Autowired
	private TermsAgreeDao termsAgreeDao;
	
	@Autowired
	private ParentsAgreeDao parentsAgreeDao;
	
	@Autowired
	private APIEnv apiEnv;
	
//	@Autowired
//	private TransactionService transactionService;
	
	/**
	 * 
	 * @param cpCode 홈모니터링 서비스 코드
	 * @return 약관 리스트 조회
	 * @throws Exception
	 */
	public JSONArray getTermsList(Map<String, Object> map) throws Exception {

		JSONArray jsonArray 					= new JSONArray();
		try {
			List<Terms> termsList		= termsDao.getTermsList(map);
			
			for(Terms termsCol : termsList){
					
					JSONObject jsonObj 			= new JSONObject();

					jsonObj.put("termsListId"		, termsCol.getPk_terms()				);	// 약관 pk
					jsonObj.put("termsCode"			, termsCol.getFk_terms_code()			);	// 약관 구분 코드
					jsonObj.put("termsVer"			, termsCol.getFd_terms_ver()			);	// 약관 버전
					jsonObj.put("termsTitle"		, termsCol.getFd_terms_title()			);	// 약관 제목
					jsonObj.put("termsUrl"			, termsCol.getFd_terms_contents()		);	// 약관 내용 url
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
	 * @param 본인인증 인증번호 발송 정보
	 * @return  법정대리인 본인인증 인증번호 발송
	 * @throws Exception
	 */
	public JSONObject identityReq(Map<String, Object> map) throws Exception {
		
		String cpId = apiEnv.getProperty("kmc.cpId");	// 회원사 ID
		
		try {
	        JSONObject jsonObj 	= new JSONObject();
			
			String urlCode 		= "001001";				// URL 코드
			String certNum		= "";					// 요청번호
			String date			= "";					// 요청일시
			String phoneNo  	= "";					// 휴대번 번호
			String phoneCorp 	= "";					// 이동통신사
			String birthDay  	= "";					// 생년월일
			String gender		= "";					// 성별
			String nation		= "0";					// 내외국인 (0:내,1:외)
			String name			= "";					// 이용자 성명
			String extendVar	= "0000000000000000";   // 확장변수
			
			long time 					= System.currentTimeMillis();
			SimpleDateFormat cur_time 	= new SimpleDateFormat("yyyyMMddHHmmss");
			date						= cur_time.format(new Date(time));

			//입력값 변수--------------------------------------------------------------------------------------------
			Map<String, Object> identityInfo		= (Map<String, Object>) map.get("identityInfo");

	        name				= identityInfo.get("mobileUserName").toString();	//성명
	        birthDay			= identityInfo.get("mobileBirth").toString();		//생년월일
	        gender				= identityInfo.get("mobileGender").toString();		//성별
	        phoneCorp			= identityInfo.get("mobileCorp").toString();		//이동통신사(SKT,KTF,LGT)
	        phoneNo				= identityInfo.get("mobileNum").toString();			//휴대폰번호
			
			certNum 			= phoneNo+time;
			
			//-------------------------------------------------------------------------------------------------------

			/** 요청번호(certNum) 주의사항 ****************************************************************************
			* 1. 본인인증 결과값 복호화를 위한 키로 활용되므로 중요함.
			* 2. 본인인증 요청시 중복되지 않게 생성해야함. (예-시퀀스번호)
			***********************************************************************************************************/

			//01. 한국모바일인증(주) 암호화 모듈 선언
		    com.icert.comm.secu.IcertSecuManager seed  = new com.icert.comm.secu.IcertSecuManager();

			//02. 1차 암호화 (tr_cert 데이터변수 조합 후 암호화)
			String enc_tr_cert 	= "";
			String tr_cert     	= cpId +"/"+ urlCode +"/"+ certNum +"/"+ date +"/"+ phoneNo +"/"+ phoneCorp +"/"+ birthDay +"/"+ gender +"/"+ nation +"/"+ name +"/"+ extendVar;
			enc_tr_cert = seed.getEnc(tr_cert, "");

			//03. 1차 암호화 데이터에 대한 위변조 검증값 생성 (HMAC)
			String hmacMsg 		= "";
	        hmacMsg 			= seed.getMsg(enc_tr_cert);

			//04. 2차 암호화 (1차 암호화 데이터, HMAC 데이터, extendVar 조합 후 암호화)
			tr_cert 			= seed.getEnc(enc_tr_cert +"/"+ hmacMsg +"/"+ extendVar, "");

			//05. 본인인증요청 URL로 암호화 데이터 넘기기
	        String send_url 	= apiEnv.getProperty("kmc.send_url1") + "?tr_cert=" + tr_cert;
//	        String send_url 	= "https://www.kmcert.com/kmcis/webc_CI/kmcisReqProc.jsp";

			//06. URL로 암호화 값 보내고 암호화된 결과 받기
	        String rec_cert 	= "";
        
	        CloseableHttpClient client 		= HttpClients.createDefault();
	        HttpPost httppost 				= new HttpPost(send_url);
	        HttpResponse response 			= client.execute(httppost);
//	        HttpClientContext localContext 	= HttpClientContext.create();
//	        localContext.setAttribute("tr_cert", tr_cert);
//	        System.out.println("POST : " + httppost.getURI());
//	        CloseableHttpResponse response 	= client.execute(httppost, localContext);
	        System.out.println("POST ========================= : " + response.getStatusLine().toString() );
	       
	        HttpEntity entity = response.getEntity();
	        
			//결과값 변수--------------------------------------------------------------------------------------------
	        String encPara      = ""; //암호화된 통합 파라미터
	        String encMsg1      = ""; //암호화된 통합 파라미터의 Hash값
  			String encMsg2      = ""; //암호화된 통합 파라미터의 Hash값 비교를 위한 Hash값
  			String msgChk       = "";

  			String r_certNum    = ""; //요청번호
  			String r_date       = ""; //요청일시
  			String r_phoneNo	= ""; //휴대폰번호
  			String r_phoneCorp	= ""; //이통사
  			String r_birthDay	= ""; //생년월일
  			String r_gender		= ""; //성별
  			String r_nation     = ""; //내국인
  			String r_name       = ""; //성명
  			String r_result     = ""; //인증결과값
  			String r_resultCode = ""; //인증결과코드
  			String r_check_1    = ""; //인증번호 확인결과 전송 및 SMS재전송 요청을 위한 파라미터 1(수정불가)
  			String r_check_2    = ""; //인증번호 확인결과 전송 및 SMS재전송 요청을 위한 파라미터 2(수정불가)
  			String r_check_3    = ""; //인증번호 확인결과 전송 및 SMS재전송 요청을 위한 파라미터 3(수정불가)

  			if (entity != null) {
	        	rec_cert		= EntityUtils.toString(entity);
	        	
	        	//08. 1차 복호화 (요청번호를 이용해 복호화)
				rec_cert 		= seed.getDec(rec_cert, certNum);
				
				int inf1 		= rec_cert.indexOf("/",0);
				int inf2 		= rec_cert.indexOf("/",inf1+1);

				encPara 		= rec_cert.substring(0, inf1);
				encMsg1 		= rec_cert.substring(inf1+1, inf2);

				//09. 1차 복호화 데이터에 대한 위변조 검증값 검증
				encMsg2 		= seed.getMsg(encPara);

				if(encMsg2.equals(encMsg1)){
					msgChk		= "Y";		//N:비정상 접근
				}

				//10. 2차 복호화 (요청번호를 이용해 복호화)
				rec_cert  = seed.getDec(encPara, certNum);

				int info1  		= rec_cert.indexOf("/",0);
				int info2  		= rec_cert.indexOf("/",info1+1);
				int info3  		= rec_cert.indexOf("/",info2+1);
				int info4  		= rec_cert.indexOf("/",info3+1);
				int info5  		= rec_cert.indexOf("/",info4+1);
				int info6  		= rec_cert.indexOf("/",info5+1);
				int info7  		= rec_cert.indexOf("/",info6+1);
				int info8  		= rec_cert.indexOf("/",info7+1);
				int info9  		= rec_cert.indexOf("/",info8+1);
				int info10 		= rec_cert.indexOf("/",info9+1);
				int info11 		= rec_cert.indexOf("/",info10+1);
				int info12 		= rec_cert.indexOf("/",info11+1);
				int info13 		= rec_cert.indexOf("/",info12+1);

				r_certNum		= rec_cert.substring(0,info1);
				r_date			= rec_cert.substring(info1+1,info2);
				r_phoneNo		= rec_cert.substring(info2+1,info3);
				r_phoneCorp		= rec_cert.substring(info3+1,info4);
				r_birthDay		= rec_cert.substring(info4+1,info5);
				r_gender		= rec_cert.substring(info5+1,info6);
				r_nation		= rec_cert.substring(info6+1,info7);
				r_name			= rec_cert.substring(info7+1,info8);
				r_result		= rec_cert.substring(info8+1,info9);
				r_resultCode	= rec_cert.substring(info9+1,info10);
				r_check_1		= rec_cert.substring(info10+1,info11);
				r_check_2		= rec_cert.substring(info11+1,info12);
				r_check_3		= rec_cert.substring(info12+1,info13);
			
				System.out.println("r_resultCode ========================= : " + r_resultCode );
				
				jsonObj.put("resultCode"	, r_resultCode	);
				jsonObj.put("checksum"		, r_certNum		);
				jsonObj.put("check_1"		, r_check_1		);
				jsonObj.put("check_2"		, r_check_2		);
				jsonObj.put("check_3"		, r_check_3		);
 			}
  			return jsonObj;
	        
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * 
	 * @param 단말에서 SMS 수신받은 정보
	 * @return  본인확인 인증번호 검증 ,14세 미만 법정대리인 동의 등록
	 * @throws Exception
	 */
	public JSONObject identityAudit(Map<String, Object> map) throws Exception {
		
		try {
			JSONObject jsonObj 	= new JSONObject();
			
			String urlCode 		= "001002";				// URL 코드
			String phoneNo  	= "";					// 휴대번 번호
			String phoneCorp 	= "";					// 이동통신사
			String birthDay  	= "";					// 생년월일
			String gender		= "";					// 성별
			String nation		= "0";					// 내외국인 (0:내,1:외)
			String name			= "";					// 이용자 성명
			String extendVar	= "0000000000000000";   // 확장변수
			
			String smsNum		= "";					// 요청일시
			String certNum		= "";					// 서버 전달 고유번호
			String check_1  	= "";					// 문자 인증확인 파라메터 1
			String check_2  	= "";					// 문자 인증확인 파라메터 2
			String check_3  	= "";					// 문자 인증확인 파라메터 3
			
			String tr_cert  	= "";
			
			//입력값 변수--------------------------------------------------------------------------------------------
			Map<String, Object> identityInfo		= (Map<String, Object>) map.get("identityInfo");

	        name				= identityInfo.get("mobileUserName").toString();	//성명
	        birthDay			= identityInfo.get("mobileBirth").toString();		//생년월일
	        gender				= identityInfo.get("mobileGender").toString();		//성별
	        phoneCorp			= identityInfo.get("mobileCorp").toString();		//이동통신사(SKT,KTF,LGT)
	        phoneNo				= identityInfo.get("mobileNum").toString();			//휴대폰번호
	        
	        smsNum				= identityInfo.get("smsNum").toString();			//SMS 수신 인증 번호
	        certNum				= identityInfo.get("checksum").toString().trim();			//서버 전달 고유번호
	        check_1				= identityInfo.get("check_1").toString().trim();			//문자 인증확인 파라메터 1
	        check_2				= identityInfo.get("check_2").toString().trim();			//문자 인증확인 파라메터 2
	        check_3				= identityInfo.get("check_3").toString().trim();			//문자 인증확인 파라메터 3

			/** 요청번호(certNum) 주의사항 ****************************************************************************
			* 1. 본인인증 결과값 복호화를 위한 키로 활용되므로 중요함.
			* 2. 본인인증 요청시 중복되지 않게 생성해야함. (예-시퀀스번호)
			***********************************************************************************************************/

			//01. 한국모바일인증(주) 암호화 모듈 선언
		    com.icert.comm.secu.IcertSecuManager seed  = new com.icert.comm.secu.IcertSecuManager();

			//02. 1차 암호화 (tr_cert 데이터변수 조합 후 암호화)
			String enc_tr_cert 	= "";
			tr_cert     		= certNum +"/"+ smsNum +"/"+ check_1 +"/"+ check_2 +"/"+ check_3 +"/"+ extendVar;
			enc_tr_cert 		= seed.getEnc(tr_cert, "");

			//03. 1차 암호화 데이터에 대한 위변조 검증값 생성 (HMAC)
			String hmacMsg 		= "";
	        hmacMsg 			= seed.getMsg(enc_tr_cert);

			//04. 2차 암호화 (1차 암호화 데이터, HMAC 데이터, extendVar 조합 후 암호화)
			tr_cert  			= seed.getEnc(enc_tr_cert +"/"+ hmacMsg +"/"+ extendVar, "");

			//05. 본인인증요청 URL로 암호화 데이터 넘기기
	        String send_url 	= apiEnv.getProperty("kmc.send_url2") + "?tr_cert=" + tr_cert;

	        //06. URL로 암호화 값 보내고 암호화된 결과 받기
	        String rec_cert 	= "";
        
	        CloseableHttpClient client 		= HttpClients.createDefault();
	        HttpPost httppost 				= new HttpPost(send_url);
	        HttpResponse response 			= client.execute(httppost);
	        
	        System.out.println("POST ========================= : " + response.getStatusLine().toString() );
	       
	        HttpEntity entity = response.getEntity();
	        
	        //결과값 변수--------------------------------------------------------------------------------------------
            String encPara      = ""; //암호화된 통합 파라미터
            String encMsg1      = ""; //암호화된 통합 파라미터의 Hash값
			String encMsg2      = ""; //암호화된 통합 파라미터의 Hash값 비교를 위한 Hash값

			String msgChk       = "";
			String r_certNum    = ""; //요청번호
			String r_CI			= ""; //연계정보(CI)
			String r_DI			= ""; //중복가입확인정보(DI)
			String r_result     = ""; //인증결과값
			String r_resultCode = ""; //인증결과코드
			String r_check_1    = ""; //인증번호 확인결과 전송 및 SMS재전송 요청을 위한 파라미터 1(수정불가)
			String r_check_2    = ""; //인증번호 확인결과 전송 및 SMS재전송 요청을 위한 파라미터 2(수정불가)
			String r_check_3    = ""; //인증번호 확인결과 전송 및 SMS재전송 요청을 위한 파라미터 3(수정불가)
			//-------------------------------------------------------------------------------------------------------
			
			if (entity != null) {
	        	rec_cert		= EntityUtils.toString(entity);
	  			
	        	//08. 1차 복호화 (요청번호를 이용해 복호화)
				rec_cert = seed.getDec(rec_cert, certNum);
	
				int inf1 = rec_cert.indexOf("/",0);
				int inf2 = rec_cert.indexOf("/",inf1+1);
	
				encPara = rec_cert.substring(0, inf1);
				encMsg1 = rec_cert.substring(inf1+1, inf2);
	
				//09. 1차 복호화 데이터에 대한 위변조 검증값 검증
				encMsg2 = seed.getMsg(encPara);
	
				if(encMsg2.equals(encMsg1)){
					msgChk="Y";		//N:비정상 접근
				}
				
				//10. 2차 복호화 (요청번호를 이용해 복호화)
				rec_cert  = seed.getDec(encPara, certNum);
	
				int info1  = rec_cert.indexOf("/",0);
				int info2  = rec_cert.indexOf("/",info1+1);
				int info3  = rec_cert.indexOf("/",info2+1);
				int info4  = rec_cert.indexOf("/",info3+1);
				int info5  = rec_cert.indexOf("/",info4+1);
				int info6  = rec_cert.indexOf("/",info5+1);
				int info7  = rec_cert.indexOf("/",info6+1);
				int info8  = rec_cert.indexOf("/",info7+1);
	
				r_certNum	= rec_cert.substring(0,info1);
				r_CI		= rec_cert.substring(info1+1,info2);
				r_DI		= rec_cert.substring(info2+1,info3);
				r_result	= rec_cert.substring(info3+1,info4);
				r_resultCode= rec_cert.substring(info4+1,info5);
				r_check_1	= rec_cert.substring(info5+1,info6);
				r_check_2	= rec_cert.substring(info6+1,info7);
				r_check_3	= rec_cert.substring(info7+1,info8);
	
				//11. CI, DI 복호화 (요청번호를 이용해 복호화)
				r_CI  = seed.getDec(r_CI, certNum);
				r_DI  = seed.getDec(r_DI, certNum);
				
				System.out.println("r_resultCode ========================= : " + r_resultCode );
				
				//정상 결과 시 보호자 동의 저장
				if(r_resultCode.equals("KIST0000")){
					ParentsAgree parentsAgree 			= new ParentsAgree();
					
					parentsAgree.setFd_parents_name( 	name	);
					parentsAgree.setFd_parents_mobile(	phoneNo	);
					parentsAgree.setFd_agree_yn(		"Y"		);
					parentsAgree.setMbr_seq(			map.get("mbr_seq").toString());
//					parentsAgree.setMbr_seq(			map.get("userId").toString());
					parentsAgree.setFk_cp_code(			map.get("cpCode").toString());

					parentsAgreeDao.setParentsAgree(parentsAgree);
				}
				
				
				jsonObj.put("resultCode"	, r_resultCode	);
			}	
		
	        return jsonObj;
	        
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

