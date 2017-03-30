package com.kt.giga.home.cms.vo.row;

import java.util.Date;


/**
 * 일시데이터
 * @since	: 2014. 11. 19.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 19. CBJ: 최초작성
 * 2014. 12.  1. 조홍진: OpenAPI 커스토마이징. 
 * 어느날 갑자기 서버 에러를 일으키며 나타난 클래스
 * ----------------------------------------------------
 * </PRE>
 */
public class DtData
{
	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 일시값 */
	private Date dtVal;

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

	public Date getDtVal() {
		return dtVal;
	}

	public void setDtVal(Date dtVal) {
		this.dtVal = dtVal;
	}


}