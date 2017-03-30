package com.kt.smcp.gw.ca.domn.cnvy;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.row.Row;

/**
 * 전달행 클래스
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class CnvyRow extends Row implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -1228830646666890564L;

	/** 트랜잭션아이디(상위) */
	private String transacId;
	/** 하위트랜잭션아이디(상위) */
	private String lowTransacId;

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

	public String getTransacId() {
		return transacId;
	}
	public void setTransacId(String transacId) {
		this.transacId = transacId;
	}
	public String getLowTransacId() {
		return lowTransacId;
	}
	public void setLowTransacId(String lowTransacId) {
		this.lowTransacId = lowTransacId;
	}


}
