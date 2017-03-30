/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.xml.ws.http.HTTPException;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.kt.giga.home.openapi.ghms.common.DeviceType;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.SpotDevItemNm;
import com.kt.giga.home.openapi.ghms.common.PushType;
import com.kt.giga.home.openapi.ghms.common.service.ECService;
import com.kt.giga.home.openapi.ghms.devices.dao.InitDeviceAddDao;
import com.kt.giga.home.openapi.ghms.devices.domain.key.SpotDevBasKey;
import com.kt.giga.home.openapi.ghms.devices.service.DevicesService;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDevDtl;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.query.SpotDevBasQueryVO;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.query.SpotDevBasVO;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.query.SpotDevBasVO.SpotDevDtlVO;
import com.kt.giga.home.openapi.ghms.ktInfra.dao.GhmsProvisionDao;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.GhmsProvisionKey;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SpotDevBas;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SpotDevExpnsnBas;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SvcTgtBas;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;
import com.kt.giga.home.openapi.ghms.util.string.StringUtils;

/**
 * 
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
 * @since 2015. 3. 10.
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
public class GhmsProvisionService {
    
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private GhmsProvisionDao ghmsProvisionDao;
    
    @Autowired
    private InitDeviceAddDao initDeviceAddDao;
    
    @Autowired
    private ECService ecService;
    
    @Autowired
    private DevicesService devicesService;
    
    public static final String EC_RSLT_SUCCESS = "100";                         // 정상처리
    public static final String EC_RSLT_REDUNDANT_PROCESSING_SUCCESS = "110";    // 정상처리 되었으나 처리시 중복데이터가 존재하여 삭제처리함.
    public static final String EC_RSLT_CACHE_NOTI_FAIL = "120";                 // 정상 처리후 Cache전파 전체 실패
    public static final String EC_RSLT_CACHE_NOTI_PART_FAIL = "121";            // 정상 처리후 Cache전파 일부 실패
    
    public static final String RESULT_SUCCESS = "1";                            // 프로비저닝 연동 성공
    public static final String RESULT_FAIL = "2";                               // 프로비저닝 연동 실패
    
    public static final String ROLLBACK_USE = "0002";                           // 정상이용
    public static final String ROLLBACK_STOP = "0003";                          // 이용정지
    public static final String ROLLBACK_EXPIRE = "0004";                        // 해지
    public static final String ROLLBACK_DELETE = "0005";                        // 삭제(신규 명령에 대한 롤백)
    
    
    public Map<String, Object> setProvision(GhmsProvisionKey key) throws APIException {
        String gwCode           = key.getGwCode();
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            // 서비스 청약 정보 조회
            SvcTgtBas svcTgtBas = ghmsProvisionDao.selectSvcTgtBasBySvcContId(key.getSaId());
            
            // 장치 정보 조회
            SpotDevBas reqSpotDevBas = new SpotDevBas();
            SpotDevBas spotDevBas = null;
            if(svcTgtBas != null) {
                reqSpotDevBas.setSvc_tgt_seq(svcTgtBas.getSvc_tgt_seq());
                reqSpotDevBas.setSpot_dev_id(key.getGwSaid());
                spotDevBas = ghmsProvisionDao.selectSpotDevBasBySpotDevId(reqSpotDevBas);
            }
            
            /* 신규 등록 */
            if("M011".equals(gwCode)) {
                long svcTgtSeq = 0;
                
                if(svcTgtBas == null) { // 신규 청약 데이터가 존재 하지 않을시
                    svcTgtSeq = ghmsProvisionDao.selectSvcTgtSeq(); // 서비스일련번호생성
                    
                    // OpenAPI 서비스 데이터 등록
                    this.insertSvcTgtBas(key, svcTgtSeq);
                } else {
                    svcTgtSeq = svcTgtBas.getSvc_tgt_seq();
                    
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("svc_tgt_seq", svcTgtSeq);
                    map.put("svc_tgt_id", key.getCustid());
                    map.put("svc_tgt_nm", key.getCustName());
                    map.put("oprt_sttus_cd", "0001");
                    map.put("use_yn", "Y");
                    map.put("del_yn", "N");
                    
                    // OpenAPI 서비스 데이터 변경
                    this.updateSvcTgtBas(map);
                }
                
                // 임의고객청약내역 데이터 갱신
                this.insertUpdateServiceNo(svcTgtSeq, key.getServiceNo());
                
                // 삭제되지 않은 목록 조회
                //List<SpotDevBas> list = ghmsProvisionDao.selectSpotDevBasListByUse(svcTgtSeq);
                
            	// 동일 타입의 장비 갯수를 가져와서 장비 이름 뒤에 숫자를 붙힌다.
                SvcTgtBas skey = new SvcTgtBas();
                skey.setDstr_cd(CommonConstants.DSTR_CD);
                skey.setSvc_theme_cd(CommonConstants.SVC_THEME_CD);
                skey.setUnit_svc_cd(CommonConstants.UNIT_SVC_CD);
                skey.setSvc_tgt_type_cd(CommonConstants.GHMS_GW_SVC_TGT_TYPE_CD);
                skey.setSvc_tgt_id(key.getCustid());
                int typeCount = initDeviceAddDao.selectSoptDevBasGateWayCount(skey);
            	key.setDevNm(DeviceType.GATE_WAY.getModelNm() + StringUtils.getLPad(String.valueOf(typeCount), "0", 2));
                
                String rpyCode = EC_RSLT_SUCCESS;
                // 기존 장치 정보가 없는 경우
                if(spotDevBas == null) {
                	long spotDevSeq = ghmsProvisionDao.selectSpotDevSeq(svcTgtSeq);  // 현장장치일련번호조회
                    String devUUID = UUID.randomUUID().toString();
                    
                    // OpneAPI 장치 데이터 등록
                    this.insertSpotDevBas(key, svcTgtSeq, spotDevSeq, devUUID);
                    
                    // OpenAPI 장치 확장 데이터 등록
                    this.insertOpenAPIExpnsnBas(key, svcTgtSeq, spotDevSeq);
                    
                    // 삭제할 장치가 있다면 기존 장치 삭제
                    //this.deleteSpotDevBas(list, spotDevSeq, svcTgtSeq);
                    
                    // EC 장치 등록
                    rpyCode = this.insertECDev(key, svcTgtSeq, spotDevSeq, devUUID);
                    
                } else { // 기존 장치가 존재하는 경우
                    long spotDevSeq = spotDevBas.getSpot_dev_seq();
                    
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("svc_tgt_seq", svcTgtSeq);
                    map.put("spot_dev_seq", spotDevSeq);
                    map.put("athn_no", key.getGwSecret());
                    map.put("dev_sttus_cd", "00");
                    map.put("use_yn", "Y");
                    map.put("del_yn", "N");
                    map.put("del_nm", key.getDevNm());
                    
                    map.put("gw_mac", key.getGwMac());
                    map.put("gw_serial", key.getGwSerial());
                    map.put("gw_model_cd", key.getGwModelCd());
                    map.put("gw_model_name", key.getGwModelName());
                    
                    // OpenAPI 장치 데이터 변경
                    this.updateSpotDevBas(map);
                    
                    // OpenAPI 장치 확장 데이터 변경
                    this.updateOpenAPIExpnsnBas(map);
                    
                    // EC 장치 변경
                    if("Y".equals(spotDevBas.getDel_yn())) {
                        this.insertECDev(key, svcTgtSeq, spotDevSeq, spotDevBas.getDev_uu_id());
                    }
                    rpyCode = this.updateECDev(spotDevBas, map);
                }
                
                // EC 등록 결과에 따른 롤백 처리
                if(rpyCode.equals(EC_RSLT_SUCCESS) || rpyCode.equals(EC_RSLT_REDUNDANT_PROCESSING_SUCCESS)
                        || rpyCode.equals(EC_RSLT_CACHE_NOTI_FAIL) || rpyCode.equals(EC_RSLT_CACHE_NOTI_PART_FAIL)) {
                    resultMap.put("resultCode", RESULT_SUCCESS);
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    resultMap.put("resultCode", RESULT_FAIL);
                }
                
                
            }else if("M020".equals(gwCode)) {   // 이용중지
                if(spotDevBas == null) {
                    throw new HTTPException(HttpStatus.SC_NOT_FOUND);
                }
                
                long svcTgtSeq  = svcTgtBas.getSvc_tgt_seq();
                long spotDevSeq = spotDevBas.getSpot_dev_seq();
                
            	// 동일 타입의 장비 갯수를 가져와서 장비 이름 뒤에 숫자를 붙힌다.
                SvcTgtBas skey = new SvcTgtBas();
                skey.setDstr_cd(CommonConstants.DSTR_CD);
                skey.setSvc_theme_cd(CommonConstants.SVC_THEME_CD);
                skey.setUnit_svc_cd(CommonConstants.UNIT_SVC_CD);
                skey.setSvc_tgt_type_cd(CommonConstants.GHMS_GW_SVC_TGT_TYPE_CD);
                skey.setSvc_tgt_id(key.getCustid());
                int typeCount = initDeviceAddDao.selectSoptDevBasGateWayCount(skey);
            	key.setDevNm(DeviceType.GATE_WAY.getModelNm() + StringUtils.getLPad(String.valueOf(typeCount), "0", 2));
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("svc_tgt_seq", svcTgtSeq);
                map.put("spot_dev_seq", spotDevSeq);
                
                map.put("oprt_sttus_cd", "0002");
                map.put("dev_sttus_cd", "02");
                map.put("use_yn", "N");
                map.put("del_yn", "N");
                map.put("del_nm", key.getDevNm());
                
                // 삭제되지 않은 목록 조회
//                List<SpotDevBas> list = ghmsProvisionDao.selectSpotDevBasListByUse(svcTgtSeq);
                
                // OpenAPI 서비스 데이터 수정
                this.updateSvcTgtBas(map);
                
                // 임의고객청약내역 데이터 갱신
                this.insertUpdateServiceNo(svcTgtSeq, key.getServiceNo());
                
                // OpenAPI 장치 데이터 수정
                this.updateSpotDevBas(map);
                
                // 삭제할 장치가 있다면 기존 장치 삭제
//                this.deleteSpotDevBas(list, spotDevSeq, svcTgtSeq);
                
                // EC 장치 변경
                if("Y".equals(spotDevBas.getDel_yn())) {
                    this.insertECDev(key, svcTgtSeq, spotDevSeq, spotDevBas.getDev_uu_id());
                }
                String rpyCode = this.updateECDev(spotDevBas, map);
                
                // EC 등록 결과에 따른 롤백 처리
                if(rpyCode.equals(EC_RSLT_SUCCESS) || rpyCode.equals(EC_RSLT_REDUNDANT_PROCESSING_SUCCESS)
                        || rpyCode.equals(EC_RSLT_CACHE_NOTI_FAIL) || rpyCode.equals(EC_RSLT_CACHE_NOTI_PART_FAIL)) {
                    resultMap.put("resultCode", RESULT_SUCCESS);
                    
                    // 서비스 이용중지 통보 PUSH 
                    Map<String, String> data = new HashMap<String, String>();
                    data.put("eventId", PushType.USE_STOP_SERVICE.getEventId());
                    data.put("svcTgtSeq", String.valueOf(svcTgtSeq));
                    data.put("devNm", spotDevBas.getDev_nm());
                    data.put("msg", "사용중인 홈매니저 허브 \"" + spotDevBas.getDev_nm() + "\"이/가 일시정지 되었습니다.\n안내가 필요하실 경우, 고객센터(지역번호 + 100번)를 통해서 확인해 주시기 바랍니다.");
                    
                    devicesService.pushSend(null, svcTgtSeq, data, "MS");
                    
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    resultMap.put("resultCode", RESULT_FAIL);
                }
                
            } else if("M030".equals(gwCode)) {  // 이용 부활
                if(spotDevBas == null) {
                    throw new HTTPException(HttpStatus.SC_NOT_FOUND);
                }
                
                long svcTgtSeq   = svcTgtBas.getSvc_tgt_seq();
                long spotDevSeq  = spotDevBas.getSpot_dev_seq();
                
            	// 동일 타입의 장비 갯수를 가져와서 장비 이름 뒤에 숫자를 붙힌다.
                SvcTgtBas skey = new SvcTgtBas();
                skey.setDstr_cd(CommonConstants.DSTR_CD);
                skey.setSvc_theme_cd(CommonConstants.SVC_THEME_CD);
                skey.setUnit_svc_cd(CommonConstants.UNIT_SVC_CD);
                skey.setSvc_tgt_type_cd(CommonConstants.GHMS_GW_SVC_TGT_TYPE_CD);
                skey.setSvc_tgt_id(key.getCustid());
                int typeCount = initDeviceAddDao.selectSoptDevBasGateWayCount(skey);
            	key.setDevNm(DeviceType.GATE_WAY.getModelNm() + StringUtils.getLPad(String.valueOf(typeCount), "0", 2));
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("svc_tgt_seq", svcTgtSeq);
                map.put("spot_dev_seq", spotDevSeq);
                
                map.put("oprt_sttus_cd", "0001");
                map.put("dev_sttus_cd", "01");
                map.put("use_yn", "Y");
                map.put("del_yn", "N");
                map.put("del_nm", key.getDevNm());
                
                // 삭제되지 않은 목록 조회
//                List<SpotDevBas> list = ghmsProvisionDao.selectSpotDevBasListByUse(svcTgtSeq);
                
                // OpenAPI 서비스 데이터 수정
                this.updateSvcTgtBas(map);
                
                // 임의고객청약내역 데이터 갱신
                this.insertUpdateServiceNo(svcTgtSeq, key.getServiceNo());
                
                // OpneAPI 장치 데이터 수정
                this.updateSpotDevBas(map);
                
                // 삭제할 장치가 있다면 기존 장치 삭제
//                this.deleteSpotDevBas(list, spotDevSeq, svcTgtSeq);
                
                // EC 장치 변경
                if("Y".equals(spotDevBas.getDel_yn())) {
                    this.insertECDev(key, svcTgtSeq, spotDevSeq, spotDevBas.getDev_uu_id());
                }
                String rpyCode = this.updateECDev(spotDevBas, map);
                
                // EC 등록 결과에 따른 롤백 처리
                if(rpyCode.equals(EC_RSLT_SUCCESS) || rpyCode.equals(EC_RSLT_REDUNDANT_PROCESSING_SUCCESS)
                        || rpyCode.equals(EC_RSLT_CACHE_NOTI_FAIL) || rpyCode.equals(EC_RSLT_CACHE_NOTI_PART_FAIL)) {
                    resultMap.put("resultCode", RESULT_SUCCESS);
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    resultMap.put("resultCode", RESULT_FAIL);
                }
                
                
            } else if("M050".equals(gwCode)) {  // 해지
                if(spotDevBas == null) {
                    throw new HTTPException(HttpStatus.SC_NOT_FOUND);
                }
                
                long svcTgtSeq = svcTgtBas.getSvc_tgt_seq();
                long spotDevSeq = spotDevBas.getSpot_dev_seq();
                
            	// 동일 타입의 장비 갯수를 가져와서 장비 이름 뒤에 숫자를 붙힌다.
                SvcTgtBas skey = new SvcTgtBas();
                skey.setDstr_cd(CommonConstants.DSTR_CD);
                skey.setSvc_theme_cd(CommonConstants.SVC_THEME_CD);
                skey.setUnit_svc_cd(CommonConstants.UNIT_SVC_CD);
                skey.setSvc_tgt_type_cd(CommonConstants.GHMS_GW_SVC_TGT_TYPE_CD);
                skey.setSvc_tgt_id(key.getCustid());
                int typeCount = initDeviceAddDao.selectSoptDevBasGateWayCount(skey);
            	key.setDevNm(DeviceType.GATE_WAY.getModelNm() + StringUtils.getLPad(String.valueOf(typeCount), "0", 2));
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("svc_tgt_seq", svcTgtSeq);
                map.put("spot_dev_seq", spotDevSeq);
                
                map.put("oprt_sttus_cd", "0003");
                map.put("dev_sttus_cd", "02");
                map.put("use_yn", "N");
                map.put("del_yn", "Y");
                map.put("del_nm", key.getDevNm());
                
                // 삭제되지 않은 목록 조회
                List<SpotDevBas> list = ghmsProvisionDao.selectSpotDevBasListByUse(svcTgtSeq);
                
                // OpenAPI 서비스 데이터 수정
                this.updateSvcTgtBas(map);
                
                // 임의고객청약내역 데이터 갱신
                this.insertUpdateServiceNo(svcTgtSeq, key.getServiceNo());
                
                // OpenAPI 장치 데이터 수정
                this.updateSpotDevBas(map);
                
                // 삭제할 장치가 있다면 기존 장치 삭제
                this.deleteSpotDevBas(list, spotDevSeq, svcTgtSeq);
                
                // EC 장치 삭제
                String rpyCode = this.deleteECDev(svcTgtSeq, spotDevSeq);
                
                // EC 등록 결과에 따른 롤백 처리
                if(rpyCode.equals(EC_RSLT_SUCCESS) || rpyCode.equals(EC_RSLT_REDUNDANT_PROCESSING_SUCCESS)
                        || rpyCode.equals(EC_RSLT_CACHE_NOTI_FAIL) || rpyCode.equals(EC_RSLT_CACHE_NOTI_PART_FAIL)) {
                    resultMap.put("resultCode", RESULT_SUCCESS);
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    resultMap.put("resultCode", RESULT_FAIL);
                }
                
            } else if("M021".equals(gwCode) || "M031".equals(gwCode) || "M040".equals(gwCode)) {    // 정보 변경
                if(spotDevBas == null) {
                    throw new HTTPException(HttpStatus.SC_NOT_FOUND);
                }
            	long svcTgtSeq = svcTgtBas.getSvc_tgt_seq();
            	long spotDevSeq = spotDevBas.getSpot_dev_seq();
                
            	// 동일 타입의 장비 갯수를 가져와서 장비 이름 뒤에 숫자를 붙힌다.
                SvcTgtBas skey = new SvcTgtBas();
                skey.setDstr_cd(CommonConstants.DSTR_CD);
                skey.setSvc_theme_cd(CommonConstants.SVC_THEME_CD);
                skey.setUnit_svc_cd(CommonConstants.UNIT_SVC_CD);
                skey.setSvc_tgt_type_cd(CommonConstants.GHMS_GW_SVC_TGT_TYPE_CD);
                skey.setSvc_tgt_id(key.getCustid());
                int typeCount = initDeviceAddDao.selectSoptDevBasGateWayCount(skey);
            	key.setDevNm(DeviceType.GATE_WAY.getModelNm() + StringUtils.getLPad(String.valueOf(typeCount), "0", 2));
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("svc_tgt_seq", svcTgtSeq);
                map.put("spot_dev_seq", spotDevSeq);
                map.put("svc_tgt_id", key.getCustid());
                map.put("svc_tgt_nm", key.getCustName());
                if(!svcTgtBas.getSvc_tgt_id().equals(key.getCustid())) { // 명의 변경 처리
                    map.put("mbr_seq", "");
                }
                map.put("spot_dev_id", key.getGwSaid());
                map.put("athn_no", key.getGwSecret());
                map.put("gw_mac", key.getGwMac());
                map.put("gw_serial", key.getGwSerial());
                map.put("gw_model_cd", key.getGwModelCd());
                map.put("gw_model_name", key.getGwModelName());
                map.put("del_nm", key.getDevNm());
                
                if("M021".equals(gwCode)) { // 이용중지
                    map.put("oprt_sttus_cd", "0002");
                    map.put("dev_sttus_cd", "02");
                    map.put("use_yn", "N");
                    map.put("del_yn", "N");
                } else if("M031".equals(gwCode)) { // 이용부활
                    map.put("oprt_sttus_cd", "0001");
                    map.put("dev_sttus_cd", "01");
                    map.put("use_yn", "Y");
                    map.put("del_yn", "N");
                }
                
                // 삭제되지 않은 목록 조회
//                List<SpotDevBas> list = ghmsProvisionDao.selectSpotDevBasListByUse(svcTgtSeq);
                
                String rpyCode = EC_RSLT_SUCCESS;
                // OpenAPI 서비스 데이터 변경
                this.updateSvcTgtBas(map);
                
                // 임의고객청약내역 데이터 갱신
                this.insertUpdateServiceNo(svcTgtSeq, key.getServiceNo());
                
                // OpenAPI 장치 데이터 변경
                this.updateSpotDevBas(map);
                
                // OpenAPI 장치 확장 데이터 변경
                this.updateOpenAPIExpnsnBas(map);
                
                // 삭제할 장치가 있다면 기존 장치 삭제
//                this.deleteSpotDevBas(list, spotDevSeq, svcTgtSeq);
                
                // EC 장치 변경
                if("Y".equals(spotDevBas.getDel_yn())) {
                    this.insertECDev(key, svcTgtSeq, spotDevSeq, spotDevBas.getDev_uu_id());
                }
                rpyCode = this.updateECDev(spotDevBas, map);
                
                // EC 등록 결과에 따른 롤백 처리
                if(rpyCode.equals(EC_RSLT_SUCCESS) || rpyCode.equals(EC_RSLT_REDUNDANT_PROCESSING_SUCCESS)
                        || rpyCode.equals(EC_RSLT_CACHE_NOTI_FAIL) || rpyCode.equals(EC_RSLT_CACHE_NOTI_PART_FAIL)) {
                    resultMap.put("resultCode", RESULT_SUCCESS);
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    resultMap.put("resultCode", RESULT_FAIL);
                }
                
            } else if("M070".equals(gwCode)) {  // 원부연동, 롤백
                long svcTgtSeq = 0;
                String rpyCode = EC_RSLT_SUCCESS;
                
                if(svcTgtBas == null) { // 신규 청약 데이터가 존재 하지 않을시
                    svcTgtSeq = ghmsProvisionDao.selectSvcTgtSeq();
                    
                    // OpenAPI 서비스 데이터 등록
                    this.insertSvcTgtBas(key, svcTgtSeq);
                    
                } else {
                    svcTgtSeq = svcTgtBas.getSvc_tgt_seq();
                }
                
                // 임의고객청약내역 데이터 갱신
                this.insertUpdateServiceNo(svcTgtSeq, key.getServiceNo());
                
            	// 동일 타입의 장비 갯수를 가져와서 장비 이름 뒤에 숫자를 붙힌다.
                SvcTgtBas skey = new SvcTgtBas();
                skey.setDstr_cd(CommonConstants.DSTR_CD);
                skey.setSvc_theme_cd(CommonConstants.SVC_THEME_CD);
                skey.setUnit_svc_cd(CommonConstants.UNIT_SVC_CD);
                skey.setSvc_tgt_type_cd(CommonConstants.GHMS_GW_SVC_TGT_TYPE_CD);
                skey.setSvc_tgt_id(key.getCustid());
                int typeCount = initDeviceAddDao.selectSoptDevBasGateWayCount(skey);
            	key.setDevNm(DeviceType.GATE_WAY.getModelNm() + StringUtils.getLPad(String.valueOf(typeCount), "0", 2));
                
                // 삭제되지 않은 목록 조회
                List<SpotDevBas> list = ghmsProvisionDao.selectSpotDevBasListByUse(svcTgtSeq);
                
                if(spotDevBas == null) {    // 기존 장치가 없는 경우
                	long spotDevSeq = ghmsProvisionDao.selectSpotDevSeq(svcTgtSeq);
                    String devUUID = UUID.randomUUID().toString();
                    
                    // OpenAPI 장치 데이터 등록
                    this.insertSpotDevBas(key, svcTgtSeq, spotDevSeq, devUUID);
                    
                    // OpenAPI 장치 확장 데이터 등록
                    this.insertOpenAPIExpnsnBas(key, svcTgtSeq, spotDevSeq);
                    
                    // 삭제할 장치가 있다면 기존 장치 삭제
                    this.deleteSpotDevBas(list, spotDevSeq, svcTgtSeq);
                    
                    // EC 장치 등록
                    rpyCode = this.insertECDev(key, svcTgtSeq, spotDevSeq, devUUID);
                    
                    // OpenAPI 서비스 조회
                    svcTgtBas = ghmsProvisionDao.selectSvcTgtBasBySvcContId(key.getSaId());
                    
                } else {    // 기존 장치가 존재하는 경우
                	long spotDevSeq = spotDevBas.getSpot_dev_seq();
                    
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("svc_tgt_seq", svcTgtSeq);
                    map.put("spot_dev_seq", spotDevSeq);
                    map.put("svc_tgt_id", key.getCustid());
                    map.put("svc_tgt_nm", key.getCustName());
                    if(!svcTgtBas.getSvc_tgt_id().equals(key.getCustid())) { // 명의 변경 처리
                        map.put("mbr_seq", "");
                    }
                    map.put("spot_dev_id", key.getGwSaid());
                    map.put("athn_no", key.getGwSecret());
                    map.put("gw_mac", key.getGwMac());
                    map.put("gw_serial", key.getGwSerial());
                    map.put("gw_model_cd", key.getGwModelCd());
                    map.put("gw_model_name", key.getGwModelName());
                    map.put("del_nm", key.getDevNm());
                    
                    if(ROLLBACK_USE.equals(key.getRollbackState())) {
                        map.put("oprt_sttus_cd", "0001");
                        map.put("del_yn", "N");
                        map.put("dev_sttus_cd", "01");
                        map.put("use_yn", "Y");
                        
                    } else if(ROLLBACK_STOP.equals(key.getRollbackState())) {
                        map.put("oprt_sttus_cd", "0002");
                        map.put("del_yn", "N");
                        map.put("dev_sttus_cd", "02");
                        map.put("use_yn", "N");
                        
                    } else if(ROLLBACK_EXPIRE.equals(key.getRollbackState())) {
                        map.put("oprt_sttus_cd", "0003");
                        map.put("del_yn", "Y");
                        map.put("dev_sttus_cd", "02");
                        map.put("use_yn", "N");
                        
                    } else if(ROLLBACK_DELETE.equals(key.getRollbackState())) {
                        map.put("oprt_sttus_cd", "0003");
                        map.put("del_yn", "Y");
                        map.put("dev_sttus_cd", "02");
                        map.put("use_yn", "N");
                    }
                    
                    // OpenAPI 서비스 데이터 변경
                    this.updateSvcTgtBas(map);
                    
                    // OpenAPI 장치 데이터 변경
                    this.updateSpotDevBas(map);
                    
                    // OpenAPI 장치 확장 데이터 변경
                    this.updateOpenAPIExpnsnBas(map);
                    
                    if(ROLLBACK_EXPIRE.equals(key.getRollbackState()) || ROLLBACK_DELETE.equals(key.getRollbackState())) {
                        // 삭제할 장치가 있다면 기존 장치 삭제
                        this.deleteSpotDevBas(list, spotDevSeq, svcTgtSeq);
                    }
                    
                    // EC 장치 변경
                    if(ROLLBACK_EXPIRE.equals(key.getRollbackState()) || ROLLBACK_DELETE.equals(key.getRollbackState())) {
                        rpyCode = this.deleteECDev(svcTgtSeq, spotDevSeq);
                        
                    } else {
                        if("Y".equals(svcTgtBas.getDel_yn())) {
                            this.insertECDev(key, svcTgtSeq, spotDevSeq, spotDevBas.getDev_uu_id());
                        }
                        rpyCode = this.updateECDev(spotDevBas, map);
                    }
                }
                
                // EC 등록 결과에 따른 롤백 처리
                if(rpyCode.equals(EC_RSLT_SUCCESS) || rpyCode.equals(EC_RSLT_REDUNDANT_PROCESSING_SUCCESS)
                        || rpyCode.equals(EC_RSLT_CACHE_NOTI_FAIL) || rpyCode.equals(EC_RSLT_CACHE_NOTI_PART_FAIL)) {
                    resultMap.put("resultCode", RESULT_SUCCESS);
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    resultMap.put("resultCode", RESULT_FAIL);
                }
                
            }
            
            
        } catch(HTTPException e){
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
        
        return resultMap;
    }
    
    /**
     * OpenAPI 서비스 생성
     * 
     * @param key
     * @param svcTgtSeq
     */
    public void insertSvcTgtBas(GhmsProvisionKey key, long svcTgtSeq) {
        SvcTgtBas svcTgtBas = new SvcTgtBas();
        
        svcTgtBas.setDstr_cd(CommonConstants.DSTR_CD);
        svcTgtBas.setSvc_theme_cd(CommonConstants.SVC_THEME_CD);
        svcTgtBas.setUnit_svc_cd(CommonConstants.UNIT_SVC_CD);
        svcTgtBas.setSvc_tgt_seq(svcTgtSeq);
        svcTgtBas.setSvc_tgt_id(key.getCustid());       // cust아이디
        svcTgtBas.setSvc_tgt_nm(key.getCustName());     // cust이름
        svcTgtBas.setSvc_cont_id(key.getSaId());        // 청약아이디
        svcTgtBas.setOprt_sttus_cd("0001");             // 운영상태코드 설정
        svcTgtBas.setDel_yn("N");                       // 삭제 여부
        svcTgtBas.setSvc_tgt_type_cd(CommonConstants.GHMS_GW_SVC_TGT_TYPE_CD);			// 서비스대상유형코드(허브:0000, AP:0001);
        
        ghmsProvisionDao.insertSvcTgtBas(svcTgtBas);
        
    }
    
    /**
     * OpenAPI 서비스 변경
     * 
     * @param map
     */
    public void updateSvcTgtBas(Map<String, Object> map) {
        ghmsProvisionDao.updateSvcTgtBas(map);
    }
    
    /**
     * OpenAPI 장치 생성
     * 
     * @param key
     * @param svcTgtSeq     서비스 대상 일련번호
     * @param spotDevSeq    현장 장치 일련번호
     * @param gwUUID        게이트웨이 UUID
     */
    public void insertSpotDevBas(GhmsProvisionKey key, long svcTgtSeq, long spotDevSeq, String devUUID) {
        SpotDevBas spotDevBas = new SpotDevBas();
        
        // 장치모델일련번호 조회
        long devModelSeq = ghmsProvisionDao.selectDevModelSeq(key.getGwModelCd());
        
        spotDevBas.setSvc_tgt_seq(svcTgtSeq);
        spotDevBas.setSpot_dev_seq(spotDevSeq);
        spotDevBas.setDev_model_seq(devModelSeq);
        spotDevBas.setDev_uu_id(devUUID);                // 장치 UUID
        spotDevBas.setSpot_dev_id(key.getGwSaid());     // 현장장치아이디
        spotDevBas.setAthn_no(key.getGwSecret());       // 인증번호
        spotDevBas.setDev_nm(key.getDevNm());           // 장치명
        spotDevBas.setUse_yn("Y");                      // 사용여부
        spotDevBas.setDev_sttus_cd("00");               // 장치 상태 - 인증 대기
        
        ghmsProvisionDao.insertSpotDevBas(spotDevBas);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("svcTgtSeq", svcTgtSeq);
        map.put("spotDevSeq", spotDevSeq);
        List<Map<String, Object>> snsnTagList = initDeviceAddDao.selectSnsnTagCd(map);
        for(Map<String, Object> snsnTagMap : snsnTagList){
            map.put("snsnTgtCd", snsnTagMap.get("snsnTgtCd"));
            initDeviceAddDao.insertSpotDevSetupBas(map);
        }
        
    }
    
    /**
     * OpenAPI 장치 확장 정보 생성
     * 
     * @param key
     * @param svcTgtSeq
     * @param spotDevSeq
     */
    public void insertOpenAPIExpnsnBas(GhmsProvisionKey key, long svcTgtSeq, long spotDevSeq) {
        SpotDevExpnsnBas sdeb = new SpotDevExpnsnBas();

        // TODO dahye 아직 KEY에 대한 정의가 정확히 이루어지지 않았음..
        // 홈캠 쪽 Key 그대로 사용..
        Map<String, String> map = new HashMap<String, String>();
        
        map.put(SpotDevItemNm.GW_MAC, key.getGwMac());
        map.put(SpotDevItemNm.GW_SEREIAL, key.getGwSerial()); 
        map.put(SpotDevItemNm.GW_SECRET, key.getGwSecret());
        /*
        map.put(SpotDevItemNm.GW_MODEL_CD, key.getGwModelCd());
        map.put(SpotDevItemNm.GW_MODEL_NM, key.getGwModelName());
        map.put(SpotDevItemNm.AP_NAME, "");
        map.put(SpotDevItemNm.AP_POWER, "");
        map.put(SpotDevItemNm.PRIVACY, "1");
        map.put(SpotDevItemNm.STORAGE_PNS, "");
        map.put(SpotDevItemNm.MOVING_PNS, "");
        map.put(SpotDevItemNm.SAVE_MODE, "");
        map.put(SpotDevItemNm.REVERTED, "");
        map.put(SpotDevItemNm.RESOLUTION, "");
        map.put(SpotDevItemNm.V_SENSITIVITY, "");
        map.put(SpotDevItemNm.M_SENSITIVITY, "");
        map.put(SpotDevItemNm.DETECTION, "");
        map.put(SpotDevItemNm.DETECTION_MODE, "");
        map.put(SpotDevItemNm.LAST_DETECTION_TIME, "");
        map.put(SpotDevItemNm.CON_STAT, "");
        map.put(SpotDevItemNm.CON_COUNT, "0");
        map.put(SpotDevItemNm.UPGRADE_STATUS, "0");
        map.put(SpotDevItemNm.UPGRADE_START_TIME, "");
        map.put(SpotDevItemNm.UCLOUD_STATUS, "0");*/
        
        Set<String> keyMap = map.keySet();
        for(Iterator<String> it = keyMap.iterator(); it.hasNext();){    // 현장장치 상세 등록
            String keyNm = it.next();
            String val = map.get(keyNm);
            
            sdeb.setSvc_tgt_seq(svcTgtSeq);   // 현장장치항목값 
            sdeb.setSpot_dev_seq(spotDevSeq); // 현장장치값
            sdeb.setSpot_dev_item_nm(keyNm);
            sdeb.setSpot_dev_item_val(val);
            
            ghmsProvisionDao.insertSpotDevExpnsnBas(sdeb);
        }
        
 
    }
    
    /**
     * OpenAPI 장치 삭제
     * 
     * @param list
     * @param spotDevSeq
     */
    public void deleteSpotDevBas(List<SpotDevBas> list, long spotDevSeq, long svcTgtSeq){
        for(SpotDevBas spotDevBas : list) {
            if(spotDevBas.getSpot_dev_seq() != spotDevSeq) {
              Map<String, Object> map = new HashMap<String, Object>();
              map.put("svc_tgt_seq", spotDevBas.getSvc_tgt_seq());
              map.put("spot_dev_seq", spotDevBas.getSpot_dev_seq());
              map.put("oprt_sttus_cd", "0003");
              map.put("dev_sttus_cd", "02");
              map.put("use_yn", "N");
              map.put("del_yn", "Y");
              
              // OpenAPI 장치 데이터 수정
              updateSpotDevBas(map);
              
              // EC 장치 삭제
              deleteECDev(spotDevBas.getSvc_tgt_seq(), spotDevBas.getSpot_dev_seq());
              
            }
        }
    }
    
    
    /**
     * OpenAPI 장치 변경
     * 
     * @param map
     */
    public void updateSpotDevBas(Map<String, Object> map) {
        ghmsProvisionDao.updateSpotDevBas(map);
    }
    
    
    /**
     * EC 장치 삭제
     * 
     * @param svcTgtSeq
     * @param spotDevSeq
     * @return
     */
    public String deleteECDev(long svcTgtSeq, long spotDevSeq) {
        SpotDevBasVO vo = new SpotDevBasVO();
        
        vo.setSvcTgtSeq(svcTgtSeq);
        vo.setSpotDevSeq(spotDevSeq);
        
        SpotDevBasQueryVO queryVo = ecService.sendDeleteQueryToEC(vo);
        
        return queryVo.getRpyCode();
    }
    
    /**
     * EC 장치 생성
     * 
     * @param key
     * @param svcTgtBasSeq  서비스 대상 일련 번호
     * @param spotDevSeq    현장 장치 일련 번호 
     * @param devUUID
     * @return
     */
    public String insertECDev(GhmsProvisionKey key, long svcTgtSeq, long spotDevSeq, String devUUID) {

        SpotDevBasVO vo = new SpotDevBasVO();
        
        Map<String, Object> modelMap = ghmsProvisionDao.selectDevModelBas(key.getGwModelCd());
        
        // 1. 현장 장치 기본
        vo.setSvcTgtSeq(svcTgtSeq);
        vo.setSpotDevSeq(spotDevSeq);
        vo.setSpotDevId(key.getGwSaid());
        vo.setGwCnctId("HOME_IOT");                 // 게이트웨이연결 ID (홈매니저는 프로비저닝할때 알수없으므로 빈값)
        vo.setSpotDevNm(key.getDevNm());
        vo.setSttusCd("0001");
        vo.setColecCyclTime(30);
        vo.setRowRstrtnYn("N");
        if(modelMap != null) {
            vo.setMakrNm(modelMap.get("terml_makr_nm") == null ? null : modelMap.get("terml_makr_nm").toString());
            vo.setModelNm(modelMap.get("dev_model_cd") == null ? null : modelMap.get("dev_model_cd").toString());
        } else {
            vo.setMakrNm(null);
            vo.setModelNm(null);
        }
        vo.setFrmwrVerNo("01.00");
        vo.setAthnFormlCd("0004");
//        vo.setAthnNo("");
        vo.setAthnRqtNo(StringUtils.nvl(key.getGwSecret(), "1001"));
//        vo.setAthnRqtNo("1001");
        vo.setUseYn("Y");
        vo.setDevConnTypeCd("02");
        
        // 2. 현장 장치 상세
        List<SpotDevDtlVO> spotDevDtlList = new ArrayList<SpotDevDtlVO>();
        
        SpotDevDtlVO voDtl = new SpotDevDtlVO();
        voDtl.setAtribNm(SpotDevItemNm.GW_MAC);
        voDtl.setAtribVal(key.getGwMac());
        spotDevDtlList.add(voDtl);

        if(key.getGwSerial() != null && !"".equals(key.getGwSerial())) {
            voDtl = new SpotDevDtlVO();
            voDtl.setAtribNm(SpotDevItemNm.GW_SEREIAL);
            voDtl.setAtribVal(key.getGwSerial());
            spotDevDtlList.add(voDtl);	
        }
        
        if(key.getGwSecret() != null && !"".equals(key.getGwSecret())) {
            voDtl = new SpotDevDtlVO();
            voDtl.setAtribNm(SpotDevItemNm.GW_SECRET);
            voDtl.setAtribVal(key.getGwSecret());
            spotDevDtlList.add(voDtl);	
        }
        
        vo.setSpotDevDtlVOs(spotDevDtlList);
        
        SpotDevBasQueryVO queryVO = ecService.sendInsertQueryToEC(vo);
        
        return queryVO.getRpyCode();
    }
    
    /**
     * EC 장치 생성
     * 
     * @param key
     * @param svcTgtBasSeq  서비스 대상 일련 번호
     * @param spotDevSeq    현장 장치 일련 번호 
     * @param devUUID
     * @param deviceType    현장장치 구분 (GW:게이트웨이, DL:도어락, GA:가스벨브, TS:침입감지)
     * @return
     */
    public String insertECDevSpotDevAdd(SpotDevBasKey key) throws Exception {
        SpotDevBasVO vo = new SpotDevBasVO();
        
        Map<String, Object> modelMap = ghmsProvisionDao.selectDevModelBas(key.getDevModelCd());
        
        // 1. 현장 장치 기본
        vo.setSvcTgtSeq(key.getSvcTgtSeq());
        vo.setSpotDevSeq(key.getSpotDevSeq());
        vo.setSpotDevId(key.getSpotDevId()); // SpotDevId
        vo.setGwCnctId("HOME_IOT"); // upGwCnctId 'HOME_IOT'
        vo.setSpotDevNm(key.getDevNm());
        vo.setSttusCd("0001");
        vo.setColecCyclTime((int)key.getColecCyclTime());
        vo.setRowRstrtnYn("N");
        if(modelMap != null) {
            vo.setMakrNm(modelMap.get("terml_makr_nm") == null ? null : modelMap.get("terml_makr_nm").toString());
            vo.setModelNm(modelMap.get("dev_model_cd") == null ? null : modelMap.get("dev_model_cd").toString());
        } else {
            vo.setMakrNm(null);
            vo.setModelNm(null);
        }
        vo.setFrmwrVerNo(key.getFrmwrVerNo());
        vo.setAthnFormlCd(key.getAthnFormlCd());
//        vo.setAthnRqtNo("1001");
        vo.setAthnNo(key.getAthnNo());
        vo.setUseYn("Y");
        if(DeviceType.GATE_WAY.getCode().equals(key.getDevTypeCd())) {
            vo.setDevConnTypeCd("02");	
        } else {
            vo.setDevConnTypeCd("03");	
        }
        
        vo.setUpGwCnctId(key.getUpGwCnctId());
        vo.setUpSpotDevId(key.getUpSpotDevId());
        vo.setUpSvcTgtSeq(key.getUpSvcTgtSeq());
        
        // 2. 현장 장치 상세
        List<SpotDevDtlVO> spotDevDtlList = new ArrayList<SpotDevDtlVO>();
        vo.setSpotDevDtlVOs(spotDevDtlList);
        
        List<SpotDevDtl> spotDevDtls = key.getSpotDevDtls();
        for(SpotDevDtl spotDevDtl : spotDevDtls) {
        	if(spotDevDtl.getAtribNm() != null && !"".equals(spotDevDtl.getAtribNm())) {
	        	SpotDevDtlVO voDtl = new SpotDevDtlVO();
	        	voDtl.setAtribNm(spotDevDtl.getAtribNm());
	        	voDtl.setAtribVal(spotDevDtl.getAtribVal());
	        	spotDevDtlList.add(voDtl);
        	}
        }

        SpotDevBasQueryVO queryVO = ecService.sendInsertQueryToEC(vo);
        
        return queryVO.getRpyCode();
    }
    
    /**
     * EC 장치 수정
     * 
     * @param key
     * @param svcTgtBasSeq  서비스 대상 일련 번호
     * @param spotDevSeq    현장 장치 일련 번호 
     * @param devUUID
     * @param deviceType    현장장치 구분 (GW:게이트웨이, DL:도어락, GA:가스벨브, TS:침입감지)
     * @return
     */
    public String updateECDevSpotDevAdd(SpotDevBasKey key) throws Exception {
        SpotDevBasVO vo = new SpotDevBasVO();
        
        Map<String, Object> modelMap = ghmsProvisionDao.selectDevModelBas(key.getDevModelCd());
        
        // 1. 현장 장치 기본
        vo.setSvcTgtSeq(key.getSvcTgtSeq());
        vo.setSpotDevSeq(key.getSpotDevSeq());
        vo.setSpotDevId(key.getSpotDevId()); // SpotDevId
        vo.setGwCnctId("HOME_IOT"); // upGwCnctId 'HOME_IOT'
        vo.setSpotDevNm(key.getDevNm());
        vo.setSttusCd("0001");
        vo.setColecCyclTime((int)key.getColecCyclTime());
        vo.setRowRstrtnYn("N");
        if(modelMap != null) {
            vo.setMakrNm(modelMap.get("terml_makr_nm") == null ? null : modelMap.get("terml_makr_nm").toString());
            vo.setModelNm(modelMap.get("dev_model_cd") == null ? null : modelMap.get("dev_model_cd").toString());
        } else {
            vo.setMakrNm(null);
            vo.setModelNm(null);
        }
        vo.setFrmwrVerNo(key.getFrmwrVerNo());
        vo.setAthnFormlCd(key.getAthnFormlCd());
        vo.setAthnRqtNo(StringUtils.nvl(key.getAthnNo(), "1001"));
//        vo.setAthnNo("");
        vo.setUseYn("Y");
        if(DeviceType.GATE_WAY.getCode().equals(key.getDevTypeCd())) {
            vo.setDevConnTypeCd("02");	
        } else {
            vo.setDevConnTypeCd("03");	
        }
        
        vo.setUpGwCnctId(key.getUpGwCnctId());
        vo.setUpSpotDevId(key.getUpSpotDevId());
        vo.setUpSvcTgtSeq(key.getUpSvcTgtSeq());
        
        // 2. 현장 장치 상세
        List<SpotDevDtlVO> spotDevDtlList = new ArrayList<SpotDevDtlVO>();
        vo.setSpotDevDtlVOs(spotDevDtlList);
        
        List<SpotDevDtl> spotDevDtls = key.getSpotDevDtls();
        for(SpotDevDtl spotDevDtl : spotDevDtls) {
        	if(spotDevDtl.getAtribNm() != null && !"".equals(spotDevDtl.getAtribNm())) {
	        	SpotDevDtlVO voDtl = new SpotDevDtlVO();
	        	voDtl.setAtribNm(spotDevDtl.getAtribNm());
	        	voDtl.setAtribVal(spotDevDtl.getAtribVal());
	        	spotDevDtlList.add(voDtl);
        	}
        }

        SpotDevBasQueryVO queryVO = ecService.sendUpdateQueryToEC(vo);
        
        return queryVO.getRpyCode();
    }
    
    /**
     * OpenAPI 장치 확장 정보 수정
     * 
     * @param map
     */
    public void updateOpenAPIExpnsnBas(Map<String, Object> map) {
        SpotDevExpnsnBas spotDevExpnsnBas = new SpotDevExpnsnBas();
        
        Map<String, Object> expnsnMap = new HashMap<String, Object>();
        expnsnMap.put(SpotDevItemNm.GW_MAC, map.get("gw_mac"));
        expnsnMap.put(SpotDevItemNm.GW_SEREIAL, map.get("gw_serial"));
        expnsnMap.put(SpotDevItemNm.GW_SECRET, map.get("athn_no"));
        
        Set<String> key = expnsnMap.keySet();
        for(Iterator<String> it = key.iterator(); it.hasNext();) { // 현장장치 상세 등록
            String keyNm = it.next();
            String val = expnsnMap.get(keyNm) == null ? null : expnsnMap.get(keyNm).toString();
            
            spotDevExpnsnBas.setSvc_tgt_seq(Long.parseLong(map.get("svc_tgt_seq").toString())); 
            spotDevExpnsnBas.setSpot_dev_seq(Long.parseLong(map.get("spot_dev_seq").toString()));
            spotDevExpnsnBas.setSpot_dev_item_nm(keyNm);
            spotDevExpnsnBas.setSpot_dev_item_val(val);
            
            ghmsProvisionDao.updateSpotDevExpnsnBas(spotDevExpnsnBas);
        }
        
    }
    
    /**
     * EC 장치 수정
     * 
     * @param spotDevBas
     * @param map
     * @return
     */
    public String updateECDev(SpotDevBas spotDevBas, Map<String, Object> map) {

        SpotDevBasVO vo = new SpotDevBasVO();
        
        Map<String, Object> modelMap = ghmsProvisionDao.selectDevModelBas(map.get("gw_model_cd") == null ? null : map.get("gw_model_cd").toString());
        
        // 현장 장치 기본
        vo.setSvcTgtSeq(NumberUtils.toLong(map.get("svc_tgt_seq").toString()));
        vo.setSpotDevSeq(NumberUtils.toLong(map.get("spot_dev_seq").toString()));
        vo.setSpotDevId(map.get("spot_dev_id") == null ? null : map.get("spot_dev_id").toString());
        vo.setGwCnctId("");
        vo.setSttusCd(map.get("oprt_sttus_cd") == null ? null : map.get("oprt_sttus_cd").toString());
        vo.setRowRstrtnYn("N");
        if(modelMap != null) {
            vo.setMakrNm(modelMap.get("terml_makr_nm") == null ? null : modelMap.get("terml_makr_nm").toString());
            vo.setModelNm(modelMap.get("dev_model_cd") == null ? null : modelMap.get("dev_model_cd").toString());
        } else {
            vo.setMakrNm(null);
            vo.setModelNm(null);
        }
        vo.setSpotDevNm(spotDevBas.getDev_nm());
        vo.setAthnRqtNo(map.get("athn_no") == null ? null : map.get("athn_no").toString());
//        vo.setAthnNo("");
        vo.setUseYn(map.get("use_yn") == null ? null : map.get("use_yn").toString());
        vo.setDevConnTypeCd("02"); //장치연결유형 고정 : 02
        
        // 현장 장치 상세 등록
        List<SpotDevDtlVO> spotDevDtlList = new ArrayList<SpotDevDtlVO>();
        
        SpotDevDtlVO voDtl = new SpotDevDtlVO();
        voDtl.setAtribNm(SpotDevItemNm.GW_MAC);
        voDtl.setAtribVal(map.get("gw_mac") == null ? null : map.get("gw_mac").toString());
        spotDevDtlList.add(voDtl);

        if(map.get("gw_serial") != null && !"".equals(map.get("gw_serial").toString())) {
            voDtl = new SpotDevDtlVO();
            voDtl.setAtribNm(SpotDevItemNm.GW_SEREIAL);
            voDtl.setAtribVal(map.get("gw_serial") == null ? null : map.get("gw_serial").toString());
            spotDevDtlList.add(voDtl);	
        }
        
        if(map.get("athn_no") != null && !"".equals(map.get("athn_no").toString())) { 
            voDtl = new SpotDevDtlVO();
            voDtl.setAtribNm(SpotDevItemNm.GW_SECRET);
            voDtl.setAtribVal(map.get("athn_no").toString());
            spotDevDtlList.add(voDtl);	
        }

        vo.setSpotDevDtlVOs(spotDevDtlList);
        
        SpotDevBasQueryVO queryVO = ecService.sendUpdateQueryToEC(vo);
        
        return queryVO.getRpyCode();
    }
    
    /**
     * 임의고객청약내역 데이터 갱신
     * @param svcTgtSeq
     * @param serviceNo
     */
    public void insertUpdateServiceNo(long svcTgtSeq, String serviceNo) {
    	
    	if(ghmsProvisionDao.selectRanCustSubsTxnCount(svcTgtSeq, serviceNo) > 0) {
    		ghmsProvisionDao.updateRanCustSubsTxnCount(svcTgtSeq, serviceNo);
    	} else {
    		ghmsProvisionDao.insertRanCustSubsTxnCount(svcTgtSeq, serviceNo);
    	}
    	
    }
    
    /**
     * OpenAPI 장치 생성 (장치 교체 인 경우)
     * 
     * @param key
     * @param svcTgtSeq
     * @param spotDevSeq
     * @param devUUID
    @Deprecated
    public void insertOpenAPIDevBas(GhmsProvisionKey key, long svcTgtSeq, long spotDevSeq, String devUUID) {
        SpotDevBas spotBas = new SpotDevBas();
        
        long devModelSeq =  ghmsProvisionDao.selectDevModelSeq(key.getGwModelCd());  
        
        spotBas.setSvc_tgt_seq(svcTgtSeq);                
        spotBas.setSpot_dev_seq(spotDevSeq);
        spotBas.setDev_model_seq(devModelSeq);             
        spotBas.setDev_uu_id(devUUID);              // 장치 UUID
        spotBas.setSpot_dev_id(key.getGwSaid());    // 현장장치아이디
        spotBas.setAthn_no(key.getGwSecret());      // 인증번호
//      spotBas.setEqp_lo_sbst(key.getCctvLoc());   // 설치위치내용
        spotBas.setDev_nm("");                      // 장치명
        spotBas.setUse_yn("Y");                     // 사용여부
        spotBas.setDev_sttus_cd("00");              // 장치 상태 - 인증 대기
        
        ghmsProvisionDao.insertSpotDevBas(spotBas);
    }
     */
    
}
