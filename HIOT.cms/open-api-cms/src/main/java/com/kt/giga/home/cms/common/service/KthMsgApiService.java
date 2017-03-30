package com.kt.giga.home.cms.common.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.*;
import org.apache.http.util.*;
import org.apache.http.message.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.common.domain.*;

/**
 * kth 메세징 API 처리 서비스
 * @author 조상현
 *
 */
@Service
public class KthMsgApiService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private APIEnv apiEnv;
		
	/**
	 * 
	 * @param <KthMsgApi> <br>
	 * 	send_time : 발송시간(없을 경우 즉시발송) “20130529171111”(2013-05-29-17:11:11) <br>
	 * 	send_phone : 발신자 전화번호 “01012345678”<br>
	 * 	dest_phone : 수신자 전화번호(동보 발송 시 콤마”,”구분자 사용) “01012345678”<br>
	 * 	send_name : 발신자 이름(32byte 미만) <br>
	 * 	dest_name : 수신자 이름(32byte 미만) <br>
	 * 	subject : 메시지의 제목(60byte 미만) <br>
	 * 	msg_body : 메시지의 내용(80byte 미만) <br>
	 * 	smsExcel : 대량 전송 할 경우 엑셀파일에 파라미터 데이터 입력하여 보냄(필드구성 : 발송시간|수신자번호|수신자이름|발신자번호|발신자이름|제목|내용) <br>
	 * @return  kth 대용량 SMS 서비스
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject sendSms(KthMsgApi kthMsgApi) throws Exception {
		
		//String CLIENT_ID 		= apiEnv.getProperty("kthMsgApi.client_id");			
		//String CLIENT_PW 		= apiEnv.getProperty("kthMsgApi.client_pw");			
//		String SMS_URL 			= apiEnv.getProperty("kthMsgApi.sms_url") + CLIENT_ID;
		String SMS_URL 			= apiEnv.getProperty("kthMsgApi.www_url");
		//String HTTP_HEADER		= apiEnv.getProperty("kthMsgApi.http_header");
		
        JSONObject jsonObj 		= new JSONObject();
        
        CloseableHttpClient httpClient = null;
		
		try {
	        String transacId	= "";

	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
    		nameValuePairs.add(new BasicNameValuePair("send_time"	, kthMsgApi.getSend_time()	));
    		nameValuePairs.add(new BasicNameValuePair("send_phone"	, kthMsgApi.getSend_phone()	));
    		nameValuePairs.add(new BasicNameValuePair("send_name"	, kthMsgApi.getSend_name()	));
    		nameValuePairs.add(new BasicNameValuePair("dest_phone"	, kthMsgApi.getDest_phone()	));
    		nameValuePairs.add(new BasicNameValuePair("dest_name"	, kthMsgApi.getDest_name()	));
    		nameValuePairs.add(new BasicNameValuePair("subject"		, kthMsgApi.getSubject()	));
    		nameValuePairs.add(new BasicNameValuePair("msg_body"	, kthMsgApi.getMsg_body()	));
    		
    		HttpPost httpPost = new HttpPost(SMS_URL); 
    		
	        httpClient 		= HttpClients.createDefault();
    		httpPost.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
    		//httpPost.addHeader("x-waple-authorization", HTTP_HEADER);
	        
    		UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
    		httpPost.setEntity(entityRequest);
    		
    		HttpResponse response = httpClient.execute(httpPost);
    		
	        System.out.println("POST ========================= : " + response.getStatusLine().toString() );
	        
	        HttpEntity entity = response.getEntity();
	        JSONParser parser = new JSONParser();
	        
	        if (entity != null) {
	        	
	        	String rtn_str		= EntityUtils.toString(entity);
	        	jsonObj 			= (JSONObject)parser.parse(rtn_str);
	        	
	        	System.out.println(jsonObj);
	        	
//	        	if(jsonObj.get("code").equals("200")){	//발송성공
	        	if(jsonObj.get("code").toString().equals("200")){	//발송성공
//	        		transacId = jsonObj.get("cmid").toString();	//서버에서 생성한 request를 식별할 수 있는 유일한 키
	        		jsonObj.put("code"	, 200);					
	        		jsonObj.put("msg"	, "발송성공");			
//	        		jsonObj.put("transacId"	, transacId);
	        		
	        	} else {
	        		jsonObj.put("code", 500);	//발송실패
	        	}
	        } else {
				jsonObj.put("code", 500);	//발송실패
	        }

		} catch(Exception e) {

			log.error(e.getMessage(), e);
		} finally {
			if(httpClient != null){
				httpClient.close();
			}
		}

		return jsonObj;
	}

}

