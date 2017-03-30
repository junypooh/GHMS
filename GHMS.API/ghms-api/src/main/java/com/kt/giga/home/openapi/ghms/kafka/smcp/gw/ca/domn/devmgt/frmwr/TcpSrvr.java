package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.devmgt.frmwr;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TcpSrvr implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 6487265654041448050L;

	/** IP주소 */
	private String ipadr;
	/** 포트번호 */
	private Integer portNo;

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

	public String getIpadr() {
		return ipadr;
	}
	public void setIpadr(String ipadr) {
		this.ipadr = ipadr;
	}
	public Integer getPortNo() {
		return portNo;
	}
	public void setPortNo(Integer portNo) {
		this.portNo = portNo;
	}

}
