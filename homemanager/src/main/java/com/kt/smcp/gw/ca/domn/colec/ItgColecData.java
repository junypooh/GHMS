package com.kt.smcp.gw.ca.domn.colec;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.CorePrcssData;

/**
 * 통합수집데이터 클래스
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class ItgColecData extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 4041502910266512558L;

	/** 게이트웨이수집데이터 */
	private GwColecData gwColecData;
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

	public GwColecData getGwColecData() {
		return gwColecData;
	}
	public void setGwColecData(GwColecData gwColecData) {
		this.gwColecData = gwColecData;
	}
	public List<SpotDevColecData> getSpotDevColecDatas() {
		return spotDevColecDatas;
	}
	public void setSpotDevColecDatas(List<SpotDevColecData> spotDevColecDatas) {
		this.spotDevColecDatas = spotDevColecDatas;
	}

	@Override
	public boolean isEmptyData()
	{
		if(gwColecData != null)
		{
			if(!gwColecData.isEmptyData())
			{
				return false;
			}
		}

		for(SpotDevColecData spotDevColecData : spotDevColecDatas)
		{
			if(!spotDevColecData.isEmptyData())
			{
				return false;
			}
		}
		return true;
	}


}
