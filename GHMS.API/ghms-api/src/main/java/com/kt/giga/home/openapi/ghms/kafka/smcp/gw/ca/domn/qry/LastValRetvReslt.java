package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.qry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.CorePrcssData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.colec.SpotDevColecData;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row.CmdData;


public class LastValRetvReslt extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -5771260664278552807L;

	/** 게이트웨이연결아이디 */
	private String gwCnctId;
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
	/** 현장장치수집데이터리스트 */
	private List<SpotDevColecData> spotDevColecDatas = new ArrayList<SpotDevColecData>();

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
		if(spotDevColecDatas == null || spotDevColecDatas.size() == 0)
		{
			return true;
		}
		for(SpotDevColecData spotDevColecData : spotDevColecDatas)
		{
			//빈객체가 아니면
			if(!spotDevColecData.isEmptyData())
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


	public String getGwCnctId() {
		return gwCnctId;
	}

	public void setGwCnctId(String gwCnctId) {
		this.gwCnctId = gwCnctId;
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

	public List<SpotDevColecData> getSpotDevColecDatas() {
		return spotDevColecDatas;
	}

	public void setSpotDevColecDatas(List<SpotDevColecData> spotDevColecDatas) {
		this.spotDevColecDatas = spotDevColecDatas;
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
