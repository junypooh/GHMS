package com.kt.giga.home.openapi.vo.row;


/**
 * 컨트롤데이터 클래스
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
public class ContlData
{
	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 제어값 */
	private Double contlVal;
	
	public ContlData(String snsnTagCd, Double contlVal) {
		this.snsnTagCd = snsnTagCd;
		this.contlVal = contlVal == null ? 0.0 : contlVal;
	}
	
	public ContlData(String snsnTagCd) {
		this(snsnTagCd, null);
	}
	
	public ContlData() {
		
	}
	
	public String getSnsnTagCd() {
		return snsnTagCd;
	}

	public void setSnsnTagCd(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}

	public Double getContlVal() {
		return contlVal;
	}

	public void setContlVal(Double contlVal) {
		this.contlVal = contlVal;
	}

}
