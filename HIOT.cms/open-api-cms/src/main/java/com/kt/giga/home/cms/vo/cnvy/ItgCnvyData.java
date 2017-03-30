package com.kt.giga.home.cms.vo.cnvy;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 통합전달데이터

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
public class ItgCnvyData
{
	/** 현장장치전달데이터리스트 */
	private List<SpotDevCnvyData> spotDevCnvyDatas = null;
	
	public ItgCnvyData(List<SpotDevCnvyData> spotDevCnvyDatas) {
		this.spotDevCnvyDatas = spotDevCnvyDatas;
	}

	public ItgCnvyData(SpotDevCnvyData spotDevCnvyData) {
		this(new ArrayList<SpotDevCnvyData>());
		this.spotDevCnvyDatas.add(spotDevCnvyData);
	}
	
	public ItgCnvyData() {
		this(new ArrayList<SpotDevCnvyData>());
	}

	public List<SpotDevCnvyData> getSpotDevCnvyDatas() {
		return spotDevCnvyDatas;
	}
	
	public SpotDevCnvyData getFirstSpotDevCnvyData() {
		
		if(spotDevCnvyDatas == null || spotDevCnvyDatas.isEmpty())
			return null;

		return spotDevCnvyDatas.get(0);
	}
	
	public CnvyRow getFirstSpotDevCnvyRow() {
		
		SpotDevCnvyData spotDevCnvyData = getFirstSpotDevCnvyData();
		if(spotDevCnvyData == null)
			return null;
		
		return spotDevCnvyData.getFirstCnvyRow();
	}

	public void setSpotDevCnvyDatas(List<SpotDevCnvyData> spotDevCnvyDatas) {
		this.spotDevCnvyDatas = spotDevCnvyDatas;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE); 
	}
}
