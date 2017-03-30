package com.kt.giga.home.openapi.ghms.kafka.type;

import java.text.SimpleDateFormat;


/**
 * @author jnam
 * For example: 
 * 1. MicroTimestamp.INSTANCE.getTransacId()/yyyyMMddHHmmssSSS + SSS(microsecond) = "20150408111530322513
 * 2. MicroTimestamp.INSTANCE.getTimestamp(20150408111530322513) = 1428459330322
 */ 
public enum MicroTimestampType {  
	TRANSAC_ID;

	private final String pattern = "yyyyMMddHHmmssSSS";
	private long              startDate;
	private long              startNanoseconds;
	private SimpleDateFormat  dateFormat;
	
	private MicroTimestampType(){
		this.startDate = System.currentTimeMillis();
		this.startNanoseconds = System.nanoTime();
		this.dateFormat = new SimpleDateFormat(this.pattern);
	}

	public String getTransacId() {
		long microSeconds = (System.nanoTime() - this.startNanoseconds) / 1000;
		long date = this.startDate + (microSeconds / 1000);
		return this.dateFormat.format(date) + String.format("%03d", microSeconds%1000) ;
	}
	
	public Long getTimestamp(String transacId) {
		try {
			String matchOfTransacId = transacId.substring(0, pattern.length());
			return this.dateFormat.parse(matchOfTransacId).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Long getReverseTimestamp(String transacId){
		return Long.MAX_VALUE - getTimestamp(transacId);
	}
}