/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.client.http;

import java.io.IOException;

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
import org.apache.log4j.Logger;
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

import com.kt.giga.home.infra.client.host.ServerInfo;
import com.kt.giga.home.infra.domain.commons.HttpObjectRequest;
import com.kt.giga.home.infra.domain.commons.HttpObjectResponse;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 10.
 */
public class CheckerWebserviceHttpClient extends CheckerHttpClient {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private WebServiceTemplate webServiceTemplate;
	
	private String soapaction;
	
	private String username;
	
	private String password;
	
	public CheckerWebserviceHttpClient(ServerInfo serverInfo) {
		
		this.username = serverInfo.getUsername();
		this.password = serverInfo.getPassword();
		
		try {
			SaajSoapMessageFactory factory = new SaajSoapMessageFactory();
	        factory.afterPropertiesSet();
	        
	        String packages[] = new String[1];
	        packages[0] = "com.kt.giga.home.infra.domain.checker";
	        
	        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	        marshaller.setPackagesToScan(packages);
	        marshaller.afterPropertiesSet();
	        
	        this.webServiceTemplate = new WebServiceTemplate(factory);
	        this.webServiceTemplate.setMarshaller(marshaller);
	        this.webServiceTemplate.setUnmarshaller(marshaller);
	        
	        
	        HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
//			connectionManagerParams.setConnectionTimeout(10 * 1000);
//			connectionManagerParams.setSoTimeout(10 * 1000);
			
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
			log.error(e, e.fillInStackTrace());
		}
	}

	public WebServiceTemplate getWebServiceTemplate() {
		return this.webServiceTemplate;
	}

	@Override
	public HttpObjectResponse send(String url, HttpObjectRequest req, HttpObjectResponse res, String action) throws Exception {
		return this.send(url, HttpMethod.POST, null, req, res, action);
	}
	
	@Override
	public HttpObjectResponse send(String url, HttpMethod method, HttpHeaders headers, HttpObjectRequest req, HttpObjectResponse res, String action) throws Exception {
		
		soapaction = action;
		
		try {
			
			res = (HttpObjectResponse)webServiceTemplate.marshalSendAndReceive(url, req, new WebServiceMessageCallback() {
				public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
					try {
						SOAPMessage soapMessage = ((SaajSoapMessage) message).getSaajMessage();
						SOAPHeader header = soapMessage.getSOAPHeader();
						
						MimeHeaders mimeHeader = soapMessage.getMimeHeaders();
						mimeHeader.setHeader("SOAPAction", soapaction);
						
						if(username != null && password != null && !"".equals(username) && !"".equals(password)) {
							SOAPHeaderElement security = header.addHeaderElement(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security", "oas"));
							SOAPElement usernameToken = security.addChildElement(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken", "oas"));
							SOAPElement usernameElement = usernameToken.addChildElement(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Username", "oas"));
							SOAPElement passwordElement = usernameToken.addChildElement(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Password", "oas"));
							
							usernameElement.setTextContent(username);
							passwordElement.setTextContent(password);
						}
					     
					} catch (Exception e) {
						log.error(e.toString(), e.fillInStackTrace());
					}
				}
			});
			
		} catch (SoapFaultClientException e) {
			throw e;
		}
	
	return res;
	}
	
	@Override	
	public String sendForm(String url, HttpMethod method, MultiValueMap<String, String> map) throws Exception {
		return "";
	}

}
