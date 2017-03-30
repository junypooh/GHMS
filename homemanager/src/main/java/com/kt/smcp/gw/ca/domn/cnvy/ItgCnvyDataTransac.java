package com.kt.smcp.gw.ca.domn.cnvy;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.CorePrcssData;

/**
 * 현장장치전달데이터 클래스
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class ItgCnvyDataTransac extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 3249161677142877986L;
	/** 게이트웨이연결아이디 */
	private String gwCnctId;
	/** 통합전달데이터 */
	private ItgCnvyData itgCnvyData;


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
		if(itgCnvyData != null)
		{
			//빈객체가 아니면
			if(!itgCnvyData.isEmptyData())
			{
				return false;
			}
		}
		return true;
	}


	public String getGwCnctId() {
		return gwCnctId;
	}

	public void setGwCnctId(String gwCnctId) {
		this.gwCnctId = gwCnctId;
	}

	public ItgCnvyData getItgCnvyData() {
		return itgCnvyData;
	}

	public void setItgCnvyData(ItgCnvyData itgCnvyData) {
		this.itgCnvyData = itgCnvyData;
	}
}
