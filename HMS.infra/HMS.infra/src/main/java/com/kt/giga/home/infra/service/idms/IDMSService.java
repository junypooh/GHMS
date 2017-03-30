package com.kt.giga.home.infra.service.idms;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.giga.home.infra.client.host.GhmsOpenApiServerInfo;
import com.kt.giga.home.infra.client.host.OpenApiServerInfo;
import com.kt.giga.home.infra.client.http.InfraHttpClient;
import com.kt.giga.home.infra.client.http.InfraHttpClientFactory;
import com.kt.giga.home.infra.dao.idms.SubscriptionDao;
import com.kt.giga.home.infra.domain.commons.HttpObjectRequest;
import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;
import com.kt.giga.home.infra.domain.idms.SubscriptionInfoRequest;
import com.kt.giga.home.infra.domain.idms.SubscriptionInfoRequestList;
import com.kt.giga.home.infra.domain.idms.SubscriptionInfoResponse;
import com.kt.giga.home.infra.domain.idms.SubscriptionManagerInfoRequest;

@Service
public class IDMSService {
	private Logger log = Logger.getLogger(this.getClass());
	
	private static String SUCCESS = "1";
	private static String FAIL = "0";
	
	@Autowired
	private InfraHttpClientFactory factory;
	
	@Autowired
	private OpenApiServerInfo serverInfo;
	
	@Autowired
	private GhmsOpenApiServerInfo ghmsServerInfo;
	
	@Autowired
	private SubscriptionDao subscriptionDao;

	@Transactional
	public void subscribes(SubscriptionInfoRequestList list) {
		for(int i = 0; i < list.getList().size(); i++) {
			subscribe((SubscriptionInfoRequest)list.getList().get(i));
		}
	}
	
	@Transactional
	public HttpObjectResponse subscribe(SubscriptionInfoRequest req) {
		
		SubscriptionInfoResponse res = new SubscriptionInfoResponse();
		
		try {
			// 1. provisioning 데이터 등록			
			subscriptionDao.insertSubscription(req);
			
			// 2. openapi 서비스로 청약 연돋 요청
			InfraHttpClient infraHttpClient = factory.getInfraHttpClient(serverInfo);
			
			try {
				// 맥 포맷 변경
				if(req.getCctvMac() == null) {
					req.setCctvMac("");
				} else {
					String mac = req.getCctvMac().trim();
					if(mac.length() == 12 && mac.indexOf(":") == -1) {
						mac = mac.substring(0, 2) + ":"
								+ mac.substring(2, 4) + ":"
								+ mac.substring(4, 6) + ":"
								+ mac.substring(6, 8) + ":"
								+ mac.substring(8, 10) + ":"
								+ mac.substring(10, 12);
						req.setCctvMac(mac);
					}
				}
				
				String url = serverInfo.getProtocol() + "://" 
						+ serverInfo.getHost() + ":" 
						+ serverInfo.getPort() 
						+ IDMSApiCode.subscribe.getUrl(); 
				
				res = (SubscriptionInfoResponse)infraHttpClient.send(url, req, new SubscriptionInfoResponse());
			} catch (Exception e) {
				log.error(e.toString(), e.fillInStackTrace());
			}
			
			// 3. 청약 연동 결과 업데이트 
			if(res != null && SUCCESS.equals(res.getResultCode())) {
				subscriptionDao.updateSubscription(req);
				
				res.setStatusCode(HttpStatus.SC_OK);
				res.setStatusText("SUCCESS");
			} else {
				res = new SubscriptionInfoResponse();
				res.setResultCode(FAIL);
			}
		} catch(Exception e) {
			log.error(e.toString(), e.fillInStackTrace());
			
			res.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			res.setStatusText(e.getMessage());
			res.setResultCode(FAIL);
			
			return res;
		}
		
		return res;
	}
	
	public HttpObjectRequest selectSubscription(SubscriptionInfoRequest req) {		
		// 1. provisioning 데이터 등록 
		SubscriptionInfoRequest res = subscriptionDao.selectSubscription(req);
		
		return res;
	}
    
    @Transactional
    public HttpObjectResponse ghmsSubscribe(SubscriptionManagerInfoRequest req) {
        
        SubscriptionInfoResponse res = new SubscriptionInfoResponse();
        
        try {
            // 1. provisioning 데이터 등록          
            subscriptionDao.insertManagerSubscription(req);

            // 2. openapi 서비스로 청약 연돋 요청
            InfraHttpClient infraHttpClient = factory.getInfraHttpClient(ghmsServerInfo);
            
            try {
            	
            	if(req.getGwMac() != null && !"".equals(req.getGwMac())) {
                    // 맥 포맷 변경
                    String mac = req.getGwMac().trim();
                	// spot_dev_id 포맷 변경
                	req.setGwSaid("C_" + mac);
                	
                    if(mac != null && mac.length() == 12 && mac.indexOf(":") == -1) {
                        mac = mac.substring(0, 2) + ":"
                                + mac.substring(2, 4) + ":"
                                + mac.substring(4, 6) + ":"
                                + mac.substring(6, 8) + ":"
                                + mac.substring(8, 10) + ":"
                                + mac.substring(10, 12);
                        req.setGwMac(mac);
                    }
            	} else {
            		req.setGwMac("");
            		req.setGwSaid("");
            	}
                
                String url = ghmsServerInfo.getProtocol() + "://" 
                        + ghmsServerInfo.getHost() + ":" 
                        + ghmsServerInfo.getPort() 
                        + IDMSApiCode.ghmsSubscribe.getUrl(); 
                
                res = (SubscriptionInfoResponse)infraHttpClient.send(url, req, new SubscriptionInfoResponse());
            } catch (Exception e) {
                log.error(e.toString(), e.fillInStackTrace());
            }

            // 3. 청약 연동 결과 업데이트 
            if(res != null && SUCCESS.equals(res.getResultCode())) {
                subscriptionDao.updateManagerSubscription(req);
                
                res.setStatusCode(HttpStatus.SC_OK);
                res.setStatusText("SUCCESS");
            } else {
                res = new SubscriptionInfoResponse();
                res.setResultCode(FAIL);                
            }
        } catch(Exception e) {
            log.error(e.toString(), e.fillInStackTrace());
            e.printStackTrace();
            
            res.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            res.setStatusText(e.getMessage());
            res.setResultCode(FAIL);
            
            return res;
        }
        
        return res;
    }
    
    public HttpObjectRequest selectSubscriptionManager(SubscriptionManagerInfoRequest req) {      

        SubscriptionManagerInfoRequest res = subscriptionDao.selectSubscriptionManager(req);
        
        return res;
    }
}
