package com.kt.smcp.gw.ca.domn.devmgt.frmwr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class HttpSrvr implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -1030235762228884624L;
	/** 서버주소 */
	private String srvrAdr;
	/** 메서드명 */
	private String mthdNm;
	/** 연결파라미터 */
	private List<Param> cnctParams = new ArrayList<HttpSrvr.Param>();

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

	public String getMthdNm() {
		return mthdNm;
	}

	public void setMthdNm(String mthdNm) {
		this.mthdNm = mthdNm;
	}

	public List<Param> getCnctParams() {
		return cnctParams;
	}

	public void setCnctParams(List<Param> cnctParams) {
		this.cnctParams = cnctParams;
	}

	public static class Param
	{
		/** Key */
		private String key;
		/** Value */
		private String value;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
}
