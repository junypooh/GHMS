package com.kt.giga.home.cms.manager.domain;

public class SiteMapPath 
{
	private String mainMenuCode;
	
	/**
	 * @return 현재 메인메뉴 코드
	 */
	public String getMainMenuCode()
	{
		return this.mainMenuCode;
	}
	
	/**
	 * @param mainMenuCode 현재 메인메뉴 코드
	 */
	public void setMainMenuCode(String mainMenuCode)
	{
		this.mainMenuCode = mainMenuCode;
	}
	
	private String mainMenuName;
	
	/**
	 * @return 현재 메인메뉴 이름
	 */
	public String getMainMenuName()
	{
		return this.mainMenuName;
	}
	
	/**
	 * @param mainMenuName 현재 메인메뉴 이름
	 */
	public void setMainMenuName(String mainMenuName)
	{
		this.mainMenuName = mainMenuName;
	}
	
	private String subMenuCode;
	
	/**
	 * @return 현재 서브메뉴 코드
	 */
	public String getSubMenuCode()
	{
		return this.subMenuCode;
	}
	
	/**
	 * @param subMenuCode 현재 서브메뉴 코드
	 */
	public void setSubMenuCode(String subMenuCode)
	{
		this.subMenuCode = subMenuCode;
	}
	
	private String subMenuName;
	
	/**
	 * @return 현재 서브메뉴 이름
	 */
	public String getSubMenuName()
	{
		return this.subMenuName;
	}
	
	/**
	 * @param subMenuName 현재 서브메뉴 이름
	 */
	public void setSubMenuName(String subMenuName)
	{
		this.subMenuName = subMenuName;
	}
	
	public SiteMapPath() {}
	
	/**
	 * @param mainMenuCode 현재 메인메뉴 코드
	 * @param mainMenuName 현재 메인메뉴 이름
	 * @param subMenuCode 현재 서브메뉴 코드
	 * @param subMenuName 현재 서브메뉴 이름
	 */
	public SiteMapPath(String mainMenuCode, String mainMenuName, String subMenuCode, String subMenuName)
	{
		this.mainMenuCode = mainMenuCode;
		this.mainMenuName = mainMenuName;
		this.subMenuCode = subMenuCode;
		this.subMenuName = subMenuName;
	}
}
