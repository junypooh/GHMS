package com.kt.smcp.gw.ca.domn.ri;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.CorePrcssData;

/**
 * 어댑터자원객체
 * @since	: 2015. 3. 1.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2015. 3. 1. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class AdapRi extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -2978921607139491278L;

	/** 어댑터아이디 */
	protected String adaptorId;
	/** 통합통신채널자원 */
	protected ItgCommChRi itgCommChRi;
	/** 인바운드프로세서자원 */
	protected InbndPrcsRi inbndPrcsRi;
	/** 아웃바운드프로세서자원 */
	protected OtbndPrcsRi otbndPrcsRi;

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
		if(itgCommChRi != null)
		{
			return false;
		}

		if(inbndPrcsRi != null)
		{
			return false;
		}

		if(otbndPrcsRi != null)
		{
			return false;
		}
		return true;
	}

	public String getAdaptorId() {
		return adaptorId;
	}

	public void setAdaptorId(String adaptorId) {
		this.adaptorId = adaptorId;
	}

	public ItgCommChRi getItgCommChRi() {
		return itgCommChRi;
	}

	public void setItgCommChRi(ItgCommChRi itgCommChRi) {
		this.itgCommChRi = itgCommChRi;
	}

	public InbndPrcsRi getInbndPrcsRi() {
		return inbndPrcsRi;
	}

	public void setInbndPrcsRi(InbndPrcsRi inbndPrcsRi) {
		this.inbndPrcsRi = inbndPrcsRi;
	}

	public OtbndPrcsRi getOtbndPrcsRi() {
		return otbndPrcsRi;
	}

	public void setOtbndPrcsRi(OtbndPrcsRi otbndPrcsRi) {
		this.otbndPrcsRi = otbndPrcsRi;
	}


}
