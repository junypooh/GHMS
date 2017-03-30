/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kt.giga.home.openapi.ghms.checker.dao.SmartCheckerDao;
import com.kt.giga.home.openapi.ghms.common.DeviceType;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.CommonConstants;
import com.kt.giga.home.openapi.ghms.common.GHmsConstants.ErrorCode;
import com.kt.giga.home.openapi.ghms.common.domain.vo.BaseVo;
import com.kt.giga.home.openapi.ghms.common.service.ECService;
import com.kt.giga.home.openapi.ghms.common.token.AuthToken;
import com.kt.giga.home.openapi.ghms.devices.dao.DevicesUpgradeDao;
import com.kt.giga.home.openapi.ghms.devices.dao.InitDeviceAddDao;
import com.kt.giga.home.openapi.ghms.devices.domain.key.SpotDevBasKey;
import com.kt.giga.home.openapi.ghms.devices.domain.vo.DeviceGw;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev.SpotDev;
import com.kt.giga.home.openapi.ghms.ktInfra.dao.GhmsProvisionDao;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.GetServiceEquipInfoResult;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.HoldingEquipInfo;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.NetworkStatusInfo;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SpotDevExpnsnBas;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.vo.SvcTgtBas;
import com.kt.giga.home.openapi.ghms.ktInfra.service.KTInfraService;
import com.kt.giga.home.openapi.ghms.user.dao.MasterDao;
import com.kt.giga.home.openapi.ghms.user.dao.UserDao;
import com.kt.giga.home.openapi.ghms.user.domain.key.LoginKey;
import com.kt.giga.home.openapi.ghms.user.domain.key.MasterUserKey;
import com.kt.giga.home.openapi.ghms.user.domain.key.SvcEtsRelKey;
import com.kt.giga.home.openapi.ghms.user.domain.vo.SnsnTagBasVo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.UserAuthVo;
import com.kt.giga.home.openapi.ghms.user.domain.vo.UserVo;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;
import com.kt.giga.home.openapi.ghms.util.properties.APIEnv;


/**
 * 인증/로그인, 토큰갱신등 회원관련 처리 서비스
 * @author dahye.kim (dahye.kim@ceinside.co.kr)
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
public class UserService {

    /**
     * 로거
     */
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    /**
     * OpenAPI 환경 프라퍼티
     */ 
    @Autowired
    private APIEnv env;
    
    /**
     * 사용자 매퍼 인터페이스
     */
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private MasterDao masterDao;
    
    /**
     * EC 연동 서비스
     */
    @Autowired
    private ECService ecService;
    
    /**
     * KT 인프라 연동 서비스
     */
    @Autowired
    private KTInfraService ktService;
    
    @Autowired
    private GhmsProvisionDao ghmsProvisionDao;
    
    @Autowired
    private InitDeviceAddDao initDeviceAddDao;
    
    @Autowired
    private DevicesUpgradeDao deviceUpgradeDao;
    
    @Autowired
    private SmartCheckerDao smartCheckerDao;
	
    /**
     * 샌싱태그 리스트(메모리)
     */
	private List<SnsnTagBasVo> snsTagBasList = new ArrayList<SnsnTagBasVo>();
    
    /**
     * 더미 인증 메쏘드, 디버깅 용도. 오류가 있을 수 있기 때문에 SDP 장애 있을때만 제한적으로 사용 TODO 향후 제거
     * 
     * @param authToken     앱 초기실행시 발급한 인증 토큰
     * @param userId        아이디
     * @param passwd        패스워드
     * @return              인증 최종 결과 UserAuh 객체
     * @throws APIException
    private UserAuthVo authDummy(String authToken, String userId, String passwd, String forceYn, String subsYn) throws APIException {

        AuthToken initAuthToken = AuthToken.checkInitAuthToken(authToken);
        long mbrSeq = userDao.getUserBase(userId).getMbrSeq();
        UserVo userBase = new UserVo(mbrSeq, userId, "999999");
        AuthToken newAuthToken = login(initAuthToken, userBase, forceYn, subsYn);
        
        UserAuthVo userAuth = new UserAuthVo();
        userAuth.setNewAuthToken(newAuthToken.getToken());
        userAuth.setNoticeOption("0");
        userAuth.setTermsYn("N");
        userAuth.setKidsCheckOption("0");
        userAuth.setLoginResult("1");
        userAuth.setAge("50");
        userAuth.setSubsYn("Y");

        return userAuth;
    }
     */

	/**
     * 아이디/패스워드 인증 메서드
     * 
     * @param authToken     앱 초기실행시 발급한 인증 토큰
     * @param userId        아이디
     * @param passwd        패스워드
     * @param forceYn       강제 로그인 처리 여부
     * @return              인증 최종 결과 UserAuh 객체
     * @throws APIException
     */
    public UserAuthVo auth(String authToken, String userId, String passwd, String forceYn) throws APIException {    
        
        UserAuthVo userAuth = new UserAuthVo();
        
        try {

            // 1. AuthToken 디코딩
            AuthToken initAuthToken = AuthToken.checkInitAuthToken(authToken);

            // 2. KT인프라 연동 서버 SDP 인증 처리, 인증 실패한 경우 바로 익셉션 처리
        	UserVo sdpUser = checkSDPAuth(userId, passwd);	
            
            // 3. 회원정보 체크, 신규 회원의 경우 회원 테이블에 등록
            UserVo userBase = checkUserBase(initAuthToken, sdpUser, userId);

            // 4. 약관 동의여부 옵션
            String termsYn = checkTermAgreeRequired(userBase) ? "Y" : "N";

            // 5. 보호자 동의여부 옵션
            String kidsCheckOption = checkParentsAgreeRequired(sdpUser, userBase) ? "1" : "0";

            // 6. 청약상태 체크, 동기화
            String subsYn = checkUserSubscription(sdpUser, userBase) ? "Y" : "N";
            
            // 7. 동시접속 로그인 처리
            AuthToken loginAuthToken = login(initAuthToken, userBase, forceYn, subsYn, userId);
            
            // 로그인 실패한 경우 기존 토큰을 그대로 내려줌
            userAuth.setNewAuthToken(loginAuthToken == null ? authToken : loginAuthToken.getToken());       // 신규 발급 인증 토큰
            userAuth.setTermsYn(termsYn);                                                                   // 약관동의 필요 여부 (Y,N)
            userAuth.setKidsCheckOption(kidsCheckOption);                                                   // 14세 미만 사용자 법정 대리인 동의 필요 여부(0:불필요, 1:필요)
            if(sdpUser.isLoginPassYn()) {
            	// 서비스 중지 여부
            	if(loginAuthToken != null && checkStopService(loginAuthToken)) {
            		userAuth.setStopServiceYn("Y");
            	} else {
            		userAuth.setStopServiceYn("N");
            	}
                userAuth.setLoginResult(loginAuthToken == null ? "2" : "1");                                // 로그인 상태(1:로그인성공, 2:동일전화번호로 로그인시도, 3:로그인 실패)	
            } else {
            	userAuth.setLoginResult("3");                               								// 로그인 상태(1:로그인성공, 2:동일전화번호로 로그인시도, 3:로그인 실패)
            }
            userAuth.setSubsYn(subsYn);                                                                     // 사용자의 현재 쳥약 여부(Y:청약상태, N:청약상태아님)
            
            // 센싱태그 리스트
//            userAuth.setSnsnTagBasVo(userDao.selectSnsnTagBasList(CommonConstants.UNIT_SVC_CD));
            userAuth.setSnsnTagBasVo(getSnsTagBasList());
            
        } catch(APIException e) { 
            throw e;
        } catch(Exception e) {
            log.warn(e.getMessage(), e);
            throw new APIException(e.getMessage(), HttpStatus.UNAUTHORIZED, ErrorCode.UNKNOWN_EXCEPTION);
        }
        
        return userAuth;
    }

	/**
     * 아이디/패스워드 인증 메서드(고도화용)
     * 
     * @param authToken     앱 초기실행시 발급한 인증 토큰
     * @param userId        아이디
     * @param passwd        패스워드
     * @param forceYn       강제 로그인 처리 여부
     * @return              인증 최종 결과 UserAuh 객체
     * @throws APIException
     */
    public UserAuthVo authUpgrade(String authToken, String userId, String passwd, String forceYn, String wifiMacAddr) throws APIException {    
        
        UserAuthVo userAuth = new UserAuthVo();
        
        try {

            // 1. AuthToken 디코딩
            AuthToken initAuthToken = AuthToken.checkInitAuthToken(authToken);

            // 2. KT인프라 연동 서버 SDP 인증 처리, 인증 실패한 경우 바로 익셉션 처리
        	UserVo sdpUser = checkSDPAuthUpgrade(userId, passwd);	
            
            // 3. 회원정보 체크, 신규 회원의 경우 회원 테이블에 등록
            UserVo userBase = checkUserBase(initAuthToken, sdpUser, userId);

            // 4. 약관 동의여부 옵션
            String termsYn = checkTermAgreeRequired(userBase) ? "Y" : "N";

            // 5. 보호자 동의여부 옵션
            String kidsCheckOption = checkParentsAgreeRequired(sdpUser, userBase) ? "1" : "0";

            // 6. 청약상태 체크, 동기화
            String subsYn = checkUserSubscription(sdpUser, userBase) ? "Y" : "N";
            
            // 7. 동시접속 로그인 처리
            AuthToken loginAuthToken = login(initAuthToken, userBase, forceYn, subsYn, userId);
            
            // 8. 임의 고객 여부 판단
            String anyCustYn = "N";
            if(ArrayUtils.isEmpty(sdpUser.getCustIdList())) {
            	anyCustYn = "Y";
            }
            userAuth.setAnyCustYn(anyCustYn);
            
            // 9. AP 청약 조회 및 신규 등록
            // 2차고도화임시주석
            checkGigaWIFIHomeSubscription(sdpUser, loginAuthToken);
            
            // 10. AP 청약 3개 이상 고객 여부
            String apSubsMoreYn = "N";
            int apSvcTgtBasCount = userDao.getMbrSvcTgtBasCount(userBase.getMbrSeq(), userBase.getDstrCd(), userBase.getSvcThemeCd(), userBase.getUnitSvcCd(), CommonConstants.GHMS_AP_SVC_TGT_TYPE_CD);
            if(sdpUser.getApSvcIdList() != null && sdpUser.getApSvcIdList().size() > 2 && apSvcTgtBasCount == 0) {
            	apSubsMoreYn = "Y";
            }
            
            // QAT를 위한 임시 로직
            List<String> mbrList = new ArrayList<String>();
            mbrList.add("ghmstest997");
            mbrList.add("smilingi");
            if(mbrList.contains(userId)) {
            	apSubsMoreYn = "Y";
            }
            
            userAuth.setApSubsMoreYn(apSubsMoreYn);

            // 로그인 실패한 경우 기존 토큰을 그대로 내려줌
            userAuth.setNewAuthToken(loginAuthToken == null ? authToken : loginAuthToken.getToken());       // 신규 발급 인증 토큰
            userAuth.setTermsYn(termsYn);                                                                   // 약관동의 필요 여부 (Y,N)
            userAuth.setKidsCheckOption(kidsCheckOption);                                                   // 14세 미만 사용자 법정 대리인 동의 필요 여부(0:불필요, 1:필요)
            if(sdpUser.isLoginPassYn()) {
            	// 서비스 중지 여부
            	if(loginAuthToken != null && checkStopService(loginAuthToken)) {
            		userAuth.setStopServiceYn("Y");
            	} else {
            		userAuth.setStopServiceYn("N");
            	}
                userAuth.setLoginResult(loginAuthToken == null ? "2" : "1");                                // 로그인 상태(1:로그인성공, 2:동일전화번호로 로그인시도, 3:로그인 실패)	
            } else {
            	userAuth.setLoginResult("3");                               								// 로그인 상태(1:로그인성공, 2:동일전화번호로 로그인시도, 3:로그인 실패)
            }
            userAuth.setSubsYn(subsYn);                                                                     // 사용자의 현재 쳥약 여부(Y:청약상태, N:청약상태아님)

            // 11. 사용자 pnsRegId 스마트 체커 전송
            // 2차고도화임시주석
            if("1".equals(userAuth.getLoginResult()) && !"ghmstest003".equals(userId)) {
                
                String deviceType = "AD";
                String userAgent = loginAuthToken.getUserAgent();
                if("IOS".equals(userAgent)) {
                	deviceType = "IP";
                }
                ktService.setDeviceTokenInfo(userId, loginAuthToken.getDeviceId(), wifiMacAddr, deviceType, loginAuthToken.getPnsRegId(), "1", "0000000000");
            }
            
            // 센싱태그 리스트
//            userAuth.setSnsnTagBasVo(userDao.selectSnsnTagBasList(CommonConstants.UNIT_SVC_CD));
            userAuth.setSnsnTagBasVo(getSnsTagBasList());
            
        } catch(APIException e) { 
            throw e;
        } catch(Exception e) {
            log.warn(e.getMessage(), e);
            throw new APIException(e.getMessage(), HttpStatus.UNAUTHORIZED, ErrorCode.UNKNOWN_EXCEPTION);
        }
        
        return userAuth;
    }
    
    /**
     * 로그인 메서드
     * 
     * @param initAuthToken 초기 인증 토큰
     * @param userBase      기본 User 정보 객체
     * @return              로그인 인증 토큰
     * @throws APIException 
     */
    
    private AuthToken login(AuthToken initAuthToken, UserVo userBase, String forceYn, String subsYn, String userId){
        
        String unionSvcCd   = initAuthToken.getUnionSvcCd();
        String dstrCd       = initAuthToken.getDstrCd();
        String svcThemeCd   = initAuthToken.getSvcThemeCd();
        
        String unitSvcCd    = initAuthToken.getUnitSvcCd();
        String termlId      = initAuthToken.getDeviceId();
        String telNo        = initAuthToken.getTelNo();
        String pnsRegId     = initAuthToken.getPnsRegId();
        String appVer       = initAuthToken.getAppVer();
        String userAgent	= initAuthToken.getUserAgent();
        
        long mbrSeq         = userBase.getMbrSeq();
        String credentialId = userBase.getCredentialId();
        
        AuthToken loginAuthToken = AuthToken.encodeAuthToken(userAgent, termlId, telNo, pnsRegId, mbrSeq, credentialId, unionSvcCd, appVer, userId);
        
        HashMap<String, Object> userSvcExist = userDao.getUserSvcExistCnt(mbrSeq, dstrCd, svcThemeCd, unitSvcCd, termlId);
        
        String smrCnt           = ((Long)userSvcExist.get("smrCnt")).toString();
        String stcbCnt          = ((Long)userSvcExist.get("stcbCnt")).toString();
        String stcbDeviceCnt    = ((Long)userSvcExist.get("stcbDeviceCnt")).toString();
        
        // 서비스회원관계 테이블에 데이터가 존재하지 않는다면 INSERT
        if("0".equals(smrCnt)){
            userDao.insertServiceUserRelation(userBase);
        }
        
        // 청약 정보가 있다면, 서비스대상기본테이블에 운영상태코드를 서비스회원관계 테이블 서비스상태코드에 UPDATE
        if("Y".equals(subsYn)){
            userDao.updateMbrRelSttusCdFromTgtBas(userBase);
        }
        
        // 서비스대상접속기본 테이블에 데이터가 존재하지 않는다면 INSERT
        if("0".equals(stcbCnt)){
            userDao.insertSvcTgtConnBas(userBase);
        }
        
        // 디바이스ID가 변경되지 않았지만 휴대폰번호가 바꼈을수있으므로 무조건 UPDATE
        if(!"0".equals(stcbCnt) && !"0".equals(stcbDeviceCnt)){
            userBase.setTermlId("");
            userBase.setTelNo(telNo);
            userBase.setDstrCd(dstrCd);
            userBase.setSvcThemeCd(svcThemeCd);
            userBase.setUnitSvcCd(unitSvcCd);
            userBase.setPnsRegId(pnsRegId);
            
            // 기존 데이터 전화번호 null 로 변경
            userDao.updateOldDeviceSvcTgtConnBas(userBase);
            
            // 신규 건 전화번호 업데이트
            userDao.updateNewDeviceSvcTgtConnBas(userBase);
        }
        
        // 기존에 접속하던 단말기가 변경이 되었다면..
        if(!"0".equals(stcbCnt) && "0".equals(stcbDeviceCnt)){
//        	forceYn = "Y"; //중복로그인임시로직
            if(!"Y".equals(forceYn)){
                log.info("## Login is Not Allowed, FORCEYN : N");
                return null;
            }else{
                userBase.setTermlId(termlId);
                userBase.setTelNo(telNo);
                userBase.setDstrCd(dstrCd);
                userBase.setSvcThemeCd(svcThemeCd);
                userBase.setUnitSvcCd(unitSvcCd);
                userBase.setPnsRegId(pnsRegId);
                
                // 기존 데이터 전화번호 null 로 변경
                userDao.updateOldDeviceSvcTgtConnBas(userBase);
                
                // 사용자의 디바이스가 변경되었다면 기존에 있는 서비스대상접속기본 데이터를 신규 디바이스 정보로 UPDATE
                userDao.updateNewDeviceSvcTgtConnBas(userBase);
            }
        }
        
        return loginAuthToken;
        
    }

    /**
     * 기본 회원 정보를 체크하고 신규회원인 경우 회원등록 처리
     * 
     * @param initAuthToken 초기 인증 토큰
     * @param sdpUser       SDP 연동 User 객체
     * @param userId        사용자 아이디
     * @return              기본 User 정보 객체
     */
    private UserVo checkUserBase(AuthToken initAuthToken, UserVo sdpUser, String userId) {

        UserVo userBase       = userDao.getUserBase(userId);
        
        String dstrCd       = initAuthToken.getDstrCd();
        String svcThemeCd   = initAuthToken.getSvcThemeCd();
        String unitSvcCd    = initAuthToken.getUnitSvcCd();
        
        String termlId      = initAuthToken.getDeviceId();
        String pnsRegId     = initAuthToken.getPnsRegId();
        String applVer      = initAuthToken.getAppVer();
        String telNo        = initAuthToken.getTelNo();
        
        log.info("## checkUserBase sdpUser.getCredentialId() : " + sdpUser.getCredentialId());
        
        // 신규 회원인 경우, 회원 기본 생성
        if(userBase == null) {
            
            long mbrSeq = createMbrSeq();
            userBase = new UserVo(dstrCd, svcThemeCd, unitSvcCd, mbrSeq, userId);
            userBase.setCredentialId(sdpUser.getCredentialId());
            userBase.setTermlId(termlId);
            userBase.setPnsRegId(pnsRegId);
            userBase.setTelNo(telNo);
            userBase.setApplVer(applVer);
            
            userDao.insertUserBase(userBase);
            userDao.insertServiceUserRelation(userBase);
            
            userDao.insertSvcTgtConnBas(userBase);
            
            // MANAGER_PROF_TXN 에 INSERT
            userDao.insertManagerProfTxn(mbrSeq, userId);
            
            log.info("## checkUserBase : New User. Create MbrSeq : {}", mbrSeq);
        } else {
            userDao.updateCredentialId(userBase.getMbrSeq(), sdpUser.getCredentialId());                
            
            userBase.setCredentialId(sdpUser.getCredentialId());
            userBase.setDstrCd(dstrCd);
            userBase.setSvcThemeCd(svcThemeCd);
            userBase.setUnitSvcCd(unitSvcCd);
            userBase.setTermlId(termlId);
            
            // 매니저 프로파일 내역에 내역이 존재하지 않는다면 등록
            Map<String, Object> mngMap = userDao.selectChkFromManagerProfTxn(userBase.getMbrSeq());
            int cnt = Integer.parseInt(mngMap.get("cnt").toString());
            if(cnt < 1) {
                userDao.insertManagerProfTxn(userBase.getMbrSeq(), userId);
            }else {
                // 매니저 프로파일 내역에 내역이 존재하지만 fileSeq가 없을시 update
                String fileSeq = mngMap.get("file_seq").toString();
                if(StringUtils.isBlank(fileSeq)) {
                    userDao.updateFileSeqFromManagerProfTxn(userBase.getMbrSeq());
                }
            }
            
            log.info("## checkUserBase : User Already Exist : {}", userBase.getMbrSeq());
        }
        
        return userBase;
    }
    
    /**
     * "KT인프라연동서버" 통해서 SDP 연동을 처리하는 메쏘드
     * 
     * @param userId        아이디
     * @param passwd        패스워드
     * @return              SDP 연동 User 객체
     * @throws APIException
     */    
    private UserVo checkSDPAuth(String userId, String passwd) throws APIException {

        HashMap<String, String> authResultMap = null; 
        
        // ktinfra.sdp.useyn
        String ktinfraSdpUseyn = env.getProperty("ktinfra.sdp.useyn");
        
        if("Y".equals(ktinfraSdpUseyn)) {

    		try {
    			// OIF_127(올레 사용자 로그인) 요청
    			authResultMap = ktService.sendLoginRequest(userId, passwd);
    		} catch(Exception e) {
    			throw new APIException("SDP Exception (Login)", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_NETWWORK_ERROR);
    		}

    		String authResultCode = authResultMap.get("resultCode");
    		int loginFailCounter = NumberUtils.toInt(authResultMap.get("loginFailCounter"));
    		UserVo user = null;

    		if(StringUtils.equals(authResultCode, "1")) {

    			// 성공
    			String credentialId = authResultMap.get("credentialId");
    			// credentialId 업데이트

    			try {
    				// OIF_138 호출
    				user = ktService.sendSubsRequest(userId, credentialId);
    	        	user.setLoginPassYn(true);
    	        	user.setMbrId(userId);
    	        	
    	        	// 테스트 코드 todo
    	        	if("ghmstest004".equals(userId)) {
    	        		user.setAge(10);
    	        	}
    	        	
    			} catch(Exception e) {
    				throw new APIException("SDP Exception (Subscription)", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_NETWWORK_ERROR);
    			}
    			
    		} else if(StringUtils.equals(authResultCode, "4")) {
    			if(loginFailCounter >= 5) {
    				throw new APIException("Invalid Password.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_PASSWD_05);
    			} else {
    				throw new APIException("Invalid Password.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_PASSWD);
    			}
    		} else if(StringUtils.equals(authResultCode, "10")) {
    			throw new APIException("Invalid Username.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_ID);
    		} else if(StringUtils.equals(authResultCode, "8")) {
    			throw new APIException("Retired ID not authorized to login", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_RETIRE_USER);
    		} else if(StringUtils.equals(authResultCode, "18")) {
    			throw new APIException("Suspended ID not authorized to login.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_CORP_USER);
    		} else if(StringUtils.equals(authResultCode, "21")) {
    			throw new APIException("Corporate ID not authorized to login.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_STOP_USER);
    		} else if(StringUtils.equals(authResultCode, "26")) {
    			throw new APIException("Invalid Password.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_PASSWD_10);
    		} else if(StringUtils.equals(authResultCode, "27")) {
    			throw new APIException("Invalid Password.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_PASSWD_10);
    		} else if(StringUtils.equals(authResultCode, "30")) {
    			throw new APIException("Qook or Show ID not authorized to login.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_QOOKNSHOW);
    		} else if(StringUtils.equals(authResultCode, "0")) {
    			throw new APIException("Invalid Username/Password.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_SYSTEM_ERROR);
    		} else {
    			String errMsg = String.format("%s (%s)", StringUtils.defaultIfBlank(authResultMap.get("resultMsg"), ""), authResultCode);
    			throw new APIException(errMsg, HttpStatus.UNAUTHORIZED, ErrorCode.SDP_UNKNOWN_ERROR);
    		}
            
            return user;
        	
        } else {

        	UserVo user = new UserVo();
        	user.setAge(30);
        	user.setLoginPassYn(true);
        	user.setMbrId(userId);
        	String[] custIdList = {userId};
        	user.setCustIdList(custIdList);
        	
        	if ("ghms01".equals(userId)) {
            	user.setCredentialId("8000000001");
        	} else if ("ghms02".equals(userId)) {
            	user.setCredentialId("8000000002");
        	} else if("ghms03".equals(userId)) {
            	user.setCredentialId("8000000003");
        	} else if("ghms04".equals(userId)) {
            	user.setCredentialId("8000000004");
        	} else if("ghms05".equals(userId)) {
            	user.setCredentialId("8000000007");
        	} else if("ghms06".equals(userId)) {
            	user.setCredentialId("8000000008");
        	} else if("kt01".equals(userId)) {
            	user.setCredentialId("8000000005");
        	} else if("kt02".equals(userId)) {
            	user.setCredentialId("8000000006");
        	} else if("kt03".equals(userId)) {
             	user.setCredentialId("8000000009");
         	} else if("kt04".equals(userId)) {
             	user.setCredentialId("8000000010");
         	} else if("kt05".equals(userId)) {
             	user.setCredentialId("8000000011");
         	} else if("kt06".equals(userId)) {
             	user.setCredentialId("8000000012");
         	} else if(userId.startsWith("ghmstest") && userId.length() == 11) {
         		user.setCredentialId("7000000"+userId.replaceAll("ghmstest", ""));
         	} else {
    			throw new APIException("Invalid Username.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_PASSWD);
         	}

        	// 테스트 코드 todo
        	if("ghmstest004".equals(userId)) {
        		user.setAge(10);
        	}
        	
            return user;
        }
        
    }
    
    /**
     * "KT인프라연동서버" 통해서 SDP 연동을 처리하는 메쏘드(고도화용)
     * 
     * @param userId        아이디
     * @param passwd        패스워드
     * @return              SDP 연동 User 객체
     * @throws APIException
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    private UserVo checkSDPAuthUpgrade(String userId, String passwd) throws APIException {

        HashMap<String, String> authResultMap = null; 
        
        // ktinfra.sdp.useyn
        String ktinfraSdpUseyn = env.getProperty("ktinfra.sdp.useyn");
        
        if("Y".equals(ktinfraSdpUseyn) || "ghmstest002".equals(userId)) {

    		try {
    			// OIF_127(올레 사용자 로그인) 요청
    			authResultMap = ktService.sendLoginRequest(userId, passwd);
    		} catch(Exception e) {
    			throw new APIException("SDP Exception (Login)", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_NETWWORK_ERROR);
    		}

    		String authResultCode = authResultMap.get("resultCode");
    		int loginFailCounter = NumberUtils.toInt(authResultMap.get("loginFailCounter"));
    		UserVo user = null;

    		if(StringUtils.equals(authResultCode, "1")) {

    			// 성공
    			String credentialId = authResultMap.get("credentialId");
    			// credentialId 업데이트

    			try {
    				// OIF_138 호출
    				user = ktService.sendSubsRequestUpgrade(userId, credentialId);
    	        	user.setLoginPassYn(true);
    	        	user.setMbrId(userId);
    	        	
    	        	// 테스트 코드 todo
    	        	if("ghmstest004".equals(userId)) {
    	        		user.setAge(10);
    	        	}
    	        	
    			} catch(Exception e) {
    				throw new APIException("SDP Exception (Subscription)", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_NETWWORK_ERROR);
    			}
        		
        		try {
        			
        			List<String> apSvcIdList = new ArrayList<String>();
        			String[] icisSaIdList = user.getIcisSaIdList();
        			if(ArrayUtils.isEmpty(icisSaIdList)) {
        				for(String icisSaId : icisSaIdList) {
                			// OIF_114 호출
                			List<String> apSaIdList = ktService.sendSpecificSubscpnInfo(icisSaId);
                			if(apSaIdList != null && !apSaIdList.isEmpty()) {
                				
                				// 혹시 몰라 중복 제거
                				for(String apSaId : apSaIdList) {
                					if(!apSvcIdList.contains(apSaId)) {
                						apSvcIdList.add(apSaId);
                					}
                				}
                				user.setApSvcIdList(apSvcIdList);
                			}	
        				}
        			}
    	        	
        		} catch(Exception e) {
    				throw new APIException("SDP Exception (SpecificSubscpnInfo)", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_NETWWORK_ERROR);
        		}
    			
    		} else if(StringUtils.equals(authResultCode, "4")) {
    			if(loginFailCounter >= 5) {
    				throw new APIException("Invalid Password.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_PASSWD_05);
    			} else {
    				throw new APIException("Invalid Password.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_PASSWD);
    			}
    		} else if(StringUtils.equals(authResultCode, "10")) {
    			throw new APIException("Invalid Username.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_ID);
    		} else if(StringUtils.equals(authResultCode, "8")) {
    			throw new APIException("Retired ID not authorized to login", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_RETIRE_USER);
    		} else if(StringUtils.equals(authResultCode, "18")) {
    			throw new APIException("Suspended ID not authorized to login.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_CORP_USER);
    		} else if(StringUtils.equals(authResultCode, "21")) {
    			throw new APIException("Corporate ID not authorized to login.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_STOP_USER);
    		} else if(StringUtils.equals(authResultCode, "26")) {
    			throw new APIException("Invalid Password.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_PASSWD_10);
    		} else if(StringUtils.equals(authResultCode, "27")) {
    			throw new APIException("Invalid Password.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_PASSWD_10);
    		} else if(StringUtils.equals(authResultCode, "30")) {
    			throw new APIException("Qook or Show ID not authorized to login.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_QOOKNSHOW);
    		} else if(StringUtils.equals(authResultCode, "0")) {
    			throw new APIException("Invalid Username/Password.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_SYSTEM_ERROR);
    		} else {
    			String errMsg = String.format("%s (%s)", StringUtils.defaultIfBlank(authResultMap.get("resultMsg"), ""), authResultCode);
    			throw new APIException(errMsg, HttpStatus.UNAUTHORIZED, ErrorCode.SDP_UNKNOWN_ERROR);
    		}
            
            return user;
        	
        } else {

        	UserVo user = new UserVo();
        	user.setAge(30);
        	user.setLoginPassYn(true);
        	user.setMbrId(userId);
        	String[] custIdList = {userId};
        	user.setCustIdList(custIdList);
        	
        	if ("ghms01".equals(userId)) {
            	user.setCredentialId("8000000001");
        	} else if ("ghms02".equals(userId)) {
            	user.setCredentialId("8000000002");
        	} else if("ghms03".equals(userId)) {
            	user.setCredentialId("8000000003");
        	} else if("ghms04".equals(userId)) {
            	user.setCredentialId("8000000004");
        	} else if("ghms05".equals(userId)) {
            	user.setCredentialId("8000000007");
        	} else if("ghms06".equals(userId)) {
            	user.setCredentialId("8000000008");
        	} else if("kt01".equals(userId)) {
            	user.setCredentialId("8000000005");
        	} else if("kt02".equals(userId)) {
            	user.setCredentialId("8000000006");
        	} else if("kt03".equals(userId)) {
             	user.setCredentialId("8000000009");
         	} else if("kt04".equals(userId)) {
             	user.setCredentialId("8000000010");
         	} else if("kt05".equals(userId)) {
             	user.setCredentialId("8000000011");
         	} else if("kt06".equals(userId)) {
             	user.setCredentialId("8000000012");
         	} else if(userId.startsWith("ghmstest") && userId.length() == 11) {
         		user.setCredentialId("7000000"+userId.replaceAll("ghmstest", ""));
         	} else {
    			throw new APIException("Invalid Username.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_PASSWD);
         	}

        	// 테스트 코드 todo
        	if("ghmstest004".equals(userId)) {
        		user.setAge(10);
        	}
        	
            return user;
        }
        
    }
    
    /**
     * 신규 회원 일련번호 발급 메쏘드
     * 디비 스키마 관리가 되지 않는 문제가 있어 자동으로 시퀀스 존재 여부 감지 필요  
     * 시퀀스 존재하지 않는 경우 시퀀스 생성해서 회원 일련번호를 발급함
     * 
     * @return      신규 발급한 회원 일련번호
     */
    private long createMbrSeq() {
        
        long mbrSeq = 0;
        
        try {
            mbrSeq = userDao.createMbrSeq();
        } catch(Throwable t) {
            
            log.warn(t.getMessage(), t);
            
            if(sequenceNotFound(t)) {
                userDao.createMbrBasTableSequence();
                
                try {
                    mbrSeq = userDao.createMbrSeq();    
                } catch(Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
        
        return mbrSeq;
    }
    
    /**
     * 시퀀스가 없어 에러가 발생했는지 판단하는 메쏘드 
     * 
     * @param t             Throwable 객체 
     * @return              시퀀스가 존재하지 않아 발생한 문제인가 여부
     */
    private boolean sequenceNotFound(Throwable t) {

        boolean sequenceNotFound = false;
        
        Throwable cause = t.getCause();
        if(cause instanceof PSQLException) {

            String state = ((PSQLException) cause).getSQLState();
            if(StringUtils.equalsIgnoreCase(state, "42P01")) {
                sequenceNotFound = true;
            }
        }       
        
        return sequenceNotFound;
    }
    
    /**
     * 사용자의 약관 동의 필요 여부 체크 메쏘드
     * 
     * @param userBase      기본 User 정보 객체
     * @return              사용자 약관 동의 필요 여부
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    private boolean checkTermAgreeRequired(UserVo userBase) {
        
        try {

            String unitSvcCd    = userBase.getUnitSvcCd();
            long mbrSeq         = userBase.getMbrSeq(); 
            
            int count = userDao.getAgreeRequiredTermsCount(unitSvcCd, mbrSeq);
            
            log.info("## checkTermAgreeRequired count : " + count);
            return count > 0;
            
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            
            return false;
        }
    }
    
    /**
     * 법정대리인 동의여부 조회 메쏘드
     * 
     * @param sdpUser       SDP 연동 User 객체
     * @param userBase      기본 User 정보 객체
     * @return              법정대리인 동의여부
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    private boolean checkParentsAgreeRequired(UserVo sdpUser, UserVo userBase) {
        
        try {

            String unitSvcCd  = userBase.getUnitSvcCd();
            long mbrSeq       = userBase.getMbrSeq();
            
            int age = sdpUser.getAge();
            int agreeCount = userDao.getParentsAgreeCount(unitSvcCd, mbrSeq);
            
            log.info("## checkParentsAgreeRequired : age[{}], agreeCount[{}]", age, agreeCount);
            
            return (age < 14 && agreeCount == 0);
            
        } catch(Exception e) {
            log.warn(e.getMessage(), e);
            
            return false;
        }
    }
    
    /**
     * 사용자의 청약 여부를 체크 및 회원 정보 매핑, 초기 이름 설정 메쏘드 
     * 
     * @param sdpUser       SDP 연동 User 객체
     * @param userBase      기본 User 정보 객체
     * @return              사용자의 청약 여부
     */
    private boolean checkUserSubscription(UserVo sdpUser, UserVo userBase) {

        long mbrSeq         = userBase.getMbrSeq();
        String dstrCd       = userBase.getDstrCd();
        String svcThemeCd   = userBase.getSvcThemeCd();
        String unitSvcCd    = userBase.getUnitSvcCd();
        
        String[] custIdList = sdpUser.getCustIdList();

        // custId 가 아예 없는 경우 바로 리턴. 가입 상태 아님
        if(ArrayUtils.isEmpty(custIdList)) {
            log.info("## checkUserSubscription : mbrSeq[{}], mbrId[{}], custId[{}]", mbrSeq, "NOT FOUND");
            return false;
        }
        if(!ArrayUtils.isEmpty(custIdList)) {
            for(String custId : custIdList) {
                log.info("## checkUserSubscription : mbrSeq[{}], mbrId[{}], custId[{}]", mbrSeq, custId);
            }	
        }

        try {

            int mbrSvcTgtBasCount = userDao.getMbrSvcTgtBasCount(mbrSeq, dstrCd, svcThemeCd, unitSvcCd, CommonConstants.GHMS_GW_SVC_TGT_TYPE_CD);

            int nullMbrSpotDevCount = 0;
            if(!ArrayUtils.isEmpty(custIdList)) {
    			List<SpotDev> nullMbrSpotDevBas = userDao.getNullMbrSpotDevBas(custIdList);
    			nullMbrSpotDevCount = CollectionUtils.size(nullMbrSpotDevBas);

    			if(nullMbrSpotDevCount > 0) {
    	            // 서비스 대상 기본 테이블에 mbr_seq 업데이트
    	            sdpUser.setDstrCd(userBase.getDstrCd());
    	            sdpUser.setSvcThemeCd(userBase.getSvcThemeCd());
    	            sdpUser.setUnitSvcCd(userBase.getUnitSvcCd());
    	            sdpUser.setMbrSeq(mbrSeq);
    	            userDao.updateSvcTgtBasMbrSeq(sdpUser);
    			}	
            }
            
            // 청약자라면 IDMS 정보로 닉네임 변경
            if(mbrSvcTgtBasCount + nullMbrSpotDevCount > 0) {
            	
            	MasterUserKey key = new MasterUserKey();
            	key.setDstrCd(userBase.getDstrCd());
            	key.setSvcThemeCd(userBase.getSvcThemeCd());
            	key.setUnitSvcCd(userBase.getUnitSvcCd());
            	key.setUserNo(mbrSeq);
            	
            	String nickName = masterDao.selectUserNickName(key);
            	
            	// 닉네임이 디폴트(아이디) 닉네임 경우 청약정보의 사용자 이름으로 변경
            	if(sdpUser.getMbrId().equals(nickName)) {
            		
            		String custNm = masterDao.selectSvcTgtNm(key);
            		
            		if(StringUtils.isNotEmpty(custNm)) {
            			key.setUserNm(custNm);
            			masterDao.updateUserNickName(key);	
            		}
            	}
            }

            // 청약이 하나라도 있으면 true 리턴
            return (mbrSvcTgtBasCount + nullMbrSpotDevCount) > 0;
        } catch(Exception e) {
            log.warn(e.getMessage(), e);
            
            return false;
        }
    }

    /**
     * 토큰 재발급 메서드
     * 
     * @param authToken     로그인 인증 토큰
     * @return              신규 발급한 로그인 인증 토큰 포함하는 User 객체
     * @throws APIException
     */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    public UserVo refreshAuthToken(String authToken) throws APIException {
        
        AuthToken refreshAuthToken = AuthToken.refresh(authToken, null);
        
        UserVo newAuthToken = new UserVo();
        newAuthToken.setNewAuthToken(refreshAuthToken.getToken());
        
        // 센싱태그 리스트
//        newAuthToken.setSnsnTagBasVo(userDao.selectSnsnTagBasList(CommonConstants.UNIT_SVC_CD));
        newAuthToken.setSnsnTagBasVo(getSnsTagBasList());
        
        return newAuthToken;
    }

	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public HashMap<String, Object> selectUserSvcExistCnt(Long mbrSeq, String dstrCd, String svcThemeCd, String unitSvcCd, String termlId) throws APIException {
		return userDao.getUserSvcExistCnt(mbrSeq, dstrCd, svcThemeCd, unitSvcCd, termlId);
	}

	/**
	 * 센싱태그 리스트 setting
	 * @return
	 * @throws APIException
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public List<SnsnTagBasVo> getSnsTagBasList() throws APIException {
		
		if(snsTagBasList == null || snsTagBasList.isEmpty() || snsTagBasList.size() == 0) {
			snsTagBasList = userDao.selectSnsnTagBasList(CommonConstants.UNIT_SVC_CD);
		}
		
		return snsTagBasList;
		
	}
	
	/**
	 * 모든 허브 서비스 중지 여부 ('Y','N')
	 * @param token
	 * @return
	 */
	private boolean checkStopService(AuthToken token) {
		
		Map<String, Long> service = userDao.selectCheckStopService(token.getUserNoLong(), token.getDstrCd(), token.getSvcThemeCd(), token.getUnitSvcCd());
		
		Long useCnt = service.get("useCnt");
		Long stopCnt = service.get("stopCnt");
		
		if(stopCnt.intValue() > 0 && useCnt.intValue() == 0) {
			return true;			
		} else {
			return false;
		}
	}
	
	/**
	 * GiGA WiFi home AP 청약 체크
	 * @param sdpUser
	 */
	private void checkGigaWIFIHomeSubscription(UserVo sdpUser, AuthToken token) {
		
		List<String> apSvcIdList = sdpUser.getApSvcIdList();
		// 청약 갯수가 3개 이상일 경우 수동 생성(IF-HLS-610)
		if(apSvcIdList != null && !apSvcIdList.isEmpty() && apSvcIdList.size() < 3) {
			
			for(String svcContId : apSvcIdList) {
				
				SvcTgtBas svcTgtBas = ghmsProvisionDao.selectSvcTgtBasBySvcContId(svcContId);
				
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
			        GetServiceEquipInfoResult equipInfoResult = ktService.sendGetServiceEquipInfo(sdpUser.getMbrId(), token.getDeviceId(), "", svcContId);
			        
			        if("T".equals(equipInfoResult.getResultCode())) {
			        	
			        	List<NetworkStatusInfo> networkStatusInfo = equipInfoResult.getNetworkStatusInfo();
			        	
			        	if(networkStatusInfo != null && !networkStatusInfo.isEmpty()) {

				        	for(NetworkStatusInfo networkInfo : networkStatusInfo) {
				        		List<HoldingEquipInfo> holdingEquipInfoList = networkInfo.getHoldingEquipInfoList();
				        		
				        		if(holdingEquipInfoList != null && !holdingEquipInfoList.isEmpty()) {

					        		for(HoldingEquipInfo holdingEquipInfo : holdingEquipInfoList) {
					        			
					        			if("HHG".equals(holdingEquipInfo.getDeviceType()) && svcContId.equals(holdingEquipInfo.getRemark())) {

					    	            	// 동일 타입의 장비 갯수를 가져와서 장비 이름 뒤에 숫자를 붙힌다.
					    	                SpotDevBasKey key = new SpotDevBasKey();
					    	                key.setSvcTgtSeq(svcTgtSeq);
					    	                key.setDevTypeCd(DeviceType.KT_AP_SVC.getCode());
					    	                key.setDevModelCd(DeviceType.KT_AP_SVC.getGroupNm());
					    	            	int typeCount = initDeviceAddDao.selectSoptDevBasByType(key);
					    	            	
					    	            	key.setDevUUID(UUID.randomUUID().toString());
					    	            	key.setSpotDevId("A_" + holdingEquipInfo.getMacAddress().replaceAll(":", ""));
					    	            	key.setDevNm(DeviceType.KT_AP_SVC.getModelNm() + com.kt.giga.home.openapi.ghms.util.string.StringUtils.getLPad(String.valueOf(typeCount), "0", 2));
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
					    	    			authKey.setGroupSetUpCd(env.getProperty("group.setup.cd", "A902"));
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
					
					// 존재 하지만 MAC 정보가 변경 되었을 경우.
					DeviceGw deviceApInfo = smartCheckerDao.getIotInfoSvcContId(svcContId, CommonConstants.GHMS_AP_SVC_TGT_TYPE_CD);
			        
			        // 스마트홈체커 연동 후 장치(AP) 데이터 MAC 정보 확인
			        GetServiceEquipInfoResult equipInfoResult = ktService.sendGetServiceEquipInfo(sdpUser.getMbrId(), token.getDeviceId(), "", svcContId);
			        
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
				}
			}
			
			String[] svcContIdArray = (String[]) apSvcIdList.toArray(new String[apSvcIdList.size()]);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("delYn", "Y");
			map.put("oprtSttusCd", "0003");
			map.put("dstrCd", CommonConstants.DSTR_CD);
			map.put("svcThemeCd", CommonConstants.SVC_THEME_CD);
			map.put("unitSvcCd", CommonConstants.UNIT_SVC_CD);
			map.put("svcTgtTypeCd", CommonConstants.GHMS_AP_SVC_TGT_TYPE_CD);
			map.put("mbrSeq", token.getUserNoLong());
			map.put("svcContIdArray", svcContIdArray);
			
			userDao.updateSvcTgtBas(map);
		}
	}

	/**
	 * 임의 고객 인증
	 * @param token
	 * @param key
	 * @return
	 * @throws APIException
	 */
	public BaseVo anyCustomerCheck(AuthToken token, LoginKey key) throws APIException {
		
		BaseVo vo = new BaseVo();
		vo.setResultCode("0");
		
		// OIF_115 연동
		Map<String, Object> specificSubsAndUserInfo = ktService.sendSpecificSubsAndUserInfo(key.getServiceNo());
		log.debug("OIF_115 : " + specificSubsAndUserInfo.toString());
		if("1".equals(specificSubsAndUserInfo.get("resultCode").toString())) {

			if(specificSubsAndUserInfo.get("birthDay") == null) {
				vo.setResultCode("-1");
				vo.setResultMsg("생년월일 정보가 존재하지 않습니다.");
			} else if(specificSubsAndUserInfo.get("gender") == null) {
				vo.setResultCode("-1");
				vo.setResultMsg("성별 정보가 존재하지 않습니다.");
			} else if(specificSubsAndUserInfo.get("lunarSolarCd") == null) {
				vo.setResultCode("-1");
				vo.setResultMsg("양력/음력 구분 정보가 존재하지 않습니다.");
			} else {
				
				String birthDay = specificSubsAndUserInfo.get("birthDay").toString();
				String gender = specificSubsAndUserInfo.get("gender").toString();
				String lunarSolarCd = specificSubsAndUserInfo.get("lunarSolarCd").toString();
				
				if(!key.getBirthDay().equals(birthDay)) {
					vo.setResultCode("-1");
					vo.setResultMsg("생년월일이 일치하지 않습니다.");
				} else if(!key.getGender().equals(gender)) {
					vo.setResultCode("-1");
					vo.setResultMsg("성별이 일치하지 않습니다.");
				} else if(!key.getLunarSolarCd().equals(lunarSolarCd)) {
					vo.setResultCode("-1");
					vo.setResultMsg("양력/음력 구분이 일치하지 않습니다.");
				} else {

					// 임의고객청약내역 데이터 확인 후 서비스대상기본 테이블 고객 정보 갱신.
					Long svcTgtSeq = userDao.selectRanCustSubsTxn(key.getServiceNo());
					
					if(svcTgtSeq != null) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("mbr_seq", token.getUserNoLong());
						map.put("svc_tgt_seq", svcTgtSeq);
						ghmsProvisionDao.updateSvcTgtBas(map);
					} else {
						vo.setResultCode("-1");
						vo.setResultMsg("청약 데이터가 존재 하지 않습니다.");
					}
				}
			}
			
		} else {
			vo.setResultCode("-1");
			vo.setResultMsg(specificSubsAndUserInfo.get("resultMsg").toString());
		}
		
		return vo;
	}

}
