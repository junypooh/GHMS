/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.service;

import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kt.giga.home.openapi.ghms.common.DeviceType;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.SpotDevAlram;
import com.kt.giga.home.openapi.ghms.common.PushType;
import com.kt.giga.home.openapi.ghms.devices.dao.DevicesDao;
import com.kt.giga.home.openapi.ghms.devices.dao.InitDeviceAddDao;
import com.kt.giga.home.openapi.ghms.devices.domain.key.SpotDevBasKey;
import com.kt.giga.home.openapi.ghms.kafka.KafkaProducer;
import com.kt.giga.home.openapi.ghms.kafka.dao.KafkaMbrPwdDao;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.avro.ComplexEvent;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevDtl;
import com.kt.giga.home.openapi.ghms.ktInfra.dao.GhmsProvisionDao;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SpotDevBas;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SpotDevExpnsnBas;
import com.kt.giga.home.openapi.ghms.ktInfra.service.GhmsProvisionService;
import com.kt.giga.home.openapi.ghms.ktInfra.service.KPNSService;
import com.kt.giga.home.openapi.ghms.util.properties.APIEnv;
import com.kt.giga.home.openapi.ghms.util.string.StringUtils;

/**
 * 장치 추가 시 초기 데이터 생성 서비스
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 3. 24.
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
public class InitDeviceAddService {

	/** Logger */
	private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private InitDeviceAddDao initDeviceAddDao;
    
    @Autowired
    private GhmsProvisionService ghmsProvisionService;
    
    @Autowired
    private KPNSService kPNSService;
    
    @Autowired
    private DevicesDao devicesDao;
    
    @Autowired
    private DevicesService devicesService;
    
    @Autowired
    private GhmsProvisionDao ghmsProvisionDao;
    
    @Autowired
    private KafkaMbrPwdDao kafkaMbrPwdDao;
    
    @Autowired 
    public KafkaProducer kafkaProducer;
    
	@Autowired
	private APIEnv apiEnv;
    
    /**
     * 장치 추가시 최초 데이터 생성(called by kafka consumer.)
     * @param key
     * @return boolean
     * @throws Exception
     */
    public boolean insertInitDataByNewDeviceAdd(SpotDevBasKey key) throws Exception {
/*    	
    	if(DeviceType.GATE_WAY.getCode().equals(key.getDevTypeCd())) {
        	throw new Exception("insertInitDataByNewDeviceAdd > GateWay DEV INFO CALL!!! ");
    	}
    	*/
        try {
        	
        	String rpyCode = null;
        	
        	// svcTgtSeq + spotDevId 존재여부 확인
        	SpotDevBas spotDevBas = initDeviceAddDao.selectSpotDevBas(key);
        	
        	// 디폴트 장비 이름 정책 적용
        	DeviceType[] values = DeviceType.values();
        	
        	boolean unknownType = true;
        	for(DeviceType deviceType : values) {
        		if(key.getDevTypeCd().equals(deviceType.getCode())) {
        			if(!"".equals(deviceType.getModelNm())) {
            			key.setDevNm(deviceType.getModelNm());	
        			}
        			unknownType = false;
        		}
        	}
        	
        	if(unknownType) {
        		key.setDevTypeCd(DeviceType.UNKNOWN.getCode());
        		key.setDevNm(DeviceType.UNKNOWN.getModelNm());
        	}
        	
        	// 동일 타입의 장비 갯수를 가져와서 장비 이름 뒤에 숫자를 붙힌다.
        	int typeCount = initDeviceAddDao.selectSoptDevBasByType(key);
        	key.setDevNm(key.getDevNm() + StringUtils.getLPad(String.valueOf(typeCount), "0", 2));
        	
        	key.setSvcTgtTypeCd(CommonConstants.GHMS_GW_SVC_TGT_TYPE_CD);
        	if(spotDevBas == null) {
            	// 현장장치기본 등록
            	key.setDevUUID(UUID.randomUUID().toString());
                initDeviceAddDao.insertSpotDevBas(key);

                // 현장장치 확장 기본 등록
                List<SpotDevDtl> spotDevDtls = key.getSpotDevDtls();
                for(SpotDevDtl spotDevDtl : spotDevDtls) {

                	if(spotDevDtl.getAtribNm() != null && !"".equals(spotDevDtl.getAtribNm())) {
                        SpotDevExpnsnBas sdeb = new SpotDevExpnsnBas();
                        sdeb.setSvc_tgt_seq(key.getSvcTgtSeq());   // 현장장치항목값 
                        sdeb.setSpot_dev_seq(key.getSpotDevSeq()); // 현장장치값
                        sdeb.setSpot_dev_item_nm(spotDevDtl.getAtribNm());
                        sdeb.setSpot_dev_item_val(spotDevDtl.getAtribVal());
                        
                        ghmsProvisionDao.insertSpotDevExpnsnBas(sdeb);	
                	}
                }
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("dstrCd", CommonConstants.DSTR_CD);
                map.put("svcThemeCd", CommonConstants.SVC_THEME_CD);
                map.put("unitSvcCd", CommonConstants.UNIT_SVC_CD);
                map.put("svcTgtSeq", key.getSvcTgtSeq());
                map.put("spotDevSeq", key.getSpotDevSeq());
                map.put("alarmSetupVal", SpotDevAlram.toDefaultString());
                
                // 회원 현장 장치 설정 내역(권한, 알림)   : MBR_SPOT_DEV_SETUP_TXN
                initDeviceAddDao.insertMbrSpotDevSetupTxn(map);
                
                // 홈모드별 상태 설정 내역                : HOM_MODE_BY_STTUS_SETUP_TXN
                /*
                 * 장비 유형에 따라 초기 값이 틀리다.
                 * 01(외출) : 도어락 N, 침입감지 Y, 가스밸브 Y
                 * 02(재택) : 도어락 N, 침입감지 N, 가스밸브 Y
                 */
                if(DeviceType.DOOR_LOCK.getCode().equals(key.getDevTypeCd())) {
                    map.put("modelCode", "01");
                    map.put("setupSttusCd", "N");
                    initDeviceAddDao.insertHomModeBySttusSetupTxn(map);
                    
                    map.put("modelCode", "02");
                    map.put("setupSttusCd", "N");
                    initDeviceAddDao.insertHomModeBySttusSetupTxn(map);
                } else if(DeviceType.GAS_VALVE.getCode().equals(key.getDevTypeCd())) {
                    map.put("modelCode", "01");
                    map.put("setupSttusCd", "Y");
                    initDeviceAddDao.insertHomModeBySttusSetupTxn(map);
                    
                    map.put("modelCode", "02");
                    map.put("setupSttusCd", "N");
                    initDeviceAddDao.insertHomModeBySttusSetupTxn(map);
                } else if(DeviceType.TRESPS_SENSOR.getCode().equals(key.getDevTypeCd())) {
                    map.put("modelCode", "01");
                    map.put("setupSttusCd", "Y");
                    initDeviceAddDao.insertHomModeBySttusSetupTxn(map);
                    
                    map.put("modelCode", "02");
                    map.put("setupSttusCd", "N");
                    initDeviceAddDao.insertHomModeBySttusSetupTxn(map);
                }
                
                // 장치 모델별 센싱 태그 관계          : DEV_MODEL_BY_SNSN_TAG_REL
                // 현장 장치 설정 기본                  : SPOT_DEV_SETUP_BAS
                List<Map<String, Object>> snsnTagList = initDeviceAddDao.selectSnsnTagCd(map);
                for(Map<String, Object> snsnTagMap : snsnTagList){
                    map.put("snsnTgtCd", snsnTagMap.get("snsnTgtCd"));
                    initDeviceAddDao.insertDevModelBySnsnTagRel(map);
                    initDeviceAddDao.insertSpotDevSetupBas(map);
                }
                
                if(DeviceType.TRESPS_SENSOR.getCode().equals(key.getDevTypeCd())) {
                    // 침임감지 일 경우 spot_dev_genl_setup_txn 테이블에 기본 값을 넣어 준다.
        			Map<String, Object> input = new HashMap<String, Object>();
        			input.put("svcTgtSeq", key.getSvcTgtSeq());
        			input.put("spotDevSeq", key.getSpotDevSeq());
        			input.put("snsnTagCd", "99990000");
        			input.put("rlNumVal", "00");	// 경계 off
        			input.put("delYn", 'N');
        			input.put("cretrId", "ADMIN");
                    devicesDao.insertSpotDevGenlSetupTxn(input);
                }
/*                
                // 장치 모델별 센싱 태그 관계          : DEV_MODEL_BY_SNSN_TAG_REL
                // 현장 장치 설정 기본                  : SPOT_DEV_SETUP_BAS
                if(DeviceType.DOOR_LOCK.getCode().equals(key.getDevTypeCd())) {
                    List<Map<String, Object>> snsnTagList = initDeviceAddDao.selectSnsnTagCd(map);
                    for(Map<String, Object> snsnTagMap : snsnTagList){
                        map.put("snsnTgtCd", snsnTagMap.get("snsnTgtCd"));
                        initDeviceAddDao.insertDevModelBySnsnTagRel(map);
                        initDeviceAddDao.insertSpotDevSetupBas(map);
                    }
                } else if(DeviceType.GAS_VALVE.getCode().equals(key.getDevTypeCd())) {
                    List<Map<String, Object>> snsnTagList = initDeviceAddDao.selectSnsnTagCd(map);
                    for(Map<String, Object> snsnTagMap : snsnTagList){
                        map.put("snsnTgtCd", snsnTagMap.get("snsnTgtCd"));
                        initDeviceAddDao.insertDevModelBySnsnTagRel(map);
                        initDeviceAddDao.insertSpotDevSetupBas(map);
                    }
                } else if(DeviceType.TRESPS_SENSOR.getCode().equals(key.getDevTypeCd())) {
                    map.put("snsTagNm", "trespsSensor");
                    List<Map<String, Object>> snsnTagList = initDeviceAddDao.selectSnsnTagCd(map);
                    for(Map<String, Object> snsnTagMap : snsnTagList){
                        map.put("snsnTgtCd", snsnTagMap.get("snsnTgtCd"));
                        initDeviceAddDao.insertDevModelBySnsnTagRel(map);
                        initDeviceAddDao.insertSpotDevSetupBas(map);
                    }
                    
                    // 침임감지 일 경우 spot_dev_genl_setup_txn 테이블에 기본 값을 넣어 준다.
        			Map<String, Object> input = new HashMap<String, Object>();
        			input.put("svcTgtSeq", key.getSvcTgtSeq());
        			input.put("spotDevSeq", key.getSpotDevSeq());
        			input.put("snsnTagCd", "99990000");
        			input.put("rlNumVal", "01");
        			input.put("delYn", 'N');
        			input.put("cretrId", "ADMIN");
                    devicesDao.insertSpotDevGenlSetupTxn(input);
                }
                */
                // EC 장치 생성
                rpyCode = ghmsProvisionService.insertECDevSpotDevAdd(key);
                
        	} else {
        		
        		key.setSpotDevSeq(spotDevBas.getSpot_dev_seq());

                initDeviceAddDao.updateSpotDevBas(key);

                // 현장장치 확장 기본 등록
                List<SpotDevDtl> spotDevDtls = key.getSpotDevDtls();
                for(SpotDevDtl spotDevDtl : spotDevDtls) {

                	if(spotDevDtl.getAtribNm() != null && !"".equals(spotDevDtl.getAtribNm())) {
                        SpotDevExpnsnBas sdeb = new SpotDevExpnsnBas();
                        sdeb.setSvc_tgt_seq(key.getSvcTgtSeq());   // 현장장치항목값 
                        sdeb.setSpot_dev_seq(key.getSpotDevSeq()); // 현장장치값
                        sdeb.setSpot_dev_item_nm(spotDevDtl.getAtribNm());
                        sdeb.setSpot_dev_item_val(spotDevDtl.getAtribVal());
                        
                        ghmsProvisionDao.updateSpotDevExpnsnBas(sdeb);	
                	}
                }
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("dstrCd", CommonConstants.DSTR_CD);
                map.put("svcThemeCd", CommonConstants.SVC_THEME_CD);
                map.put("unitSvcCd", CommonConstants.UNIT_SVC_CD);
                map.put("svcTgtSeq", key.getSvcTgtSeq());
                map.put("spotDevSeq", key.getSpotDevSeq());
                map.put("alarmSetupVal", SpotDevAlram.toDefaultString());
                
                // 기존 회원 현장 장치 설정 내역(권한, 알림) 삭제 : MBR_SPOT_DEV_SETUP_TXN
                initDeviceAddDao.deleteMbrSpotDevSetupTxn(map);
                
                // 회원 현장 장치 설정 내역(권한, 알림)   		: MBR_SPOT_DEV_SETUP_TXN
                initDeviceAddDao.insertMbrSpotDevSetupTxn(map);
                
                // 홈모드별 상태 설정 내역 삭제           : HOM_MODE_BY_STTUS_SETUP_TXN
                initDeviceAddDao.deleteHomModeBySttusSetupTxn(map);
                
                // 홈모드별 상태 설정 내역                : HOM_MODE_BY_STTUS_SETUP_TXN
                /*
                 * 장비 유형에 따라 초기 값이 틀리다.
                 * 01(외출) : 도어락 N, 침입감지 Y, 가스밸브 Y
                 * 02(재택) : 도어락 N, 침입감지 N, 가스밸브 Y
                 */
                if(DeviceType.DOOR_LOCK.getCode().equals(key.getDevTypeCd())) {
                    map.put("modelCode", "01");
                    map.put("setupSttusCd", "N");
                    initDeviceAddDao.insertHomModeBySttusSetupTxn(map);
                    
                    map.put("modelCode", "02");
                    map.put("setupSttusCd", "N");
                    initDeviceAddDao.insertHomModeBySttusSetupTxn(map);
                } else if(DeviceType.GAS_VALVE.getCode().equals(key.getDevTypeCd())) {
                    map.put("modelCode", "01");
                    map.put("setupSttusCd", "Y");
                    initDeviceAddDao.insertHomModeBySttusSetupTxn(map);
                    
                    map.put("modelCode", "02");
                    map.put("setupSttusCd", "N");
                    initDeviceAddDao.insertHomModeBySttusSetupTxn(map);
                } else if(DeviceType.TRESPS_SENSOR.getCode().equals(key.getDevTypeCd())) {
                    map.put("modelCode", "01");
                    map.put("setupSttusCd", "Y");
                    initDeviceAddDao.insertHomModeBySttusSetupTxn(map);
                    
                    map.put("modelCode", "02");
                    map.put("setupSttusCd", "N");
                    initDeviceAddDao.insertHomModeBySttusSetupTxn(map);
                }
                
                // 장치 모델별 센싱 태그 관계          : DEV_MODEL_BY_SNSN_TAG_REL
                // 현장 장치 설정 기본                  : SPOT_DEV_SETUP_BAS
                List<Map<String, Object>> snsnTagList = initDeviceAddDao.selectSnsnTagCd(map);
                for(Map<String, Object> snsnTagMap : snsnTagList){
                    map.put("snsnTgtCd", snsnTagMap.get("snsnTgtCd"));
                    initDeviceAddDao.insertDevModelBySnsnTagRel(map);
                    initDeviceAddDao.insertSpotDevSetupBas(map);
                }
                
                // 기존 spot_dev_genl_setup_txn 테이블 삭제
                devicesDao.deleteSpotDevGenlSetupTxn(map);
                
                if(DeviceType.TRESPS_SENSOR.getCode().equals(key.getDevTypeCd())) {
                    // 침임감지 일 경우 spot_dev_genl_setup_txn 테이블에 기본 값을 넣어 준다.
        			Map<String, Object> input = new HashMap<String, Object>();
        			input.put("svcTgtSeq", key.getSvcTgtSeq());
        			input.put("spotDevSeq", key.getSpotDevSeq());
        			input.put("snsnTagCd", "99990000");
        			input.put("rlNumVal", "00");	// 경계 off
        			input.put("delYn", 'N');
        			input.put("cretrId", "ADMIN");
                    devicesDao.insertSpotDevGenlSetupTxn(input);
                }
/*                
                if(DeviceType.DOOR_LOCK.getCode().equals(key.getDevTypeCd())) {
                    List<Map<String, Object>> snsnTagList = initDeviceAddDao.selectSnsnTagCd(map);
                    for(Map<String, Object> snsnTagMap : snsnTagList){
                        map.put("snsnTgtCd", snsnTagMap.get("snsnTgtCd"));
                        initDeviceAddDao.insertDevModelBySnsnTagRel(map);
                        initDeviceAddDao.insertSpotDevSetupBas(map);
                    }
                } else if(DeviceType.GAS_VALVE.getCode().equals(key.getDevTypeCd())) {
                    List<Map<String, Object>> snsnTagList = initDeviceAddDao.selectSnsnTagCd(map);
                    for(Map<String, Object> snsnTagMap : snsnTagList){
                        map.put("snsnTgtCd", snsnTagMap.get("snsnTgtCd"));
                        initDeviceAddDao.insertDevModelBySnsnTagRel(map);
                        initDeviceAddDao.insertSpotDevSetupBas(map);
                    }
                } else if(DeviceType.TRESPS_SENSOR.getCode().equals(key.getDevTypeCd())) {
                    map.put("snsTagNm", "trespsSensor");
                    List<Map<String, Object>> snsnTagList = initDeviceAddDao.selectSnsnTagCd(map);
                    for(Map<String, Object> snsnTagMap : snsnTagList){
                        map.put("snsnTgtCd", snsnTagMap.get("snsnTgtCd"));
                        initDeviceAddDao.insertDevModelBySnsnTagRel(map);
                        initDeviceAddDao.insertSpotDevSetupBas(map);
                    }
                    
                    // 침임감지 일 경우 spot_dev_genl_setup_txn 테이블에 기본 값을 넣어 준다.
        			Map<String, Object> input = new HashMap<String, Object>();
        			input.put("svcTgtSeq", key.getSvcTgtSeq());
        			input.put("spotDevSeq", key.getSpotDevSeq());
        			input.put("snsnTagCd", "99990000");
        			input.put("rlNumVal", "01");
        			input.put("delYn", 'N');
        			input.put("cretrId", "ADMIN");
                    devicesDao.insertSpotDevGenlSetupTxn(input);
                }
*/
                if("Y".equals(spotDevBas.getDel_yn())) {
                    // EC 장치 생성
                    rpyCode = ghmsProvisionService.insertECDevSpotDevAdd(key);	
                } else {
                    // EC 장치 수정
                    rpyCode = ghmsProvisionService.updateECDevSpotDevAdd(key);
                }
                
        	} 
        	
            log.warn(rpyCode);

            if("100".equals(rpyCode)){
                // 디바이스 추가 완료시 통보 PUSH
                pushDevice(PushType.DEVICE_ADD.getEventId(), key.getSpotDevSeq(), key.getSvcTgtSeq());
                return true;
            } else {
            	throw new Exception("insertInitDataByNewDeviceAdd > ec insert Exception");
            }
            
            

            
        } catch(Exception e) {
        	log.error(e.getMessage());
            throw e;
        }
    }
    
    /**
     * 장치 삭제(called by kafka consumer.)
     * @param key
     * @return
     * @throws Exception
     */
    public boolean deleteDevice(SpotDevBasKey key) throws Exception {
    	
        try {
        	
        	long spotDevSeq = initDeviceAddDao.selectSpotDevSeqBySpotDevId(key);
        	key.setSpotDevSeq(spotDevSeq);
        	
    		initDeviceAddDao.deleteSpotDevBas(key);
    		
    		/** 도어락 패스워드 초기화 */
    		if(DeviceType.DOOR_LOCK.getCode().equals(key.getDevTypeCd())){
	    		Map<String, Object> mbrPwd = new HashMap<String, Object>();
	    		mbrPwd.put("svcTgtSeq", key.getSvcTgtSeq());
	    		mbrPwd.put("spotDevSeq", spotDevSeq);
	    		kafkaMbrPwdDao.deleteMbrPwdTxn(mbrPwd);
    		}
            String deleteECDev = ghmsProvisionService.deleteECDev(key.getSvcTgtSeq(), key.getSpotDevSeq());
            
            if("100".equals(deleteECDev)){
                // 디바이스 제거 완료시 PUSH
                pushDevice(PushType.DEVICE_DEL.getEventId(), key.getSpotDevSeq(), key.getSvcTgtSeq());
                
                //침입감지 센서 OFF 알림 to 3MP Core
        		ComplexEvent complexEvent = new ComplexEvent();
        		complexEvent.setSvcTgtSeq(key.getSvcTgtSeq());
        		complexEvent.setEvtId("001HIT003E0009");
        		
        		Map<String, Object> value = new HashMap<String, Object>();
        		
        		byte[] bytes = new byte[1];
        		bytes[0] = (byte)0x00;
				value.put("99990000", ByteBuffer.wrap(bytes));
				value.put("svcCode", CommonConstants.DSTR_CD + CommonConstants.SVC_THEME_CD + CommonConstants.UNIT_SVC_CD);
				value.put("spotDevSeq", key.getSpotDevSeq());
				
				complexEvent.setItems(value);
        		kafkaProducer.sendAvro(apiEnv.getProperty("producer.api.topic.complex"), complexEvent);
                
                return true;
            } else {
            	throw new Exception("deleteInitDataByNewDeviceAdd > ec delete Exception");
            }
            
        } catch(Exception e) {
            throw e;
        }
    }
    
    /**
     * 게이트웨이 연결시 정보 update
     * @param key : #{svcTgtSeq}, #{spotDevId}, #{tmpDevYn}
     * @return
     * @throws Exception
     */
    public boolean updateGateWayTmpDev(SpotDevBasKey key) throws Exception {
        
        try {
        	long spotDevSeq = initDeviceAddDao.selectSpotDevSeqBySpotDevId(key);
        	key.setSpotDevSeq(spotDevSeq);
        	
            int upChk = initDeviceAddDao.updateGateWayTmpDev(key);
            
            return upChk > 0 ? true : false;
        } catch(Exception e) {
            throw e;
        }
    }
    
    /**
     * @param eventId       푸쉬이벤트아이디
     * @param spotDevSeq    현장장치일련번호
     * @param svcTgtSeq     서비스대상일련번호
     * @throws Exception
     */
    private void pushDevice(String eventId, long spotDevSeq, long svcTgtSeq) throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("eventId", eventId);
        data.put("spotDevSeq", String.valueOf(spotDevSeq));
        
        devicesService.pushSend(null, svcTgtSeq, data, "M");
    }
    
    /**
     * 공장 초기화 시 호출됨.
     * 게이트웨이에 연결된 하위 장치 삭제
     * @param key(게이트웨이 정보) : #{svcTgtSeq}, #{spotDevId}, #{cretrId}
     * @return
     * @throws Exception
     */
    public boolean deleteSpotDevsConnectedByGw(SpotDevBasKey key) throws Exception {
        
        try {
        	long spotDevSeq = initDeviceAddDao.selectSpotDevSeqBySpotDevId(key);
        	key.setSpotDevSeq(spotDevSeq);
        	
            int upChk = initDeviceAddDao.updateGateWayTmpDev(key);
            
            if(upChk < 1){
            	throw new Exception("deleteSpotDevsConnectedByGw > GateWat delete Exception");
            }
        	
        	List<Long> devsConnectedByGw = initDeviceAddDao.selectSpotDevsConnectedByGw(key);
        	
        	for(Long _spotDevSeq : devsConnectedByGw) {

                String deleteECDev = ghmsProvisionService.deleteECDev(key.getSvcTgtSeq(), _spotDevSeq);
                
        		/** 도어락 패스워드 초기화 */
	    		Map<String, Object> mbrPwd = new HashMap<String, Object>();
	    		mbrPwd.put("svcTgtSeq", key.getSvcTgtSeq());
	    		mbrPwd.put("spotDevSeq", _spotDevSeq);
	    		kafkaMbrPwdDao.deleteMbrPwdTxn(mbrPwd);
                
                
                if(!"100".equals(deleteECDev)){
                	throw new Exception("deleteInitDataByNewDeviceAdd > ec delete Exception");
                }
        	}
        	
            initDeviceAddDao.deleteSpotDevsConnectedByGw(key);

            // 디바이스 제거 완료시 PUSH
            String devNm = devicesDao.selectDevNmFromSpotDevBas(key.getSvcTgtSeq(), spotDevSeq);
            
            Map<String, String> data = new HashMap<String, String>();
            data.put("eventId", PushType.DEVICE_INITIAL.getEventId());
            data.put("spotDevSeq", String.valueOf(spotDevSeq));
            data.put("svcTgtSeq", String.valueOf(key.getSvcTgtSeq()));
            data.put("devNm", devNm);
            data.put("msg", "사용중인 홈매니저 허브 \"" + devNm + "\" 이/가 공장초기화 되었습니다.\n등록 홈매니저 허브 목록을 확인하여 주시기 바랍니다.");
            
            devicesService.pushSend(null, key.getSvcTgtSeq(), data, "MS");
            
            // 침입감지 센서 OFF 알림 to 3MP Core
    		ComplexEvent complexEvent = new ComplexEvent();
    		complexEvent.setSvcTgtSeq(key.getSvcTgtSeq());
    		complexEvent.setEvtId("001HIT003E0009");
    		
    		Map<String, Object> value = new HashMap<String, Object>();
    		
    		byte[] bytes = new byte[1];
    		bytes[0] = (byte)0x00;
			value.put("99990000", ByteBuffer.wrap(bytes));
			value.put("svcCode", CommonConstants.DSTR_CD + CommonConstants.SVC_THEME_CD + CommonConstants.UNIT_SVC_CD);
			
			complexEvent.setItems(value);
        	
        	for(Long _spotDevSeq : devsConnectedByGw){
				value.put("spotDevSeq", _spotDevSeq);				
        		kafkaProducer.sendAvro(apiEnv.getProperty("producer.api.topic.complex"), complexEvent);
        	}
            
            
            return true;
            
        } catch(Exception e) {
            throw e;
        }
    }
    

	
	/**
	 * 하위 장치 공장 초기화 시간 정보 조회
	 * @param key : #{svcTgtSeq}, #{spotDevId}
	 * @return
	 */
	public List<Map<String, Object>> selectSpotDevsLastMtnDt(SpotDevBasKey key) {
		return initDeviceAddDao.selectSpotDevsLastMtnDt(key);
	}
    
}
