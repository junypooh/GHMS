package com.kt.smcp.gw.ca.domn.ri;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.CorePrcssData;

/**
 * 트랜잭션자원
 * @since	: 2015. 3. 1.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2015. 3. 1. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class TransacRi extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -4750528859631463780L;

	/** 상위트랜잭션자원 */
	protected UpSysTransacRi upSysTransacRi;
	/** 하위트랜잭션자원 */
	protected LowSysTransacRi lowSysTransacRi;

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
		if(upSysTransacRi != null)
		{
			return false;
		}

		if(lowSysTransacRi != null)
		{
			return false;
		}
		return true;
	}

	public UpSysTransacRi getUpSysTransacRi() {
		return upSysTransacRi;
	}

	public void setUpSysTransacRi(UpSysTransacRi upSysTransacRi) {
		this.upSysTransacRi = upSysTransacRi;
	}

	public LowSysTransacRi getLowSysTransacRi() {
		return lowSysTransacRi;
	}

	public void setLowSysTransacRi(LowSysTransacRi lowSysTransacRi) {
		this.lowSysTransacRi = lowSysTransacRi;
	}


}
