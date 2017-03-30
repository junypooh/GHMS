package com.kt.giga.home.openapi.ghms.kafka.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HbaseControlHistory {
	private String rowKey = "";	//rowKey
	private String svcCode = "001HIT003";
	private String eventSeq = "";	//제어시퀀스
	private String svcTgtSeq = "";	//서비스대상일련번호
	private String devTypeCd = "";	//장치그룹코드
	private String spotDevSeq = "";	//장치일련번호
	private String devUuid = "";	//장치식별자
	private String devNm = "";	//닉네임
	private String contDt = "";	//제어시간
	private String contlCd = "";	//제어코드(센싱코드)
	private String contlVal = "";	//제어값
	private String contlTrtSttusCd = "";	//제어결과
	private String mbrSeq = "";	//사용자 일련번호
	private String custNm = "";	//사용자 이름
	private String acesUrlAdr = "";	//사용자 이미지URL
	private String eventId = "";
	private String attributes = "";
	
	public Map<String, Object> toMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rowKey", rowKey);
		map.put("eventSeq", eventSeq);
		map.put("svcTgtSeq", svcTgtSeq);
		map.put("devTypeCd", devTypeCd);
		map.put("devUuid", spotDevSeq);
		map.put("devNm", devNm);
		map.put("contDt", contDt);
		map.put("contlCd", contlCd);
		map.put("contlVal", contlVal);
		map.put("contlTrtSttusCd", contlTrtSttusCd);
		map.put("mbrSeq", mbrSeq);
		map.put("custNm", custNm);
		map.put("eventId", eventId);
		map.put("acesUrlAdr", acesUrlAdr);
		return map;
	}
	
	public HbaseControlHistory(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		contDt = format.format(new Date());
	}
	
	public HbaseControlHistory(String rowKey, String eventSeq, String svcTgtSeq,
			String devTypeCd, String spotDevSeq, String devUuid, String devNm, String contDt,
			String contlCd, String contlVal, String contlTrtSttusCd,
			String mbrSeq, String custNm, String acesUrlAdr) {
		this.rowKey = rowKey;
		this.eventSeq = eventSeq;
		this.svcTgtSeq = svcTgtSeq;
		this.devTypeCd = devTypeCd;
		this.spotDevSeq = spotDevSeq;
		this.devUuid = devUuid;
		this.devNm = devNm;
		this.contDt = contDt;
		this.contlCd = contlCd;
		this.contlVal = contlVal;
		this.contlTrtSttusCd = contlTrtSttusCd;
		this.mbrSeq = mbrSeq;
		this.custNm = custNm;
		this.acesUrlAdr = acesUrlAdr;
	}
	
	
	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getEventSeq() {
		return eventSeq;
	}

	public void setEventSeq(String eventSeq) {
		this.eventSeq = eventSeq;
	}

	public String getSvcTgtSeq() {
		return svcTgtSeq;
	}

	public void setSvcTgtSeq(String svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}

	public String getDevTypeCd() {
		return devTypeCd;
	}

	public void setDevTypeCd(String devTypeCd) {
		this.devTypeCd = devTypeCd;
	}

	public String getDevUuid() {
		return devUuid;
	}

	public void setDevUuid(String devUuid) {
		this.devUuid = devUuid;
	}

	public String getDevNm() {
		return devNm;
	}

	public void setDevNm(String devNm) {
		this.devNm = devNm;
	}

	public String getContDt() {
		return contDt;
	}

	public void setContDt(String contDt) {
		this.contDt = contDt;
	}

	public String getContlCd() {
		return contlCd;
	}

	public void setContlCd(String contlCd) {
		this.contlCd = contlCd;
	}

	public String getContlVal() {
		return contlVal;
	}

	public void setContlVal(String contlVal) {
		this.contlVal = contlVal;
	}

	public String getContlTrtSttusCd() {
		return contlTrtSttusCd;
	}

	public void setContlTrtSttusCd(String contlTrtSttusCd) {
		this.contlTrtSttusCd = contlTrtSttusCd;
	}

	public String getMbrSeq() {
		return mbrSeq;
	}

	public void setMbrSeq(String mbrSeq) {
		this.mbrSeq = mbrSeq;
	}

	public String getCustNm() {
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}

	public String getAcesUrlAdr() {
		return acesUrlAdr;
	}

	public void setAcesUrlAdr(String acesUrlAdr) {
		this.acesUrlAdr = acesUrlAdr;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getSpotDevSeq() {
		return spotDevSeq;
	}

	public void setSpotDevSeq(String spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	
	public String getSvcCode() {
		return svcCode;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}

	@Override
	public String toString() {
		return "HbaseControlHistory [rowKey=" + rowKey + ", eventSeq="
				+ eventSeq + ", svcTgtSeq=" + svcTgtSeq + ", devTypeCd="
				+ devTypeCd + ", spotDevSeq=" + spotDevSeq + ", devUuid="
				+ devUuid + ", devNm=" + devNm + ", contDt=" + contDt
				+ ", contlCd=" + contlCd + ", contlVal=" + contlVal
				+ ", contlTrtSttusCd=" + contlTrtSttusCd + ", mbrSeq=" + mbrSeq
				+ ", custNm=" + custNm + ", acesUrlAdr=" + acesUrlAdr
				+ ", eventId=" + eventId + ", attributes=" + attributes + "]";
	}




	

	
}
