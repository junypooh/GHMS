package com.kt.giga.home.openapi.ghms.kafka;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.avro.ActionTask;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.avro.EventAction;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.avro.EventInformation;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.cnvy.CnvyRow;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.cnvy.ItgCnvyData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.cnvy.SpotDevCnvyData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.BinData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.BinSetupData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.CmdData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.ContlData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.GenlSetupData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.SclgData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.SclgSetupData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.SclgTimeData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevRetvReslt;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.KthMsgApi;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.PushInfoRequest;

public class KafkaApiConsumerForEventAction extends Thread{
	private Log logger = LogFactory.getLog(this.getClass());
	
    private KafkaStream<byte[], byte[]> kafkaStream;
    private Integer threadNumber;
	
    private KafkaApiConsumerPool pool;
    
    Gson gson = new Gson();
    
	public KafkaApiConsumerForEventAction(KafkaStream<byte[], byte[]> kafkaStream, Integer threadNumber, KafkaApiConsumerPool pool){
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

					SpecificDatumReader<EventAction> specificDatumReader = new SpecificDatumReader<EventAction>(EventAction.class);
					BinaryDecoder binaryDecoder = DecoderFactory.get().binaryDecoder(message, null);
					EventAction eventAction = specificDatumReader.read(null, binaryDecoder);

					logger.info("********************************************************");
					logger.info("event action data ::" + eventAction.toString());
					logger.info("********************************************************");
					
					
					EventInformation eventInfo = eventAction.getEventInformation();
					
					String eventId = eventInfo.getEventId();
					Long svcTgtSeq = eventInfo.getSvcTgtSeq();
					Long spotDevSeq = eventInfo.getSpotDevSeq();
					
					List<ActionTask> actionTaskList = eventAction.getActionTaskList();
					
					for (ActionTask actionTask : actionTaskList) {
						String actionCode = actionTask.getActionCode();
						Map<String, Object> taskResources = actionTask.getTaskResources();
						
						taskResources.put("eventId", String.valueOf(eventId));
						taskResources.put("svcTgtSeq", String.valueOf(svcTgtSeq));
						taskResources.put("deviceSeq", String.valueOf(spotDevSeq));
						taskResources.put("spotDevSeq", "1");
						
						
						/** 장치명 세팅 */
						String devNm = pool.kafkaSpotDevService.selectSpotDevNm(svcTgtSeq, spotDevSeq);
						if(StringUtils.isEmpty(devNm)){
							devNm = "";
						}
						if("001HIT003D0002".equals(eventId)){ 	/** 도어락 출입이벤트 */
							if(taskResources.containsKey("50997105")){
								/** 3mcore에서 센싱코드 값을 핵사스트링으로 넘어옴. */
								String hexStr = (String) taskResources.get("50997105");
								byte[] snsnVal = DatatypeConverter.parseHexBinary(hexStr);
								if(snsnVal[4] == (byte)0x06){
									if(snsnVal[5] == (byte)0x02){ /** 안에서 열림 */
										taskResources.put("msg", "도어락 \""+devNm+"\"을/를 안에서 열었습니다.");
									}else if(snsnVal[5] == (byte)0x06 && snsnVal[7] == (byte)0x63 && snsnVal[8] == (byte)0x03){	/** 밖에서 열림 */	
										if(snsnVal[9] == (byte)0x00){	/** 마스터로 열림 */
											taskResources.put("msg", "도어락 \""+devNm+"\"을/를 마스터번호로 열었습니다.");
										}else{
											String userNm = pool.KafkaMbrPwdService.selectMbrPwdTxnNm(svcTgtSeq, spotDevSeq, Long.valueOf(snsnVal[9]));
											if(StringUtils.isEmpty(userNm)){
												userNm = "";
											}
											
											if("RFID".equals(userNm)){
												taskResources.put("msg", "도어락 \""+devNm+"\"을/를 RF카드로 열었습니다.");
											}else{
												taskResources.put("msg", "도어락 \""+userNm+"\"이/가 \""+devNm+"\"을/를 열었습니다.");
											}
										}
									}
								}
							}
						}else{
							Object msg = taskResources.get("msg");
							if(msg != null){
								String msg2 = (String) msg;
								if(msg2.contains("${devNm}")){	/** 장치명 replace */
									taskResources.put("msg", msg2.replaceAll("\\$\\{devNm\\}", devNm));
								}
							}
						}						
						
						logger.info("********************************************************");
						logger.info("taskResources : " + taskResources);
						logger.info("********************************************************");	
						
						try{
							switch(actionCode){
							case "01" :	/** SMS */
								KthMsgApi kthMsgApi = new KthMsgApi();
								kthMsgApi.setSend_phone("100");
								kthMsgApi.setDest_phone(actionTask.getActionTarget());
								kthMsgApi.setMsg_body((String)taskResources.get("msg"));
								pool.kthMsgApiService.sendSms(kthMsgApi);
								break;
							case "02" : /** email */
								break;
							case "03" : /** PNS */
								PushInfoRequest pushReq = new PushInfoRequest();
								pushReq.setRegistrationId(actionTask.getActionTarget());
								pushReq.setData(taskResources);
								pool.kpnsService.push(pushReq, true);
								break;
							case "04" :
								/** homecam 제어 */
								String snsnVal = (String)taskResources.get("tagStrmVal");
								if(snsnVal != null){
									processToHomeCam(snsnVal);
								}
								
								break;
							}
						}catch(Exception e){
							logger.info("********************************************************");
							logger.info("error sending msg : "+e.getMessage());
							logger.info("********************************************************");
						}

					}

					
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
				}
			}
			logger.info("Shutting down Thread: " + this.threadNumber);
	}

	public void processToHomeCam(String snsnVal){
		
		
		HttpClient client = new HttpClient();		
		
		/**homecam 인증 */
		PostMethod post = new PostMethod("https://api.hiot.olleh.com:8543/hcam/user/authentication");
		GetMethod get = new GetMethod("https://api.hiot.olleh.com:8543/hcam/devices/spot");
		
		/** 동작제어 */
		PutMethod control = new PutMethod("https://api.hiot.olleh.com:8543/hcam/devices/control/rtime");

		/** 설정제어 */
		PutMethod conf1 = new PutMethod("https://api.hiot.olleh.com:8543/hcam/devices/control/config");
		PutMethod conf2 = new PutMethod("https://api.hiot.olleh.com:8543/hcam/devices/control/config");
		
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("userId", "deathswat");
		input.put("passwd", "ohmntest1@");
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(input);
		StringRequestEntity requestEntity;
		String strItgCnvyData;
		try {
			requestEntity = new StringRequestEntity(jsonString, "application/json", "UTF-8");
			post.setRequestEntity(requestEntity);
			post.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			post.setRequestHeader("authToken", "87fbe11bc11525636837daf2cf9c93cb868d6b6350f0fa50801111ae123917600d631fe5af6705ac7ed5796d170e4cf55ea589378ef5546451f0437c31c0565fe938217587b321e804fd82a011aed0fa2d1443d856ecad1137844267651a56a4f727c5f7269c4c0dd1a6e4686748f1ddd6956daf935193860389bd7e036704cd2ca82571bf17f68646d102a59c167473532381fa99436544c615d8bd59d374e097cf6a");
			
			client.executeMethod(post);
			
			String resAuth = post.getResponseBodyAsString();
			logger.info("auth : "+resAuth);

			Map<String, String> auth = gson.fromJson(resAuth, new TypeToken<Map<String, String>>(){}.getType());
			String authToken = auth.get("newAuthToken");
			
			get.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			get.setRequestHeader("authToken", authToken);

			client.executeMethod(get);
			String strDevice = get.getResponseBodyAsString();
			SpotDevRetvReslt device = gson.fromJson(strDevice, SpotDevRetvReslt.class);
			logger.info("device : "+device);

			String devUUID = device.getSpotDevs().get(0).getDevUUID();
			
			conf1.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			conf1.setRequestHeader("authToken", authToken);

			
			if("00".equals(snsnVal)){	/** 00 : 감시해제  */
				/** 일반설정, 침입 감지 활성화 여부. 0:비활성화, 1:활성화 */
				GenlSetupData genlSetupData = new GenlSetupData();
				genlSetupData.setSnsnTagCd("80000003");
				genlSetupData.setSetupVal("0");
				
				strItgCnvyData = getItgCnvyData(devUUID, genlSetupData);
				logger.info("strItgCnvyData : "+ strItgCnvyData);
				requestEntity = new StringRequestEntity(strItgCnvyData, "application/json", "UTF-8");
				conf1.setRequestEntity(requestEntity);
				client.executeMethod(conf1);
				
			}else if("01".equals(snsnVal)){ /** 01: 감시  */
				/** 일반설정, 침입 감지 활성화 여부. 0:비활성화, 1:활성화 */
				GenlSetupData genlSetupData = new GenlSetupData();
				genlSetupData.setSnsnTagCd("80000003");
				genlSetupData.setSetupVal("1");
				
				
				strItgCnvyData = getItgCnvyData(devUUID, genlSetupData);
				logger.info("strItgCnvyData : "+ strItgCnvyData);
				requestEntity = new StringRequestEntity(strItgCnvyData, "application/json", "UTF-8");
				conf1.setRequestEntity(requestEntity);
				client.executeMethod(conf1);
			}else if("02".equals(snsnVal)){ /** 02:패닝후 감시 */
				/** 일반설정, 침입 감지 활성화 여부. 0:비활성화, 1:활성화 */
				GenlSetupData genlSetupData = new GenlSetupData();
				genlSetupData.setSnsnTagCd("80000003");
				genlSetupData.setSetupVal("1");
				
				
				strItgCnvyData = getItgCnvyData(devUUID, genlSetupData);
				System.out.println("strItgCnvyData : "+ strItgCnvyData);
				requestEntity = new StringRequestEntity(strItgCnvyData, "application/json", "UTF-8");
				conf1.setRequestEntity(requestEntity);
				client.executeMethod(conf1);
				
				/** 일반설정, 예약 녹화 모드. 1:고정, 2:회전 */
				genlSetupData.setSnsnTagCd("80000014");
				genlSetupData.setSetupVal("2");
				
				strItgCnvyData = getItgCnvyData(devUUID, genlSetupData);
				requestEntity = new StringRequestEntity(strItgCnvyData, "application/json", "UTF-8");
				conf1.setRequestEntity(requestEntity);
				client.executeMethod(conf1);

				/** 일반설정,예약 녹화 스케줄 활성화 여부. 0:비활성화, 1:활성화 */
				genlSetupData.setSnsnTagCd("80000012");
				genlSetupData.setSetupVal("1");
				
				strItgCnvyData = getItgCnvyData(devUUID, genlSetupData);
				requestEntity = new StringRequestEntity(strItgCnvyData, "application/json", "UTF-8");
				conf1.setRequestEntity(requestEntity);
				client.executeMethod(conf1);

				
				/** 감시스제줄 설정 */
				SclgSetupData schedule = new SclgSetupData();
				List<SclgData> sclgDatas = new ArrayList<>();
				schedule.setSclgDatas(sclgDatas);
				schedule.setSnsnTagCd("81000002");
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.MINUTE, 1);
				Date startDt = cal.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
				
				cal.add(Calendar.HOUR, 1);
				Date finishDt = cal.getTime();

				/** dowCd는 요일임. 0=일요일 */
				for(int dowCd=0; dowCd<7; dowCd++){
					List<SclgTimeData> sclgTimeDatas = new ArrayList<>();
					SclgData sclgData = new SclgData();
					sclgDatas.add(sclgData);
					sclgData.setDowCd(String.valueOf(dowCd));
					sclgData.setSclgTimeDatas(sclgTimeDatas);
		
					SclgTimeData sclgTimeData = new SclgTimeData();
					sclgTimeDatas.add(sclgTimeData);
					sclgTimeData.setStTime(sdf.format(startDt));/** 시작시간(HHMM) 초기값 0000 */
					sclgTimeData.setFnsTime(sdf.format(finishDt));/** 종료시간(HHMM) 초기값 0000 */
				}

				strItgCnvyData = getItgCnvyData(devUUID, schedule);
				logger.info("strItgCnvyData : "+ strItgCnvyData);
				requestEntity = new StringRequestEntity(strItgCnvyData, "application/json", "UTF-8");
				conf2.setRequestEntity(requestEntity);
				conf2.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
				conf2.setRequestHeader("authToken", authToken);

				client.executeMethod(conf2);				
			}
			

			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch(IOException e2){
			e2.printStackTrace();
		} finally{
			post.releaseConnection();
			get.releaseConnection();
			control.releaseConnection();
			conf1.releaseConnection();
			conf2.releaseConnection();
		}
	}
	
	public String getItgCnvyData(String deviceUUID, Object... array) {
		Gson gson = new Gson();
		ItgCnvyData itgCnvyData = new ItgCnvyData();
		List<SpotDevCnvyData> spotDevCnvyDataList = new ArrayList<SpotDevCnvyData>();
		SpotDevCnvyData spotDevCnvyData = new SpotDevCnvyData();
		
		//EC server name
		itgCnvyData.setUnitSvcCd("001HIT001");
		List<CnvyRow> cnvyRowList = new ArrayList<CnvyRow>();
		CnvyRow cnvyRow = new CnvyRow();
		List<GenlSetupData> genlSetupDataList = new ArrayList<GenlSetupData>();
		List<ContlData> contlDataList = new ArrayList<ContlData>();
		List<BinData> binDataList = new ArrayList<BinData>();
		List<CmdData> cmdDataList = new ArrayList<CmdData>();
		List<BinSetupData> binSetupDataList = new ArrayList<BinSetupData>();
		List<SclgSetupData> sclgSetupDataList = new ArrayList<SclgSetupData>();
		
		itgCnvyData.setSpotDevCnvyDatas(spotDevCnvyDataList);
		
		spotDevCnvyDataList.add(spotDevCnvyData);
		spotDevCnvyData.setCnvyRows(cnvyRowList);
		cnvyRowList.add(cnvyRow);
		
		cnvyRow.setGenlSetupDatas(genlSetupDataList);
		cnvyRow.setContlDatas(contlDataList);
		cnvyRow.setBinDatas(binDataList);
		cnvyRow.setCmdDatas(cmdDataList);
		cnvyRow.setBinSetupDatas(binSetupDataList);
		cnvyRow.setSclgSetupDatas(sclgSetupDataList);
		
		//고정
		spotDevCnvyData.setDevUUID(deviceUUID);
		
		
		for(Object o : array){
			if(o instanceof GenlSetupData){
				genlSetupDataList.add((GenlSetupData) o);
			}
			
			if(o instanceof ContlData){
				contlDataList.add((ContlData) o);
			}
			
			if(o instanceof BinData){
				binDataList.add((BinData) o);
			}
			
			if(o instanceof CmdData){
				cmdDataList.add((CmdData) o);
			}

			if(o instanceof BinSetupData){
				binSetupDataList.add((BinSetupData) o);
			}
			
			if(o instanceof SclgSetupData){
				sclgSetupDataList.add((SclgSetupData)o);
			}

		}

		
		
		return gson.toJson(itgCnvyData, ItgCnvyData.class);
		
	}	
}
