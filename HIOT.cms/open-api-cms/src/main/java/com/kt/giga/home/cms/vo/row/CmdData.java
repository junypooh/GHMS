package com.kt.giga.home.cms.vo.row;


public class CmdData
{
	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 명령어값내역 */
	private byte[]	cmdValTxn;
	
	public CmdData() {
		
	}
	
	public CmdData(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}
	
	public CmdData(String snsnTagCd, byte[] cmdValTxn) {
		this.snsnTagCd = snsnTagCd;
		this.cmdValTxn = cmdValTxn;
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
