package com.kt.giga.home.openapi.vo.row;


/**
 * 일반셋팅데이터 클래스
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
public class GenlSetupData
{
	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 설정값 */
	private String setupVal;
	
	public GenlSetupData() {
		
	}
	
	public GenlSetupData(String snsnTagCd, String setupVal)
	{
		this.snsnTagCd = snsnTagCd;
		this.setupVal = setupVal;
	}

	public String getSnsnTagCd() {
		return snsnTagCd;
	}

	public void setSnsnTagCd(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}

	public String getSetupVal() {
		return setupVal;
	}

	public void setSetupVal(String setupVal) {
		this.setupVal = setupVal;
	}

}
