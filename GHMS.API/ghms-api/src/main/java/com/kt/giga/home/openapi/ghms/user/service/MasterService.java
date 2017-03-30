/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kt.giga.home.openapi.ghms.common.PushType;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.ErrorCode;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.SpotDevAlram;
import com.kt.giga.home.openapi.ghms.common.domain.vo.BaseVo;
import com.kt.giga.home.openapi.ghms.common.token.AuthToken;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.PushInfoRequest;
import com.kt.giga.home.openapi.ghms.ktInfra.service.KPNSService;
import com.kt.giga.home.openapi.ghms.user.dao.MasterDao;
import com.kt.giga.home.openapi.ghms.user.domain.key.DeviceKey;
import com.kt.giga.home.openapi.ghms.user.domain.key.DeviceMasterKey;
import com.kt.giga.home.openapi.ghms.user.domain.key.MasterUserKey;
import com.kt.giga.home.openapi.ghms.user.domain.key.SvcEtsRelKey;
import com.kt.giga.home.openapi.ghms.user.domain.vo.DeviceAuthVo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.DeviceMasterVo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.DeviceVo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.MasterUserVo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.UserInfo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.UserVo;
import com.kt.giga.home.openapi.ghms.util.data.DataUtil;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;
import com.kt.giga.home.openapi.ghms.util.properties.APIEnv;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 2.
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
public class MasterService {

	/** Logger */
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private KPNSService kPNSService;
	
	@Autowired
	private MasterDao masterDao;
	
	@Autowired
	private DataUtil dataUtil;
	
	@Autowired
	private APIEnv apiEnv;
	
	/**
	 * 마스터의 PNS ID 조회(by 전화번호) 후 푸시 발송(PNS) 요청
	 * @param authToken
	 * @param telNo
	 * @return
	 * @throws APIException
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public BaseVo insertSlaveRequest(AuthToken token, MasterUserKey key) throws APIException {
		
		BaseVo vo = new BaseVo();
		
		if(key.getTelNo().equals(token.getTelNo())) {
			log.error("마스터가 본인한테 신청.");
			vo.setResultCode("-2");
		} else {
			
			// 마스터 정보 조회.
			UserVo masterMemberInfo = masterDao.selectMemberInfo(key.getTelNo(), null, token.getDstrCd(), token.getSvcThemeCd(), token.getUnitSvcCd(), "Y");
			
			if(masterMemberInfo == null || "".equals(masterMemberInfo.getPnsRegId())) {
				log.error("마스터 정보 없음.");
				vo.setResultCode("2");
				return vo;
			}
			
			log.debug("master member info pnsId:{} telNo:{}", masterMemberInfo.getPnsRegId(), key.getTelNo());
			
			// PNS 푸시 발송
			Map<String, String> data = new HashMap<String, String>();
			data.put("msg", "다음의 사용자가 서비스 가입을 신청하였습니다.\n"+ key.getUserNm() + "/" + token.getTelNo());
			data.put("eventId", PushType.SLAVE_REQUEST.getEventId());
			data.put("sMbrSeq", token.getUserNo());
			data.put("mbrNickNm", key.getUserNm());
			data.put("telNo", token.getTelNo());
			
			log.debug("push data:{}", data.toString());
			
			PushInfoRequest req = new PushInfoRequest();
			req.setRegistrationId(masterMemberInfo.getPnsRegId());
			req.setData(data);
			
			kPNSService.push(req);
			
			vo.setResultCode("0");
		}
		
		return vo;
	}
	
	/**
	 * 슬레이브 신청 승인
	 * @param token
	 * @param userNo
	 * @param svcTgtSeq
	 * @param deviceId
	 * @return
	 * @throws APIException
	 */
	public BaseVo insertSlaveInfoByPushAdmission(AuthToken token, MasterUserKey masterUserKey) throws APIException {
		
		boolean push = false;
		int svcEtsRelCnt = 0;
		// 승인시
		if("Y".equals(masterUserKey.getApprovalYn())) {

			// 서비스위임관계T INSERT, 서비스대상접속설정내역T INSERT
			
			// SvcEtsRelKey setting.
			SvcEtsRelKey key = new SvcEtsRelKey();
			key.setMbrSeq(Long.parseLong(token.getUserNo()));
			key.setEtsMbrSeq(masterUserKey.getUserNo());
			key.setDstrCd(token.getDstrCd());
			key.setSvcThemeCd(token.getSvcThemeCd());
			key.setUnitSvcCd(token.getUnitSvcCd());
			key.setSvcTgtSeq(masterUserKey.getServiceNo());
			
			// 존재여부(기승인여부) 확인.
			svcEtsRelCnt = masterDao.selectSvcEtsRel(key);
			
			if(svcEtsRelCnt < 1) {
				
				String svcTgtTypeCd = masterDao.selectSvcTgtTypeCd(masterUserKey.getServiceNo());
				key.setSvcTgtTypeCd(svcTgtTypeCd);
				
				masterDao.insertSlaveByPushIntoSvcEtsRel(key);

				key.setGroupSetUpCd(apiEnv.getProperty("group.setup.cd", "A902"));
				// 서비스가 허브일 경우만.
				if(CommonConstants.GHMS_GW_SVC_TGT_TYPE_CD.equals(svcTgtTypeCd)) {
					// 알람설정 기본 데이터 등록
					key.setSetUpCd("001");
					key.setSetUpValue(SpotDevAlram.toDefaultString());
					masterDao.insertSlaveByPushIntoSvcTgtConnSetupTxn(key);
				}
				// 권한 기본 데이터 등록
				key.setSetUpCd("003");
				key.setSetUpValue("Y");
				masterDao.insertSlaveByPushIntoSvcTgtConnSetupTxn(key);

				// 슬레이브 정보 조회.
				UserVo slaveMemberInfo = masterDao.selectMemberInfo(null, masterUserKey.getUserNo(), token.getDstrCd(), token.getSvcThemeCd(), token.getUnitSvcCd(), "N");
				
				// 슬에이브에게 승인 푸시 발송
				Map<String, String> data = new HashMap<String, String>();
				data.put("eventId", PushType.SLAVE_REQUEST_OK.getEventId());
				data.put("msg", "가족사용자 서비스 가입 신청 완료 되었습니다.");
				
				log.debug("push data:{}", data.toString());
				
				PushInfoRequest req = new PushInfoRequest();
				req.setRegistrationId(slaveMemberInfo.getPnsRegId());
				req.setData(data);

				kPNSService.push(req);
			}
			
		} else {
			// 거절시

			// 슬레이브 정보 조회.
			UserVo slaveMemberInfo = masterDao.selectMemberInfo(null, masterUserKey.getUserNo(), token.getDstrCd(), token.getSvcThemeCd(), token.getUnitSvcCd(), "N");
			
			// 슬에이브에게 승인 푸시 발송
			Map<String, String> data = new HashMap<String, String>();
			data.put("eventId", PushType.SLAVE_REQUEST_NOK.getEventId());
			data.put("msg", "가족사용자 서비스 가입 신청이 거절 되었습니다.");
			
			log.debug("push data:{}", data.toString());
			
			PushInfoRequest req = new PushInfoRequest();
			req.setRegistrationId(slaveMemberInfo.getPnsRegId());
			req.setData(data);

			kPNSService.push(req);
		}
		
		
		BaseVo vo = new BaseVo();
		
		// 처리 결과
		if(svcEtsRelCnt > 0) {
			vo.setResultCode("-2");
		} else {
			vo.setResultCode("0");
		}
				
		return vo;
	}
	
	/**
	 * 사용자 리스트 조회
	 * 
	 * @param token
	 * @param key
	 * @return
	 * @throws APIException
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public List<MasterUserVo> selectSlaveUserList(AuthToken token, long serviceNo) throws APIException{
	    
	    MasterUserKey key = new MasterUserKey();
        
	    key.setUserNo(token.getUserNoLong());
	    key.setDstrCd(token.getDstrCd());
	    key.setSvcThemeCd(token.getSvcThemeCd());
	    key.setUnitSvcCd(token.getUnitSvcCd());
	    key.setServiceNo(serviceNo);
	    key.setGroupSetupCd(apiEnv.getProperty("group.setup.cd", "A902"));
	    key.setSetupCd("002");
	    
	    List<MasterUserVo> resultList = masterDao.selectSlaveUserList(key);
	    
	    return resultList;
	}
	
	
	/**
	 * 사용자 삭제
	 * 
	 * @param token
	 * @param userNo       회원 일련 번호
	 * @param serviceNo    서비스 대상 일련 번호
	 * @return 
	 * @throws APIException
	 */
	public BaseVo deleteSlaveUser(AuthToken token, long userNo, long serviceNo){
		
		BaseVo vo = new BaseVo();
		
		try {
		    SvcEtsRelKey key = new SvcEtsRelKey();
		    
		    key.setEtsMbrSeq(userNo);         // 삭제할 슬레이브의 회원일련번호
		    key.setMbrSeq(token.getUserNoLong());                       // 자기 자신 (MASTER) 회원일련번호
		    key.setSvcTgtSeq(serviceNo);
		    key.setDstrCd(token.getDstrCd());
		    key.setSvcThemeCd(token.getSvcThemeCd());
		    key.setUnitSvcCd(token.getUnitSvcCd());
		    
		    masterDao.deleteUserSvcEtsRel(key);
		    masterDao.deleteUserMbrSpotDevSetupTxn(key);
		    vo.setResultCode("0");
		} catch(Exception e) {
		    vo.setResultCode("-1");
		}
		
		return vo;
	}
    
    /**
     * 도어락 회원비밀번호내역 등록 
     * 
     * @param key serviceNo, devUUID, devNm, passwd, passwdSeq
     * @return
     */
    public BaseVo insertMbrPwdTxn(MasterUserKey key){
        
        BaseVo vo = new BaseVo();
        
        int exCnt = masterDao.selectMbrPwdTxnCnt(key);
        
        int chk = 0;
        
        if(exCnt > 0) {
            chk = masterDao.updateMbrPwdTxn(key);
        } else {
            chk = masterDao.insertMbrPwdTxn(key);
        }
        
        if(0 < chk) {
            vo.setResultCode("0");
        } else {
            vo.setResultCode("-1");
        }
        
        return vo;
    }
	
	/**
	 * 사용자 디바이스(도어락) 비밀번호 조회
	 * 
	 * @param key
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public List<MasterUserVo> selectMbrPwdTxn(long serviceNo, long devUUID){
	    
	    MasterUserKey key = new MasterUserKey();
	    
	    key.setServiceNo(serviceNo);
	    key.setDevUUID(devUUID);
	    
	    List<MasterUserVo> vo = masterDao.selectMbrPwdTxn(key);
	    
	    return vo;
	}
	
	/**
	 * 사용자 디바이스(도어락) 비밀번호 삭제
	 * 
	 * @param userNo
	 * @param serviceNo
	 * @param devUUID
	 * @return
	 */
	public BaseVo deleteMbrPwdTxn(long userNo, long serviceNo, long devUUID, long passwdSeq){
	    MasterUserKey key = new MasterUserKey();
	    
	    key.setServiceNo(serviceNo);
	    key.setDevUUID(devUUID);
	    key.setPasswdSeq(passwdSeq);
	    
	    int delChk = masterDao.deleteMbrPwdTxn(key);
	    
	    BaseVo vo = new BaseVo();
	    if(delChk > 0){
	        vo.setResultCode("0");
	    }else{
	        vo.setResultCode("-1");
	    }
	    
	    return vo;
	}
	
	
	/**
	 * 사용자 디바이스 사용권한 조회
	 * 
	 * @param token
	 * @param serviceNo    서비스 대상 일련 번호
	 * @param userNo       대상 회원 일련 번호
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public DeviceMasterVo selectDeviceUseAuth(AuthToken token, long serviceNo, long userNo){
	    
	    MasterUserKey key = new MasterUserKey();
	    
        key.setDstrCd(token.getDstrCd());
        key.setSvcThemeCd(token.getSvcThemeCd());
        key.setUnitSvcCd(token.getUnitSvcCd());
        key.setServiceNo(serviceNo);
        key.setUserNo(userNo);
        key.setGroupSetupCd(apiEnv.getProperty("group.setup.cd", "A902"));
        key.setSetupCd("003");
        
        DeviceMasterVo vo = new DeviceMasterVo();
        DeviceAuthVo authVo = new DeviceAuthVo();
        List<DeviceVo> deviceList = masterDao.selectDeviceUseAuth(key);        
        authVo.setMbrSeq(userNo);
        authVo.setDeviceList(deviceList);
        vo.setDeviceAuthVo(authVo);
        vo.setSvcTgtSeq(serviceNo);
        
	    
	    return vo;
	}
	
	/**
	 * 사용자 디바이스 사용권한 수정
	 * 
	 * @param token
	 * @param key
	 * @return
	 * @throws APIException 
	 */
	public BaseVo updateDeviceUserAuth(AuthToken token, DeviceMasterKey key) throws APIException{
	    
		BaseVo vo = new BaseVo();
		
		int updChk = 0;
		
		try {
		    List<DeviceKey> deviceList = key.getDeviceAuthVo().getDeviceList();
		    
		    for(DeviceKey devKey : deviceList) {
		        
		        MasterUserKey masterKey = new MasterUserKey();
		        masterKey.setSetupVal(devKey.getCntrValue());
		        masterKey.setDstrCd(token.getDstrCd());
		        masterKey.setSvcThemeCd(token.getSvcThemeCd());
		        masterKey.setUnitSvcCd(token.getUnitSvcCd());
		        masterKey.setGroupSetupCd(apiEnv.getProperty("group.setup.cd", "A902"));
		        masterKey.setSetupCd(devKey.getCntrCode());
		        masterKey.setServiceNo(key.getSvcTgtSeq());
		        masterKey.setUserNo(key.getDeviceAuthVo().getMbrSeq());
		        masterKey.setDevUUID(devKey.getDevUUID());
		        
		        updChk = masterDao.updateDeviceUserAuth(masterKey);
		        
		        // 권한 제거 시 알림 설정 내역 변경
	        	SvcEtsRelKey skey = new SvcEtsRelKey();
	        	
	        	skey.setEtsMbrSeq(key.getDeviceAuthVo().getMbrSeq());         // 삭제할 슬레이브의 회원일련번호
	        	skey.setSvcTgtSeq(key.getSvcTgtSeq());
	        	skey.setDstrCd(token.getDstrCd());
	        	skey.setSvcThemeCd(token.getSvcThemeCd());
	        	skey.setUnitSvcCd(token.getUnitSvcCd());
	        	skey.setSetUpCd("001");
	        	skey.setSpotDevSeq(devKey.getDevUUID());
	        	skey.setGroupSetUpCd(apiEnv.getProperty("group.setup.cd", "A902"));
	        	skey.setSetUpValue(SpotDevAlram.toDefaultString());
	        	
		        if("N".equals(devKey.getCntrValue())) {
				    masterDao.deleteUserMbrSpotDevSetupTxn(skey);
		        } else {
		        	int cnt = masterDao.selectUserMbrSpotDevSetupTxnCount(skey);
		        	if(cnt < 1) {
		        		masterDao.insertSlaveByPushIntoSvcTgtConnSetupTxn(skey);
		        	}
		        }
		    }
		    
	        if(updChk > 0){
	            vo.setResultCode("0");
	        }else{
	            vo.setResultCode("-1");
	        }
		    
		} catch(Exception e) {
		    throw new APIException(e.getMessage(), org.springframework.http.HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_NULL_EXCEPTION);
		}
	    
	    return vo;
	}
	
	/**
	 * 사용자(가족구성원) 수정
	 * @param token
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public BaseVo updateUserInfo(AuthToken token, MasterUserKey key) throws Exception {
		
		BaseVo vo = new BaseVo();
		
		try {
			
			key.setDevUUID(Long.parseLong(token.getDeviceId()));
			key.setDstrCd(token.getDstrCd());
			key.setSvcThemeCd(token.getSvcThemeCd());
			key.setUnitSvcCd(token.getUnitSvcCd());
			key.setGroupSetupCd(apiEnv.getProperty("group.setup.cd", "A902"));
			
			// 사용자 프로필 이미지
			if(key.getUserImg() != null) {
				
				Map<String, Object> fileMap = dataUtil.uploadImgFile("/DATA/ghms", key.getUserImg());
		    	/*
		    	 * [key]
		    	 * ORIGIN_FILE_NM	: 원본파일명
		    	 * STORE_FILE_NM	: 저장파일명
		    	 * FILE_SIZE_VAL	: 파일사이즈값
		    	 * CONTS_TYPE_VAL	: 컨텐츠타입값
		    	 * FILE_PATH_VAL	: 파일 저장 위치
		    	 */
				if(fileMap != null) {
					key.setOrgFileNm(fileMap.get("ORIGIN_FILE_NM").toString());
					key.setStoreFileNm(fileMap.get("STORE_FILE_NM").toString());
					key.setFileSize(Integer.parseInt(fileMap.get("FILE_SIZE_VAL").toString()));
					key.setContsType(fileMap.get("CONTS_TYPE_VAL").toString());
					masterDao.insertUserProfileImg(key);	
				}
			}
			
			// 사용자 닉네임 변경
			if(!"".equals(key.getUserNm())) {
				String nickName = masterDao.selectUserNickName(key);
				if(nickName != null && !"".equals(nickName)) {
					masterDao.updateUserNickName(key);
				} else {
					masterDao.insertUserNickName(key);
				}
			}

			vo.setResultCode("0");
			
		} catch (Exception ex) {
			log.error("사용자(가족구성원) 수정 에러 : " + ex.getMessage());
			ex.printStackTrace();
			vo.setResultCode("-1");
		}

		return vo;
		
	}

	/**
	 * 사용자 프로필 이미지 정보 조회
	 * @param mbrSeq
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public Map<String, Object> selectUserProfileImg(long mbrSeq) {
		return masterDao.selectUserProfileImg(mbrSeq);
	}

	/**
	 * @param serviceNo
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public List<UserInfo> selectUserInfoList(Long serviceNo) {
		return masterDao.selectUserInfoList(serviceNo);
	}
	
}
