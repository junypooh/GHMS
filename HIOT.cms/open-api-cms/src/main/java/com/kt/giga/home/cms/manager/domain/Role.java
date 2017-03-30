package com.kt.giga.home.cms.manager.domain;

/**
 * 역할
 * @author 김용성
 *
 */
public class Role {
	
	private int role;
	private String name;
	private String description;
	private String useYN;
	
	/**
	 * 
	 * @return 역할 PK
	 */
	public int getRole() {
		return role;
	}
	
	/**
	 * 
	 * @param role 역할 PK
	 */
	public void setRole(int role) {
		this.role = role;
	}
	
	/**
	 * 
	 * @return 역할 이름
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name 역할 이름
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return 역할 설명
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 
	 * @param description 역할 설명
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 
	 * @return 역할 사용여부
	 */
	public String getUseYN() {
		return useYN;
	}
	
	/**
	 * 
	 * @param useYN 역할 사용여부
	 */
	public void setUseYN(String useYN) {
		this.useYN = useYN;
	}
	
	
}
