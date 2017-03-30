package com.kt.hiot.homemanager.kafka.entity;

import java.util.HashMap;
import java.util.Map;

public class KafkaDevSetupGroup {
	private int cmdDevSetupCount;
	
	//key is svcTgtSeq + spotDevSeq
	private Map<String, KafkaDevSetup> devSetup = new HashMap<String, KafkaDevSetup>();
	
	public Map<String, KafkaDevSetup> getDevSetup() {
		return devSetup;
	}
	public void setDevSetup(Map<String, KafkaDevSetup> devSetup) {
		this.devSetup = devSetup;
	}
	public int getCmdDevSetupCount() {
		return cmdDevSetupCount;
	}
	public void setCmdDevSetupCount(int cmdDevSetupCount) {
		this.cmdDevSetupCount = cmdDevSetupCount;
	}
	
	public boolean isFull(){
		return (devSetup.size() == cmdDevSetupCount);
	}
	
	
}
