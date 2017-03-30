package com.kt.giga.home.openapi.hcam.service;

import com.google.gson.GsonBuilder;
import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.common.AuthToken;
import com.kt.giga.home.openapi.dao.HomeProvisionDao;
import com.kt.giga.home.openapi.domain.HomeProvision;
import com.kt.giga.home.openapi.hcam.dao.DummyDao;
import com.kt.giga.home.openapi.hcam.dao.UserDao;
import com.kt.giga.home.openapi.hcam.domain.User;
import com.kt.giga.home.openapi.health.paring.domain.KthMsgApi;
import com.kt.giga.home.openapi.health.paring.service.KthMsgApiService;
import com.kt.giga.home.openapi.kpns.domain.Event;
import com.kt.giga.home.openapi.kpns.domain.PushInfoRequest;
import com.kt.giga.home.openapi.kpns.service.KPNSService;
import com.kt.giga.home.openapi.service.HomeProvisionService;
import com.kt.giga.home.openapi.vo.spotdev.SpotDev;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service("HCam.DummyService")
public class DummyService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private KPNSService kpnsService;
	
	@Autowired
	private RelayTestControlService relayTestControlService;
	
	@Autowired
	private HomeProvisionService homeProvisionService;
	
	@Autowired
	private KthMsgApiService kthMsgApiService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private HomeProvisionDao homeProvisionDao;
	
	@Autowired
	private DummyDao dummyDao;
	
	@Autowired
	private APIEnv env;
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public void dummy(int index, int count) throws Exception {
		int custid = 69000000 + index;
		int said = 9000000 + index;
		int secret = 900000 + index;
		
		for(int i = index; i < count; i++) {
			said++;
			secret++;
			custid++;
			
			HomeProvision home = new HomeProvision();
			home.setSaId("3000" + said);
			home.setIntfCode("");
			home.setProdCode("OV46");
			home.setProdDtlCode("40");
			home.setWoNo("");
			home.setInsDate("");
			home.setCustid("300" + custid);
			home.setCustName("custName" + index);
			home.setCctvModelName("LPK5100WI");
			home.setCctvModelCd("K9183381");
			home.setCctvSaid("3000" + said);
			home.setCctvMac(this.randomMACAddress());
			home.setCctvSerial("");
			home.setCctvSecret("857CCB0EEA8A706C4C34A16891" + secret);
			home.setCctvCode("M011");
			home.setCctvLoc("79");
			home.setIdmsInsDate("");
			home.setRollbackState("0000");
	
			homeProvisionService.setProvision(home);
		}
	}
	
	public void dummyUser(int index, int count) throws Exception {
		SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
	    UserDao mapper = session.getMapper(UserDao.class);
	    
	    int userSeq = 10000000 + index;
	    
	    try {
	    	for(int i = index; i < count; i++) {
				userSeq++;
				
				long mbrSeq = userDao.createMbrSeq();
				User user = new User("001", "HIT", "001", mbrSeq, "user" + userSeq);
				
				String deviceId = "00000000-0000-0000-0001-0000" + userSeq;
				
				user.setTermlId(deviceId);
				user.setTelNo("00012345678");
				
				mapper.insertUserBase(user);
				mapper.insertServiceUserRelation(user);
				mapper.insertAppUser(user);
				mapper.insertAppUserToken(user);
				
				if (i % 1000 == 0) {
                    session.commit();
                    session.clearCache();
                }
			}
	    	
	    	session.commit();
            session.clearCache();
            
	    } catch (Exception e) {
	    	session.rollback();
	    } finally {
	        session.close();
	    }
	}
	
	public void kpns(Map<String, Object> map) throws Exception {
		int time = (int)map.get("time");
		int period = (int)map.get("period");
		List list = (List)map.get("pnsList");
		
		ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new KPNSTestService(scheduledExecutorService, kpnsService, list, time), 
				0, period, TimeUnit.MILLISECONDS);
	}
	
	public void initUser(String userId) throws Exception {
		dummyDao.deleteTermsAgree(userId);
		dummyDao.deleteParentsAgree(userId);
		dummyDao.updateSvcTgtConnBas(userId);
	}
	
	public String relay(Map<String, Object> map) throws Exception {
		List<Map> resultList = new ArrayList<Map>();
		StringBuffer sb = new StringBuffer();
		List<String> list = (List)map.get("list");
		int count = (int)map.get("count");
		
		for(String devUUID : list) {
			try {
				List<SpotDev> spotDevList = dummyDao.selectSpotDevList(devUUID);
				
				for(int i = 0; i < count; i++) {
					try {
						String termlId = UUID.randomUUID().toString();
						AuthToken authToken = AuthToken.encodeAuthToken(termlId, "", "", 0, "", "001");
						
						Map<String, String> res = relayTestControlService.getIntegratedRelaySession(authToken, spotDevList, devUUID);
	
						if(res != null && res.containsKey("rtspUrl")) {
							Map<String, String> resultMap = new HashMap<String, String>();
							
							resultMap.put("devUUID", devUUID);
							resultMap.put("rtspUrl", res.get("rtspUrl"));
							resultList.add(resultMap);
							
							if(i == (count-1)) {
								sb.append(res.get("rtspUrl"));
							} else {
								sb.append(res.get("rtspUrl") + "\n");
							}
						}
						
						Thread.sleep(100);
					} catch(Exception e) {
						log.error(e.getMessage(), e);
					}
				}
					
			} catch(Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		return sb.toString();
	}
	
	private String randomMACAddress(){
	    Random rand = new Random();
	    byte[] macAddr = new byte[6];
	    rand.nextBytes(macAddr);

	    StringBuilder sb = new StringBuilder(18);
	    for(byte b : macAddr){
	        if(sb.length() > 0){
	            sb.append(":");
	        }else{ //first byte, we need to set some options
	            b = (byte)(b | (byte)(0x01 << 6)); //locally adminstrated
	            b = (byte)(b | (byte)(0x00 << 7)); //unicast

	        }
	        sb.append(String.format("%02x", b));
	    }

	    return sb.toString().toUpperCase();
	}
	
	public class KPNSTestService extends Thread {
		
		ScheduledExecutorService scheduledExecutorService;
		KPNSService kpnsService;
		private int time;
		private List<String> list;
		private int seq = 1;
		private long startTime;
		private String reportUrl;
				
		public KPNSTestService(ScheduledExecutorService scheduledExecutorService, 
				KPNSService kpnsService, List<String> list, int time) {
			this.scheduledExecutorService = scheduledExecutorService;
			this.kpnsService = kpnsService;
			this.list = list;
			this.time = time;
			this.startTime = System.currentTimeMillis();
			this.reportUrl = env.getProperty("kpns.report.url");
		}

		@Override
		public void run() {
			for(String pnsRegId : list) {
				
				try {
					String msgid = "PNS_" + (seq++);
					
					// 1. 푸시 시퀀스 조회
					// long msg_seq = service.selectMsgTrmForwardTxnSeq();
					long msg_seq = System.nanoTime();
					
					// 2. 푸시 발송
					PushInfoRequest req = new PushInfoRequest();
					Map<String, Object> res;
					
					SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
					String nowDateTime = fmt.format(new Date());
					
					Map<String, Object> payloadData = new HashMap<String, Object>();
					payloadData.put("telNo", "01012345678");
					payloadData.put("msgId", msgid);
					payloadData.put("seq", Long.toString(msg_seq));
					payloadData.put("msg", "${telNo}에서 올레 홈캠을 로그인하였습니다.");
					payloadData.put("eventId", "001HIT001E0005");
					
					req.setRegistrationId(pnsRegId);
					req.setData(payloadData);
					req.setReportUrl(reportUrl);
					res = kpnsService.push(req);
					
					// 3. 푸시 이력 등록
					String msg_id = "";
					String result = "";
					String forwardYn = "N";
					if(res != null) {
						if((int)res.get("statusCode") == HttpStatus.SC_OK || (int)res.get("statusCode") == HttpStatus.SC_CREATED) {
							if(1 == (int)res.get("success")) {
								msg_id = (String)res.get("multicast_id");
								result = "success";
								forwardYn = "Y";										
								log.debug("Push Success : " + req.getRegistrationId());										
							} else {
								result = ((Map<String, String>)res.get("results")).get("error");
								log.debug("Push Failed : " + req.getRegistrationId());
							}
						} else {
							result = (String)res.get("statusText");
							log.debug("Push Failed : " + req.getRegistrationId());
						}
					} else {
						result = "unknown error";
						log.debug("Push Failed : " + req.getRegistrationId());
					}
					
					Event event = new Event();
					event.setPns_reg_id(pnsRegId);
					event.setDstr_cd("001");
					event.setSvc_theme_cd("HIT");
					event.setUnit_svc_cd("001");
					event.setSetup_cd("0000");
					event.setMsg_seq(msg_seq);
					event.setMsg_id(msg_id);
					event.setUp_msg_id(msgid);
					event.setMsg_trm_sbst(new GsonBuilder().create().toJson(payloadData));
					event.setMsg_trm_fail_txn(result);
					event.setMsg_trm_forward_yn(forwardYn);
					
					kpnsService.insertMsgTrmForwardTxn(event);
					
					// PNS 발송 요청 오류 시 운영자에게 SMS 전송
					try {
						if("N".equals(forwardYn)) {
							String errorMsg = "";
							if("error_unregistered_receiver".equals(result)) {
								errorMsg = "error_unregistered_receiver";
							} else {
								if(result != null && result.length() > 20) {
									errorMsg = result.substring(0, 20) + "...";
								} else {
									errorMsg = result;
								}
							}
							String sendMsg = "[올레 기가 홈캠] KPNS 장애발생\n" + errorMsg;
							
							KthMsgApi kthMsgApi = new KthMsgApi();
							
							for(String adminTelNo : env.splitProperty("service.admin.telNoList")) {
								kthMsgApi.setSend_name("HIT");			// 발신자 이름
								kthMsgApi.setSend_phone("100");			// 발신자 전화번호  [필수]
								kthMsgApi.setSend_time("");				// 발신 예약일시 [없으면 즉시발송]
								kthMsgApi.setDest_name("");				// 수진자 이름
								kthMsgApi.setDest_phone(adminTelNo);	// 수신자 전화번호 [필수]
								kthMsgApi.setSubject("ERROR");			// 제목
								kthMsgApi.setMsg_body(sendMsg);			// 메세지 내용
								
								kthMsgApiService.sendSms(kthMsgApi);
							}
						}
					} catch(Exception e) {
						log.error(e.getMessage(), e);
					}
					
				} catch(Exception e) {
					log.warn(e.getMessage(), e.fillInStackTrace());
				}
			}
			
			if(System.currentTimeMillis() > (startTime + (1000 * 60 * time))) {
				scheduledExecutorService.shutdown();
			}
		}
	}
}
