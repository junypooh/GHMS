package com.kt.giga.home.infra.service.silk;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.kt.giga.home.infra.client.host.OpenApiServerInfo;
import com.kt.giga.home.infra.client.http.InfraHttpClient;
import com.kt.giga.home.infra.client.http.InfraHttpClientFactory;
import com.kt.giga.home.infra.dao.silk.OllehRtrmmbDao;
import com.kt.giga.home.infra.domain.silk.OllehRtrmmb;

/**
 * 스케줄 처리 서비스
 *
 * @author
 *
 */
@Service
public class OllehRtrmmbService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public static final String LOGIN_TYPE_CD_OLLEH = "01";
	public static final String LOGIN_TYPE_CD_QOOK = "02";
	public static final String LOGIN_TYPE_CD_SHOW = "03";
	
	public static final int PROC_RSLT_READY = 1;
	public static final int PROC_RSLT_COMPLETE = 2;
	public static final int PROC_RSLT_ERROR = 99;
	public static final int PROC_RSLT_NOTFOUND = 80;
	public static final int PROC_RSLT_FORBIDDEN = 81;
	
	public static final String RESULT_OK = "0";
	public static final String RESULT_ERROR = "-61";
	public static final String RESULT_NOTFOUND = "-62";
	
	@Autowired
	private InfraHttpClientFactory factory;
	
	@Autowired
	private OpenApiServerInfo serverInfo;
	
	@Autowired
	private OllehRtrmmbDao ollehRtrmmbDao;
	
	/**
	 * 유클라우드 탈퇴 연동 스케줄
	 */
	@Scheduled(fixedDelay=60*60*1000, initialDelay = 60000)
	public void setOllehRtrmmb() {
		OllehRtrmmb ollehRtrmmbReq = new OllehRtrmmb();
		ollehRtrmmbReq.setLoginIdTypeCd(LOGIN_TYPE_CD_OLLEH);
		ollehRtrmmbReq.setProcRslt(PROC_RSLT_READY);
		
		List<OllehRtrmmb> list = ollehRtrmmbDao.selectOllehRtrmmb(ollehRtrmmbReq);
		
		int success = 0;
		int fail = 0;
		
		for(OllehRtrmmb ollehRtrmmb : list) {
			try {
				// 1. openapi 서비스로 유클라우드 계정 삭제 연돋 요청
				InfraHttpClient infraHttpClient = factory.getInfraHttpClient(serverInfo);
				String resBody = "";
				try {
					String queryString = "";
					queryString += "?unitSvcCd=001";
					queryString += "&loginId=" + ollehRtrmmb.getLoginId();
					
					String url = serverInfo.getProtocol() + "://" 
							+ serverInfo.getHost() + ":" 
							+ serverInfo.getPort() 
							+ OllehRtrmmbCode.deleteUCloudAccount.getUrl()
							+ queryString;
					
					resBody = (String)infraHttpClient.sendForm(url, HttpMethod.DELETE, null);
				} catch (Exception e) {
					log.error(e.toString(), e.fillInStackTrace());
				}
				
				// 2. 유클라우드 게정 삭제 연동 결과 업데이트 
				Map<String, String> resMap = new Gson().fromJson(resBody, Map.class);
				
				if(!resMap.isEmpty()) {
					if(RESULT_OK.equals(resMap.get("code"))) {
						ollehRtrmmb.setProcRslt(PROC_RSLT_COMPLETE);
						ollehRtrmmbDao.updateOllehRtrmmb(ollehRtrmmb);
						success++;
					} else if(RESULT_NOTFOUND.equals(resMap.get("code"))) {
						ollehRtrmmb.setProcRslt(PROC_RSLT_NOTFOUND);
						ollehRtrmmbDao.updateOllehRtrmmb(ollehRtrmmb);
						success++;
					} else {
						ollehRtrmmb.setProcRslt(PROC_RSLT_ERROR);
						ollehRtrmmbDao.updateOllehRtrmmb(ollehRtrmmb);
						fail++;
					}
				} else {
					ollehRtrmmb.setProcRslt(PROC_RSLT_ERROR);
					ollehRtrmmbDao.updateOllehRtrmmb(ollehRtrmmb);
					fail++;
				}
			} catch(Exception e) {
				log.error(e.getMessage(), e.fillInStackTrace());
			}
		}
	}
}
