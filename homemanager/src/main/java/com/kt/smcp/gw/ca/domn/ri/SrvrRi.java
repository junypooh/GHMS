package com.kt.smcp.gw.ca.domn.ri;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.domn.CorePrcssData;

/**
 * 서버자원
 * @since	: 2015. 3. 1.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2015. 3. 1. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class SrvrRi extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -6197431747639010167L;

	/** 통신채널아이디 */
	protected String commChId;
	/** 생성일시  */
	protected Date cretDt;
	/** 최종수신일시 */
	protected Date lastRcvDt;
	/** 최종송신일시  */
	protected Date lastSndDt;
	/** 세션카운트  */
	protected Integer sessnCnt;

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

	public String getCommChId() {
		return commChId;
	}

	public void setCommChId(String commChId) {
		this.commChId = commChId;
	}

	public Date getCretDt() {
		return cretDt;
	}

	public void setCretDt(Date cretDt) {
		this.cretDt = cretDt;
	}

	public Date getLastRcvDt() {
		return lastRcvDt;
	}

	public void setLastRcvDt(Date lastRcvDt) {
		this.lastRcvDt = lastRcvDt;
	}

	public Date getLastSndDt() {
		return lastSndDt;
	}

	public void setLastSndDt(Date lastSndDt) {
		this.lastSndDt = lastSndDt;
	}

	public Integer getSessnCnt() {
		return sessnCnt;
	}

	public void setSessnCnt(Integer sessnCnt) {
		this.sessnCnt = sessnCnt;
	}
}
