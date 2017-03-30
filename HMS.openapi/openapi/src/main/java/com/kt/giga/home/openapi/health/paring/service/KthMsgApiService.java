package com.kt.giga.home.openapi.health.paring.service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.health.paring.domain.KthMsgApi;
import com.kt.giga.home.openapi.service.APIException;
import com.kt.giga.home.openapi.util.ObjectConverter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * kth 메세징 API 처리 서비스
 *
 * @author 조상현
 *
 */
@Service
public class KthMsgApiService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

//	@Autowired
//	private TransactionService transactionService;

    @Autowired
    private APIEnv apiEnv;

//	long time 						= System.currentTimeMillis();
//	SimpleDateFormat cur_time 		= new SimpleDateFormat("yyyyMMddHHmmss");
//	String date						= cur_time.format(new Date(time));

    /**
     *
     * @param {@link com.kt.giga.home.openapi.health.paring.domain.KthMsgApi}
     *
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
    public JSONObject sendSms(KthMsgApi kthMsgApi) throws Exception {

        String CLIENT_ID 		= apiEnv.getProperty("kthMsgApi.client_id");			// ID
        String SMS_URL 			= apiEnv.getProperty("kthMsgApi.sms_url")+CLIENT_ID;
        String HTTP_HEADER		= apiEnv.getProperty("kthMsgApi.http_header");

        try (CloseableHttpAsyncClient httpClient	= HttpAsyncClients.createDefault()) {
            JSONObject jsonObj 				= new JSONObject();

            String transacId;

            List<NameValuePair> nameValuePairs = new ArrayList<>(1);
            nameValuePairs.add(new BasicNameValuePair("send_time"		, kthMsgApi.getSend_time()	));
            nameValuePairs.add(new BasicNameValuePair("send_phone"		, kthMsgApi.getSend_phone()	));
            nameValuePairs.add(new BasicNameValuePair("send_name"		, kthMsgApi.getSend_name()	));
            nameValuePairs.add(new BasicNameValuePair("dest_phone"		, kthMsgApi.getDest_phone()	));
            nameValuePairs.add(new BasicNameValuePair("dest_name"		, kthMsgApi.getDest_name()	));
            nameValuePairs.add(new BasicNameValuePair("subject"			, kthMsgApi.getSubject()	));
            nameValuePairs.add(new BasicNameValuePair("msg_body"		, kthMsgApi.getMsg_body()	));

            HttpPost httpPost 	= new HttpPost(SMS_URL);
//    		HttpGet httpGet 	= new HttpGet(SMS_URL);	//비동기처리

//	        CloseableHttpClient httpClient 		= HttpClients.createDefault();		//동기 처리
            httpClient.start();//비동기 처리

            httpPost.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.addHeader("x-waple-authorization", HTTP_HEADER);

            UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httpPost.setEntity(entityRequest);

            String jsonStr = ObjectConverter.toJSONString(nameValuePairs, Include.ALWAYS);
            log.info("======================= sms 발송  =======================");
            log.info("\n" + jsonStr);
            log.info("=======================================================");

//    		HttpResponse response = httpClient.execute(httpPost);				//동기 처리
            try {
                Future<HttpResponse> future = httpClient.execute(httpPost, null);    //비동기 처리
                HttpResponse response = future.get();                                //비동기 처리

                log.info("================= : " + response.getStatusLine().toString());

                HttpEntity entity = response.getEntity();
                JSONParser parser = new JSONParser();

                if (entity != null) {
                    String rtn_str = EntityUtils.toString(entity);
                    jsonObj = (JSONObject) parser.parse(rtn_str);
//	        	System.out.println("jsonObj ========================= : " + jsonObj );

                    if (jsonObj.get("result_code").equals("200")) {    //발송성공
                        transacId = (String) jsonObj.get("cmid");    //서버에서 생성한 request를 식별할 수 있는 유일한 키
                        jsonObj.put("resultCode", "1");        //발송성공
                        jsonObj.put("transacId", transacId);        //transacId

                    } else {
                        jsonObj.put("resultCode", "2");            //발송실패
                    }
                } else {
                    jsonObj.put("resultCode", "2");            //발송실패
                }
            } catch (Exception fe) {
                throw fe;
            } finally {
                if (null != httpPost)
                httpPost.releaseConnection();
            }

            jsonStr = ObjectConverter.toJSONString(jsonObj, Include.ALWAYS);
            log.info("======================= sms 발송 결과 =======================");
            log.info("\n" + jsonStr);
            log.info("============================================================");

            return jsonObj;

        } catch(DataAccessException e) {

            log.error(e.getMessage(), e);
            throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

