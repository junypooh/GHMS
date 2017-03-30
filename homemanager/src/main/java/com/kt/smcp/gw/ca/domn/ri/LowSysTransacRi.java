package com.kt.smcp.gw.ca.domn.ri;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.CorePrcssData;

/**
 * 하위트랜잭션자원
 * @since	: 2015. 3. 1.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2015. 3. 1. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class LowSysTransacRi extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -4679853018355509034L;

	/** 하위시스템수신트랜잭션카운트 */
	protected Integer lowSysRcvTransacCnt;
	/** 상위시스템수신트랜잭션카운트 */
	protected Integer upSysRcvTransacCnt;

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
		return false;
	}

	public Integer getLowSysRcvTransacCnt() {
		return lowSysRcvTransacCnt;
	}

	public void setLowSysRcvTransacCnt(Integer lowSysRcvTransacCnt) {
		this.lowSysRcvTransacCnt = lowSysRcvTransacCnt;
	}

	public Integer getUpSysRcvTransacCnt() {
		return upSysRcvTransacCnt;
	}

	public void setUpSysRcvTransacCnt(Integer upSysRcvTransacCnt) {
		this.upSysRcvTransacCnt = upSysRcvTransacCnt;
	}


}
