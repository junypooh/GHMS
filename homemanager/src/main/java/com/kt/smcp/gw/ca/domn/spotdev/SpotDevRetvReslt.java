package com.kt.smcp.gw.ca.domn.spotdev;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.CorePrcssData;
import com.kt.smcp.gw.ca.domn.row.CmdData;

public class SpotDevRetvReslt extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -5771260664278552807L;

	/**요청API서버아이디  */
	private String reqApiSrvrId;
	/**요청EC서버아이디  */
	private String reqEcSrvrId;
	/** 트랜잭션아이디(상위) */
	private String transacId;
	/** 하위트랜잭션아이디(하위) */
	private String lowTransacId;
	/** 단위서비스코드 */
	private String unitSvcCd;
	/** 명령어데이터리스트(31) */
	private List<CmdData> cmdDatas = new ArrayList<CmdData>();
	/** 현장장치조회결과 */
	private List<SpotDev> spotDevs = new ArrayList<SpotDev>();

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
		if(spotDevs == null || spotDevs.size() == 0)
		{
			return true;
		}
		for(SpotDev spotDev : spotDevs)
		{
			//빈객체가 아니면
			if(!spotDev.isEmptyData())
			{
				return false;
			}
		}
		return true;
	}

	public String getTransacId() {
		return transacId;
	}

	public void setTransacId(String transacId) {
		this.transacId = transacId;
	}

	public String getLowTransacId() {
		return lowTransacId;
	}

	public void setLowTransacId(String lowTransacId) {
		this.lowTransacId = lowTransacId;
	}

	public String getUnitSvcCd() {
		return unitSvcCd;
	}

	public void setUnitSvcCd(String unitSvcCd) {
		this.unitSvcCd = unitSvcCd;
	}

	public List<CmdData> getCmdDatas() {
		return cmdDatas;
	}

	public void setCmdDatas(List<CmdData> cmdDatas) {
		this.cmdDatas = cmdDatas;
	}

	public List<SpotDev> getSpotDevs() {
		return spotDevs;
	}

	public void setSpotDevs(List<SpotDev> spotDevs) {
		this.spotDevs = spotDevs;
	}

	public String getReqApiSrvrId() {
		return reqApiSrvrId;
	}

	public void setReqApiSrvrId(String reqApiSrvrId) {
		this.reqApiSrvrId = reqApiSrvrId;
	}

	public String getReqEcSrvrId() {
		return reqEcSrvrId;
	}

	public void setReqEcSrvrId(String reqEcSrvrId) {
		this.reqEcSrvrId = reqEcSrvrId;
	}


}
