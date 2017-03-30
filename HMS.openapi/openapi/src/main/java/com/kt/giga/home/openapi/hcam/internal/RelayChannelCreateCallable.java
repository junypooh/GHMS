package com.kt.giga.home.openapi.hcam.internal;

import com.kt.giga.home.openapi.common.AuthToken;
import com.kt.giga.home.openapi.hcam.service.RelayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Callable;

public class RelayChannelCreateCallable implements Callable<Map<String, String>> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private RelayService relayService;

	private String devUUID;

	private AuthToken authToken;

	private int relayDeviceId;

	private int relayDevicePw;

	private String expireTime;

	private String maxExpireTime;

    public RelayChannelCreateCallable() {

    }

	public void setRelayService(RelayService relayService) {
		this.relayService = relayService;
	}

	public void setDevUUID(String devUUID) {
		this.devUUID = devUUID;
	}

	public void setAuthToken(AuthToken authToken) {
		this.authToken = authToken;
	}

	public void setRelayDeviceId(int relayDeviceId) {
		this.relayDeviceId = relayDeviceId;
	}

	public void setRelayDevicePw(int relayDevicePw) {
		this.relayDevicePw = relayDevicePw;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public void setMaxExpireTime(String maxExpireTime) {
		this.maxExpireTime = maxExpireTime;
	}

	@Override
	public Map<String, String> call() throws Exception {
		Map<String, String> resultMap = relayService.createRelayChannel(devUUID, authToken.getDeviceId(), relayDeviceId, relayDevicePw, expireTime, maxExpireTime);

		return resultMap;
	}


}
