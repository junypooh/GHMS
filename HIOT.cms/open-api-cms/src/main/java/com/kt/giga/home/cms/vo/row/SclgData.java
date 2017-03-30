package com.kt.giga.home.cms.vo.row;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 스케줄데이터 클래스
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
public class SclgData
{
	/** 스케줄설정요일코드 */
	private String dowCd;
	/** 스케줄설정요일코드 */
	private List<SclgTimeData> sclgTimeDatas = new ArrayList<SclgTimeData>();

	public SclgData() {

	}

	public SclgData(String dowCd) {
		this.dowCd = dowCd;
	}

	public SclgData(String dowCd, String stTime, String fnsTime) {
		this.dowCd = dowCd;
		addSclgTime(stTime, fnsTime);
	}

	public String getDowCd() {
		return dowCd;
	}

	public void setDowCd(String dowCd) {
		this.dowCd = dowCd;
	}

	public List<SclgTimeData> getSclgTimeDatas() {
		return sclgTimeDatas;
	}

	@JsonIgnore
	public SclgTimeData getFirstSclgTimeData() {
		if(sclgTimeDatas == null || sclgTimeDatas.isEmpty()) {
			return null;
		} else {
			return sclgTimeDatas.get(0);
		}
	}

	public void setSclgTimeDatas(List<SclgTimeData> sclgTimeDatas) {
		this.sclgTimeDatas = sclgTimeDatas;
	}

	public void addSclgTimeData(SclgTimeData sclgTimeData) {
		sclgTimeDatas.add(sclgTimeData);
	}

	public void addSclgTime(String stTime, String fnsTime) {
		addSclgTimeData(new SclgTimeData(stTime, fnsTime));
	}

	public void addSclgTime(String stTime, String fnsTime, Integer perdTime, Integer duratTime) {
		addSclgTimeData(new SclgTimeData(stTime, fnsTime, perdTime, duratTime));
	}

}
