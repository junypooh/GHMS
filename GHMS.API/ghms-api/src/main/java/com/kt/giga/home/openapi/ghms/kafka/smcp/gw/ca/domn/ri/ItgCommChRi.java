package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.ri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.CorePrcssData;

/**
 * 통신채널자원
 * @since	: 2015. 3. 1.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2015. 3. 1. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class ItgCommChRi extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 2352708702790452986L;

	/** 서버자원 리스트 */
	protected List<SrvrRi> srvrRis = new ArrayList<SrvrRi>();
	/** 클라이언트자원 리스트 */
	protected List<ClntRi> cntRi = new ArrayList<ClntRi>();

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
		if(srvrRis.size() != 0)
		{
			return false;
		}

		if(cntRi.size() != 0)
		{
			return false;
		}
		return true;
	}

	public List<SrvrRi> getSrvrRis() {
		return srvrRis;
	}

	public void setSrvrRis(List<SrvrRi> srvrRis) {
		this.srvrRis = srvrRis;
	}

	public List<ClntRi> getCntRi() {
		return cntRi;
	}

	public void setCntRi(List<ClntRi> cntRi) {
		this.cntRi = cntRi;
	}


}
