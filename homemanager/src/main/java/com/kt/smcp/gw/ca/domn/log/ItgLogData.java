package com.kt.smcp.gw.ca.domn.log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.comm.GwCode.PltfrmVer;
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
public class ItgLogData extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -4292318930826758275L;

	/** 상위시스템아이디 */
	private String upSysId;
	/** 게이트웨이전달데이터 */
	private GwLogData gwLogData;
	/** 현장장치전달데이터리스트 */
	private List<SpotDevLogData> spotDevLogDatas = new ArrayList<SpotDevLogData>();


	public ItgLogData()
	{
		this.pltfrmVer = PltfrmVer.VER_02_00;
	}

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
		if(gwLogData == null && spotDevLogDatas.size() == 0)
		{
			return true;
		}

		if(gwLogData != null)
		{
			//빈객체가 아니면
			if(!gwLogData.isEmptyData())
			{
				return false;
			}
		}

		for(SpotDevLogData spotDevLogData : spotDevLogDatas)
		{
			//빈객체가 아니면
			if(!spotDevLogData.isEmptyData())
			{
				return false;
			}
		}
		return true;
	}

	public String getUpSysId() {
		return upSysId;
	}

	public void setUpSysId(String upSysId) {
		this.upSysId = upSysId;
	}

	public GwLogData getGwLogData() {
		return gwLogData;
	}

	public void setGwLogData(GwLogData gwLogData) {
		this.gwLogData = gwLogData;
	}

	public List<SpotDevLogData> getSpotDevLogDatas() {
		return spotDevLogDatas;
	}

	public void setSpotDevLogDatas(List<SpotDevLogData> spotDevLogDatas) {
		this.spotDevLogDatas = spotDevLogDatas;
	}


}
