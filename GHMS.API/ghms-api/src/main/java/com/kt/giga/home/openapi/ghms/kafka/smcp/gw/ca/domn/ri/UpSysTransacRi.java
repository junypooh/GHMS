package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.ri;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.CorePrcssData;

/**
 * 상위트랜잭션자원
 * @since	: 2015. 3. 1.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2015. 3. 1. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class UpSysTransacRi extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 8567868744339100013L;

	/** 상위시스템수신트랜잭션카운트 */
	protected Integer upSysRcvTransacCnt;
	/** 하위시스템수신트랜잭션카운트 */
	protected Integer lowSysRcvTransacCnt;

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

	public Integer getUpSysRcvTransacCnt() {
		return upSysRcvTransacCnt;
	}

	public void setUpSysRcvTransacCnt(Integer upSysRcvTransacCnt) {
		this.upSysRcvTransacCnt = upSysRcvTransacCnt;
	}

	public Integer getLowSysRcvTransacCnt() {
		return lowSysRcvTransacCnt;
	}

	public void setLowSysRcvTransacCnt(Integer lowSysRcvTransacCnt) {
		this.lowSysRcvTransacCnt = lowSysRcvTransacCnt;
	}


}
