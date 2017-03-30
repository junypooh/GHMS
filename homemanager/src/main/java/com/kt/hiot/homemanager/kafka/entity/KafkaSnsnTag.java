package com.kt.hiot.homemanager.kafka.entity;

public class KafkaSnsnTag {
	private String snsnTagCd;
	private String rlNumVal;
	
	public KafkaSnsnTag(String snsnTagCd, String rlNumVal){
		this.snsnTagCd = snsnTagCd;
		this.rlNumVal = rlNumVal;
	}
	
	public String getSnsnTagCd() {
		return snsnTagCd;
	}
	public void setSnsnTagCd(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}
	public String getRlNumVal() {
		return rlNumVal;
	}
	public void setRlNumVal(String rlNumVal) {
		this.rlNumVal = rlNumVal;
	}
	
	
	
}
