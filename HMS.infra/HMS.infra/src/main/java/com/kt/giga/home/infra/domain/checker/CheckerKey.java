/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;


/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 29.
 */
public class CheckerKey {
	
	/** 조회유형 1(서비스계약아이디), 2(KT임대단말맥주소), 3(인터넷접속번호), 4(인터넷ID)*/
	private String inType;
	
	/** InType(조회 유형)에 대한 단말 조회 값 → InType이 1이면 서비스계약아이디(SCN) */
	private String inValue;
	
	private Long svcTgtSeq;
	
	private Long spotDevSeq;
	
	/**
	 * default 생성자
	 */
	public CheckerKey(){}
	
	/**
	 * 생성자
	 * @param inType
	 * @param inValue
	 */
	public CheckerKey(String inType, String inValue) {
		this.inType = inType;
		this.inValue = inValue;
	}

	/**
	 * @return TODO
	 */
	public String getInType() {
		return inType;
	}

	/**
	 * @param inType TODO
	 */
	public void setInType(String inType) {
		this.inType = inType;
	}

	/**
	 * @return TODO
	 */
	public String getInValue() {
		return inValue;
	}

	/**
	 * @param inValue TODO
	 */
	public void setInValue(String inValue) {
		this.inValue = inValue;
	}

	/**
	 * @return TODO
	 */
	public Long getSvcTgtSeq() {
		return svcTgtSeq;
	}

	/**
	 * @param svcTgtSeq TODO
	 */
	public void setSvcTgtSeq(Long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}

	/**
	 * @return TODO
	 */
	public Long getSpotDevSeq() {
		return spotDevSeq;
	}

	/**
	 * @param spotDevSeq TODO
	 */
	public void setSpotDevSeq(Long spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}

}
