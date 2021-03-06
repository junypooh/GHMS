package com.kt.giga.home.cms.vo.row;


/**
 * 문자열데이터 클래스
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
public class StrData
{
	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 문자열값 */
	private String strVal;

	public String getSnsnTagCd() {
		return snsnTagCd;
	}

	public void setSnsnTagCd(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}

	public String getStrVal() {
		return strVal;
	}

	public void setStrVal(String strVal) {
		this.strVal = strVal;
	}

}
