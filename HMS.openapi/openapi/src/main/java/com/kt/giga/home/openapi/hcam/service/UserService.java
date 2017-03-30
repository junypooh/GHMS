package com.kt.giga.home.openapi.hcam.service;

import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.common.AuthToken;
import com.kt.giga.home.openapi.hcam.HCamConstants.ErrorCode;
import com.kt.giga.home.openapi.hcam.dao.DeviceDao;
import com.kt.giga.home.openapi.hcam.dao.UserDao;
import com.kt.giga.home.openapi.hcam.domain.AppInitRequest;
import com.kt.giga.home.openapi.hcam.domain.LoginHistory;
import com.kt.giga.home.openapi.hcam.domain.User;
import com.kt.giga.home.openapi.hcam.domain.UserAuth;
import com.kt.giga.home.openapi.service.APIException;
import com.kt.giga.home.openapi.service.ECService;
import com.kt.giga.home.openapi.vo.spotdev.SpotDev;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 인증/로그인, 토큰갱신등 회원관련 처리 서비스
 *
 * @author
 */
@Service("HCam.UserService")
public class UserService {

    /**
     * 로그인 만료 시간 (인증 토큰 만료 시간)
     */
    private static final int    LOGIN_EXPIRE_TIME_MIN       = 10;
    /**
     * 동일 아이디에 대한 최대 동시 접속 수
     */
    private static final int    LOGIN_MAX_COUNT             = 5;
    /**
     * 사용자 설정 그룹 코드
     */
    private static final String GROUP_SETUP_CD              = "A900";
    /**
     * 동일 사용자의 반복 로그인 무시를 위한 버퍼링 타임
     */
    private static final long   LOGIN_BUFFERING_TIME_MILLIS = 1000;
    /**
     * 로거
     */
    private              Logger log                         = LoggerFactory.getLogger(this.getClass());
    /**
     * OpenAPI 환경 프라퍼티
     */
    @Autowired
    private APIEnv                 env;
    /**
     * 사용자 매퍼 인터페이스
     */
    @Autowired
    private UserDao                userDao;
    /**
     * 디바이스 매퍼 인터페이스
     */
    @Autowired
    private DeviceDao              deviceDao;
    /**
     * KT 인프라 연동 서비스
     */
    @Autowired
    private KTInfraService         ktService;
    /**
     * EC 연동 서비스
     */
    @Autowired
    private ECService              ecService;
    /**
     * 이벤트 코어 연동 서비스
     */
    @Autowired
    private EventEmitService       eventService;
    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Resource(name = "loginHistoryQueue")
    private BlockingQueue<User> loginSuccessQueue;
    /**
     * 동일 사용자의 반복 로그인 무시를 위한 시간 저장 맵
     */
    private Map<String, Long> loginTimeMap = new HashMap<>();

    /**
     * 아이디/패스워드 인증 메쏘드
     *
     * @param authToken 앱 초기실행시 발급한 인증 토큰
     * @param userId    아이디
     * @param passwd    패스워드
     * @throws Exception
     * @return {@link com.kt.giga.home.openapi.hcam.domain.UserAuth}
     */
    public UserAuth auth(String authToken, String userId, String passwd) throws Exception {

        UserAuth userAuth = new UserAuth();

        try {

            // 1. AuthToken 디코딩
            final AuthToken initAuthToken = checkInitAuthToken(authToken);

            // 2. KT인프라 연동 서버 SDP 인증 처리, 인증 실패한 경우 바로 익셉션 처리
            User sdpUser = checkSDPAuth(userId, passwd);

            // 회선 아이디 여부 Set
            userAuth.setInternetOnlyID(sdpUser.isInternetOnlyID());

            // 3. 회원정보 체크, 신규 회원의 경우 회원 테이블에 등록
            final User userBase = checkUserBase(initAuthToken, sdpUser, userId);

            // 4. 공지사항 옵션 (현재 버전에서 로그인 시점의 공지는 사용하지 않음)
            // String noticeOption = "0";

            // 5. 약관 동의여부 옵션
            String termsYn = checkTermAgreeRequired(userBase) ? "Y" : "N";

            // 6. 보호자 동의여부 옵션
            String kidsCheckOption = checkParentsAgreeRequired(sdpUser, userBase) ? "1" : "0";

            // 7. 청약상태 체크, 동기화
            String subsYn = checkUserSubscription(sdpUser, userBase) ? "Y" : "N";

            // 8. 동시접속 로그인 처리
            final AuthToken loginAuthToken = login(initAuthToken, userBase);

            // 9. 로그인 Push 및 디바이스 정보 초기화
            if ("Y".equals(env.getProperty("kpns.kafka.useyn"))) {
                if (loginAuthToken != null) {
                    executor.execute(new Runnable() {
                        public void run() {

                            try {
                                sendLoginEventPush(loginAuthToken, userBase);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            // 기존 등록된 전화번호가 01012345678 인 경우 전화번호 미등록 처리
            String telYn = "Y";
            if (loginAuthToken == null || StringUtils.isBlank(loginAuthToken.getTelNo())) {
                telYn = "N";
            } else {
                if ("01012345678".equals(loginAuthToken.getTelNo())) {
                    telYn = "N";
                }
            }

            // 로그인 실패한 경우 기존 토큰을 그대로 내려줌
            userAuth.setNewAuthToken(loginAuthToken == null ? authToken : loginAuthToken.getToken());
            userAuth.setTermsYn(termsYn);
            userAuth.setKidsCheckOption(kidsCheckOption);
            userAuth.setLoginResult(loginAuthToken == null ? "2" : "1");
            userAuth.setAge(String.valueOf(sdpUser.getAge()));
            userAuth.setSubsYn(subsYn);
            userAuth.setTelYn(telYn);

        } catch(APIException e) {
            throw e;
        } catch(Exception e) {
            log.warn(e.getMessage(), e);
            throw new APIException(e.getMessage(), HttpStatus.UNAUTHORIZED, ErrorCode.UNKNOWN_EXCEPTION);
        }

        return userAuth;
    }

    /**
     * 휴대폰 번호 등록 메쏘드
     *
     * @param authToken		앱 초기실행시 발급한 인증 토큰
     * @param appInitRequest    {@link com.kt.giga.home.openapi.hcam.domain.AppInitRequest}
     * @return				인증 최종 결과 UserAuh 객체
     * @throws Exception
     */
    public UserAuth regTel(String authToken, AppInitRequest appInitRequest) throws Exception {

        UserAuth userAuth = new UserAuth();

        try {

            // 1. AuthToken 디코딩
            final AuthToken loginAuthToken = checkLoginAuthToken(authToken);

            // 2. 회원정보 조회
            final User userBase = new User();
            userBase.setDstrCd(env.getDstrCd());
            userBase.setSvcThemeCd(env.getSvcThemeCd());
            userBase.setUnitSvcCd(loginAuthToken.getSvcCode());
            userBase.setMbrId(loginAuthToken.getUserId());
            userBase.setMbrSeq(loginAuthToken.getUserNoLong());
            userBase.setTermlId(loginAuthToken.getDeviceId());
            userBase.setPnsRegId(loginAuthToken.getPnsRegId());
            userBase.setTelNo(appInitRequest.getTelNo());

            // 3. 휴대폰 번호 등록
            userDao.updateAppUser(userBase);

            // 4. 전화번호 포함된 토큰 업데이트
            final AuthToken newAuthToken = AuthToken.encodeAuthToken(loginAuthToken.getDeviceId(),
                    appInitRequest.getTelNo(),
                    loginAuthToken.getPnsRegId(),
                    loginAuthToken.getUserNoLong(),
                    loginAuthToken.getUserId(),
                    loginAuthToken.getSvcCode());

            userAuth.setNewAuthToken(newAuthToken.getToken());

            // 5. 로그인 Push 및 디바이스 정보 초기화
            if("Y".equals(env.getProperty("kpns.kafka.useyn"))) {
                executor.execute(new Runnable() {
                    public void run() {
                        try {
                            sendLoginEventPush(newAuthToken, userBase);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        } catch(APIException e) {
            throw e;
        } catch(Exception e) {
            log.warn(e.getMessage(), e);
            throw new APIException(e.getMessage(), HttpStatus.UNAUTHORIZED, ErrorCode.UNKNOWN_EXCEPTION);
        }

        return userAuth;
    }

    /**
     * PNS regid 등록 메쏘드
     *
     * @param authToken		앱 초기실행시 발급한 인증 토큰
     * @param appInitRequest    {@link com.kt.giga.home.openapi.hcam.domain.AppInitRequest}
     * @return				인증 최종 결과 UserAuh 객체
     * @throws Exception
     */
    public UserAuth regPns(String authToken, AppInitRequest appInitRequest) throws Exception {

        UserAuth userAuth = new UserAuth();

        try {

            // 1. AuthToken 디코딩
            final AuthToken loginAuthToken = checkLoginAuthToken(authToken);

            // 2. 회원정보 조회
            final User userBase = new User();
            userBase.setDstrCd(env.getDstrCd());
            userBase.setSvcThemeCd(env.getSvcThemeCd());
            userBase.setUnitSvcCd(loginAuthToken.getSvcCode());
            userBase.setMbrId(loginAuthToken.getUserId());
            userBase.setMbrSeq(loginAuthToken.getUserNoLong());
            userBase.setTermlId(loginAuthToken.getDeviceId());
            userBase.setPnsRegId(appInitRequest.getPnsRegId());
            userBase.setTelNo(loginAuthToken.getTelNo());

            // 3. PNS regid 등록
            userDao.updateAppUser(userBase);

            // 4. 동일한 PNS regid 등록 단말 삭제
            User[] userList = getSvcTgtConnBasByPnsRegId(loginAuthToken.getSvcCode(), appInitRequest.getPnsRegId());

            if(userList != null || userList.length > 0) {
                for(int i = 0; i < userList.length; i++) {
                    if(userList[i].getMbrSeq().longValue() != userBase.getMbrSeq().longValue() || !userList[i].getTermlId().equals(userBase.getTermlId())) {
                        userDao.deleteAppUserToken(userBase.getDstrCd(), userBase.getSvcThemeCd(), userBase.getUnitSvcCd(),
                                userList[i].getMbrSeq(), userList[i].getTermlId());
                        userDao.deleteUserSetup(userBase.getDstrCd(), userBase.getSvcThemeCd(), userBase.getUnitSvcCd(),
                                userList[i].getMbrSeq(), userList[i].getTermlId(), GROUP_SETUP_CD);
                        userDao.deleteAppUser(userBase.getDstrCd(), userBase.getSvcThemeCd(), userBase.getUnitSvcCd(),
                                userList[i].getMbrSeq(), userList[i].getTermlId());
                    }
                }
            }

            // 5. 변경된 토큰 정보 리턴
            AuthToken newAuthToken = AuthToken.encodeAuthToken(loginAuthToken.getDeviceId(),
                    loginAuthToken.getTelNo(),
                    loginAuthToken.getPnsRegId(),
                    loginAuthToken.getUserNoLong(),
                    loginAuthToken.getUserId(),
                    loginAuthToken.getSvcCode());

            userAuth.setNewAuthToken(newAuthToken.getToken());

        } catch(APIException e) {
            throw e;
        } catch(Exception e) {
            log.warn(e.getMessage(), e);
            throw new APIException(e.getMessage(), HttpStatus.UNAUTHORIZED, ErrorCode.UNKNOWN_EXCEPTION);
        }

        return userAuth;
    }

    /**
     * 로그아웃 메쏘드
     *
     * @param authToken		로그인 인증 토큰
     * @throws Exception
     */
    @Async
    public void logout(String authToken) throws Exception {
        AuthToken loginAuthToken = checkLoginAuthToken(authToken);
        updateLoginAuthTokenExpired(loginAuthToken);
    }

    /**
     * 로그인 인증 토큰 갱신 메쏘드
     *
     * @param authToken		로그인 인증 토큰
     * @return				신규 발급한 로그인 인증 토큰 포함하는 User 객체
     * @throws Exception
     */
    public User refreshLoginAuthToken(String authToken) throws Exception {

        AuthToken newLoginAuthToken = checkLoginAuthToken(authToken);

        User newAuthToken = new User();
        newAuthToken.setNewAuthToken(newLoginAuthToken.getToken());

        return newAuthToken;
    }

    /**
     * 앱 초기 실행에서 발급한 인증토큰 유효성을 체크하는 메쏘드
     *
     * @param authToken		초기실행 인증 토큰
     * @return				초기실행 인증 토큰 객체
     * @throws Exception
     */
    public AuthToken checkInitAuthToken(String authToken) throws Exception {
        AuthToken initAuthToken = AuthToken.decodeAuthToken(authToken);
        if(!initAuthToken.isValidInitToken()) {
            throw new APIException("Invalid InitAuthToken", HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_INIT_AUTH_TOKEN);
        }

        return initAuthToken;
    }

    /**
     * 아이디/패스워드 인증 후 발급한 로그인 인증토큰 유효성을 체크하는 메쏘드
     *
     * @param authToken		로그인 인증 토큰
     * @return				로그인 인증 토큰 객체
     * @throws Exception
     */
    public AuthToken checkLoginAuthToken(String authToken) throws Exception {

        AuthToken loginAuthToken = AuthToken.decodeAuthToken(authToken);

        if(!loginAuthToken.isValidLoginToken()) {
            throw new APIException("Invalid LoginAuthToken", HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_LOGIN_AUTH_TOKEN);
        }

        String unitSvcCd	= loginAuthToken.getSvcCode();
        long mbrSeq			= NumberUtils.toLong(loginAuthToken.getUserNo());
        String termlId		= loginAuthToken.getDeviceId();

        if(!isInternalRequest(loginAuthToken) && isExpiredAppUser(unitSvcCd, mbrSeq, termlId)) {
            throw new APIException("Expired LoginAuthToken", HttpStatus.UNAUTHORIZED, ErrorCode.EXPIRED_AUTH_TOKEN);
        }

        log.info(loginAuthToken.toString());

        // 현재는 토큰을 갱신해도 앱으로 다시 내리지 않기 때문에 크게 의미는 없다.
        AuthToken newLoginAuthToken = AuthToken.refresh(authToken);
        updateLoginAuthToken(newLoginAuthToken);

        return newLoginAuthToken;
    }

    /**
     * 앱 접속자 정보를 삭제한다.
     *
     * @param unitSvcCd		단위 서비스 코드
     * @param mbrSeq		멤버 일련번호
     * @param termlId		단말 아이디
     */
    public void deleteAppUser(String unitSvcCd, long mbrSeq, String termlId) {
        String dstrCd = env.getDstrCd();
        String svcThemeCd = env.getSvcThemeCd();

        userDao.deleteAppUserToken(dstrCd, svcThemeCd, unitSvcCd, mbrSeq, termlId);
        userDao.deleteUserSetup(dstrCd, svcThemeCd, unitSvcCd, mbrSeq, termlId, GROUP_SETUP_CD);
        userDao.deleteAppUser(dstrCd, svcThemeCd, unitSvcCd, mbrSeq, termlId);
    }

    /**
     * 디비 접속 정보 기반으로 앱 접속자 토큰 만료 여부를 체크하는 메쏘드
     *
     * @param unitSvcCd		단위 서비스 코드
     * @param mbrSeq		멤버 일련번호
     * @param termlId		단말 아이디
     * @return				토큰 만료 여부
     */
    private boolean isExpiredAppUser(String unitSvcCd, long mbrSeq, String termlId) {

        boolean isExpired = false;
        try {

            ArrayList<User> appUserList = getAppUserList(unitSvcCd, mbrSeq, termlId);

            if(CollectionUtils.isNotEmpty(appUserList)) {
                User appUser = appUserList.get(0);
                if(StringUtils.equals(appUser.getStatus(), "0")) {
                    isExpired = true;
                }
            }

        } catch(Exception e) {
            log.warn(e.getMessage(), e);
        }

        return isExpired;
    }

    /**
     * @param authToken
     * @return
     */
    private boolean isInternalRequest(AuthToken authToken) {
        boolean isInternal = false;

        if(!env.getProperty("cms.user").equals(authToken.getUserId())) return isInternal;

        // TODO : 테스트를 위한 임시코드 삭제 필요
        if(env.getProperty("cms.user").equals(authToken.getUserId())) return true;

        try {
            String[] cmsServerList = env.splitProperty("cms.serverList");

            for(int i = 0; i < cmsServerList.length; i++) {
                if(cmsServerList[i].equals(authToken.getRequestIp())) {
                    isInternal = true;
                    break;
                }
            }

        } catch(Exception e) {
            log.warn(e.getMessage(), e);
        }

        return isInternal;
    }

    private boolean loginUpdateRequired(String termlId, long mbrSeq, long currentTimeMillis) {

        String loginTimeMapKey = termlId + "#" + mbrSeq;
        return (currentTimeMillis - MapUtils.getLong(loginTimeMap, loginTimeMapKey, 0L) > LOGIN_BUFFERING_TIME_MILLIS);
    }

    private void updateLoginTimeMap(String termlId, long mbrSeq, long currentTimeMillis) {

        String loginTimeMapKey = termlId + "#" + mbrSeq;
        loginTimeMap.put(loginTimeMapKey, currentTimeMillis);
    }

    /**
     * 로그인 인증 토큰 자동연장 처리 메쏘드
     * 테이블의 토큰 발급시간을 현재시간으로 갱신한다.
     *
     * @param newLoginAuthToken	신규 발급된 로그인 인증 토큰
     */
    private void updateLoginAuthToken(AuthToken newLoginAuthToken) {

        try {

            String termlId			= newLoginAuthToken.getDeviceId();
            long mbrSeq				= newLoginAuthToken.getUserNoLong();
            long currentTimeMillis	= System.currentTimeMillis();

            if(loginUpdateRequired(termlId, mbrSeq, currentTimeMillis)) {

                log.debug("## updateLoginAuthToken : loginUpdateRequired! {}, {}, {}", termlId, mbrSeq, currentTimeMillis);
                User appUser = new User(env.getDstrCd(), env.getSvcThemeCd(), newLoginAuthToken);
                appUser.setTermlId(newLoginAuthToken.getDeviceId());
                appUser.setAuthToken(newLoginAuthToken.getToken());
                userDao.updateAppUserToken(appUser);

                updateLoginTimeMap(termlId, mbrSeq, currentTimeMillis);
            }

        } catch(Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * 로그인 인증 토큰 만료 처리 메쏘드
     * 테이블의 토큰 발급시간을 널로 갱신한다.
     *
     * @param loginAuthToken 기존에 발급된 인증토큰
     */
    private void updateLoginAuthTokenExpired(AuthToken loginAuthToken) {

        try {

            User appUser = new User(env.getDstrCd(), env.getSvcThemeCd(), loginAuthToken);
            appUser.setTermlId(loginAuthToken.getDeviceId());
            appUser.setAuthToken(loginAuthToken.getToken());
            userDao.updateAppUserTokenExpired(appUser);

        } catch(Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * "KT인프라연동서버" 통해서 SDP 연동을 처리하는 메쏘드
     *
     * @param userId		아이디
     * @param passwd		패스워드
     * @return				SDP 연동 User 객체
     * @throws Exception
     */
    private User checkSDPAuth(String userId, String passwd) throws Exception {

        HashMap<String, String> authResultMap = null;

        try {
            authResultMap = ktService.sendLoginRequest(userId, passwd);
        } catch(Exception e) {
            log.warn(e.getMessage(), e.fillInStackTrace());
            throw new APIException("SDP Exception (Login)", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_NETWWORK_ERROR);
        }

        String authResultCode = authResultMap.get("resultCode");
        String credentialDetailTypeCode = authResultMap.get("credentialDetailTypeCode");

        int loginFailCounter = NumberUtils.toInt(authResultMap.get("loginFailCounter"));
        User user;

        if (StringUtils.equals(authResultCode, "1")) {
            // 성공
            String credentialId = authResultMap.get("credentialId");

            try {
                user = ktService.sendSubsRequest(userId, credentialId);
            } catch (Exception e) {
                log.warn(e.getMessage(), e.fillInStackTrace());
                throw new APIException("SDP Exception (Subscription)", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_NETWWORK_ERROR);
            }

            // 홈캠 청약 대상 고객 유형 중 생년월일이 없는 사용자는 에러 처리
            if (StringUtils.isEmpty(user.getBirth())) {
                throw new APIException("Require age", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_REQ_AGE);
            }

            // 인터넷 회선만 있는 고객
            if (StringUtils.equals(credentialDetailTypeCode, "02"))
                user.setInternetOnlyID(Boolean.TRUE);

        } else if (StringUtils.equals(authResultCode, "4")) {
            if (loginFailCounter >= 5) {
                throw new APIException("Invalid Password.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_PASSWD_05);
            } else {
                throw new APIException("Invalid Password.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_IDNPASSWD);
            }
        } else if (StringUtils.equals(authResultCode, "10")) {
            throw new APIException("Invalid Username.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_IDNPASSWD);
        } else if (StringUtils.equals(authResultCode, "8")) {
            throw new APIException("Retired ID not authorized to login", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_RETIRE_USER);
        } else if (StringUtils.equals(authResultCode, "11")) {
            throw new APIException("Require agreement on term.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_REQ_TERM);
        } else if (StringUtils.equals(authResultCode, "18")) {
            throw new APIException("Suspended ID not authorized to login.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_CORP_USER);
        } else if (StringUtils.equals(authResultCode, "21")) {
            throw new APIException("Corporate ID not authorized to login.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_STOP_USER);
        } else if (StringUtils.equals(authResultCode, "26")) {
            throw new APIException("Invalid Password.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_PASSWD_10);
        } else if (StringUtils.equals(authResultCode, "27")) {
            throw new APIException("Invalid Password.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_INVALID_PASSWD_10);
        } else if (StringUtils.equals(authResultCode, "30") || StringUtils.equals(credentialDetailTypeCode, "01") || StringUtils.equals(credentialDetailTypeCode, "03")) {
            throw new APIException("Qook or Show ID not authorized to login.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_QOOKNSHOW);
        } else if (StringUtils.equals(authResultCode, "0")) {
            throw new APIException("Invalid Username/Password.", HttpStatus.UNAUTHORIZED, ErrorCode.SDP_SYSTEM_ERROR);
        } else {
            String errMsg = String.format("%s (%s)", StringUtils.defaultIfBlank(authResultMap.get("resultMsg"), ""), authResultCode);
            throw new APIException(errMsg, HttpStatus.UNAUTHORIZED, ErrorCode.SDP_UNKNOWN_ERROR);
        }

        return user;
    }

    /**
     * 기본 회원 정보를 체크하고 신규회원인 경우 회원등록 처리
     *
     * @param initAuthToken 초기 인증 토큰
     * @param sdpUser       SDP 연동 User 객체
     * @param userId        사용자 아이디
     * @return 기본 User 정보 객체
     */
    private User checkUserBase(AuthToken initAuthToken, User sdpUser, String userId) {

        User userBase = userDao.getUserBase(userId);

        String dstrCd = env.getDstrCd();
        String svcThemeCd = env.getSvcThemeCd();
        String unitSvcCd = initAuthToken.getSvcCode();

        log.debug("## checkUserBase sdpUser.getCredentialId() : " + sdpUser.getCredentialId());

        // 신규 회원인 경우, 회원 기본 생성
        if (userBase == null) {

            long mbrSeq = createMbrSeq();
            userBase = new User(dstrCd, svcThemeCd, unitSvcCd, mbrSeq, userId);
            userBase.setCredentialId(sdpUser.getCredentialId());

            userDao.insertUserBase(userBase);
            userDao.insertServiceUserRelation(userBase);
            log.debug("## checkUserBase : New User. Create MbrSeq : {}", mbrSeq);
        } else {

            userDao.updateCredentialId(userBase.getMbrSeq(), sdpUser.getCredentialId());

            userBase.setDstrCd(dstrCd);
            userBase.setSvcThemeCd(svcThemeCd);
            userBase.setUnitSvcCd(unitSvcCd);
            log.debug("## checkUserBase : User Already Exist : {}", userBase.getMbrSeq());
        }

        return userBase;
    }

    /**
     * 신규 회원 일련번호 발급 메쏘드
     * 디비 스키마 관리가 되지 않는 문제가 있어 자동으로 시퀀스 존재 여부 감지 필요
     * 시퀀스 존재하지 않는 경우 시퀀스 생성해서 회원 일련번호를 발급함
     *
     * @return 신규 발급한 회원 일련번호
     */
    private long createMbrSeq() {

        long mbrSeq = 0;

        try {
            mbrSeq = userDao.createMbrSeq();
        } catch (Throwable t) {

            log.warn(t.getMessage(), t);

            if (sequenceNotFound(t)) {
                userDao.createMbrBasTableSequence();

                try {
                    mbrSeq = userDao.createMbrSeq();
                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }

        return mbrSeq;
    }

    /**
     * 시퀀스가 없어 에러가 발생했는지 판단하는 메쏘드
     *
     * @param t Throwable 객체
     * @return 시퀀스가 존재하지 않아 발생한 문제인가 여부
     */
    private boolean sequenceNotFound(Throwable t) {

        boolean sequenceNotFound = false;

        Throwable cause = t.getCause();
        if (cause instanceof PSQLException) {

            String state = ((PSQLException) cause).getSQLState();
            if (StringUtils.equalsIgnoreCase(state, "42P01")) {
                sequenceNotFound = true;
            }
        }

        return sequenceNotFound;
    }

    /**
     * 사용자의 청약 여부를 체크 및 회원 정보 매핑, 초기 이름 설정 메쏘드
     *
     * @param sdpUser  SDP 연동 User 객체
     * @param userBase 기본 User 정보 객체
     * @return 사용자의 청약 여부
     */
    private boolean checkUserSubscription(User sdpUser, User userBase) {

        long mbrSeq = userBase.getMbrSeq();
        String mbrId = userBase.getMbrId();

        String[] custIdList = sdpUser.getCustIdList();

        // custId 가 아예 없는 경우 바로 리턴. 가입 상태 아님
        if (ArrayUtils.isEmpty(custIdList)) {
            log.debug("## checkUserSubscription : mbrSeq[{}], mbrId[{}], custId[{}]", mbrSeq, mbrId, "NOT FOUND");
            return false;
        }

        for (String custId : custIdList) {
            log.debug("## checkUserSubscription : mbrSeq[{}], mbrId[{}], custId[{}]", mbrSeq, mbrId, custId);
        }

        try {

            int mbrSvcTgtBasCount = userDao.getMbrSvcTgtBasCount(mbrSeq);

            // mbrSeq 가 널인 SpotDev 는 신규 등록된 장비로 이름을 A, B, C 순차적으로 붙여줘야 함.
            ArrayList<SpotDev> nullMbrSpotDevBas = userDao.getNullMbrSpotDevBas(custIdList);
            int nullMbrSpotDevCount = CollectionUtils.size(nullMbrSpotDevBas);

            if (nullMbrSpotDevCount > 0) {

                for (int i = 0; i < nullMbrSpotDevCount; i++) {
                    SpotDev spotDev = nullMbrSpotDevBas.get(i);
                    if (StringUtils.isBlank(spotDev.getSpotDevNm())) {
                        String devNm = "카메라" + (char) (65 + i % 26 + mbrSvcTgtBasCount);
                        setDeviceName(spotDev.getDevUUID(), devNm);
                    }
                }

                sdpUser.setDstrCd(userBase.getDstrCd());
                sdpUser.setSvcThemeCd(userBase.getSvcThemeCd());
                sdpUser.setUnitSvcCd(userBase.getUnitSvcCd());
                sdpUser.setMbrSeq(mbrSeq);
                sdpUser.setMbrId(mbrId);
                userDao.updateSvcTgtBasMbrSeq(sdpUser);
            }

            // 청약이 하나라도 있으면 true 리턴
            return (mbrSvcTgtBasCount + nullMbrSpotDevCount) > 0;

        } catch (Exception e) {
            log.warn(e.getMessage(), e);

            return false;
        }
    }

    /**
     * 사용자의 약관 동의 필요 여부 체크 메쏘드
     *
     * @param userBase 기본 User 정보 객체
     * @return 사용자 약관 동의 필요 여부
     */
    private boolean checkTermAgreeRequired(User userBase) {

        try {

            String svcCode = userBase.getUnitSvcCd();
            long mbrSeq = userBase.getMbrSeq();

            int count = userDao.getAgreeRequiredTermsCount(svcCode, mbrSeq);

            log.debug("## checkTermAgreeRequired count : " + count);
            return count > 0;

        } catch (Exception e) {
            log.warn(e.getMessage(), e);

            return false;
        }
    }

    /**
     * 법정대리인 동의여부 조회 메쏘드
     *
     * @param sdpUser  SDP 연동 User 객체
     * @param userBase 기본 User 정보 객체
     * @return 법정대리인 동의여부
     */
    private boolean checkParentsAgreeRequired(User sdpUser, User userBase) {

        try {

            String svcCode = userBase.getUnitSvcCd();
            long mbrSeq = userBase.getMbrSeq();

            int age = sdpUser.getAge();
            int agreeCount = userDao.getParentsAgreeCount(svcCode, mbrSeq);

            log.debug("## checkParentsAgreeRequired : age[{}], agreeCount[{}]", age, agreeCount);

            return (age < 14 && agreeCount == 0);

        } catch (Exception e) {
            log.warn(e.getMessage(), e);

            return false;
        }
    }

    /**
     * 로그인 메쏘드
     *
     * @param initAuthToken 초기 인증 토큰
     * @param userBase      기본 User 정보 객체
     * @return 로그인 인증 토큰
     */
    private AuthToken login(AuthToken initAuthToken, User userBase) {

        String dstrCd = env.getDstrCd();
        String svcThemeCd = env.getSvcThemeCd();

        String unitSvcCd = initAuthToken.getSvcCode();
        String deviceId = initAuthToken.getDeviceId();
        String pnsRegId = initAuthToken.getPnsRegId();

        long mbrSeq = userBase.getMbrSeq();
        String mbrId = userBase.getMbrId();

        // 1. 온라인 사용자 조회
        int loginCount = getOnlineAppUserCount(unitSvcCd, mbrSeq, deviceId);

        log.debug("## loginCount for {}({}) : {}", mbrId, mbrSeq, loginCount);

        // 2. 디바이스 정보 조회
        User userApp = userDao.selectAppUser(dstrCd, svcThemeCd, unitSvcCd, mbrSeq, deviceId);
        String telNo = userApp == null ? "" : userApp.getTelNo();
        AuthToken loginAuthToken = AuthToken.encodeAuthToken(deviceId, telNo, pnsRegId, mbrSeq, mbrId, unitSvcCd);

        // 로그인 처리
        if (loginCount < LOGIN_MAX_COUNT) {
            User appUser = new User(dstrCd, svcThemeCd, unitSvcCd, mbrSeq, mbrId);
            appUser.setTermlId(deviceId);
            appUser.setTelNo(telNo);
            appUser.setPnsRegId(pnsRegId);
            appUser.setAuthToken(loginAuthToken.getToken());

            // 앱 단말 아이디에 연결된 회원 일련번호 조회
            User[] userListByTermlId = getSvcTgtConnBasByTermlId(unitSvcCd, deviceId);
            User[] userListByPnsRegId = getSvcTgtConnBasByPnsRegId(unitSvcCd, pnsRegId);
            User[] userList = ArrayUtils.addAll(userListByTermlId, userListByPnsRegId);

            boolean isExist = false;

            if (userList != null || userList.length > 0) {
                for (int i = 0; i < userList.length; i++) {
                    if (userList[i].getMbrSeq() == mbrSeq && userList[i].getTermlId().equals(deviceId)) {
                        // 기존에 연결된 회원 일련번호가 존재하고 로그인 요청 회원 일련번호와 동일하면 동일한 아이디, 앱 단말 아이디로 로그인 판단
                        // : 앱 접속자 정보 업데이트
                        log.debug("## AppUser existed : {},{},{},{}", mbrId, mbrSeq, deviceId, pnsRegId);

                        isExist = true;

                        // 로그인 업데이트가 필요한 경우에만 업데이트
                        long currentTimeMillis = System.currentTimeMillis();
                        if (loginUpdateRequired(deviceId, mbrSeq, currentTimeMillis)) {

                            log.debug("## login : loginUpdateRequired! {}, {}, {}", deviceId, mbrSeq, currentTimeMillis);

                            userDao.updateAppUser(appUser);
                            userDao.updateAppUserToken(appUser);
                            updateLoginTimeMap(deviceId, mbrSeq, currentTimeMillis);
                        }
                    } else {
                        // 그 이외에는 기존에 연결된 회원 일련번호가 존재하지만 다른 앱 단말로 로그인한 것으로 판단
                        // 앱 접속자 정보 초기화, 앱 접속자 정보는 가장 최근 정보만 유지함
                        log.debug("## AppUser deleted : {},{}->{},{}", mbrId, userList[i].getMbrSeq(), mbrSeq, userList[i].getTermlId());

                        userDao.deleteAppUserToken(dstrCd, svcThemeCd, unitSvcCd, userList[i].getMbrSeq(), userList[i].getTermlId());
                        userDao.deleteUserSetup(dstrCd, svcThemeCd, unitSvcCd, userList[i].getMbrSeq(), userList[i].getTermlId(), GROUP_SETUP_CD);
                        userDao.deleteAppUser(dstrCd, svcThemeCd, unitSvcCd, userList[i].getMbrSeq(), userList[i].getTermlId());
                    }
                }
            }

            if (!isExist) {
                // 기존에 연결된 회원 일련번호가 없다면 신규 로그인으로 판단
                // 회원 관련 테이블에 신규 등록
                log.debug("## AppUser is new : {},{},{},{}", mbrId, mbrSeq, deviceId, pnsRegId);
                userDao.insertAppUser(appUser);
                userDao.insertAppUserToken(appUser);
            }

            // Add a login history
            try {
                queueLoginHistory(appUser);
            } catch (Exception e) {
                log.info("Failed to log login history. : {}", appUser);
            }
            // 사용자 설정 체크 및 초기화
            checkUserSetup(loginAuthToken);

            return loginAuthToken;
        } else {

            log.debug("## Login is Not Allowed, LOGIN_MAX_COUNT is {}", LOGIN_MAX_COUNT);

            return null;
        }
    }

    /**
     * 로그인 이벤트에 대한 Push 발송 메쏘드
     *
     * @param initAuthToken 초기 인증 토큰
     * @param userBase      기본 User 정보 객체
     */
    private void sendLoginEventPush(AuthToken initAuthToken, User userBase) {

        if (StringUtils.isBlank(initAuthToken.getTelNo()))
            return; // 전화번호 없는 사용자는 pns 발송하지 않음. 약관 동의 후 전화번호 등록 시 pns 발송

        try {

            String dstrCd = env.getDstrCd();
            String svcThemeCd = env.getSvcThemeCd();
            String unitSvcCd = initAuthToken.getSvcCode();
            long mbrSeq = userBase.getMbrSeq();
            String termlId = initAuthToken.getDeviceId();
            String telNo = initAuthToken.getTelNo();
            String setupCd = env.getProperty("userSetupNm.loginPns");

            // 로그인 여부와 무관하게 Push를 보내려면 ExpireTime 을 조정한다. 3개월
            ArrayList<User> pnsUserList = userDao.getPnsTargetList(dstrCd, svcThemeCd, unitSvcCd, mbrSeq, 60 * 24 * 90, setupCd);

            if (CollectionUtils.isNotEmpty(pnsUserList)) {

                // 결정하지 못하는 경우 기본 -1 로 처리하기로 했음
                long svcTgtSeq = -1;
                ArrayList<Long> svcTgtSeqList = deviceDao.getMemberSvcTgtSeq(unitSvcCd, mbrSeq);
                int svcTgtSeqCount = CollectionUtils.size(svcTgtSeqList);

                // svcTgtSeq 매핑이 없으면 PNS 보내지 않는 것으로 했음
                if (svcTgtSeqCount == 0) {
                    throw new Exception(String.format("## svcTgtSeqCount is 0. %s", termlId));
                }

                // 정확하게 한개로 결정되어야 함, 그렇지 않으면 처리할 수 없음.
                if (svcTgtSeqCount == 1) {
                    svcTgtSeq = svcTgtSeqList.get(0);
                }

                Map<String, Object> items = new ConcurrentHashMap<>();
                for (User pnsUser : pnsUserList) {
                    if (!termlId.equals(pnsUser.getTermlId())
                            && !StringUtils.isEmpty(pnsUser.getTelNo())
                            && !StringUtils.isEmpty(pnsUser.getPnsRegId())) { // 로그인 본인 단말은 제외
                        items.clear();
                        items.put("mbrSeq", mbrSeq);
                        items.put("loginTelNo", telNo);
                        items.put("telNo", pnsUser.getTelNo());

                        eventService.sendComplexEventPush(unitSvcCd, setupCd, pnsUser.getPnsRegId(), svcTgtSeq, items);
                    }
                }
            }

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * 디바이스 접속 종료 이벤트에 대한 Push 발송 메쏘드
     *
     * @param unitSvcCd  단위 서비스 코드
     * @param svcTgtSeq  서비스대상 기본 일련번호
     * @param spotDevSeq 현장장치 일련번호
     * @param devUUID    디바이스 UUID
     * @param devNm      디바이스 이름
     */
    @Async
    public void sendDeviceCloseEventPush(String unitSvcCd, long svcTgtSeq, long spotDevSeq, String devUUID, String devNm) {

        try {

            String dstrCd = env.getDstrCd();
            String svcThemeCd = env.getSvcThemeCd();
            String setupCd = env.getProperty("userSetupNm.cctvPns");

            // 로그인 여부와 무관하게 Push를 보내려면 ExpireTime 을 조정한다. 1년
            Long mbrSeq = userDao.getMbrSeqBySvcTgtSeq(svcTgtSeq);
            if (mbrSeq == null) {
                log.info("## sendDeviceCloseEventPush : mbrSeq is NULL!");
            } else {

                ArrayList<User> pnsUserList = userDao.getPnsTargetList(dstrCd, svcThemeCd, unitSvcCd, mbrSeq, 60 * 24 * 365, setupCd);

                log.info("## sendDeviceCloseEventPush count : {}", CollectionUtils.size(pnsUserList));

                Map<String, Object> items = new ConcurrentHashMap<>();
                if (CollectionUtils.isNotEmpty(pnsUserList)) {
                    for (User pnsUser : pnsUserList) {
                        if (!StringUtils.isEmpty(pnsUser.getTelNo()) && !StringUtils.isEmpty(pnsUser.getPnsRegId())) {
                            items.clear();
                            items.put("mbrSeq", mbrSeq);
                            items.put("devUUId", devUUID);
                            items.put("devNm", devNm);
                            items.put("telNo", pnsUser.getTelNo());

                            eventService.sendComplexEventPush(unitSvcCd, setupCd, pnsUser.getPnsRegId(), svcTgtSeq, items);
                        }
                    }
                }
            }

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * 동일한 mbrSeq 에 연결된 앱 사용자 리스트를 조회 메쏘드. 내부적으로 getAppUserList 호출
     *
     * @param authToken 로그인 인증 토큰
     * @throws Exception
     * @return 동일한 mbrSeq에 연결된 앱 사용자 리스트
     */
    public ArrayList<User> getAppUserList(String authToken) throws Exception {

        AuthToken loginAuthToken = checkLoginAuthToken(authToken);
        String unitSvcCd = loginAuthToken.getSvcCode();
        long mbrSeq = NumberUtils.toLong(loginAuthToken.getUserNo());

        return getAppUserList(unitSvcCd, mbrSeq, null);
    }

    /**
     * 동일한 mbrSeq 에 연결된 앱 사용자 리스트를 조회 메쏘드. termlId 가 null 이면 동일한 mbrSeq 에 연결된 모든  사용자 리스트를 조회함
     *
     * @param unitSvcCd 단위 서비스 코드
     * @param mbrSeq    멤버 일련번호
     * @param termlId   단말 아이디. 옵션.
     * @throws Exception
     * @return 앱 사용자 리스트
     */
    private ArrayList<User> getAppUserList(String unitSvcCd, long mbrSeq, String termlId) throws Exception {

        String dstrCd = env.getDstrCd();
        String svcThemeCd = env.getSvcThemeCd();

        ArrayList<User> list = userDao.getAppUserList(dstrCd, svcThemeCd, unitSvcCd, mbrSeq, termlId, LOGIN_EXPIRE_TIME_MIN);

        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (User user : list) {
            sb.append(user.getTermlId())
                    .append(", ")
                    .append(user.getTelNo())
                    .append(", ")
                    .append(user.getStatus())
                    .append("\n");
        }
        sb.append("]\n");

        log.info(sb.toString());

        return list;
    }

    /**
     * 동시 접속 앱 사용자 수 조회 메쏘드
     *
     * @param unitSvcCd 단위 서비스 코드
     * @param mbrSeq    회원 일련번호
     * @return 동시 접속 앱 사용자 수
     */
    private int getOnlineAppUserCount(String unitSvcCd, long mbrSeq, String deviceId) {

        int count = 0;
        try {
            List<User> appUserList = getAppUserList(unitSvcCd, mbrSeq, null);

            if (CollectionUtils.isNotEmpty(appUserList)) {
                for (User user : appUserList) {
                    if (StringUtils.equals(user.getStatus(), "1") && !deviceId.equals(user.getTermlId())) {
                        count++;
                    }
                }
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }

        return count;
    }

    /**
     * 앱 단말 아이디에 연결된 회원 일련번호 조회 메쏘드
     *
     * @param unitSvcCd 단위 서비스 코드
     * @param termlId   앱 단말 아이디
     * @return 앱 단말 아이디에 연결된 회원 일련번호
     */
    private User[] getSvcTgtConnBasByTermlId(String unitSvcCd, String termlId) {

        String dstrCd = env.getDstrCd();
        String svcThemeCd = env.getSvcThemeCd();
        User[] userList = null;

        try {
            userList = userDao.getSvcTgtConnBasByTermlId(dstrCd, svcThemeCd, unitSvcCd, termlId);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }

        return userList;
    }

    /**
     * 앱 단말 아이디에 연결된 회원 일련번호 조회 메쏘드
     *
     * @param unitSvcCd 단위 서비스 코드
     * @param pnsRegId
     * @return 앱 단말 아이디에 연결된 회원 일련번호
     */
    private User[] getSvcTgtConnBasByPnsRegId(String unitSvcCd, String pnsRegId) {

        String dstrCd = env.getDstrCd();
        String svcThemeCd = env.getSvcThemeCd();
        User[] userList = null;

        try {
            if (StringUtils.isNotBlank(pnsRegId)) {
                userList = userDao.getSvcTgtConnBasByPnsRegId(dstrCd, svcThemeCd, unitSvcCd, pnsRegId);
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }

        return userList;
    }

    /**
     * 사용자 설정 조회 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @throws Exception
     * @return 사용자 설정 맵
     */
    public HashMap<String, String> getUserSetup(String authToken) throws Exception {

        AuthToken loginAuthToken = checkLoginAuthToken(authToken);
        HashMap<String, String> resultMap = new HashMap<>();

        ArrayList<HashMap<String, String>> list = checkUserSetup(loginAuthToken);
        for (HashMap<String, String> m : list) {
            resultMap.put(m.get("setupCd"), m.get("setupVal"));
        }

        // 키(이름)/값 구조의 사용자 설정의 코드/값 구조로 변경한다.
        return toAppUserSetup(resultMap);
    }

    /**
     * 사용자 설정 세팅 메쏘드
     *
     * @param authToken    로그인 인증 토큰
     * @param appUserSetup 앱 사용자 설정 맵
     * @throws Exception
     */
    public void setUserSetup(String authToken, HashMap<String, String> appUserSetup) throws Exception {

        AuthToken loginAuthToken = checkLoginAuthToken(authToken);

        if (appUserSetup == null || appUserSetup.isEmpty()) {
            return;
        }

        String dstrCd = env.getDstrCd();
        String svcThemeCd = env.getSvcThemeCd();

        String unitSvcCd = loginAuthToken.getSvcCode();
        long mbrSeq = loginAuthToken.getUserNoLong();
        String connTermlId = loginAuthToken.getDeviceId();

        HashMap<String, String> userSetup = fromAppUserSetup(appUserSetup);

        Iterator<String> it = userSetup.keySet().iterator();
        while (it.hasNext()) {
            String setupCd = it.next();
            String setupVal = userSetup.get(setupCd);
            int result = userDao.updateUserSetup(dstrCd, svcThemeCd, unitSvcCd, mbrSeq, connTermlId, GROUP_SETUP_CD, setupCd, setupVal);
            if (result < 1) {
                userDao.insertUserSetup(dstrCd, svcThemeCd, unitSvcCd, mbrSeq, connTermlId, GROUP_SETUP_CD, setupCd, setupVal);
            }
        }
    }

    /**
     * 사용자 설정 매핑 메쏘드. 키(이름)/값 구조의 사용자 설정을 코드/값 구조의 설정으로 변경한다.
     * 키/값 구조의 사용자 설정으로 개발 완료된 상태에서
     * 플랫폼 변경사항 수용하기 위해 중간 매핑 메쏘드가 필요함
     *
     * @param userSetup 키(이름)/값 구조의 사용자 설정 맵
     * @return 코드/값 구조의 사용자 설정 맵
     */
    private HashMap<String, String> toAppUserSetup(HashMap<String, String> userSetup) {

        if (MapUtils.isEmpty(userSetup)) {
            return userSetup;
        }

        HashMap<String, String> appUserSetup = new HashMap<String, String>();

        Iterator<String> it = userSetup.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String val = userSetup.get(key);

            String newKey = env.getProperty("userSetupCd." + key);
            String newVal = StringUtils.equalsIgnoreCase(StringUtils.upperCase(val), "Y") ? "1" : "0";

            if (StringUtils.isNotBlank(newKey)) {
                appUserSetup.put(newKey, newVal);
            }
        }

        return appUserSetup;
    }

    /**
     * 사용자 설정 매핑 메쏘드. 코드/값 구조의 사용자 설정을 키(이름)/값 구조의 설정으로 변경한다.
     * 키/값 구조의 사용자 설정으로 개발 완료된 상태에서
     * 플랫폼 변경사항 수용하기 위해 중간 매핑 메쏘드가 필요함
     *
     * @param appUserSetup 코드/값 구조의 사용자 설정 맵
     * @return 키(이름)/값 구조의 사용자 설정 맵
     */
    private HashMap<String, String> fromAppUserSetup(HashMap<String, String> appUserSetup) {

        if (MapUtils.isEmpty(appUserSetup)) {
            return appUserSetup;
        }

        HashMap<String, String> userSetup = new HashMap<String, String>();

        Iterator<String> it = appUserSetup.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String val = appUserSetup.get(key);

            String newKey = env.getProperty("userSetupNm." + key);
            String newVal = StringUtils.equalsIgnoreCase(val, "1") ? "Y" : "N";

            if (StringUtils.isNotBlank(newKey)) {
                userSetup.put(newKey, newVal);
            }
        }

        return userSetup;
    }

    /**
     * 사용자 설정 체크 및 초기화 메쏘드
     *
     * @param loginAuthToken 로그인 인증 토큰
     * @return 사용자 설정 맵 리스트
     */
    private ArrayList<HashMap<String, String>> checkUserSetup(AuthToken loginAuthToken) {

        String dstrCd = env.getDstrCd();
        String svcThemeCd = env.getSvcThemeCd();
        String unitSvcCd = loginAuthToken.getSvcCode();

        long mbrSeq = loginAuthToken.getUserNoLong();

        String connTermlId = loginAuthToken.getDeviceId();

        ArrayList<HashMap<String, String>> list = userDao.getUserSetup(dstrCd, svcThemeCd, unitSvcCd, mbrSeq, connTermlId, GROUP_SETUP_CD);

        if (CollectionUtils.isEmpty(list)) {
            log.debug("## initUserSetup : {}, {}", mbrSeq, connTermlId);
            list = initUserSetup(loginAuthToken);
        }

        return list;
    }

    /**
     * 사용자 설정 초기화 메쏘드
     *
     * @param loginAuthToken 로그인 인증 토큰
     * @return 초기화 된 사용자 설정 맵 리스트
     */
    private ArrayList<HashMap<String, String>> initUserSetup(AuthToken loginAuthToken) {

        String dstrCd = env.getDstrCd();
        String svcThemeCd = env.getSvcThemeCd();
        String unitSvcCd = loginAuthToken.getSvcCode();
        long mbrSeq = loginAuthToken.getUserNoLong();
        String connTermlId = loginAuthToken.getDeviceId();

        userDao.deleteUserSetup(dstrCd, svcThemeCd, unitSvcCd, mbrSeq, connTermlId, GROUP_SETUP_CD);

        ArrayList<HashMap<String, String>> setupList = new ArrayList<HashMap<String, String>>();
        setupList.add(insertUserSetupValue(loginAuthToken, "0001", "Y"));    // 침입감지 이벤트
        setupList.add(insertUserSetupValue(loginAuthToken, "0002", "Y"));    // UCLOUD 저장 공간 부족 이벤트
        setupList.add(insertUserSetupValue(loginAuthToken, "0003", "Y"));    // UCLOUD 업로드 오류 이벤트
        setupList.add(insertUserSetupValue(loginAuthToken, "0004", "Y"));    // SD카드 장애 이벤트
        setupList.add(insertUserSetupValue(loginAuthToken, "0005", "Y"));    // 로그인 알림 이벤트
        setupList.add(insertUserSetupValue(loginAuthToken, "0008", "Y"));    // 카메라 연결 오류
        setupList.add(insertUserSetupValue(loginAuthToken, "0009", "Y"));    // SD카드 저장 공간 부족 이벤트

        return setupList;
    }

    /**
     * 사용자 설정 단일 삽입 메쏘드
     *
     * @param loginAuthToken 로그인 인증 토큰
     * @param setupCd        사용자 설정 코드
     * @param setupVal       사용자 설정 값
     * @return
     */
    private HashMap<String, String> insertUserSetupValue(AuthToken loginAuthToken, String setupCd, String setupVal) {

        String dstrCd = env.getDstrCd();
        String svcThemeCd = env.getSvcThemeCd();
        String unitSvcCd = loginAuthToken.getSvcCode();
        long mbrSeq = loginAuthToken.getUserNoLong();
        String connTermlId = loginAuthToken.getDeviceId();

        userDao.insertUserSetup(dstrCd, svcThemeCd, unitSvcCd, mbrSeq, connTermlId, GROUP_SETUP_CD, setupCd, setupVal);
        HashMap<String, String> setup = new HashMap<String, String>();
        setup.put("setupCd", setupCd);
        setup.put("setupVal", setupVal);

        return setup;
    }

    /**
     * 디바이스(카메라) 이름 변경 메쏘드
     * 디비에서 이름을 변경하고 EC 쪽으로 변경 요청을 보낸다.
     * 디바이스의 ucloudPath 는 변경하지 않는다.
     *
     * @param devUUID 디바이스 UUID
     * @param devNm   디바이스 이름
     * @throws Exception
     */
    public void setDeviceName(String devUUID, String devNm) {

        try {

            // 1. 디비 업데이트
            deviceDao.updateDeviceName(devUUID, devNm);

            // 2. EC 업데이트
            HashMap<String, Object> deviceKey = deviceDao.getSpotDevKey(devUUID);
            long svcTgtSeq = (long) deviceKey.get("svcTgtSeq");
            long spotDevSeq = (long) deviceKey.get("spotDevSeq");
            String spotDevId = (String) deviceKey.get("spotDevId");
            ecService.setDevNm(svcTgtSeq, spotDevSeq, spotDevId, devNm);

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    public void initializeMappedDevices(long mbrSeq) {

        userDao.initializeMappedDevices(mbrSeq);

    }

    public void queueLoginHistory(User user) throws InterruptedException {

        loginSuccessQueue.put(user);

    }

    public List<LoginHistory> getLoginHistory(String authToken, int count) throws Exception {
        final AuthToken loginAuthToken = checkLoginAuthToken(authToken);

        return userDao.getLoginHistory(loginAuthToken.getUserNoLong(), count);
    }
}
