package com.kt.giga.home.cms.vo.cnvy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kt.giga.home.cms.vo.row.Row;


/**
 * 전달행 클래스
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class CnvyRow extends Row
{
	/** 트랜잭션아이디(상위) */
	private String transacId;
	
	/** 하위트랜잭션아이디(상위) */
	private String lowTransacId;

	public String getTransacId() {
		return transacId;
	}
	public void setTransacId(String transacId) {
		this.transacId = transacId;
	}
	public String getLowTransacId() {
		return lowTransacId;
	}
	public void setLowTransacId(String lowTransacId) {
		this.lowTransacId = lowTransacId;
	}


}
