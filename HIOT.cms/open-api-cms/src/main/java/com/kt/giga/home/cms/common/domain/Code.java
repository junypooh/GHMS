package com.kt.giga.home.cms.common.domain;


/**
 * 기초코드 클래스
 * 
 * @author 김용성
 *
 */

public class Code {
	
	private String	code;		// 기초코드
	
	private String	upCode;		// 상위코드
	
	private String	name;		// 코드이름
	
	private String	useYN;		// 코드 사용여부 YN
	
	private String	memo;		// 코드 설명
	
	/**
	 * @return 기초코드
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @param 기초코드
	 */
	public void setCode(String code) {
		this.code = code;
	}	
	
	/**
	 * @return 상위코드
	 */
	public String getUpCode() {
		return upCode;
	}
	
	/**
	 * @param 상위코드
	 */
	public void setUpCode(String upCode) {
		this.upCode = upCode;
	}

	/**
	 * @return 코드이름
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param 코드이름
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return 코드 사용여부 YN
	 */
	public String getUseYN() {
		return useYN;
	}
	
	/**
	 * @param 코드 사용여부 YN
	 */
	public void setUseYN(String useYN) {
		this.useYN = useYN;
	}
	
	/**
	 * @return 코드 설명
	 */
	public String getMemo() {
		return memo;
	}
	
	/**
	 * @param 코드 설명
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
