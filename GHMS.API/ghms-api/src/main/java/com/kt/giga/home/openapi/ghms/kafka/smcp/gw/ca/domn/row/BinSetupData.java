package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BinSetupData implements Serializable, Cloneable
{
	private String snsnTagCd;
	private byte[] setupVal;


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

	public final String getSnsnTagCd() {
		return snsnTagCd;
	}

	public final void setSnsnTagCd(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}

	public final byte[] getSetupVal() {
		return setupVal;
	}

	public final void setSetupVal(byte[] setupVal) {
		this.setupVal = setupVal;
	}


}
