package com.kt.hiot.homemanager.kafka.entity;

import java.util.ArrayList;
import java.util.List;

public class KafkaDevSetup {
	private long svcTgtSeq;
	private long spotDevSeq;
	
	private List<KafkaSnsnTag> snsnTagList = new ArrayList<KafkaSnsnTag>();
	
	private String owner = "Home Manager OpenAPI";

	public String getGroupKey(){
		return ""+svcTgtSeq+spotDevSeq;
	}
	
	public long getSvcTgtSeq() {
		return svcTgtSeq;
	}

	public void setSvcTgtSeq(long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}

	public long getSpotDevSeq() {
		return spotDevSeq;
	}

	public void setSpotDevSeq(long spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public List<KafkaSnsnTag> getSnsnTagList() {
		return snsnTagList;
	}

	public void setSnsnTagList(List<KafkaSnsnTag> snsnTagList) {
		this.snsnTagList = snsnTagList;
	}


	
}
