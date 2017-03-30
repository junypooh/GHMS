package com.kt.giga.home.cms.vo.row;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 스케줄셋팅데이터
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
public class SclgSetupData
{
	/** 센싱태그코드 */
	private String snsnTagCd;
	/** 스케줄데이터리스트 */
	private List<SclgData> sclgDatas = new ArrayList<SclgData>();
	
	public SclgSetupData() {
		
	}
	
	public SclgSetupData(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}
	
	public SclgSetupData(String snsnTagCd, String stTime, String fnsTime, String[] dowCds) {
		this(snsnTagCd);
		
		if(dowCds != null) {
			for(String dowCd : dowCds) {
				addSclgData(new SclgData(dowCd, stTime, fnsTime));
			}
		}
	}
	
	public SclgSetupData(String snsnTagCd, String stTime, String fnsTime, int[] dowCds) {
		this(snsnTagCd);

		if(dowCds != null) {
			for(int dowCd : dowCds) {
				addSclgData(new SclgData(String.valueOf(dowCd), stTime, fnsTime));
			}
		}
	}		


	public String getSnsnTagCd() {
		return snsnTagCd;
	}

	public void setSnsnTagCd(String snsnTagCd) {
		this.snsnTagCd = snsnTagCd;
	}

	public List<SclgData> getSclgDatas() {
		return sclgDatas;
	}
	
	public String getTimeBaseSetupVal(String delimiter) {
		
		if(sclgDatas == null || sclgDatas.isEmpty())
			return "";
		
		String timeValue = "", dowValue = "";
		for(int i=0;i<sclgDatas.size();i++) {
			
			SclgData sclgData = sclgDatas.get(i);
			String dowCd = sclgData.getDowCd();
			List<SclgTimeData> sclgTimeDatas = sclgData.getSclgTimeDatas();
			
			if(sclgTimeDatas == null || sclgTimeDatas.isEmpty())
				continue;
			
			SclgTimeData sclgTimeData = sclgTimeDatas.get(0);
			timeValue = sclgTimeData.getStTime() + delimiter + sclgTimeData.getFnsTime();
			dowValue = dowValue + dowCd;
		}
		
		return timeValue + delimiter + dowValue;
	}

	public void setSclgDatas(List<SclgData> sclgDatas) {
		this.sclgDatas = sclgDatas;
	}
	
	public void addSclgData(SclgData sclgData) {
		this.sclgDatas.add(sclgData);
	}
	
}
