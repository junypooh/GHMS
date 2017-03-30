package com.kt.giga.home.openapi.kpns.host;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kt.giga.home.openapi.common.APIEnv;

@Service
public class KPNSServerInfo {
	@Autowired
	private APIEnv env;

	public String getName() {
		return env.getProperty("kpns.name");
	}

	public String getType() {
		return env.getProperty("kpns.type");
	}

	public String getHost() {
		return env.getProperty("kpns.host");
	}

	public String getPort() {
		return env.getProperty("kpns.port");
	}

	public String getProtocol() {
		return env.getProperty("kpns.protocol");
	}
}
