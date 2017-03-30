package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.net;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.CorePrcssData;

public class ChKeepAlive extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 5924067136295683415L;
	/** 세션 Key  */
	private String sessionKey;
	/** 생존연장시간(ms) */
	private Long aliveExtendTime;

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

	/**
	 * @return the sessionKey
	 */
	public String getSessionKey() {
		return sessionKey;
	}
	/**
	 * @param sessionKey the sessionKey to set
	 */
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	/**
	 * @return the aliveExtendTime
	 */
	public Long getAliveExtendTime() {
		return aliveExtendTime;
	}
	/**
	 * @param aliveExtendTime the aliveExtendTime to set
	 */
	public void setAliveExtendTime(Long aliveExtendTime) {
		this.aliveExtendTime = aliveExtendTime;
	}

	@Override
	public boolean isEmptyData()
	{
		return false;
	}
}
