package com.kt.giga.home.openapi.ghms.kafka;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kt.giga.home.openapi.ghms.common.PushType;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaDevSetup;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaSnsnTag;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.avro.CollectEvent;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.PushInfoRequest;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;
import com.kt.giga.home.openapi.ghms.util.string.StringUtils;

public class KafkaApiConsumerForCollectEvent extends Thread{
	private Log logger = LogFactory.getLog(this.getClass());
	
    private KafkaStream<byte[], byte[]> kafkaStream;
    private Integer threadNumber;
	
    private KafkaApiConsumerPool pool;
    
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
    
	public KafkaApiConsumerForCollectEvent(KafkaStream<byte[], byte[]> kafkaStream, Integer threadNumber, KafkaApiConsumerPool pool){
        this.threadNumber = threadNumber;
        this.kafkaStream = kafkaStream;
        this.pool = pool;
	}

	@Override
	public void run() {
		
			ConsumerIterator<byte[], byte[]> consumerIte = kafkaStream.iterator();
			while (consumerIte.hasNext()) {
				byte[] message = consumerIte.next().message();
				try {

					SpecificDatumReader<CollectEvent> specificDatumReader = new SpecificDatumReader<CollectEvent>(CollectEvent.class);
					BinaryDecoder binaryDecoder = DecoderFactory.get().binaryDecoder(message, null);
					CollectEvent collectEvent = specificDatumReader.read(null, binaryDecoder);

					logger.info("********************************************************************************************");
					logger.info("collect event data ::" + collectEvent.toString());
					logger.info("********************************************************************************************");
					
					long svcTgtSeq = collectEvent.getSvcTgtSeq();
					long spotDevSeq = collectEvent.getSpotDevSeq();
					
					KafkaDevSetup devSetup = new KafkaDevSetup();
					devSetup.setSvcTgtSeq(svcTgtSeq);
					devSetup.setSpotDevSeq(spotDevSeq);
					devSetup.setSnsnTagList(attributesToKafkaSnsnTagList(devSetup, collectEvent.getAttributes()));
					
					/** save sensing code/value */
					pool.kafkaSpotDevService.saveSpotDevGenlSetupTxn(devSetup);
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
				}
			}
			logger.info("Shutting down Thread: " + this.threadNumber);
	}

	private List<KafkaSnsnTag> attributesToKafkaSnsnTagList(KafkaDevSetup devSetup, final Map<String, Object> attr){
		List<KafkaSnsnTag> output = new ArrayList<KafkaSnsnTag>();
		for(String snsnCd : attr.keySet()){	/** 411 = 5(byte)0xxxxxxx */
			if(snsnCd.startsWith("50")){
								
				ByteBuffer buffer =  (ByteBuffer)attr.get(snsnCd);
				byte[] bytes = buffer.array();
				
				logger.info("********************************************************");
				logger.info("411, "+snsnCd+" :: " + StringUtils.byteArrayToHex(bytes));
				logger.info("********************************************************");
				
				if("50997105".equals(snsnCd)){	/** 도어락, 침입감지 통보관련 */
					if(bytes[4] == (byte)0x06 /** access control */
//							|| (bytes[4] == (byte)0x07 && bytes[5] == (byte)0x02)	/** 도어락 파손시도 */
//							|| (bytes[4] == (byte)0x04 && bytes[5] == (byte)0x02)	/** 가스밸브 과열 */
//							|| (bytes[4] == (byte)0x07 && bytes[5] == (byte)0x09)	/** 도어락 장시간 문열림 */
//							|| (bytes[4] == (byte)0x07 && bytes[5] == (byte)0x04)	/** 도어락 비밀번호 5회 오류 */
//							|| (bytes[4] == (byte)0x0A && bytes[5] == (byte)0x02)	/** 도어락 화재 */
							){
						if(bytes[5] == (byte)0x00|| bytes[5] == (byte)0x16 || bytes[5] == (byte)0x02 || bytes[5] == (byte)0x04 || bytes[5] == (byte)0x06){	/** open */							
							snsnCd = "50996203";
							buffer.clear();
							buffer = ByteBuffer.allocate(5).put((byte)0x00).put((byte)0x00).put((byte)0x00).put((byte)0x00).put((byte)0x00);
							if(bytes[5] != (byte)0x16 && bytes[5] != (byte)0x04){	/**	침입감지 붙음 아닐경우 푸시전송 */
								sendPushToSlave(devSetup.getSvcTgtSeq(), Long.valueOf(1), devSetup.getSpotDevSeq());
							}
						}else if(bytes[5] == (byte)0x17 || bytes[5] == (byte)0x01 || bytes[5] == (byte)0x03 || bytes[5] == (byte)0x05 || bytes[5] == (byte)0x09){	/** close */
							snsnCd = "50996203";
							buffer.clear();
							buffer = ByteBuffer.allocate(5).put((byte)0xff).put((byte)0x00).put((byte)0x00).put((byte)0x00).put((byte)0x00);
							
							/** push : 도어락 닫힘(refresh 용도)  */
							if(bytes[5] != (byte)0x17){	/**	침입감지 떨어짐 아닐경우 푸시전송 */
								sendPush(devSetup.getSvcTgtSeq(), Long.valueOf(1), devSetup.getSpotDevSeq());
							}
							
						}else{	/** 나머지 7105는 모두 버림 */
							continue;
						}
					}else{	/** 나머지 7105는 모두는 버림 */
						continue;
					}
				}else if("50992503".equals(snsnCd)){	/** 가스밸브 */
					/** push : 가스밸브 통보(refresh 용도) */
					sendPush(devSetup.getSvcTgtSeq(), Long.valueOf(1), devSetup.getSpotDevSeq());
					
				}else if("50998003".equals(snsnCd)){	/** 저전압 상태  */
					if(bytes[0] == (byte)0xff){	/** 저전압상태 알림은 따로 3MP 이벤트로 푸시로 처리됨. DB업데이트 안함. ff를 제외한 나머지 알림은 배터리 잔량이므로 업데이트 진행.  */
						continue;
					}
				}
				
				output.add(new KafkaSnsnTag(snsnCd, StringUtils.byteArrayToHex(buffer.array())));	
				
			}
			
		}	
		return output;
	}
	
	
	private void sendPush(Long svcTgtSeq, Long gwSeq, Long devSeq){
        Map<String, String> data = new HashMap<String, String>();
        data.put("eventId", PushType.GASVALVE_REFRESH.getEventId());
        data.put("svcTgtSeq", String.valueOf(svcTgtSeq));
        data.put("spotDevSeq", String.valueOf(gwSeq));
        data.put("deviceSeq", String.valueOf(devSeq));
		
		PushInfoRequest pushReq = new PushInfoRequest();
		pushReq.setData(data);
		
		try {
			for(String regId : pool.kpnsService.selectMasterAndSlavePnsRegId(svcTgtSeq)){
				pushReq.setRegistrationId(regId);
				pool.kpnsService.push(pushReq);
			}
		} catch (APIException e) {
			e.printStackTrace();
		}
	}
	
	private void sendPushToSlave(Long svcTgtSeq, Long gwSeq, Long devSeq){
        Map<String, String> data = new HashMap<String, String>();
        data.put("eventId", PushType.GASVALVE_REFRESH.getEventId());
        data.put("svcTgtSeq", String.valueOf(svcTgtSeq));
        data.put("spotDevSeq", String.valueOf(gwSeq));
        data.put("deviceSeq", String.valueOf(devSeq));
		
		PushInfoRequest pushReq = new PushInfoRequest();
		pushReq.setData(data);
				
		try {
			for(String regId : pool.kpnsService.selectSlavePnsRegId(svcTgtSeq)){
				pushReq.setRegistrationId(regId);
				pool.kpnsService.push(pushReq);
			}
		} catch (APIException e) {
			e.printStackTrace();
		}
	}
}
