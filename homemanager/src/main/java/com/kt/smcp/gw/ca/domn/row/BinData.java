package com.kt.smcp.gw.ca.domn.row;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 바이너리데이터 클래스
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class BinData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -1815366275274997742L;

	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 이진값내역 */
	private byte[]	binValTxn;

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

	public String getSnsnTagCd() {
		return snsnTagCd;
	}

	public void setSnsnTagCd(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}

	public byte[] getBinValTxn() {
		return binValTxn;
	}

	public void setBinValTxn(byte[] binValTxn) {
		this.binValTxn = binValTxn;
	}


}
