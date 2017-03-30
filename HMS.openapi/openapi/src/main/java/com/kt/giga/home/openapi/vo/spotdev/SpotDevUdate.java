package com.kt.giga.home.openapi.vo.spotdev;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * 현장장치업데이트
 * @since	: 2014. 11. 10.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 10. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotDevUdate
{
	private List<SpotDev> spotDevs = new ArrayList<SpotDev>();
	/** 요청 API서버 아이디 */
	private String reqApiSrvrId;
	/** 요청 EC서버 아이디 */
	private String reqEcSrvrId;

	public List<SpotDev> getSpotDevs() {
		return spotDevs;
	}
	
	@JsonIgnore
	public SpotDev getFirstSpotDev() {
		if(spotDevs == null || spotDevs.isEmpty()) {
			return null;
		}

		return spotDevs.get(0);
	}

	public void setSpotDevs(List<SpotDev> spotDevs) {
		this.spotDevs = spotDevs;
	}

	public void addSpotDev(SpotDev spotDev) {
		this.spotDevs.add(spotDev);
	}

	public String getReqApiSrvrId() {
		return reqApiSrvrId;
	}

	public void setReqApiSrvrId(String reqApiSrvrId) {
		this.reqApiSrvrId = reqApiSrvrId;
	}

	public String getReqEcSrvrId() {
		return reqEcSrvrId;
	}

	public void setReqEcSrvrId(String reqEcSrvrId) {
		this.reqEcSrvrId = reqEcSrvrId;
	}
}
