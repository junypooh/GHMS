package com.kt.giga.home.openapi.ghms.kafka.entity;

import java.util.ArrayList;
import java.util.List;

public class KafkaDevSetup {
	/** 서비스 타겟 일련번호 */
	private long svcTgtSeq;
	/** 장치 일련번호 */
	private long spotDevSeq;
	/** 장치 명령에 대한 상태(KafkaDevTrtSttusType) */
	private String devTrtSttusCd = "01";
	
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

	public String getDevTrtSttusCd() {
		return devTrtSttusCd;
	}

	public void setDevTrtSttusCd(String devTrtSttusCd) {
		this.devTrtSttusCd = devTrtSttusCd;
	}


	
}
