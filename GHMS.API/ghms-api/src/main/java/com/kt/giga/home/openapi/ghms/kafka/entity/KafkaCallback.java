package com.kt.giga.home.openapi.ghms.kafka.entity;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.CorePrcssData;

public interface KafkaCallback{
	public void callback(CorePrcssData coreData);
}
