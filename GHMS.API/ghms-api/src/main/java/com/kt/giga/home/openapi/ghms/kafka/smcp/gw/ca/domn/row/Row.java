package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.CorePrcssData;

/**
 * Row 클래스(플랫폼에서 처리되는 주요데이터를 일반화하여 관리)
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class Row  extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 753841586409007192L;

	/** 발생일시 */
	private Date occDt;
	/** 계측데이터리스트(10) */
	private List<MsrData> msrDatas = new ArrayList<MsrData>();
	/** 상태데이터리스트(20) */
	private List<SttusData> sttusDatas = new ArrayList<SttusData>();
	/** 제어데이터리스트(30) */
	private List<ContlData> contlDatas = new ArrayList<ContlData>();
	/** 명령어데이터리스트(31) */
	private List<CmdData> cmdDatas = new ArrayList<CmdData>();
	/** 위치데이터(40) */
	private LoData loData;
	/** 이진데이터(50) */
	private List<BinData> binDatas = new ArrayList<BinData>();
	/** 문자열데이터(60) */
	private List<StrData> strDatas = new ArrayList<StrData>();
	/** 일시데이터(61) */
	private List<DtData> dtDatas = new ArrayList<DtData>();
	/** 이벤트데이터(70) */
	private EvData evData;
	/** 일반설정데이터(80) */
	private List<GenlSetupData> genlSetupDatas = new ArrayList<GenlSetupData>();
	/** 스케줄설정데이터(81) */
	private List<SclgSetupData> sclgSetupDatas = new ArrayList<SclgSetupData>();
	/** 이진설정데이터(82) */
	private List<BinSetupData> binSetupDatas = new ArrayList<BinSetupData>();
	/** 행확장필드 */
	private HashMap<String, Object> rowExtension = new HashMap<String, Object>();

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	@Override
	public boolean isEmptyData()
	{
		if(msrDatas != null && msrDatas.size() != 0)
		{
			return false;
		}

		if(sttusDatas != null && sttusDatas.size() != 0)
		{
			return false;
		}

		if(contlDatas != null && contlDatas.size() != 0)
		{
			return false;
		}

		if(cmdDatas != null && cmdDatas.size() != 0)
		{
			return false;
		}

		if(loData != null)
		{
			return false;
		}

		if(binDatas != null && binDatas.size() != 0)
		{
			return false;
		}

		if(strDatas != null && strDatas.size() != 0)
		{
			return false;
		}

		if(dtDatas != null && dtDatas.size() != 0)
		{
			return false;
		}

		if(evData != null)
		{
			return false;
		}

		if(genlSetupDatas != null && genlSetupDatas.size() != 0)
		{
			return false;
		}

		if(sclgSetupDatas != null && sclgSetupDatas.size() != 0)
		{
			return false;
		}
		return true;
	}

	public Date getOccDt() {
		return occDt;
	}

	public void setOccDt(Date occDt) {
		this.occDt = occDt;
	}

	public List<MsrData> getMsrDatas() {
		return msrDatas;
	}

	public void setMsrDatas(List<MsrData> msrDatas) {
		this.msrDatas = msrDatas;
	}

	public List<SttusData> getSttusDatas() {
		return sttusDatas;
	}

	public void setSttusDatas(List<SttusData> sttusDatas) {
		this.sttusDatas = sttusDatas;
	}

	public List<ContlData> getContlDatas() {
		return contlDatas;
	}

	public void setContlDatas(List<ContlData> contlDatas) {
		this.contlDatas = contlDatas;
	}

	public List<CmdData> getCmdDatas() {
		return cmdDatas;
	}

	public void setCmdDatas(List<CmdData> cmdDatas) {
		this.cmdDatas = cmdDatas;
	}

	public LoData getLoData() {
		return loData;
	}

	public void setLoData(LoData loData) {
		this.loData = loData;
	}

	public List<BinData> getBinDatas() {
		return binDatas;
	}

	public void setBinDatas(List<BinData> binDatas) {
		this.binDatas = binDatas;
	}

	public List<StrData> getStrDatas() {
		return strDatas;
	}

	public void setStrDatas(List<StrData> strDatas) {
		this.strDatas = strDatas;
	}

	public List<DtData> getDtDatas() {
		return dtDatas;
	}

	public void setDtDatas(List<DtData> dtDatas) {
		this.dtDatas = dtDatas;
	}

	public EvData getEvData() {
		return evData;
	}

	public void setEvData(EvData evData) {
		this.evData = evData;
	}

	public List<GenlSetupData> getGenlSetupDatas() {
		return genlSetupDatas;
	}

	public void setGenlSetupDatas(List<GenlSetupData> genlSetupDatas) {
		this.genlSetupDatas = genlSetupDatas;
	}

	public List<SclgSetupData> getSclgSetupDatas() {
		return sclgSetupDatas;
	}

	public void setSclgSetupDatas(List<SclgSetupData> sclgSetupDatas) {
		this.sclgSetupDatas = sclgSetupDatas;
	}

	public HashMap<String, Object> getRowExtension() {
		return rowExtension;
	}

	public void setRowExtension(HashMap<String, Object> rowExtension) {
		this.rowExtension = rowExtension;
	}

	public List<BinSetupData> getBinSetupDatas() {
		return binSetupDatas;
	}

	public void setBinSetupDatas(List<BinSetupData> binSetupDatas) {
		this.binSetupDatas = binSetupDatas;
	}

}
