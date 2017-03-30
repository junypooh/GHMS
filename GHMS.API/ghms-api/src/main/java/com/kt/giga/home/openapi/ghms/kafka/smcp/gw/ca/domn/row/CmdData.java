package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.row;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CmdData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 4084433714216038471L;
	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 명령어값내역 */
	private byte[]	cmdValTxn;

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

	public byte[] getCmdValTxn() {
		return cmdValTxn;
	}

	public void setCmdValTxn(byte[] cmdValTxn) {
		this.cmdValTxn = cmdValTxn;
	}


}
