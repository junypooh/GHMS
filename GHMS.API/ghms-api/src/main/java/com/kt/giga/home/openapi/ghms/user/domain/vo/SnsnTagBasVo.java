/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.vo;

/**
 * 샌싱태그 VO
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 3. 9.
 */
public class SnsnTagBasVo {

	/** 센싱태그코드 */
	private String snsn_tag_cd;
	
	/** 센싱태그명 */
	private String snsn_tag_nm;

	/** 센싱태그단위코드 */
	private String snsn_tag_unit_cd;

	/** 센싱태그유형코드 */
	private String snsn_tag_type_cd;

	/** 센싱태그설명 */
	private String snsn_tag_desc;

	/** 표시순서 */
	private int indc_odrg;

	/** 확장문자열값1 */
	private String expnsn_str_val1;

	/** 확장문자열값2 */
	private String expnsn_str_val2;

	/** 확장문자열값3 */
	private String expnsn_str_val3;

	/**
	 * @return TODO
	 */
	public String getSnsn_tag_cd() {
		return snsn_tag_cd;
	}

	/**
	 * @param snsn_tag_cd TODO
	 */
	public void setSnsn_tag_cd(String snsn_tag_cd) {
		this.snsn_tag_cd = snsn_tag_cd;
	}

	/**
	 * @return TODO
	 */
	public String getSnsn_tag_nm() {
		return snsn_tag_nm;
	}

	/**
	 * @param snsn_tag_nm TODO
	 */
	public void setSnsn_tag_nm(String snsn_tag_nm) {
		this.snsn_tag_nm = snsn_tag_nm;
	}

	/**
	 * @return TODO
	 */
	public String getSnsn_tag_unit_cd() {
		return snsn_tag_unit_cd;
	}

	/**
	 * @param snsn_tag_unit_cd TODO
	 */
	public void setSnsn_tag_unit_cd(String snsn_tag_unit_cd) {
		this.snsn_tag_unit_cd = snsn_tag_unit_cd;
	}

	/**
	 * @return TODO
	 */
	public String getSnsn_tag_type_cd() {
		return snsn_tag_type_cd;
	}

	/**
	 * @param snsn_tag_type_cd TODO
	 */
	public void setSnsn_tag_type_cd(String snsn_tag_type_cd) {
		this.snsn_tag_type_cd = snsn_tag_type_cd;
	}

	/**
	 * @return TODO
	 */
	public String getSnsn_tag_desc() {
		return snsn_tag_desc;
	}

	/**
	 * @param snsn_tag_desc TODO
	 */
	public void setSnsn_tag_desc(String snsn_tag_desc) {
		this.snsn_tag_desc = snsn_tag_desc;
	}

	/**
	 * @return TODO
	 */
	public int getIndc_odrg() {
		return indc_odrg;
	}

	/**
	 * @param indc_odrg TODO
	 */
	public void setIndc_odrg(int indc_odrg) {
		this.indc_odrg = indc_odrg;
	}

	/**
	 * @return TODO
	 */
	public String getExpnsn_str_val1() {
		return expnsn_str_val1;
	}

	/**
	 * @param expnsn_str_val1 TODO
	 */
	public void setExpnsn_str_val1(String expnsn_str_val1) {
		this.expnsn_str_val1 = expnsn_str_val1;
	}

	/**
	 * @return TODO
	 */
	public String getExpnsn_str_val2() {
		return expnsn_str_val2;
	}

	/**
	 * @param expnsn_str_val2 TODO
	 */
	public void setExpnsn_str_val2(String expnsn_str_val2) {
		this.expnsn_str_val2 = expnsn_str_val2;
	}

	/**
	 * @return TODO
	 */
	public String getExpnsn_str_val3() {
		return expnsn_str_val3;
	}

	/**
	 * @param expnsn_str_val3 TODO
	 */
	public void setExpnsn_str_val3(String expnsn_str_val3) {
		this.expnsn_str_val3 = expnsn_str_val3;
	}
}
