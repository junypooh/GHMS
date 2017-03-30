package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.spotdev;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.InfoUpdType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.CorePrcssData;

/**
 * 현장장치업데이트
 * @since	: 2014. 11. 10.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 10. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class SpotDevUdate extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -6210401667302941830L;

	/**요청API서버아이디  */
	private String reqApiSrvrId;
	/**요청EC서버아이디  */
	private String reqEcSrvrId;
	/**정보갱신유형  */
	private InfoUpdType infoUpdTypeCd;
	/** 현장장치리스트  */
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

	public InfoUpdType getInfoUpdTypeCd() {
		return infoUpdTypeCd;
	}

	public void setInfoUpdTypeCd(InfoUpdType infoUpdTypeCd) {
		this.infoUpdTypeCd = infoUpdTypeCd;
	}


}
