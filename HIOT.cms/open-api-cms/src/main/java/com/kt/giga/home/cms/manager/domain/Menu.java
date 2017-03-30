package com.kt.giga.home.cms.manager.domain;

/**
 * 메뉴
 * @author 김용성
 *
 */
public class Menu {

	private String orgMenu = "";
	private String menu;
	private String upMenu;
	private String name;
	private String useYN;
	private String url;
	private int sortNo;
	
	/**
	 * 
	 * @return Original 메뉴코드
	 */
	public String getOrgMenu() {
		return orgMenu;
	}
	
	/**
	 * 
	 * @param orgMenu Original 메뉴코드
	 */
	public void setOrgMenu(String orgMenu) {
		this.orgMenu = orgMenu;
	}
	
	/**
	 * 
	 * @return 메뉴코드
	 */
	public String getMenu() {
		return menu;
	}
	
	/**
	 * 
	 * @param menu 메뉴코드
	 */
	public void setMenu(String menu) {
		this.menu = menu;
	}
	
	/**
	 * 
	 * @return 상위 메뉴코드
	 */
	public String getUpMenu() {
		return upMenu;
	}
	
	/**
	 * 
	 * @param upMenu 상위 메뉴코드
	 */
	public void setUpMenu(String upMenu) {
		this.upMenu = upMenu;
	}
	
	/**
	 * 
	 * @return 메뉴이름
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name 메뉴이름
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return 메뉴 노출여부
	 */
	public String getUseYN() {
		return useYN;
	}
	
	/**
	 * 
	 * @param useYN 메뉴 노출여부
	 */
	public void setUseYN(String useYN) {
		this.useYN = useYN;
	}
	
	/**
	 * 
	 * @return 메뉴 URL
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * 
	 * @param url 메뉴 URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 
	 * @return 메뉴 동일 Level 정렬순서
	 */
	public int getSortNo() {
		return sortNo;
	}
	
	/**
	 * 
	 * @param sortNo 메뉴 동일 Level 정렬순서
	 */
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
}
