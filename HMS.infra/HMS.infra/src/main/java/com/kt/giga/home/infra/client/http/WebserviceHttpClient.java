package com.kt.giga.home.infra.client.http;

import com.google.gson.JsonObject;
import com.kt.giga.home.infra.client.host.ServerInfo;
import com.kt.giga.home.infra.domain.commons.HttpObjectRequest;
import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;
import com.kt.giga.home.infra.util.JsonUtils;

import java.io.IOException;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.MultiValueMap;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.CommonsHttpMessageSender;

public class WebserviceHttpClient extends InfraHttpClient {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private WebServiceTemplate webServiceTemplate;
	
	private String action;
	
	private String username;
	
	private String password;
	
	public WebserviceHttpClient(ServerInfo serverInfo) {
		this.action = serverInfo.getAction();
		this.username = serverInfo.getUsername();
		this.password = serverInfo.getPassword();
		
		try {
			SaajSoapMessageFactory factory = new SaajSoapMessageFactory();
	        factory.afterPropertiesSet();
	        
	        String packages[] = new String[3];
	        packages[0] = "com.kt.giga.home.infra.domain.idms";
	        packages[1] = "com.kt.giga.home.infra.domain.sdp";
	        packages[2] = "com.kt.giga.home.infra.domain.ucems";
	        
	        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	        marshaller.setPackagesToScan(packages);
	        marshaller.afterPropertiesSet();
	        
	        this.webServiceTemplate = new WebServiceTemplate(factory);
	        this.webServiceTemplate.setMarshaller(marshaller);
	        this.webServiceTemplate.setUnmarshaller(marshaller);	        
	        
	        HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
			connectionManagerParams.setConnectionTimeout(10 * 1000);
			connectionManagerParams.setSoTimeout(10 * 1000);
			
	        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();			
			connectionManager.setParams(connectionManagerParams);
			
			HttpClientParams httpClientParams = new HttpClientParams();
			httpClientParams.setAuthenticationPreemptive(true);
			
			HttpClient client = new HttpClient(httpClientParams, connectionManager);
			HostConfiguration config = client.getHostConfiguration();
			
			if(serverInfo.getProxyHost() != null && !"".equals(serverInfo.getProxyHost())) {
				config.setProxy(serverInfo.getProxyHost(), serverInfo.getProxyPort());
			}
			
			connectionManagerParams.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, 300);
			connectionManagerParams.setMaxTotalConnections(1000);
	        
	        CommonsHttpMessageSender mmessageSender = new CommonsHttpMessageSender(client);	        
	        this.webServiceTemplate.setMessageSender(mmessageSender);
			
		} catch(Exception e) {
			log.error(e.getMessage(), e.fillInStackTrace());
		}
	}
	
	public WebServiceTemplate getWebServiceTemplate() {
		return this.webServiceTemplate;
	}

	public HttpObjectResponse send(String url, HttpObjectRequest req, HttpObjectResponse res) 
			throws Exception {
		return this.send(url, HttpMethod.POST, null, req, res);
	}
			
	@Override
	public HttpObjectResponse send(String url, HttpMethod method, HttpHeaders headers, HttpObjectRequest req, HttpObjectResponse res) 
			throws Exception {
		try {
			
			// pw 내역 삭제
			String requestJson = JsonUtils.toJson(req);
			JsonObject jsonObj = JsonUtils.fromJson(requestJson);
			if(jsonObj.get("pw") != null){
				jsonObj.remove("pw");
			}
			
			log.info("# Send Request to : {}\n{}", url, JsonUtils.toPrettyJson(jsonObj));
//			log.info("# Send Request to : {}\n{}", url, JsonUtils.toPrettyJson(req));
			
			res = (HttpObjectResponse)webServiceTemplate.marshalSendAndReceive(url, req, new WebServiceMessageCallback() {
				public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
					try {
						SOAPMessage soapMessage = ((SaajSoapMessage) message).getSaajMessage();
						SOAPHeader header = soapMessage.getSOAPHeader();
						
						// set soapaction
						if(action != null && !"".equals(action)) {
							MimeHeaders mimeHeader = soapMessage.getMimeHeaders();
							mimeHeader.setHeader("SOAPAction", action + soapMessage.getSOAPBody().getFirstChild().getLocalName());
						}
						
						if(username != null && password != null && !"".equals(username) && !"".equals(password)) {
							SOAPHeaderElement security = header.addHeaderElement(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security", "oas"));
							SOAPElement usernameToken = security.addChildElement(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken", "oas"));
							SOAPElement usernameElement = usernameToken.addChildElement(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Username", "oas"));
							SOAPElement passwordElement = usernameToken.addChildElement(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Password", "oas"));
							
							usernameElement.setTextContent(username);
							passwordElement.setTextContent(password);
						}
					     
					} catch (Exception e) {
						log.error(e.getMessage(), e.fillInStackTrace());
					}
				}
			});
			log.info("# Recv Response from : {}\n{}", url, JsonUtils.toPrettyJson(res));
			
		} catch (SoapFaultClientException e) {
			log.warn(e.getMessage(), e);
			throw e;
		}
		
		return res;
	}
	
	@Override	
	public String sendForm(String url, HttpMethod method, MultiValueMap<String, String> map) 
			throws Exception {
		return "";
	}
}

