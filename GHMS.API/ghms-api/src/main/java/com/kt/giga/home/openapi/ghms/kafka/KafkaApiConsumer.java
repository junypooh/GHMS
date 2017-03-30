package com.kt.giga.home.openapi.ghms.kafka;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kt.giga.home.openapi.ghms.devices.domain.key.SpotDevBasKey;
import com.kt.giga.home.openapi.ghms.kafka.entity.HbaseControlHistory;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaDevSetup;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaDevSetupGroup;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaSnsnTag;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.cnvy.CnvyRow;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.cnvy.ItgCnvyData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.cnvy.SpotDevCnvyData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.colec.SpotDevColecData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.qry.LastValRetvReslt;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.BinData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.BinSetupData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.CmdData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.ContlData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.DtData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.GenlSetupData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.MsrData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.Row;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.StrData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.SttusData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDev;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevRetvReslt;
import com.kt.giga.home.openapi.ghms.kafka.type.KafkaDevTrtSttusType;
import com.kt.giga.home.openapi.ghms.kafka.type.KafkaEncdngType;
import com.kt.giga.home.openapi.ghms.kafka.type.KafkaMsgType;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;
import com.kt.giga.home.openapi.ghms.util.string.StringUtils;

public class KafkaApiConsumer extends Thread{
	private Log logger = LogFactory.getLog(this.getClass());
	
    private KafkaStream<byte[], byte[]> kafkaStream;
    private Integer threadNumber;
	
    private KafkaApiConsumerPool pool;
    
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
    
    
	public KafkaApiConsumer(KafkaStream<byte[], byte[]> kafkaStream, Integer threadNumber, KafkaApiConsumerPool pool){
        this.threadNumber = threadNumber;
        this.kafkaStream = kafkaStream;
        this.pool = pool;
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
					
			        
					logger.info("***************Recieved Message from EC*******************");
					logger.info("header ::{encodingType:"+encBuffer[0]+",reserved:"+resvdBuffer[0]+",msgType:"+type+",bodySize:"+bodySize+"}");
					
					switch(KafkaEncdngType.fromByte(encBuffer[0])){
					case JSON: 
				        String json = new String(body, "UTF-8");
						logger.info("body ::" + json);
						switch(KafkaMsgType.fromShort(type)){
						case INITA_DEV_RETV_RESLT_EXTR: processInitaDevRetvResltExtr(json); break;
						case CONTL_ITGCNVY_DATA_RESLT: processContlItgCnvyData(json); break;
						case QRY_LAST_VAL_RESLT: processQryLastValReslt(json); break;
						default :
						}
						break;
					default : 
						logger.info("******************Not support EncdngType*********************");
						break;
					}
					logger.info("********************************************************");

				} catch (Exception e) {
					logger.debug("KafkaApiConsumer Exception : "+e.getMessage());
					logger.error(e);
				} finally{
					IOUtils.closeQuietly(in);
				}
			}
			logger.info("Shutting down Thread: " + this.threadNumber);
	}
	
	/**
	 * @param json
	 * @throws APIException 
	 * @see 7122(LastValRetvReslt, 쿼리결과)에 대한 처리로직 
	 */

	private void processQryLastValReslt(String json) throws APIException{
		LastValRetvReslt data = gson.fromJson(json, LastValRetvReslt.class);
		String transacId = data.getTransacId();
		KafkaDevSetupGroup group = pool.kafkaDevSetupManager.getDevSetupGroup(transacId);
		Map<String, KafkaDevSetup> devSetup = group.getDevSetup();		

		for(SpotDevColecData dev :data.getSpotDevColecDatas()){
			KafkaDevSetup setup = new KafkaDevSetup();
			setup.setSpotDevSeq(dev.getSpotDevSeq());
			setup.setSvcTgtSeq(dev.getSvcTgtSeq());
			setup.setDevTrtSttusCd(dev.getDevTrtSttusCd());
			
			/** 명령한 센싱태그들의 코드와 값 */
			List<KafkaSnsnTag> snsnTagList = setup.getSnsnTagList();
			for(Row row : dev.getRows()){
				/** 계측데이터리스트(10) */
				for(MsrData msr : row.getMsrDatas()){
					snsnTagList.add(new KafkaSnsnTag(msr.getSnsnTagCd(), String.valueOf(msr.getRlNumVal())));
				}
				/** 상태데이터리스트(20) */
				for(SttusData sttus : row.getSttusDatas()){
					snsnTagList.add(new KafkaSnsnTag(sttus.getSnsnTagCd(), String.valueOf(sttus.getRlNumVal())));
				}
				/** 제어데이터리스트(30) */
				for (ContlData contl : row.getContlDatas()) {
					snsnTagList.add(new KafkaSnsnTag(contl.getSnsnTagCd(), String.valueOf(contl.getContlVal())));
				}
				/** 명령어데이터리스트(31) */
				for (CmdData cmd : row.getCmdDatas()) {
					snsnTagList.add(new KafkaSnsnTag(cmd.getSnsnTagCd(), StringUtils.byteArrayToHex(cmd.getCmdValTxn())));
				}
				/** 위치데이터(40) */
//				LoData lo = row.getLoData();
				/** 이진데이터(50) */
				for (BinData bin : row.getBinDatas()) {
					snsnTagList.add(new KafkaSnsnTag(bin.getSnsnTagCd(), StringUtils.byteArrayToHex(bin.getBinValTxn())));
				}
				/** 문자열데이터(60) */
				for (StrData str : row.getStrDatas()) {
					snsnTagList.add(new KafkaSnsnTag(str.getSnsnTagCd(), String.valueOf(str.getStrVal())));
				}
				/** 일시데이터(61) */
				for (DtData dt : row.getDtDatas()) {
					snsnTagList.add(new KafkaSnsnTag(dt.getSnsnTagCd(), String.valueOf(dt.getDtVal())));
				}
				/** 이벤트데이터(70) */
//				EvData ev = row.getEvData();
				/** 일반설정데이터(80) */
				for (GenlSetupData genlSetup : row.getGenlSetupDatas()) {
					snsnTagList.add(new KafkaSnsnTag(genlSetup.getSnsnTagCd(), String.valueOf(genlSetup.getSetupVal())));
				}
				/** 스케줄설정데이터(81) */
//				for (SclgSetupData sclgSetup : row.getSclgSetupDatas()) {
//					snsnTagList.add(new KafkaSnsnTag(sclgSetup.getSnsnTagCd(), String.valueOf(sclgSetup.get)));
//				}
				/** 이진설정데이터(82) */
				for (BinSetupData binSetup : row.getBinSetupDatas()) {
					snsnTagList.add(new KafkaSnsnTag(binSetup.getSnsnTagCd(), StringUtils.byteArrayToHex(binSetup.getSetupVal())));
				}
				
			}
			devSetup.put(setup.getGroupKey(), setup);
			
		}
		
		if(group.isControl()){	/** 도착한 데이터가 제어 데이터의 경우 hbase 히스토리 기록 */
			saveProcess(group, false);
		}
		
	}
	
	/**
	 * @param json
	 * @throws APIException 
	 * @see 3332(SpotDevRetvReslt, 외부장치조회 리포트)에 대한 처리로직 
	 */

	private void processInitaDevRetvResltExtr(String json) throws APIException{
		
		SpotDevRetvReslt data = gson.fromJson(json, SpotDevRetvReslt.class);
		String transacId = data.getTransacId();		

		KafkaDevSetupGroup group = pool.kafkaDevSetupManager.getDevSetupGroup(transacId);
		
		/** 등록된 콜백인터페이스의 콜백함수를 수행. */
		group.getCallback().callback(data);
		
		Map<String, KafkaDevSetup> devSetup = group.getDevSetup();
		
		for(SpotDev dev :data.getSpotDevs()){
			
			KafkaDevSetup setup = new KafkaDevSetup();
			setup.setSpotDevSeq(dev.getSpotDevSeq());
			setup.setSvcTgtSeq(dev.getSvcTgtSeq());
			setup.setDevTrtSttusCd(dev.getDevTrtSttusCd());
			
			/** 명령한 센싱태그들의 코드와 값 */
			List<KafkaSnsnTag> snsnTagList = setup.getSnsnTagList();
			for(GenlSetupData genlSetup : dev.getGenlSetupDatas()){
				snsnTagList.add(new KafkaSnsnTag(genlSetup.getSnsnTagCd(), genlSetup.getSetupVal()));
			}
			
			/** 이진설정데이터(82) */
			for (BinSetupData binSetup : dev.getBinSetupDatas()) {
				String snsnCd = binSetup.getSnsnTagCd();
				if("82997105".equals(snsnCd)){	/** 7105 필터링 */
					continue;
				}
				snsnTagList.add(new KafkaSnsnTag(binSetup.getSnsnTagCd(), StringUtils.byteArrayToHex(binSetup.getSetupVal())));
			}
			
			devSetup.put(setup.getGroupKey(), setup);
		}
		
		if(group.isControl()){	/** 도착한 데이터가 제어 데이터의 경우 hbase 히스토리 기록 */
			saveProcess(group, false);
		}

	}
	
	/**
	 * @param json
	 * @throws Exception 
	 * @see 5252(ItgCnvyData, 제어/설정)에 대한 처리로직 
	 */
	private void processContlItgCnvyData(String json) throws Exception{
		ItgCnvyData data = gson.fromJson(json, ItgCnvyData.class);
		
		String transacId = data.getTransacId();
		
		KafkaDevSetupGroup group = pool.kafkaDevSetupManager.getDevSetupGroup(transacId);
		logger.debug("ItgCnvyData 5252 : "+group);
		Map<String, KafkaDevSetup> devSetup = group.getDevSetup();		
		
		for(SpotDevCnvyData dev :data.getSpotDevCnvyDatas()){
			logger.debug("ItgCnvyData 5252 iterate in getSpotDevCnvyDatas() start");
			KafkaDevSetup setup = new KafkaDevSetup();
			setup.setSpotDevSeq(dev.getSpotDevSeq());
			setup.setSvcTgtSeq(dev.getSvcTgtSeq());
			
			/** 명령한 센싱태그들의 코드와 값 */
			List<KafkaSnsnTag> snsnTagList = setup.getSnsnTagList();
			for(CnvyRow row : dev.getCnvyRows()){						
				/** 계측데이터리스트(10) */
				for(MsrData msr : row.getMsrDatas()){
					snsnTagList.add(new KafkaSnsnTag(msr.getSnsnTagCd(), String.valueOf(msr.getRlNumVal())));
				}
				/** 상태데이터리스트(20) */
				for(SttusData sttus : row.getSttusDatas()){
					snsnTagList.add(new KafkaSnsnTag(sttus.getSnsnTagCd(), String.valueOf(sttus.getRlNumVal())));
				}
				/** 제어데이터리스트(30) */
				for (ContlData contl : row.getContlDatas()) {
					snsnTagList.add(new KafkaSnsnTag(contl.getSnsnTagCd(), String.valueOf(contl.getContlVal())));
				}
				/** 명령어데이터리스트(31) */
				for (CmdData cmd : row.getCmdDatas()) {
					snsnTagList.add(new KafkaSnsnTag(cmd.getSnsnTagCd(), StringUtils.byteArrayToHex(cmd.getCmdValTxn())));
				}
				/** 위치데이터(40) */
//				LoData lo = row.getLoData();
				/** 이진데이터(50) */
				for (BinData bin : row.getBinDatas()) {
					
					String snsnCd = bin.getSnsnTagCd();
					byte[] bytes = bin.getBinValTxn();

					if("50000007".equals(snsnCd)){ /** 하위작치삭제 */
						if(bytes[0] == (byte)0x00){	/** 하위장치삭제 실패된 케이스는 실제 장치가 hub에 없는 것으로 보고 삭제 프로세스 진행. */
							group.setInexClusion(true);
							SpotDevBasKey key = new SpotDevBasKey();
							key.setSvcTgtSeq(dev.getSvcTgtSeq());
							key.setSpotDevId(dev.getSpotDevId());
							key.setCretrId("KAFKA");
							key.setAmdrId("KAFKA");
							pool.initDeviceAddService.deleteDevice(key);
						}
					}
					
					
					if("50000003".equals(snsnCd) || "50000004".equals(snsnCd)){	/** inclusion or exclusion 에 대한 성공유유 */
						if(bytes[0] == (byte)0x01){ /** 성공 */
							group.setInexClusion(true);
						}
					}
					
					
					if("50997105".equals(snsnCd)){	/** 도어락, 침입감지 통보관련 */
						if(bytes[4] == (byte)0x06){	/** access control header */
							if(bytes[5] == (byte)0x0E){	/** 신규 사용자 등록 성공 */
								group.setInexClusion(true);
							}
						}
					}
					
					if("50996203".equals(snsnCd)){	/** 도어락 */
						group.setInexClusion(true); /** 제어 성공 플래그 */
						if(bytes[0] == (byte)0x00){	/** 열림 */
							bytes = ByteBuffer.allocate(5)
							.put((byte)0x00)
							.put((byte)0x00)
							.put((byte)0x00)
							.put((byte)0x00)
							.put((byte)0x00).array();
						}else if(bytes[0] == (byte)0xff){ /** 닫힘 */
							bytes = ByteBuffer.allocate(5)
							.put((byte)0xff)
							.put((byte)0x00)
							.put((byte)0x00)
							.put((byte)0x00)
							.put((byte)0x00).array();
						}
					}
					
					if("50992503".equals(snsnCd)){	/** 가스밸브 */
						group.setInexClusion(true); /** 제어 성공 플래그 */
					}
					
					snsnTagList.add(new KafkaSnsnTag(snsnCd, StringUtils.byteArrayToHex(bytes)));
					
				}
				/** 문자열데이터(60) */
				for (StrData str : row.getStrDatas()) {
					snsnTagList.add(new KafkaSnsnTag(str.getSnsnTagCd(), String.valueOf(str.getStrVal())));
				}
				/** 일시데이터(61) */
				for (DtData dt : row.getDtDatas()) {
					snsnTagList.add(new KafkaSnsnTag(dt.getSnsnTagCd(), String.valueOf(dt.getDtVal())));
				}
				/** 이벤트데이터(70) */
//				EvData ev = row.getEvData();
				/** 일반설정데이터(80) */
				for (GenlSetupData genlSetup : row.getGenlSetupDatas()) {
//					if("82996301".equals(genlSetup.getSnsnTagCd())){
//						continue;
//					}
					snsnTagList.add(new KafkaSnsnTag(genlSetup.getSnsnTagCd(), String.valueOf(genlSetup.getSetupVal())));
				}
				/** 스케줄설정데이터(81) */
//				for (SclgSetupData sclgSetup : row.getSclgSetupDatas()) {
//					snsnTagList.add(new KafkaSnsnTag(sclgSetup.getSnsnTagCd(), String.valueOf(sclgSetup.get)));
//				}
				/** 이진설정데이터(82) */
				for (BinSetupData binSetup : row.getBinSetupDatas()) {
					String snsnCd = binSetup.getSnsnTagCd();
					byte[] snsnVal = binSetup.getSetupVal();
					
					if("82997105".equals(snsnCd)){	/** 7105 필터링 */
						continue;
					}
					
					if("82997006".equals(snsnCd)){	/** 70046필터링, 가스밸브 시간세팅 */
						/** 센싱코드 82997006으로 올라온경우 성공한 케이스임  */
						group.setInexClusion(true);	/** 제어성공 플래그 */
					}
					
					if("82996303".equals(snsnCd)){	/** 도어락 비밀번호 등록에 대한 처리, 50997105 0x06 0x0E 올라오지 않음. 2015-08-07 */
						group.setInexClusion(true);
					}
					
					snsnTagList.add(new KafkaSnsnTag(snsnCd, StringUtils.byteArrayToHex(snsnVal)));
				}
				
			}
			
			
			devSetup.put(setup.getGroupKey(), setup);
			
			logger.debug("ItgCnvyData 5252 iterate in getSpotDevCnvyDatas() end");

		}
		
		if(group.isControl()){	/** 도착한 데이터가 제어 데이터의 경우 hbase 히스토리 기록 */
			logger.debug("ItgCnvyData 5252 isControl");
			saveProcess(group, true);
		}
	}
	
	/**
	 * @param transacId
	 * @param group
	 * @param control, true : 설정/제어, 조회 : false
	 * @throws APIException 
	 * @see 제어 데이터를 저장하는 로직 (제어모드저장, 제어상태저장, 제어이력저장)
	 */
	public void saveProcess(KafkaDevSetupGroup group, boolean control) throws APIException{
		Map<String, KafkaDevSetup> devSetup = group.getDevSetup();
		
		logger.debug("saveProcess 1");

		String rootTransacId = group.getTransacIds().get(0);
		
		logger.debug("saveProcess 2");

		
		for(KafkaDevSetup setup : devSetup.values()){

			logger.debug("saveProcess 3");

			if(setup.getSpotDevSeq() != 1){	/** 제어가 hub가 아닌경우만 제어로 보고 저장   */
				logger.debug("saveProcess 4");
				if(control){ /** 제어의 경우 명령이 모두 도착한 경우에만 저장 */
					logger.debug("saveProcess 6");
					if(group.isReturnAll()){	/** 제어한 명령이 전부 도착한 rdb 상태 기록 */
						logger.debug("saveProcess 7");
						pool.kafkaSpotDevService.saveSpotDevGenlSetupTxn(setup);
					}
					
				}else{	/** 조회의 경우 명령 도착에 상관없이 저장 */
					logger.debug("saveProcess 8");
					pool.kafkaSpotDevService.saveSpotDevGenlSetupTxn(setup);
				}
			}
		}
		
		/** Kafka로부터 데이터가 다도착해서 DB까지 업데이트 되었음을 hbase 제어이력 업데이트. */
		if(group.isReturnAll()){			

			logger.debug("saveProcess 9");

			/** mode 제어 성공 */
			if(group.isMode()){
				logger.debug("saveProcess 10");
				pool.hbaseControlHistoryService.insertMode(group);
			}

			group.setSaved(true);

			
			Map<String, Object> attr = group.getAttributes();
			String rowKey = (String) attr.get("rowKey");
			pool.hbaseControlHistoryService.update(rowKey, "contlTrtSttusCd", KafkaDevTrtSttusType.SUCCESS.getValue());
			
//			pool.hbaseControlHistoryService.update(rootTransacId);
//			for(HbaseControlHistory hist: pool.hbaseControlHistoryService.findList(rootTransacId)){
//				logger.debug("saveProcess 11");
//				String rowKey = hist.getRowKey();
//				pool.hbaseControlHistoryService.update(rowKey, "contlTrtSttusCd", KafkaDevTrtSttusType.SUCCESS.getValue());
//			}
		}
		
	}
}
