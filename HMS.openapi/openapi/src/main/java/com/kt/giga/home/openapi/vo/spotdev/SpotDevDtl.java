package com.kt.giga.home.openapi.vo.spotdev;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotDevDtl
{
	/** 애트리뷰트명 */
	private String atribNm;
	/** 애트리뷰트값 */
	private String atribVal;

	public SpotDevDtl()	{

	}

	public SpotDevDtl(SpotDevDtl spotDevDtl) {
		this.atribNm = spotDevDtl.getAtribNm();
		this.atribVal = spotDevDtl.getAtribVal();
	}
	
	public SpotDevDtl(String atribNm, String atribVal) {
		this.atribNm = atribNm;
		this.atribVal = atribVal;
	}

	public String getAtribNm() {
		return atribNm;
	}

	public void setAtribNm(String atribNm) {
		this.atribNm = atribNm;
	}

	public String getAtribVal() {
		return atribVal;
	}

	public void setAtribVal(String atribVal) {
		this.atribVal = atribVal;
	}

}
