/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.checker.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.kt.giga.home.openapi.ghms.checker.dao.SmartCheckerDao;
import com.kt.giga.home.openapi.ghms.checker.domain.key.GetIoTEquipConnInfo;
import com.kt.giga.home.openapi.ghms.checker.domain.key.TimeSettingKey;
import com.kt.giga.home.openapi.ghms.checker.domain.vo.GetIoTEquipConnInfoResponse;
import com.kt.giga.home.openapi.ghms.checker.domain.vo.GetIoTEquipConnInfoResult;
import com.kt.giga.home.openapi.ghms.checker.domain.vo.InternetAccessRule;
import com.kt.giga.home.openapi.ghms.checker.domain.vo.IoTDeviceInfo;
import com.kt.giga.home.openapi.ghms.checker.domain.vo.SmartChkResultVo;
import com.kt.giga.home.openapi.ghms.checker.domain.vo.TimeSettingVo;
import com.kt.giga.home.openapi.ghms.common.DeviceType;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.ErrorCode;
import com.kt.giga.home.openapi.ghms.common.token.AuthToken;
import com.kt.giga.home.openapi.ghms.devices.dao.DevicesDao;
import com.kt.giga.home.openapi.ghms.devices.dao.DevicesUpgradeDao;
import com.kt.giga.home.openapi.ghms.devices.dao.InitDeviceAddDao;
import com.kt.giga.home.openapi.ghms.devices.domain.key.SpotDevBasKey;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.Device;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.DeviceGw;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.SpotDevice;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.SpotDeviceGw;
import com.kt.giga.home.openapi.ghms.kafka.KafkaDevSetupManager;
import com.kt.giga.home.openapi.ghms.kafka.KafkaProducer;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaDevSetupGroup;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.CmdData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevKey;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevRetv;
import com.kt.giga.home.openapi.ghms.kafka.type.KafkaMsgType;
import com.kt.giga.home.openapi.ghms.ktInfra.dao.GhmsProvisionDao;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetInternetAccessControlResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetInternetSAIDResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetNASStateInfoResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetPCOnOffStateResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetServiceEquipInfoResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.HoldingEquipInfo;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.NetworkStatusInfo;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SetInternetAccessControlRecoveryResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SetInternetAccessControlResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SetWakeOnLanResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SpotDevBas;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SpotDevExpnsnBas;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SvcTgtBas;
import com.kt.giga.home.openapi.ghms.ktInfra.service.KTInfraService;
import com.kt.giga.home.openapi.ghms.user.dao.MasterDao;
import com.kt.giga.home.openapi.ghms.user.dao.UserDao;
import com.kt.giga.home.openapi.ghms.user.domain.key.SvcEtsRelKey;
import com.kt.giga.home.openapi.ghms.user.domain.vo.UserVo;
import com.kt.giga.home.openapi.ghms.util.date.DateTimeUtil;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;
import com.kt.giga.home.openapi.ghms.util.properties.APIEnv;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 20.
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
public class SmartCheckerService {

	/** Logger */
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private KTInfraService ktIntraService;
	
	@Autowired
	private SmartCheckerDao smartCheckerDao;
	
	@Autowired
	private DevicesUpgradeDao devicesUpgradeDao;
	
	@Autowired
	private InitDeviceAddDao initDeviceAddDao;
	
	@Autowired
	private GhmsProvisionDao ghmsProvisionDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MasterDao masterDao;
	
	@Autowired
	private DevicesDao devicesDao;
	
	@Autowired
	private KafkaDevSetupManager kafkaDevSetupManager;
	
	@Autowired
	private KafkaProducer kafkaProducer;
	
	@Autowired
	private APIEnv apiEnv;
	
	private Map<String, String> baseMap;
	
	public Gson gson = new Gson();
	
	public SmartCheckerService() {
	    
		// 현장장치 그룹별 setting.
		baseMap = new HashMap<String, String>();
		baseMap.put(DeviceType.DOOR_LOCK.getCode(), DeviceType.DOOR_LOCK.getGroupNm());
		baseMap.put(DeviceType.GAS_VALVE.getCode(), DeviceType.GAS_VALVE.getGroupNm());
		baseMap.put(DeviceType.TRESPS_SENSOR.getCode(), DeviceType.TRESPS_SENSOR.getGroupNm());
		baseMap.put(DeviceType.UNKNOWN.getCode(), DeviceType.UNKNOWN.getGroupNm());
	}

	/**
	 * 인터넷 시간 제한 설정 조회
	 * @param mbrId
	 * @param svcTgtSeq
	 * @param apMacAddress
	 * @param hostMacAddress
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public TimeSettingVo getInternetAccessControl(AuthToken token, String svcTgtSeq, String apMacAddress, String hostMacAddress) throws APIException {
		
		GetInternetAccessControlResult internetAccessControlResult = ktIntraService.getInternetAccessControl(token.getMbrId(), token.getDeviceId(), apMacAddress, hostMacAddress);
		
		TimeSettingVo vo = new TimeSettingVo();
		
		vo.setResultCode(internetAccessControlResult.getResultCode());
		vo.setResultMessage(internetAccessControlResult.getResultMessage());
		vo.setGigaAPMac(apMacAddress);
		vo.setInternetAccessControlEnable(internetAccessControlResult.getInternetAccessControlEnable());
		vo.setInternetAccessRules(internetAccessControlResult.getInternetAccessRules());
		
		// 현재시간 포함여부 셋팅
		String currentDay = DateTimeUtil.getEnglishDayOfWeek();
		int currentTime = Integer.parseInt(DateTimeUtil.getHour() + DateTimeUtil.getMinute());
		if(vo.getInternetAccessRules() != null) {
			for(InternetAccessRule rule : vo.getInternetAccessRules()) {
				if(rule.getInternetAccessDate() != null) {
					if(rule.getInternetAccessDate().indexOf(currentDay) > -1 || "EVERY".equals(rule.getInternetAccessDate())) {
						
						int startTime = Integer.parseInt(rule.getInternetAccessStartTime().replaceAll(":", ""));
						int endTime = Integer.parseInt(rule.getInternetAccessEndTime().replaceAll(":", ""));
						
						if(startTime == 0 &&  endTime == 0) {
							// 00:00 ~ 00:00 까지 case
							rule.setCurrentEnable(true);	
						} else if(startTime <= currentTime && currentTime <= endTime) {
							rule.setCurrentEnable(true);
						} else {
							rule.setCurrentEnable(false);
						}
						
					} else {
						rule.setCurrentEnable(false);
					}
				} else {
					rule.setCurrentEnable(false);
				}
			}	
		}
		
		return vo;
	}

	/**
	 * 인터넷 시간 제한 설정
	 * @param token
	 * @param key
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public SmartChkResultVo setInternetAccessControl(AuthToken token, TimeSettingKey key) throws APIException {
		
		SetInternetAccessControlResult setInternetAccessControl = ktIntraService.setInternetAccessControl(token.getMbrId(), token.getDeviceId(), key);
		
		SmartChkResultVo vo = new SmartChkResultVo();
		vo.setResultCode("T".equals(setInternetAccessControl.getResultCode()) ? "0" : "-1");
		vo.setResultMessage(setInternetAccessControl.getResultMessage());
		
		return vo;
	}

	/**
	 * 인터넷 시간 제한 복구
	 * @param token
	 * @param key
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public SmartChkResultVo setInternetAccessControlRecovery(AuthToken token, String apMacAddress) throws APIException {
		
		SetInternetAccessControlRecoveryResult setInternetAccessControlRecovery = ktIntraService.setInternetAccessControlRecovery(token.getMbrId(), token.getDeviceId(), apMacAddress);
		
		SmartChkResultVo vo = new SmartChkResultVo();
		vo.setResultCode("T".equals(setInternetAccessControlRecovery.getResultCode()) ? "0" : "-1");
		vo.setResultMessage(setInternetAccessControlRecovery.getResultMessage());
		
		return vo;
	}

	/**
	 * NAS 상태 조회
	 * @param token
	 * @param key
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public SmartChkResultVo getNASStateInfo(AuthToken token, String apMacAddress) throws APIException {
		
		GetNASStateInfoResult getNASStateInfoResult = ktIntraService.getNASStateInfo(token.getMbrId(), token.getDeviceId(), apMacAddress);
		
		SmartChkResultVo vo = new SmartChkResultVo();
		vo.setResultCode("T".equals(getNASStateInfoResult.getResultCode()) ? "0" : "-1");
		vo.setResultMessage(getNASStateInfoResult.getResultMessage());
		vo.setEnable(getNASStateInfoResult.getEnable());
		
		return vo;
	}

	/**
	 * PC 상태 조회
	 * @param token
	 * @param key
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public SmartChkResultVo getPCOnOffState(AuthToken token, String apMacAddress, String pcMacAddress) throws APIException {
		
		GetPCOnOffStateResult getPCOnOffStateResult = ktIntraService.getPCOnOffState(token.getMbrId(), token.getDeviceId(), apMacAddress, pcMacAddress);
		
		SmartChkResultVo vo = new SmartChkResultVo();
		vo.setResultCode("T".equals(getPCOnOffStateResult.getResultCode()) ? "0" : "-1");
		vo.setResultMessage(getPCOnOffStateResult.getResultMessage());
		vo.setOnOff(getPCOnOffStateResult.getOnOff());
		
		return vo;
	}

	/**
	 * PC 원격 켜기
	 * @param token
	 * @param key
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public SmartChkResultVo setWakeOnLan(AuthToken token, String apMacAddress, String pcMacAddress) throws APIException {
		
		SetWakeOnLanResult setWakeOnLanResult = ktIntraService.setWakeOnLan(token.getMbrId(), token.getDeviceId(), apMacAddress, pcMacAddress);
		
		SmartChkResultVo vo = new SmartChkResultVo();
		vo.setResultCode("T".equals(setWakeOnLanResult.getResultCode()) ? "0" : "-1");
		vo.setResultMessage(setWakeOnLanResult.getResultMessage());
		
		return vo;
	}

	/**
	 * 관리단말 추가
	 * @param token
	 * @param key
	 * @return
	 */
	public SmartChkResultVo setDeviceManager(AuthToken token, TimeSettingKey key) throws APIException {
		
		// spotDevSeq 값은 AP의 정보이기 때문에 잠시 빼둔다.
		Long spotDevSeq = key.getSpotDevSeq();
		key.setSpotDevSeq(null);
		Device device = devicesUpgradeDao.selectSpotDevBasByDevMac(key);
		key.setSpotDevSeq(spotDevSeq);
		
		SmartChkResultVo vo = new SmartChkResultVo();
		
		try {
			
			if(device == null) {

	            SpotDevBasKey skey = new SpotDevBasKey();
	            skey.setSvcTgtSeq(key.getSvcTgtSeq());
	            
	            if("001".equals(key.getType())) {
		            skey.setDevTypeCd(DeviceType.KT_TIME_SVC.getCode());
		            skey.setDevModelCd(DeviceType.KT_TIME_SVC.getGroupNm());
	            } else if("002".equals(key.getType())) {
		            skey.setDevTypeCd(DeviceType.KT_PC_SVC.getCode());
		            skey.setDevModelCd(DeviceType.KT_PC_SVC.getGroupNm());
	            } else {
		            skey.setDevTypeCd(DeviceType.KT_TIME_SVC.getCode());
		            skey.setDevModelCd(DeviceType.KT_TIME_SVC.getGroupNm());
	            }
	            
	        	skey.setSpotDevId("A_" + key.getHostMacAddress().replaceAll(":", ""));
	        	skey.setDevNm(key.getDevNm());
	        	skey.setSvcTgtTypeCd(CommonConstants.GHMS_AP_SVC_TGT_TYPE_CD);
	        	skey.setUpSvcTgtSeq(key.getSvcTgtSeq());
	        	skey.setUpSpotDevSeq(key.getSpotDevSeq());
	        	
	        	// svcTgtSeq + spotDevId 존재여부 확인
	        	SpotDevBas spotDevBas = initDeviceAddDao.selectSpotDevBas(skey);
	        	
	        	if(spotDevBas == null) {
		        	skey.setDevUUID(UUID.randomUUID().toString());
		        	initDeviceAddDao.insertSpotDevBas(skey);	

		            SpotDevExpnsnBas sdeb = new SpotDevExpnsnBas();
		            sdeb.setSvc_tgt_seq(key.getSvcTgtSeq());   // 현장장치항목값 
		            sdeb.setSpot_dev_seq(skey.getSpotDevSeq()); // 현장장치값
		            sdeb.setSpot_dev_item_nm("devMac");
		            
		            String mac = key.getHostMacAddress();
					if(mac.length() == 12 && mac.indexOf(":") == -1) {
						mac = mac.substring(0, 2) + ":"
								+ mac.substring(2, 4) + ":"
								+ mac.substring(4, 6) + ":"
								+ mac.substring(6, 8) + ":"
								+ mac.substring(8, 10) + ":"
								+ mac.substring(10, 12);
					}
					
		            sdeb.setSpot_dev_item_val(mac);
		        	ghmsProvisionDao.insertSpotDevExpnsnBas(sdeb);
	        	} else {
	        		skey.setSpotDevSeq(spotDevBas.getSpot_dev_seq());
	                initDeviceAddDao.updateSpotDevBas(skey);
	        	}

				vo.setResultCode("0");
			} else {
				vo.setResultCode("-1");
				vo.setResultMessage("이미 관리 되는 장비.");				
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
			vo.setResultCode("-1");
			vo.setResultMessage(ex.getMessage());
		}
		
		return vo;
	}

	/**
	 * 관리단말 삭제
	 * @param token
	 * @param svcTgtSeq
	 * @param spotDevSeq
	 * @param hostMacAddress
	 * @return
	 */
	public SmartChkResultVo deleteDeviceManager(AuthToken token, Long svcTgtSeq, Long spotDevSeq, String hostMacAddress) {
		
		TimeSettingKey key = new TimeSettingKey();
		key.setSvcTgtSeq(svcTgtSeq);
		key.setSpotDevSeq(spotDevSeq);
		key.setHostMacAddress(hostMacAddress);
		
		Device device = devicesUpgradeDao.selectSpotDevBasByDevMac(key);
		
		SmartChkResultVo vo = new SmartChkResultVo();
		
		try {
			
			if(device != null) {
				
				SpotDevBasKey skey = new SpotDevBasKey();
				skey.setSvcTgtSeq(svcTgtSeq);
				skey.setSpotDevSeq(spotDevSeq);
				initDeviceAddDao.deleteSpotDevBas(skey);

				vo.setResultCode("0");
				
			} else {
				vo.setResultCode("-1");
				vo.setResultMessage("관리 단말 장비 존재하지 않음.");	
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
			vo.setResultCode("-1");
			vo.setResultMessage(ex.getMessage());
		}

		return vo;
	}

	/**
	 * @param param
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public GetIoTEquipConnInfoResult getIoTEquipConnInfo(GetIoTEquipConnInfo equipKey) {
		
		GetIoTEquipConnInfoResult result = new GetIoTEquipConnInfoResult();
		List<String> transacIdList = new ArrayList<String>();
		result.setResultCode("T");
		result.setResultMessage("성공");
		result.setTransacIdList(transacIdList);
		
		DeviceGw deviceGw = smartCheckerDao.getIotInfoSvcContId(equipKey.getInValue(), CommonConstants.GHMS_GW_SVC_TGT_TYPE_CD);
		
		if(deviceGw == null) {
			result.setResultCode("F");
			result.setResultMessage("HOME IoT허브  정보 없음.");
			
			return result;
		}
		
		long serviceNo = deviceGw.getSvcTgtSeq();
		long spotDevSeq = deviceGw.getSpotDevSeq();

		// 게이트 웨이 먼저 가져온다.
		List<SpotDeviceGw> gateWayList = devicesDao.selectSpotGateWayList((long)0, CommonConstants.DSTR_CD, CommonConstants.SVC_THEME_CD, CommonConstants.UNIT_SVC_CD, serviceNo, spotDevSeq, DeviceType.GATE_WAY.getCode());
		
		if(gateWayList == null || gateWayList.size() == 0) {
			result.setResultCode("F");
			result.setResultMessage("HOME IoT 허브 정보 없음.");
			
			return result;
		}

		int sendCount = 0;
		// 게이트 웨이 갯수 만큼 게이트웨이에 연결된 현장장치 리스트를 가져온다.
		for(SpotDeviceGw gateway : gateWayList) {

			// 장비 그룹별로 조회한다.
			Set<String> keySet = baseMap.keySet();
			for(Iterator<String> it = keySet.iterator(); it.hasNext();) {
				
				String keyNm = it.next();

				if(!DeviceType.TRESPS_SENSOR.getCode().equals(keyNm) && CommonConstants.GHMS_GW_SVC_TGT_TYPE_CD.equals(gateway.getSvcTgtTypeCd())) { // 침입감지센서는 해당되지 않음. 홈매니저(허브) 상품일 경우만
					List<SpotDevice> spotDevs = devicesDao.selectSpotDevList(keyNm, gateway.getSvcTgtSeq(), gateway.getSpotDevSeq(), null, (long)0);

					if(spotDevs != null && spotDevs.size() > 0) {
						// 가져온 현장장리 리스트(현장장치)만큼 샌싱 태그를 싫어서 kafka 에 전송 한다.
						List<String> list = kafkaDevSetupManager.create(spotDevs.size(), spotDevs.size(), false); // 단말 개수
						int i = 0;
						for(SpotDevice spotDev : spotDevs) {

							// kafka 연동
							SpotDevRetv spotDevRetv = new SpotDevRetv();

//							포함현장장치
							List<SpotDevKey> inclSpotDevKeys = new ArrayList<SpotDevKey>();
//							명령어데이터리스트(31)
							List<CmdData> cmdDatas = new ArrayList<CmdData>();
							
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
							
							String transactionId = list.get(i++);
							spotDevRetv.setTransacId(transactionId);
							
							String json = gson.toJson(spotDevRetv, SpotDevRetv.class);
							
							// kafka send!!!
							kafkaProducer.sendJson(apiEnv.getProperty("producer.api.topic"), KafkaMsgType.INITA_DEV_RETV_EXTR, json);

							transacIdList.add(transactionId);
							
							sendCount++;
						}
					}
				}
			}
		}
		if(sendCount < 1) {
			result.setResultCode("F");
			result.setResultMessage("HOME IoT 허브 하위 단말 정보 없음.");
			
			return result;
		}
		
		return result;
	}

	/**
	 * @param param
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public GetIoTEquipConnInfoResponse getIoTEquipConnInfoResponse(GetIoTEquipConnInfo equipKey) {
		
		GetIoTEquipConnInfoResponse result = new GetIoTEquipConnInfoResponse();
		result.setResultCode("T");
		result.setResultMessage("성공");
		
		DeviceGw deviceGw = smartCheckerDao.getIotInfoSvcContId(equipKey.getInValue(), CommonConstants.GHMS_GW_SVC_TGT_TYPE_CD);
		
		if(deviceGw == null) {
			result.setResultCode("F");
			result.setResultMessage("HOME IoT 허브 정보 없음.");
			
			return result;
		}
		
		long serviceNo = deviceGw.getSvcTgtSeq();
		long spotDevSeq = deviceGw.getSpotDevSeq();

		// 게이트 웨이 먼저 가져온다.
		List<SpotDeviceGw> gateWayList = devicesDao.selectSpotGateWayList((long)0, CommonConstants.DSTR_CD, CommonConstants.SVC_THEME_CD, CommonConstants.UNIT_SVC_CD, serviceNo, spotDevSeq, DeviceType.GATE_WAY.getCode());
		
		if(gateWayList == null || gateWayList.size() == 0) {
			result.setResultCode("F");
			result.setResultMessage("HOME IoT 허브 정보 없음.");
			
			return result;
		}
		boolean isFull = true;
		
		// kafka receive!!!
		List<String> transacIdList = equipKey.getTransacIdList();
		KafkaDevSetupGroup kafkaDevSetupGroup = null;
		for(String transacId : transacIdList) {
			
			kafkaDevSetupGroup = kafkaDevSetupManager.getDevSetupGroup(transacId);
			if(!kafkaDevSetupGroup.isFull()) {
				isFull = false;
				
				break;
			}
		}

		log.info("############### isFull : " + isFull + " #######################");
		
		if(!isFull) {
			result.setResultCode("F");
			result.setResultMessage("연결상태 정보 수신 되지 않음. 잠시 후 다시 시도 요청 바람.");
			
			return result;
		} else {

			SpotDevBasKey key = new SpotDevBasKey();
			key.setSvcTgtSeq(serviceNo);
			key.setSpotDevSeq(spotDevSeq);
			List<IoTDeviceInfo> iotDeviceInfos = smartCheckerDao.getIotDeviceInfo(key);
			
			result.setIoTDeviceInfos(iotDeviceInfos);
		}
		
		return result;
	}

	/**
	 * AP 맥 등록 (청약 생성)
	 * 1. 체커를 통해 MAC의 SA_ID 값을 가져온다.
	 * 2. SDP를 통해 AP(허브) 상품 부가상품인 SA_ID 값을 가져온다.
	 * 3. 위 두 SA_ID까 일치 할 경우 청약을 생성한다. 
	 * @param token
	 * @param apMacAddress
	 * @return
	 */
	public SmartChkResultVo insertService(AuthToken token, String apMacAddress) throws APIException {
		
		SmartChkResultVo vo = new SmartChkResultVo();
		vo.setResultCode("0");
		vo.setResultMessage("성공");
		
		// 소문자로 올수 있으므로 대문자로 변환
		apMacAddress = apMacAddress.toUpperCase();
		
//		1. 체커를 통해 MAC의 SA_ID 값을 가져온다.
		String internetSAID = "";
		GetInternetSAIDResult getInternetSAIDResult = ktIntraService.getInternetSAID(token.getMbrId(), token.getDeviceId(), apMacAddress);
		
		if("T".equals(getInternetSAIDResult.getResultCode())) {
			internetSAID = getInternetSAIDResult.getInternetSAID();

			boolean chk = false;
	        String ktinfraSdpUseyn = apiEnv.getProperty("ktinfra.sdp.useyn");
			if("Y".equals(ktinfraSdpUseyn)) {

//				2. SDP를 통해 AP(허브) 상품 부가상품인 SA_ID 값을 가져온다.
				UserVo userBase = userDao.getUserBase(token.getMbrId());
				UserVo user = null;
				List<String> apSvcIdList = new ArrayList<String>();
				try {
					// OIF_138 호출
					user = ktIntraService.sendSubsRequestUpgrade(token.getMbrId(), userBase.getCredentialId());
		        	
				} catch(Exception e) {
					throw new APIException("SDP Exception (Subscription)", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_NETWWORK_ERROR);
				}
				
				try {
					
					String[] icisSaIdList = user.getIcisSaIdList();
					if(ArrayUtils.isEmpty(icisSaIdList)) {
						for(String icisSaId : icisSaIdList) {
		        			// OIF_114 호출
		        			List<String> apSaIdList = ktIntraService.sendSpecificSubscpnInfo(icisSaId);
		        			if(apSaIdList != null && !apSaIdList.isEmpty()) {
		        				
		        				// 혹시 몰라 중복 제거
		        				for(String apSaId : apSaIdList) {
		        					if(!apSvcIdList.contains(apSaId)) {
		        						apSvcIdList.add(apSaId);
		        					}
		        				}
		        			}	
						}
					}
		        	
				} catch(Exception e) {
					throw new APIException("SDP Exception (SpecificSubscpnInfo)", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_NETWWORK_ERROR);
				}
				
				for(String apSaId : apSvcIdList) {
					if(internetSAID.equals(apSaId)) {
						chk = true;
						break;
					}
				}
			}
			
			// QAT를 위한 임시 로직
			List<String> macList = new ArrayList<String>();
			macList.add("08:5D:DD:00:09:C8");
			macList.add("08:5D:DD:00:09:CD");
			macList.add("08:5D:DD:00:09:D2");
			macList.add("08:5D:DD:00:09:D7");
			macList.add("08:5D:DD:00:09:DC");
			macList.add("08:5D:DD:00:09:E1");
			macList.add("08:5D:DD:00:09:E6");
			macList.add("08:5D:DD:00:09:EB");
			macList.add("08:5D:DD:00:09:F0");
			macList.add("08:5D:DD:00:09:F5");
			macList.add("08:5D:DD:00:09:FA");
			macList.add("08:5D:DD:00:09:FF");
			macList.add("08:5D:DD:00:0A:04");
			macList.add("08:5D:DD:00:0A:09");
			macList.add("08:5D:DD:00:0A:0E");
			macList.add("08:5D:DD:00:0A:13");
			macList.add("08:5D:DD:00:0A:18");
			macList.add("08:5D:DD:00:0A:1D");
			macList.add("08:5D:DD:00:0A:22");
			macList.add("08:5D:DD:00:0A:27");
			macList.add("08:5D:DD:00:0A:2C");
			macList.add("08:5D:DD:00:0A:31");
			macList.add("08:5D:DD:00:0A:36");
			macList.add("08:5D:DD:00:0A:3B");
			macList.add("08:5D:DD:00:0A:40");
			macList.add("08:5D:DD:00:0A:45");
			macList.add("08:5D:DD:00:0A:4A");
			macList.add("08:5D:DD:00:0A:4F");
			macList.add("08:5D:DD:00:0A:54");
			macList.add("08:5D:DD:00:0A:59");

			if(macList.contains(apMacAddress)) {
				chk = true;
			}
			
//			3. 위 두 SA_ID까 일치 할 경우 청약을 생성한다. 
			if(chk) {
				try {
					checkGigaWIFIHomeSubscription(token, internetSAID, apMacAddress);
				} catch (Exception e) {
					e.printStackTrace();
					vo.setResultCode("-1");
					vo.setResultMessage("[GHMS] 청약생성이 실패하였습니다.");
				}
			} else {
				vo.setResultCode("-1");
				vo.setResultMessage("[SDP] 유효하지 않은 MAC 정보 입니다.");
			}
			
		} else {
			vo.setResultCode("-1");
			vo.setResultMessage("[스마트홈체커] 유효하지 않은 MAC 정보 입니다.");
		}
		
		return vo;
	}
	

	
	/**
	 * GiGA WiFi home AP 청약 체크
	 * @param sdpUser
	 */
	private void checkGigaWIFIHomeSubscription(AuthToken token, String svcContId, String apMacAddress) throws Exception {
		
		SvcTgtBas svcTgtBas = ghmsProvisionDao.selectSvcTgtBasBySvcContIdByMac(svcContId, null, apMacAddress);
		
		if(svcTgtBas == null) {
			
			long svcTgtSeq = ghmsProvisionDao.selectSvcTgtSeq();
			
	        SvcTgtBas newSvcTgtBas = new SvcTgtBas();
	        
	        newSvcTgtBas.setDstr_cd(CommonConstants.DSTR_CD);
	        newSvcTgtBas.setSvc_theme_cd(CommonConstants.SVC_THEME_CD);
	        newSvcTgtBas.setUnit_svc_cd(CommonConstants.UNIT_SVC_CD);
	        newSvcTgtBas.setSvc_tgt_seq((long)svcTgtSeq);
	        newSvcTgtBas.setMbr_seq(token.getUserNo());
	        newSvcTgtBas.setSvc_tgt_id("");       // cust아이디
	        newSvcTgtBas.setSvc_tgt_nm("");     // cust이름
	        newSvcTgtBas.setSvc_cont_id(svcContId);        // 청약아이디
	        newSvcTgtBas.setOprt_sttus_cd("0001");             // 운영상태코드 설정
	        newSvcTgtBas.setDel_yn("N");                       // 삭제 여부
	        newSvcTgtBas.setSvc_tgt_type_cd(CommonConstants.GHMS_AP_SVC_TGT_TYPE_CD);			// 서비스대상유형코드(허브:0000, AP:0001);
	        
	        ghmsProvisionDao.insertSvcTgtBas(newSvcTgtBas);
	        
	        // 스마트홈체커 연동 후 장치(AP) 데이터 생성
	        GetServiceEquipInfoResult equipInfoResult = ktIntraService.sendGetServiceEquipInfo(token.getMbrId(), token.getDeviceId(), "", svcContId);
	        
	        if("T".equals(equipInfoResult.getResultCode())) {
	        	
	        	List<NetworkStatusInfo> networkStatusInfo = equipInfoResult.getNetworkStatusInfo();
	        	
	        	if(networkStatusInfo != null && !networkStatusInfo.isEmpty()) {

		        	for(NetworkStatusInfo networkInfo : networkStatusInfo) {
		        		List<HoldingEquipInfo> holdingEquipInfoList = networkInfo.getHoldingEquipInfoList();
		        		
		        		if(holdingEquipInfoList != null && !holdingEquipInfoList.isEmpty()) {

			        		for(HoldingEquipInfo holdingEquipInfo : holdingEquipInfoList) {
			        			
			        			if("HHG".equals(holdingEquipInfo.getDeviceType()) && svcContId.equals(holdingEquipInfo.getRemark())
			        					&& holdingEquipInfo.getMacAddress().replaceAll(":", "").equals(apMacAddress.replaceAll(":", ""))) {

			    	            	// 동일 타입의 장비 갯수를 가져와서 장비 이름 뒤에 숫자를 붙힌다.
			    	                SpotDevBasKey key = new SpotDevBasKey();
			    	                key.setSvcTgtSeq(svcTgtSeq);
			    	                key.setDevTypeCd(DeviceType.KT_AP_SVC.getCode());
			    	                key.setDevModelCd(DeviceType.KT_AP_SVC.getGroupNm());
			    	            	int typeCount = initDeviceAddDao.selectSoptDevBasByType(key);
			    	            	
			    	            	key.setDevUUID(UUID.randomUUID().toString());
			    	            	key.setSpotDevId("A_" + holdingEquipInfo.getMacAddress().replaceAll(":", ""));
//			    	            	key.setDevNm(DeviceType.KT_AP_SVC.getModelNm() + com.kt.giga.home.openapi.ghms.util.string.StringUtils.getLPad(String.valueOf(typeCount), "0", 2));
			    	            	key.setDevNm(apMacAddress);
			    	            	key.setSvcTgtTypeCd(CommonConstants.GHMS_AP_SVC_TGT_TYPE_CD);
			    	            	key.setIpadr(holdingEquipInfo.getIpAddress());
			    	            	initDeviceAddDao.insertSpotDevBas(key);

			                        SpotDevExpnsnBas sdeb = new SpotDevExpnsnBas();
			                        sdeb.setSvc_tgt_seq(svcTgtSeq);   // 현장장치항목값 
			                        sdeb.setSpot_dev_seq(key.getSpotDevSeq()); // 현장장치값
			                        sdeb.setSpot_dev_item_nm("gwMac");
			                        
			                        String mac = holdingEquipInfo.getMacAddress();
			    					if(mac.length() == 12 && mac.indexOf(":") == -1) {
			    						mac = mac.substring(0, 2) + ":"
			    								+ mac.substring(2, 4) + ":"
			    								+ mac.substring(4, 6) + ":"
			    								+ mac.substring(6, 8) + ":"
			    								+ mac.substring(8, 10) + ":"
			    								+ mac.substring(10, 12);
			    					}
			    					
			                        sdeb.setSpot_dev_item_val(mac);
			    	            	ghmsProvisionDao.insertSpotDevExpnsnBas(sdeb);
			    	            	
			    	            	// 권한 생성
			    	    			SvcEtsRelKey authKey = new SvcEtsRelKey();
			    	    			authKey.setMbrSeq(token.getUserNoLong());
			    	    			authKey.setDstrCd(token.getDstrCd());
			    	    			authKey.setSvcThemeCd(token.getSvcThemeCd());
			    	    			authKey.setUnitSvcCd(token.getUnitSvcCd());
			    	    			authKey.setSvcTgtSeq(svcTgtSeq);
			    	    			authKey.setSpotDevSeq(key.getSpotDevSeq());
			    	    			authKey.setSetUpCd("003");
			    	    			authKey.setSetUpValue("Y");
			    	    			authKey.setGroupSetUpCd(apiEnv.getProperty("group.setup.cd", "A902"));
			    					masterDao.insertSlaveByPushIntoSvcTgtConnSetupTxnAp(authKey);
			        			}
			        		}
		        		}
		        	}
	        	}
	        }
		} else {

			if(!"0001".equals(svcTgtBas.getOprt_sttus_cd()) || !"N".equals(svcTgtBas.getDel_yn())) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("svc_tgt_seq", svcTgtBas.getSvc_tgt_seq());
				map.put("mbr_seq", token.getUserNoLong());
				map.put("del_yn", "N");
				map.put("use_yn", "Y");
				map.put("oprt_sttus_cd", "0000");
				ghmsProvisionDao.updateSvcTgtBas(map);
				
				ghmsProvisionDao.updateSpotDevBas(map);
			}
			/*
			// 존재 하지만 MAC 정보가 변경 되었을 경우.
			DeviceGw deviceApInfo = smartCheckerDao.getIotInfoSvcContId(svcContId, CommonConstants.GHMS_AP_SVC_TGT_TYPE_CD);
	        
	        // 스마트홈체커 연동 후 장치(AP) 데이터 MAC 정보 확인
	        GetServiceEquipInfoResult equipInfoResult = ktIntraService.sendGetServiceEquipInfo(token.getMbrId(), token.getDeviceId(), "", svcContId);
	        
	        if("T".equals(equipInfoResult.getResultCode())) {
	        	
	        	List<NetworkStatusInfo> networkStatusInfo = equipInfoResult.getNetworkStatusInfo();
	        	
	        	if(networkStatusInfo != null && !networkStatusInfo.isEmpty()) {

		        	for(NetworkStatusInfo networkInfo : networkStatusInfo) {
		        		List<HoldingEquipInfo> holdingEquipInfoList = networkInfo.getHoldingEquipInfoList();
		        		
		        		if(holdingEquipInfoList != null && !holdingEquipInfoList.isEmpty()) {

			        		for(HoldingEquipInfo holdingEquipInfo : holdingEquipInfoList) {
			        			
			        			if("HHG".equals(holdingEquipInfo.getDeviceType()) && svcContId.equals(holdingEquipInfo.getRemark())) {
			        				
			        				if(!holdingEquipInfo.getMacAddress().replaceAll(":", "").equals(deviceApInfo.getGwMac().replaceAll(":", ""))) {
				                        
				                        String mac = holdingEquipInfo.getMacAddress();
				    					if(mac.length() == 12 && mac.indexOf(":") == -1) {
				    						mac = mac.substring(0, 2) + ":"
				    								+ mac.substring(2, 4) + ":"
				    								+ mac.substring(4, 6) + ":"
				    								+ mac.substring(6, 8) + ":"
				    								+ mac.substring(8, 10) + ":"
				    								+ mac.substring(10, 12);
				    					}
				    					
			        					// 장비 MAC 정보 변경
			        					Map<String, Object> map = new HashMap<String, Object>();
			        					map.put("spot_dev_id", "A_" + holdingEquipInfo.getMacAddress().replaceAll(":", ""));
			        					map.put("svc_tgt_seq", deviceApInfo.getSvcTgtSeq());
			        					map.put("spot_dev_seq", deviceApInfo.getSpotDevSeq());
			        					ghmsProvisionDao.updateSpotDevBas(map);
			        					
			        			        SpotDevExpnsnBas spotDevExpnsnBas = new SpotDevExpnsnBas();
			        		            
			        		            spotDevExpnsnBas.setSvc_tgt_seq(deviceApInfo.getSvcTgtSeq()); 
			        		            spotDevExpnsnBas.setSpot_dev_seq(deviceApInfo.getSpotDevSeq());
			        		            spotDevExpnsnBas.setSpot_dev_item_nm("gwMac");
			        		            spotDevExpnsnBas.setSpot_dev_item_val(mac);
			        					
			        					ghmsProvisionDao.updateSpotDevExpnsnBas(spotDevExpnsnBas);
			        				}
			        			}
			        		}
		        		}
		        	}
	        	}
	        }
	        */
		}
	}

}
