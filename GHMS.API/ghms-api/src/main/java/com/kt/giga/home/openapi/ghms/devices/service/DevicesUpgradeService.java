/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.kt.giga.home.openapi.ghms.checker.domain.key.TimeSettingKey;
import com.kt.giga.home.openapi.ghms.checker.domain.vo.InternetAccessRule;
import com.kt.giga.home.openapi.ghms.common.DeviceType;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.PushType;
import com.kt.giga.home.openapi.ghms.common.service.ECService;
import com.kt.giga.home.openapi.ghms.common.token.AuthToken;
import com.kt.giga.home.openapi.ghms.devices.dao.DevicesDao;
import com.kt.giga.home.openapi.ghms.devices.dao.DevicesUpgradeDao;
import com.kt.giga.home.openapi.ghms.devices.domain.key.DeviceCtrStatusKey;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.Device;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.DeviceGw;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.DeviceMasterVo;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.SpotDevice;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.SpotDeviceGw;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.SpotDeviceSnsnData;
import com.kt.giga.home.openapi.ghms.kafka.KafkaDevSetupManager;
import com.kt.giga.home.openapi.ghms.kafka.KafkaProducer;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaDevSetup;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaDevSetupGroup;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaSnsnTag;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.cnvy.CnvyRow;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.cnvy.ItgCnvyData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.cnvy.SpotDevCnvyData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.BinData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.BinSetupData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.CmdData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.ContlData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.GenlSetupData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevDtl;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevKey;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevRetv;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.query.SpotDevBasQueryVO;
import com.kt.giga.home.openapi.ghms.kafka.type.KafkaMsgType;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.PushInfoRequest;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetInternetAccessControlResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetServiceEquipInfoResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetWakeOnLanListResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.HoldingEquipInfo;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.HostDeviceInfo;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.MyPCInfo;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.NetworkStatusInfo;
import com.kt.giga.home.openapi.ghms.ktInfra.service.KPNSService;
import com.kt.giga.home.openapi.ghms.ktInfra.service.KTInfraService;
import com.kt.giga.home.openapi.ghms.user.dao.MasterDao;
import com.kt.giga.home.openapi.ghms.user.domain.key.MasterUserKey;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;
import com.kt.giga.home.openapi.ghms.util.properties.APIEnv;

/**
 * 제어 서비스(고도화 전용)
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
public class DevicesUpgradeService {

	/** Logger */
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private Map<String, String> baseMap;
	
	public Gson gson = new Gson();
	
	public DevicesUpgradeService() {
	    
		// 현장장치 그룹별 setting.
		baseMap = new HashMap<String, String>();
		baseMap.put(DeviceType.DOOR_LOCK.getCode(), DeviceType.DOOR_LOCK.getGroupNm());
		baseMap.put(DeviceType.GAS_VALVE.getCode(), DeviceType.GAS_VALVE.getGroupNm());
		baseMap.put(DeviceType.TRESPS_SENSOR.getCode(), DeviceType.TRESPS_SENSOR.getGroupNm());
		baseMap.put(DeviceType.UNKNOWN.getCode(), DeviceType.UNKNOWN.getGroupNm());
	}
	
	@Autowired
	private KafkaDevSetupManager kafkaDevSetupManager;
	
	@Autowired
	private DevicesDao devicesDao;
	
	@Autowired
	private DevicesUpgradeDao devicesUpgradeDao;
	
	@Autowired
	private KafkaProducer kafkaProducer;
	
	@Autowired
	private KPNSService kPNSService;
	
	@Autowired
	private APIEnv apiEnv;
	
	@Autowired
	private MasterDao masterDao;
	
	@Autowired
	private ECService eCService;
	
	@Autowired
	private KTInfraService ktService;
	
	/**
	 * 게이트웨이 정보 조회
	 * @param token
	 * @param gwUUID
	 * @return
	 * @throws APIException
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public DeviceMasterVo selectSpotGateWayList(AuthToken token, DeviceCtrStatusKey key) throws APIException {
		
		DeviceMasterVo masterVo = null;
		
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
			
			masterVo = selectSpotGateWayListByDataBase(token, key.getServiceNo(), key.getGwUUID(), key.getGetCheckerYn());
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
					
					masterVo = new DeviceMasterVo();
					masterVo.setTransacIdList(key.getTransacIdList());
					masterVo.setGetCmdYn(key.getGetCmdYn());
					masterVo.setGetCheckerYn(key.getGetCheckerYn());
					
					break;
				}
			}
			
			log.info("############### isFull : " + isFull + " #######################");
			
			// 데이터가 존재하고 DB에 저장이 되었다면....
			if(isFull) {
				
			    // DB 조회
				masterVo = selectSpotGateWayListByDataBase(token, key.getServiceNo(), key.getGwUUID(), key.getGetCheckerYn());

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
					
					List<DeviceGw> gwList = masterVo.getSpotDevGwList();
					DeviceGw deviceGw = gwList.get(0);
					
					List<Device> spotDevs = deviceGw.getSpotDevs();
					
					if(spotDevs != null && spotDevs.size() > 0) {

						for(Device devs : spotDevs) {
							
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
	private DeviceMasterVo selectSpotGateWayListByDataBase(AuthToken token, long serviceNo, long spotDevSeq, String getCheckerYn) {
		
		DeviceMasterVo masterVo = new DeviceMasterVo();
		
		// 게이트 웨이 먼저 가져온다.
		List<DeviceGw> gateWayList = devicesUpgradeDao.selectSpotGateWayList(token.getUserNoLong(), token.getDstrCd(), token.getSvcThemeCd(), token.getUnitSvcCd(), serviceNo, spotDevSeq, DeviceType.GATE_WAY.getCode());
		
		// 게이트 웨이 갯수 만큼 게이트웨이에 연결된 현장장치 리스트를 가져온다.
		for(DeviceGw gateway : gateWayList) {
			
			List<Device> spotDevs = devicesUpgradeDao.selectSpotDevList(gateway.getSvcTgtSeq(), gateway.getSpotDevSeq(), null, token.getUserNoLong(), DeviceType.GATE_WAY.getCode());
			
			// 가져온 현장장리 리스트만큼 현장장치 상세, 일반설정 데이터를 가져온다.
			for(Device spotDev : spotDevs) {
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
//		2차고도화임시주석
		// AP 먼저 가져온다.
		List<DeviceGw> apList = devicesUpgradeDao.selectSpotGateWayList(token.getUserNoLong(), token.getDstrCd(), token.getSvcThemeCd(), token.getUnitSvcCd(), serviceNo, spotDevSeq, DeviceType.KT_AP_SVC.getCode());
		
		// AP 만큼 게이트웨이에 연결된 현장장치 리스트를 가져온다.
		if("Y".equals(getCheckerYn)) {
			for(DeviceGw apInfo : apList) {

				apInfo.setOnOff("0");
				List<Device> spotDevs = new ArrayList<Device>();
				
				// KT 서비스단말 상태조회
		        GetServiceEquipInfoResult equipInfoResult = ktService.sendGetServiceEquipInfo(token.getMbrId(), token.getDeviceId(), apInfo.getGwMac(), apInfo.getSvcContId());
		        
		        // 인터넷 이용제한 목록조회
		        List<String> ruleMacList = new ArrayList<String>();
		        GetInternetAccessControlResult internetAccessControl = ktService.getInternetAccessControl(token.getMbrId(), token.getDeviceId(), apInfo.getGwMac(), "");
		        if("T".equals(internetAccessControl.getResultCode())) {
		        	List<InternetAccessRule> accessRules = internetAccessControl.getInternetAccessRules();
		        	if(accessRules != null) {
		        		apInfo.setInternetRuleCnt(accessRules.size());
		        		for(InternetAccessRule rule : accessRules) {
		        			String accessControlMac = rule.getInternetAccessControlMac();
		        			if(!ruleMacList.contains(accessControlMac)) {
		        				ruleMacList.add(accessControlMac);
		        			}
		        		}
		        	}
		        }
		        
		        // PC 원격 켜기 목록조회
		        GetWakeOnLanListResult wakeOnLanList = ktService.getWakeOnLanList(token.getMbrId(), token.getDeviceId(), apInfo.getGwMac());
		        List<MyPCInfo> pcInfoList = null;
		        List<String> pcMacAddresses = new ArrayList<String>();
		        if("T".equals(wakeOnLanList.getResultCode())) {
		        	pcInfoList = wakeOnLanList.getPcInfoList();
		        	
		        	if(pcInfoList != null) {
			        	for(MyPCInfo myPcInfo : pcInfoList) {
			        		pcMacAddresses.add(myPcInfo.getPcMacAddress());
			        	}	
		        	}
		        }
		        
		        List<String> macAddrList = new ArrayList<String>();
		        if("T".equals(equipInfoResult.getResultCode())) {
					
		        	List<NetworkStatusInfo> networkStatusInfo = equipInfoResult.getNetworkStatusInfo();
		        	if(networkStatusInfo != null && !networkStatusInfo.isEmpty()) {

			        	for(NetworkStatusInfo networkInfo : networkStatusInfo) {
			        		/*
			        		필요 로직, 규격서 가 변경 될 예정이라 반영하지 않음.
			        		if(!"KT".equals(networkInfo.getInternetType())) {
			        			continue;
			        		}
			        		*/
			        		List<HoldingEquipInfo> holdingEquipInfoList = networkInfo.getHoldingEquipInfoList();
			        		
			        		if(holdingEquipInfoList != null && !holdingEquipInfoList.isEmpty()) {

				        		for(HoldingEquipInfo holdingEquipInfo : holdingEquipInfoList) {
	// HHG(GiGA-AP),HH(Homehub),HHS(Homehub-Special),HHP(Homehub-Phone),STB(OllehTV),OTS(OllehTV-SkyLife),VOIP(Internet Phone),KIBOT(키봇),UNKNOWN(알수없음)

									if( "HHG".equals(holdingEquipInfo.getDeviceType()) && apInfo.getSvcContId().equals(holdingEquipInfo.getRemark())
											&& apInfo.getGwMac().replaceAll(":", "").equals(holdingEquipInfo.getMacAddress().replaceAll(":", "")) ) {

//				        				if(apInfo.getGwMac() != null && holdingEquipInfo.getMacAddress() != null
//				        						&& apInfo.getGwMac().replaceAll(":", "").equals(holdingEquipInfo.getMacAddress().replaceAll(":", ""))) {
										
											apInfo.setOnOff(holdingEquipInfo.getOnOff());
				        					
						        			List<HostDeviceInfo> hostDeviceInfoList = holdingEquipInfo.getHostDeviceInfoList();
						        			
						        			if(hostDeviceInfoList != null && !hostDeviceInfoList.isEmpty()) {

							        			for(HostDeviceInfo hostDevice : hostDeviceInfoList) {

							        				if(pcMacAddresses.contains(hostDevice.getMacAddress()) || ruleMacList.contains(hostDevice.getMacAddress())) {

								        				Device dev = new Device();
								        				spotDevs.add(dev);
								        				
								        				dev.setSvcTgtSeq(apInfo.getSvcTgtSeq());
								        				if(pcMacAddresses.contains(hostDevice.getMacAddress())) {
								        					dev.setSpotGroupCd(DeviceType.KT_PC_SVC.getCode());
								        					dev.setSpotGroupNm(DeviceType.KT_PC_SVC.getGroupNm());
								        				} else {
								        					dev.setSpotGroupCd(DeviceType.KT_TIME_SVC.getCode());
								        					dev.setSpotGroupNm(DeviceType.KT_TIME_SVC.getGroupNm());
								        				}
								        				
								        				// 장비 이름 셋팅
								        				if(dev.getSpotGroupCd().equals(DeviceType.KT_PC_SVC.getCode())) {
								        					if(pcInfoList != null) {
								        			        	for(MyPCInfo myPcInfo : pcInfoList) {
								        			        		if(hostDevice.getMacAddress().equals(myPcInfo.getPcMacAddress())) {
								        			        			dev.setSpotDevNm(myPcInfo.getPcDescription());
								        			        		}
								        			        	}
								        					}
								        				} else {
									        				dev.setSpotDevNm(hostDevice.getNetworkName());
								        				}
								        				dev.setSpotDevMac(hostDevice.getMacAddress());
								        				dev.setOnOff(hostDevice.getOnOff());
								        				dev.setMngDevYn("N");
								        				dev.setMakrNm(hostDevice.getManufacturer());
								        				dev.setChannelName(holdingEquipInfo.getChannelName());

								        				TimeSettingKey key = new TimeSettingKey();
								        				key.setSvcTgtSeq(apInfo.getSvcTgtSeq());
								        				key.setHostMacAddress(hostDevice.getMacAddress());
								        				Device devInfo = devicesUpgradeDao.selectSpotDevBasByDevMac(key);
								        				if(devInfo != null) {
								        					dev.setMngDevYn("Y");
								        					dev.setSpotDevNm(devInfo.getSpotDevNm());
								        					dev.setSpotDevSeq(devInfo.getSpotDevSeq());
								        				}
								        				
								        				macAddrList.add(hostDevice.getMacAddress());
							        				} else {
							        					// 허브(GateWay) 정보 존재 여부 판단.
								        				Device devInfo = devicesUpgradeDao.selectSpotDevBasByGwMac(token.getUserNoLong(), token.getDstrCd(), token.getSvcThemeCd(), token.getUnitSvcCd(), hostDevice.getMacAddress());
								        				if(devInfo != null) {
								        					devInfo.setOnOff(hostDevice.getOnOff());
									        				devInfo.setSpotGroupCd(DeviceType.GATE_WAY.getCode());
									        				devInfo.setSpotGroupNm(DeviceType.GATE_WAY.getGroupNm());
									        				spotDevs.add(devInfo);	
								        				}
								        				
							        				}
							        			}
						        			}
//				        				}
				        				
				        			} else {

					        			if("STB".equals(holdingEquipInfo.getDeviceType()) || "OTS".equals(holdingEquipInfo.getDeviceType()) 
					        			|| "VOIP".equals(holdingEquipInfo.getDeviceType()) || "KIBOT".equals(holdingEquipInfo.getDeviceType())
					        			|| "HH".equals(holdingEquipInfo.getDeviceType()) || "HHS".equals(holdingEquipInfo.getDeviceType())
					        			|| "HHP".equals(holdingEquipInfo.getDeviceType())) {
					        				
					        				Device dev = new Device();
					        				spotDevs.add(dev);
					        				
					        				dev.setSpotGroupCd(DeviceType.KT_HOME_SVC.getCode());
					        				dev.setSpotGroupNm(DeviceType.KT_HOME_SVC.getGroupNm());
					        				dev.setSvcTgtSeq(serviceNo);
					        				if("STB".equals(holdingEquipInfo.getDeviceType())) {
					        					dev.setSpotDevNm("OllehTV");
					        				} else if("OTS".equals(holdingEquipInfo.getDeviceType())) {
					        					dev.setSpotDevNm("OllehTV-SkyLife");
					        				} else if("VOIP".equals(holdingEquipInfo.getDeviceType())) {
					        					dev.setSpotDevNm("Internet Phone");
					        				} else if("KIBOT".equals(holdingEquipInfo.getDeviceType())) {
					        					dev.setSpotDevNm("키봇");
					        				} else if("HH".equals(holdingEquipInfo.getDeviceType())) {
					        					dev.setSpotDevNm("홈허브");
					        				} else if("HHS".equals(holdingEquipInfo.getDeviceType())) {
					        					dev.setSpotDevNm("홈허브-스페셜");
					        				} else if("HHP".equals(holdingEquipInfo.getDeviceType())) {
					        					dev.setSpotDevNm("홈허브-폰");
					        				}
					        				
					        				dev.setSpotDevMac(holdingEquipInfo.getMacAddress());
					        				dev.setOnOff(holdingEquipInfo.getOnOff());
					        				dev.setMngDevYn("N");
					        				dev.setMakrNm(holdingEquipInfo.getManufacturer());
					        				dev.setChannelName(holdingEquipInfo.getChannelName());
					        				
					        			}
				        			}
				        		}
			        		}
			        	}
		        	}
		        	
		        }

		        String[] macAddrArray = null;
		        if(!macAddrList.isEmpty()) {
		        	macAddrArray = (String[]) macAddrList.toArray(new String[macAddrList.size()]);
		        }
				List<Device> spotDevs2 = devicesUpgradeDao.selectSpotDevListFilter(apInfo.getSvcTgtSeq(), apInfo.getSpotDevSeq(), null, token.getUserNoLong(), DeviceType.KT_AP_SVC.getCode(), macAddrArray);
				spotDevs.addAll(spotDevs2);
				
				if(spotDevs.size() > 0) {
					apInfo.setSpotDevs(spotDevs);	
				}
				
			}
		}
		masterVo.setSpotDevAPList(apList);

		return masterVo;
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
	 * @param param
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public void setCmsWifiLog(Map<String, Object> param) {
		
		long serviceNo = Long.parseLong(param.get("SVC_TGT_SEQ").toString());
		long spotDevSeq = Long.parseLong(param.get("STOP_DEV_SEQ").toString());
		
		String collectCount = param.get("collectCount").toString();
		String collectPeriod = param.get("collectPeriod").toString();
		String logMode = param.get("logMode").toString();
		
		List<SpotDeviceGw> gateWayList = devicesDao.selectSpotGateWayList((long)0, CommonConstants.DSTR_CD, CommonConstants.SVC_THEME_CD, CommonConstants.UNIT_SVC_CD, serviceNo, spotDevSeq, DeviceType.GATE_WAY.getCode());
		
		for(SpotDeviceGw gateway : gateWayList) {
			
			/* 제어를 내릴 게이트웨이가 붙어있는 EC서버명을 넣을 수 있도록 하는 로직. ECService(svcTgtSeq, spotDevSeq, spotDevId) 이용 */
			SpotDevBasQueryVO spotDevBasQueryVO = eCService.sendSelectQueryToEC(serviceNo
					, gateway.getSpotDevSeq()
					, gateway.getSpotDevId());

			ItgCnvyData itgCnvyData = new ItgCnvyData();

			itgCnvyData.setUnitSvcCd(CommonConstants.DSTR_CD + CommonConstants.SVC_THEME_CD + CommonConstants.UNIT_SVC_CD);
			itgCnvyData.setReqEcSrvrId(spotDevBasQueryVO.getSpotDevBasVO().getConnSrvrId());

			List<SpotDevCnvyData> spotDevCnvyDataList = new ArrayList<SpotDevCnvyData>();

			/* kafka ItgCnvyData 껍데기 생성 시작 */
			SpotDevCnvyData spotDevCnvyData = new SpotDevCnvyData();
			spotDevCnvyData.setSvcTgtSeq(gateway.getSvcTgtSeq());
			spotDevCnvyData.setSpotDevSeq(gateway.getSpotDevSeq());
			spotDevCnvyData.setSpotDevId(gateway.getSpotDevId());
			spotDevCnvyData.setUpSpotDevId(gateway.getUpSpotDevId());
			
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
			
			BinData binData = new BinData();
			binData.setSnsnTagCd("50000008");
			binData.setBinValTxn(new byte[0]);
			binDatas.add(binData);

			rowExtension.put("collectCount"	, collectCount);				
			rowExtension.put("collectPeriod", collectPeriod );	//단위 : 초			
			rowExtension.put("logMode", logMode);	//로그 on : 1 / off : 0	

			List<String> rtnTransacIdList = kafkaDevSetupManager.create(1, 1, false);
			String transacId = rtnTransacIdList.get(0);
			
			itgCnvyData.setTransacId(transacId);
			spotDevCnvyData.setTransacId(transacId+"_"+0);
			spotDevCnvyDataList.add(spotDevCnvyData);
			
			itgCnvyData.setSpotDevCnvyDatas(spotDevCnvyDataList);
			
			String json = gson.toJson(itgCnvyData, ItgCnvyData.class);
			
			if(log.isDebugEnabled()) {
				log.debug("json : {}", json);	
			}

			kafkaProducer.sendJson(apiEnv.getProperty("producer.api.topic"), KafkaMsgType.CONTL_ITGCNVY_DATA, json);
		}
	}

	/**
	 * @param param
	 * @return
	 */
	public void setCmsSatusLog(Map<String, Object> param) {
		
		long serviceNo = Long.parseLong(param.get("SVC_TGT_SEQ").toString());
		long spotDevSeq = Long.parseLong(param.get("STOP_DEV_SEQ").toString());

		// 게이트 웨이 먼저 가져온다.
		List<SpotDeviceGw> gateWayList = devicesDao.selectSpotGateWayList((long)0, CommonConstants.DSTR_CD, CommonConstants.SVC_THEME_CD, CommonConstants.UNIT_SVC_CD, serviceNo, spotDevSeq, DeviceType.GATE_WAY.getCode());
		
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
						}
					}
				}
			}
		}
	}

}
