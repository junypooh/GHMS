package com.kt.giga.home.infra.util;

import java.net.URLEncoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtil {
	@Value("#{system['ucloud.apikey']}")
	private String key;
	
	@Value("#{system['ucloud.apisecret']}")
	private String secret;
	
	@Value("#{system['ucloud.cakey']}")
	private String caKey;
	
	@Value("#{system['ucloud.client.apikey']}")
	private String clientKey;
	
	@Value("#{system['ucloud.client.apisecret']}")
	private String clientSecret;
	
	private String calculateRFC2104HMAC(String data, String key) {
		String HMAC_SHA1_ALGORITHM = "HmacSHA1";
		byte[] result = null;
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);

			byte[] rawHmac = mac.doFinal(data.getBytes());

			result = Base64.encode(rawHmac);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new String(result);
	}
	
	public String getApiKeyToken() {
		String ts = String.valueOf(System.currentTimeMillis());
		ts = ts.substring(0, 10);
		String apiSign = calculateRFC2104HMAC(key + ";" + ts, secret);
		String accessKey = (key + ";" + ts + ";" + apiSign).trim();
		String apiToken = URLEncoder.encode(new String(Base64.encode(accessKey.getBytes())));
		
		return apiToken;
	}
	
	public String getApiKey() {
		return key;
	}
	
	public String getApiSecret() {
		return secret;
	}
	
	public String getCaKey() {
		return caKey;
	}
	
	public String getClientApiKey() {
		return clientKey;
	}
	
	public String getClientApiSecret() {
		return clientSecret;
	}
}
