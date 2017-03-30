package com.kt.smcp.gw.ca.domn.ri;

import java.io.Serializable;

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
public class PrcsRi extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -6598140888812354101L;

	/** 프로세스아이디 */
	protected String prcssId;
	/** 워커스레드수 */
	protected Integer wrkrThrCnt;
	/** 큐최대크기 */
	protected Integer queueMaxSize;
	/** 큐현재수 */
	protected Integer queueNowCnt;

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


	public String getPrcssId() {
		return prcssId;
	}

	public void setPrcssId(String prcssId) {
		this.prcssId = prcssId;
	}

	public Integer getWrkrThrCnt() {
		return wrkrThrCnt;
	}

	public void setWrkrThrCnt(Integer wrkrThrCnt) {
		this.wrkrThrCnt = wrkrThrCnt;
	}

	public Integer getQueueMaxSize() {
		return queueMaxSize;
	}

	public void setQueueMaxSize(Integer queueMaxSize) {
		this.queueMaxSize = queueMaxSize;
	}

	public Integer getQueueNowCnt() {
		return queueNowCnt;
	}

	public void setQueueNowCnt(Integer queueNowCnt) {
		this.queueNowCnt = queueNowCnt;
	}


}
