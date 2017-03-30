package com.kt.giga.home.cms.vo.row;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Row 클래스(플랫폼에서 처리되는 주요데이터를 일반화하여 관리)
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * 2014. 11.16. 조홍진: OpenAPI 커스토마이징 
 * ----------------------------------------------------
 * </PRE>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Row
{
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
	private List<StrData> strDatas = null;
	/** 일시데이터(61) - 어느날 갑자기 서버 에러를 일으키면 나타난 멤버 변수 */
	private List<DtData> dtDatas = new ArrayList<DtData>();	
	/** 이벤트데이터(70) */
	private EvData evData;
	/** 일반설정데이터(80) */
	private List<GenlSetupData> genlSetupDatas = new ArrayList<GenlSetupData>();
	/** 스케줄설정데이터(81) */
	private List<SclgSetupData> sclgSetupDatas = new ArrayList<SclgSetupData>();
	/** 행확장필드 */
	private HashMap<String, Object> rowExtension = new HashMap<String, Object>();
	
	public void addContlData(ContlData contlData) {
		if(contlDatas == null) {
			contlDatas = new ArrayList<ContlData>();
		}
			
		contlDatas.add(contlData);
	}
	
	public void addGenlSetupData(GenlSetupData genlSetupData) {
		if(genlSetupDatas == null) {
			genlSetupDatas = new ArrayList<GenlSetupData>();
		}
			
		genlSetupDatas.add(genlSetupData);		
	}
	
	public void addSclgSetupData(SclgSetupData sclgSetupData) {
		if(sclgSetupDatas == null) {
			sclgSetupDatas = new ArrayList<SclgSetupData>();
		}
			
		sclgSetupDatas.add(sclgSetupData);		
	}
	
	public void addRowExtensionValue(String key, Object value) {
		if(rowExtension == null) {
			rowExtension = new HashMap<String, Object>(); 
		}
		
		rowExtension.put(key, value);
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
	
	public ContlData getFirstContlData() {
		
		if(contlDatas == null || contlDatas.isEmpty())
			return null;
		
		return contlDatas.get(0);
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

	public EvData getEvData() {
		return evData;
	}

	public void setEvData(EvData evData) {
		this.evData = evData;
	}

	public List<GenlSetupData> getGenlSetupDatas() {
		return genlSetupDatas;
	}
	
	public GenlSetupData getFirstGenlSetupData() {
		
		if(genlSetupDatas == null || genlSetupDatas.isEmpty())
			return null;
		
		return genlSetupDatas.get(0);
	}

	public void setGenlSetupDatas(List<GenlSetupData> genlSetupDatas) {
		this.genlSetupDatas = genlSetupDatas;
	}

	public List<SclgSetupData> getSclgSetupDatas() {
		return sclgSetupDatas;
	}
	
	public SclgSetupData getFirstSclgSetupData() {
		if(sclgSetupDatas == null || sclgSetupDatas.isEmpty())
			return null;
		
		return sclgSetupDatas.get(0);
	}
	
	public void setFirstSclgSetupDataSnsnTagCd(String snsnTagCd) {
		
		if(getFirstSclgSetupData() == null) {
			addSclgSetupData(new SclgSetupData(snsnTagCd));
		} else {
			getFirstSclgSetupData().setSnsnTagCd(snsnTagCd);	
		}
	}

	public void setSclgSetupDatas(List<SclgSetupData> sclgSetupDatas) {
		this.sclgSetupDatas = sclgSetupDatas;
	}

	public HashMap<String, Object> getRowExtension() {
		if(rowExtension == null) {
			rowExtension = new HashMap<String, Object>();
		}
			
		return rowExtension;
	}
	
	public Object getRowExtensionValue(String key) {
		return getRowExtension().get(key);
	}
	
	public void setRowExtension(HashMap<String, Object> rowExtension) {
		this.rowExtension = rowExtension;
	}

	public List<DtData> getDtDatas() {
		return dtDatas;
	}

	public void setDtDatas(List<DtData> dtDatas) {
		this.dtDatas = dtDatas;
	}
	
}
