package com.kt.giga.home.openapi.vo.cnvy;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 현장장치전달데이터 클래스
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
public class SpotDevCnvyData
{
	/** 외부시스템(앱)과 연동에 사용하는 장치 유니크 아이디 */
	private String devUUID;

	/** 서비스대상일련번호 */
	private Long svcTgtSeq;

	/** 현장장치일련번호 */
	private Long spotDevSeq;

	/** 현장장치아이디 */
	private String spotDevId;

	/** 게이트웨이 컨넥터? 컨넥션 아이디 */
	private String gwCnctId;

	/** 전달행 리스트 */
	private List<CnvyRow> cnvyRows;

	public SpotDevCnvyData(String devUUID, ArrayList<CnvyRow> cnvyRows) {
		this.devUUID = devUUID;
		this.cnvyRows = cnvyRows;
	}

	public SpotDevCnvyData() {
		this.cnvyRows = new ArrayList<CnvyRow>();
	}

	public SpotDevCnvyData(String devUUID, CnvyRow cnvyRow) {
		this.devUUID = devUUID;
		this.cnvyRows = new ArrayList<CnvyRow>();
		this.cnvyRows.add(cnvyRow);
	}

	public SpotDevCnvyData(long svcTgtSeq, long spotDevSeq, CnvyRow cnvyRow) {
		this.svcTgtSeq = svcTgtSeq;
		this.spotDevSeq = spotDevSeq;
		this.cnvyRows = new ArrayList<CnvyRow>();
		this.cnvyRows.add(cnvyRow);
	}

	public CnvyRow getFirstCnvyRow() {

		if(cnvyRows == null || cnvyRows.isEmpty())
			return null;

		return cnvyRows.get(0);
	}

	public Long getSvcTgtSeq() {
		return svcTgtSeq;
	}

	public void setSvcTgtSeq(Long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}

	public Long getSpotDevSeq() {
		return spotDevSeq;
	}

	public void setSpotDevSeq(Long spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}

	public String getSpotDevId() {
		return spotDevId;
	}

	public void setSpotDevId(String spotDevId) {
		this.spotDevId = spotDevId;
	}

	public List<CnvyRow> getCnvyRows() {
		return cnvyRows;
	}

	public void setCnvyRows(List<CnvyRow> cnvyRows) {
		this.cnvyRows = cnvyRows;
	}

	public String getDevUUID() {
		return devUUID;
	}

	public void setDevUUID(String devUUID) {
		this.devUUID = devUUID;
	}

	public String getGwCnctId() {
		return gwCnctId;
	}

	public void setGwCnctId(String gwCnctId) {
		this.gwCnctId = gwCnctId;
	}

}
