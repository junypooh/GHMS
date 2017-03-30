package com.kt.giga.home.openapi.vo.cnvy;

import java.util.ArrayList;
import java.util.List;

/**
 * 게이트웨이전달데이터 클래스

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
public class GwCnvyData
{
	/** 서비스대상일련번호 */
	private Long svcTgtSeq;
	/** 게이트웨이연결아이디 */
	private String gwCnctId;

	/** 전달행 리스트 */
	private List<CnvyRow> cnvyRows = new ArrayList<CnvyRow>();

	public Long getSvcTgtSeq() {
		return svcTgtSeq;
	}

	public void setSvcTgtSeq(Long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}

	public String getGwCnctId() {
		return gwCnctId;
	}

	public void setGwCnctId(String gwCnctId) {
		this.gwCnctId = gwCnctId;
	}

	public List<CnvyRow> getCnvyRows() {
		return cnvyRows;
	}

	public void setCnvyRows(List<CnvyRow> cnvyRows) {
		this.cnvyRows = cnvyRows;
	}

}
