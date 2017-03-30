package com.kt.giga.home.openapi.kpns.kafka;

import com.google.gson.GsonBuilder;
import com.kt.giga.home.openapi.common.CipherUtils;
import com.kt.giga.home.openapi.health.paring.domain.KthMsgApi;
import com.kt.giga.home.openapi.health.paring.service.KthMsgApiService;
import com.kt.giga.home.openapi.kpns.domain.*;
import com.kt.giga.home.openapi.kpns.service.KPNSService;
import com.kt.giga.home.openapi.util.JsonUtils;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

public class EventConsumer implements Runnable {
	
    // 이벤트 코드 : 침입감지 이벤트
 	private static final String EVENT_CODE_DETECT = "001HIT001D0001";
 	// 이벤트 코드 : UCLOUD 저장 공간 부족 이벤트
 	private static final String EVENT_CODE_UCLOUD_STORE = "001HIT001D0002";
 	// 이벤트 코드 : UCLOUD 업로드 오류 이벤트
 	private static final String EVENT_CODE_UCLOUD_ERROR = "001HIT001D0003";
 	// 이벤트 코드 : SD카드 장애 이벤트
 	private static final String EVENT_CODE_SDCARD = "001HIT001D0004";
 	// 이벤트 코드 : 로그인 알림 이벤트
 	private static final String EVENT_CODE_LOGIN = "001HIT001E0005";
 	// 이벤트 코드 : 끊김 10분 경과 이벤트
   	private static final String EVENT_CODE_CONN_ERROR = "001HIT001E0008";
 	// 이벤트 코드 : SD카드 저장 공간 부족 이벤트
    private static final String EVENT_CODE_SDCARD_STORE = "001HIT001D0009";
    // 세부 이벤트 구분 키값
   	private static final String EVENT_SUB_TYPE = "10001097";
   	private static final double EVENT_SUB_TYPE_DETECT_MOVE = 21.0;
   	private static final double EVENT_SUB_TYPE_DETECT_SOUND = 22.0;
   	private static final double EVENT_SUB_TYPE_SDCARD = 7.0;
   	private static final double EVENT_SUB_TYPE_SDCARD_NONE = 9.0;
   	private static final double EVENT_SUB_TYPE_SDCARD_NS = 10.0;
   	private static final double EVENT_SUB_TYPE_SDCARD_STORE = 8.0;
   	private static final double EVENT_SUB_TYPE_SDCARD_STORE_NONE = 11.0;
 	// PUSH
 	private static final String ACTION_CODE_PUSH = "03";
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private KafkaStream stream;
    private int threadNumber;
    private KPNSService service;
    private KthMsgApiService kthMsgApiService;
    private String reportUrl;
    private String[] telNoList;
 
    public EventConsumer(KafkaStream stream, int threadNumber, KPNSService service, 
    		KthMsgApiService kthMsgApiService, String reportUrl, String[] telNoList) {
    	this.stream = stream;
        this.threadNumber = threadNumber;
        this.service = service;
        this.kthMsgApiService = kthMsgApiService;
        this.reportUrl = reportUrl;
        this.telNoList = telNoList;
    }
 
    public void run() {
    	SpecificDatumReader<EventAction> specificDatumReader = new SpecificDatumReader<EventAction>(EventAction.class);
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while (it.hasNext()) {
        	MessageAndMetadata<byte[], byte[]> data = it.next();
			byte[] header = data.key();
			byte[] message = data.message();
			BinaryDecoder binaryDecoder = DecoderFactory.get().binaryDecoder(message, null);
			
			try {
				EventAction read = specificDatumReader.read(null, binaryDecoder);
				EventInformation info = read.getEventInformation();
				
				log.info("# Recv Event from msg : \n{}", JsonUtils.toPrettyJson(info));
				
				log.debug("EventInformation : " + info);
				log.debug("ActionTask size : " + read.getActionTaskList().size());
				
				for(int i = 0; i < read.getActionTaskList().size(); i++) {
					try {
						ActionTask task = read.getActionTaskList().get(i);
						
						log.debug("ActionTask : " + task);
						
						if(ACTION_CODE_PUSH.equals(task.getActionCode())) {
							Map<String, Object> taskResources = task.getTaskResources();
							taskResources.put("eventId", info.getEventId());
							
							log.info("TaskResources : " + taskResources);
							
							// 1. 푸시 메시지 시퀀스 조회
							// long msg_seq = service.selectMsgTrmForwardTxnSeq();
							long msg_seq = System.nanoTime();
							
							// 2. 푸시 발송, 푸시 이력 데이터 추출
							long mbrSeq = 0;
							String telNo = "";
							String eventSubTypeStr = "";
							double eventSubType = 0.0;
							
							mbrSeq = NumberUtils.toLong(taskResources.get("mbrSeq") == null ? "" : taskResources.get("mbrSeq").toString());
							telNo = taskResources.get("telNo") == null ? "" : taskResources.get("telNo").toString();
							eventSubTypeStr = taskResources.get(EVENT_SUB_TYPE) == null ? "" : taskResources.get(EVENT_SUB_TYPE).toString();
							eventSubType = NumberUtils.toDouble(eventSubTypeStr);
							
							// 푸시 발송 시 payload 데이터 정리
							if(EVENT_CODE_LOGIN.equals(info.getEventId())) {
								String loginTelNo = taskResources.get("loginTelNo") == null ? "" : taskResources.get("loginTelNo").toString();
								String maskedLoginTelNo = CipherUtils.getMaskingTelNo(loginTelNo);
								
								taskResources.remove("mbrSeq");
								taskResources.remove("loginTelNo");
								taskResources.put("telNo", maskedLoginTelNo);
								taskResources.put("seq", Long.toString(msg_seq));
								
							} else if(EVENT_CODE_DETECT.equals(info.getEventId())) {
								if(EVENT_SUB_TYPE_DETECT_MOVE == eventSubType) {
									taskResources.put("msg", "${devNm} 움직임 감지(format($detectTime, 'yyyy/MM/DD HH:mm'))");
								} else if(EVENT_SUB_TYPE_DETECT_SOUND == eventSubType) {
									taskResources.put("msg", "${devNm} 소리 감지(format($detectTime, 'yyyy/MM/DD HH:mm'))");
								}
								
								taskResources.remove("mbrSeq");
								taskResources.remove("telNo");
								taskResources.put("seq", Long.toString(msg_seq));
								
							} else if(EVENT_CODE_SDCARD.equals(info.getEventId())) {
								if(EVENT_SUB_TYPE_SDCARD == eventSubType) {
									taskResources.put("msg", "SD카드 저장에 실패하였습니다.");
								} else if(EVENT_SUB_TYPE_SDCARD_NONE == eventSubType) {
									taskResources.put("msg", "SD카드가 제거 되었습니다.");
								} else if(EVENT_SUB_TYPE_SDCARD_NS == eventSubType) {
									taskResources.put("msg", "SD카드가 인식되지 않았습니다. SD카드를 확인해주세요.");
								}
								
								taskResources.remove("mbrSeq");
								taskResources.remove("telNo");
								taskResources.put("seq", Long.toString(msg_seq));
								
							} else if(EVENT_CODE_SDCARD_STORE.equals(info.getEventId())) {
								if(EVENT_SUB_TYPE_SDCARD_STORE == eventSubType) {
									taskResources.put("msg", "SD카드 용량이 부족합니다. 용량 정리 후 사용해주세요.");
								} else if(EVENT_SUB_TYPE_SDCARD_STORE_NONE == eventSubType) {
									taskResources.put("msg", "SD카드 용량이 부족하여 저장에 실패하였습니다.");
								}
								
								taskResources.remove("mbrSeq");
								taskResources.remove("telNo");
								taskResources.put("seq", Long.toString(msg_seq));
								
							} else {
								taskResources.remove("mbrSeq");
								taskResources.remove("telNo");
								taskResources.put("seq", Long.toString(msg_seq));
							}
							
							
							// 3. 푸시 발송
							PushInfoRequest req = new PushInfoRequest();
							Map<String, Object> res;
							
							req.setRegistrationId(task.getActionTarget());
							req.setData(taskResources);
							req.setReportUrl(reportUrl);
							res = service.push(req);
							
							// 4. 침임감지 이력 업데이트
							if(EVENT_CODE_DETECT.equals(info.getEventId())) {
								service.updateDetectTime(req);
							}
							
							// 5. 푸시 이력 등록
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
							
							if(StringUtils.isEmpty(msg_id)) {
								msg_id = StringUtils.replaceChars(UUID.randomUUID().toString(), "-", "");
							}
							
							Event event = new Event();
							event.setMbr_seq(mbrSeq);
							event.setSvc_tgt_seq(info.getSvcTgtSeq());
							event.setSpot_dev_seq(info.getSpotDevSeq());
							event.setPns_reg_id(task.getActionTarget());
							event.setTel_no(telNo);
							event.setDstr_cd("001");
							event.setSvc_theme_cd("HIT");
							event.setUnit_svc_cd("001");
							event.setSetup_cd(info.getEventId().substring(10, 14));
							event.setMsg_seq(msg_seq);
							event.setMsg_id(msg_id);
							event.setMsg_trm_sbst(new GsonBuilder().create().toJson(taskResources));
							event.setMsg_trm_fail_txn(result);
							event.setMsg_trm_forward_yn(forwardYn);
							
							service.insertMsgTrmForwardTxn(event);
							
							// PNS 발송 요청 오류 시 운영자에게 SMS 전송
							try {
								if("N".equals(forwardYn)) {
									String errorMsg = "";
                                    if (!"error_unregistered_receiver".equals(result)) {
                                        if (result.length() > 40) {
                                            errorMsg = result.substring(0, 40) + "...";
                                        } else {
											errorMsg = result;
										}

                                        String sendMsg = "[홈캠] PNS장애\n" + errorMsg;

                                        KthMsgApi kthMsgApi = new KthMsgApi();

                                        for (String adminTelNo : telNoList) {
                                            kthMsgApi.setSend_name("HIT");            // 발신자 이름
                                            kthMsgApi.setSend_phone("100");            // 발신자 전화번호  [필수]
                                            kthMsgApi.setSend_time("");                // 발신 예약일시 [없으면 즉시발송]
                                            kthMsgApi.setDest_name("");                // 수진자 이름
                                            kthMsgApi.setDest_phone(adminTelNo);    // 수신자 전화번호 [필수]
                                            kthMsgApi.setSubject("ERROR");            // 제목
                                            kthMsgApi.setMsg_body(sendMsg);            // 메세지 내용

                                            kthMsgApiService.sendSms(kthMsgApi);
                                        }
                                    }
                                }
							} catch(Exception e) {
								log.error(e.getMessage(), e);
							}
						}
					} catch(Exception e) {
						log.error(e.getMessage(), e);
					}
				
					Thread.currentThread().sleep(50);
				}
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
        }
    }
}
