package com.kt.giga.home.infra.service.kpns;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import com.kt.giga.home.infra.client.host.KPNSServerInfo;
import com.kt.giga.home.infra.client.http.InfraHttpClient;
import com.kt.giga.home.infra.client.http.InfraHttpClientFactory;
import com.kt.giga.home.infra.client.http.RestHttpClient;
import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;
import com.kt.giga.home.infra.domain.kpns.PushInfoRequest;
import com.kt.giga.home.infra.domain.kpns.PushInfoResponse;
import com.kt.giga.home.infra.service.idms.IDMSApiCode;

@Service
public class KPNSService {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private InfraHttpClientFactory factory;
	
	@Autowired
	private KPNSServerInfo serverInfo;
	
	@Value("#{system['kpns.auth']}")
	private String auth;
	
	public HttpObjectResponse push(PushInfoRequest req) throws Exception {
		InfraHttpClient infraHttpClient = factory.getInfraHttpClient(serverInfo);
		PushInfoResponse res = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PNSLogin auth=" + auth);
		
		String url = serverInfo.getProtocol() + "://" 
				+ serverInfo.getHost() + ":" 
				+ serverInfo.getPort() 
				+ KPNSApiCode.req_message_send.getUrl(); 
		
		try {
			res = (PushInfoResponse)infraHttpClient.send(url, HttpMethod.POST, headers, req, new PushInfoResponse());
		} catch(HttpMessageNotReadableException e) {
			res = new PushInfoResponse();
			res.setStatusCode(HttpStatus.SC_OK); 
		}
		
		if(res != null) {
			if(res.getStatusCode() != 200) {
				log.debug("req.getRetryCount() : " + req.getRetryCount());
				
				if(req.getRetryCount() < 5) {
					req.setRetryCount(req.getRetryCount() + 1);
					push(req);
				}
			}
		}
		
		return res;
	}
	
	private String getAuthorization(String username, String password){
		byte[] base64Token = Base64.encode((username+":"+password).getBytes());
		return new String (base64Token);
	}
}
