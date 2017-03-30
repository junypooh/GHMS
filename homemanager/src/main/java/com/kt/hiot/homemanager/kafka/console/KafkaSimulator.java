package com.kt.hiot.homemanager.kafka.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.kt.hiot.homemanager.kafka.entity.KafkaDevSetupGroup;
import com.kt.hiot.homemanager.kafka.type.KafkaMsgType;
import com.kt.smcp.gw.ca.domn.cnvy.CnvyRow;
import com.kt.smcp.gw.ca.domn.cnvy.ItgCnvyData;
import com.kt.smcp.gw.ca.domn.cnvy.SpotDevCnvyData;
import com.kt.smcp.gw.ca.domn.qry.LastValRetv;
import com.kt.smcp.gw.ca.domn.row.BinData;
import com.kt.smcp.gw.ca.domn.row.CmdData;
import com.kt.smcp.gw.ca.domn.row.ContlData;
import com.kt.smcp.gw.ca.domn.row.GenlSetupData;
import com.kt.smcp.gw.ca.domn.spotdev.SpotDevRetv;


public class KafkaSimulator implements Runnable{
		
	private static final Logger logger = LoggerFactory.getLogger(KafkaSimulator.class);

	final String operatingSystem = System.getProperty("os.name");
	
	private boolean running = true;
	
	public int currnetTransactionId = 0;

	BufferedReader reader = null;
	KafkaConsumer apiConsumer = null;
	KafkaConsumer ecConsumer = null;
	
	Gson gson = new Gson();

	public KafkaDevSetupManager manager = new KafkaDevSetupManager();
	
	public KafkaSimulator() {
		reader = new BufferedReader(new InputStreamReader(System.in));
		apiConsumer = new KafkaConsumer(KafkaConsumer.API_TOPIC, "v-hms02:9091", "API_SND_ITG_001HIT003_SIM_GROUP_TEST_01", manager);
		ecConsumer = new KafkaConsumer(KafkaConsumer.EC_TOPIC, "v-hms02:9091", "EC_SND_ITG_001HIT003_SIM_GROUP_TEST_01", manager);
	}

	@Override
	public void run() {
//		Scanner scanner = null;
//		KafkaProducer producer = null;
//		BinData binData = null;
//		ContlData conData = null;
//		CmdData cmdData1 = null;
//		CmdData cmdData2 = null;
//		GenlSetupData genlSetupData = null;
//		try {
//			
//			//configure user input 
////			reader = new BufferedReader(new InputStreamReader(System.in));
////			scanner = new Scanner(System.in);
////			String chooseEvent = scanner.nextLine();
//			
//			producer = new KafkaProducerImpl("211.42.137.222:9092");
//			apiConsumer.start();
//			ecConsumer.start();
//			
//			mainCommandDisplay();
//			
//			String transacId = "0";
//			
//			//장치(2000000000000000001 > 허브, 2000000000000000002 > 도어락, 2000000000000000003 > 가스밸브, 2000000000000000004 > 침입감지)
//			while(running){
//				String line = reader.readLine();
//				switch(line){
//					case "1-1" :
//						transacId = manager.create("272828ddkdke887237", 1);
//						cmdData1 = new CmdData();
//						cmdData1.setSnsnTagCd("31000008");
//						cmdData1.setCmdValTxn(new byte[0]);
//						producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.INITA_DEV_RETV_EXTR, getDummySpotDevRetv(transacId, cmdData1));
//						break;
//					case "1-2" :
//						transacId = manager.create("272828ddkdke887237", 1);
//						binData = new BinData();
//						binData.setSnsnTagCd("50000003");
//						binData.setBinValTxn(new byte[0]);
//						producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, getDummyItgCnvyData(transacId, 2000000000000000001L, "iothub",binData));
//						break;
//					case "1-3" :
//						transacId = manager.create("272828ddkdke887237", 1);
//						binData = new BinData();
//						binData.setSnsnTagCd("50000004");
//						binData.setBinValTxn(new byte[0]);
//						producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, getDummyItgCnvyData(transacId, 2000000000000000001L, "iothub", binData));
//						break;
//					case "2-1" :
//						transacId = manager.create("272828ddkdke887237", 1);
//						cmdData1 = new CmdData();
//						cmdData1.setSnsnTagCd("31996202");
//						cmdData1.setCmdValTxn(new byte[0]);
//						cmdData2 = new CmdData();
//						cmdData2.setSnsnTagCd("31998002");
//						cmdData2.setCmdValTxn(new byte[0]);
//						producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, getDummyItgCnvyData(transacId, 2000000000000000002L, "iotdevice01",cmdData1, cmdData2));
//						break;
//					case "2-2" :
//						transacId = manager.create("272828ddkdke887237", 1);
//						genlSetupData = new GenlSetupData();
//						genlSetupData.setSnsnTagCd("82996301");
//						
//						producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, getDummyItgCnvyData(transacId, 2000000000000000002L, "iotdevice01", cmdData1));
//						break;
//					case "3-1" :
//						transacId = manager.create("272828ddkdke887237", 1);
//						GenlSetupData gData1 = new GenlSetupData();
//						gData1.setSnsnTagCd("82998404");
//						gData1.setSetupVal("0");
//						producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, getDummyItgCnvyData(transacId, 2000000000000000003L, "iotdevice03", gData1));
//						break;
//					case "4-1" :
//						transacId = manager.create("272828ddkdke887237", 1);
//						cmdData1 = new CmdData();
//						cmdData1.setSnsnTagCd("31992502");
//						cmdData1.setCmdValTxn(new byte[0]);
//						cmdData2 = new CmdData();
//						cmdData2.setSnsnTagCd("31998002");
//						cmdData2.setCmdValTxn(new byte[0]);
//						producer.sendJson(KafkaConsumer.API_TOPIC, KafkaMsgType.CONTL_ITGCNVY_DATA, getDummyItgCnvyData(transacId, 2000000000000000004L, "iotdevice02", cmdData1, cmdData2));
//						break;
//					case "q" : 
//						quitDisplay("command by input(q)");
//						running = false; 
//						break;
//					case "s" : 
//						mainCommandDisplay();
//						break;
//					default : System.out.println("잘못된 커맨드! 커맨드매뉴를 보시려면 's'를 입력하세요.");
//				}
//			}
//		} catch (Exception e) {
//			quitDisplay(e.getMessage());
//			e.printStackTrace();
//		} finally {
//			try {
//				if(reader != null){
//					reader.close();
//				}
//				if(scanner != null){
//					scanner.close();
//				}
//				
//				if(producer != null){
//					producer.close();
//				}
//
//				if(apiConsumer != null){
//					apiConsumer.interrupt();
//				}
//				if(ecConsumer != null){
//					ecConsumer.interrupt();
//				}
//			} catch (IOException e) {
//			}
//			
//		}
	}
	
	public void mainCommandDisplay(){
		System.out.println("*********************************** command key for [IoT GW] ******************************************");
		System.out.println("1-1 : IoT 단말 연결상태  조회");
		System.out.println("1-2 : z-wave inclusion 요청(S/W Key)");
		System.out.println("1-3 : z-wave exclusion 요청(S/W Key)");
		System.out.println("*********************************** command key for [Door Lock] ***************************************");
		System.out.println("2-1 : 도어락 상태 확인(장치상태/배터리)");
		System.out.println("2-2 : 사용자 PW 추가");
		System.out.println("2-3 : 사용자 PW 변경");
		System.out.println("2-4 : 사용자 개별 삭제");
		System.out.println("2-5 : 사용자 전체 삭제");
		System.out.println("*********************************** command key for [Open/Close sensor] *******************************");
		System.out.println("3-1 : wake-up timer 설정");
		System.out.println("*********************************** command key for [Gas valve] ***************************************");
		System.out.println("4-1 : Gas valve 상태 확인(장치상태/배터리)");
		System.out.println("*******************************************************************************************************");
		System.out.println("q : 종료");
		System.out.println("s : 매뉴보기");
		System.out.println("*******************************************************************************************************");
	}
	
	public void quitDisplay(String cause){
		System.out.println("**************************************************************");
		System.out.println("System exit");
		System.out.println("cause : "+cause);
		System.out.println("**************************************************************");
	}	
	
	private String getDummySpotDevRetv(String transacId, CmdData... array){
		SpotDevRetv spotDevRetv = new SpotDevRetv();
		List<CmdData> cmdDataList = new ArrayList<CmdData>();
		spotDevRetv.setTransacId(transacId);
		spotDevRetv.setReqEcSrvrId("HM_EC_01");
		spotDevRetv.setCmdDatas(cmdDataList);
		for (CmdData cmdData : array) {
			cmdDataList.add(cmdData);
		}
		
		return gson.toJson(spotDevRetv, SpotDevRetv.class);
	}
	
	private String getDummyItgCnvyData(String transacId, long devSeq, String spotDevId, Object... array) {
		
		ItgCnvyData itgCnvyData = new ItgCnvyData();
		List<SpotDevCnvyData> spotDevCnvyDataList = new ArrayList<SpotDevCnvyData>();
		SpotDevCnvyData spotDevCnvyData = new SpotDevCnvyData();
		
		//EC server name
		itgCnvyData.setReqEcSrvrId("HM_EC_01");
		spotDevCnvyData.setTransacId(transacId+"_"+System.currentTimeMillis());
		
		List<CnvyRow> cnvyRowList = new ArrayList<CnvyRow>();
		CnvyRow cnvyRow = new CnvyRow();
		List<GenlSetupData> genlSetupDataList = new ArrayList<GenlSetupData>();
		GenlSetupData genlSetupData = new GenlSetupData();
		List<ContlData> contlDataList = new ArrayList<ContlData>();
		List<BinData> binDataList = new ArrayList<BinData>();
		List<CmdData> cmdDataList = new ArrayList<CmdData>();
		
		itgCnvyData.setTransacId(transacId);
		itgCnvyData.setSpotDevCnvyDatas(spotDevCnvyDataList);
		
		spotDevCnvyDataList.add(spotDevCnvyData);
		spotDevCnvyData.setCnvyRows(cnvyRowList);
		cnvyRowList.add(cnvyRow);
		cnvyRow.setGenlSetupDatas(genlSetupDataList);
		genlSetupDataList.add(genlSetupData);
		cnvyRow.setContlDatas(contlDataList);
		cnvyRow.setBinDatas(binDataList);
		cnvyRow.setCmdDatas(cmdDataList);
		
		//고정
		spotDevCnvyData.setSvcTgtSeq(5000000001L);
		spotDevCnvyData.setSpotDevSeq(devSeq);
		spotDevCnvyData.setSpotDevId(spotDevId);
		
		
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
		}

		
		
		return gson.toJson(itgCnvyData, ItgCnvyData.class);
		
	}		


}
