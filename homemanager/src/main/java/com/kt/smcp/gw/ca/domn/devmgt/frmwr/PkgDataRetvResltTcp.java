package com.kt.smcp.gw.ca.domn.devmgt.frmwr;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.CorePrcssData;

/**
 * 펌웨어정보응답
 * @since	: 2015. 1. 17.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2015. 1. 17. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class PkgDataRetvResltTcp extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -4522035720701152696L;

	private byte[] binData;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	public byte[] getBinData() {
		return binData;
	}
	public void setBinData(byte[] binData) {
		this.binData = binData;
	}
	@Override
	public boolean isEmptyData()
	{
		return false;
	}


}
