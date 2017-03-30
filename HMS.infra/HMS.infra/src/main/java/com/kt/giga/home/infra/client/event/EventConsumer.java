package com.kt.giga.home.infra.client.event;

import java.util.Map;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.log4j.Logger;

import com.kt.giga.home.infra.domain.event.ActionTask;
import com.kt.giga.home.infra.domain.event.EventAction;
import com.kt.giga.home.infra.domain.event.EventInformation;
import com.kt.giga.home.infra.domain.kpns.PushInfoRequest;
import com.kt.giga.home.infra.domain.kpns.PushInfoResponse;
import com.kt.giga.home.infra.service.kpns.KPNSService;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

public class EventConsumer implements Runnable {
	
	private Logger log = Logger.getLogger(this.getClass());
	
    private KafkaStream stream;
    private int threadNumber;    
    private KPNSService service;
    
    // 이벤트 코드 : 침입감지 이벤트
 	private static final String EVENT_CODE_DETECT = "001HIT001D0001";
 	
 	// 이벤트 코드 : UCLOUD 저장 공간 부족 이벤트
 	private static final String EVENT_CODE_UCLOUD_STORE = "001HIT001D0002";
 	
 	// 이벤트 코드 : UCLOUD 업로드 오류 이벤트
 	private static final String EVENT_CODE_UCLOUD_ERROR = "001HIT001D0003";
 	
 	// 이벤트 코드 : SD카드 장애 이벤트
 	private static final String EVENT_CODE_SDCARD = "001HIT001D0004";
 	
 	// 이벤트 코드 : 로그인 알림 이벤트
 	private static final String EVENT_CODE_LOGIN = "001HIT001D0005";
 
    public EventConsumer(KafkaStream stream, int threadNumber, KPNSService service) {
    	this.stream = stream;
        this.threadNumber = threadNumber;
        this.service = service;
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
				
				log.error(read.getActionTaskList());
				
				
				for(int i = 0; i < read.getActionTaskList().size(); i++) {
					ActionTask task = read.getActionTaskList().get(i);	
					
					if("03".equals(task.getActionCode())) {
						final Map<String, Object> taskResources = task.getTaskResources();
						taskResources.put("eventId", info.getEventId());
						taskResources.remove("eventCode");
						taskResources.remove("eventType");
						
						try {							
							PushInfoRequest req = new PushInfoRequest();
							PushInfoResponse res = new PushInfoResponse();
							
							req.setRegistrationId(task.getActionTarget());
							req.setData(taskResources);
							
							res = (PushInfoResponse) service.push(req);
							
							if(res != null && res.getStatusCode() == 200 && "1".equals(res.getSuccess())) {
								log.debug("Push Success : " + req.getData());
							} else {
								log.debug("Push Failed : " + req.getData());
							}
												
						} catch(Exception e) {
							log.error(e, e.fillInStackTrace());
						}
					
						Thread.currentThread().sleep(100);
					}
				}
				
			} catch (Exception e) {
				log.error(e, e.fillInStackTrace());
			}
        }
    }
}