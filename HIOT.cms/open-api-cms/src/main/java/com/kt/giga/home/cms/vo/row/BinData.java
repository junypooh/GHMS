package com.kt.giga.home.cms.vo.row;


/**
 * 바이너리데이터 클래스
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * 2014. 11.16. 조홍진: OpenAPI 커스토마이징  
 * ----------------------------------------------------
 * </PRE>
 */
public class BinData
{
	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 이진값내역 */
	private byte[]	binValTxn;

	public String getSnsnTagCd() {
		return snsnTagCd;
	}

	public void setSnsnTagCd(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}

	public byte[] getBinValTxn() {
		return binValTxn;
	}

	public void setBinValTxn(byte[] binValTxn) {
		this.binValTxn = binValTxn;
	}

}
