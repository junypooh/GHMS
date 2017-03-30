/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.service;

import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.kt.giga.home.openapi.ghms.common.DeviceType;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.SpotDevItemNm;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.SpotDevItemVal;
import com.kt.giga.home.openapi.ghms.common.PushType;
import com.kt.giga.home.openapi.ghms.common.domain.vo.BaseVo;
import com.kt.giga.home.openapi.ghms.common.service.ECService;
import com.kt.giga.home.openapi.ghms.common.token.AuthToken;
import com.kt.giga.home.openapi.ghms.devices.dao.DevicesDao;
import com.kt.giga.home.openapi.ghms.devices.dao.InitDeviceAddDao;
import com.kt.giga.home.openapi.ghms.devices.domain.key.CnvyRowData;
import com.kt.giga.home.openapi.ghms.devices.domain.key.DeviceCtrStatusKey;
import com.kt.giga.home.openapi.ghms.devices.domain.key.DeviceModeKey;
import com.kt.giga.home.openapi.ghms.devices.domain.key.IotControlKey;
import com.kt.giga.home.openapi.ghms.devices.domain.key.SpotDevBasKey;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.DeviceModeVo;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.NewSpotDevice;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.NewSpotDeviceGw;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.NewSpotDeviceMasterVo;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.SpotDevice;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.SpotDeviceGroup;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.SpotDeviceGw;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.SpotDeviceMasterVo;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.SpotDeviceSnsnData;
import com.kt.giga.home.openapi.ghms.kafka.KafkaDevSetupManager;
import com.kt.giga.home.openapi.ghms.kafka.KafkaProducer;
import com.kt.giga.home.openapi.ghms.kafka.entity.HbaseControlHistory;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaDevSetup;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaDevSetupGroup;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaSnsnTag;
import com.kt.giga.home.openapi.ghms.kafka.service.HbaseControlHistoryService;
import com.kt.giga.home.openapi.ghms.kafka.service.KafkaSpotDevService;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.avro.ComplexEvent;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.cnvy.CnvyRow;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.cnvy.ItgCnvyData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.cnvy.SpotDevCnvyData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.qry.LastValRetv;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.BinData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.BinSetupData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.CmdData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.ContlData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.GenlSetupData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDev;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevDtl;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevKey;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevRetv;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.query.SpotDevBasQueryVO;
import com.kt.giga.home.openapi.ghms.kafka.type.KafkaMsgType;
import com.kt.giga.home.openapi.ghms.ktInfra.dao.GhmsProvisionDao;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.PushInfoRequest;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SpotDevBas;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SvcTgtBas;
import com.kt.giga.home.openapi.ghms.ktInfra.service.KPNSService;
import com.kt.giga.home.openapi.ghms.ktInfra.service.KTInfraService;
import com.kt.giga.home.openapi.ghms.user.dao.MasterDao;
import com.kt.giga.home.openapi.ghms.user.domain.key.DeviceKey;
import com.kt.giga.home.openapi.ghms.user.domain.key.DeviceMasterKey;
import com.kt.giga.home.openapi.ghms.user.domain.key.MasterUserKey;
import com.kt.giga.home.openapi.ghms.user.domain.vo.DeviceMasterVo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.DeviceVo;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;
import com.kt.giga.home.openapi.ghms.util.properties.APIEnv;

/**
 * 제어 서비스
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 16.
 */
@Service
@Transactional(
        propagation = Propagation.REQUIRED,
        rollbackFor = {
                Exception.class,
                RuntimeException.class,
                SQLException.class
        }
)
public class DevicesService {

	/** Logger */
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DevicesDao devicesDao;
	
	@Autowired
	private KTInfraService ktService;
	
	@Autowired
	private KafkaDevSetupManager kafkaDevSetupManager;
	
	@Autowired
	private KafkaProducer kafkaProducer;
	
	@Autowired
	private KafkaSpotDevService kafkaSpotDevService;
	
	@Autowired
	private HbaseControlHistoryService hbaseControlHistoryService;
	
	@Autowired
	private APIEnv apiEnv;
	
	@Autowired
	private KPNSService kPNSService;
	
	@Autowired
	private ECService eCService;
	
	@Autowired
	private MasterDao masterDao;

	@Autowired
	private InitDeviceAddDao initDeviceAddDao;
	
	@Autowired
	private GhmsProvisionDao ghmsProvisionDao;
	
	private Map<String, String> baseMap;
	
	public Gson gson = new Gson();
	
	public DevicesService() {
	    
		// 현장장치 그룹별 setting.
		baseMap = new HashMap<String, String>();
		baseMap.put(DeviceType.DOOR_LOCK.getCode(), DeviceType.DOOR_LOCK.getGroupNm());
		baseMap.put(DeviceType.GAS_VALVE.getCode(), DeviceType.GAS_VALVE.getGroupNm());
		baseMap.put(DeviceType.TRESPS_SENSOR.getCode(), DeviceType.TRESPS_SENSOR.getGroupNm());
		baseMap.put(DeviceType.UNKNOWN.getCode(), DeviceType.UNKNOWN.getGroupNm());
	}
	
	/**
	 * 장치 정보 조회(kafka 요청)
	 * @param svcTgtSeq
	 * @param devType
	 * @param spotDevSeq
	 * @param snsnCd
	 * @return Map<String, Object>
	 * @throws APIException
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public Map<String, Object> requestDeviceStatusToKafka(Long svcTgtSeq, String devType, Long spotDevSeq, String snsnCd, String command) throws APIException{
		
		Map<String, Object> res = new HashMap<String, Object>();
		List<String> transacIds = kafkaDevSetupManager.create(1, 1, false);
		List<SpotDevice> spotDevs = devicesDao.selectSpotDevList(devType, svcTgtSeq, null, spotDevSeq, 0);
		if(spotDevs == null){
			throw new APIException(String.format("Not exists device!! svcTgtSeq : %s, spotDevSeq %s ", svcTgtSeq, spotDevSeq), HttpStatus.NOT_FOUND);
		}	
		SpotDevice spotDev = spotDevs.get(0);
		SpotDevBasQueryVO gwQueryVO = eCService.sendSelectQueryToEC(svcTgtSeq, spotDev.getUpSpotDevSeq(), spotDev.getUpSpotDevId());
		
		String gwConnEcId = gwQueryVO.getSpotDevBasVO().getConnSrvrId();		
		
		if(gwConnEcId == null){
			throw new APIException(String.format("GW do not connect at EC!! upSpotDevId : %s", spotDev.getUpSpotDevId()), HttpStatus.NOT_FOUND);
		}
		

		SpotDevRetv spotDevRetv = new SpotDevRetv();
		LastValRetv lastValRetv = new LastValRetv();
		
		List<CmdData> cmdDatas = new ArrayList<CmdData>();
		CmdData cmdData = new CmdData();
		List<SpotDevKey> inclSpotDevKeys = new ArrayList<SpotDevKey>();
		SpotDevKey key = new SpotDevKey();
		
		
		spotDevRetv.setReqEcSrvrId(gwConnEcId);
		lastValRetv.setReqEcSrvrId(gwConnEcId);
		spotDevRetv.setTransacId(transacIds.get(0));
		lastValRetv.setTransacId(transacIds.get(0));
		spotDevRetv.setUnitSvcCd(CommonConstants.DSTR_CD + CommonConstants.SVC_THEME_CD + CommonConstants.UNIT_SVC_CD);
		lastValRetv.setUnitSvcCd(CommonConstants.DSTR_CD + CommonConstants.SVC_THEME_CD + CommonConstants.UNIT_SVC_CD);
		spotDevRetv.setCmdDatas(cmdDatas);
		lastValRetv.setCmdDatas(cmdDatas);
		spotDevRetv.setInclSpotDevKeys(inclSpotDevKeys);
		lastValRetv.setInclSpotDevKeys(inclSpotDevKeys);
		
		cmdDatas.add(cmdData);
		inclSpotDevKeys.add(key);
		
		cmdData.setSnsnTagCd(snsnCd);
		cmdData.setCmdValTxn(new byte[0]);
		
		key.setSvcTgtSeq(svcTgtSeq);
		key.setSpotDevSeq(spotDev.getSpotDevSeq());
		key.setGwCnctId(spotDev.getGwCnctId());
		key.setSpotDevId(spotDev.getSpotDevId());
		
		
		if("333".equals(command)){
			String json = gson.toJson(spotDevRetv, SpotDevRetv.class);
			kafkaProducer.sendJson(apiEnv.getProperty("producer.api.topic"), KafkaMsgType.INITA_DEV_RETV_RESLT_EXTR, json);
		}else{
			String json = gson.toJson(lastValRetv, LastValRetv.class);
			kafkaProducer.sendJson(apiEnv.getProperty("producer.api.topic"), KafkaMsgType.QRY_LAST_VAL, json);
		}
		
		res.put("transacIdList", transacIds);
		res.put("getCmdYn", "S");
		
		return res;

		
	}
	
	/**
	 * AP 접속 정보 통보(kafka)
	 * @param svcTgtSeq
	 * @param eventId
	 * @return void
	 */
	public void sendApStatusToKafka(Long svcTgtSeq, String eventId){
		ComplexEvent complexEvent = new ComplexEvent();
		complexEvent.setSvcTgtSeq(svcTgtSeq);
		complexEvent.setEvtId(eventId);
		kafkaProducer.sendAvro(apiEnv.getProperty("producer.api.topic.complex"), complexEvent);
	}	
	
	
	/**
	 * 게이트웨이 정보 조회(kafka 요청)
	 * @param token
	 * @param gwUUID
	 * @return
	 * @throws APIException
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public Map<String, Object> selectSpotGateWayListRequest(AuthToken token, long serviceNo, long spotDevSeq, String getCmdYn, String getCheckerYn) throws APIException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> transacIdList = new ArrayList<String>();
		
		// N일 경우 kafka 연동을 하지 않는다.
		if("N".equals(getCmdYn)) {
			// 임의의 transactionId를 만든다.
			List<String> list = kafkaDevSetupManager.create(1, 1, false); // 단말 개수
			String transactionId = list.get(0); // 단말 개수
			transacIdList.add(transactionId);
		} else {

			/*
			 * 1. kafka 연동
			 *  cmdDevCount (샌싱태그 갯수)를 알기 위해 현장장치 조회를 한다.
			 *  
			 *  - 장치별 센싱태그
			 *  도어락(4003) :  31996202(장치상태) / 31998002(배터리)
			 *  가스벨브(1006) : 31992502(장치상태) / 31998002(배터리)
	-		 *  침입감지센서(0701) : DB만 본다. kafka 연동하지 않는다
			 */
			// 게이트 웨이 먼저 가져온다.
			List<SpotDeviceGw> gateWayList = devicesDao.selectSpotGateWayList(token.getUserNoLong(), token.getDstrCd(), token.getSvcThemeCd(), token.getUnitSvcCd(), serviceNo, spotDevSeq, DeviceType.GATE_WAY.getCode());

			// kafka 연동
			SpotDevRetv spotDevRetv = new SpotDevRetv();

//			포함현장장치
			List<SpotDevKey> inclSpotDevKeys = new ArrayList<SpotDevKey>();
//			명령어데이터리스트(31)
			List<CmdData> cmdDatas = new ArrayList<CmdData>();
			
			// 게이트 웨이 갯수 만큼 게이트웨이에 연결된 현장장치 리스트를 가져온다.
			int spotDevCnt = 0;
			for(SpotDeviceGw gateway : gateWayList) {

				// 장비 그룹별로 조회한다.
				Set<String> keySet = baseMap.keySet();
				for(Iterator<String> it = keySet.iterator(); it.hasNext();) {
					
					String keyNm = it.next();

					if(!DeviceType.TRESPS_SENSOR.getCode().equals(keyNm) && CommonConstants.GHMS_GW_SVC_TGT_TYPE_CD.equals(gateway.getSvcTgtTypeCd())) { // 침입감지센서는 해당되지 않음. 홈매니저(허브) 상품일 경우만
						List<SpotDevice> spotDevs = devicesDao.selectSpotDevList(keyNm, gateway.getSvcTgtSeq(), gateway.getSpotDevSeq(), null, token.getUserNoLong());

						if(spotDevs != null && spotDevs.size() > 0) {
							// 가져온 현장장리 리스트(현장장치)만큼 샌싱 태그를 싫어서 kafka 에 전송 한다.
							List<String> list = kafkaDevSetupManager.create(spotDevs.size() * 3, spotDevs.size() * 3, false); // 단말 개수
							int i = 1;
							for(SpotDevice spotDev : spotDevs) {
								
								SpotDevKey key = new SpotDevKey();
								key.setSvcTgtSeq(gateway.getSvcTgtSeq());
								key.setSpotDevSeq(spotDev.getSpotDevSeq());
								key.setGwCnctId(spotDev.getGwCnctId());
								key.setSpotDevId(spotDev.getSpotDevId());
								
								inclSpotDevKeys.add(key);
								
								// GW IoT 단말 연결상태  조회
								CmdData data = new CmdData();
								data.setSnsnTagCd("31000008");
								data.setCmdValTxn(new byte[0]);
								cmdDatas.add(data);
								
								spotDevRetv.setInclSpotDevKeys(inclSpotDevKeys);
								spotDevRetv.setCmdDatas(cmdDatas);
								spotDevRetv.setUnitSvcCd(CommonConstants.DSTR_CD + CommonConstants.SVC_THEME_CD + CommonConstants.UNIT_SVC_CD);
								
								String transactionId = list.get(i*3-3);
								spotDevRetv.setTransacId(transactionId);
								
								String json = gson.toJson(spotDevRetv, SpotDevRetv.class);
								
								// kafka send!!!
								kafkaProducer.sendJson(apiEnv.getProperty("producer.api.topic"), KafkaMsgType.INITA_DEV_RETV_EXTR, json);
								
								transacIdList.add(transactionId);
								
								cmdDatas.clear();
								// 배터리 상태 확인 
								CmdData data1 = new CmdData();
								data1.setSnsnTagCd("31998002");
								data1.setCmdValTxn(new byte[0]);
								cmdDatas.add(data1);
								
								spotDevRetv.setInclSpotDevKeys(inclSpotDevKeys);
								spotDevRetv.setCmdDatas(cmdDatas);
								spotDevRetv.setUnitSvcCd(CommonConstants.DSTR_CD + CommonConstants.SVC_THEME_CD + CommonConstants.UNIT_SVC_CD);
								
								String transactionId1 = list.get(i*3-2);
								spotDevRetv.setTransacId(transactionId1);
								
								String json1 = gson.toJson(spotDevRetv, SpotDevRetv.class);
								
								// kafka send!!!
								kafkaProducer.sendJson(apiEnv.getProperty("producer.api.topic"), KafkaMsgType.QRY_LAST_VAL, json1);
								
								transacIdList.add(transactionId1);
	
								cmdDatas.clear();
	
								if(DeviceType.DOOR_LOCK.getCode().equals(keyNm)) {
									// 도어락 장치 상태 확인 
									CmdData data2 = new CmdData();
									data2.setSnsnTagCd("31996202");
									data2.setCmdValTxn(new byte[0]);
									cmdDatas.add(data2);
								}
	
								if(DeviceType.GAS_VALVE.getCode().equals(keyNm)) {
									// Gas valve 장치 상태 확인
									CmdData data2 = new CmdData();
									data2.setSnsnTagCd("31992502");
									data2.setCmdValTxn(new byte[0]);
									cmdDatas.add(data2);
								}
								
								spotDevRetv.setInclSpotDevKeys(inclSpotDevKeys);
								spotDevRetv.setCmdDatas(cmdDatas);
								spotDevRetv.setUnitSvcCd(CommonConstants.DSTR_CD + CommonConstants.SVC_THEME_CD + CommonConstants.UNIT_SVC_CD);
								
								String transactionId2 = list.get(i*3-1);
								spotDevRetv.setTransacId(transactionId2);
								
								String json2 = gson.toJson(spotDevRetv, SpotDevRetv.class);
								
								// kafka send!!!
								kafkaProducer.sendJson(apiEnv.getProperty("producer.api.topic"), KafkaMsgType.QRY_LAST_VAL, json2);
	
								transacIdList.add(transactionId2);
								
								spotDevCnt++;
								i++;
							}
						}
					} else {
						List<String> list = kafkaDevSetupManager.create(1, 1, false); // 단말 개수
						String transactionId2 = list.get(0); // 단말 개수
						transacIdList.add(transactionId2);
					}
				}
			}
			
			if(spotDevCnt > 0) {
				getCmdYn = "Y";
			} else {
				getCmdYn = "N";
			}
		}
		
		map.put("transacIdList", transacIdList);
		map.put("getCmdYn", getCmdYn);
		map.put("getCheckerYn", getCheckerYn);
		return map;

	}
	
	/**
	 * 게이트웨이 정보 조회
	 * @param token
	 * @param gwUUID
	 * @return
	 * @throws APIException
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public SpotDeviceMasterVo selectSpotGateWayList(AuthToken token, DeviceCtrStatusKey key) throws APIException {
		
		SpotDeviceMasterVo masterVo = null;
		
		// N일 경우 kafka 연동과 무관하게 DB만 조회한다.
		if("N".equals(key.getGetCmdYn())) {
			
			if(!StringUtils.isEmpty(key.getModeCode())) {
				// 마스터, 슬레이브에게 PUSH 발송
				String devNm = devicesDao.selectGWDevNmFromSpotDevBas(key.getServiceNo());
				String modeNm = "일반"; 
				if("01".equals(key.getModeCode())){
				    modeNm = "외출";
				} else if("02".equals(key.getModeCode())) {
				    modeNm = "재택";
				}
//				List<String> pnsRegIdList = kPNSService.selectMasterAndSlavePnsRegId(key.getServiceNo());
				List<String> pnsRegIdList = kPNSService.selectExcludeMyInfoPnsRegId(token, key.getServiceNo());
				
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("eventId", PushType.HUB_MOD_CHG.getEventId());
				data.put("devNm", devNm);
				data.put("msg", "\"" + modeNm + "\" 간편제어를 완료하였습니다.");
				
				log.debug("push data:{}", data.toString());
				
				PushInfoRequest req = new PushInfoRequest();
				for(String pnsRegId : pnsRegIdList) {
				    req.setRegistrationId(pnsRegId);
				    req.setData(data);
				    
				    // PUSH 발송
				    kPNSService.push(req);
				}
			}
			
			masterVo = selectSpotGateWayListByDataBase(token, key.getServiceNo(), key.getGwUUID());
			masterVo.setControllRst(true);
		} else {
			
			boolean isFull = true;
			
			// kafka receive!!!
			List<String> transacIdList = key.getTransacIdList();
			KafkaDevSetupGroup kafkaDevSetupGroup = null;
			for(String transacId : transacIdList) {
				
				kafkaDevSetupGroup = kafkaDevSetupManager.getDevSetupGroup(transacId);
				if(!kafkaDevSetupGroup.isFull()) {
					isFull = false;
					
					masterVo = new SpotDeviceMasterVo();
					masterVo.setTransacIdList(key.getTransacIdList());
					masterVo.setGetCmdYn(key.getGetCmdYn());
					
					break;
				}
			}
			
			log.info("############### isFull : " + isFull + " #######################");
			
			// 데이터가 존재하고 DB에 저장이 되었다면....
			if(isFull) {
				
			    // DB 조회
				masterVo = selectSpotGateWayListByDataBase(token, key.getServiceNo(), key.getGwUUID());

				/** inclusion or exclusion 에 대한 성공유무 */
				masterVo.setControllRst(kafkaDevSetupGroup.isInexClusion());
				
				// 복합제어[모드변경] push 발송
				if(transacIdList !=null && transacIdList.size() > 0 && !StringUtils.isEmpty(key.getModeCode())) {
					
					// 마스터, 슬레이브에게 PUSH 발송
					String devNm = devicesDao.selectGWDevNmFromSpotDevBas(key.getServiceNo());
					String modeNm = "일반"; 
					if("01".equals(key.getModeCode())){
					    modeNm = "외출";
					} else if("02".equals(key.getModeCode())) {
					    modeNm = "재택";
					}
//					List<String> pnsRegIdList = kPNSService.selectMasterAndSlavePnsRegId(key.getServiceNo());
					List<String> pnsRegIdList = kPNSService.selectExcludeMyInfoPnsRegId(token, key.getServiceNo());
					
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("eventId", PushType.HUB_MOD_CHG.getEventId());
					data.put("devNm", devNm);
					data.put("msg", "\"" + modeNm + "\" 간편제어를 완료하였습니다.");
					
					log.debug("push data:{}", data.toString());
					
					PushInfoRequest req = new PushInfoRequest();
					for(String pnsRegId : pnsRegIdList) {
					    req.setRegistrationId(pnsRegId);
					    req.setData(data);
					    
					    // PUSH 발송
					    kPNSService.push(req);
					}
				}

			    // push 발송 (단일 제어)
				if(!"S".equals(key.getGetCmdYn()) && transacIdList != null && transacIdList.size() > 0 && StringUtils.isEmpty(key.getModeCode())) {
					
					// 도어락 push 발송/가스밸브 잠금 push 발송 은 transacId 가 무조건 하나. 왜냐면 제어이기 때문이다.
					kafkaDevSetupGroup = kafkaDevSetupManager.getDevSetupGroup(transacIdList.get(0));
				    Map<String, KafkaDevSetup> devSetup = kafkaDevSetupGroup.getDevSetup();
					
					List<SpotDeviceGw> gwList = masterVo.getSpotDevGwList();
					SpotDeviceGw deviceGw = gwList.get(0);
					
					List<SpotDeviceGroup> devGroupList = deviceGw.getSpotDevGroupList();
					
					if(devGroupList != null && devGroupList.size() > 0) {
						
						for(SpotDeviceGroup spotDeiceGroup : devGroupList) {
							
							if(spotDeiceGroup.getSpotGroupCd().equals(DeviceType.DOOR_LOCK.getCode())) {
								
								for(SpotDevice devs : spotDeiceGroup.getSpotDevs()) {
									
								    KafkaDevSetup setup = devSetup.get(""+key.getServiceNo()+devs.getSpotDevSeq());
									
								    if(setup != null) {
									    List<KafkaSnsnTag> snsnTagList = setup.getSnsnTagList();
										
										try {
										    for(KafkaSnsnTag kafkaSnsnTag : snsnTagList) {
												
										        if(PushType.DOOR_LOCK_OPEN.getSnsnTagCd().equals(kafkaSnsnTag.getSnsnTagCd()) && "0000000000".equals(kafkaSnsnTag.getRlNumVal())) {
										            // 도어락 열림 푸시
										            this.pushDoorLockOpen(token, key.getServiceNo(), key.getGwUUID(), devs.getSpotDevSeq());
										        }
										    }
										} catch(Exception pushEx) {
											log.error("push send Fail!!!! {}", pushEx.getMessage());
											pushEx.printStackTrace();
										}
								    }
								}
							}

							
							if(spotDeiceGroup.getSpotGroupCd().equals(DeviceType.GAS_VALVE.getCode())) {
								
								for(SpotDevice devs : spotDeiceGroup.getSpotDevs()) {
									
								    KafkaDevSetup setup = devSetup.get(""+key.getServiceNo()+devs.getSpotDevSeq());
									
								    if(setup != null) {
									    List<KafkaSnsnTag> snsnTagList = setup.getSnsnTagList();
										
										try {
										    for(KafkaSnsnTag kafkaSnsnTag : snsnTagList) {
												
										        if(PushType.GAS_VALVE_CLOSE.getSnsnTagCd().equals(kafkaSnsnTag.getSnsnTagCd()) && "00".equals(kafkaSnsnTag.getRlNumVal())){
							                        // 가스밸브 잠금 푸시
							                        this.pushGasValveClose(token, key.getServiceNo(), key.getGwUUID(), devs.getSpotDevSeq());
							                    }
										    }
										} catch(Exception pushEx) {
											log.error("push send Fail!!!! {}", pushEx.getMessage());
											pushEx.printStackTrace();
										}
								    }
								}
							}
						}
					}
				}
				
				// kafka 정보 삭제
				for(String transacId : transacIdList) {
					kafkaDevSetupManager.remove(transacId);
				}
			}
		}
		
		masterVo.setModeCode(key.getModeCode());
		
		return masterVo;

	}
	
	/**
	 * 게이트웨이 정보 조회(IFTTT 용)
	 * @param token
	 * @param gwUUID
	 * @return
	 * @throws APIException
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public NewSpotDeviceMasterVo selectSpotGateWayList2(AuthToken token, DeviceCtrStatusKey key) throws APIException {
		
		NewSpotDeviceMasterVo masterVo = null;
		
		// N일 경우 kafka 연동과 무관하게 DB만 조회한다.
		if("N".equals(key.getGetCmdYn())) {
			
			if(!StringUtils.isEmpty(key.getModeCode())) {
				// 마스터, 슬레이브에게 PUSH 발송
				String devNm = devicesDao.selectGWDevNmFromSpotDevBas(key.getServiceNo());
				String modeNm = "일반"; 
				if("01".equals(key.getModeCode())){
				    modeNm = "외출";
				} else if("02".equals(key.getModeCode())) {
				    modeNm = "재택";
				}
//				List<String> pnsRegIdList = kPNSService.selectMasterAndSlavePnsRegId(key.getServiceNo());
				List<String> pnsRegIdList = kPNSService.selectExcludeMyInfoPnsRegId(token, key.getServiceNo());
				
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("eventId", PushType.HUB_MOD_CHG.getEventId());
				data.put("devNm", devNm);
				data.put("msg", "\"" + modeNm + "\" 간편제어를 완료하였습니다.");
				
				log.debug("push data:{}", data.toString());
				
				PushInfoRequest req = new PushInfoRequest();
				for(String pnsRegId : pnsRegIdList) {
				    req.setRegistrationId(pnsRegId);
				    req.setData(data);
				    
				    // PUSH 발송
				    kPNSService.push(req);
				}
			}
			
			masterVo = selectSpotGateWayListByDataBase2(token, key.getServiceNo(), key.getGwUUID());
		} else {
			
			boolean isFull = true;
			
			// kafka receive!!!
			List<String> transacIdList = key.getTransacIdList();
			KafkaDevSetupGroup kafkaDevSetupGroup = null;
			for(String transacId : transacIdList) {
				
				kafkaDevSetupGroup = kafkaDevSetupManager.getDevSetupGroup(transacId);
				if(!kafkaDevSetupGroup.isFull()) {
					isFull = false;
					
					masterVo = new NewSpotDeviceMasterVo();
					masterVo.setTransacIdList(key.getTransacIdList());
					masterVo.setGetCmdYn(key.getGetCmdYn());
					
					break;
				}
			}
			
			log.info("############### isFull : " + isFull + " #######################");
			
			// 데이터가 존재하고 DB에 저장이 되었다면....
			if(isFull) {
				
			    // DB 조회
				masterVo = selectSpotGateWayListByDataBase2(token, key.getServiceNo(), key.getGwUUID());

				/** inclusion or exclusion 에 대한 성공유무 */
				masterVo.setControllRst(kafkaDevSetupGroup.isInexClusion());
				
				// 복합제어[모드변경] push 발송
				if(transacIdList !=null && transacIdList.size() > 0 && !StringUtils.isEmpty(key.getModeCode())) {
					
					// 마스터, 슬레이브에게 PUSH 발송
					String devNm = devicesDao.selectGWDevNmFromSpotDevBas(key.getServiceNo());
					String modeNm = "일반"; 
					if("01".equals(key.getModeCode())){
					    modeNm = "외출";
					} else if("02".equals(key.getModeCode())) {
					    modeNm = "재택";
					}
//					List<String> pnsRegIdList = kPNSService.selectMasterAndSlavePnsRegId(key.getServiceNo());
					List<String> pnsRegIdList = kPNSService.selectExcludeMyInfoPnsRegId(token, key.getServiceNo());
					
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("eventId", PushType.HUB_MOD_CHG.getEventId());
					data.put("devNm", devNm);
					data.put("msg", "\"" + modeNm + "\" 간편제어를 완료하였습니다.");
					
					log.debug("push data:{}", data.toString());
					
					PushInfoRequest req = new PushInfoRequest();
					for(String pnsRegId : pnsRegIdList) {
					    req.setRegistrationId(pnsRegId);
					    req.setData(data);
					    
					    // PUSH 발송
					    kPNSService.push(req);
					}
				}

			    // push 발송 (단일 제어)
				if(!"S".equals(key.getGetCmdYn()) && transacIdList !=null && transacIdList.size() > 0 && StringUtils.isEmpty(key.getModeCode())) {
					
					// 도어락 push 발송/가스밸브 잠금 push 발송 은 transacId 가 무조건 하나. 왜냐면 제어이기 때문이다.
					kafkaDevSetupGroup = kafkaDevSetupManager.getDevSetupGroup(transacIdList.get(0));
				    Map<String, KafkaDevSetup> devSetup = kafkaDevSetupGroup.getDevSetup();
					
					List<NewSpotDeviceGw> gwList = masterVo.getSpotDevGwList();
					NewSpotDeviceGw deviceGw = gwList.get(0);
					
					List<NewSpotDevice> spotDevs = deviceGw.getSpotDevs();
					
					if(spotDevs != null && spotDevs.size() > 0) {

						for(NewSpotDevice devs : spotDevs) {
							
							if(devs.getSpotGroupCd().equals(DeviceType.DOOR_LOCK.getCode())) {
								
							    KafkaDevSetup setup = devSetup.get(""+key.getServiceNo()+devs.getSpotDevSeq());
								
							    if(setup != null) {
								    List<KafkaSnsnTag> snsnTagList = setup.getSnsnTagList();
									
									try {
									    for(KafkaSnsnTag kafkaSnsnTag : snsnTagList) {
											
									        if(PushType.DOOR_LOCK_OPEN.getSnsnTagCd().equals(kafkaSnsnTag.getSnsnTagCd()) && "0000000000".equals(kafkaSnsnTag.getRlNumVal())) {
									            // 도어락 열림 푸시
									            this.pushDoorLockOpen(token, key.getServiceNo(), key.getGwUUID(), devs.getSpotDevSeq());
									        }
									    }
									} catch(Exception pushEx) {
										log.error("push send Fail!!!! {}", pushEx.getMessage());
										pushEx.printStackTrace();
									}
							    }
							}
							
							if(devs.getSpotGroupCd().equals(DeviceType.GAS_VALVE.getCode())) {
								
							    KafkaDevSetup setup = devSetup.get(""+key.getServiceNo()+devs.getSpotDevSeq());
								
							    if(setup != null) {
								    List<KafkaSnsnTag> snsnTagList = setup.getSnsnTagList();
									
									try {
									    for(KafkaSnsnTag kafkaSnsnTag : snsnTagList) {
											
									        if(PushType.GAS_VALVE_CLOSE.getSnsnTagCd().equals(kafkaSnsnTag.getSnsnTagCd()) && "00".equals(kafkaSnsnTag.getRlNumVal())){
						                        // 가스밸브 잠금 푸시
						                        this.pushGasValveClose(token, key.getServiceNo(), key.getGwUUID(), devs.getSpotDevSeq());
						                    }
									    }
									} catch(Exception pushEx) {
										log.error("push send Fail!!!! {}", pushEx.getMessage());
										pushEx.printStackTrace();
									}
							    }
							}
							
						}
					}
				}
				
				// kafka 정보 삭제
				for(String transacId : transacIdList) {
					kafkaDevSetupManager.remove(transacId);
				}
			}
		}
		
		masterVo.setModeCode(key.getModeCode());
		
		
		return masterVo;

	}
	
	/**
	 * 게이트웨이 정보 조회(DB조회)
	 * @param token
	 * @param gwUUID
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	private SpotDeviceMasterVo selectSpotGateWayListByDataBase(AuthToken token, long serviceNo, long spotDevSeq) {
		
		SpotDeviceMasterVo masterVo = new SpotDeviceMasterVo();
		
		// 게이트 웨이 먼저 가져온다.
		List<SpotDeviceGw> gateWayList = devicesDao.selectSpotGateWayList(token.getUserNoLong(), token.getDstrCd(), token.getSvcThemeCd(), token.getUnitSvcCd(), serviceNo, spotDevSeq, DeviceType.GATE_WAY.getCode());
		
		// 게이트 웨이 갯수 만큼 게이트웨이에 연결된 현장장치 리스트를 가져온다.
		for(SpotDeviceGw gateway : gateWayList) {
			
			List<SpotDeviceGroup> spotDevGroupList = new ArrayList<SpotDeviceGroup>();
			
			// 장비 그룹별로 조회한다.
			Set<String> keySet = baseMap.keySet();
			for(Iterator<String> it = keySet.iterator(); it.hasNext();) {
				
				String keyNm = it.next();
				
				SpotDeviceGroup spotDeviceGroup = new SpotDeviceGroup();
				spotDeviceGroup.setSpotGroupCd(keyNm);
				spotDeviceGroup.setSpotGroupNm(baseMap.get(keyNm));
				
				List<SpotDevice> spotDevs = null;
				
				spotDevs = devicesDao.selectSpotDevList(keyNm, gateway.getSvcTgtSeq(), gateway.getSpotDevSeq(), null, token.getUserNoLong());
				
				// 가져온 현장장리 리스트만큼 현장장치 상세, 일반설정 데이터를 가져온다.
				int devCnt = 0;
				for(SpotDevice spotDev : spotDevs) {
					// 현장장치 상세
					List<SpotDevDtl> spotDevDtls = devicesDao.selectSpotDevDtl(gateway.getSvcTgtSeq(), spotDev.getSpotDevSeq(), null);
					spotDev.setSpotDevDtls(spotDevDtls);

					// 일반설정/상태 데이터
					List<SpotDeviceSnsnData> genlSetupDatas = devicesDao.selectSpotDevSetupTxn(gateway.getSvcTgtSeq(), spotDev.getSpotDevSeq());
					spotDev.setGenlSetupDatas(genlSetupDatas);
					devCnt++;
				}
				
				// 해당 장비 그룹에 속한 장비가 존재할 경우만 add 한다.
				if(devCnt > 0) {
					spotDeviceGroup.setSpotDevs(spotDevs);
					spotDevGroupList.add(spotDeviceGroup);
				}
			}
			
			if(spotDevGroupList.size() > 0) {
				gateway.setSpotDevGroupList(spotDevGroupList);	
			}
			
		}
		masterVo.setSpotDevGwList(gateWayList);
		
		return masterVo;
	}
	
	/**
	 * 게이트웨이 정보 조회(DB조회)(IFTTT 용)
	 * @param token
	 * @param gwUUID
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	private NewSpotDeviceMasterVo selectSpotGateWayListByDataBase2(AuthToken token, long serviceNo, long spotDevSeq) {
		
		NewSpotDeviceMasterVo masterVo = new NewSpotDeviceMasterVo();
		
		// 게이트 웨이 먼저 가져온다.
		List<NewSpotDeviceGw> gateWayList = devicesDao.selectSpotGateWayList2(token.getUserNoLong(), token.getDstrCd(), token.getSvcThemeCd(), token.getUnitSvcCd(), serviceNo, spotDevSeq, DeviceType.GATE_WAY.getCode());
		
		// 게이트 웨이 갯수 만큼 게이트웨이에 연결된 현장장치 리스트를 가져온다.
		for(NewSpotDeviceGw gateway : gateWayList) {
			
			List<NewSpotDevice> spotDevs = devicesDao.selectSpotDevList2(gateway.getSvcTgtSeq(), gateway.getSpotDevSeq(), null, token.getUserNoLong());
			
			// 가져온 현장장리 리스트만큼 현장장치 상세, 일반설정 데이터를 가져온다.
			for(NewSpotDevice spotDev : spotDevs) {
				// 현장장치 상세
				List<SpotDevDtl> spotDevDtls = devicesDao.selectSpotDevDtl(gateway.getSvcTgtSeq(), spotDev.getSpotDevSeq(), null);
				spotDev.setSpotDevDtls(spotDevDtls);

				// 일반설정/상태 데이터
				List<SpotDeviceSnsnData> genlSetupDatas = devicesDao.selectSpotDevSetupTxn(gateway.getSvcTgtSeq(), spotDev.getSpotDevSeq());
				spotDev.setGenlSetupDatas(genlSetupDatas);
				
				// 그룹 이름 설정
				spotDev.setSpotGroupNm(baseMap.get(spotDev.getSpotGroupCd()));
			}
			
			if(spotDevs.size() > 0) {
				gateway.setSpotDevs(spotDevs);	
			}
			
		}
		masterVo.setSpotDevGwList(gateWayList);
		
		return masterVo;
	}
	
	/**
	 * 디바이스 상태 조회
	 * @param token
	 * @param gwUUID
	 * @return
	 * @throws APIException
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public SpotDeviceMasterVo selectSpotDevStatus(AuthToken token, long serviceNo, long gwUUID, long devUUID) throws APIException {
		
		// 1. kafka 연동
		
		// 2. DB 조회
		// 게이트 웨이 먼저 가져온다.
		List<SpotDeviceGw> gateWayList = devicesDao.selectSpotGateWayList(token.getUserNoLong(), token.getDstrCd(), token.getSvcThemeCd(), token.getUnitSvcCd(), serviceNo, gwUUID, DeviceType.GATE_WAY.getCode());
		
		// 게이트 웨이 갯수 만큼 게이트웨이에 연결된 현장장치 리스트를 가져온다.
		for(SpotDeviceGw gateway : gateWayList) {
			
			List<SpotDeviceGroup> spotDevGroupList = new ArrayList<SpotDeviceGroup>();
			
			Set<String> keySet = baseMap.keySet();
			for(Iterator<String> it = keySet.iterator(); it.hasNext();) {
				
				String keyNm = it.next();
				
				List<SpotDevice> spotDevs = devicesDao.selectSpotDevList(keyNm, gateway.getSvcTgtSeq(), gateway.getSpotDevSeq(), devUUID, token.getUserNoLong());
				
				if(spotDevs != null && spotDevs.size() > 0) {
					
					SpotDeviceGroup spotDeviceGroup = new SpotDeviceGroup();
					spotDeviceGroup.setSpotGroupCd(keyNm);
					spotDeviceGroup.setSpotGroupNm(baseMap.get(keyNm));
				
					// 가져온 현장장리 리스트만큼 현장장치 상세, 일반설정 데이터를 가져온다.
					for(SpotDevice spotDev : spotDevs) {
						// 현장장치 상세
						List<SpotDevDtl> spotDevDtls = devicesDao.selectSpotDevDtl(gateway.getSvcTgtSeq(), spotDev.getSpotDevSeq(), null);
						spotDev.setSpotDevDtls(spotDevDtls);
	
						// 일반설정 데이터
						List<SpotDeviceSnsnData> genlSetupDatas = devicesDao.selectSpotDevSetupTxn(gateway.getSvcTgtSeq(), spotDev.getSpotDevSeq());
						spotDev.setGenlSetupDatas(genlSetupDatas);
					}
					
					spotDeviceGroup.setSpotDevs(spotDevs);
					spotDevGroupList.add(spotDeviceGroup);
				}
			}
			
			gateway.setSpotDevGroupList(spotDevGroupList);
			
		}
		
		SpotDeviceMasterVo masterVo = new SpotDeviceMasterVo();
		masterVo.setSpotDevGwList(gateWayList);
		
		return masterVo;
	}
	
	/**
	 * 디바이스 동작 제어
	 * @param token
	 * @param iotControlKey
	 * @return transacId
	 * @throws APIException
	 */
	public Map<String, Object> spotDeviceAction(AuthToken token, IotControlKey iotControlKey) throws APIException {
		boolean mode = !StringUtils.isEmpty(iotControlKey.getModeCode());
		SpotDevBas gwSpotDevSeqBySvcTgtSeq = devicesDao.selectGwSpotDevSeqBySvcTgtSeq(Long.parseLong(iotControlKey.getSpotDevCnvyDatas().get(0).getSvcTgtSeq()));
		
		/* 제어를 내릴 게이트웨이가 붙어있는 EC서버명을 넣을 수 있도록 하는 로직. ECService(svcTgtSeq, spotDevSeq, spotDevId) 이용 */
		SpotDevBasQueryVO spotDevBasQueryVO = eCService.sendSelectQueryToEC(Long.parseLong(iotControlKey.getSpotDevCnvyDatas().get(0).getSvcTgtSeq())
				, gwSpotDevSeqBySvcTgtSeq.getSpot_dev_seq()
				, gwSpotDevSeqBySvcTgtSeq.getSpot_dev_id());
		
		try {
			if(spotDevBasQueryVO.getSpotDevBasVO().getConnSrvrId() == null || "".equals(spotDevBasQueryVO.getSpotDevBasVO().getConnSrvrId())) {
				
				log.error("GateWay Network Disconnected!!!! : ");

				Map<String, Object> map = new HashMap<String, Object>();
				List<String> transacIdList = null;
				map.put("transacIdList", transacIdList);
				map.put("getCmdYn", null);
				map.put("modeCode", iotControlKey.getModeCode());
				
				return map;
			}
		} catch (NullPointerException ne) {
			
			log.error("GateWay Network Disconnected!!!! : " + ne.getMessage());
			ne.printStackTrace();

			Map<String, Object> map = new HashMap<String, Object>();
			List<String> transacIdList = null;
			map.put("transacIdList", transacIdList);
			map.put("getCmdYn", null);
			map.put("modeCode", iotControlKey.getModeCode());
			
			return map;
		}
		
		int transactionIdMakeCnt = 0;
		for(com.kt.giga.home.openapi.ghms.devices.domain.key.SpotDevCnvyData _spotDevCnvyData : iotControlKey.getSpotDevCnvyDatas()) {
			if("Y".equals(_spotDevCnvyData.getKafkaIfYn())) {
				transactionIdMakeCnt++;
			}
		}
		
		boolean trespsSnsor = false;
		List<String> transacIdList = new ArrayList<String>();
		// 제어 ControlVo 안에 장비 갯수 만큼 transactionId를 만든다.
//		List<String> rtnTransacIdList = kafkaDevSetupManager.create(iotControlKey.getSpotDevCnvyDatas().size(), mode);
//		List<String> rtnTransacIdList = kafkaDevSetupManager.create(transactionIdMakeCnt, mode);
		List<String> rtnTransacIdList = kafkaDevSetupManager.create(iotControlKey.getSpotDevCnvyDatas().size(), transactionIdMakeCnt, mode);
		int rtnTransacIdListIdx = 0;
		for(com.kt.giga.home.openapi.ghms.devices.domain.key.SpotDevCnvyData _spotDevCnvyData : iotControlKey.getSpotDevCnvyDatas()) {
			// 침임감지 여부 변수 초기화
			trespsSnsor = false;

			ItgCnvyData itgCnvyData = new ItgCnvyData();

			itgCnvyData.setUnitSvcCd(CommonConstants.DSTR_CD + CommonConstants.SVC_THEME_CD + CommonConstants.UNIT_SVC_CD);
			
			itgCnvyData.setReqEcSrvrId(spotDevBasQueryVO.getSpotDevBasVO().getConnSrvrId());

			List<SpotDevCnvyData> spotDevCnvyDataList = new ArrayList<SpotDevCnvyData>();

			/* kafka ItgCnvyData 껍데기 생성 시작 */
			SpotDevCnvyData spotDevCnvyData = new SpotDevCnvyData();
			spotDevCnvyData.setSvcTgtSeq(Long.parseLong(_spotDevCnvyData.getSvcTgtSeq()));
			spotDevCnvyData.setSpotDevSeq(Long.parseLong(_spotDevCnvyData.getSpotDevSeq()));
			spotDevCnvyData.setSpotDevId(_spotDevCnvyData.getSpotDevId());
			spotDevCnvyData.setUpSpotDevId(_spotDevCnvyData.getUpSpotDevId());
			
			List<CnvyRow> cnvyRowList = new ArrayList<CnvyRow>();
			
			List<CmdData> cmdDatas = new ArrayList<CmdData>();
			List<BinData> binDatas = new ArrayList<BinData>();
			List<GenlSetupData> genlSetupDatas = new ArrayList<GenlSetupData>();
			List<BinSetupData> binSetupDatas = new ArrayList<BinSetupData>();
			List<ContlData> contlDatas = new ArrayList<ContlData>();
			HashMap<String, Object> rowExtension = new HashMap<String, Object>();
			
			CnvyRow cnvyRow = new CnvyRow();
			cnvyRow.setCmdDatas(cmdDatas);
			cnvyRow.setBinDatas(binDatas);
			cnvyRow.setGenlSetupDatas(genlSetupDatas);
			cnvyRow.setBinSetupDatas(binSetupDatas);
			cnvyRow.setContlDatas(contlDatas);
			cnvyRow.setRowExtension(rowExtension);
			
			cnvyRowList.add(cnvyRow);
			
			spotDevCnvyData.setCnvyRows(cnvyRowList);
			/* kafka ItgCnvyData 껍데기 생성 1차 끝 */
			
			/* kafka ItgCnvyData 껍데기 에 APP에서 받은 ControlVo 값 셋팅 시작 */
			// 장치당 들어오는 샌싱태그 값은 무조건 1개 이다.
			List<CnvyRowData> _cnvyRowDatas = _spotDevCnvyData.getCnvyRowDatas();
			
			CnvyRowData rowData = _cnvyRowDatas.get(0);

			List<CmdData> _cmdDatas = rowData.getCmdDatas();
			List<BinData> _binDatas = rowData.getBinDatas();
			List<GenlSetupData> _genlSetupDatas = rowData.getGenlSetupDatas();
			List<BinSetupData> _binSetupDatas = rowData.getBinSetupDatas();
			List<ContlData> _contlDatas = rowData.getContlDatas();
			HashMap<String,Object> _rowExtension = rowData.getRowExtension();

			if(_cmdDatas != null && _cmdDatas.size() > 0) {
				cmdDatas.add(_cmdDatas.get(0));
			}
			if(_contlDatas != null && _contlDatas.size() > 0) {
				contlDatas.add(_contlDatas.get(0));
			}
			if(_rowExtension != null && !_rowExtension.isEmpty()) {
				Set<String> keySet = _rowExtension.keySet();
				for(Iterator<String> it = keySet.iterator(); it.hasNext();) {
					String keyNm = it.next();
					rowExtension.put(keyNm, _rowExtension.get(keyNm));
				}
			}
			if(_binDatas != null && _binDatas.size() > 0) {
				binDatas.add(_binDatas.get(0));
				
				if(!"Y".equals(_spotDevCnvyData.getKafkaIfYn()) && "99990000".equals(_binDatas.get(0).getSnsnTagCd())) {
					// 침입감지 제어 일 경우 kafka로 전달하지 않고 DB만 업데이트 한다.
					trespsSnsor = true;
					
					Map<String, Object> input = new HashMap<String, Object>();
					input.put("svcTgtSeq", Long.parseLong(_spotDevCnvyData.getSvcTgtSeq()));
					input.put("spotDevSeq", Long.parseLong(_spotDevCnvyData.getSpotDevSeq()));
					input.put("cretrId", "KAFKA");
					input.put("amdrId", "KAFKA");
					input.put("snsnTagCd", _binDatas.get(0).getSnsnTagCd());
					input.put("rlNumVal", com.kt.giga.home.openapi.ghms.util.string.StringUtils.byteArrayToHex(_binDatas.get(0).getBinValTxn()));
					input.put("delYn", 'N');

					int count = devicesDao.selectSpotDevGenlSetupTxnCnt(input);
					if(count > 0){
						devicesDao.updateSpotDevGenlSetupTxn(input);
					}else{
						devicesDao.insertSpotDevGenlSetupTxn(input);
					}
					
					Map<String, Object> value = new HashMap<String, Object>();
					
					ComplexEvent complexEvent = new ComplexEvent();
					complexEvent.setSvcTgtSeq(Long.parseLong(_spotDevCnvyData.getSvcTgtSeq()));
					
					byte on = (byte)0x01;
					byte off = (byte)0x00;
					byte[] binValTxn = _binDatas.get(0).getBinValTxn();
					byte b = binValTxn[0];

					/** 침입감지 on : 001HIT003E0008, off : 001HIT003E0009 */
					if(b == on) {
						complexEvent.setEvtId("001HIT003E0008");
					} else if(b == off) {
						complexEvent.setEvtId("001HIT003E0009");
					}
					
					value.put("99990000", ByteBuffer.wrap(_binDatas.get(0).getBinValTxn()));
					value.put("svcCode", CommonConstants.DSTR_CD + CommonConstants.SVC_THEME_CD + CommonConstants.UNIT_SVC_CD);
					value.put("spotDevSeq", Long.parseLong(_spotDevCnvyData.getSpotDevSeq()));
					
					complexEvent.setItems(value);
					
					kafkaProducer.sendAvro(apiEnv.getProperty("producer.api.topic.complex"), complexEvent);
					
					// 모드 변경이 아닐 경우만(단일건 제어 경우만 푸시 발송)
					if(StringUtils.isEmpty(iotControlKey.getModeCode()) && token != null) {
						// 침임 감지 변경 푸시 발송
						// 현장장치 정보 조회
						List<SpotDevice> spotGwDevList = devicesDao.selectSpotDevList(DeviceType.TRESPS_SENSOR.getCode(), Long.parseLong(_spotDevCnvyData.getSvcTgtSeq())
								, null, Long.parseLong(_spotDevCnvyData.getSpotDevSeq()), token.getUserNoLong());
						if(b == on) {
				            // 침입감지 작동 제어시 푸시
				            this.pushTrespsSensorOn(token, Long.parseLong(_spotDevCnvyData.getSvcTgtSeq()), spotGwDevList.get(0).getUpSpotDevSeq(), Long.parseLong(_spotDevCnvyData.getSpotDevSeq()));
				        } else if(b == off) {
			                // 침입감지 해제 제어시 푸시
				            this.pushTrespsSensorOff(token, Long.parseLong(_spotDevCnvyData.getSvcTgtSeq()), spotGwDevList.get(0).getUpSpotDevSeq(), Long.parseLong(_spotDevCnvyData.getSpotDevSeq()));
			            }	
					}
					
				}
			}
			if(_genlSetupDatas != null && _genlSetupDatas.size() > 0) {
				genlSetupDatas.add(_genlSetupDatas.get(0));
			}


			if(!trespsSnsor) {
				String transacId = rtnTransacIdList.get(rtnTransacIdListIdx++);
				
				/** 가스밸브 시간세팅 시 값을 메모리에 저장하를 로직 추가 */
				if(_binSetupDatas != null && _binSetupDatas.size() > 0) {
					//tag : 82997004, value 저장
					BinSetupData setupData = _binSetupDatas.get(0);
					String tagCd = setupData.getSnsnTagCd();
					byte[] tagVal = setupData.getSetupVal();
					if(!trespsSnsor && "82997004".equals(tagCd)){
						KafkaDevSetupGroup group = kafkaDevSetupManager.get(transacId);
						Map<String, Object> map = new HashMap<>();
						map.put(tagCd, tagVal);
						group.setAttributes(map);
					}
					
					binSetupDatas.add(setupData);
				}
				
				
				itgCnvyData.setTransacId(transacId);
				spotDevCnvyData.setTransacId(transacId+"_"+0);
				spotDevCnvyDataList.add(spotDevCnvyData);
				
				transacIdList.add(transacId);	
				
				itgCnvyData.setSpotDevCnvyDatas(spotDevCnvyDataList);
				
				String json = gson.toJson(itgCnvyData, ItgCnvyData.class);
				
				if(log.isDebugEnabled()) {
					log.debug("json : {}", json);	
				}
				
				kafkaProducer.sendJson(apiEnv.getProperty("producer.api.topic"), KafkaMsgType.CONTL_ITGCNVY_DATA, json);
			}
			/* kafka ItgCnvyData 껍데기 생성 2차 끝 */
			/* kafka ItgCnvyData 껍데기 에 APP에서 받은 ControlVo 값 셋팅 끝 */
			
		}
		
		// 제어 이력 Hbase 남김.
		if(token != null) {

			for(com.kt.giga.home.openapi.ghms.devices.domain.key.SpotDevCnvyData _spotDevCnvyData : iotControlKey.getSpotDevCnvyDatas()) {
				
				// 허브(게이트웨이)경우 이력 남기지 않는다. 게이트 웨이의 spotDevSeq 는 무조건 1
				if(Integer.parseInt(_spotDevCnvyData.getSpotDevSeq()) > 1) {

					HbaseControlHistory hbaseControlHistory = null;
					List<CnvyRowData> _cnvyRowDatas = _spotDevCnvyData.getCnvyRowDatas();
					CnvyRowData rowData = _cnvyRowDatas.get(0);

					List<CmdData> _cmdDatas = rowData.getCmdDatas();
					List<BinData> _binDatas = rowData.getBinDatas();
					List<GenlSetupData> _genlSetupDatas = rowData.getGenlSetupDatas();
					List<BinSetupData> _binSetupDatas = rowData.getBinSetupDatas();

					if(_cmdDatas != null && _cmdDatas.size() > 0) {
						hbaseControlHistory = kafkaSpotDevService.selectHbaseControlHistory(CommonConstants.DSTR_CD + CommonConstants.SVC_THEME_CD + CommonConstants.UNIT_SVC_CD
								, Long.parseLong(_spotDevCnvyData.getSvcTgtSeq())
								, Long.parseLong(_spotDevCnvyData.getSpotDevSeq())
								, _cmdDatas.get(0).getSnsnTagCd()
								, com.kt.giga.home.openapi.ghms.util.string.StringUtils.byteArrayToHex(_cmdDatas.get(0).getCmdValTxn())
								, token.getUserNoLong()
								, rtnTransacIdList.get(0));
						hbaseControlHistoryService.insert(hbaseControlHistory);
					}
					if(_binDatas != null && _binDatas.size() > 0) {
						hbaseControlHistory = kafkaSpotDevService.selectHbaseControlHistory(CommonConstants.DSTR_CD + CommonConstants.SVC_THEME_CD + CommonConstants.UNIT_SVC_CD
								, Long.parseLong(_spotDevCnvyData.getSvcTgtSeq())
								, Long.parseLong(_spotDevCnvyData.getSpotDevSeq())
								, _binDatas.get(0).getSnsnTagCd()
								, com.kt.giga.home.openapi.ghms.util.string.StringUtils.byteArrayToHex(_binDatas.get(0).getBinValTxn())
								, token.getUserNoLong()
								, rtnTransacIdList.get(0));
						hbaseControlHistoryService.insert(hbaseControlHistory);
					}
					if(_genlSetupDatas != null && _genlSetupDatas.size() > 0) {
						hbaseControlHistory = kafkaSpotDevService.selectHbaseControlHistory(CommonConstants.DSTR_CD + CommonConstants.SVC_THEME_CD + CommonConstants.UNIT_SVC_CD
								, Long.parseLong(_spotDevCnvyData.getSvcTgtSeq())
								, Long.parseLong(_spotDevCnvyData.getSpotDevSeq())
								, _genlSetupDatas.get(0).getSnsnTagCd()
								, _genlSetupDatas.get(0).getSetupVal()
								, token.getUserNoLong()
								, rtnTransacIdList.get(0));
						hbaseControlHistoryService.insert(hbaseControlHistory);				
					}
					if(_binSetupDatas != null && _binSetupDatas.size() > 0) {
						hbaseControlHistory = kafkaSpotDevService.selectHbaseControlHistory(CommonConstants.DSTR_CD + CommonConstants.SVC_THEME_CD + CommonConstants.UNIT_SVC_CD
								, Long.parseLong(_spotDevCnvyData.getSvcTgtSeq())
								, Long.parseLong(_spotDevCnvyData.getSpotDevSeq())
								, _binSetupDatas.get(0).getSnsnTagCd()
								, com.kt.giga.home.openapi.ghms.util.string.StringUtils.byteArrayToHex(_binSetupDatas.get(0).getSetupVal())
								, token.getUserNoLong()
								, rtnTransacIdList.get(0));
						String snsnCd = hbaseControlHistory.getContlCd();
						if(!"82997004".equals(snsnCd)){	/** 가스밸브 시간세팅 필터링. */
							hbaseControlHistoryService.insert(hbaseControlHistory);
						}
					}
				}
				
			}
		}
		
		if(!StringUtils.isEmpty(iotControlKey.getModeCode()) && token != null) {
			log.debug("ModeCode 변경 이력 생성 시작");
			// 값이 있다면 모드설정 변경 제어.
			hbaseControlHistoryService.insert(CommonConstants.DSTR_CD + CommonConstants.SVC_THEME_CD + CommonConstants.UNIT_SVC_CD
					, iotControlKey.getSpotDevCnvyDatas().get(0).getSvcTgtSeq()
					, "99990001"
					, iotControlKey.getModeCode()
					, rtnTransacIdList.get(0)
					, token.getUserNo());
			log.debug("ModeCode 변경 이력 생성 끝");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("transacIdList", transacIdList);
		map.put("modeCode", iotControlKey.getModeCode());
		if(transactionIdMakeCnt > 0) {
			map.put("getCmdYn", "Y");
		} else {
			map.put("getCmdYn", "N");	
		}
		
		return map;
	}
	
	/**
	 * 모드 설정 변경
	 * @param token
	 * @param serviceNo
	 * @param modeCode
	 * @throws APIException
	 */
	public BaseVo insertModeStatus(AuthToken token, long serviceNo, String modeCode) throws APIException {

		BaseVo vo = new BaseVo();
		
		try {
			// 모드 코드에 따른 장비별 제어 설정 값 조회
			DeviceModeKey key = new DeviceModeKey();
			key.setModeCode(modeCode);
			key.setServiceNo(serviceNo);
			key.setUserNo(token.getUserNoLong());
			key.setUnitSvcCd(token.getUnitSvcCd());
			key.setSvcThemeCd(token.getSvcThemeCd());
			key.setDstrCd(token.getDstrCd());
			
			// DB에 현재 최신 모드 상태 갱신
			int modeStatus = devicesDao.selectModeStatus(key);
			
			if(modeStatus > 0) {
				devicesDao.updateModeStatus(key);
			} else {
				devicesDao.insertModeStatus(key);
			}
			vo.setResultCode("0");
			
		} catch (Exception ex) {
			log.error("모드 설정 변경 에러");
			ex.printStackTrace();
			vo.setResultCode("-1");
		}
		
		return vo;
		
	}
	
	/**
	 * 모드 상태 코드 조회
	 * @param key
	 * @return String 모드 상태 코드
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public DeviceMasterVo selectModeStatusCode(AuthToken token, String modeCode, long serviceNo) throws APIException {

		DeviceModeKey key = new DeviceModeKey();
		key.setServiceNo(serviceNo);
		key.setModeCode(modeCode);
		key.setUserNo(token.getUserNoLong());
		key.setUnitSvcCd(token.getUnitSvcCd());
		key.setSvcThemeCd(token.getSvcThemeCd());
		key.setDstrCd(token.getDstrCd());
		
		DeviceMasterVo vo = new DeviceMasterVo();
		vo.setSvcTgtSeq(serviceNo);
		
		// modeCode 값이 없을 경우 현재 모드 상태 값만 조회 해옴(메인페이지 제공용)
		if(StringUtils.isEmpty(modeCode)) {
			vo.setModeCode(devicesDao.selectModeStatusCode(key));
		// modeCode 값이 있을 경우 모드별 장비별 장치 설정 값 조회 해옴(모드 편집 화면 제공용)
		} else {
			vo.setModeCode(modeCode);
			
			List<DeviceVo> deviceList = new ArrayList<DeviceVo>();
			
			List<DeviceModeVo> deviceModeVoList = devicesDao.selectDeviceModeStatusCode(key);
			for(DeviceModeVo _vo : deviceModeVoList) {
				
				DeviceVo dvo = new DeviceVo();
				dvo.setDevUUID(String.valueOf(_vo.getSpotDevSeq()));
				dvo.setDevNm(_vo.getDevNm());
				dvo.setDevModelNm(_vo.getDevModelNm());
				dvo.setCntrCode("004");
				dvo.setCntrValue(_vo.getSetupSttusCd());
				
				deviceList.add(dvo);
			}
			
			vo.setDeviceList(deviceList);
		}
		
		return vo;
	}
	
	/**
	 * 모드 상태 편집
	 * @param token
	 * @param key
	 * @throws APIException
	 */
	public BaseVo updateModeStatusSetupTxn(AuthToken token, DeviceMasterKey key) throws APIException {

		BaseVo vo = new BaseVo();
		
		try {
			DeviceModeKey deviceKey = new DeviceModeKey();
			deviceKey.setServiceNo(key.getSvcTgtSeq());
			deviceKey.setUserNo(token.getUserNoLong());
			deviceKey.setModeCode(key.getModeCode());
			deviceKey.setUnitSvcCd(token.getUnitSvcCd());
			deviceKey.setSvcThemeCd(token.getSvcThemeCd());
			deviceKey.setDstrCd(token.getDstrCd());
			
			List<DeviceKey> deviceList = key.getDeviceList();
			
			for(DeviceKey _key : deviceList) {
				deviceKey.setSpotDevSeq(_key.getDevUUID());
                if("004".equals(_key.getCntrCode())) {
                    deviceKey.setSetupSttusCd(_key.getCntrValue());
                }
				
				devicesDao.updateModeStatusSetupTxn(deviceKey);
			}
			
			vo.setResultCode("0");
		} catch (Exception ex) {
			log.error("모드 상태 편집 에러");
			ex.printStackTrace();
			vo.setResultCode("-1");
		}
		
		return vo;
	}
	
	/**
	 * 장치 닉네임 설정
	 * @param serviceNo
	 * @param gwUUID
	 * @param devNm
	 * @return BaseVo
	 * @throws APIException
	 */
	public BaseVo updateSpotDeviceNickName(long mbrSeq, long serviceNo, long spotDevSeq, String devNm) throws APIException {

		BaseVo vo = new BaseVo();
		
		try {
			
			int devNmCount = devicesDao.selectSpotDevNmCount(mbrSeq, devNm, serviceNo, spotDevSeq);
			
			if(devNmCount > 0) {
				vo.setResultCode("-2");
			} else {
				
				ArrayList<SpotDev> spotDev = devicesDao.selectSpotDev(null, null, serviceNo, spotDevSeq);
				
				SpotDev dev = spotDev.get(0);

				devicesDao.updateSpotDeviceNickName(serviceNo, spotDevSeq, devNm);
				
				// AP 상품이 아닐 경우만 EC 이름 변경 한다.
				SvcTgtBas svcTgtBas = ghmsProvisionDao.selectSvcTgtBasBySvcContIdByMac(null, serviceNo, null);
				if(CommonConstants.GHMS_GW_SVC_TGT_TYPE_CD.equals(svcTgtBas.getSvc_tgt_type_cd())) {
					
					SpotDevBasQueryVO spotDevBasQueryVO = eCService.setDevNm(serviceNo, spotDevSeq, dev.getSpotDevId(), devNm);
					
					if(!"100".equals(spotDevBasQueryVO.getRpyCode())) {
						throw new APIException("updateSpotDeviceNickName > ec update Exception", HttpStatus.INTERNAL_SERVER_ERROR);
					}	
				}
				vo.setResultCode("0");
			}
			
			
		} catch (Exception ex) {
			log.error("장치 닉네임 설정 에러 > " + ex.getMessage());
			ex.printStackTrace();
			vo.setResultCode("-1");
		}
		
		return vo;
	}
	
	/**
	 * 디바이스 펌웨어 최신 릴리즈 버전 조회
	 * UCEMS 직접 연동에 의한 버전 정보 조회
	 * 
	 * @param authToken
	 * @param devUUID      디바이스 UUID
	 * @return
	 * @throws APIException
	 */
	public HashMap<String, String> selectUcemsFirmverVersion(AuthToken token, long svcTgtSeq, long spotDevSeq) throws APIException {
	    
	    SpotDev spotDev = selectDevice(token.getUnitSvcCd(), null, svcTgtSeq, spotDevSeq);
	    if(spotDev == null) {
	        throw new APIException(String.format("Device key not found : %s", spotDevSeq), HttpStatus.NOT_FOUND);
	    }
	    
	    // TODO dahye 현재 홈매니져는 정해진 규약이 없기때문에 홈캠에 있는 규약을 그대로 가져옴
	    // 규약이 정해지는 대로 변경해야함.(secret,modelCode 값)
	    String secret = org.apache.commons.lang3.StringUtils.defaultString(spotDev.getAthnNo(), spotDev.getSpotDevDtlVal(SpotDevItemNm.GW_SECRET));
//	    String modelCode = spotDev.getSpotDevDtlVal("gwModelCd");
	    String modelCode = spotDev.getModelNm();
	    
	    log.info("## getUCEMSFirmwareVersion : secret:{}, modelCode:{}", secret, modelCode);
	    String version = ktService.sendFirmVersionCheckRequest(secret, modelCode);
	    
	    HashMap<String, String> resultMap = new HashMap<String, String>();
//	    String version = "1.0.0";
	    resultMap.put("newFrmwrVerNo", version);
//	    resultMap.put("isMaster", token.getUserType());
	    
	    return resultMap;
	}
	
	/**
	 * 디바이스 정보 단일 조회. 상세정보 포함
	 * 
	 * @param unitSvcCd    단위 서비스 코드
	 * @param devUUID      디바이스 UUID
	 * @return             현장 장치 객체
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    private SpotDev selectDevice(String unitSvcCd, String devUUID, Long svcTgtSeq, Long spotDevSeq) {
	    
	    SpotDev spotDev = null;
	    
	    try {
	        ArrayList<SpotDev> spotDevList = devicesDao.selectSpotDev(unitSvcCd, devUUID, svcTgtSeq, spotDevSeq);
	        if(CollectionUtils.isNotEmpty(spotDevList)) {
	            spotDev = spotDevList.get(0);
	            spotDev = selectDeviceDetail(spotDev, spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq());
	        }
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	        log.warn(e.getMessage(), e);
	    }
	    
	    return spotDev;
	}
	
	/**
	 * 디바이스 상세 정보 조회
	 * 
	 * @param spotDev      현장 장치 객체
	 * @param svcTgtSeq    서비스 대상 일련번호
	 * @param spotDevSeq   현장 장치 일련번호
	 * @return             현장 장치 객체
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	private SpotDev selectDeviceDetail(SpotDev spotDev, long svcTgtSeq, long spotDevSeq) {
	    
	    if(spotDev == null) {
	        spotDev = new SpotDev();
	    }
	    
	    ArrayList<SpotDevDtl> spotDevDtlList = devicesDao.selectSpotDevDtl(svcTgtSeq, spotDevSeq, null);
	    spotDev.setSpotDevDtls(spotDevDtlList);
	    
	    return spotDev;
	}    
	
	/**
     * 디바이스 펌웨어 업그레이드 요청 메쏘드
     * gwUUID 가 널이면 사용자에 연결된 전체 디바이스에 대해 업그레이드 요청한다.
     * 요청에 대해 비동기 처리하며 처리 상태는 테이블에 저장한다.
     *
     * @param authToken             로그인 인증 토큰
     * @param gwUUID                게이트웨이 UUID
     * @throws APIException
     */
    @Async
    public void setFirmwareUpgradeControlToUCEMS(AuthToken token, long svcTgtSeq, long spotDevSeq) throws APIException {
        
        String unitSvcCd = token.getUnitSvcCd();

        long mbrSeq = token.getUserNoLong();
        ArrayList<SpotDev> spotDevList = devicesDao.selectMemberSpotDev(unitSvcCd, mbrSeq, null, svcTgtSeq, spotDevSeq);
        for(SpotDev spotDev : spotDevList) {
            setFirmwareUpgradeRequestToUCEMS(unitSvcCd, spotDev.getDevUUID(), svcTgtSeq);
        }
        
    }

    /**
     * 디바이스 펌웨어 업그레이드 요청 메쏘드.
     *
     * @param unitSvcCd             단위 서비스 코드
     * @param gwUUID                게이트웨이 UUID
     * @throws APIException
     */
    private void setFirmwareUpgradeRequestToUCEMS(String unitSvcCd, String devUUID, long serviceNo) throws APIException {
        long svcTgtSeq = 0;
        long spotDevSeq = 0;
        
        try {
            
            SpotDev spotDev = checkDevice(unitSvcCd, devUUID);
            
            svcTgtSeq   = spotDev.getSvcTgtSeq();
            spotDevSeq  = spotDev.getSpotDevSeq();
            
            String secret = org.apache.commons.lang3.StringUtils.defaultIfBlank(spotDev.getAthnNo(), spotDev.getSpotDevDtlVal(SpotDevItemNm.GW_SECRET));
            String mac = spotDev.getSpotDevDtlVal(SpotDevItemNm.GW_MAC);
            String spotDevId = spotDev.getSpotDevId();
            
            // 1. 업그레이드 상태 - 시작
            setDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.UPGRADE_STATUS, SpotDevItemVal.UPGRADE_STATUS_START);
            
            // 2. 업그레이드 요청
            Map<String, String> gwFirmUpRequest = ktService.sendGwFirmUpRequest(secret, mac, spotDevId);
            
            if(gwFirmUpRequest.get("newVersion") != null && !"".equals(gwFirmUpRequest.get("newVersion"))) {
                SpotDevBasKey key = new SpotDevBasKey();
                key.setSvcTgtSeq(svcTgtSeq);
                key.setSpotDevSeq(spotDevSeq);
                key.setFrmwrVerNo(gwFirmUpRequest.get("newVersion"));
                this.updateFrmwrVer(key);
            }
            
            // 3. 업그레이드 시간을 CENS로 전달받을 업그레이드 요청 시작 시간으로 설정
            setFirmwareUpgradeStartTimeToNow(svcTgtSeq, spotDevSeq, gwFirmUpRequest.get("requestDT"));
            
            
        } catch(APIException e) {
            log.warn(e.getMessage(), e);

            // 예외가 발생한 경우 초기값으로 설정
            setDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.UPGRADE_STATUS, SpotDevItemVal.UPGRADE_STATUS_INIT);
            
            throw e;
        }
        
    }

    /**
     * 디바이스 상세 정보를 저장하는 메쏘드
     * 기존에 존재하면 update, 존재하지 않으면 insert
     *
     * @param svcTgtSeq             서비스 대상 일련번호
     * @param spotDevSeq            현장 장치 일련번호
     * @param spotDevItemNm         항목 이름
     * @param spotDevItemVal        항목 값
     */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    private void setDeviceDetailValue(long svcTgtSeq, long spotDevSeq, String spotDevItemNm, String spotDevItemVal) {
        
        try {
            
            int result = devicesDao.updateSpotDevDtl(svcTgtSeq, spotDevSeq, spotDevItemNm, spotDevItemVal);
            
            if(result < 1){
                devicesDao.insertSpotDevDtl(svcTgtSeq, spotDevSeq, spotDevItemNm, spotDevItemVal);
            }
            
        } catch(Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * 디바이스 펌웨어 업그레이드 시작 시간을 현재 시간으로 설정하는 메쏘드
     *
     * @param svcTgtSeq             서비스 대상 일련번호
     * @param spotDevSeq            현장 장치 일련번호
     */
    private void setFirmwareUpgradeStartTimeToNow(long svcTgtSeq, long spotDevSeq, String dateTime) {
        setDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.UPGRADE_START_TIME, dateTime);
    }

    /**
     * 디바이스 정보 단일 조회 메쏘드. 상세정보 포함. 존재하지 않는 경우 Exception 처리
     *
     * @param unitSvcCd             단위 서비스 코드
     * @param gwUUID                게이트웨이 UUID
     * @return                      현장 장치 객체
     */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    private SpotDev checkDevice(String unitSvcCd, String devUUID) throws APIException {
        
        SpotDev spotDev = selectDevice(unitSvcCd, devUUID, null, null);
        
        if(spotDev == null) {
            throw new APIException(String.format("Device not found : %s", devUUID), HttpStatus.NOT_FOUND);
        }
        
        return spotDev;
    }
    
    /**
     * PUSH 전송
     * @param svcTgtSeq     서비스대상일련번호
     * @param data          푸시전송데이터
     * @param type          push보낼 대상(M:마스터, S:슬레이브, MS:전체)
     * @throws APIException
     */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    public void pushSend(AuthToken token, long svcTgtSeq, Map<String, String> data, String type) throws APIException {
        
        PushInfoRequest req = new PushInfoRequest();
        
        if(type != null && !"".equals(type)) {
            if("M".equals(type)) {
                String pnsRegId = kPNSService.selectMasterPnsRegId(svcTgtSeq);
                req.setRegistrationId(pnsRegId);
                req.setData(data);
                kPNSService.push(req);
            } else {
                List<String> pnsRegIdList = null;
                
                if("S".equals(type)) {
                    pnsRegIdList = kPNSService.selectSlavePnsRegId(svcTgtSeq);
                } else {
                    pnsRegIdList = kPNSService.selectMasterAndSlavePnsRegId(svcTgtSeq);
                }
                
                for(String pnsRegId : pnsRegIdList) {
                    req.setRegistrationId(pnsRegId);
                    req.setData(data);
                    
                    kPNSService.push(req);
                }
            }
        } else {

        	if(token != null) {
                List<String> pnsRegIdList = kPNSService.selectExcludeMyInfoPnsRegId(token, svcTgtSeq);
                
                for(String pnsRegId : pnsRegIdList) {
                    req.setRegistrationId(pnsRegId);
                    req.setData(data);
                    
                    kPNSService.push(req);
                }	
        	}
        }
        
    }
    
    /**
     * 도어락 열림 PUSH
     * @param svcTgtSeq     서비스대상일련번호
     * @param spotDevSeq    현장장치일련번호
     */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    private void pushDoorLockOpen(AuthToken token, long svcTgtSeq, long spotDevSeq, long deviceSeq) throws APIException {
        
        String devNm = devicesDao.selectDevNmFromSpotDevBas(svcTgtSeq, deviceSeq);
        
        MasterUserKey key = new MasterUserKey();
        key.setUserNo(token.getUserNoLong());
        String nickNm = masterDao.selectUserNickName(key);
        
        Map<String, String> data = new HashMap<String, String>();
        data.put("eventId", PushType.DOOR_LOCK_OPEN.getEventId());
        data.put("spotDevSeq", String.valueOf(spotDevSeq));
        data.put("svcTgtSeq", String.valueOf(svcTgtSeq));
        data.put("deviceSeq", String.valueOf(deviceSeq));
        data.put("devNm", devNm);
        data.put("msg", "사용자 \"" + nickNm + "\"이/가 도어락 \"" + devNm + "\"을/를 열었습니다.");
        
//        pushSend(svcTgtSeq, data, "MS");
        pushSend(token, svcTgtSeq, data, null);
    }
    
    /**
     * 침입감지 작동 제어시 PUSH(제어알림설정 ON시)
     * @param svcTgtSeq
     * @param spotDevSeq
     * @throws APIException
     */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    private void pushTrespsSensorOn(AuthToken token, long svcTgtSeq, long spotDevSeq, long deviceSeq) throws APIException {
        
        String devNm = devicesDao.selectDevNmFromSpotDevBas(svcTgtSeq, deviceSeq);
        
        Map<String, String> data = new HashMap<String, String>();
        data.put("eventId", PushType.TRESPS_SENSOR_ON.getEventId());
        data.put("spotDevSeq", String.valueOf(spotDevSeq));
        data.put("svcTgtSeq", String.valueOf(svcTgtSeq));
        data.put("deviceSeq", String.valueOf(deviceSeq));
        data.put("devNm", devNm);
        data.put("msg", "침입감지센서 \"" + devNm + "\" 경계를 시작하였습니다.");
        
//        pushSend(svcTgtSeq, data, "MS");
        pushSend(token, svcTgtSeq, data, null);
    }
    
    /**
     * 침입감지 해제 제어시 PUSH(제어알림설정 OFF시)
     * @param svcTgtSeq
     * @param spotDevSeq
     * @throws APIException
     */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    private void pushTrespsSensorOff(AuthToken token, long svcTgtSeq, long spotDevSeq, long deviceSeq) throws APIException {
        
        String devNm = devicesDao.selectDevNmFromSpotDevBas(svcTgtSeq, deviceSeq);
        
        Map<String, String> data = new HashMap<String, String>();
        data.put("eventId", PushType.TRESPS_SENSOR_OFF.getEventId());
        data.put("spotDevSeq", String.valueOf(spotDevSeq));
        data.put("svcTgtSeq", String.valueOf(svcTgtSeq));
        data.put("deviceSeq", String.valueOf(deviceSeq));
        data.put("devNm", devNm);
        data.put("msg", "침입감지센서 \"" + devNm + "\" 경계가 종료되었습니다.");
        
//        pushSend(svcTgtSeq, data, "MS");
        pushSend(token, svcTgtSeq, data, null);
    }
    
    /**
     * 가스밸브 잠금 제어시 PUSH
     * @param svcTgtSeq
     * @param spotDevSeq
     * @throws APIException
     */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    private void pushGasValveClose(AuthToken token, long svcTgtSeq, long spotDevSeq, long deviceSeq) throws APIException {
        
        String devNm = devicesDao.selectDevNmFromSpotDevBas(svcTgtSeq, deviceSeq);
        
        Map<String, String> data = new HashMap<String, String>();
        data.put("eventId", PushType.GAS_VALVE_CLOSE.getEventId());
        data.put("spotDevSeq", String.valueOf(spotDevSeq));
        data.put("svcTgtSeq", String.valueOf(svcTgtSeq));
        data.put("deviceSeq", String.valueOf(deviceSeq));
        data.put("devNm", devNm);
        data.put("msg", "가스밸브 \"" + devNm + "\"을/를 잠갔습니다.");
        
//        pushSend(svcTgtSeq, data, "MS");
        pushSend(token, svcTgtSeq, data, null);
        
    }
	
	/**
	 * 장치 비밀번호 등록
	 * @param svcTgtSeq
	 * @param spotDevSeq
	 * @param pwd
	 * @param userCdSeq
	 */
	public void insertUpdateMbrPwdTnx(long svcTgtSeq, long spotDevSeq, String pwd, int userCdSeq) {
		
		int countMbrPwdTnx = devicesDao.selectCountMbrPwdTnx(svcTgtSeq, spotDevSeq, userCdSeq);
		
		if(countMbrPwdTnx > 0) {
			devicesDao.updateMbrPwdTnx(svcTgtSeq, spotDevSeq, pwd, userCdSeq);
		} else {
			devicesDao.insertMbrPwdTnx(svcTgtSeq, spotDevSeq, pwd, userCdSeq);
		}
	}
    
    /**
     * 현장장치기본 펌웨어 DB 업데이트
     * @param key
     * @return
     * @throws Exception
     */
    private boolean updateFrmwrVer(SpotDevBasKey key) throws APIException {
        
        int upChk = initDeviceAddDao.updateFrmwrVerNoFromSpotDevBas(key);
        
        // 마스터에게 업데이트 종료 푸시 발송
        String devNm = devicesDao.selectGWDevNmFromSpotDevBas(key.getSvcTgtSeq());
        
        Map<String, String> data = new HashMap<String, String>();
        data.put("devNm", devNm);
        data.put("eventId", PushType.FRMWR_VER_COMPLETE.getEventId());
        data.put("msg", "홈매니저 허브 \"" + key.getDevNm() + "\"\n업데이트를 종료하였습니다.");
        
        log.debug("push data:{}", data.toString());
        
        // Master PNS등록아이디 조회
        String pnsRegId = kPNSService.selectMasterPnsRegId(key.getSvcTgtSeq());
        
        PushInfoRequest req = new PushInfoRequest();
        req.setRegistrationId(pnsRegId);
        req.setData(data);
        
        // PUSH 전송
        kPNSService.push(req);
        
        return upChk > 0 ? true : false;
    }

}
