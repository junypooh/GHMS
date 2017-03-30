package com.kt.giga.home.openapi.ghms.kafka;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kt.giga.home.openapi.ghms.common.DeviceType;
import com.kt.giga.home.openapi.ghms.common.PushType;
import com.kt.giga.home.openapi.ghms.devices.domain.key.SpotDevBasKey;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaDevSetup;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaSnsnTag;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.InfoUpdType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.UseYn;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.BinSetupData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDev;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevUdate;
import com.kt.giga.home.openapi.ghms.kafka.type.KafkaEncdngType;
import com.kt.giga.home.openapi.ghms.kafka.type.KafkaMsgType;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.PushInfoRequest;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;
import com.kt.giga.home.openapi.ghms.util.string.StringUtils;

public class KafkaApiConsumerFor332 extends Thread{
	private Log logger = LogFactory.getLog(this.getClass());
	
    private KafkaStream<byte[], byte[]> kafkaStream;
    private Integer threadNumber;
	
    private KafkaApiConsumerPool pool;
    
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
    
	public KafkaApiConsumerFor332(KafkaStream<byte[], byte[]> kafkaStream, Integer threadNumber, KafkaApiConsumerPool pool){
        this.threadNumber = threadNumber;
        this.kafkaStream = kafkaStream;
        this.pool = pool;
	}

	
	private void sendPush(String eventId, Long svcTgtSeq, Long gwSeq, Long devSeq, String snsnTag, String snsnVal){
        Map<String, String> data = new HashMap<String, String>();
        data.put("eventId", eventId);
        data.put("svcTgtSeq", String.valueOf(svcTgtSeq));
        data.put("spotDevSeq", String.valueOf(gwSeq));
        data.put("deviceSeq", String.valueOf(devSeq));
        
        if(snsnTag != null){        	
            data.put("snsnTag", snsnTag);
        }
        if(snsnVal != null){        	
            data.put("snsnVal", snsnVal);
        }
		
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
	
	@Override
	public void run() {
		
			ConsumerIterator<byte[], byte[]> consumerIte = kafkaStream.iterator();
			while (consumerIte.hasNext()) {
				byte[] message = consumerIte.next().message();
				ByteArrayInputStream in = new ByteArrayInputStream(message);
				try {
					/** header 인코딩타입(1byte)+reserved(1byte)+메세지유형(2byte)+바디길이(4byte) */
					byte[] encBuffer  = new byte[1];
					byte[] resvdBuffer  = new byte[1];
					byte[] msgTypeBuffer  = new byte[2];
					byte[] bodySizeBuffer  = new byte[4];

			        in.read(encBuffer, 0, 1);
			        in.read(resvdBuffer, 0, 1);
			        in.read(msgTypeBuffer, 0, 2);
			        in.read(bodySizeBuffer, 0, 4);
			        
			        short type = (short) (((msgTypeBuffer[0] << 8)) | ((msgTypeBuffer[1] & 0xff)));
			        int bodySize = ((bodySizeBuffer[0]&0xff)<<24) | ((bodySizeBuffer[1]&0xff)<<16) | ((bodySizeBuffer[2]&0xff)<< 8) | (bodySizeBuffer[3]&0xff);
					
					byte[] body = new byte[message.length-8];
					in.read(body);
					
					
					switch(KafkaEncdngType.fromByte(encBuffer[0])){
					case JSON: 
				        String json = new String(body, "UTF-8");
						logger.info("body ::" + json);
						switch(KafkaMsgType.fromShort(type)){
						case INITA_DEV_UDATERPRT_EXTR: processSpotDevUdate(json); break;
						default :
						}
						break;
					default : 
						break;
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					IOUtils.closeQuietly(in);
				}
			}
			logger.info("Shutting down Thread: " + this.threadNumber);
	}
	
	/**
	 * @param json
	 * @see 3321(SpotDevUdate, 외부장치 업데이트)에 대한 처리로직 
	 */
	private void processSpotDevUdate(String json) throws Exception{
		SpotDevUdate data = gson.fromJson(json, SpotDevUdate.class);
		InfoUpdType infoUpdType = data.getInfoUpdTypeCd();
		
		for (SpotDev spotDev : data.getSpotDevs()) {
			//장치 모델코드
			String devModelCd = spotDev.getModelNm();
			//장치 유형코드
			StringBuffer devTypeCd = new StringBuffer();
			HashMap<String, String> dtlMap = spotDev.getSpotDevDtlMap();
			devTypeCd.append(dtlMap.get("GEN_DEV_CLASS"));
			devTypeCd.append(dtlMap.get("SPCF_DEV_CLASS"));
			
			
			SpotDevBasKey key = new SpotDevBasKey();
			key.setDevModelCd(devModelCd);
			key.setDevTypeCd(devTypeCd.toString());
			key.setSvcTgtSeq(spotDev.getUpSvcTgtSeq());
			key.setSpotDevSeq(spotDev.getSpotDevSeq());
			key.setUpSvcTgtSeq(spotDev.getUpSvcTgtSeq());
			key.setSpotDevId(spotDev.getSpotDevId());
			key.setUpSpotDevId(spotDev.getUpSpotDevId());
			key.setUpGwCnctId(spotDev.getUpGwCnctId());
			key.setAthnNo(spotDev.getAthnNo());
			key.setDevNm(spotDev.getSpotDevNm());
			key.setColecCyclTime(0);
			key.setFrmwrVerNo(spotDev.getFrmwrVerNo());
			key.setCretrId("KAFKA");
			key.setAmdrId("KAFKA");
			key.setSpotDevDtls(spotDev.getSpotDevDtls());
			
			logger.info("*****************Recieved 332 from EC**********************");
			logger.info("SpotDevUdate : "+json);
			logger.info("infoUpdType : "+infoUpdType);
			logger.info("***********************************************************");
			
			Long svcTgtSeq = spotDev.getSvcTgtSeq();
			Long upSvcTgtSeq = spotDev.getUpSvcTgtSeq();
			
			UseYn useYn = spotDev.getConnYn();
			
			
			if(infoUpdType == null){/** ec 채널인증 */
				if(UseYn.YES.equals(useYn)){	/** connect on */
					key.setSvcTgtSeq(spotDev.getSvcTgtSeq());
					key.setTmpDevYn("N");
					pool.initDeviceAddService.updateGateWayTmpDev(key);
				}
			}else{ /** inclusion or exclusion */
				switch (infoUpdType) {
				case INSERT_UPDATE:	/** inclusion */ 
					
					if(!DeviceType.GATE_WAY.getCode().equals(devTypeCd.toString())){	/** hub가 아닌경우만 호출되도록 변경 */
						if("1".equals(dtlMap.get("ABNORMAL_INCLUSION_CAUSE"))){ /** Security 제품 비정상 등록 PUSH 및 필터링. */
							
							logger.info("***********************************************************");
							logger.info("ABNORMAL_INCLUSION_CAUSE : 1");
							logger.info("***********************************************************");
					        Map<String, String> push = new HashMap<String, String>();
					        push.put("eventId", PushType.SECURITY_PRODUCT_INCLUSION_FAULT.getEventId());
					        push.put("svcTgtSeq", String.valueOf(upSvcTgtSeq));
					        push.put("spotDevSeq", String.valueOf("1"));
					        push.put("deviceSeq", String.valueOf("1"));
							push.put("msg", "IoT 단말이 정상적으로 등록되지 않았습니다. 단말을 제거하고 다시 등록해 주십시오.");
					        
							PushInfoRequest pushReq = new PushInfoRequest();
							pushReq.setRegistrationId(pool.kpnsService.selectMasterPnsRegId(upSvcTgtSeq));
							pushReq.setData(push);
							pool.kpnsService.push(pushReq);
						}else{
							pool.initDeviceAddService.insertInitDataByNewDeviceAdd(key);
						}
					}
					break;
				case DELETE:	/** exclusion and 하위단말 공장초기화 */ 
					/** z-wave spec상 등록되지 않은 장치가 올라올 수도 있음. */
					pool.initDeviceAddService.deleteDevice(key);
					break;
				case UPDATE:	/** hub 공장초기화 */ 	
					if((upSvcTgtSeq != null && svcTgtSeq != null) 
							&& upSvcTgtSeq.equals(svcTgtSeq)){	/** hub인 경우에만... */
						key.setSvcTgtSeq(spotDev.getSvcTgtSeq());
						key.setTmpDevYn("Y");
//						transaction이 분리 되어 EC 에서 에러 시 rollback 되지 않을 수 있으므로 
//						deleteSpotDevsConnectedByGw(key) 로직에 추가 함.
//						pool.initDeviceAddService.updateGateWayTmpDev(key);
						pool.initDeviceAddService.deleteSpotDevsConnectedByGw(key);
					}
					break;
				case UPDATE_PART:	/** 도어락 비밀번호가 세팅될때 도어락 비빌번호 전체가 332를 통해 통보됨. */ 	
					
					key.setSvcTgtSeq(spotDev.getSvcTgtSeq());
					Long spotDevSeq = pool.initDeviceAddDao.selectSpotDevSeqBySpotDevId(key);
					
					for(BinSetupData binSetupData :spotDev.getBinSetupDatas()){
						String snsnCd = binSetupData.getSnsnTagCd();
						byte[] snsnVal = binSetupData.getSetupVal();
												
						if("82996303".equals(snsnCd)){	/** 도어락 비밀번호 */
							Map<String, Object> mbrPwd = new HashMap<String, Object>();
							
							/** 1byte : user sequence, 1byte : 현재상태, 나머지 : 아스키 비빌번호 */
							int snsnIdx=0;
							byte userSeq = snsnVal[snsnIdx++];
							byte userStatus = snsnVal[snsnIdx++];
							
							/** 도어락 비빌번호 시퀀스 */
							mbrPwd.put("svcTgtSeq", svcTgtSeq);
							mbrPwd.put("spotDevSeq", spotDevSeq);
							mbrPwd.put("userSeq", new Long(userSeq));
							
							if(userStatus == 0x00){	/** RFID 등록/삭제를 위해 사용, 단말에서 멀티비밀번호를 세팅할 수는 없음.  00 : 삭제(삭제), 01 : 등록(merge) */
								mbrPwd.put("userPw", "0");
								/** 삭제 프로세스 */
								pool.KafkaMbrPwdService.deleteMbrPwdTxn(mbrPwd);
							}else{
								ByteBuffer userPw = ByteBuffer.allocate(snsnVal.length-2);
								for(;snsnIdx<snsnVal.length; snsnIdx++){
									userPw.put((snsnVal[snsnIdx]));
								}
								byte[] pw = userPw.array();
								if(!StringUtils.isAscii(pw, pw.length)){	/** ascii 값이 아닌경우 RFID에 의한 패스워드 */
									mbrPwd.put("userPw", "RFID");
									mbrPwd.put("userNm", "RFID");
								}else{
									mbrPwd.put("userPw", new String(pw, "UTF-8"));
									mbrPwd.put("userNm", userSeq+"");
								}
								/** merge */
								pool.KafkaMbrPwdService.saveMbrPwdTxn(mbrPwd);
							}
							
							

							
							
							

						}
						
						
						if("82997006".equals(snsnCd)){	/** 가스밸브 초기시간 설정 변경 통보 */
							if(snsnVal[0] == (byte)0x01){	/** 기본설정값에 대한 통보 */
								
								KafkaDevSetup setup = new KafkaDevSetup();
								setup.setSvcTgtSeq(svcTgtSeq);
								setup.setSpotDevSeq(spotDevSeq);
								List<KafkaSnsnTag> tagList = new ArrayList<>();
								setup.setSnsnTagList(tagList);
								tagList.add(new KafkaSnsnTag(snsnCd, StringUtils.byteArrayToHex(snsnVal)));
								/** 센싱태그, 값 저장 */
								pool.kafkaSpotDevService.saveSpotDevGenlSetupTxn(setup);
								
								/** refresh용 푸시 */
								sendPush(PushType.GASVALVE_REFRESH.getEventId(), svcTgtSeq, 1L, spotDevSeq, null, null);
								
							}else if(snsnVal[0] == (byte)0x03){ /** 원타임 시간설정에 대한 변경 to push */
								sendPush(PushType.GASVALVE_REMAIN_TIME.getEventId(), svcTgtSeq, 1L, spotDevSeq, snsnCd, StringUtils.byteArrayToHex(snsnVal));
							}
							
							
							
						}
						
					}
					break;
				default:	
					break;
				}
			}
		}
		
		
	}
	

}
