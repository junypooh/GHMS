package com.kt.smcp.gw.ca.domn.cnvy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.CorePrcssData;

/**
 * 통합전달데이터
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class ItgCnvyData extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -4292318930826758275L;

	/** 상위시스템아이디 */
	private String upSysId;
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
	/** 게이트웨이전달데이터 */
	private GwCnvyData gwCnvyData;
	/** 현장장치전달데이터리스트 */
	private List<SpotDevCnvyData> spotDevCnvyDatas = new ArrayList<SpotDevCnvyData>();

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
		if(gwCnvyData == null && spotDevCnvyDatas.size() == 0)
		{
			return true;
		}

		if(gwCnvyData != null)
		{
			//빈객체가 아니면
			if(!gwCnvyData.isEmptyData())
			{
				return false;
			}
		}

		for(SpotDevCnvyData spotDevCnvyData : spotDevCnvyDatas)
		{
			//빈객체가 아니면
			if(!spotDevCnvyData.isEmptyData())
			{
				return false;
			}
		}
		return true;
	}

	public void setItgCnvyData(ItgCnvyData itgCnvyData)
	{
		/** 상위시스템아이디 */
		this.upSysId = itgCnvyData.getUpSysId();
		/** 게이트웨이연결아이디 */
		this.gwCnctId = itgCnvyData.getGwCnctId();
		/**요청API서버아이디  */
		this.reqApiSrvrId = itgCnvyData.getReqApiSrvrId();
		/**요청EC서버아이디  */
		this.reqEcSrvrId = itgCnvyData.getReqEcSrvrId();
		/** 트랜잭션아이디(상위) */
		this.transacId = itgCnvyData.getTransacId();
		/** 하위트랜잭션아이디(하위) */
		this.lowTransacId = itgCnvyData.getLowTransacId();
		/** 단위서비스코드 */
		this.unitSvcCd = itgCnvyData.getUnitSvcCd();
	}

	public String getUpSysId() {
		return upSysId;
	}

	public void setUpSysId(String upSysId) {
		this.upSysId = upSysId;
	}

	public String getGwCnctId() {
		return gwCnctId;
	}

	public void setGwCnctId(String gwCnctId) {
		this.gwCnctId = gwCnctId;
	}

	public GwCnvyData getGwCnvyData() {
		return gwCnvyData;
	}

	public void setGwCnvyData(GwCnvyData gwCnvyData) {
		this.gwCnvyData = gwCnvyData;
	}

	public List<SpotDevCnvyData> getSpotDevCnvyDatas() {
		return spotDevCnvyDatas;
	}

	public void setSpotDevCnvyDatas(List<SpotDevCnvyData> spotDevCnvyDatas) {
		this.spotDevCnvyDatas = spotDevCnvyDatas;
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


}
