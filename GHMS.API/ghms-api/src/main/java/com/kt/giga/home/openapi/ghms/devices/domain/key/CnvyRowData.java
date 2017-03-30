/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.devices.domain.key;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.BinData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.BinSetupData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.CmdData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.ContlData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.DtData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.EvData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.GenlSetupData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.LoData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.MsrData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.SclgSetupData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.StrData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.SttusData;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 3. 30.
 */
public class CnvyRowData {

	/** 트랜잭션아이디(상위) */
	private String transacId;
	/** 하위트랜잭션아이디(상위) */
	private String lowTransacId;
	/** 발생일시 */
	private Date occDt;
	/** 계측데이터리스트(10) */
	private List<MsrData> msrDatas;
	/** 상태데이터리스트(20) */
	private List<SttusData> sttusDatas;
	/** 제어데이터리스트(30) */
	private List<ContlData> contlDatas;
	/** 명령어데이터리스트(31) */
	private List<CmdData> cmdDatas;
	/** 위치데이터(40) */
	private LoData loData;
	/** 이진데이터(50) */
	private List<BinData> binDatas;
	/** 문자열데이터(60) */
	private List<StrData> strDatas;
	/** 일시데이터(61) */
	private List<DtData> dtDatas;
	/** 이벤트데이터(70) */
	private EvData evData;
	/** 일반설정데이터(80) */
	private List<GenlSetupData> genlSetupDatas;
	/** 스케줄설정데이터(81) */
	private List<SclgSetupData> sclgSetupDatas;
	/** 이진설정데이터(82) */
	private List<BinSetupData> binSetupDatas;
	/** 행확장필드 */
	private HashMap<String, Object> rowExtension;
	/**
	 * @return TODO
	 */
	public String getTransacId() {
		return transacId;
	}
	/**
	 * @param transacId TODO
	 */
	public void setTransacId(String transacId) {
		this.transacId = transacId;
	}
	/**
	 * @return TODO
	 */
	public String getLowTransacId() {
		return lowTransacId;
	}
	/**
	 * @param lowTransacId TODO
	 */
	public void setLowTransacId(String lowTransacId) {
		this.lowTransacId = lowTransacId;
	}
	/**
	 * @return TODO
	 */
	public Date getOccDt() {
		return occDt;
	}
	/**
	 * @param occDt TODO
	 */
	public void setOccDt(Date occDt) {
		this.occDt = occDt;
	}
	/**
	 * @return TODO
	 */
	public List<MsrData> getMsrDatas() {
		return msrDatas;
	}
	/**
	 * @param msrDatas TODO
	 */
	public void setMsrDatas(List<MsrData> msrDatas) {
		this.msrDatas = msrDatas;
	}
	/**
	 * @return TODO
	 */
	public List<SttusData> getSttusDatas() {
		return sttusDatas;
	}
	/**
	 * @param sttusDatas TODO
	 */
	public void setSttusDatas(List<SttusData> sttusDatas) {
		this.sttusDatas = sttusDatas;
	}
	/**
	 * @return TODO
	 */
	public List<ContlData> getContlDatas() {
		return contlDatas;
	}
	/**
	 * @param contlDatas TODO
	 */
	public void setContlDatas(List<ContlData> contlDatas) {
		this.contlDatas = contlDatas;
	}
	/**
	 * @return TODO
	 */
	public List<CmdData> getCmdDatas() {
		return cmdDatas;
	}
	/**
	 * @param cmdDatas TODO
	 */
	public void setCmdDatas(List<CmdData> cmdDatas) {
		this.cmdDatas = cmdDatas;
	}
	/**
	 * @return TODO
	 */
	public LoData getLoData() {
		return loData;
	}
	/**
	 * @param loData TODO
	 */
	public void setLoData(LoData loData) {
		this.loData = loData;
	}
	/**
	 * @return TODO
	 */
	public List<BinData> getBinDatas() {
		return binDatas;
	}
	/**
	 * @param binDatas TODO
	 */
	public void setBinDatas(List<BinData> binDatas) {
		this.binDatas = binDatas;
	}
	/**
	 * @return TODO
	 */
	public List<StrData> getStrDatas() {
		return strDatas;
	}
	/**
	 * @param strDatas TODO
	 */
	public void setStrDatas(List<StrData> strDatas) {
		this.strDatas = strDatas;
	}
	/**
	 * @return TODO
	 */
	public List<DtData> getDtDatas() {
		return dtDatas;
	}
	/**
	 * @param dtDatas TODO
	 */
	public void setDtDatas(List<DtData> dtDatas) {
		this.dtDatas = dtDatas;
	}
	/**
	 * @return TODO
	 */
	public EvData getEvData() {
		return evData;
	}
	/**
	 * @param evData TODO
	 */
	public void setEvData(EvData evData) {
		this.evData = evData;
	}
	/**
	 * @return TODO
	 */
	public List<GenlSetupData> getGenlSetupDatas() {
		return genlSetupDatas;
	}
	/**
	 * @param genlSetupDatas TODO
	 */
	public void setGenlSetupDatas(List<GenlSetupData> genlSetupDatas) {
		this.genlSetupDatas = genlSetupDatas;
	}
	/**
	 * @return TODO
	 */
	public List<SclgSetupData> getSclgSetupDatas() {
		return sclgSetupDatas;
	}
	/**
	 * @param sclgSetupDatas TODO
	 */
	public void setSclgSetupDatas(List<SclgSetupData> sclgSetupDatas) {
		this.sclgSetupDatas = sclgSetupDatas;
	}
	/**
	 * @return TODO
	 */
	public List<BinSetupData> getBinSetupDatas() {
		return binSetupDatas;
	}
	/**
	 * @param binSetupDatas TODO
	 */
	public void setBinSetupDatas(List<BinSetupData> binSetupDatas) {
		this.binSetupDatas = binSetupDatas;
	}
	/**
	 * @return TODO
	 */
	public HashMap<String, Object> getRowExtension() {
		return rowExtension;
	}
	/**
	 * @param rowExtension TODO
	 */
	public void setRowExtension(HashMap<String, Object> rowExtension) {
		this.rowExtension = rowExtension;
	}

}
