package com.kt.giga.home.openapi.hcam.service;

import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.hcam.HCamConstants.ErrorCode;
import com.kt.giga.home.openapi.hcam.domain.RelayEventHandlerRequest;
import com.kt.giga.home.openapi.hcam.domain.RelayEventHandlerResponse;
import com.kt.giga.home.openapi.service.APIException;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.springframework.xml.xpath.XPathOperations;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 릴레이 서버 연동 서비스
 *
 * @author
 */
@Service("HCam.RelayService")
public class RelayService {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * OpenAPI 환경 프라퍼티
     */
    @Autowired
    private APIEnv env;

    //	@Autowired
    private RestTemplate restTemplate;

    private String relayEventHandlerPath;

    @Autowired
    private Environment environment;

    private String[] eventHandlerProfiles = {"hcam_production", "hcam_staging", "hcam_dev"};

    @PostConstruct
    public void registRelayEventHandler() throws JAXBException {
        try {
            if (!getActiveProfilesContains(eventHandlerProfiles))
                return;

            RelayEventHandlerRequest request = new RelayEventHandlerRequest();
            String handlerURL = "http://" + this.relayEventHandlerPath;
            request.setURL(handlerURL);
            log.debug("### Registering RelayEventHandler : {}", handlerURL);

            StringWriter sw = new StringWriter();

            JAXBContext jaxbContext = JAXBContext.newInstance(RelayEventHandlerRequest.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            jaxbMarshaller.marshal(request, sw);
            RelayEventHandlerResponse registHandlerResult = postToRelayForObject(sw.toString(), RelayEventHandlerResponse.class);
            log.debug("### Result of registering relay event handler : {}", registHandlerResult.getResult());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @PreDestroy
    public void unregistRelayEventHandler() throws JAXBException {
        try {
            if (!getActiveProfilesContains(eventHandlerProfiles))
                return;

            RelayEventHandlerRequest request = new RelayEventHandlerRequest();
            log.debug("### Unregistering RelayEventHandler");
            request.setURL("");
            StringWriter sw = new StringWriter();

            JAXBContext jaxbContext = JAXBContext.newInstance(RelayEventHandlerRequest.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            jaxbMarshaller.marshal(request, sw);
            postToRelayServer(sw.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    @Autowired
    private void setEventHandlerPath(@Value("${relay.EventHandlerPath}") String eventHandlerPath, @Value("${context.port}") String port, ServletContext servletContext) throws UnknownHostException {

        String hostIP = InetAddress.getLocalHost().getHostAddress();
        this.relayEventHandlerPath = hostIP + ":" + port + servletContext.getContextPath() + eventHandlerPath;
    }

    private boolean getActiveProfilesContains(String[] profiles) {
        for (String profileName : environment.getActiveProfiles()) {
            for (String profile : profiles) {
                if (profileName.equals(profile))
                    return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

    /**
     * 릴레이서버 연동 메쏘드. 실시간 모니터링 세션 생성/조인 요청
     *
     * @param inputDeviceId  입력 디바이스 아이디. 카메라 식별자
     * @param outputDeviceId 출력 디바이스 아이디. 앱 식별자
     * @param authId         인증 아이디
     * @param authPasswd     인증 패스워드
     * @param endTime        세션 만료 시간
     * @param maxTime        세션 최대 유지 시간
     * @return 릴레이서버 연동 결과 맵
     */
    public Map<String, String> createRelayChannel(String inputDeviceId, String outputDeviceId, int authId, int authPasswd, String endTime, String maxTime) throws Exception {

        String xml;
        String version = env.getProperty("relay.api.version");

        if (StringUtils.equals(version, "0.4")) {

            // 0.4 버전
            xml = String.format(""
                    + "<CreateChannelRequest version=\"1.0\">\n"
                    + "<serviceType id=\"KT_HOME_CAM\">\n"
                    + "<input>\n"
                    + "<deviceID>%s</deviceID>\n"
                    + "<deviceType>Cam</deviceType>\n"
                    + "</input>\n"
                    + "<output>\n"
                    + "<deviceID>%s</deviceID>\n"
                    + "<deviceType>Phone</deviceType>\n"
                    + "<schedule>\n"
                    + "<end>%s</end>\n"
                    + "<maxExtensionTime>%s</maxExtensionTime>\n"
                    + "</schedule>\n"
                    + "</output>\n"
                    + "<authID>%d</authID>\n"
                    + "<authPassword>%d</authPassword>\n"
                    + "</serviceType>\n"
                    + "</CreateChannelRequest>\n", inputDeviceId, outputDeviceId, endTime, maxTime, authId, authPasswd);

        } else {

            // 0.3 버전
            xml = String.format(""
                    + "<CreateChannelRequest version=\"1.0\">\n"
                    + "<serviceType id=\"KT_HOME_CAM\">\n"
                    + "<input>\n"
                    + "<deviceID>%s</deviceID>\n"
                    + "<deviceType>Cam</deviceType>\n"
                    + "</input>\n"
                    + "<output>\n"
                    + "<deviceID>%s</deviceID>\n"
                    + "<deviceType>Phone</deviceType>\n"
                    + "<schedule>\n"
                    + "<end>%s</end>\n"
                    + "</schedule>\n"
                    + "</output>\n"
                    + "<authID>%d</authID>\n"
                    + "<authPassword>%d</authPassword>\n"
                    + "</serviceType>\n"
                    + "</CreateChannelRequest>\n", inputDeviceId, outputDeviceId, endTime, authId, authPasswd);
        }

        log.debug("## createRelayChannel version : {}, xml : {}", version, xml);

        HashMap<String, String> resultMap = new HashMap<>();

        // 릴레이서버는 에러가 자주 발생함
        Source xmlSource;
        try {
            xmlSource = postToRelayServer(xml);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            resultMap.put("result", "Relay server error : " + e.getMessage());
            return checkRelayServerError(resultMap);
        }

        XPathOperations template = new Jaxp13XPathTemplate();

        String result = template.evaluateAsString("//result", xmlSource);
        resultMap.put("result", result);

        if (StringUtils.equalsIgnoreCase(result, "success")) {
            resultMap.put("status", template.evaluateAsString("//status", xmlSource));
            resultMap.put("channelId", template.evaluateAsString("//channelID", xmlSource));
            resultMap.put("sessionId", template.evaluateAsString("//sessionID", xmlSource));
            resultMap.put("ip", template.evaluateAsString("//ip", xmlSource));
            resultMap.put("port", template.evaluateAsString("//port", xmlSource));
            resultMap.put("rtspUrl", template.evaluateAsString("//rtspURL", xmlSource));
            resultMap.put("algorithm", template.evaluateAsString("//algorithm", xmlSource));
            resultMap.put("key", template.evaluateAsString("//key", xmlSource));
            resultMap.put("iv", template.evaluateAsString("//iv", xmlSource));

        } else {
            setRelayServerError(template, xmlSource, resultMap);
        }

        log.debug("## CreateRelayChannel Result : {}", resultMap);

        return checkRelayServerError(resultMap);
    }

    /**
     * 릴레이서버 연동 메쏘드. 실시간 모니터링 세션 연장 처리
     *
     * @param sessionId 실시간 모니터링 세션 아이디
     * @param endTime   실시간 모니터링 세션 만료 시간
     * @return 릴레이서버 연동 결과 맵
     * @throws Exception
     */
    public HashMap<String, String> renewRelayMonitoringTime(String sessionId, String endTime) throws Exception {

        String xml = String.format(""
                + "<RenewMonitoringTimeRequest version=\"1.0\">\n"
                + "<sessionID>%s</sessionID>\n"
                + "<schedule>\n"
                + "<end>%s</end>\n"
                + "</schedule>\n"
                + "</RenewMonitoringTimeRequest>\n", sessionId, endTime);

        Source xmlSource = postToRelayServer(xml);
        XPathOperations template = new Jaxp13XPathTemplate();

        HashMap<String, String> resultMap = new HashMap<>();
        String result = template.evaluateAsString("//result", xmlSource);
        resultMap.put("result", result);

        if (StringUtils.equalsIgnoreCase(result, "success")) {
            resultMap.put("sessionId", template.evaluateAsString("/RenewMonitoringTimeResponse/sessionID", xmlSource));
        } else {
            setRelayServerError(template, xmlSource, resultMap);
        }

        log.debug("## RenewMonitoringTimeRequest Result : {}", resultMap);

        return checkRelayServerError(resultMap);
    }

    /**
     * 릴레이서버 채널 삭제하는 메소드
     *
     * @param inputDeviceId 입력 디바이스 아이디. 카메라 식별자
     * @param forcibly      강제 삭제 여부
     * @return 릴레이서버 연동 결과 맵
     * @throws Exception
     */
    public HashMap<String, String> checkAndDeleteRelayChannel(String inputDeviceId, boolean forcibly) throws Exception {

        String xml = String.format(""
                + "<DeleteChannelRequest version=\"1.0\">\n"
                + "<inputDeviceID>%s</inputDeviceID>\n"
                + "<mode>%s</mode>\n"
                + "</DeleteChannelRequest>\n", inputDeviceId, (forcibly) ? "Delete" : "SessionChecking");

        HashMap<String, String> resultMap = new HashMap<>();

        // 릴레이서버는 에러가 자주 발생함
        Source xmlSource;
        try {
            xmlSource = postToRelayServer(xml);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            resultMap.put("result", "Relay server error : " + e.getMessage());
            return checkRelayServerError(resultMap);
        }

        XPathOperations template = new Jaxp13XPathTemplate();
        String result = template.evaluateAsString("//result", xmlSource);
        String errorCode = template.evaluateAsString("//errorCode", xmlSource);
        if (result.equals("error") && errorCode.equals("116"))
            result = "success";

        resultMap.put("result", result);


        if (!StringUtils.equalsIgnoreCase(result, "success")) {
            setRelayServerError(template, xmlSource, resultMap);
        }

        if (result.equals("error") && errorCode.equals("126"))
            throw new APIException(resultMap.get("description"), HttpStatus.FORBIDDEN, ErrorCode.NOT_CONTROLLABLE_DEVICE);

        log.debug("## checkAndDeleteRelayChannel Result : {}", resultMap);

        return checkRelayServerError(resultMap);
    }

    /**
     * 릴레이서버 연동 결과 에러 XML 에 대한 맵 구성 메쏘드
     *
     * @param template  XPath 처리 템플릿
     * @param xmlSource 연동 결과 에러 XML 소스
     * @param resultMap 에러 내용을 구성할 맵
     * @return 에러 내용을 구성한 맵
     */
    public HashMap<String, String> setRelayServerError(XPathOperations template, Source xmlSource, HashMap<String, String> resultMap) {

        resultMap.put("errorCode", "-22");
        resultMap.put("detailedErrorCode", template.evaluateAsString("//errorCode", xmlSource));
        resultMap.put("status", template.evaluateAsString("//status", xmlSource));
        resultMap.put("description", template.evaluateAsString("//description", xmlSource));

        return resultMap;
    }

    @SuppressWarnings("deprecation")
    private RestTemplate getRelayRestTemplate() {

        if (restTemplate == null) {

            try {

                int readTimeout = env.getIntProperty("relay.timeout.read");
                int connTimeout = env.getIntProperty("relay.timeout.conn");

                HttpClientParams httpClientParams = new HttpClientParams();
                httpClientParams.setConnectionManagerTimeout(connTimeout);
                httpClientParams.setSoTimeout(readTimeout);

                HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
                connectionManagerParams.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, env.getIntProperty("relay.conn.host"));
                connectionManagerParams.setMaxTotalConnections(env.getIntProperty("relay.conn.max"));
                connectionManagerParams.setLinger(0);

                MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
                connectionManager.setParams(connectionManagerParams);

                CommonsClientHttpRequestFactory factory = new CommonsClientHttpRequestFactory(new HttpClient(httpClientParams, connectionManager));
                restTemplate = new RestTemplate(factory);

            } catch (Exception e) {

                log.warn(e.getMessage(), e);
            }
        }

        return restTemplate;
    }

    /**
     * 릴레이 서버 연동 처리 메쏘드
     *
     * @param xml 요청 XML
     * @return 릴레이서버 호출 결과 XML source
     */
    public Source postToRelayServer(String xml) {

        LinkedList<String> list = getRandomRelayServer();

        DOMSource xmlResult = null;
        while (!list.isEmpty()) {
            String url = list.get(0);
            list.remove(0);

            try {
                log.info("# Send Request to Relay : {}\n{}", url, xml);
                xmlResult = getRelayRestTemplate().postForObject(url, xml, DOMSource.class);

                try {
                    StringWriter writer = new StringWriter();
                    StreamResult result = new StreamResult(writer);
                    TransformerFactory tf = TransformerFactory.newInstance();
                    Transformer transformer = tf.newTransformer();
                    transformer.transform(xmlResult, result);
                    log.info("# Recv Response from Relay : {}\n{}", url, writer.toString());
                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }

                break;
            } catch (ResourceAccessException | HttpClientErrorException e) {
                log.warn(e.getMessage(), e.fillInStackTrace());
            }
        }

        return xmlResult;
    }

    public <T> T postToRelayForObject(String xml, Class<T> clazz) {

        LinkedList<String> list = getRandomRelayServer();

        T t = null;
        while (!list.isEmpty()) {
            String url = list.get(0);
            list.remove(0);

            try {
                log.info("# Send Request to Relay : {}\n{}", url, xml);
                t = getRelayRestTemplate().postForObject(url, xml, clazz);
                log.info("# Recv Response from Relay : {}\n{}", url, t);
                break;
            } catch (ResourceAccessException | HttpClientErrorException e) {
                log.warn(e.getMessage(), e.fillInStackTrace());
            }
        }

        return t;
    }

    /**
     * 릴레이서버 연동 결과 XML 에 대한 기본 익셉션 처리 메쏘드
     *
     * @param resultMap 릴레이서버 연동 결과 XML 데이터
     * @throws Exception
     */
    public HashMap<String, String> checkRelayServerError(HashMap<String, String> resultMap) throws Exception {
        String result = resultMap.get("result");
        if (!StringUtils.equalsIgnoreCase(result, "success")) {
            String errMsg = String.format(
                    "result : %s, errorCode : %s, status : %s, description : %s",
                    result,
                    resultMap.get("errorCode"),
                    resultMap.get("status"),
                    resultMap.get("description")
            );
            throw new APIException(errMsg, HttpStatus.FORBIDDEN, ErrorCode.RELAY_SERVER_ERROR);
        }

        return resultMap;
    }

    // 릴레이서버 개수만큼의 서버IP목록을 순서 섞어서 리턴
    public LinkedList<String> getRandomRelayServer() {
        LinkedList<String> list = new LinkedList<>();
        String[] ecServerList = env.splitProperty("relay.urllist");

        for (String anEcServerList : ecServerList) {
            list.add(anEcServerList);
        }

        Collections.shuffle(list);

        return list;
    }
}
