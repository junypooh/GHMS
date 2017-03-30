package com.kt.smcp.gw.ca.domn.cnvy;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.CorePrcssData;


/**
 * 현장장치전달행
 * @since	: 2015. 3. 7.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2015. 3. 7. CBJ: 최초작성
 * CnvyRow에 장치정보가 없어 트랜잭션처리를 위해 생성
 * ----------------------------------------------------
 * </PRE>
 */
public class CnvyRowTransac extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -5656543716622143784L;

	/** 통합전달데이터 */
	private ItgCnvyData itgCnvyData;
	/** 장치전달데이터 */
	private SpotDevCnvyData spotDevCnvyData;
	/** 전달행 */
	private CnvyRow cnvyRow;

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

		if(spotDevCnvyData != null)
		{
			//빈객체가 아니면
			if(!spotDevCnvyData.isEmptyData())
			{
				return false;
			}
		}

		if(cnvyRow != null)
		{
			//빈객체가 아니면
			if(!cnvyRow.isEmptyData())
			{
				return false;
			}
		}
		return true;
	}

	public ItgCnvyData getItgCnvyData() {
		return itgCnvyData;
	}

	public void setItgCnvyData(ItgCnvyData itgCnvyData) {
		this.itgCnvyData = itgCnvyData;
	}

	public SpotDevCnvyData getSpotDevCnvyData() {
		return spotDevCnvyData;
	}

	public void setSpotDevCnvyData(SpotDevCnvyData spotDevCnvyData) {
		this.spotDevCnvyData = spotDevCnvyData;
	}

	public CnvyRow getCnvyRow() {
		return cnvyRow;
	}

	public void setCnvyRow(CnvyRow cnvyRow) {
		this.cnvyRow = cnvyRow;
	}


}
