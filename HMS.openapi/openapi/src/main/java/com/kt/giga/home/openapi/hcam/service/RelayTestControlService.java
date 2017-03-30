package com.kt.giga.home.openapi.hcam.service;

import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.common.AuthToken;
import com.kt.giga.home.openapi.hcam.HCamConstants.ErrorCode;
import com.kt.giga.home.openapi.hcam.HCamConstants.SnsnTagCd;
import com.kt.giga.home.openapi.hcam.dao.DeviceDao;
import com.kt.giga.home.openapi.hcam.dao.RelayDao;
import com.kt.giga.home.openapi.hcam.domain.RelaySession;
import com.kt.giga.home.openapi.hcam.internal.GenlSetupDataMapCallback;
import com.kt.giga.home.openapi.hcam.internal.RelayCamConnRunnable;
import com.kt.giga.home.openapi.hcam.internal.RelayChannelCreateCallable;
import com.kt.giga.home.openapi.hcam.internal.RetvCallable;
import com.kt.giga.home.openapi.service.APIException;
import com.kt.giga.home.openapi.service.ECService;
import com.kt.giga.home.openapi.vo.cnvy.CnvyRow;
import com.kt.giga.home.openapi.vo.cnvy.ItgCnvyData;
import com.kt.giga.home.openapi.vo.cnvy.SpotDevCnvyData;
import com.kt.giga.home.openapi.vo.row.ContlData;
import com.kt.giga.home.openapi.vo.spotdev.SpotDev;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 실시간 모니터링 컨트롤 서비스
 *
 * @author
 */
@Service("HCam.RelayTestControlService")
public class RelayTestControlService {

    /**
     * 로거
     */
    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * OpenAPI 환경 프라퍼티
     */
    @Autowired
    private APIEnv env;

    /**
     * 실시간 모니터링 매퍼 인터페이스
     */
    @Autowired
    private RelayDao relayDao;

    /**
     * 사용자 서비스
     */
    @Autowired
    private UserService userService;

    /**
     * 디바이스 매퍼 인터페이스
     */
    @Autowired
    private DeviceDao deviceDao;

    /**
     * 릴레이 서버 연동 서비스
     */
    @Autowired
    private RelayService relayService;

    /**
     * 앱 초기 실행 서비스
     */
    @Autowired
    private InitService initService;

    /**
     * 디바이스 서비스
     */
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    /**
     * EC 연동 서비스
     */
    @Autowired
    private ECService ecService;

    /**
     * 실시간 모니터링 세션 만료 시간 (분)
     */
    @Value("${relay.defaultSessionMinutes}")
    private int defaultSessionMinute;

    /**
     * 릴레이 서버 연동 시간 포맷
     */
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public Map<String, String> getIntegratedRelaySession(AuthToken loginAuthToken, List<SpotDev> spotDevList, final String devUUID) throws Exception {
        try {
            Map<String, String> result = new HashMap<>();

            for (SpotDev spotDev : spotDevList) {
                if (deviceService.isUpgradingDeviceFirmware(spotDev)) {
                    result.put("result", "-100");
                    return result;
                }
            }

            // 1. 릴레이 아이디/패스워드 체크 - 기존에 있는 경우 기존값 사용, 없는 경우 신규 발급
            int[] relayDeviceIdPw = checkRelayDeviceIdPw(devUUID);
            int relayDeviceId = relayDeviceIdPw[0];
            int relayDevicePw = relayDeviceIdPw[1];

            // 2. 릴레이 서버에 채널 생성 요청
            String[] nextExpireTime = nextExpireTime();

            Callable<Map<String, String>> callable = getGenlSetupConfig(spotDevList);
            Future<Map<String, String>> retvResultFuture = executor.submit(callable);

            RelayChannelCreateCallable relayCallable = new RelayChannelCreateCallable();

            relayCallable.setRelayService(relayService);
            relayCallable.setDevUUID(devUUID);
            relayCallable.setAuthToken(loginAuthToken);
            relayCallable.setRelayDeviceId(relayDeviceId);
            relayCallable.setRelayDevicePw(relayDevicePw);
            relayCallable.setExpireTime(nextExpireTime[0]);
            relayCallable.setMaxExpireTime(nextExpireTime[1]);

            Future<Map<String, String>> relaySessionFuture = executor.submit(relayCallable);

            while (true) {

                // If retrieving camera config first comes
                if (retvResultFuture.isDone()) {
                    log.debug("### Camera Setup Retrieval arrived first");
                    Map<String, String> retvMap = retvResultFuture.get();

                    if (retvMap.get("conStat").equals("1")) {
                        result.put("result", retvMap.get("result"));
                        result.put("videoActive", retvMap.get("privacy"));
                    } else {
                        result.put("result", "-1");
                    }

                    if (!result.get("result").equals("0") || retvMap.get("privacy").equals("0")) {
                        relaySessionFuture.cancel(true);
                        requestRelayToDeleteSession(devUUID);
                    } else {
                        Map<String, String> relayMap = relaySessionFuture.get();

                        result.put("sessionId", relayMap.get("sessionId"));
                        result.put("rtspUrl", relayMap.get("rtspUrl"));
                        result.put("key", relayMap.get("key"));
                        result.put("iv", relayMap.get("iv"));
                        result.put("expireTime", Integer.toString(defaultSessionMinute));
                        result.put("maxExpireTime", StringUtils.defaultIfBlank(initService.getProperty("001", "dev.relay.maxExpireTime"), "60"));
                        result.put("status", relayMap.get("status"));

                        if (StringUtils.equalsIgnoreCase(relayMap.get("status"), "CREATE")) {
                            int port = NumberUtils.toInt(relayMap.get("port"), 0);
                            String ip = relayMap.get("ip");
                            String iv = relayMap.get("iv");
                            String key = relayMap.get("key");

                            RequestCamConnRelay(devUUID, port, ip, iv, key, relayDeviceId, relayDevicePw);
                        }

                    }
                    break;
                }

                // If retrieving relay config first comes
                else if (relaySessionFuture.isDone()) {
                    log.debug("### Relay creation arrived first");
                    Map<String, String> relayMap = relaySessionFuture.get();

                    result.put("sessionId", relayMap.get("sessionId"));
                    result.put("rtspUrl", relayMap.get("rtspUrl"));
                    result.put("key", relayMap.get("key"));
                    result.put("iv", relayMap.get("iv"));
                    result.put("expireTime", Integer.toString(defaultSessionMinute));
                    result.put("maxExpireTime", StringUtils.defaultIfBlank(initService.getProperty("001", "dev.relay.maxExpireTime"), "60"));
                    result.put("status", relayMap.get("status"));

                    if (relayMap.get("status").equals("JOIN")) {
                        result.put("result", "0");
                        result.put("videoActive", "1");
                        retvResultFuture.cancel(true);
                    } else {
                        Map<String, String> retvMap = retvResultFuture.get();

                        if (retvMap.get("conStat").equals("1")) {
                            result.put("result", retvMap.get("result"));
                            result.put("videoActive", retvMap.get("privacy"));

                            if (retvMap.get("privacy").equals("0")) {
                                requestRelayToDeleteSession(devUUID);
                            } else if (StringUtils.equalsIgnoreCase(relayMap.get("status"), "CREATE")) {
                                int port = NumberUtils.toInt(relayMap.get("port"), 0);
                                String ip = relayMap.get("ip");
                                String iv = relayMap.get("iv");
                                String key = relayMap.get("key");

                                RequestCamConnRelay(devUUID, port, ip, iv, key, relayDeviceId, relayDevicePw);
                            }

                        } else {
                            // Camera does not responding
                            requestRelayToDeleteSession(devUUID);
                            result.put("result", "-1");
                        }
                    }
                    break;
                }

                Thread.sleep(100);
            }

            return result;
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof APIException) {
                log.debug(cause.getMessage());
                throw (APIException) cause;
            } else {
                throw e;
            }

        }

    }

    private void requestRelayToDeleteSession(final String devUUID) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    relayService.checkAndDeleteRelayChannel(devUUID, false);
                } catch (Exception e) {
                    log.info("Failed to delete relay session.");
                    e.fillInStackTrace();
                }
            }
        });
    }

    public void RequestCamConnRelay(String devUUID, int port, String ip, String iv, String key, int relayDeviceId, int relayDevicePw) throws APIException {
        HashMap<String, Long> spotDevKey = relayDao.getSpotDevKey(devUUID);

        if (spotDevKey == null || spotDevKey.isEmpty())
            throw new APIException(String.format("%s not found", devUUID), HttpStatus.NOT_FOUND, ErrorCode.INVALID_DEVICE);

        long svcTgtSeq = spotDevKey.get("svcTgtSeq");
        long spotDevSeq = spotDevKey.get("spotDevSeq");

        RelayCamConnRunnable runnable = new RelayCamConnRunnable();
        runnable.setSvcTgtSeq(svcTgtSeq);
        runnable.setSpotDevSeq(spotDevSeq);
        runnable.setDeviceDao(deviceDao);
        runnable.setECService(ecService);
        runnable.setIP(ip);
        runnable.setIV(iv);
        runnable.setKey(key);
        runnable.setPort(port);
        runnable.setRelayDeviceId(relayDeviceId);
        runnable.setRelayDevicePw(relayDevicePw);

        executor.execute(runnable);
    }

    /**
     * 실시간 모니터링 세션 연장 요청 처리 메쏘드
     *
     * @param authToken 로그인 인증 토큰
     * @param devUUID   홈 카메라 식별자
     * @param sessionId 실시간 모니터링 세션 아이디
     * @return 릴레이 세션정보 객체
     * @throws Exception
     */
    public RelaySession refreshRelaySessionExpireTime(String authToken, String devUUID, String sessionId) throws Exception {

        userService.checkLoginAuthToken(authToken);

        relayService.renewRelayMonitoringTime(sessionId, nextExpireTime()[0]);
        RelaySession relaySession = new RelaySession();
        relaySession.setExpireTime(defaultSessionMinute);

        return relaySession;
    }

    /**
     * 릴레이 디바이스 아이디/패스워드 체크 메쏘드
     * 기존에 발급된 아이디/패스워드가 있으면 그대로 사용, 없으면 신규 생성
     *
     * @param devUUID 홈 카메라 식별자
     * @return 릴레이 디바이스 아이디/패스워드
     */
    public int[] checkRelayDeviceIdPw(String devUUID) {

        HashMap<String, Integer> relayDeviceIdPw = relayDao.getRelayDeviceIdPw(devUUID);

        if (relayDeviceIdPw == null) {
            relayDeviceIdPw = new HashMap<>();
        }

        if (relayDeviceIdPw.isEmpty()) {
            int relayDeviceId = relayDao.createRelayDeviceId();
            int relayDevicePw = RandomUtils.nextInt(1000, 9999);

            relayDao.insertRelayDeviceIdPw(devUUID, relayDeviceId, relayDevicePw);
            return new int[]{relayDeviceId, relayDevicePw};
        } else {
            return new int[]{relayDeviceIdPw.get("relayDeviceId"), relayDeviceIdPw.get("relayDevicePw")};
        }
    }

    /**
     * 실시간 모니터링 세션 만료시간 계산 메쏘드
     * 릴레이서버에서 사용하는 세계 기준 시간에 맞춰야 함. 현재 한국 시간에서 9시간을 뺀다.
     *
     * @return 실시간 모니터링 세션 만료시간, 최대 연장 가능 시간의 사이즈 2 의 배열
     */
    public String[] nextExpireTime() {

        long currentTimeMillis = System.currentTimeMillis();
        long currentUTCTimeMillis = currentTimeMillis - TimeUnit.HOURS.toMillis(9);

        long nextExpireTimeMillis = currentUTCTimeMillis + TimeUnit.MINUTES.toMillis(defaultSessionMinute);
        long nextMaxExtensionTimeMillis = currentUTCTimeMillis + TimeUnit.MINUTES.toMillis(NumberUtils.toLong(StringUtils.defaultIfBlank(initService.getProperty("001", "dev.relay.maxExpireTime"), "60")));

        String nextExpireTime = dateFormat.format(new Date(nextExpireTimeMillis));
        String nextMaxExtensionTime = dateFormat.format(new Date(nextMaxExtensionTimeMillis));

        log.debug("## nextExpireTime currentTimeMillis[{}], currentUTCTimeMillis[{}], nextExpireTime[{}], nextMaxExtensionTime[{}]",
                currentTimeMillis, currentUTCTimeMillis, nextExpireTime, nextMaxExtensionTime);

        return new String[]{nextExpireTime, nextMaxExtensionTime};
    }

    /**
     * EC 연동 메쏘드. 홈 카메라 RTSP 연결 요청
     * TODO 다른 제어 흐름과 다르게 별도 흐름을 가지고 있어 트랜잭션 히스토리 테이블에 쌓이지 않음
     *
     * @param svcTgtSeq     서비스 대상 일련번	호 (EC 처리용)
     * @param spotDevSeq    현장장치 일련번호 (EC 처리용)
     * @param ip            접속할 릴레이서버 아이피
     * @param port          접속할 릴레이서버 포트
     * @param relayDeviceId 릴레이서버 접속 인증에 사용할 아이디
     * @param relayDevicePw 릴레이서버 접속 인증에 사용랄 패스워드
     * @param keyBase64     영상 암호화에 사용할 KEY
     * @param ivBase64      영상 암호화에 사용할 IV
     */
    private void controlHomeCameraRtspCon(long svcTgtSeq, long spotDevSeq, String ip, int port, int relayDeviceId, int relayDevicePw, String keyBase64, String ivBase64) {

        ItgCnvyData itgCnvyData = new ItgCnvyData();

        SpotDevCnvyData spotDevCnvyData = new SpotDevCnvyData();
        spotDevCnvyData.setSvcTgtSeq(svcTgtSeq);
        spotDevCnvyData.setSpotDevSeq(spotDevSeq);

        CnvyRow cnvyRow = new CnvyRow();

        cnvyRow.setTransacId(deviceDao.createTransactionId());

        ContlData contlData = new ContlData();
        contlData.setSnsnTagCd(SnsnTagCd.RTIME_RELAY_SESSION_CONN);
        cnvyRow.addContlData(contlData);

        HashMap<String, Object> rowExtension = cnvyRow.getRowExtension();
        rowExtension.put("relayIp", ip);
        rowExtension.put("relayPort", port);
        rowExtension.put("relayDeviceId", relayDeviceId);
        rowExtension.put("relayDevicePw", relayDevicePw);
        rowExtension.put("secretKey", keyBase64);
        rowExtension.put("iV", ivBase64);
        spotDevCnvyData.getCnvyRows().add(cnvyRow);
        itgCnvyData.getSpotDevCnvyDatas().add(spotDevCnvyData);

        ecService.sendRTimeControlToEC(itgCnvyData);
    }

    /**
     * Gets genl setup config.
     *
     * @param authToken the auth token
     * @param spotDevs  SpotDevs to retrieve.
     * @return the genl setup config
     * @throws Exception the exception
     */
    public Callable<Map<String, String>> getGenlSetupConfig(List<SpotDev> spotDevs) throws Exception {

        RetvCallable<Map<String, String>> callable = new RetvCallable<>();
        callable.setTimeout(3000L);

        GenlSetupDataMapCallback callback = new GenlSetupDataMapCallback();
        callback.setAPIEnv(env);
        callback.setDeviceService(deviceService);
        callback.setRealtimeRequired(true);

        log.debug("### Retrieving Camera Information");

        deviceService.retvDeviceConfig("001", spotDevs, false, callable, callback);

        return callable;
    }
}
