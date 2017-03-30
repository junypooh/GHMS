package com.kt.giga.home.cms.vo.row;


/**
 * 스케줄시간데이터 클래스

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
public class SclgTimeData
{
	/** 시작시간(HHMMSS) */
	private String stTime;
	/** 종료시간(HHMMSS) */
	private String fnsTime;
	/** 주기(ms) */
	private Integer perdTime;
	/** 기간(ms) */
	private Integer duratTime;

	public SclgTimeData() {

	}

	public SclgTimeData(String stTime, String fnsTime)	{
		this.stTime = stTime;
		this.fnsTime = fnsTime;
	}

	public SclgTimeData(String stTime, String fnsTime, Integer perdTime, Integer duratTime)	{
		this.stTime = stTime;
		this.fnsTime = fnsTime;
		this.perdTime = perdTime;
		this.duratTime = duratTime;
	}

	public String getStTime() {
		return stTime;
	}

	public void setStTime(String stTime) {
		this.stTime = stTime;
	}

	public String getFnsTime() {
		return fnsTime;
	}

	public void setFnsTime(String fnsTime) {
		this.fnsTime = fnsTime;
	}

	public Integer getPerdTime() {
		return perdTime;
	}

	public void setPerdTime(Integer perdTime) {
		this.perdTime = perdTime;
	}

	public Integer getDuratTime() {
		return duratTime;
	}

	public void setDuratTime(Integer duratTime) {
		this.duratTime = duratTime;
	}

}
