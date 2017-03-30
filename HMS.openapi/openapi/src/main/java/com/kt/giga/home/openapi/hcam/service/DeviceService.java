package com.kt.giga.home.openapi.hcam.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.common.AuthToken;
import com.kt.giga.home.openapi.hcam.HCamConstants.*;
import com.kt.giga.home.openapi.hcam.dao.DeviceDao;
import com.kt.giga.home.openapi.hcam.dao.UserDao;
import com.kt.giga.home.openapi.hcam.domain.ControlTransaction;
import com.kt.giga.home.openapi.hcam.domain.EnrollmentStatus;
import com.kt.giga.home.openapi.hcam.domain.ScheduleData;
import com.kt.giga.home.openapi.hcam.domain.User;
import com.kt.giga.home.openapi.hcam.internal.GenlSetupDataMapCallback;
import com.kt.giga.home.openapi.hcam.internal.MasterRetvCallback;
import com.kt.giga.home.openapi.hcam.internal.RetvCallable;
import com.kt.giga.home.openapi.hcam.internal.RetvCallback;
import com.kt.giga.home.openapi.service.APIException;
import com.kt.giga.home.openapi.service.ECService;
import com.kt.giga.home.openapi.util.JsonUtils;
import com.kt.giga.home.openapi.vo.cnvy.CnvyRow;
import com.kt.giga.home.openapi.vo.cnvy.ItgCnvyData;
import com.kt.giga.home.openapi.vo.cnvy.SpotDevCnvyData;
import com.kt.giga.home.openapi.vo.row.*;
import com.kt.giga.home.openapi.vo.spotdev.*;
import com.kt.giga.home.openapi.vo.spotdev.query.CommnAgntBasVO;
import com.kt.giga.home.openapi.vo.spotdev.query.SpotDevBasQueryVO;
import com.kt.giga.home.openapi.vo.spotdev.query.SpotDevBasVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * 디바이스 서비스
 *
 * @author
 */
@Service("HCam.DeviceService")
public class DeviceService {

    private static List<String> integratedDataItemNms = new ArrayList<>();

    static {
        integratedDataItemNms.add(SpotDevItemNm.UPGRADE_STATUS);
        integratedDataItemNms.add(SpotDevItemNm.LAST_DETECTION_TIME);
    }

    /**
     * 로거
     */
    private final Logger log = LoggerFactory.getLogger(getClass());
    /**
     * 장치의 접속 종료 최종 판단을 위한 만료시간. 그 시간 안에 재접속 없으면 최종 접속 종료 판단
     */
    private final int CONN_EXPIRE_TIME_MIN = 4;                // 5분안에 재접속이 없으면 Push, EC 감지시간 70 초 감안하여 4분 설정
    /**
     * OpenAPI 환경 프라퍼티
     */
    @Autowired
    private APIEnv env;
    /**
     * 디바이스 매퍼 인터페이스
     */
    @Autowired
    private DeviceDao deviceDao;
    /**
     * 사용자 매퍼 인터페이스
     */
    @Autowired
    private UserDao userDao;
    /**
     * 앱 초기 실행 서비스
     */
    @Autowired
    private InitService initService;
    /**
     * 사용자 서비스
     */
    @Autowired
    private UserService userService;
    /**
     * 릴레이 서버 연동 서비스
     */
    @Autowired
    private RelayService relayService;
    /**
     * EC 연동 서비스
     */
    @Autowired
    private ECService ecService;

    @Autowired
    private IFirmwareService firmwareService;
    /**
     * KT 인프라 연동 서비스
     */
    @Autowired
    private KTInfraService ktService;
    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    private ReportChannelService resltService;
    /**
     * 테이블 저장을 위한 여러 제어 값에 대한 구분자
     */
    private String controlValueDelimiter = ";";
    private SimpleDateFormat upgradeStartTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Get a SpotDevRetvReslt from database.
     *
     * @param authToken A token for authentication.
     * @param devUUID   A device UUID.
     * @return SpotDevRetvReslt {@link com.kt.giga.home.openapi.vo.spotdev.SpotDevRetvReslt}
     * @throws Exception the exception
     */
    public SpotDevRetvReslt getMasterConfigOnlyDB(String authToken, String devUUID) throws Exception {
        AuthToken loginAuthToken = userService.checkLoginAuthToken(authToken);
        String svcCode = loginAuthToken.getSvcCode();
        long mbrSeq = loginAuthToken.getUserNoLong();

        // 1. 회원에 연결된 디바이스 리스트 조회
        List<SpotDev> spotDevList = deviceDao.getMemberSpotDev(svcCode, mbrSeq, devUUID, null, null);

        return getSpotDevRetvResltFromDB(svcCode, spotDevList);

    }

    /**
     * 디바이스 마스터 정보 조회 메쏘드
     *
     * @param svcCode  서비스 코드
     * @param spotDevs 조회할 디바이스 리스트
     * @throws Exception
     * @return 디바이스 마스터 정보. SpotDevRetvReslt 객체
     */
    private SpotDevRetvReslt getSpotDevRetvResltFromDB(String svcCode, List<SpotDev> spotDevs) {
        SpotDevRetvReslt spotDevRetvReslt = new SpotDevRetvReslt();

        for (SpotDev spotDev : spotDevs) {

            long svcTgtSeq = spotDev.getSvcTgtSeq();
            long spotDevSeq = spotDev.getSpotDevSeq();

            // 상세정보 설정
            spotDev = getDeviceDetail(spotDev, svcTgtSeq, spotDevSeq);

            // 일반설정
            spotDev = getDeviceGeneralSetup(spotDev, svcTgtSeq, spotDevSeq);

            // 스케쥴 설정
            spotDev = getDeviceScheduleSetup(spotDev, svcTgtSeq, spotDevSeq);

            // 서비스 종속 상세정보 설정
            spotDev = getDeviceSvcDetail(svcCode, spotDev, svcTgtSeq, spotDevSeq);
        }

        // 3. 내부 키는 제거
        if (CollectionUtils.isNotEmpty(spotDevs)) {
            for (SpotDev spotDev : spotDevs) {
                spotDev.setSvcTgtSeq(null);
                spotDev.setSpotDevSeq(null);
            }
        }

        spotDevRetvReslt.setSpotDevs(spotDevs);

        return spotDevRetvReslt;
    }


    public Callable<SpotDevRetvReslt> getMasterConfig(String authToken, String devUUID) throws Exception {
        return getMasterConfig(authToken, devUUID, 3000L);
    }
    /**
     * Gets master config.
     *
     * @param authToken the auth token
     * @param devUUID   the dev uUID
     * @param timeout   milliseconds to timeout
     * @return the master config
     * @throws Exception the exception
     */
    public Callable<SpotDevRetvReslt> getMasterConfig(String authToken, String devUUID, long timeout) throws Exception {
        AuthToken loginAuthToken = userService.checkLoginAuthToken(authToken);
        String svcCode = loginAuthToken.getSvcCode();
        long mbrSeq = loginAuthToken.getUserNoLong();

        // 1. 회원에 연결된 디바이스 리스트 조회
        List<SpotDev> spotDevList = deviceDao.getMemberSpotDev(svcCode, mbrSeq, devUUID, null, null);

        if (spotDevList.isEmpty() || spotDevList.size() == 0) {
            return new Callable<SpotDevRetvReslt>() {

                @Override
                public SpotDevRetvReslt call() throws Exception {
                    return new SpotDevRetvReslt();
                }
            };
        }

        RetvCallable<SpotDevRetvReslt> callable = new RetvCallable<>();
        callable.setTimeout(timeout);

        MasterRetvCallback masterCallback = new MasterRetvCallback();
        masterCallback.setInitService(initService);
        masterCallback.setSvcCode(svcCode);
        masterCallback.setAPIEnv(env);
        masterCallback.setDeviceService(this);
        masterCallback.setIFirmwareService(firmwareService);

        retvDeviceConfig(svcCode, spotDevList, true, callable, masterCallback);

        return callable;
    }

    /**
     * Gets genl setup config.
     *
     * @param authToken the auth token
     * @param devUUID   the dev uUID
     * @return the genl setup config
     * @throws Exception the exception
     */
    public Callable<Map<String, String>> getGenlSetupConfig(String authToken, String devUUID) throws Exception {

        AuthToken loginAuthToken = userService.checkLoginAuthToken(authToken);
        String svcCode = loginAuthToken.getSvcCode();
        long mbrSeq = loginAuthToken.getUserNoLong();

        // 1. 회원에 연결된 디바이스 리스트 조회
        List<SpotDev> spotDevList = deviceDao.getMemberSpotDev(svcCode, mbrSeq, devUUID, null, null);

        RetvCallable<Map<String, String>> callable = new RetvCallable<>();
        callable.setTimeout(3000L);

        GenlSetupDataMapCallback callback = new GenlSetupDataMapCallback();
        callback.setAPIEnv(env);
        callback.setDeviceService(this);
        callback.setRealtimeRequired(true);

        log.debug("### Retrieving Camera Information");

        retvDeviceConfig(svcCode, spotDevList, false, callable, callback);

        return callable;
    }

    /**
     * Gets genl setup config.
     *
     * @param authToken the auth token
     * @param spotDevs  SpotDevs to retrieve.
     * @return the genl setup config
     * @throws Exception the exception
     */
    public Callable<Map<String, String>> getGenlSetupConfig(String authToken, List<SpotDev> spotDevs) throws Exception {

        AuthToken loginAuthToken = userService.checkLoginAuthToken(authToken);
        String svcCode = loginAuthToken.getSvcCode();
        long mbrSeq = loginAuthToken.getUserNoLong();

        RetvCallable<Map<String, String>> callable = new RetvCallable<>();
        callable.setTimeout(3000L);

        GenlSetupDataMapCallback callback = new GenlSetupDataMapCallback();
        callback.setAPIEnv(env);
        callback.setDeviceService(this);
        callback.setRealtimeRequired(true);

        log.debug("### Retrieving Camera Information");

        retvDeviceConfig(svcCode, spotDevs, false, callable, callback);

        return callable;
    }

    /**
     * 디바이스로 부터 설정 정보를 직접 조회하는 메쏘드 (비동기 요청)
     * 조회 요청은 비동기 요청이기 때문에 주기적으로 결과 Report 도착을 반복 확인해야 함
     *
     * @param svcCode         the svc code
     * @param spotDevList     the spot dev list
     * @param occrId          the occr id
     * @param retryCount      the retry count
     * @param sleepTimeMillis the sleep time millis
     * @return the spot dev retv reslt [ ]
     */
    public SpotDevRetvReslt[] getDeviceSetupDirectAsync(final String svcCode, final List<SpotDev> spotDevList, final String occrId, final int retryCount, final int sleepTimeMillis) {

        final String[] snsnTagCds = {SnsnTagCd.RETV_SCH_SETUP, SnsnTagCd.RETV_GEN_SETUP};

        log.debug("Start retrieving information of {} devices", spotDevList);
        long startTime = System.nanoTime();
        List<SpotDevRetvReslt> deviceSetupDirectAsync = getDeviceSetupRetv(svcCode, spotDevList, occrId, snsnTagCds, retryCount, sleepTimeMillis);
        long endTime = System.nanoTime();
        log.debug("End retrieving information of {} devices", spotDevList);
        long diff = endTime - startTime;
        log.debug("Elapsed time to get information of {} devices : {} milliseconds", spotDevList.size(), diff / 1000000);

        if (deviceSetupDirectAsync == null || deviceSetupDirectAsync.isEmpty())
            return null;
        else
            return deviceSetupDirectAsync.toArray(new SpotDevRetvReslt[deviceSetupDirectAsync.size()]);
    }


    /**
     * 디바이스로 부터 설정 정보를 직접 조회하는 메쏘드
     * 조회 요청은 비동기 요청이기 때문에 주기적으로 결과 Report 도착을 반복 확인해야 함
     *
     * @param svcCode         서비스 코드
     * @param spotDevList     설정 정보 조회 대상 SpotDev
     * @param occrId          조회 제어 발생자 아이디
     * @param snsnTagCds      센싱 태그 코드 배열
     * @param retryCount      조회 결과 Report 도착 확인 재시도 수
     * @param sleepTimeMillis 조회 결과 Report 도착 확인 간격 sleep time
     * @return 디바이스 마스터 정보. SpotDevRetvReslt 객체
     */
    private List<SpotDevRetvReslt> getDeviceSetupRetv(final String svcCode, List<SpotDev> spotDevList, final String occrId, final String[] snsnTagCds, final int retryCount, final int sleepTimeMillis) {

        // 예외처리
        if (CollectionUtils.isEmpty(spotDevList)) {
            return null;
        }

        CompletionService<SpotDevRetvReslt> taskCompletionService = new ExecutorCompletionService<SpotDevRetvReslt>(executor);

        for (final SpotDev spotDev : spotDevList) {
            for (final String snsnTagCd : snsnTagCds) {
                taskCompletionService.submit(new Callable<SpotDevRetvReslt>() {
                    @Override
                    public SpotDevRetvReslt call() throws Exception {
                        long startTime = System.nanoTime();
                        long endTime;
                        long diff;

                        log.debug("Start retrieving information of {}, with snsnTagCd : {}", spotDev.getSpotDevId(), snsnTagCd);
                        String transacId = setDeviceSetupReadControl(svcCode, spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq(), occrId, snsnTagCd);
                        SpotDevRetvReslt spotDevRetvReslt = null;

                        try {
                            Thread.sleep(sleepTimeMillis / 4);
                        } catch (InterruptedException e) {
                            log.warn(e.getMessage(), e);
                        }

                        HashMap<String, String> transactionResult = null;
                        for (int i = 0; i < retryCount; i++) {
                            transactionResult = deviceDao.getTransactionResult(transacId, null);
                            String jsonResult = MapUtils.getString(transactionResult, "result");
                            if (StringUtils.isNotEmpty(jsonResult)) {
                                spotDevRetvReslt = JsonUtils.fromJson(jsonResult, SpotDevRetvReslt.class);
                                log.debug("End retrieving information of {}, with snsnTagCd : {}", spotDev.getSpotDevId(), snsnTagCd);
                                endTime = System.nanoTime();
                                diff = endTime - startTime;
                                log.debug("Elapsed time to retrieve information of : {} milliseconds", diff / 1000000);
                                break;
                            }

                            if (retryCount > 5) {
                                Thread.sleep(sleepTimeMillis);
                            } else {
                                Thread.sleep(sleepTimeMillis / 2);
                            }
                        }

                        // 연결 상태 업데이트
                        SpotDevUdate updateData = new SpotDevUdate();

                        if (spotDevRetvReslt == null) { // 제한시간내에 장치 정보에 대한 리포팅이 없는 경우 연결오류로 업데이트
                            spotDev.setSessnId(null);
                            updateData.addSpotDev(spotDev);
                        } else { // 장치 정보의 세션 상태에 따른 연결 상태 업데이트
                            updateData.addSpotDev(spotDevRetvReslt.getFirstSpotDev());
                        }

                        setDeviceOnlineStatus(updateData);

                        endTime = System.nanoTime();
                        diff = endTime - startTime;
                        log.debug("Elapsed time to get an device setup information : {} milliseconds", diff / 1000000);
                        return spotDevRetvReslt;
                    }
                });
            }
        }

        List<SpotDevRetvReslt> resultList = new ArrayList<SpotDevRetvReslt>();
        for (int i = 0; i < (spotDevList.size() * snsnTagCds.length); i++) {
            try {
                SpotDevRetvReslt spotDevRetvReslt = taskCompletionService.take().get();
                if (spotDevRetvReslt != null)
                    resultList.add(spotDevRetvReslt);
            } catch (InterruptedException | ExecutionException e) {
            }
        }

        return resultList;
    }

    /**
     * 디바이스 설정 조회 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param devUUID   디바이스 UUID
     * @return 디바이스 마스터 정보. SpotDevRetvReslt 객체
     * @throws Exception the exception
     */
    public SpotDevRetvReslt getDeviceSetup(String authToken, String devUUID) throws Exception {

        userService.checkLoginAuthToken(authToken);
        HashMap<String, Object> keyMap = deviceDao.getSpotDevKey(devUUID);

        SpotDev spotDev = new SpotDev();

        if (MapUtils.isNotEmpty(keyMap)) {

            long svcTgtSeq = (long) keyMap.get("svcTgtSeq");
            long spotDevSeq = (long) keyMap.get("spotDevSeq");

            // 일반 설정
            spotDev = getDeviceGeneralSetup(spotDev, svcTgtSeq, spotDevSeq);

            // 스케쥴 설정
            spotDev = getDeviceScheduleSetup(spotDev, svcTgtSeq, spotDevSeq);
        }

        return new SpotDevRetvReslt(spotDev);
    }

    /**
     * 디바이스 설정 조회 메쏘드
     *
     * @param devUUID 디바이스 UUID
     * @return 디바이스 마스터 정보. SpotDevRetvReslt 객체
     * @throws Exception
     */
    public SpotDev getDeviceSetup(String devUUID) {

        HashMap<String, Object> keyMap = deviceDao.getSpotDevKey(devUUID);

        SpotDev spotDev = new SpotDev();

        if (MapUtils.isNotEmpty(keyMap)) {
            long svcTgtSeq = (long) keyMap.get("svcTgtSeq");
            long spotDevSeq = (long) keyMap.get("spotDevSeq");

            spotDev = getDeviceSetup(svcTgtSeq, spotDevSeq);
        }

        return spotDev;
    }

    /**
     * Gets device setup.
     *
     * @param svcTgtSeq  the svc tgt seq
     * @param spotDevSeq the spot dev seq
     * @return the device setup
     */
    public SpotDev getDeviceSetup(long svcTgtSeq, long spotDevSeq) {

        SpotDev spotDev = new SpotDev();

        // 일반 설정
        spotDev = getDeviceGeneralSetup(spotDev, svcTgtSeq, spotDevSeq);

        // 스케쥴 설정
        spotDev = getDeviceScheduleSetup(spotDev, svcTgtSeq, spotDevSeq);

        return spotDev;
    }

    /**
     * 디바이스 정보 단일 조회 메쏘드. 상세정보 포함
     *
     * @param unitSvcCd 단위 서비스 코드
     * @param devUUID   디바이스 UUID
     * @return 현장 장치 객체
     */
    private SpotDev getDevice(String unitSvcCd, String devUUID) {

        SpotDev spotDev = null;

        try {

            ArrayList<SpotDev> spotDevList = deviceDao.getSpotDev(unitSvcCd, devUUID, null, null);
            if (CollectionUtils.isNotEmpty(spotDevList)) {
                spotDev = spotDevList.get(0);
                spotDev = getDeviceDetail(spotDev, spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq());
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }

        return spotDev;
    }

    /**
     * 디바이스 정보 단일 조회 메쏘드. 상세정보 포함. 존재하지 않는 경우 Exception 처리
     *
     * @param unitSvcCd 단위 서비스 코드
     * @param devUUID   디바이스 UUID
     * @return 현장 장치 객체
     */
    private SpotDev checkDevice(String unitSvcCd, String devUUID) throws Exception {
        if (StringUtils.isEmpty(devUUID)) {
            throw new APIException(String.format("Device not found : %s", devUUID), HttpStatus.NOT_FOUND);
        }

        SpotDev spotDev = getDevice(unitSvcCd, devUUID);

        if (spotDev == null) {
            throw new APIException(String.format("Device not found : %s", devUUID), HttpStatus.NOT_FOUND);
        }

        return spotDev;
    }

    /**
     * 디바이스 정보 단일 조회 메쏘드. 상세정보 포함. 존재하지 않는 경우 Exception 처리
     *
     * @param devUUID 디바이스 UUID
     * @return 현장 장치 객체
     */
    private SpotDev checkDevice(String devUUID) throws Exception {
        if (StringUtils.isEmpty(devUUID)) {
            throw new APIException(String.format("Device not found : %s", devUUID), HttpStatus.NOT_FOUND);
        }

        SpotDev spotDev = getDevice(null, devUUID);

        if (spotDev == null) {
            throw new APIException(String.format("Device not found : %s", devUUID), HttpStatus.NOT_FOUND);
        }

        return spotDev;
    }

    /**
     * 디바이스 상세 정보 조회 메쏘드
     *
     * @param spotDev    현장 장치 객체
     * @param svcTgtSeq  서비스 대상 일련번호
     * @param spotDevSeq 현장 장치 일련번호
     * @return 현장 장치 객체
     */
    public SpotDev getDeviceDetail(SpotDev spotDev, long svcTgtSeq, long spotDevSeq) {

        if (spotDev == null) {
            spotDev = new SpotDev();
        }

        ArrayList<SpotDevDtl> spotDevDtlList = deviceDao.getSpotDevDtl(svcTgtSeq, spotDevSeq, null);
        spotDev.setSpotDevDtls(spotDevDtlList);

        return spotDev;
    }

    /**
     * 디바이스 상세 정보 단일 조회 메쏘드
     *
     * @param svcTgtSeq     서비스 대상 일련번호
     * @param spotDevSeq    현장 장치 일련번호
     * @param spotDevItemNm 항목 이름
     * @return 항목 값
     */
    private String getDeviceDetailValue(long svcTgtSeq, long spotDevSeq, String spotDevItemNm) {

        ArrayList<SpotDevDtl> spotDevDtlList = deviceDao.getSpotDevDtl(svcTgtSeq, spotDevSeq, spotDevItemNm);
        return CollectionUtils.isEmpty(spotDevDtlList) ? null : spotDevDtlList.get(0).getAtribVal();
    }

    /**
     * 디바이스 상세 정보를 저장하는 메쏘드
     * 기존에 존재하면 update, 존재하지 않으면 insert
     *
     * @param svcTgtSeq      서비스 대상 일련번호
     * @param spotDevSeq     현장 장치 일련번호
     * @param spotDevItemNm  항목 이름
     * @param spotDevItemVal 항목 값
     */
    private void setDeviceDetailValue(long svcTgtSeq, long spotDevSeq, String spotDevItemNm, String spotDevItemVal) {

        try {
            int result = deviceDao.updateSpotDevDtl(svcTgtSeq, spotDevSeq, spotDevItemNm, spotDevItemVal);

            if (result < 1) {
                deviceDao.insertSpotDevDtl(svcTgtSeq, spotDevSeq, spotDevItemNm, spotDevItemVal);
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * 장치 상세 정보를 +1 해서 저장하는 메쏘드
     *
     * @param svcTgtSeq     서비스 대상 일련번호
     * @param spotDevSeq    현장 장치 일련번호
     * @param spotDevItemNm 항목 이름
     * @return 항목 값. 최종 +1 된 값
     */
    private void increaseDetailValue(long svcTgtSeq, long spotDevSeq, String spotDevItemNm) {

        //TODO upsert로 바꾸기
        try {
            int result = deviceDao.updateSpotDevDtlInc(svcTgtSeq, spotDevSeq, spotDevItemNm);

            if (result < 1) {
                deviceDao.insertSpotDevDtl(svcTgtSeq, spotDevSeq, spotDevItemNm, "1");
            }

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * 디바이스의 서비스 관련 상세 정보 조회 메쏘드
     *
     * @param svcCode    서비스 코드
     * @param spotDev    현장 장치 객체. 널인 경우 내부에서 생성
     * @param svcTgtSeq  서비스 대상 일련번호
     * @param spotDevSeq 현장 장치 일련번호
     * @return 현장 장치 객체
     */
    private SpotDev getDeviceSvcDetail(String svcCode, SpotDev spotDev, long svcTgtSeq, long spotDevSeq) {

        // 최신 펌웨어 버전 및 업그레이드 옵션 조회
        String currentFirmwareVersion = getCurrentReleasedFirmwareVersion(svcCode);
        String currentFirmwareUpgradeOption = getCurrentReleasedFirmwareUpgradeOption(svcCode);

        // 버전이 동일하거나 상위버전이면 업그레이드 옵션은 0 처리
        // 버전 형태는 0.00.00 형태이며 비교 방법은 lexical ordering 처리
        if (StringUtils.defaultString(spotDev.getFrmwrVerNo()).compareTo(currentFirmwareVersion) >= 0) {
            currentFirmwareUpgradeOption = "0";
        }

        spotDev.addSpotDevDtl("newFrmwrVerNo", currentFirmwareVersion);
        spotDev.addSpotDevDtl("upgradeOption", currentFirmwareUpgradeOption);

        // TODO EC에서 리포팅 시 spotDevDtls 값에 status(privacy)값이 포함되어 있어 일반설정 값을 추가로 넣을 필요가 없어보임
        // 확장에 들어가야할 것들 처리 - 현재는 사생활 보호 모드 하나
        /*
        String privaySnsnTagCd = env.getProperty("snsnTagCd.status");
		for(GenlSetupData genlSetupData : spotDev.getGenlSetupDatas()) {
			if(StringUtils.equals(genlSetupData.getSnsnTagCd(), privaySnsnTagCd)) {
				spotDev.addSpotDevDtl("privacy", genlSetupData.getSetupVal());
			}
		}
		*/

        return spotDev;
    }

    /**
     * 디바이스의 서비스 관련 상세 정보 조회 메쏘드
     * <p/>
     * 전달된 SpotDev에 펌웨어 버전, 펌웨어 업그레이드 옵션 등의 정보를 저장.
     *
     * @param svcCode 서비스 코드
     * @param spotDev 현장 장치 객체. 널인 경우 내부에서 생성
     */
    private void setIntegratedDataConfig(String svcCode, SpotDev spotDev) {

        List<SpotDevDtl> firmProcStatus = deviceDao.getSpotDevDtls(spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq(), integratedDataItemNms);
        if (firmProcStatus.size() > 0) {
            for (SpotDevDtl spotDevDtl : firmProcStatus) {
                spotDev.addSpotDevDtl(spotDevDtl.getAtribNm(), spotDevDtl.getAtribVal());
            }
        } else {
            spotDev.addSpotDevDtl(SpotDevItemNm.UPGRADE_STATUS, SpotDevItemVal.UPGRADE_STATUS_INIT);
        }

    }

    /**
     * 디바이스의 서비스 관련 상세 정보 조회 메쏘드
     *
     * @param svcCode 서비스 코드
     * @param spotDev 현장 장치 객체. 널인 경우 내부에서 생성
     * @return 현장 장치 객체
     */
    public void setDeviceSvcDetail(String svcCode, SpotDev spotDev) {

        // 최신 펌웨어 버전 및 업그레이드 옵션 조회
        String currentFirmwareVersion = getCurrentReleasedFirmwareVersion(svcCode);
        String currentFirmwareUpgradeOption = getCurrentReleasedFirmwareUpgradeOption(svcCode);

        // 버전이 동일하거나 상위버전이면 업그레이드 옵션은 0 처리
        // 버전 형태는 0.00.00 형태이며 비교 방법은 lexical ordering 처리
        if (StringUtils.defaultString(spotDev.getFrmwrVerNo()).compareTo(currentFirmwareVersion) >= 0) {
            currentFirmwareUpgradeOption = "0";
        }

        spotDev.addSpotDevDtl("newFrmwrVerNo", currentFirmwareVersion);
        spotDev.addSpotDevDtl("upgradeOption", currentFirmwareUpgradeOption);

    }

    /**
     * 디바이스 일반 설정 조회 메쏘드
     *
     * @param spotDev    현장 장치 객체
     * @param svcTgtSeq  서비스 대상 일련번호
     * @param spotDevSeq 현장 장치 일련번호
     * @return 현장 장치 객체
     */
    private SpotDev getDeviceGeneralSetup(SpotDev spotDev, long svcTgtSeq, long spotDevSeq) {

        if (spotDev == null) {
            spotDev = new SpotDev();
        }

        ArrayList<GenlSetupData> spotDevGenSetupList = deviceDao.getSpotDevGenSetup(svcTgtSeq, spotDevSeq);
        spotDev.setGenlSetupDatas(spotDevGenSetupList);

        return spotDev;
    }

    /**
     * 디바이스 일반 설정 변경 메쏘드
     *
     * @param svcTgtSeq     서비스 대상 일련번호
     * @param spotDevSeq    현장 장치 일련번호
     * @param genlSetupData 일반 설정 객체
     */
    private void setDeviceGeneralSetup(long svcTgtSeq, long spotDevSeq, GenlSetupData genlSetupData) {

        String snsnTagCd = genlSetupData.getSnsnTagCd();
        String setupVal = genlSetupData.getSetupVal();
        deviceDao.updateSpotDevGenSetup(svcTgtSeq, spotDevSeq, snsnTagCd, setupVal);
    }

    /**
     * 디바이스 일반 설정 변경 메쏘드
     *
     * @param svcTgtSeq      서비스 대상 일련번호
     * @param spotDevSeq     현장 장치 일련번호
     * @param genlSetupDatas 일반 설정 객체
     */
    private void setDeviceGeneralSetups(long svcTgtSeq, long spotDevSeq, List<GenlSetupData> genlSetupDatas) {

        deviceDao.updateSpotDevGenSetups(svcTgtSeq, spotDevSeq, genlSetupDatas);
    }

    /**
     * 디바이스 일반 설정 등록 메쏘드
     *
     * @param svcTgtSeq     서비스 대상 일련번호
     * @param spotDevSeq    현장 장치 일련번호
     * @param genlSetupData 일반 설정 객체
     */
    public void createDeviceSetupBas(long svcTgtSeq, long spotDevSeq, GenlSetupData genlSetupData) {

        String snsnTagCd = genlSetupData.getSnsnTagCd();
        deviceDao.insertSpotDevSetupBase(svcTgtSeq, spotDevSeq, snsnTagCd);
    }

    /**
     * 디바이스 모든 설정 삭제 메쏘드
     *
     * @param svcTgtSeq  서비스 대상 일련번호
     * @param spotDevSeq 현장 장치 일련번호
     */
    public void deleteDeviceSetup(long svcTgtSeq, long spotDevSeq) {

        deviceDao.deleteSpotDevSetupBaseByDevice(svcTgtSeq, spotDevSeq);
        deviceDao.deleteSpotDevGenSetupByDevice(svcTgtSeq, spotDevSeq);
        deviceDao.deleteSpotDevSchSetupByDevice(svcTgtSeq, spotDevSeq);
    }

    /**
     * 디바이스 일반 설정 등록 메쏘드
     *
     * @param svcTgtSeq     서비스 대상 일련번호
     * @param spotDevSeq    현장 장치 일련번호
     * @param genlSetupData 일반 설정 객체
     */
    public void createDeviceGeneralSetup(long svcTgtSeq, long spotDevSeq, GenlSetupData genlSetupData) {

        String snsnTagCd = genlSetupData.getSnsnTagCd();
        String setupVal = genlSetupData.getSetupVal();
        deviceDao.insertSpotDevGenSetup(svcTgtSeq, spotDevSeq, snsnTagCd, setupVal);
    }

    /**
     * 디바이스 스케줄 설정 조회 메쏘드
     *
     * @param svcTgtSeq  서비스 대상 일련번호
     * @param spotDevSeq 현장 장치 일련번호
     * @return 스케줄 설정 객체 리스트
     */
    private ArrayList<SclgSetupData> getSclgSetupDataList(long svcTgtSeq, long spotDevSeq) {

        ArrayList<HashMap<String, Object>> spotDevSchSetupList = deviceDao.getSpotDevSchSetup(svcTgtSeq, spotDevSeq);
        int size = spotDevSchSetupList.size();

        ArrayList<SclgSetupData> setupDataList = new ArrayList<SclgSetupData>();
        SclgSetupData setupData = null;
        SclgData schData = null;
        String prevSnsnTagCd = null, prevDowCd = null;

        for (int i = 0; i < size; i++) {

            HashMap<String, Object> m = spotDevSchSetupList.get(i);

            String snsnTagCd = MapUtils.getString(m, "snsnTagCd");
            String dowCd = MapUtils.getString(m, "dowCd");
            String stTime = MapUtils.getString(m, "stTime");
            String fnsTime = MapUtils.getString(m, "fnsTime");
            Integer perdTime = MapUtils.getInteger(m, "perdTime");
            Integer duratTime = MapUtils.getInteger(m, "duratTime");

            if (StringUtils.equals(snsnTagCd, prevSnsnTagCd)) {
                if (!StringUtils.equals(dowCd, prevDowCd)) {
                    schData = new SclgData(dowCd);
                    setupData.addSclgData(schData);
                }
            } else {
                setupData = new SclgSetupData(snsnTagCd);
                setupDataList.add(setupData);
                schData = new SclgData(dowCd);
                setupData.addSclgData(schData);
            }

            schData.addSclgTime(stTime, fnsTime, perdTime, duratTime);
            prevSnsnTagCd = snsnTagCd;
            prevDowCd = dowCd;
        }

        return setupDataList;
    }

    /**
     * 디바이스 스케줄 설정 조회 메쏘드
     *
     * @param svcTgtSeq  서비스 대상 일련번호
     * @param spotDevSeq 현장 장치 일련번호
     * @param snsnTagCd  센싱 태그 코드
     * @return 스케줄 설정 객체 리스트
     */
    private ArrayList<SclgSetupData> getSclgSetupDataList(long svcTgtSeq, long spotDevSeq, String snsnTagCd) {

        ArrayList<SclgSetupData> sclgSetupDataList = getSclgSetupDataList(svcTgtSeq, spotDevSeq);
        ArrayList<SclgSetupData> newList = new ArrayList<SclgSetupData>();

        if (CollectionUtils.isEmpty(sclgSetupDataList)) {
            return newList;
        }

        for (SclgSetupData sclgSetupData : sclgSetupDataList) {
            if (StringUtils.equals(sclgSetupData.getSnsnTagCd(), snsnTagCd)) {
                newList.add(sclgSetupData);
            }
        }

        return newList;
    }

    /**
     * 디바이스 스케줄 설정 조회 메쏘드
     *
     * @param spotDev    현장 장치 객체
     * @param svcTgtSeq  서비 스대상 일련번호
     * @param spotDevSeq 현장 장치 일련번호
     * @return 현장 장치 객체
     */
    private SpotDev getDeviceScheduleSetup(SpotDev spotDev, long svcTgtSeq, long spotDevSeq) {

        if (spotDev == null) {
            spotDev = new SpotDev();
        }

        ArrayList<SclgSetupData> setupDataList = getSclgSetupDataList(svcTgtSeq, spotDevSeq);
        spotDev.setSclgSetupDatas(setupDataList);

        return spotDev;
    }

    private void upsertDeviceScheduleSetup(long svcTgtSeq, long spotDevSeq, List<SclgSetupData> sclgSetupDatas) {

        List<ScheduleData> scheduleDatas = new ArrayList<>();

        for (SclgSetupData sclgSetupData : sclgSetupDatas) {
            for (SclgData sclgData : sclgSetupData.getSclgDatas()) {
                for (SclgTimeData sclgTimeData : sclgData.getSclgTimeDatas()) {
                    final ScheduleData scheduleData = new ScheduleData(svcTgtSeq, spotDevSeq, sclgSetupData.getSnsnTagCd(), sclgData.getDowCd(), sclgTimeData.getStTime(), sclgTimeData.getFnsTime(), sclgTimeData.getPerdTime(), sclgTimeData.getDuratTime());
                    log.debug("Adding a schedule data : {}", scheduleData.toString());
                    scheduleDatas.add(scheduleData);
                }
            }
        }

        if (scheduleDatas.size() > 0) {
            try {
                deviceDao.upsertSpotDevSchSetup(scheduleDatas);
            } catch (DataIntegrityViolationException e) {
                // If a race condition occurs, retry.
                deviceDao.upsertSpotDevSchSetup(scheduleDatas);
            }
        }

    }

    /**
     * 디바이스 스케줄 설정 생성 메쏘드
     *
     * @param svcTgtSeq     서비스 대상 일련번호
     * @param spotDevSeq    현장 장치 일련번호
     * @param snsnTagCd     센싱 태그 코드
     * @param sclgSetupData 스케줄 설정 객체
     */
    public void createDeviceScheduleSetup(long svcTgtSeq, long spotDevSeq, String snsnTagCd, SclgSetupData sclgSetupData) {

        deviceDao.insertSpotDevSetupBase(svcTgtSeq, spotDevSeq, snsnTagCd);
        createDeviceSchedule(svcTgtSeq, spotDevSeq, snsnTagCd, sclgSetupData);
    }

    /**
     * 디바이스 스케줄 생성 메쏘드
     *
     * @param svcTgtSeq     서비스 대상 일련번호
     * @param spotDevSeq    현장 장치 일련번호
     * @param snsnTagCd     센싱 태그 코드
     * @param sclgSetupData 스케줄 설정 객체
     */
    private void createDeviceSchedule(long svcTgtSeq, long spotDevSeq, String snsnTagCd, SclgSetupData sclgSetupData) {

        List<SclgData> sclgDatas = sclgSetupData.getSclgDatas();
        for (SclgData sclgData : sclgDatas) {
            String dowCd = sclgData.getDowCd();
            SclgTimeData timeData = sclgData.getFirstSclgTimeData();
            String stTime = timeData.getStTime();
            String fnsTime = timeData.getFnsTime();
            Integer perdTime = timeData.getPerdTime();
            Integer duratTime = timeData.getDuratTime();
            int result = deviceDao.updateSpotDevSchSetup(svcTgtSeq, spotDevSeq, snsnTagCd, dowCd, stTime, fnsTime, perdTime, duratTime);
            if (result < 1) {
                deviceDao.insertSpotDevSchSetup(svcTgtSeq, spotDevSeq, snsnTagCd, dowCd, stTime, fnsTime, perdTime, duratTime);
            }
        }
    }

    /**
     * 외부에서 사용하는 디바이스 UUID 로 시스템 내부 사용 디바이스 식별자를 추출하는 메쏘드
     *
     * @param devUUID 디바이스 UUID
     * @return 내부 디바이스 식별자 배열. {서비스대상일련번호, 현장장치일련번호}
     */
    private long[] checkDeviceInternalKey(String devUUID) throws Exception {

        HashMap<String, Object> keyMap = deviceDao.getSpotDevKey(devUUID);

        if (MapUtils.isEmpty(keyMap)) {
            throw new APIException(String.format("Device key not found : %s", devUUID), HttpStatus.NOT_FOUND);
        }

        long svcTgtSeq = (long) keyMap.get("svcTgtSeq");
        long spotDevSeq = (long) keyMap.get("spotDevSeq");

        return new long[]{svcTgtSeq, spotDevSeq};
    }

    /**
     * 제어 VO ItgCnvyData 유효성 체크 메쏘드
     *
     * @param itgCnvyData        제어 VO
     * @param checkTransactionId 트랜잭션 아이디 체크 여부
     * @return 추출된 CnvyRow
     * @throws Exception
     */
    private CnvyRow checkContolVOValidation(ItgCnvyData itgCnvyData, boolean checkTransactionId) throws Exception {

        if (itgCnvyData == null) {
            throw new APIException("Invalid ControlVO. itgCnvyData is null", HttpStatus.BAD_REQUEST);
        }

        if (itgCnvyData.getFirstSpotDevCnvyData() == null) {
            throw new APIException("Invalid ControlVO. spotDevCnvyData is null", HttpStatus.BAD_REQUEST);
        }

        CnvyRow cnvyRow = itgCnvyData.getFirstSpotDevCnvyData().getFirstCnvyRow();
        if (cnvyRow == null) {
            throw new APIException("Invalid ControlVO. cnvyRow is null", HttpStatus.BAD_REQUEST);
        }

        if (checkTransactionId && StringUtils.isBlank(cnvyRow.getTransacId())) {
            throw new APIException("Invalid ControlVO. transacId is blank", HttpStatus.BAD_REQUEST);
        }

        return cnvyRow;
    }

    /**
     * 제어 VO ItgCnvyData 유효성 체크 메쏘드, 트랜잭션 아이디는 체크하지 않음
     *
     * @param itgCnvyData 제어 VO
     * @return 추출된 CnvyRow
     * @throws Exception
     */
    private CnvyRow checkContolVOValidation(ItgCnvyData itgCnvyData) throws Exception {
        return checkContolVOValidation(itgCnvyData, false);
    }

    /**
     * 전달행 CnvyRow 객체가 스케줄 설정 정보에 대한 것인지 체크하는 메쏘드
     * 포맷이 맞지 않는 경우 익셉션 발생
     *
     * @param cnvyRow 조사 대상 전달행 CnvyRow
     * @return 스케줄 설정 정보 여부
     * @throws Exception
     */
    private boolean isScheduleSetupCnvyRow(CnvyRow cnvyRow) throws Exception {

        boolean isScheduleSetup = false;

        String snsnTagCd;
        GenlSetupData genlSetupData = cnvyRow.getFirstGenlSetupData();
        if (genlSetupData == null) {
            SclgSetupData sclgSetupData = cnvyRow.getFirstSclgSetupData();
            snsnTagCd = sclgSetupData.getSnsnTagCd();
            isScheduleSetup = true;
        } else {
            snsnTagCd = genlSetupData.getSnsnTagCd();
            isScheduleSetup = false;
        }

        // 설정 정보 찾지 못한 경우. 개발 중에만 연동 테스트 중에만 발생하고 안정화 후 발생 안함
        if (!StringUtils.isNumeric(snsnTagCd)) {
            return false;
        }

        // 센싱태그 코드가 8로 시작하지 않는 경우. 개발 중에만 연동 테스트 중에만 발생하고 안정화 후 발생 안함
        if (!StringUtils.startsWith(snsnTagCd, "8")) {
            return false;
        }

        return isScheduleSetup;
    }

    /**
     * 트랜잭션 occrId 생성 메쏘드
     *
     * @param loginAuthToken 로그인 인증 토큰
     * @return 생성된 occrId
     */
    private String createOccrId(AuthToken loginAuthToken) {

        String userId = loginAuthToken.getUserId();
        String telNo = loginAuthToken.getTelNo();

        return userId + controlValueDelimiter + telNo;
    }

    /**
     * 제어 트랜잭션 생성 메쏘드
     *
     * @param loginAuthToken 로그인 인증 토큰
     * @param devUUID        디바이스 UUID
     * @param snsnTagCd      센싱 태그 코드
     * @param controlValue   제어 값
     * @return 생성된 제어 트랜잭션 객체
     * @throws Exception
     */
    private ControlTransaction createControlTransaction(AuthToken loginAuthToken, String devUUID, String snsnTagCd, String controlValue) throws Exception {

        String svcCode = loginAuthToken.getSvcCode();
        String occrId = createOccrId(loginAuthToken);

        long[] deviceKey = checkDeviceInternalKey(devUUID);
        long svcTgtSeq = deviceKey[0];
        long spotDevSeq = deviceKey[1];

        return createControlTransaction(svcCode, occrId, svcTgtSeq, spotDevSeq, snsnTagCd, controlValue);
    }

    /**
     * 제어 트랜잭션 생성 메쏘드
     *
     * @param svcCode      서비스 코드
     * @param occrId       제어 발생자 아이디
     * @param svcTgtSeq    서비스 대상 일련번호
     * @param spotDevSeq   현장 장치 일련번호
     * @param snsnTagCd    센싱 태그 코드
     * @param controlValue 제어 값
     * @return 생성된 제어 트랜잭션 객체
     * @throws Exception
     */
    private ControlTransaction createControlTransaction(String svcCode, String occrId, long svcTgtSeq, long spotDevSeq, String snsnTagCd, String controlValue) throws Exception {

        // 컨트롤 트랜잭션 생성
        String transactionId = deviceDao.createTransactionId();
        ControlTransaction transaction = new ControlTransaction();
        transaction.setTransactionId(transactionId);
        transaction.setUnitSvcCode(svcCode);
        transaction.setSvcTgtSeq(svcTgtSeq);
        transaction.setSpotDevSeq(spotDevSeq);
        transaction.setSnsnTagCd(snsnTagCd);
        transaction.setRtimeControlYn("N");        // TODO 여기 정리가 필요함
        transaction.setOccrId(occrId);
        transaction.setControlValue(controlValue);
        transaction.setStatusCode("0");
        transaction.setFnsYn("N");

        deviceDao.insertTransaction(transaction);

        return transaction;
    }

    /**
     * 앱으로 부터의 제어 VO 를 EC 연동 VO 로 변환하는 메쏘드
     * 내부 키와 트랜잭션 아이디를 설정함
     *
     * @param itgCnvyData 제어 VO
     * @param transaction 제어 트랜잭션
     * @return
     */
    private ItgCnvyData toInternalControlData(ItgCnvyData itgCnvyData, ControlTransaction transaction) {

        itgCnvyData.getFirstSpotDevCnvyData().setSvcTgtSeq(transaction.getSvcTgtSeq());
        itgCnvyData.getFirstSpotDevCnvyData().setSpotDevSeq(transaction.getSpotDevSeq());
        itgCnvyData.getFirstSpotDevCnvyData().getFirstCnvyRow().setTransacId(transaction.getTransactionId());

        return itgCnvyData;
    }

    /**
     * 제어 트랜잭션 처리 상태 객체를 생성하는 메쏘드
     * 리턴 오브젝트는 디바이스 마스터 정보 SpotDevRetvReslt 객체
     * SpotDevRetvReslt 가 널인 경우 내부에서 생성함
     *
     * @param spotDevRetvReslt 디바이스 마스터 정보. SpotDevRetvReslt 객체
     * @param transaction      제어 트랜잭션 객체
     * @return 디바이스 마스터 정보. SpotDevRetvReslt 객체
     */
    private SpotDevRetvReslt createControlTransactionStatusResult(SpotDevRetvReslt spotDevRetvReslt, ControlTransaction transaction) {

        if (spotDevRetvReslt == null) {
            spotDevRetvReslt = new SpotDevRetvReslt();
        }

        spotDevRetvReslt.setTransacId(transaction.getTransactionId());
        spotDevRetvReslt.setTransacStatus(transaction.getStatusCode());

        return spotDevRetvReslt;
    }

    /**
     * 제어 트랜잭션 처리 완료 결과를 리턴하는 메쏘드
     * 제어 트랜잭션이 완료된 경우 마지막 마스터 정보를 조회해서 리턴한다.
     *
     * @param authToken   로그인 인증 토큰
     * @param transaction 제어 트랜잭션 객체
     * @return 디바이스 마스터 정보. SpotDevRetvReslt 객체
     * @throws Exception
     */
    private SpotDevRetvReslt getControlTransactionCompleteResult(String authToken, ControlTransaction transaction) throws Exception {

        long svcTgtSeq = transaction.getSvcTgtSeq();
        long spotDevSeq = transaction.getSpotDevSeq();
        String devUUID = deviceDao.getSpotDevUUID(svcTgtSeq, spotDevSeq);
//		SpotDevRetvReslt result =
        Callable<SpotDevRetvReslt> masterConfig = getMasterConfig(authToken, devUUID, 10000L);
        Future<SpotDevRetvReslt> future = executor.submit(masterConfig);
        SpotDevRetvReslt result = future.get();

//		SpotDevRetvReslt result = getMasterConfigOnlyDB(authToken, devUUID);
        return createControlTransactionStatusResult(result, transaction);
    }

    /**
     * 제어 트랜잭션 처리 완료되지 않은 결과를 리턴하는 메쏘드
     * 현재 처리 상태값만 리턴한다.
     *
     * @param transaction 제어 트랜잭션 객체
     * @return 디바이스 마스터 정보. SpotDevRetvReslt 객체
     */
    private SpotDevRetvReslt getControlTransactionNotCompleteRequestResult(ControlTransaction transaction) {
        return createControlTransactionStatusResult(null, transaction);
    }

    /**
     * 디바이스 설정 제어가 가능한 상태인지 체크하는 메쏘드
     * 현재 모니터링 상태가 아닌 경우에만 설정 제어를 내릴 수 있음
     * 제어를 할 수 없는 경우 익셉션 발생시킴
     *
     * @param devUUID   디바이스 UUID
     * @param snsnTagCd 센싱 태그 코드
     * @throws Exception the exception
     */
    public void checkDeviceSetupControllable(String devUUID, String snsnTagCd) throws Exception {

        if (StringUtils.isEmpty(devUUID) || StringUtils.isEmpty(snsnTagCd)) {
            return;
        }

        // 해상도 변경인 경우에만 적용함
        try {
            if (StringUtils.equals(snsnTagCd, SnsnTagCd.GEN_SETUP_RESOLUTION) && relayService.checkAndDeleteRelayChannel(devUUID, false).get("result").equals("error")) {
                throw new APIException(String.format("Not Controllable device : %s, %s", devUUID, snsnTagCd), HttpStatus.FORBIDDEN, ErrorCode.NOT_CONTROLLABLE_DEVICE);
            }
        } catch (Exception e) {
            throw new APIException(String.format("Not Controllable device : %s, %s", devUUID, snsnTagCd), HttpStatus.FORBIDDEN, ErrorCode.NOT_CONTROLLABLE_DEVICE);
        }
    }

    /**
     * 디바이스 설정 제어 메쏘드
     *
     * @param authToken   로그인 인증 토큰
     * @param controlData 제어 요청 VO. ItgCnvyData 객체
     * @return 디바이스 마스터 정보. SpotDevRetvReslt 객체
     * @throws Exception the exception
     */
    public SpotDevRetvReslt setDeviceSetupControl(String authToken, ItgCnvyData controlData) throws Exception {

        AuthToken loginAuthToken = userService.checkLoginAuthToken(authToken);

        SpotDevCnvyData spotDevCnvyData = controlData.getFirstSpotDevCnvyData();
        String snsnTagCd, controlValue;

        // 1. ControlVO 유효성 체크 및 cnvyRow 추출
        CnvyRow cnvyRow = checkContolVOValidation(controlData);

        // 2. 설정 정보 찾기 및 에러 처리
        if (isScheduleSetupCnvyRow(cnvyRow)) {
            SclgSetupData sclgSetupData = cnvyRow.getFirstSclgSetupData();
            snsnTagCd = sclgSetupData.getSnsnTagCd();
            controlValue = sclgSetupData.getTimeBaseSetupVal(controlValueDelimiter);
        } else {
            GenlSetupData genlSetupData = cnvyRow.getFirstGenlSetupData();
            snsnTagCd = genlSetupData.getSnsnTagCd();
            controlValue = genlSetupData.getSetupVal();
        }

        checkDeviceSetupControllable(spotDevCnvyData.getDevUUID(), snsnTagCd);

        // 3. 트랜잭션 생성
        ControlTransaction transaction = createControlTransaction(loginAuthToken, spotDevCnvyData.getDevUUID(), snsnTagCd, controlValue);

        // 4. ControlVO 내부 변환
        controlData = toInternalControlData(controlData, transaction);

        // 임시 VO 체크
        controlData = toECControlVO(loginAuthToken.getSvcCode(), loginAuthToken.getUserNoLong(), snsnTagCd, controlData);

        // 5. EC 전송
        ecService.sendSetupChangeToEC(controlData);

        // 6. 결과 리턴
        return getControlTransactionNotCompleteRequestResult(transaction);
    }

    /**
     * 디바이스 실시간 동작 제어 메쏘드
     *
     * @param authToken   로그인 인증 토큰
     * @param itgCnvyData the itg cnvy data
     * @return 디바이스 마스터 정보. SpotDevRetvReslt 객체
     * @throws Exception the exception
     */
    public SpotDevRetvReslt setDeviceRTimeControl(String authToken, ItgCnvyData itgCnvyData) throws Exception {

        AuthToken loginAuthToken = userService.checkLoginAuthToken(authToken);

        SpotDevCnvyData spotDevCnvyData = itgCnvyData.getFirstSpotDevCnvyData();
        String snsnTagCd = null;
        double controlValue = -1;

        // 1. ControlVO 유효성 체크 및 cnvyRow 추출
        CnvyRow cnvyRow = checkContolVOValidation(itgCnvyData);

        // 2. 제어 정보 찾기 및 에러 처리
        ContlData contlData = cnvyRow.getFirstContlData();

        if (contlData == null) {
            throw new APIException("Invalid ControlVO. contlData is null", HttpStatus.BAD_REQUEST);
        }

        snsnTagCd = contlData.getSnsnTagCd();
        controlValue = contlData.getContlVal();

        // 설정 정보 찾지 못한 경우 에러 처리
        if (!StringUtils.isNumeric(snsnTagCd)) {
            throw new APIException(String.format("Invalid ControlVO. snsnTagCd [%s] is not defined", snsnTagCd), HttpStatus.BAD_REQUEST);
        }

        long startTime = System.nanoTime();
        // 3. 트랜잭션 생성
        ControlTransaction transaction = createControlTransaction(loginAuthToken, spotDevCnvyData.getDevUUID(), snsnTagCd, String.valueOf(controlValue));

        // 4. ControlVO 내부 변환
        itgCnvyData = toInternalControlData(itgCnvyData, transaction);

        log.debug("### Elapsed to insert to database : {}", (System.nanoTime() - startTime) / 1000000);
        // 5. EC 전송
        String result = ecService.sendRTimeControlToEC(itgCnvyData);

        log.debug("### Elapsed to send to ec : {}", (System.nanoTime() - startTime) / 1000000);

        // 6. 결과 리턴
        SpotDevRetvReslt spotDevRetvReslt = getControlTransactionNotCompleteRequestResult(transaction);

        if (spotDevRetvReslt == null) {
            spotDevRetvReslt = new SpotDevRetvReslt();
            spotDevRetvReslt.setTransacId(transaction.getTransactionId());
            spotDevRetvReslt.setTransacStatus(transaction.getStatusCode());
        }

        if (spotDevRetvReslt.getSpotDevs() == null || spotDevRetvReslt.getSpotDevs().size() == 0) {
            SpotDev spotDev = new SpotDev();
            spotDevRetvReslt.addSpotDev(spotDev);
        }

        spotDevRetvReslt.getFirstSpotDev().addSpotDevDtl(SpotDevItemNm.CON_STAT, result);

        return spotDevRetvReslt;
    }

    /**
     * 디바이스 실시간 동작 제어 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param devUUID   요청 디바이스 UUID
     * @param snsnTagCd 요청 센싱 태그
     * @return 디바이스 마스터 정보. SpotDevRetvReslt 객체
     * @throws Exception the exception
     */
    public SpotDevRetvReslt setDeviceSetupReadControl(String authToken, String devUUID, String snsnTagCd) throws Exception {

        AuthToken loginAuthToken = userService.checkLoginAuthToken(authToken);

        HashMap<String, Object> spotDevKey = deviceDao.getSpotDevKey(devUUID);
        long svcTgtSeq = (long) spotDevKey.get("svcTgtSeq");
        long spotDevSeq = (long) spotDevKey.get("spotDevSeq");

        String svcCode = loginAuthToken.getSvcCode();
        String occrId = createOccrId(loginAuthToken);

        SpotDevRetvReslt spotDevRetvReslt = null;
        String result = null;

        try {

            // 1. 트랜잭션 생성
            ControlTransaction transaction = createControlTransaction(svcCode, occrId, svcTgtSeq, spotDevSeq, snsnTagCd, "");
            String transactionId = transaction.getTransactionId();

            SpotDevRetv spotDevRetv = new SpotDevRetv();

            spotDevRetv.setUnitSvcCd(env.getServiceCode(svcCode));    // 주의. 실제로 9자리 서비스코드가 들어가야 정상 동작함.
            spotDevRetv.setTransacId(transactionId);
            spotDevRetv.addInclSpotDevKey(new SpotDevKey(svcTgtSeq, spotDevSeq));
            spotDevRetv.addCmdData(new CmdData(snsnTagCd));

            // 2. EC 전송
            result = ecService.sendRetrieveToEC(spotDevRetv);

            spotDevRetvReslt = new SpotDevRetvReslt();
            spotDevRetvReslt.setTransacId(transaction.getTransactionId());
            spotDevRetvReslt.setTransacStatus(transaction.getStatusCode());

            SpotDev spotDev = new SpotDev();
            spotDev.addSpotDevDtl(SpotDevItemNm.CON_STAT, result);
            spotDevRetvReslt.addSpotDev(spotDev);

        } catch (Exception e) {

            log.warn(e.getMessage(), e);
        }

        return spotDevRetvReslt;
    }

    /**
     * 디바이스 설정 조회 제어 메쏘드
     * 현재는 내부적으로만 사용됨
     *
     * @param svcCode    서비스 코드
     * @param svcTgtSeq  서비스 대상 일련번호
     * @param spotDevSeq 현장 장치 일련번호
     * @param occrId     제어 발생자 아이디
     * @param snsnTagCd  센싱 태그 코드
     * @return 트랜잭션 아이디
     */
    private String setDeviceSetupReadControl(String svcCode, long svcTgtSeq, long spotDevSeq, String occrId, String snsnTagCd) {

        String transactionId = null;

        try {

            // 1. 트랜잭션 생성
            ControlTransaction transaction = createControlTransaction(svcCode, occrId, svcTgtSeq, spotDevSeq, snsnTagCd, "");
            transactionId = transaction.getTransactionId();

            SpotDevRetv spotDevRetv = new SpotDevRetv();

            spotDevRetv.setUnitSvcCd(env.getServiceCode(svcCode));    // 주의. 실제로 9자리 서비스코드가 들어가야 정상 동작함.
            spotDevRetv.setTransacId(transactionId);
            spotDevRetv.addInclSpotDevKey(new SpotDevKey(svcTgtSeq, spotDevSeq));
            spotDevRetv.addCmdData(new CmdData(snsnTagCd));

            // 2. EC 전송
            ecService.sendRetrieveToEC(spotDevRetv);

        } catch (Exception e) {

            log.warn(e.getMessage(), e);
        }

        return transactionId;
    }

    /**
     * 제어 트랜잭션 처리 상태를 조회하는 메쏘드
     *
     * @param authToken     로그인 인증 토큰
     * @param transactionId 트랜잭션 아이디
     * @return 디바이스 마스터 정보. SpotDevRetvReslt 객체
     * @throws Exception the exception
     */
    public SpotDevRetvReslt getControlTransactionStatus(String authToken, String transactionId) throws Exception {

        userService.checkLoginAuthToken(authToken);
        ControlTransaction transaction = deviceDao.getTransaction(transactionId);

        log.debug("## transaction : " + transaction);
        if (transaction == null) {
            throw new APIException(String.format("TransactionId %s not found", transactionId), HttpStatus.NOT_FOUND);
        }

        String status = transaction.getStatusCode();
        if (StringUtils.equals(status, "0")) {
            return getControlTransactionNotCompleteRequestResult(transaction);
        } else {
            return getControlTransactionCompleteResult(authToken, transaction);
        }
    }

    /**
     * 디바이스(카메라) 이름 변경 메쏘드
     * 디비에서 이름을 변경하고 EC 쪽으로 변경 요청을 보낸다.
     * 디바이스의 ucloudPath 도 변경한다.
     *
     * @param authToken 인증토큰
     * @param devUUID   디바이스 UUID
     * @param devNm     디바이스 이름
     * @throws Exception the exception
     */
    @Async
    public void setDeviceName(String authToken, String devUUID, String devNm) throws Exception {

        userService.checkLoginAuthToken(authToken);

        HashMap<String, Object> spotDevKey = deviceDao.getSpotDevKey(devUUID);
        long svcTgtSeq = (long) spotDevKey.get("svcTgtSeq");
        long spotDevSeq = (long) spotDevKey.get("spotDevSeq");

        // 1. 디비 업데이트, EC 업데이트
        userService.setDeviceName(devUUID, devNm);

        // 2. 디바이스 ucloudPath 설정 제어 (저장모드가 ucloud 인 경우)
        String saveMode = deviceDao.getSpotDevGenSetupVal(svcTgtSeq, spotDevSeq, SnsnTagCd.GEN_SETUP_SAVE_MODE);
        if (StringUtils.equals(saveMode, "2")) {

            CnvyRow cnvyRow = new CnvyRow();
            cnvyRow.addGenlSetupData(new GenlSetupData(SnsnTagCd.GEN_SETUP_SAVE_MODE, "2"));

            SpotDevCnvyData spotDevCnvyData = new SpotDevCnvyData(svcTgtSeq, spotDevSeq, cnvyRow);
            spotDevCnvyData.setDevUUID(devUUID);

            ItgCnvyData itgCnvyData = new ItgCnvyData(spotDevCnvyData);
            setDeviceSetupControl(authToken, itgCnvyData);
        }
    }

    /**
     * 디바이스 펌웨어 최신 릴리즈 버전 조회 메쏘드
     * CMS 연동에 의한 버전 정보 조회
     *
     * @param svcCode 서비스 코드
     * @return 버전 스트링
     */
    private String getCurrentReleasedFirmwareVersion(String svcCode) {

        return initService.getProperty(svcCode, "dev.firm.version");
    }

    /**
     * 디바이스 펌웨어 최신 릴리즈 버전 업그레이드 옵션 조회 메쏘드
     * CMS 연동에 의한 버전 정보 조회
     *
     * @param svcCode 서비스 코드
     * @return 버전 스트링
     */
    private String getCurrentReleasedFirmwareUpgradeOption(String svcCode) {

        return initService.getProperty(svcCode, "dev.firm.upOption");
    }

    /**
     * 디바이스 펌웨어 최신 릴리즈 버전 조회 메쏘드
     * UCEMS 직접 연동에 의한 버전 정보 조회
     *
     * @param authToken 로그인 인증 토큰
     * @param devUUID   디바이스 UUID
     * @return 조회 결과 맵
     * newFrmwrVerNo	: 최신 펌웨어 버전 스트링
     * @throws Exception the exception
     */
    public HashMap<String, String> getCurrentReleasedFirmwareVersionFromUCEMS(String authToken, String devUUID) throws Exception {

        AuthToken loginAuthToken = userService.checkLoginAuthToken(authToken);

        SpotDev spotDev = getDevice(loginAuthToken.getSvcCode(), devUUID);
        if (spotDev == null) {
            throw new APIException(String.format("Device key not found : %s", devUUID), HttpStatus.NOT_FOUND);
        }

        String secret = StringUtils.defaultIfBlank(spotDev.getAthnNo(), spotDev.getSpotDevDtlVal(SpotDevItemNm.CCTV_SECRET));
        String modelCode = spotDev.getSpotDevDtlVal("cctvModelCd");

        log.debug("## getUCEMSFirmwareVersion : secret:{}, modelCode:{}", secret, modelCode);
        String version = ktService.sendCameraFirmVersionCheckRequest(secret, modelCode);

        HashMap<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("newFrmwrVerNo", version);

        return resultMap;
    }

    /**
     * 디바이스 펌웨어 업그레이드 요청 메쏘드
     * devUUID 가 널이면 사용자에 연결된 전체 디바이스에 대해 업그레이드 요청한다.
     *
     * @param authToken 로그인 인증 토큰
     * @param devUUID   디바이스 UUID
     * @throws Exception
     */
    public HashMap<String, String> setFirmwareUpgradeControlToUCEMS(String authToken, String devUUID) throws Exception {

        AuthToken loginAuthToken = userService.checkLoginAuthToken(authToken);
        String unitSvcCd = loginAuthToken.getSvcCode();

        if (StringUtils.isEmpty(devUUID)) {

            String svcCode = loginAuthToken.getSvcCode();

            ArrayList<SpotDev> spotDevList = deviceDao.getMemberSpotDev(svcCode, loginAuthToken.getUserNoLong(), null, null, null);
            for (SpotDev spotDev : spotDevList) {
                setFirmwareUpgradeRequestToUCEMS(unitSvcCd, spotDev.getDevUUID(), loginAuthToken);
            }

        } else {
            setFirmwareUpgradeRequestToUCEMS(unitSvcCd, devUUID, loginAuthToken);
        }

        return getIntnSuccessResult();
    }

    /**
     * 디바이스 펌웨어 업그레이드 요청 메쏘드.
     *
     * @param unitSvcCd 단위 서비스 코드
     * @param devUUID   디바이스 UUID
     * @throws Exception
     */
    private void setFirmwareUpgradeRequestToUCEMS(String unitSvcCd, String devUUID, AuthToken loginAuthToken) throws Exception {

        long svcTgtSeq = 0, spotDevSeq = 0;
        try {

            SpotDev spotDev = null;
            try {
                spotDev = checkDevice(unitSvcCd, devUUID);
            } catch (APIException e) {
                throw new APIException("Do not upgrade firmware.", HttpStatus.NOT_FOUND, ErrorCode.INVALID_DEVICE);
            }

            svcTgtSeq = spotDev.getSvcTgtSeq();
            spotDevSeq = spotDev.getSpotDevSeq();

            String secret = StringUtils.defaultIfBlank(spotDev.getAthnNo(), spotDev.getSpotDevDtlVal(SpotDevItemNm.CCTV_SECRET));
            String mac = spotDev.getSpotDevDtlVal(SpotDevItemNm.CCTV_MAC);
            String cameraSaid = spotDev.getSpotDevId();

            // 1. 타 장치 업그레이드 여부 체크
            boolean isUpgrade = false;
            ArrayList<Map<String, String>> list = deviceDao.getSpotDevDtlByMbrId(loginAuthToken.getUserNoLong(), SpotDevItemNm.UPGRADE_STATUS);
            for (Map map : list) {
                if (!SpotDevItemVal.UPGRADE_STATUS_INIT.equals(map.get("atribVal"))) {
                    isUpgrade = true;
                    break;
                }
            }

            if (isUpgrade) {
                throw new APIException("Do not upgrade firmware.", HttpStatus.CONFLICT, ErrorCode.CONFLICT_FIRMWAREUPGRADE);
            }

            // 2. 모니터링 여부 체크
            try {
                checkDeviceSetupControllable(spotDev.getDevUUID(), SnsnTagCd.GEN_SETUP_RESOLUTION);
            } catch (APIException e) {
                throw new APIException("Do not upgrade firmware.", HttpStatus.CONFLICT, ErrorCode.CONFLICT_MONITORING);
            }

            // 3. 업그레이드 요청
            String requestDT = "";
            try {
                requestDT = ktService.sendCameraFirmUpRequest(secret, mac, cameraSaid);
            } catch (Exception e) {
                throw new APIException("Do not upgrade firmware.", HttpStatus.SERVICE_UNAVAILABLE, ErrorCode.CEMS_ERROR);
            }

            // 4. 업그레이드 상태 - 시작
            setDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.UPGRADE_STATUS, SpotDevItemVal.UPGRADE_STATUS_IDLE);

            // 5. 업그레이드 시간을 CEMS로 전달받은 업그레이드 요청 시작 시간으로 설정
            setFirmwareUpgradeStartTimeToNow(svcTgtSeq, spotDevSeq, requestDT);

        } catch (Exception e) {
            log.warn(e.getMessage(), e);

            throw e;
        }
    }

    /**
     * 디바이스 펌웨어 업그레이드 시작 시간을 현재 시간으로 설정하는 메쏘드
     *
     * @param svcTgtSeq  서비스 대상 일련번호
     * @param spotDevSeq 현장 장치 일련번호
     */
    private void setFirmwareUpgradeStartTimeToNow(long svcTgtSeq, long spotDevSeq, String dateTime) {
        setDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.UPGRADE_START_TIME, dateTime);
    }

    /**
     * 펌웨어 업그레이드 상태 조회 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param devUUID   디바이스 UUID
     * @return 상태 조회 결과 맵
     * result		: 결과코드 -1:실패, 0:성공
     * stateCode	: 상태코드
     * @throws Exception the exception
     */
    public HashMap<String, String> getFirmwareUpgradeStatusFromUCEMS(String authToken, String devUUID) throws Exception {

        userService.checkLoginAuthToken(authToken);
        return getFirmwareUpgradeStatusFromUCEMS(devUUID);
    }

    /**
     * 펌웨어 업그레이드 상태 조회 메쏘드
     *
     * @param devUUID 디바이스 UUID
     * @throws Exception
     * @return 상태 조회 결과 맵
     * result		: 결과코드 -1:실패, 0:성공
     * stateCode	: 상태코드
     */
    private HashMap<String, String> getFirmwareUpgradeStatusFromUCEMS(String devUUID) throws Exception {

        SpotDev spotDev = checkDevice(devUUID);

        String secret = StringUtils.defaultIfBlank(spotDev.getAthnNo(), spotDev.getSpotDevDtlVal(SpotDevItemNm.CCTV_SECRET));
        String mac = spotDev.getSpotDevDtlVal(SpotDevItemNm.CCTV_MAC);
        String cameraSaid = spotDev.getSpotDevId();

        String upgradeStartTime = getDeviceDetailValue(spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq(), SpotDevItemNm.UPGRADE_START_TIME);
        String upgradeStatus = getDeviceDetailValue(spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq(), SpotDevItemNm.UPGRADE_STATUS);

        HashMap<String, String> resultMap = new HashMap<String, String>();

        // 1. 펌웨어 업데이트 상태 조회 후 업데이트
        if(SpotDevItemVal.UPGRADE_STATUS_IDLE.equals(upgradeStatus)
                || SpotDevItemVal.UPGRADE_STATUS_DOWNLOAD.equals(upgradeStatus)
                || SpotDevItemVal.UPGRADE_STATUS_DOWNLOAD_OK.equals(upgradeStatus)
                || SpotDevItemVal.UPGRADE_STATUS_INSTALL.equals(upgradeStatus)) {
            resultMap = ktService.sendCameraFirmUpCheckRequest(secret, mac, cameraSaid, upgradeStartTime);
            resultMap.put("startDate", upgradeStartTime);

            upgradeStatus = resultMap.get(SpotDevItemNm.UPGRADE_STATUS);
            setDeviceDetailValue(spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq(), SpotDevItemNm.UPGRADE_STATUS, upgradeStatus);

            if (SpotDevItemVal.UPGRADE_STATUS_SUCCESS.equals(upgradeStatus)) {
                String upgradeCompleteTime = upgradeStartTimeFormat.format(new Date());
                setDeviceDetailValue(spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq(), SpotDevItemNm.UPGRADE_COMPLETE_TIME, upgradeCompleteTime);

                resultMap.put("result", "0");
                resultMap.put("upgradeStatus", upgradeStatus);
                resultMap.put("startDate", upgradeCompleteTime);
            }
        } else if (SpotDevItemVal.UPGRADE_STATUS_SUCCESS.equals(upgradeStatus)) {
            String upgradeCompleteTime = getDeviceDetailValue(spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq(), SpotDevItemNm.UPGRADE_COMPLETE_TIME);

            resultMap.put("result", "0");
            resultMap.put("upgradeStatus", upgradeStatus);
            resultMap.put("startDate", upgradeCompleteTime);
        } else {
            resultMap.put("result", "0");
            resultMap.put("upgradeStatus", upgradeStatus);
            resultMap.put("startDate", upgradeStartTime);
        }

        return resultMap;
    }

    /**
     * 디바이스 펌웨어 업그레이드 확인 완료 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param devUUID   디바이스 UUID
     * @throws Exception
     */
    public HashMap<String, String> setFirmwareUpgradeComplete(String authToken, String devUUID) throws Exception {

        AuthToken loginAuthToken = userService.checkLoginAuthToken(authToken);

        SpotDev spotDev = checkDevice(loginAuthToken.getSvcCode(), devUUID);
        setDeviceDetailValue(spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq(), SpotDevItemNm.UPGRADE_STATUS, SpotDevItemVal.UPGRADE_STATUS_INIT);

        return getIntnSuccessResult();
    }

    /**
     * 펌웨어 업그레이드 상태 체크 메쏘드. 스케줄에 의해 호출됨
     */
    @Async
    public void checkFirmwareUpgradeStatusFromUCEMS() {

        ArrayList<SpotDev> spotDevList = null;
        try {

            spotDevList = deviceDao.getFirmwareUpgradeDeviceList();

            int count = CollectionUtils.size(spotDevList);
            log.debug("## checkUCEMSFirmwareUpgradeStatus : count is {}", count);

            if (count == 0) {
                return;
            }

            for (SpotDev spotDev : spotDevList) {

                log.debug("## checkFirmwareUpgradeStatusFromUCEMS SpotDev : " + spotDev);

                if (spotDev == null) {
                    continue;
                }

                try {
                    long svcTgtSeq = spotDev.getSvcTgtSeq();
                    long spotDevSeq = spotDev.getSpotDevSeq();

                    // 업그레이드 요청 만료시간 체크
                    checkFirmwareUpgradeStartTimeExpired(svcTgtSeq, spotDevSeq);

                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }

        } catch (Exception e) {

            log.warn(e.getMessage(), e);
        }
    }

    /**
     * 펌웨어 업그레이드 상태 체크 메쏘드. 스케줄에 의해 호출됨
     */
    @Async
    public void checkFirmwareUpgradeComplete() {

        ArrayList<SpotDev> spotDevList = null;
        try {

            spotDevList = deviceDao.getFirmwareUpgradeCompleteDeviceList();

            int count = CollectionUtils.size(spotDevList);
            log.debug("## checkFirmwareUpgradeComplete : count is {}", count);

            if (count == 0) {
                return;
            }

            for (SpotDev spotDev : spotDevList) {

                log.debug("## checkFirmwareUpgradeComplete SpotDev : " + spotDev);

                if (spotDev == null) {
                    continue;
                }

                try {
                    long svcTgtSeq = spotDev.getSvcTgtSeq();
                    long spotDevSeq = spotDev.getSpotDevSeq();

                    // 업그레이드 완료 후 EC 연결 여부 체크
                    String connStatus = getDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.CON_STAT);
                    String upgradeCompleteTime = getDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.UPGRADE_COMPLETE_TIME);
                    long upgradeCompleteTimeLong = upgradeStartTimeFormat.parse(upgradeCompleteTime).getTime();
                    int expireTimeMinutes = env.getIntProperty("ktinfra.firmupcomplete.expireTime");
                    boolean isExpired = ((System.currentTimeMillis() - upgradeCompleteTimeLong) > (expireTimeMinutes * 60 * 1000));
                    boolean isNotYet = ((System.currentTimeMillis() - upgradeCompleteTimeLong) < (10 * 1000));

                    if (SpotDevItemVal.CON_STAT_ONLINE.equals(connStatus) && !isNotYet) {
                        setDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.UPGRADE_STATUS, SpotDevItemVal.UPGRADE_STATUS_INIT);
                        continue;
                    }

                    if (isExpired) {
                        setDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.UPGRADE_STATUS, SpotDevItemVal.UPGRADE_STATUS_CONN_TIMEOUT);
                    }

                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }

        } catch (Exception e) {

            log.warn(e.getMessage(), e);
        }
    }

    /**
     * 디바이스 펌웨어 업그레이드 시간 만료 여부 체크 메쏘드
     * 업그레이드 시간이 만료된 경우 업그레이드 상태를 실패로 처리한다.
     *
     * @param svcTgtSeq  서비스 대상 일련번호
     * @param spotDevSeq 현장 장치 일련번호
     * @return 펌웨어 업그레이드 시간 만료 여부
     */
    private boolean checkFirmwareUpgradeStartTimeExpired(long svcTgtSeq, long spotDevSeq) {

        boolean isExpired = false;    // 업그레이드 최대 대기시간이 지났는지 여부

        try {

            String upgradeStartTime = getDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.UPGRADE_START_TIME);
            long upgradeStartTimeLong = upgradeStartTimeFormat.parse(upgradeStartTime).getTime();
            int expireTimeMinutes = env.getIntProperty("ktinfra.firmup.expireTime");
            isExpired = ((System.currentTimeMillis() - upgradeStartTimeLong) > (expireTimeMinutes * 60 * 1000));

            if (isExpired) {
                String upgradeStatus = getDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.UPGRADE_STATUS);

                // 아직 완료되지 않았으면 실패 처리
                if (!StringUtils.equalsIgnoreCase(upgradeStatus, SpotDevItemVal.UPGRADE_STATUS_INIT)) {
                    setDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.UPGRADE_STATUS, SpotDevItemVal.UPGRADE_STATUS_TIMEOUT);
                }
            }

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }

        return isExpired;
    }

    /**
     * Intn API 호출 결과 리턴 메쏘드
     *
     * @param code 리턴 코드
     * @param msg  리턴 메시지
     * @return 호출 결과 맵
     */
    public HashMap<String, String> getIntnResult(String code, String msg) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("code", code);
        resultMap.put("msg", msg);

        return resultMap;
    }

    /**
     * Intn API 호출 결과 성공 리턴 메쏘드
     *
     * @return 호출 결과 맵. code:"0", msg:"success"
     */
    public HashMap<String, String> getIntnSuccessResult() {
        return getIntnResult("0", "success");
    }

    /**
     * 실시간 및 동작 제어 명령에 대한 트랜잭션 완료 처리 메쏘드
     *
     * @param controlResult ItgCnvyData 객체
     * @param statusCode    트랜잭션 최종 상태 코드
     * @return 전달행 CnvyRow 객체
     * @throws Exception
     */
    private CnvyRow finalizeControlTransaction(ItgCnvyData controlResult, String statusCode) throws Exception {

        CnvyRow cnvyRow = checkContolVOValidation(controlResult);
        String transactionId = cnvyRow.getTransacId();
        deviceDao.finalizeTransaction(transactionId, statusCode);

        return cnvyRow;
    }

    /**
     * 실시간 및 동작 제어 명령에 대한 트랜잭션 완료 처리 메쏘드. 트랜잭션 최종 상태 1 로 완료 처리함.
     *
     * @param controlResult ItgCnvyData 객체
     * @return 전달행 CnvyRow 객체
     * @throws Exception
     */
    private CnvyRow finalizeControlTransaction(ItgCnvyData controlResult) throws Exception {
        return finalizeControlTransaction(controlResult, "1");
    }

    /**
     * 조회 명령에 대한 트랜잭션 완료 처리 메쏘드
     *
     * @param retvResult 디바이스 마스터 정보. SpotDevRetvReslt 객체
     * @param statusCode 트랜잭션 최종 상태 코드
     * @return 디바이스 마스터 정보. SpotDevRetvReslt 객체
     * @throws Exception
     */
    private SpotDevRetvReslt finalizeControlTransaction(SpotDevRetvReslt retvResult, String statusCode) throws Exception {

        String transactionId = retvResult.getTransacId();
        deviceDao.finalizeTransaction(transactionId, statusCode);

        return retvResult;
    }

    /**
     * 제어 결과 데이터 저장 메쏘드
     *
     * @param transactionId 트랜잭션 아이디
     * @param type          트랜잭션 유형. r:retrieve(조회), c:control(실시간제어), s:setup(설정제어)
     * @param result        트랜잭션 결과. EC 로부터 Report 내용
     */
    private void createControlResult(String transactionId, String type, String result) {
        try {
            deviceDao.insertTransactionResult(transactionId, type, result);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * 디바이스가 연결된 EC 서버 정보를 리턴하는 메쏘드
     *
     * @param unitSvcCd 단위 서비스 코드
     * @param devUUID   디바이스 UUID
     * @return SpotDevBasVO 객체
     * @throws Exception the exception
     */
    public SpotDevBasVO checkDeviceConnectedEC(String unitSvcCd, String devUUID) throws Exception {

        HashMap<String, Object> spotDevKey = deviceDao.getSpotDevKey(devUUID);

        if (MapUtils.isEmpty(spotDevKey)) {
            throw new APIException(String.format("SpotDevKey is Empty. (%s, %s)", unitSvcCd, devUUID), HttpStatus.FORBIDDEN);
        }

        long svcTgtSeq = (long) spotDevKey.get("svcTgtSeq");
        long spotDevSeq = (long) spotDevKey.get("spotDevSeq");
        String spotDevId = (String) spotDevKey.get("spotDevId");

        SpotDevBasQueryVO spotDevBasQuery = ecService.sendSelectQueryToEC(svcTgtSeq, spotDevSeq, spotDevId);

        if (spotDevBasQuery == null) {
            throw new APIException(String.format("EC call failure. SpotDevBasQuery is NULL. (%s, %s)", unitSvcCd, devUUID), HttpStatus.FORBIDDEN);
        }

        SpotDevBasVO spotDevBas = spotDevBasQuery.getSpotDevBasVO();
        if (spotDevBas == null) {
            throw new APIException(String.format("EC call failure. SpotDevBas is NULL. (%s, %s)", unitSvcCd, devUUID), HttpStatus.FORBIDDEN);
        }

        CommnAgntBasVO commnAgnt = spotDevBas.getCommnAgntBasVO();
        if (commnAgnt == null) {
            throw new APIException(String.format("EC call failure. CommnAgntBas is NULL. (%s, %s)", unitSvcCd, devUUID), HttpStatus.FORBIDDEN);
        }

        String ecServer = commnAgnt.getIpadr();
        log.debug("## checkDeviceConnectedEC Result ({}, {}) -> {}", unitSvcCd, devUUID, ecServer);

        return spotDevBas;
    }

    /**
     * 조회 요청에 대한 결과 Report 처리 메쏘드
     *
     * @param spotDevRetvReslt the spot dev retv reslt
     * @return 처리 결과 맵
     * @throws Exception the exception
     */
    public Callable<Map<String, String>> setDeviceReadResult(final SpotDevRetvReslt spotDevRetvReslt) throws Exception {

        if (spotDevRetvReslt.getTransacId().length() > 10) {

            return new Callable<Map<String, String>>() {

                @Override
                public Map<String, String> call() throws Exception {

                    String transacId = spotDevRetvReslt.getTransacId();
                    log.debug("### Received TransacId : {}", transacId);
                    log.debug("## Elapsed Time to Retrieve {} : {}", transacId, resltService.getElapsed(transacId));
                    transacId = transacId.substring(0, transacId.length() - 4);
                    log.debug("### Returning Request with TransacId : {}", transacId);
                    UUID uuid = UUID.fromString(transacId);


                    resltService.addSpotDevRetvReslt(uuid, spotDevRetvReslt);

                    // Database Update
                    updateDevice(spotDevRetvReslt);

                    return getIntnSuccessResult();
                }
            };

        } else {
            return new Callable<Map<String, String>>() {

                @Override
                public Map<String, String> call() throws Exception {

                    return setDeviceReadResultOld(spotDevRetvReslt);
                }

            };
        }
    }

    /**
     * 조회 요청에 대한 결과 Report 처리 메쏘드
     *
     * @param spotDevRetvReslt A retrieved result for SpotDev
     * @return Map<String  result map.
     * @throws Exception the exception
     */
    public Map<String, String> setDeviceReadResultOld(final SpotDevRetvReslt spotDevRetvReslt) throws Exception {

        // 트랜잭션 완료처리
        finalizeControlTransaction(spotDevRetvReslt, "1");

        // 조회 결과 write (제어 결과 저장 포함)
        writeDeviceReadResult(spotDevRetvReslt);

        // 성공으로 리턴
        return getIntnSuccessResult();
    }

    /**
     * 조회 요청에 대한 결과 Report 처리 메쏘드
     * 일반 설정은 SpotDevDtl 에서 꺼내 처리한다.
     * 스케줄 설정은 SclgSetupData 리스트에서 꺼내 처리한다.
     * 정확한 규격이 없고 센싱태그 코드가 꼬였기 때문에 현재 나오는 모양에 맞춘다.
     *
     * @param spotDevRetvReslt 조회 결과 SpotDevRetvReslt
     * @throws JsonProcessingException
     */
    private void updateDevice(SpotDevRetvReslt spotDevRetvReslt) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        log.debug(mapper.writeValueAsString(spotDevRetvReslt));
        SpotDev spotDev = spotDevRetvReslt.getFirstSpotDev();
        long svcTgtSeq = spotDev.getSvcTgtSeq();
        long spotDevSeq = spotDev.getSpotDevSeq();

        // 일반 설정 데이터는 SpotDevDtl 에서 추출
        Map<String, String> spotDevDtl = spotDev.getSpotDevDtlMap();

        // 스케줄 설정 데이터는 SclgSetupData 리스트에서 추출
        List<SclgSetupData> sclgSetupDataList = spotDev.getSclgSetupDatas();

        // 일반 설정과 스케줄 분기를 위한 카운트 계산
        int count = CollectionUtils.size(sclgSetupDataList);

        // 이 경우는 일반 설정, 설정테이블과 확장 테이블에 조회 결과를 넣는다. 펌웨어는 예외.
        if (count <= 0) {
            Iterator<String> it = spotDevDtl.keySet().iterator();
            while (it.hasNext()) {
                String name = it.next();
                String val = MapUtils.getString(spotDevDtl, name);

                if (StringUtils.equals(name, "firmwareVersion")) {
                    // 펌웨어 버전 업데이트
                    deviceDao.updateSpotDevFirmwareVersion(svcTgtSeq, spotDevSeq, val);
                } else {
                    // 센싱태그로 정의된 경우 일반설정 테이블에 저장
                    String snsnTagCd = env.getProperty("snsnTagCd." + name);
                    if (StringUtils.isNotBlank(snsnTagCd)) {
                        setDeviceGeneralSetup(svcTgtSeq, spotDevSeq, new GenlSetupData(snsnTagCd, val));
                    }

                    // 상세정보로 정의된 경우 상세정보 저장
                    String itemName = env.getProperty("retv.spotDev.itemNm." + name);
                    if (StringUtils.isNotBlank(itemName)) {
                        setDeviceDetailValue(svcTgtSeq, spotDevSeq, itemName, val);
                    }
                }
            }

        } else {
            // 스케줄 설정이 있는 경우
            log.debug("Upsert schedule datas.");
            upsertDeviceScheduleSetup(svcTgtSeq, spotDevSeq, sclgSetupDataList);
        }
    }


    /**
     * Update a SpotDev {@link com.kt.giga.home.openapi.vo.spotdev.SpotDev}.
     *
     * @param spotDevUdate {@link com.kt.giga.home.openapi.vo.spotdev.SpotDevUdate}
     * @throws JsonProcessingException {@link com.fasterxml.jackson.core.JsonProcessingException}
     */
    private void updateDevice(SpotDevUdate spotDevUdate) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        log.debug(mapper.writeValueAsString(spotDevUdate));
        SpotDev spotDev = spotDevUdate.getFirstSpotDev();
        long svcTgtSeq = spotDev.getSvcTgtSeq();
        long spotDevSeq = spotDev.getSpotDevSeq();

        // 일반 설정 데이터는 SpotDevDtl 에서 추출
        Map<String, String> spotDevDtl = spotDev.getSpotDevDtlMap();

        // 스케줄 설정 데이터는 SclgSetupData 리스트에서 추출
        List<SclgSetupData> sclgSetupDataList = spotDev.getSclgSetupDatas();

        // 일반 설정과 스케줄 분기를 위한 카운트 계산
        int count = CollectionUtils.size(sclgSetupDataList);

        // 이 경우는 일반 설정, 설정테이블과 확장 테이블에 조회 결과를 넣는다. 펌웨어는 예외.
        Iterator<String> it = spotDevDtl.keySet().iterator();
        while (it.hasNext()) {
            long stime = System.currentTimeMillis();

            String name = it.next();
            String val = MapUtils.getString(spotDevDtl, name);

            if (StringUtils.equals(name, "firmwareVersion")) {
                // 펌웨어 버전 업데이트
                deviceDao.updateSpotDevFirmwareVersion(svcTgtSeq, spotDevSeq, val);
            } else {
                // 센싱태그로 정의된 경우 일반설정 테이블에 저장
                String snsnTagCd = env.getProperty("snsnTagCd." + name);
                if (StringUtils.isNotBlank(snsnTagCd)) {
                    setDeviceGeneralSetup(svcTgtSeq, spotDevSeq, new GenlSetupData(snsnTagCd, val));
                }

                // 상세정보로 정의된 경우 상세정보 저장
                String itemName = env.getProperty("retv.spotDev.itemNm." + name);
                if (StringUtils.isNotBlank(itemName) && StringUtils.isNotBlank(val)) {
                    setDeviceDetailValue(svcTgtSeq, spotDevSeq, itemName, val);
                }
            }
        }

        if (count > 0) {
            // 스케줄 설정이 있는 경우
            upsertDeviceScheduleSetup(svcTgtSeq, spotDevSeq, sclgSetupDataList);
        }

        // 접속 상태 업데이트
        String sessnId = spotDev.getSessnId();
        String connYn = StringUtils.isNotBlank(sessnId) ? "Y" : "N";

        int result = deviceDao.updateSpotDevConnBas(svcTgtSeq, spotDevSeq, connYn, "N");
        if (result < 1) {
            deviceDao.insertSpotDevConnBas(svcTgtSeq, spotDevSeq, connYn);
        }

        if (StringUtils.equals(connYn, "Y")) {

            // 연결 상태 업데이트
            setDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.CON_STAT, SpotDevItemVal.CON_STAT_ONLINE);

            // 연결 카운트 +1
            increaseDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.CON_COUNT);

            // 상태 정상 처리
            deviceDao.updteSpotDevStatus(svcTgtSeq, spotDevSeq, "01");

        } else {

            // 연결 상태 업데이트
            setDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.CON_STAT, SpotDevItemVal.CON_STAT_OFFLINE);
        }
    }

    /**
     * 조회 요청에 대한 결과 Report 처리 메쏘드
     * 일반 설정은 SpotDevDtl 에서 꺼내 처리한다.
     * 스케줄 설정은 SclgSetupData 리스트에서 꺼내 처리한다.
     * 정확한 규격이 없고 센싱태그 코드가 꼬였기 때문에 현재 나오는 모양에 맞춘다.
     * TODO 꼬인 부분을 하나씩 풀어야 한다.
     *
     * @param retvResult 조회 결과 SpotDevRetvReslt
     * @throws JsonProcessingException
     */
    private void writeDeviceReadResult(SpotDevRetvReslt retvResult) throws JsonProcessingException {

        SpotDev spotDev = retvResult.getFirstSpotDev();
        long svcTgtSeq = spotDev.getSvcTgtSeq();
        long spotDevSeq = spotDev.getSpotDevSeq();

        // 일반 설정 데이터는 SpotDevDtl 에서 추출
        Map<String, String> spotDevDtl = spotDev.getSpotDevDtlMap();

        // 스케줄 설정 데이터는 SclgSetupData 리스트에서 추출
        List<SclgSetupData> sclgSetupDataList = spotDev.getSclgSetupDatas();

        // 일반 설정과 스케줄 분기를 위한 카운트 계산
        int count = CollectionUtils.size(sclgSetupDataList);

        // 이 경우는 일반 설정, 설정테이블과 확장 테이블에 조회 결과를 넣는다. 펌웨어는 예외.
        if (count <= 0) {
            List<GenlSetupData> genlSetupDatas = new ArrayList<GenlSetupData>();

            Iterator<String> it = spotDevDtl.keySet().iterator();
            while (it.hasNext()) {
                String name = it.next();
                String val = MapUtils.getString(spotDevDtl, name);

                if (StringUtils.equals(name, "firmwareVersion")) {
                    // 펌웨어 버전 업데이트
                    deviceDao.updateSpotDevFirmwareVersion(svcTgtSeq, spotDevSeq, val);
                } else {
                    // 센싱태그로 정의된 경우 일반설정 테이블에 저장
                    String snsnTagCd = env.getProperty("snsnTagCd." + name);
                    if (StringUtils.isNotBlank(snsnTagCd)) {
                        genlSetupDatas.add(new GenlSetupData(snsnTagCd, val));
//						setDeviceGeneralSetup(svcTgtSeq, spotDevSeq, new GenlSetupData(snsnTagCd, val));
                    }

                    // 상세정보로 정의된 경우 상세정보 저장
                    String itemName = env.getProperty("retv.spotDev.itemNm." + name);
                    if (StringUtils.isNotBlank(itemName)) {
                        setDeviceDetailValue(svcTgtSeq, spotDevSeq, itemName, val);
                    }
                }
            }

            if (genlSetupDatas.size() > 0)
                deviceDao.updateSpotDevGenSetups(svcTgtSeq, spotDevSeq, genlSetupDatas);
        } else {
            // 스케줄 설정이 있는 경우
            log.debug("Upsert schedule datas.");
            upsertDeviceScheduleSetup(svcTgtSeq, spotDevSeq, sclgSetupDataList);
        }

        // 제어 결과 저장
        ObjectMapper mapper = new ObjectMapper();
        String resultJson = mapper.writeValueAsString(retvResult);
        createControlResult(retvResult.getTransacId(), TransacResultType.RETRIEVE, resultJson);
    }

    /**
     * EC 로 부터 디바이스 정보 조회 요청 수신 메쏘드
     * 현재 UCloud 정보 조회 요청 수신 용도로 사용함
     *
     * @param requestJson 조회 요청 JSON
     * @throws Exception the exception
     */
    @Async
    public void setDeviceReadRequest(String requestJson) throws Exception {

        SpotDevRetv spotDevRetv = JsonUtils.fromJson(requestJson, SpotDevRetv.class);

        SpotDevKey spotDevKey = spotDevRetv.getFirstInclSpotDevKey();
        if (spotDevKey != null) {
            String snsnTagCd = spotDevRetv.getFirstCmdDataSnsnTagCd();

            // snsnTagCd 가 UCloud 토큰 요청인 경우
            if (StringUtils.equals(snsnTagCd, SnsnTagCd.RETV_UCLOUD_TOKEN)) {
                setUCloudAccessTokenRequest(spotDevRetv);
            }
        }
    }

    /**
     * EC 로 부터 UCloud 토큰 요청을 처리하는 메쏘드
     *
     * @param spotDevRetv 디바이스 정보 요청 VO (UCloud 토큰 요청)
     * @throws Exception
     */
    private void setUCloudAccessTokenRequest(SpotDevRetv spotDevRetv) throws Exception {

        SpotDevKey spotDevKey = spotDevRetv.getFirstInclSpotDevKey();

        String unitSvcCd = env.getLast3ByteUnitSvcCd(spotDevRetv.getUnitSvcCd());
        long svcTgtSeq = spotDevKey.getSvcTgtSeq();
        long spotDevSeq = spotDevKey.getSpotDevSeq();
        String spotDevId = spotDevKey.getSpotDevId();

        ArrayList<SpotDev> spotDevList = deviceDao.getMemberSpotDev(unitSvcCd, null, null, svcTgtSeq, spotDevSeq);
        if (CollectionUtils.isNotEmpty(spotDevList)) {

            for (SpotDev spotDev : spotDevList) {

                log.debug("## setUCloudAccessTokenRequest " + spotDev);

                String mbrId = spotDev.getMbrId();
                String credentialId = spotDev.getCredentialId();
                String saId = spotDev.getSvcContId();
                String cameraSaid = spotDev.getSpotDevId();
                String devNm = spotDev.getSpotDevNm();

                HashMap<String, Object> ucloudResult = ktService.sendUCloudAccessTokenReq(mbrId, credentialId, saId, cameraSaid);
                log.debug("## ucloudResult : " + ucloudResult);

                // cmdData 는 EC 에서 수신된 것에 처리 결과 값을 설정함
                CmdData cmdData = spotDevRetv.getFirstCmdData();
                cmdData.setCmdValTxn(ByteBuffer.allocate(4).putInt(MapUtils.getIntValue(ucloudResult, "resultCode")).array());

                // UCloud 관련 데이터는 일반 설정으로 구성
                SpotDev resultSpotDev = new SpotDev(svcTgtSeq, spotDevSeq, spotDevId);
                resultSpotDev.addGenlSetupData(new GenlSetupData(SnsnTagCd.UCLOUD_PATH, devNm));
                resultSpotDev.addGenlSetupData(new GenlSetupData(SnsnTagCd.UCLOUD_API_KEY, MapUtils.getString(ucloudResult, "apiKey")));
                resultSpotDev.addGenlSetupData(new GenlSetupData(SnsnTagCd.UCLOUD_API_SECRET, MapUtils.getString(ucloudResult, "apiSecret")));
                resultSpotDev.addGenlSetupData(new GenlSetupData(SnsnTagCd.UCLOUD_AUTH_TOKEN, MapUtils.getString(ucloudResult, "authToken")));
                resultSpotDev.addGenlSetupData(new GenlSetupData(SnsnTagCd.UCLOUD_AUTH_TOKEN_SECRET, MapUtils.getString(ucloudResult, "authTokenSecret")));

                // 최종 SpotDevRetvReslt 구성
                SpotDevRetvReslt spotDevRetvReslt = new SpotDevRetvReslt(resultSpotDev);
                spotDevRetvReslt.addCmdData(cmdData);
                spotDevRetvReslt.setTransacId(spotDevRetv.getTransacId());
                spotDevRetvReslt.setLowTransacId(spotDevRetv.getLowTransacId());
                spotDevRetvReslt.setReqEcSrvrId(spotDevRetv.getReqEcSrvrId());

                // EC 로 발송
                ecService.sendRetrieveReportToEC(spotDevRetvReslt);
            }
        }
    }

    /**
     * UCloud 토큰을 조회하는 메쏘드
     * 계정 생성을 먼저 요청하고 토큰을 조회함
     *
     * @param svcTgtSeq    서비스 대상 일련번호
     * @param spotDevSeq   현장 장치 일련번호
     * @param mbrId        회원 일련번호
     * @param credentialId Credential ID
     * @param saId         SAID
     * @param cameraSaid   카메라 SAID
     * @return UCloud 연동 처리 결과
     */
    private HashMap<String, Object> checkUCloudAccessToken(long svcTgtSeq, long spotDevSeq, String mbrId, String credentialId, String saId, String cameraSaid) throws Exception {

        String ucloudStatus = getDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.UCLOUD_STATUS);

        // 가입 상태가 아니면 가입상태 체크
        if (!StringUtils.equals(ucloudStatus, SpotDevItemVal.UCLOUD_STATUS_ON)) {

            HashMap<String, Object> ucloudResult = ktService.sendUCloudAccountCreateReq(mbrId, credentialId, saId);
            String ucloudResultCode = MapUtils.getString(ucloudResult, "resultCode");

            // resultCode = 0     : 계정 생성 성공 ==> 토큰 요청 가능
            // resultCode = -11   : [SDP] 홈 모니터링 이미 가입된 고객입니다 ==> 그러나 토큰 요청 가능
            // resultCode = -13   : [DB] 홈 모니터링 이미 가입된 고객입니다 ==> 그러나 토큰 요청 가능

            if (StringUtils.equals(ucloudResultCode, "0") || StringUtils.equals(ucloudResultCode, "-11") || StringUtils.equals(ucloudResultCode, "-13")) {
                setDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.UCLOUD_STATUS, SpotDevItemVal.UCLOUD_STATUS_ON);
            } else {
                throw new APIException("ucloud error. ResultCode = " + ucloudResultCode, HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.UCLOUD_ERROR);
            }

        }

        HashMap<String, Object> ucloudResult = ktService.sendUCloudAccessTokenReq(mbrId, credentialId, saId, cameraSaid);
        String ucloudResultCode = MapUtils.getString(ucloudResult, "resultCode");
        if (!StringUtils.equals(ucloudResultCode, "0")) {
            throw new APIException("ucloud error. ResultCode = " + ucloudResultCode, HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.UCLOUD_ERROR);
        }

        return ucloudResult;
    }

    /**
     * UCloud 계정 삭제 메쏘드
     *
     * @param unitSvcCd 단위 서비스 코드
     * @param loginId   로그인 아이디
     * @return 처리 결과 맵
     */
    public HashMap<String, String> deleteUCloudAccount(String unitSvcCd, String loginId) {

        try {
            User userBase = userDao.getUserBase(loginId);

            if (userBase == null) {
                return getIntnResult(ErrorCode.UCLOUD_ID_NOT_FOUND, String.format("loginId [%s] not found in MBR_BAS", loginId));
            }

            ArrayList<SpotDev> spotDevList = deviceDao.getMemberSpotDev(unitSvcCd, userBase.getMbrSeq(), null, null, null);
            if (CollectionUtils.isNotEmpty(spotDevList)) {

                for (SpotDev spotDev : spotDevList) {

                    log.debug("## deleteUCloudAccount : " + spotDev);

                    long svcTgtSeq = spotDev.getSvcTgtSeq();
                    long spotDevSeq = spotDev.getSpotDevSeq();
                    String credentialId = spotDev.getCredentialId();
                    String saId = spotDev.getSvcContId();

                    log.debug("## deleteUCloudAccount : {}", spotDev);

                    HashMap<String, Object> resultMap = ktService.sendUCloudAccountDeleteReq(loginId, credentialId, saId);
                    String ucloudResult = MapUtils.getString(resultMap, "resultCode");

                    if (StringUtils.equals(ucloudResult, "0")) {
                        setDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.UCLOUD_STATUS, SpotDevItemVal.UCLOUD_STATUS_OFF);
                    } else {
                        log.debug("## deleteUCloudAccount error : {} ", resultMap);
                        return getIntnResult(ErrorCode.UCLOUD_ERROR, String.valueOf(resultMap));
                    }
                }
            }

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return getIntnResult(ErrorCode.UCLOUD_ERROR, e.getMessage());
        }

        return getIntnSuccessResult();
    }

    /**
     * 실시간 동작 제어에 대한 결과 Report 처리 메쏘드
     *
     * @param resultJson 동작 제어 결과 JSON
     * @return 처리 결과 맵
     * @throws Exception the exception
     */
    public HashMap<String, String> setRTimeControlResult(final String resultJson) throws Exception {

        executor.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    ItgCnvyData itgCnvyData = JsonUtils.fromJson(resultJson, ItgCnvyData.class);
                    CnvyRow cnvyRow = finalizeControlTransaction(itgCnvyData);
                    createControlResult(cnvyRow.getTransacId(), TransacResultType.CONTROL, resultJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        return getIntnSuccessResult();
    }

    /**
     * 설정 제어에 대한 결과 Report 처리 메쏘드
     *
     * @param resultJson 설정 제어 결과 Report
     * @return 처리 결과 맵
     * @throws Exception the exception
     */
    public HashMap<String, String> setSetupControlResult(final String resultJson) throws Exception {

        executor.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    ItgCnvyData itgCnvyData = JsonUtils.fromJson(resultJson, ItgCnvyData.class);
                    CnvyRow cnvyRow = setSetupControlResult(itgCnvyData);
                    createControlResult(cnvyRow.getTransacId(), TransacResultType.SETUP, resultJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        return getIntnSuccessResult();
    }

    /**
     * 설정 제어 결과를 적용하는 메쏘드
     *
     * @param controlResult 제어 VO ItgCnvyData 객체
     * @return CnvyRow 객체
     * @throws Exception
     */
    private CnvyRow setSetupControlResult(ItgCnvyData controlResult) throws Exception {

        CnvyRow cnvyRow = finalizeControlTransaction(controlResult);
        long svcTgtSeq = controlResult.getFirstSpotDevCnvyData().getSvcTgtSeq();
        long spotDevSeq = controlResult.getFirstSpotDevCnvyData().getSpotDevSeq();

        if (isScheduleSetupCnvyRow(cnvyRow)) {
            upsertDeviceScheduleSetup(svcTgtSeq, spotDevSeq, cnvyRow.getSclgSetupDatas());

        } else {
            GenlSetupData genlSetupData = cnvyRow.getFirstGenlSetupData();
            setDeviceGeneralSetup(svcTgtSeq, spotDevSeq, genlSetupData);
        }

        int rowExtResult = setReportRowExtension(svcTgtSeq, spotDevSeq, cnvyRow);
        if (rowExtResult > 1) {
            // rowExt 때문에 한번 더한다. result 가 1 이상인 경우에만. 결과를 업데이트..
            // 이부분 정리가 좀 필요함, 디비 테이블의 구조적인 문제점이 있음
            finalizeControlTransaction(controlResult, String.valueOf(rowExtResult));
        }

        return cnvyRow;
    }

    /**
     * 제어 결과로 수신된 rowExtension 의 일반설정 처리
     * 주의. rowExtension 관련 부분은 모두 예외적인 상황임에 주의. 결과를 보고 맞췄음
     *
     * @param svcTgtSeq  서비스 대상 일련번호
     * @param spotDevSeq 현장 장치 일련번호
     * @param cnvyRow    전달행 CnvyRow 객체
     * @return rowExtenstion 의 result 값 (디바이스로 부터의 처리 결과 리턴 값)
     * @throws Exception
     */
    private int setReportRowExtension(long svcTgtSeq, long spotDevSeq, CnvyRow cnvyRow) throws Exception {
        HashMap<String, Object> rowExt = cnvyRow.getRowExtension();

        if (MapUtils.isEmpty(rowExt)) {
            return -1;
        }

        int rowExtResult = (int) NumberUtils.toFloat(String.valueOf(rowExt.get("result")), -1f);

        Iterator<String> it = rowExt.keySet().iterator();
        while (it.hasNext()) {
            String name = it.next();
            String snsnTagCd = env.getProperty("snsnTagCd." + name);

            // 스케줄 예외처리
            if (StringUtils.equals(name, "scheduled")) {
                String oSnsnTagCd = cnvyRow.getFirstSclgSetupData().getSnsnTagCd();

                if (StringUtils.equals(oSnsnTagCd, SnsnTagCd.SCH_SETUP_DETECTION)) {            // 감시스케줄
                    snsnTagCd = SnsnTagCd.GEN_SETUP_DETECTION_SCHEDULE;
                } else if (StringUtils.equals(oSnsnTagCd, SnsnTagCd.SCH_SETUP_RECORD)) {        // 녹화스케줄
                    snsnTagCd = SnsnTagCd.GEN_SETUP_RECORD_SCHEDULE;
                }

                log.debug("## setRowExtension ScheduleException snsnTagCd : {} --> {}", oSnsnTagCd, snsnTagCd);
            }

            if (StringUtils.isBlank(snsnTagCd)) {
                continue;
            }

            // float 형 예외처리 필요함. 정수형으로 컨버팅
            String val = String.valueOf(MapUtils.getIntValue(rowExt, name));

            // 스케줄 예외처리
            if (StringUtils.equals(name, "scheduled")) {
                val = StringUtils.equals(val, "0") ? "0" : "1";
                log.debug("## setRowExtension ScheduleException val : " + val);
            }

            setDeviceGeneralSetup(svcTgtSeq, spotDevSeq, new GenlSetupData(snsnTagCd, val));
        }

        return rowExtResult;
    }

    /**
     * 디바이스 갱신 Report 처리 메쏘드
     * 현재 연결 상태 갱신 Report 를 사용
     *
     * @param updateJson 디바이스 갱신 알림 JSON
     * @return 처리 결과 맵
     * @throws Exception the exception
     */
    public HashMap<String, String> setSpotDevUpdate(String updateJson) throws Exception {

        SpotDevUdate spotDevUdate = JsonUtils.fromJson(updateJson, SpotDevUdate.class);

        updateDevice(spotDevUdate);

        return getIntnSuccessResult();
    }

    /**
     * 디바이스 연결 상태 변경 메쏘드
     *
     * @param updateData 디바이스 갱신 객체 SpotDevUdate
     */
    private void setDeviceOnlineStatus(SpotDevUdate updateData) {

        try {

            List<SpotDev> spotDevList = updateData.getSpotDevs();
            for (SpotDev spotDev : spotDevList) {

                long svcTgtSeq = spotDev.getSvcTgtSeq();
                long spotDevSeq = spotDev.getSpotDevSeq();
                String sessnId = spotDev.getSessnId();
                String connYn = StringUtils.isNotBlank(sessnId) ? "Y" : "N";

                int count = deviceDao.getSpotDevConnBasCount(svcTgtSeq, spotDevSeq);
                if (count == 0) {
                    deviceDao.insertSpotDevConnBas(svcTgtSeq, spotDevSeq, connYn);
                } else {
                    deviceDao.updateSpotDevConnBas(svcTgtSeq, spotDevSeq, connYn, "N");
                }

                if (StringUtils.equals(connYn, "Y")) {

                    // 연결 상태 업데이트
                    setDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.CON_STAT, SpotDevItemVal.CON_STAT_ONLINE);

                    // 연결 카운트 +1
                    increaseDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.CON_COUNT);

                    // 상태 정상 처리
                    deviceDao.updteSpotDevStatus(svcTgtSeq, spotDevSeq, "01");

                } else {

                    // 연결 상태 업데이트
                    setDeviceDetailValue(svcTgtSeq, spotDevSeq, SpotDevItemNm.CON_STAT, SpotDevItemVal.CON_STAT_OFFLINE);
                }
            }

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * 앱과 정의된 센싱태그 코드 및 VO 규격을 EC 구간에 맞게 조정하는 메쏘드
     * 근본적으로 센싱태그 코드 및 연동규격이 꼬여있음.
     * UCloud 연동 예외처리도 포함. 수정하는데 주의가 많이 필요합니다.
     *
     * @param snsnTagCd   센싱태그 코드
     * @param itgCnvyData 제어 VO ItgCnvyData 객체
     * @return 제어 VO ItgCnvyData 객체
     * @Param unitSvcCd        단위 서비스 코드
     */
    private ItgCnvyData toECControlVO(String unitSvcCd, long mbrSeq, String snsnTagCd, ItgCnvyData itgCnvyData) throws Exception {

        long svcTgtSeq = itgCnvyData.getFirstSpotDevCnvyData().getSvcTgtSeq();
        long spotDevSeq = itgCnvyData.getFirstSpotDevCnvyData().getSpotDevSeq();
        CnvyRow cnvyRow = itgCnvyData.getFirstSpotDevCnvyRow();

        if (StringUtils.equals(snsnTagCd, SnsnTagCd.GEN_SETUP_DETECTION)) {

            // 실시간감지 ON/OFF 요청인 경우의 변환
            String detection = cnvyRow.getFirstGenlSetupData().getSetupVal();
            String detectionMode = deviceDao.getSpotDevGenSetupVal(svcTgtSeq, spotDevSeq, SnsnTagCd.GEN_SETUP_DETECTION_MODE);

            cnvyRow.getFirstGenlSetupData().setSnsnTagCd(SnsnTagCd.GEN_SETUP_DETECTION_MODE);
            cnvyRow.addRowExtensionValue("detection", NumberUtils.toFloat(detection));
            cnvyRow.addRowExtensionValue("detectionMode", NumberUtils.toFloat(detectionMode));

        } else if (StringUtils.equals(snsnTagCd, SnsnTagCd.GEN_SETUP_DETECTION_MODE)) {

            // 감지 범위 변경 요청인 경우의 변환 - 위와 비슷하지만 다름
            String detectionMode = cnvyRow.getFirstGenlSetupData().getSetupVal();
            String detection = deviceDao.getSpotDevGenSetupVal(svcTgtSeq, spotDevSeq, SnsnTagCd.GEN_SETUP_DETECTION);

            cnvyRow.getFirstGenlSetupData().setSnsnTagCd(SnsnTagCd.GEN_SETUP_DETECTION_MODE);
            cnvyRow.addRowExtensionValue("detection", NumberUtils.toFloat(detection));
            cnvyRow.addRowExtensionValue("detectionMode", NumberUtils.toFloat(detectionMode));

        } else if (StringUtils.equals(snsnTagCd, SnsnTagCd.GEN_SETUP_DETECTION_SCHEDULE)) {

            // 예약감지ON/OFF 요청인 경우의 변환
            // 감시모드 스케쥴 설정	(0x01)
            // 스케쥴 미 설정			(0x00) – 스케쥴 리셋 용도
            // 스케쥴 미 설정			(0x04) – 기존설정유지
            // 녹화모드 스케쥴 설정	(0x05) – 기존설정유지

            String scheduled = cnvyRow.getFirstGenlSetupData().getSetupVal();
            cnvyRow.setGenlSetupDatas(null);

            ArrayList<SclgSetupData> sclgSetupDataList = getSclgSetupDataList(svcTgtSeq, spotDevSeq, SnsnTagCd.SCH_SETUP_DETECTION);    // 감지 스케줄
            cnvyRow.setSclgSetupDatas(sclgSetupDataList);

            String detectionMode = deviceDao.getSpotDevGenSetupVal(svcTgtSeq, spotDevSeq, SnsnTagCd.GEN_SETUP_DETECTION_MODE);

            cnvyRow.setFirstSclgSetupDataSnsnTagCd(SnsnTagCd.SCH_SETUP_DETECTION);
            cnvyRow.addRowExtensionValue("scheduled", StringUtils.equals(scheduled, "1") ? 1 : 0);
            cnvyRow.addRowExtensionValue("detectionMode", NumberUtils.toFloat(detectionMode));

        } else if (StringUtils.equals(snsnTagCd, SnsnTagCd.SCH_SETUP_DETECTION)) {

            // 예약감지 요청인 경우의 변환

            cnvyRow.setGenlSetupDatas(null);

            String detectionMode = deviceDao.getSpotDevGenSetupVal(svcTgtSeq, spotDevSeq, SnsnTagCd.GEN_SETUP_DETECTION_MODE);

            cnvyRow.setFirstSclgSetupDataSnsnTagCd(SnsnTagCd.SCH_SETUP_DETECTION);
            cnvyRow.addRowExtensionValue("scheduled", 1);
            cnvyRow.addRowExtensionValue("detectionMode", NumberUtils.toFloat(detectionMode));

        } else if (StringUtils.equals(snsnTagCd, SnsnTagCd.GEN_SETUP_RECORD_SCHEDULE)) {

            // 예약녹화ON/OFF 요청인 경우의 변환
            // 녹화모드 스케쥴 설정	(0x02)
            // 스케쥴 미 설정			(0x00) – 스케쥴 리셋 용도
            // 스케쥴 미 설정			(0x06) – 기존설정유지
            // 녹화모드 스케쥴 설정	(0x07) – 기존설정유지

            String scheduled = cnvyRow.getFirstGenlSetupData().getSetupVal();
            cnvyRow.setGenlSetupDatas(null);

            ArrayList<SclgSetupData> sclgSetupDataList = getSclgSetupDataList(svcTgtSeq, spotDevSeq, SnsnTagCd.SCH_SETUP_RECORD);    // 녹화 스케줄
            cnvyRow.setSclgSetupDatas(sclgSetupDataList);

            String recordMode = deviceDao.getSpotDevGenSetupVal(svcTgtSeq, spotDevSeq, SnsnTagCd.GEN_SETUP_RECORD_MODE);

            cnvyRow.setFirstSclgSetupDataSnsnTagCd(SnsnTagCd.SCH_SETUP_RECORD);
            cnvyRow.addRowExtensionValue("scheduled", StringUtils.equals(scheduled, "1") ? 2 : 0);
            cnvyRow.addRowExtensionValue("modeRec", NumberUtils.toFloat(recordMode));

        } else if (StringUtils.equals(snsnTagCd, SnsnTagCd.SCH_SETUP_RECORD)) {

            // 예약녹화 요청인 경우의 변환
            cnvyRow.setGenlSetupDatas(null);

            String recordMode = deviceDao.getSpotDevGenSetupVal(svcTgtSeq, spotDevSeq, SnsnTagCd.GEN_SETUP_RECORD_MODE);

            cnvyRow.setFirstSclgSetupDataSnsnTagCd(SnsnTagCd.SCH_SETUP_RECORD);
            cnvyRow.addRowExtensionValue("scheduled", 2);
            cnvyRow.addRowExtensionValue("modeRec", NumberUtils.toFloat(recordMode));

        } else if (StringUtils.equals(snsnTagCd, SnsnTagCd.GEN_SETUP_RECORD_MODE)) {

            // 녹화모드 설정 요청인 경우

            String recordMode = cnvyRow.getFirstGenlSetupData().getSetupVal();
            cnvyRow.setGenlSetupDatas(null);

            ArrayList<SclgSetupData> sclgSetupDataList = getSclgSetupDataList(svcTgtSeq, spotDevSeq, SnsnTagCd.SCH_SETUP_RECORD);    // 녹화 스케줄
            cnvyRow.setSclgSetupDatas(sclgSetupDataList);

            cnvyRow.setFirstSclgSetupDataSnsnTagCd(SnsnTagCd.SCH_SETUP_RECORD);
            cnvyRow.addRowExtensionValue("scheduled", 2);
            cnvyRow.addRowExtensionValue("modeRec", NumberUtils.toFloat(recordMode));


        } else if (StringUtils.equals(snsnTagCd, SnsnTagCd.GEN_SETUP_SAVE_MODE)) {

            // 저장매체 설정 요청인 경우
            String saveMode = cnvyRow.getFirstGenlSetupData().getSetupVal();
            cnvyRow.addRowExtensionValue("saveMode", NumberUtils.toInt(saveMode, 0));

            ArrayList<SpotDev> spotDevList = deviceDao.getMemberSpotDev(unitSvcCd, mbrSeq, null, svcTgtSeq, spotDevSeq);
            if (CollectionUtils.isNotEmpty(spotDevList)) {

                SpotDev spotDev = spotDevList.get(0);

                log.debug("## spotDev for UCLOUD : " + spotDev);

                // ucloud 저장인 경우
                if (StringUtils.equals(saveMode, "2")) {

                    String mbrId = spotDev.getMbrId();
                    String credentialId = spotDev.getCredentialId();
                    String saId = spotDev.getSvcContId();            // 널임
                    String cameraSaid = spotDev.getSpotDevId();
                    String devNm = spotDev.getSpotDevNm();

                    // UCloud 실제연동
                    HashMap<String, Object> ucloudResult = checkUCloudAccessToken(svcTgtSeq, spotDevSeq, mbrId, credentialId, saId, cameraSaid);
                    String ucloudResultCode = MapUtils.getString(ucloudResult, "resultCode");
                    log.debug("## ucloudResult : " + ucloudResult);

                    cnvyRow.addRowExtensionValue("ucloudResult", ucloudResultCode);                                        // 결과 코드
                    cnvyRow.addRowExtensionValue("ucloudAPIKey", MapUtils.getString(ucloudResult, "apiKey"));            // 발급시 고정값
                    cnvyRow.addRowExtensionValue("ucloudAPISecret", MapUtils.getString(ucloudResult, "apiSecret"));            // 발급시 고정값
                    cnvyRow.addRowExtensionValue("ucloudToken", MapUtils.getString(ucloudResult, "authToken"));            // 수시 갱신
                    cnvyRow.addRowExtensionValue("ucloudSecret", MapUtils.getString(ucloudResult, "authTokenSecret"));    // 수시 갱신
                    cnvyRow.addRowExtensionValue("ucloudPath", devNm);
                }
            }
        }

        return itgCnvyData;
    }

    /**
     * 디바이스 연결상태 체크 메쏘드
     *
     * @param unitSvcCd 단위 서비스 코드
     */
    @Async
    public void checkDeviceConnStatus(String unitSvcCd) {

        unitSvcCd = StringUtils.defaultIfBlank(unitSvcCd, "001");
        ArrayList<HashMap<String, Long>> connList = null;
        try {

            connList = deviceDao.getNotCheckClosedSpotDevConnBas(CONN_EXPIRE_TIME_MIN);
            log.debug("## checkDeviceConnStatus : count is {}", connList.size());

            for (HashMap<String, Long> conn : connList) {
                log.debug("## checkDeviceConnStatus conn : " + conn);
                checkDeviceCloseEvent(unitSvcCd, conn.get("svcTgtSeq"), conn.get("spotDevSeq"));
            }

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * 단일 디바이스 접속 종료 이벤트 처리 메쏘드
     *
     * @param unitSvcCd  단위 서비스 코드
     * @param svcTgtSeq  서비스 대상 일련번호
     * @param spotDevSeq 현장 장치 일련번호
     */
    private void checkDeviceCloseEvent(String unitSvcCd, long svcTgtSeq, long spotDevSeq) {

        try {

            log.debug("## checkDeviceCloseEvent : {}, {}", svcTgtSeq, spotDevSeq);

            // 1. Push 발송
            ArrayList<SpotDev> spotDevList = deviceDao.getSpotDev(unitSvcCd, null, svcTgtSeq, spotDevSeq);

            if ("Y".equals(env.getProperty("kpns.kafka.useyn"))) {
                log.debug("## checkDeviceCloseEvent sendDeviceCloseEventPush Count : {}", CollectionUtils.size(spotDevList));
                for (SpotDev spotDev : spotDevList) {
                    String devUUID = spotDev.getDevUUID();
                    String devNm = spotDev.getSpotDevNm();
                    userService.sendDeviceCloseEventPush(unitSvcCd, svcTgtSeq, spotDevSeq, devUUID, devNm);
                }
            }

            // 2. 체크 플래그 처리
            deviceDao.updateSpotDevConnBas(svcTgtSeq, spotDevSeq, null, "Y");

        } catch (Exception e) {

            log.warn(e.getMessage(), e);
        }
    }


    /**
     * 디바이스로 부터 설정 정보를 직접 조회하는 메쏘드
     *
     * @param <V>
     * @param svcCode     서비스 코드
     * @param spotDevList 설정 정보 조회 대상 SpotDev
     */
//	private void retvDeviceConfig(final String svcCode, List<SpotDev> spotDevList, boolean includeDetail, final String[] snsnTagCds, DeferredResult<SpotDevRetvReslt> deferredResult) {
//
//		List<SpotDev> onlineSpotDevs = getOnlineSpotDevs(spotDevList, includeDetail);
//
//
//		UUID uuid = UUID.randomUUID();
//
//		int requestCount = onlineSpotDevs.size() * snsnTagCds.length;
//
//		log.debug("### Online Spot Devs / Total : {} / {} ", onlineSpotDevs.size(), spotDevList.size());
//
//		final DefWrapperRetvReslts defWrapperRetvReslts = new DefWrapperRetvReslts(requestCount, deferredResult);
//		defWrapperRetvReslts.setSpotDevs(spotDevList);
//
//		drService.addDefRetvReslts(uuid, defWrapperRetvReslts);
//
//		int i = 1;
//
//		for(final SpotDev spotDev : onlineSpotDevs) {
//
//			for (final String snsnTagCd : snsnTagCds) {
//				final String threadNum = String.format("%03d", i);
//
//				final String uuidString = uuid.toString() + "-" + threadNum;
//
//				executor.execute(new Runnable() {
//
//					@Override
//					public void run() {
//						SpotDevRetv spotDevRetv = new SpotDevRetv();
//
//						spotDevRetv.setUnitSvcCd(env.getServiceCode(svcCode));
//						spotDevRetv.setTransacId(uuidString);
//						spotDevRetv.addInclSpotDevKey(new SpotDevKey(spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq()));
//						spotDevRetv.addCmdData(new CmdData(snsnTagCd));
//						log.debug("### Sending device retrieve request with parameters : {}, {}, {}", spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq(), snsnTagCd);
//						ecService.sendRetrieveToEC(spotDevRetv);
//					}
//
//				});
//
//				i++;
//			}
//		}
//
//	}
    public <V> void retvDeviceConfig(final String svcCode, List<SpotDev> spotDevList, boolean includeDetail, RetvCallable<V> callable, RetvCallback<V> callback) {

        if (includeDetail) {
            for (SpotDev spotDev : spotDevList) {
                setIntegratedDataConfig(svcCode, spotDev);
            }
        }


        List<SpotDev> onlineSpotDevs = getOnlineSpotDevs(spotDevList, includeDetail);

        final String[] snsnTagCds = callback.getSnsnTagCds();

        final UUID uuid = UUID.randomUUID();

        int requestCount = onlineSpotDevs.size() * snsnTagCds.length;

        log.debug("### Online Spot Devs / Total : {} / {} ", onlineSpotDevs.size(), spotDevList.size());

        callable.setUUID(uuid);
        callable.setRequestCount(requestCount);
        callable.setSpotDevs(spotDevList);
        callable.setRetvCallback(callback);

        resltService.addRetvCallable(uuid, callable);

        if (spotDevList.size() == 0) {
            resltService.addSpotDevRetvReslt(uuid, null);
        }

        int i = 1;

        for (final SpotDev spotDev : onlineSpotDevs)
            for (final String snsnTagCd : snsnTagCds) {
                final String threadNum = String.format("%03d", i);

                final String uuidString = uuid.toString() + "-" + threadNum;

                executor.execute(new Runnable() {

                    @Override
                    public void run() {
                        SpotDevRetv spotDevRetv = new SpotDevRetv();
                        resltService.addStartTime(uuidString);

                        spotDevRetv.setUnitSvcCd(env.getServiceCode(svcCode));
                        spotDevRetv.setTransacId(uuidString);
                        spotDevRetv.addInclSpotDevKey(new SpotDevKey(spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq()));
                        spotDevRetv.addCmdData(new CmdData(snsnTagCd));
                        ObjectMapper mapper = new ObjectMapper();
                        String json;
                        try {
                            json = mapper.writeValueAsString(spotDevRetv);
                            log.debug("### Sending device retrieve request with json : {}", json);
                        } catch (JsonProcessingException e) {
                        }
                        ecService.sendRetrieveToEC(spotDevRetv);
                    }

                });

                i++;
            }

    }

    /**
     * 디바이스로 부터 설정 정보를 직접 조회하는 메쏘드
     * 조회 요청은 비동기 요청이기 때문에 주기적으로 결과 Report 도착을 반복 확인해야 함
     *
     * @param svcCode         서비스 코드
     * @param spotDevList     설정 정보 조회 대상 SpotDev
     * @param occrId          조회 제어 발생자 아이디
     * @param snsnTagCd       센싱 태그 코드
     * @param retryCount      조회 결과 Report 도착 확인 재시도 수
     * @param sleepTimeMillis 조회 결과 Report 도착 확인 간격 sleep time
     * @return 디바이스 마스터 정보. SpotDevRetvReslt 객체
     */
    private SpotDevRetvReslt[] getDeviceSetupDirect(String svcCode, ArrayList<SpotDev> spotDevList, String occrId, String snsnTagCd, int retryCount, int sleepTimeMillis) {

        // 예외처리
        if (CollectionUtils.isEmpty(spotDevList)) {
            return null;
        }

        long startTime = System.nanoTime();
        List<SpotDev> onlineSpotDev = getOnlineSpotDevs(spotDevList, true);
        long endTime = System.nanoTime();

        long diff = endTime - startTime;
        log.debug("Elapsed time to get online devices of {} : {} milliseconds", spotDevList.size(), diff / 1000000);

        // 2. 트랜잭션 생성 및 조회 명령
        HashMap<Integer, String> transactionIdMap = new HashMap<Integer, String>();
        int onlineSpotDevCount = onlineSpotDev.size();

        for (int i = 0; i < onlineSpotDevCount; i++) {
            SpotDev spotDev = onlineSpotDev.get(i);
            String transactionId = setDeviceSetupReadControl(svcCode, spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq(), occrId, snsnTagCd);
            transactionIdMap.put(i, transactionId);
        }


        // 3. 트랜잭션 결과 체크
        try {
            Thread.sleep(sleepTimeMillis / 4);
        } catch (InterruptedException e) {
            log.warn(e.getMessage(), e);
        }

        SpotDevRetvReslt[] retvList = new SpotDevRetvReslt[onlineSpotDevCount];
        for (int i = 0; i < retryCount; i++) {

            for (int j = 0; j < onlineSpotDevCount; j++) {

                SpotDev spotDev = onlineSpotDev.get(j);
                String transactionId = transactionIdMap.get(j);

                log.debug("## getDeviceSetupDirect start : TransactionId[{}]", transactionId);
                if (StringUtils.isBlank(transactionId)) {
                    log.debug("## getDeviceSetupDirect skip : TransactionId[{}]", transactionId);
                    continue;    // 끝난 것은 스킵
                }

                try {

                    log.debug("## getDeviceSetupDirect : retry[{}], index[{}], svcTgtSeq[{}], spotDevSeq[{}], snsnTagCd[{}], retryCount[{}], sleepTime[{}]",
                            i, j, spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq(), snsnTagCd, retryCount, sleepTimeMillis);

                    HashMap<String, String> transactionResult = deviceDao.getTransactionResult(transactionId, null);
                    String resultJson = MapUtils.getString(transactionResult, "result");

                    // Report 가 도착해서 디비에 transac_result 테이블에서 결과를 조회할 수 있는 경우
                    // retvList 에 결과 값을 저장하고 transactionIdMap 에서 종료된 트랜잭션 삭제 처리

                    if (StringUtils.isNotEmpty(resultJson)) {
                        retvList[j] = JsonUtils.fromJson(resultJson, SpotDevRetvReslt.class);
                        transactionIdMap.remove(j);
                    }

                    log.debug("## getDeviceSetupDirectResult : {}", resultJson);

                } catch (Exception e) {

                    log.warn(e.getMessage(), e);
                }
            }

            // retryCount 만큼 시도하기 이전에 종료할 수 있는 조건
            try {

                log.debug("## getDeviceSetupDirect check : {}", transactionIdMap);

                if (MapUtils.isEmpty(transactionIdMap)) {
                    break;
                } else {
                    if (retryCount > 5) {
                        Thread.sleep(sleepTimeMillis);
                    } else {
                        Thread.sleep(sleepTimeMillis / 2);
                    }
                }
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
            }
        }


        // 4. 장치 접속 상태 업데이트
        List<SpotDev> spotDevs = new ArrayList<SpotDev>();

        for (int i = 0; i < retvList.length; i++) {
            if (retvList[i] != null) {
                SpotDev spotDev = retvList[i].getFirstSpotDev();
                spotDevs.add(spotDev);
            }
        }

        SpotDevUdate updateData = new SpotDevUdate();
        updateData.setSpotDevs(spotDevs);
        setDeviceOnlineStatus(updateData);


        // 5. 종료되지 않은 트랜잭션에 대해 오프라인 처리
        log.debug("## getDeviceSetupDirect transaction result : " + transactionIdMap);
        if (MapUtils.isNotEmpty(transactionIdMap)) {
            List<SpotDev> faildSpotDevs = new ArrayList<SpotDev>();

            // 처리되지 않은 트랜잭션이 남아있는 경우
            Iterator<Integer> it = transactionIdMap.keySet().iterator();
            while (it.hasNext()) {
                int index = it.next();
                SpotDev spotDev = onlineSpotDev.get(index);
                spotDev.setSessnId(null);

                faildSpotDevs.add(spotDev);
                log.debug("## getDeviceSetupDirect offline list : index[{}], svcTgtSeq[{}], spotDevSeq[{}]", index, spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq());
            }

            SpotDevUdate faildUpdateData = new SpotDevUdate();
            faildUpdateData.setSpotDevs(faildSpotDevs);
            setDeviceOnlineStatus(faildUpdateData);
        }

        return retvList;
    }

    private List<SpotDev> getOnlineSpotDevs(List<SpotDev> spotDevList, final boolean includeDetail) {
        List<SpotDev> onlineSpotDev = new ArrayList<>();

        CompletionService<SpotDev> taskCompletionService = new ExecutorCompletionService<SpotDev>(executor);

        for (final SpotDev spotDev : spotDevList) {
            spotDev.addSpotDevDtl(SpotDevItemNm.CON_STAT, SpotDevItemVal.CON_STAT_OFFLINE);
            taskCompletionService.submit(new Callable<SpotDev>() {

                @Override
                public SpotDev call() throws Exception {
                    log.debug("### Getting Online Status of Device {}", spotDev.getSpotDevId());
                    long startTime = System.nanoTime();
                    SpotDev spotDevOnline = ecService.getOnlineSpotDev(spotDev, includeDetail);
                    long endTime = System.nanoTime();
                    long diff = endTime - startTime;
                    log.debug("### Elapsed time to get an online devices : {} milliseconds", diff / 1000000);

                    return spotDevOnline;
                }
            });
        }


        for (int i = 0; i < spotDevList.size(); i++) {
            SpotDev spotDev = null;
            try {
                Future<SpotDev> spotDevFuture = taskCompletionService.take();
                spotDev = spotDevFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                spotDev = null;
            }

            if (spotDev != null) {

                for (Iterator<SpotDevDtl> iterator = spotDev.getSpotDevDtls().iterator(); iterator.hasNext(); ) {
                    SpotDevDtl spotDevDtl = iterator.next();
                    if (spotDevDtl.getAtribNm().equals("conStat"))
                        iterator.remove();
                }
                spotDev.addSpotDevDtl(SpotDevItemNm.CON_STAT, SpotDevItemVal.CON_STAT_ONLINE);

                log.debug("### Online device : {}", spotDev.getSpotDevId());
                onlineSpotDev.add(spotDev);
            }
        }

        return onlineSpotDev;

    }

    public boolean isUpgradingDeviceFirmware(SpotDev spotDev) {
        String status = getDeviceDetailValue(spotDev.getSvcTgtSeq(), spotDev.getSpotDevSeq(), SpotDevItemNm.UPGRADE_STATUS);

        return !status.equals(SpotDevItemVal.UPGRADE_STATUS_INIT);
    }

    /**
     * Verify status of camera enrollment.
     *
     * @param authTokenString
     * @param mac       Device MAC of NIC.
     * @return {@link com.kt.giga.home.openapi.hcam.domain.EnrollmentStatus}
     * @throws Exception
     */
    public EnrollmentStatus getEnrollmentStatus(String authTokenString, String mac) throws Exception {
        AuthToken authToken = userService.checkLoginAuthToken(authTokenString);
        return deviceDao.getEnrollmentStatus(authToken.getUserNoLong(), mac);
    }

}
