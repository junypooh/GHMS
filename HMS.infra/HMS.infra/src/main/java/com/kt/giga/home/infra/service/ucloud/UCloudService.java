package com.kt.giga.home.infra.service.ucloud;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.kt.giga.home.infra.client.host.UCloudServerInfo;
import com.kt.giga.home.infra.client.http.InfraHttpClient;
import com.kt.giga.home.infra.client.http.InfraHttpClientFactory;
import com.kt.giga.home.infra.domain.ucloud.AccountRequest;
import com.kt.giga.home.infra.domain.ucloud.AccountResponse;
import com.kt.giga.home.infra.domain.ucloud.TokenRequest;
import com.kt.giga.home.infra.domain.ucloud.TokenResponse;
import com.kt.giga.home.infra.util.SecurityUtil;

@Service
public class UCloudService {
	private Logger log = Logger.getLogger(this.getClass());
		
	@Autowired
	private InfraHttpClientFactory factory;
	
	@Autowired
	private UCloudServerInfo serverInfo;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	@Value("#{system['ucloud.type']}")
	private String serviceType;
	
	public TokenResponse postToken(TokenRequest req) throws Exception {
		// 01. 클라이언트 생성
		InfraHttpClient infraHttpClient = factory.getInfraHttpClient(serverInfo);
		TokenResponse res = new TokenResponse();
		
		// 02. 요청값 설정
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("api_token", securityUtil.getApiKeyToken());
		map.add("login_id", req.getLoginId());
		map.add("cid", req.getCid());
		map.add("service_type", serviceType);
		map.add("sa_id", req.getSaId());
		map.add("ca_saId", req.getCaSaid());
		map.add("ca_key", securityUtil.getCaKey());
		
		// 03. 유클라우드로 요청
		String url = serverInfo.getProtocol() + "://" 
				+ serverInfo.getHost() + ":" 
				+ serverInfo.getPort() 
				+ UCloudApiCode.create_access_token.getUrl(); 
		
		String resBody = (String)infraHttpClient.sendForm(url, HttpMethod.POST, map);
		Map<String, String> resMap = this.toMap(resBody);
		
		if(!resMap.isEmpty()) {
			res.setStatusCode(Integer.parseInt(resMap.get("statusCode")));
			res.setStatusText(resMap.get("statusText"));
			res.setResultCode(resMap.get("result_code"));
			res.setResultMsg(URLDecoder.decode(resMap.get("result_msg"), "UTF-8"));
			res.setAuthToken(resMap.get("oauth_token"));
			res.setAuthTokenSecret(resMap.get("oauth_token_secret"));
			res.setApiKey(securityUtil.getClientApiKey());
			res.setApiSecret(securityUtil.getClientApiSecret());			
		}
		
		return res;
	}
	
	public TokenResponse deleteToken(TokenRequest req) throws Exception {
		// 01. 클라이언트 생성
		InfraHttpClient infraHttpClient = factory.getInfraHttpClient(serverInfo);
		TokenResponse res = new TokenResponse();
		
		// 02. 요청값 설정
		String queryString = "";
		queryString += "?api_token=" + securityUtil.getApiKeyToken();
		queryString += "&oauth_token=" + req.getAuthToken();
		queryString += "&ca_saId=" + req.getCaSaid();
		
		// 03. 유클라우드로 요청
		String url = serverInfo.getProtocol() + "://" 
				+ serverInfo.getHost() + ":" 
				+ serverInfo.getPort() 
				+ UCloudApiCode.delete_access_token.getUrl()
				+ queryString;
		
		String resBody = (String)infraHttpClient.sendForm(url, HttpMethod.DELETE, null);
		Map<String, String> resMap = this.toMap(resBody);
		
		if(!resMap.isEmpty()) {
			res.setStatusCode(Integer.parseInt(resMap.get("statusCode")));
			res.setStatusText(resMap.get("statusText"));
			res.setResultCode(resMap.get("result_code"));
			res.setResultMsg(URLDecoder.decode(resMap.get("result_msg"), "UTF-8"));
		}
		
		return res;
	}
	
	public AccountResponse postAccount(AccountRequest req) throws Exception {
		// 01. 클라이언트 생성
		InfraHttpClient infraHttpClient = factory.getInfraHttpClient(serverInfo);
		AccountResponse res = new AccountResponse();
		
		// 02. 요청값 설정
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("api_token", securityUtil.getApiKeyToken());
		map.add("login_id", req.getLoginId());
		map.add("cid", req.getCid());
		map.add("service_type", serviceType);
		map.add("sa_id", req.getSaId());
		
		// 03. 유클라우드로 요청
		String url = serverInfo.getProtocol() + "://" 
				+ serverInfo.getHost() + ":" 
				+ serverInfo.getPort() 
				+ UCloudApiCode.create_user.getUrl();
		
		String resBody = (String)infraHttpClient.sendForm(url, HttpMethod.POST, map);
		Map<String, String> resMap = this.toMap(resBody);
		
		if(!resMap.isEmpty()) {
			res.setStatusCode(Integer.parseInt(resMap.get("statusCode")));
			res.setStatusText(resMap.get("statusText"));
			res.setResultCode(resMap.get("result_code"));
			res.setResultMsg(URLDecoder.decode(resMap.get("result_msg"), "UTF-8"));
		}
		
		return res;
	}
	
	public AccountResponse deleteAccount(AccountRequest req) throws Exception {
		// 01. 클라이언트 생성
		InfraHttpClient infraHttpClient = factory.getInfraHttpClient(serverInfo);
		AccountResponse res = new AccountResponse();
		
		// 02. 요청값 설정
		String queryString = "";
		queryString += "?api_token=" + securityUtil.getApiKeyToken();
		queryString += "&login_id=" + req.getLoginId();
		queryString += "&cid=" + req.getCid();
		queryString += "&service_type=" + serviceType;
		queryString += "&sa_id=" + req.getSaId();
		
		// 03. 유클라우드로 요청
		String url = serverInfo.getProtocol() + "://" 
				+ serverInfo.getHost() + ":" 
				+ serverInfo.getPort() 
				+ UCloudApiCode.delete_user.getUrl()
				+ queryString;
		
		String resBody = (String)infraHttpClient.sendForm(url, HttpMethod.DELETE, null);
		Map<String, String> resMap = this.toMap(resBody);
		
		if(!resMap.isEmpty()) {
			res.setStatusCode(Integer.parseInt(resMap.get("statusCode")));
			res.setStatusText(resMap.get("statusText"));
			res.setResultCode(resMap.get("result_code"));
			res.setResultMsg(URLDecoder.decode(resMap.get("result_msg"), "UTF-8"));
		}
		
		return res;
	}
	
	public AccountResponse modifyAccount(AccountRequest req) throws Exception {
		// 01. 클라이언트 생성
		InfraHttpClient infraHttpClient = factory.getInfraHttpClient(serverInfo);
		AccountResponse res = new AccountResponse();
		
		// 02. 요청값 설정
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("api_token", securityUtil.getApiKeyToken());
		map.add("login_id", req.getLoginId());
		map.add("cid", req.getCid());
		map.add("service_type", serviceType);
		map.add("sa_id", req.getSaId());
		map.add("op_type", req.getOpType());
		
		// 03. 유클라우드로 요청
		String url = serverInfo.getProtocol() + "://" 
				+ serverInfo.getHost() + ":" 
				+ serverInfo.getPort() 
				+ UCloudApiCode.modify_user.getUrl();
		
		String resBody = (String)infraHttpClient.sendForm(url, HttpMethod.PUT, map);
		Map<String, String> resMap = this.toMap(resBody);
		
		if(!resMap.isEmpty()) {
			res.setStatusCode(Integer.parseInt(resMap.get("statusCode")));
			res.setStatusText(resMap.get("statusText"));
			res.setResultCode(resMap.get("result_code"));
			res.setResultMsg(URLDecoder.decode(resMap.get("result_msg"), "UTF-8"));
		}
		
		return res;
	}
	
	public Map toMap(String body) {
		Map map = new HashMap();
		
		StringTokenizer st = new StringTokenizer(body, "&");
		while(st.hasMoreTokens()) {
			String param = st.nextToken();			
			map.put(param.substring(0, param.indexOf("=")), param.substring(param.indexOf("=") + 1, param.length()));
		}		
		
		return map;
	}
}
