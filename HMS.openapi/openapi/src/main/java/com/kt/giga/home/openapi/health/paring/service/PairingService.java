package com.kt.giga.home.openapi.health.paring.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.domain.SvcTgtBas;
import com.kt.giga.home.openapi.health.otv.dao.OTVLocationDao;
import com.kt.giga.home.openapi.health.paring.dao.MsgTrmTxnDao;
import com.kt.giga.home.openapi.health.paring.dao.OtvPairingBasDao;
import com.kt.giga.home.openapi.health.paring.domain.KthMsgApi;
import com.kt.giga.home.openapi.health.paring.domain.MsgTrmTxn;
import com.kt.giga.home.openapi.health.paring.domain.OtvPairingBas;
import com.kt.giga.home.openapi.service.APIException;

/**
 * 페어링 관련 처리 서비스
 * 
 * @author 조상현
 *
 */
@Service
public class PairingService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private APIEnv env;
	
	@Autowired
	private OTVLocationDao otvLocationDao;
	
	@Autowired
	private OtvPairingBasDao otvPairingBasDao;
	
	@Autowired
	private KthMsgApiService kthMsgApiService;
	
	@Autowired
	private MsgTrmTxnDao msgTrmTxnDao;

//	@Autowired
//	private TransactionService transactionService;
	
	
	/**
	 * 
	 * @param cpCode:서비스 구분코드, telNo:휴대폰 번호, said:올레TV 계약 ID
	 * @return 페어링 조회 및 휴대폰 인증 요청
	 * @throws Exception
	 */
	public JSONObject getPairingAuth(Map<String, Object> map) throws Exception {
		JSONObject jsonObj 				= new JSONObject();

		try {
			int memberCnt				= otvPairingBasDao.getMemberCnt(map);	//4:회원가입 존재여부

			// 회원상태값. 1:성공, 2:실패, 3:페어링 존재, 4:회원 미가입자
			if(memberCnt > 0){	// 회원 가입자
				int paringCnt				= otvPairingBasDao.getParingCnt(map);	//3:페어링 존재여부
				
				if(paringCnt > 0){	// 페어링 존재
					jsonObj.put("mbrSttus", 	"3"			);		
					
				}else{
//					//인증 SMS메세지 전송
//					int msgRtn	= 1;
//
//					if(msgRtn == 1){
//						jsonObj.put("mbrSttus", 	"1"			);		
//					}else{
//						jsonObj.put("mbrSttus", 	"2"			);		
//					}
					
					try {
						//-------------------------- 인증메세지 전송 ---------------------------
						Random generator 	= new Random();   
						int randomMsg		= generator.nextInt(8999) + 1000;	//1000~9999 4자리 난수발생
						String otpCode		= Integer.toString(randomMsg);

						KthMsgApi kthMsgApi = new KthMsgApi();
						
						String sendMsg		= "[올레 기가 홈피트니스] 올레 TV 연결을 위해 인증번호 ["+ otpCode +"]을 입력해 주세요.";
//						sendMsg				= "[olleh GiGA home-fitness] Please enter the pin number ["+ sendMsg +"] in order to connect the olleh TV.";
						
						//발송할 메세지 저장
						kthMsgApi.setSend_name("HHC");						// 발신자 이름
						kthMsgApi.setSend_phone("100");						// 발신자 전화번호  // [필수]
						kthMsgApi.setSend_time("");							// 발신 예약일시 	// 없으면 즉시발송
						kthMsgApi.setDest_name("");							// 수진자 이름
						kthMsgApi.setDest_phone( (String)map.get("telNo") );// 수신자 전화번호	// [필수]
						kthMsgApi.setSubject("HHC");						// 제목
						kthMsgApi.setMsg_body( sendMsg );					// 메세지 내용

//						kthMsgApiService.sendSms(kthMsgApi);	//메세지 발송(테스트용으로 조건앞에서 확인함)

						//서비스대상 기본
						Map<String, Object> svcTgtBasMap	= new HashMap<String, Object>();
						svcTgtBasMap.put("cpCode"	, (String)map.get("cpCode")	);
						svcTgtBasMap.put("telNo"	, (String)map.get("telNo")	);
						
						SvcTgtBas svcTgtBas 		= msgTrmTxnDao.getSvcTgtBas(svcTgtBasMap);
						if(svcTgtBas != null){
							String dstr_cd				= svcTgtBas.getDstr_cd();		//지역 코드
							String mbr_seq				= svcTgtBas.getMbr_seq();		//회원 일련번호
//							String mbr_seq				= svcTgtBas.getMbrs_seq();		//회원 일련번호
							String svc_theme_cd			= svcTgtBas.getSvc_theme_cd();	//서비스 테마코드
							String unit_svc_cd			= svcTgtBas.getUnit_svc_cd();	//단위서비스 코드
							
							//서비스대상 접속기본
							Map<String, Object> svcTgtConnBasMap	= new HashMap<String, Object>();
							svcTgtConnBasMap.put("dstr_cd"		, dstr_cd		);
							svcTgtConnBasMap.put("mbr_seq"		, mbr_seq		);
							svcTgtConnBasMap.put("svc_theme_cd"	, svc_theme_cd	);
							svcTgtConnBasMap.put("unit_svc_cd"	, unit_svc_cd	);
							
							String conn_terml_id		= msgTrmTxnDao.getSvcTgtConnBas(svcTgtConnBasMap);	//접속단말 아이디
							
							if(conn_terml_id != null){	// 메세지 발송 및 이력 저장
//								JSONObject jsonRtn = kthMsgApiService.sendSms(kthMsgApi);	//메세지 발송 (트랜잭션 id 기다림)
								kthMsgApiService.sendSms(kthMsgApi);						//메세지 발송 (트랜잭션 id 안기다림, 자체 생성함)
								long time 					= System.currentTimeMillis();
								SimpleDateFormat cur_time 	= new SimpleDateFormat("yyyyMMddHHmmssSSS");
								String transacId			= cur_time.format(new Date(time)) + otpCode;
								
								//트랜젝션 id를 DB에 저장
								MsgTrmTxn msgTrmTxn				= new MsgTrmTxn();
								
								msgTrmTxn.setConn_terml_id(conn_terml_id);
								msgTrmTxn.setMbr_seq(mbr_seq);
								msgTrmTxn.setDstr_cd(dstr_cd);
								msgTrmTxn.setSvc_theme_cd(svc_theme_cd);
								msgTrmTxn.setUnit_svc_cd(unit_svc_cd);
								msgTrmTxn.setMsg_trm_sbst(otpCode);
//								msgTrmTxn.setMsg_trm_key_val( jsonRtn.get("transacId").toString() );
								msgTrmTxn.setMsg_trm_key_val( transacId );	//트랜잭션 id 자체 생성함
								
								msgTrmTxnDao.setMsgTrmTxn(msgTrmTxn);	//DB 이력 저장

//								if( jsonRtn.get("resultCode").equals("1") ){
//									
//									jsonObj.put("mbrSttus", 	"1"							);	//전송성공
//									jsonObj.put("transacId", 	jsonRtn.get("transacId")	);	//트랜잭션 id	
//									
//								}else{
//									jsonObj.put("mbrSttus", 	"2"			);	//메세지 전송 실패
//								}
								
								//전송성공 처리하고 트랜잭션 저장
								jsonObj.put("mbrSttus", 	"1"							);	//전송성공
//								jsonObj.put("transacId", 	jsonRtn.get("transacId")	);	//트랜잭션 id	
								jsonObj.put("transacId", 	transacId	);	//트랜잭션 id 자체 생성함
							}else{
								jsonObj.put("mbrSttus", 	"2"			);	//메세지 전송 실패
							}
							
						}else{
							jsonObj.put("mbrSttus", 	"2"			);	//메세지 전송 실패
						}
						//-------------------------- 인증메세지 전송 ---------------------------
					}catch (Exception e){
						log.error(e.getMessage(), e);
						jsonObj.put("mbrSttus", 	"2"			);		
					}
				}
				
			}else{	// 회원 미가입자
				jsonObj.put("mbrSttus", 	"4"				);	
			}

		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return jsonObj;
	}
	
	/**
	 * 
	 * @param Map (svcCode, telNo, said, appId, transacId, otpCode)
	 * @return 페어링 등록 요청
	 * @throws Exception
	 */
	public JSONObject getPairingAudit(Map<String, Object> map) throws Exception {
		JSONObject jsonObj 				= new JSONObject();

		try {
			int memberCnt				= otvPairingBasDao.getMemberCnt(map);	//회원가입 존재여부
			
			if(memberCnt > 0){	// 회원 가입자
				//트랜젝션 id 로 메시지 발송내역 있는지 조회
				Map<String, Object> msgTrmTxnMap	= new HashMap<String, Object>();
				msgTrmTxnMap.put("msg_trm_key_val"	, map.get("transacId")		);//트랜잭션 id
				msgTrmTxnMap.put("msg_trm_sbst"		, map.get("otpCode")		);//sms 인증번호 4자리
				
				MsgTrmTxn msgTrmTxn	= msgTrmTxnDao.getMsgTrmTxn(msgTrmTxnMap);
				
				//메시지 발송내역이 있으면 페어링 등록
				if(msgTrmTxn != null){
					OtvPairingBas otvPairingBas		= new OtvPairingBas();
					
					otvPairingBas.setOtv_svc_cont_id(	map.get("said").toString()	);
					otvPairingBas.setMbr_seq(			msgTrmTxn.getMbr_seq()		);
					otvPairingBas.setDstr_cd(			msgTrmTxn.getDstr_cd()		);
					otvPairingBas.setSvc_theme_cd(		msgTrmTxn.getSvc_theme_cd()	);
					otvPairingBas.setUnit_svc_cd(		msgTrmTxn.getUnit_svc_cd()	);
					otvPairingBas.setTel_no(			map.get("telNo").toString()	);
					otvPairingBas.setOtv_app_id(		map.get("appId").toString()	);
//					otvPairingBas.setStb_nick_nm(									);
//					otvPairingBas.setCret_dt(										);

					//기 등록되어 있으면 스킵(중복 저장 방지)
					int cnt = otvPairingBasDao.getOtvPairingBasCnt(otvPairingBas);
					if(cnt==0){
						otvPairingBasDao.setOtvPairingBas(otvPairingBas);
					}
					
					// otv tv app. 위치정보 생성 - 위치정보 테이블에 없을 경우만.
					Map<String, Object> otvConfMap	= new HashMap<String, Object>();
					otvConfMap.put("said", map.get("said").toString());
					otvConfMap.put("dstrCd", msgTrmTxn.getDstr_cd());
					otvConfMap.put("svcThemeCd", msgTrmTxn.getSvc_theme_cd());
					otvConfMap.put("unitSvcCode", msgTrmTxn.getUnit_svc_cd());
					otvConfMap.put("type", env.getProperty("health.tvapp.location.type"));
					otvConfMap.put("x", Integer.parseInt(env.getProperty("health.tvapp.location.x")));
					otvConfMap.put("y", Integer.parseInt(env.getProperty("health.tvapp.location.y")));
		
					// otv tv app. 위치정보 저장 - 위치저장 테이블에 없을 경우.
					cnt = otvLocationDao.getOtvConfCnt(otvConfMap); 
					
					log.info("######## getPairingAudit -> insert otv conf ###########");
					log.info("cnt : "+cnt);
					log.info(otvConfMap.toString());
					log.info("#######################################################");
				
					if(cnt == 0){
						otvLocationDao.insertOtvConf(otvConfMap);
					}

					jsonObj.put("pairRegSttus"			, "1"			);	
				}else{
					jsonObj.put("pairRegSttus"			, "2"			);	// 페어링 정보 없음
				}
			}else{	
				jsonObj.put("pairRegSttus"				, "3"			);	// 회원 미가입자
			}

		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return jsonObj;
	}
	
	/**
	 * 
	 * @param Map (otv_svc_cont_id, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, stb_nick_nm)
	 * @return 페어링 - OTV 닉네임 수정
	 * @throws Exception
	 */
	public JSONObject modifyPairing(Map<String, Object> map) throws Exception {
		JSONObject jsonObj 				= new JSONObject();

		try {
				OtvPairingBas otvPairingBas		= new OtvPairingBas();
				
				otvPairingBas.setOtv_svc_cont_id(	map.get("otv_svc_cont_id").toString()	);
				otvPairingBas.setMbr_seq(			map.get("mbr_seq").toString()			);
				otvPairingBas.setDstr_cd(			map.get("dstr_cd").toString()			);
				otvPairingBas.setSvc_theme_cd(		map.get("svc_theme_cd").toString()		);
				otvPairingBas.setUnit_svc_cd(		map.get("unit_svc_cd").toString()		);
//				otvPairingBas.setTel_no(			map.get("otv_svc_cont_id").toString()	);
//				otvPairingBas.setOtv_app_id(		map.get("otv_svc_cont_id").toString()	);
				otvPairingBas.setStb_nick_nm(		map.get("stb_nick_nm").toString()		);
				
				int cnt	= otvPairingBasDao.modifyOtvPairingBasCnt(otvPairingBas);
				if(cnt == 1){
					otvPairingBasDao.modifyOtvPairingBas(otvPairingBas);
					jsonObj.put("nickMdfSttus"			, "1"			);	
				}else{
					jsonObj.put("nickMdfSttus"			, "2"			);	
				}

		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return jsonObj;
	}
	
	/**
	 * 
	 * @param Map (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, telNo)
	 * @return 페어링 - 페어링된 STB 리스트 조회
	 * @throws Exception
	 */
	public JSONObject getPairingStbList(Map<String, Object> map) throws Exception {
		
		JSONObject jsonObj 			= new JSONObject();
		JSONArray jsonArray 		= new JSONArray();
		try {
				OtvPairingBas otvPairingBas		= new OtvPairingBas();
				
				otvPairingBas.setMbr_seq(		map.get("mbr_seq").toString()		);
				otvPairingBas.setDstr_cd(		map.get("dstr_cd").toString()		);
				otvPairingBas.setSvc_theme_cd(	map.get("svc_theme_cd").toString()	);
				otvPairingBas.setUnit_svc_cd(	map.get("unit_svc_cd").toString()	);
				
				otvPairingBas.setTel_no(		map.get("tel_no").toString()		);
				
				List<OtvPairingBas>	otvPairingBasList	= otvPairingBasDao.getOtvPairingBasList(otvPairingBas);
				
				jsonObj.put("totalCnt"		, otvPairingBasList.size()		);
				for(OtvPairingBas listCol : otvPairingBasList){
					JSONObject listObj 			= new JSONObject();
					
					listObj.put("svcCode"		, listCol.getDstr_cd() + listCol.getSvc_theme_cd() + listCol.getUnit_svc_cd()	);	// 서비스 구분 코드 (지역코드+테마코드+단위서비스)
					if(listCol.getStb_nick_nm() != null){
						listObj.put("nickname"		, listCol.getStb_nick_nm()		);	// 올레 TV 닉네임
					}else{
						listObj.put("nickname"		, ""							);	// 올레 TV 닉네임
					}
					listObj.put("telNo"			, listCol.getTel_no()			);	// 휴대폰 번호
					listObj.put("said"			, listCol.getOtv_svc_cont_id()	);	// 올레TV 계약 ID
					listObj.put("appId"			, listCol.getOtv_app_id()		);	// 매시업 매니저에 등록된 앱ID(제어 대상 앱ID)

					jsonArray.add(listObj);
				}
				jsonObj.put("list"		, jsonArray		);
				
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return jsonObj;
	}
	
	/**
	 * 
	 * @param cpCode:서비스 구분코드, telNo:휴대폰 번호, said:올레TV 계약 ID
	 * @return 페어링 - 페어링된 App 리스트 조회
	 * @throws Exception
	 */
	public JSONObject getPairingAppList(Map<String, Object> map) throws Exception {
		
		JSONObject jsonObj 			= new JSONObject();
		JSONArray jsonArray 		= new JSONArray();
		try {

			OtvPairingBas otvPairingBas		= new OtvPairingBas();
			
//					otvPairingBas.setMbr_seq(			svcTgtBas.getMbr_seq()				);	// 회원 일련번호
//					otvPairingBas.setMbr_seq(			svcTgtBas.getMbrs_seq()				);	// 회원 일련번호
			otvPairingBas.setDstr_cd(			map.get("dstr_cd").toString()		);	// 지역 코드
			otvPairingBas.setSvc_theme_cd(		map.get("svc_theme_cd").toString()	);	// 서비스 테마코드
			otvPairingBas.setUnit_svc_cd(		map.get("cpCode").toString()		);	// 단위서비스 코드
			otvPairingBas.setTel_no(			map.get("telNo").toString()			);	// 전화번호
			otvPairingBas.setOtv_svc_cont_id(	map.get("said").toString()			);	// 서비스 계약아이디
			
			List<OtvPairingBas>	otvPairingBasList	= otvPairingBasDao.getOtvPairingBasList(otvPairingBas);
			
			jsonObj.put("totalCnt"		, otvPairingBasList.size()		);
			for(OtvPairingBas listCol : otvPairingBasList){
				JSONObject listObj 			= new JSONObject();
				
				listObj.put("svcCode"		, listCol.getDstr_cd() + listCol.getSvc_theme_cd() + listCol.getUnit_svc_cd()	);	// 서비스 구분 코드 (지역코드+테마코드+단위서비스)
				if(listCol.getStb_nick_nm() != null){
					listObj.put("nickname"		, listCol.getStb_nick_nm()		);	// 올레 TV 닉네임
				}else{
					listObj.put("nickname"		, ""							);	// 올레 TV 닉네임
				}
				listObj.put("telNo"			, listCol.getTel_no()			);	// 휴대폰 번호
				listObj.put("said"			, listCol.getOtv_svc_cont_id()	);	// 올레TV 계약 ID
				listObj.put("appId"			, listCol.getOtv_app_id()		);	// 매시업 매니저에 등록된 앱ID(제어 대상 앱ID)

				jsonArray.add(listObj);
			}
			jsonObj.put("list"		, jsonArray		);
		

		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return jsonObj;
	}

	/**
	 * 
	 * @param Map (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, otv_svc_cont_id)
	 * @return 페어링 - 페어링된 STB 삭제(단일 끊기)
	 * @throws Exception
	 */
	public void delPairingStb(Map<String, Object> map) throws Exception {
		
		try {
				OtvPairingBas otvPairingBas		= new OtvPairingBas();
				
//				otvPairingBas.setMbr_seq(			map.get("mbr_seq").toString()			);
				otvPairingBas.setDstr_cd(			map.get("dstr_cd").toString()			);
				otvPairingBas.setSvc_theme_cd(		map.get("svc_theme_cd").toString()		);
				otvPairingBas.setUnit_svc_cd(		map.get("unit_svc_cd").toString()		);
				otvPairingBas.setTel_no(			map.get("telNo").toString()				);
				otvPairingBas.setOtv_svc_cont_id(	map.get("otv_svc_cont_id").toString()	);
				
				otvPairingBasDao.delPairingStb(otvPairingBas);
				
				// otv tv app. 위치정보 삭제 - 페어링 기본 테이블에 없을 경우만.
				int cnt = otvPairingBasDao.getSaidCnt(otvPairingBas); 
				if(cnt == 0){
					Map<String, Object> otvConfMap	= new HashMap<String, Object>();
					otvConfMap.put("said", map.get("otv_svc_cont_id").toString());
					otvConfMap.put("dstrCd", map.get("dstr_cd").toString());
					otvConfMap.put("svcThemeCd",map.get("svc_theme_cd").toString());
					otvConfMap.put("unitSvcCode", map.get("unit_svc_cd").toString());
					
					log.info("######## delPairingStb -> delete otv conf #############");
					log.info(otvConfMap.toString());
					log.info("#######################################################");
					
					otvLocationDao.deleteOtvConf(otvConfMap);
				}
				

		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 
	 * @param Map (cpCode:서비스 구분코드, telNo:휴대폰 번호, said:올레TV 계약 ID)
	 * @return 페어링 - 페어링된 App 삭제(단일 끊기)
	 * @throws Exception
	 */
	public void delPairingApp(Map<String, Object> map) throws Exception {
		
		try {
			OtvPairingBas otvPairingBas		= new OtvPairingBas();

			otvPairingBas.setDstr_cd(			map.get("dstr_cd").toString()		);	// 지역 코드
			otvPairingBas.setSvc_theme_cd(		map.get("svc_theme_cd").toString()	);	// 서비스 테마코드
			otvPairingBas.setUnit_svc_cd(		map.get("cpCode").toString()		);	// 단위서비스 코드
			otvPairingBas.setTel_no(			map.get("telNo").toString()			);	// 전화번호
			otvPairingBas.setOtv_svc_cont_id(	map.get("said").toString()			);	// 서비스 계약아이디

			otvPairingBasDao.delPairingStb(otvPairingBas);

			// otv tv app. 위치정보 삭제 - 페어링 기본 테이블에 없을 경우만.
			int cnt = otvPairingBasDao.getSaidCnt(otvPairingBas); 
			if(cnt == 0){
				Map<String, Object> otvConfMap	= new HashMap<String, Object>();
				otvConfMap.put("said", map.get("said").toString());
				otvConfMap.put("dstrCd", map.get("dstr_cd").toString());
				otvConfMap.put("svcThemeCd",map.get("svc_theme_cd").toString());
				otvConfMap.put("unitSvcCode", map.get("cpCode").toString());
				
				log.info("######## delPairingApp -> delete otv conf #############");
				log.info(otvConfMap.toString());
				log.info("#######################################################");
				
				otvLocationDao.deleteOtvConf(otvConfMap);
			}					


		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
			throw new APIException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

