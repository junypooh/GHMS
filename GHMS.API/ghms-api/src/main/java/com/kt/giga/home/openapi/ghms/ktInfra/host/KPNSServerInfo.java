/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.ktInfra.host;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.giga.home.openapi.ghms.util.properties.APIEnv;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 3. 10.
 */
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
