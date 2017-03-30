package com.kt.hiot.homemanager.kafka.console;

import java.util.HashMap;
import java.util.Map;

import com.kt.hiot.homemanager.kafka.entity.KafkaDevSetup;
import com.kt.hiot.homemanager.kafka.entity.KafkaDevSetupGroup;

public class KafkaDevSetupManager extends HashMap<String, KafkaDevSetupGroup>{

	private static final long serialVersionUID = 1L;

	//key is transactionId
	public KafkaDevSetupGroup getDevSetupGroup(String transacId){
		return this.get(transacId);
	}
	
	//return transactionId
	public String create(String authToken, int cmdDevCount){
		KafkaDevSetupGroup group = new KafkaDevSetupGroup();
		group.setCmdDevSetupCount(cmdDevCount);
		String transacId = authToken+"_"+System.currentTimeMillis();
		this.put(transacId, group);
		
		return transacId;
	}
	
}
