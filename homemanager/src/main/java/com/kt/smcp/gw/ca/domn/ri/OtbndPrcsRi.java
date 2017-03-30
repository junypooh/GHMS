package com.kt.smcp.gw.ca.domn.ri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.CorePrcssData;

/**
 * 인바운드프로세스자원
 * @since	: 2015. 3. 1.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2015. 3. 1. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class OtbndPrcsRi extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -191358325561329329L;

	/** 프로세스자원리스트 */
	protected List<PrcsRi> prcsRis = new ArrayList<PrcsRi>();

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
		if(prcsRis.size() != 0)
		{
			return false;
		}
		return true;
	}

	public List<PrcsRi> getPrcsRis() {
		return prcsRis;
	}

	public void setPrcsRis(List<PrcsRi> prcsRis) {
		this.prcsRis = prcsRis;
	}
}
