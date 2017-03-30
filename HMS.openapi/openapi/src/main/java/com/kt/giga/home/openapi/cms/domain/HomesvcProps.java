package com.kt.giga.home.openapi.cms.domain;


/**
 * 앱초기화 변수 클래스
 * 
 * @author 조상현
 *
 */

public class HomesvcProps {
	private String	svccode;			// 서비스 코드
	private String	name;				// 변수명
	private String	value;				// 변수값
	private String	status = "1";		// 상태
	private String	cret_dt;			// 등록일
	
	/**
	 * @return the svccode
	 */
	public String getSvccode() {
		return svccode;
	}
	/**
	 * @param svccode the svccode to set
	 */
	public void setSvccode(String svccode) {
		this.svccode = svccode;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the cret_dt
	 */
	public String getCret_dt() {
		return cret_dt;
	}
	/**
	 * @param cret_dt the cret_dt to set
	 */
	public void setCret_dt(String cret_dt) {
		this.cret_dt = cret_dt;
	}
	

}
