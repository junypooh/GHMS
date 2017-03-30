package com.kt.smcp.gw.ca.domn.spotdev;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.row.BinSetupData;
import com.kt.smcp.gw.ca.domn.row.GenlSetupData;
import com.kt.smcp.gw.ca.domn.row.SclgSetupData;

public class SpotDev extends SpotDevBas implements Serializable, Cloneable
{
	/** 직렬화일련번호 */
	private static final long serialVersionUID = -8125100773880853917L;
	/** 현장장치상세 */
	private List<SpotDevDtl> spotDevDtls = new ArrayList<SpotDevDtl>();
	/** 현장장치통신채널 */
	private List<SpotDevCommChDtl> spotDevCommChDtls = new ArrayList<SpotDevCommChDtl>();
	/** 일반설정데이터(80) */
	private List<GenlSetupData> genlSetupDatas = new ArrayList<GenlSetupData>();
	/** 스케줄설정데이터(81) */
	private List<SclgSetupData> sclgSetupDatas = new ArrayList<SclgSetupData>();
	/** 이진설정데이터(82) */
	private List<BinSetupData> binSetupDatas = new ArrayList<BinSetupData>();

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

	public List<SpotDevDtl> getSpotDevDtls() {
		return spotDevDtls;
	}
	public void setSpotDevDtls(List<SpotDevDtl> spotDevDtls) {
		this.spotDevDtls = spotDevDtls;
	}
	public List<SpotDevCommChDtl> getSpotDevCommChDtls() {
		return spotDevCommChDtls;
	}
	public void setSpotDevCommChDtls(List<SpotDevCommChDtl> spotDevCommChDtls) {
		this.spotDevCommChDtls = spotDevCommChDtls;
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

	public List<BinSetupData> getBinSetupDatas() {
		return binSetupDatas;
	}

	public void setBinSetupDatas(List<BinSetupData> binSetupDatas) {
		this.binSetupDatas = binSetupDatas;
	}

}
