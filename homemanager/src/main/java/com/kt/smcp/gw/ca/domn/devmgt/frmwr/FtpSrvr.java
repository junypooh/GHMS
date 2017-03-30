package com.kt.smcp.gw.ca.domn.devmgt.frmwr;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class FtpSrvr implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -8914776785255706224L;

	/** 서버주소 */
	private String srvrAdr;
	/** 포트번호 */
	private Integer portNo;
	/** 아이디 */
	private String id;
	/** 패스워드 */
	private String pwd;

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


	public String getSrvrAdr() {
		return srvrAdr;
	}
	public void setSrvrAdr(String srvrAdr) {
		this.srvrAdr = srvrAdr;
	}
	public Integer getPortNo() {
		return portNo;
	}
	public void setPortNo(Integer portNo) {
		this.portNo = portNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


}
