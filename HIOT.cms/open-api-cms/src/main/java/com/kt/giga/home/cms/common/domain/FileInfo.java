package com.kt.giga.home.cms.common.domain;

/**
 * 파일정보
 * @author 김용성
 *
 */
public class FileInfo {
	
	private int file;
	private String table;
	private int unique;
	private String description;
	private String urlPath;
	private String virtualPath;
	private String realPath;
	private String orgName;
	private String saveName;
	private String size;
	private String ext;
	private int sortNo;
	
	/**
	 * 
	 * @return 파일 정보 PK
	 */
	public int getFile() {
		return file;
	}
	
	/**
	 * 
	 * @param file 파일 정보 PK
	 */
	public void setFile(int file) {
		this.file = file;
	}
	
	/**
	 * 
	 * @return 대상 테이블
	 */
	public String getTable() {
		return table;
	}
	
	/**
	 * 
	 * @param table 대상 테이블
	 */
	public void setTable(String table) {
		this.table = table;
	}
	
	/**
	 * 
	 * @return 대상 테이블내 Unique Key
	 */
	public int getUnique() {
		return unique;
	}
	
	/**
	 * 
	 * @param unique 대상 테이블내 Unique Key
	 */
	public void setUnique(int unique) {
		this.unique = unique;
	}
	
	/**
	 * 
	 * @return 파일 설명
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 
	 * @param description 파일 설명
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 
	 * @return 파일의 웹 경로
	 */
	public String getUrlPath() {
		return urlPath;
	}
	
	/**
	 * 
	 * @param urlPath 파일의 웹 경로
	 */
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	
	/**
	 * 
	 * @return 파일의 가상 경로
	 */
	public String getVirtualPath() {
		return virtualPath;
	}
	
	/**
	 * 
	 * @param virtualPath 파일의 가상 경로
	 */
	public void setVirtualPath(String virtualPath) {
		this.virtualPath = virtualPath;
	}
	
	/**
	 * 
	 * @return 파일의 물리적 경로
	 */
	public String getRealPath() {
		return realPath;
	}
	
	/**
	 * 
	 * @param realPath 파일의 물리적 경로
	 */
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	
	/**
	 * 
	 * @return 파일 원본 이름
	 */
	public String getOrgName() {
		return orgName;
	}
	
	/**
	 * 
	 * @param orgName 파일 원본 이름
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	/**
	 * 
	 * @return 파일의 저장된 이름
	 */
	public String getSaveName() {
		return saveName;
	}
	
	/**
	 * 
	 * @param saveName 파일 저장된 이름
	 */
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	
	/**
	 * 
	 * @return 파일 사이즈
	 */
	public String getSize() {
		return size;
	}
	
	/**
	 * 
	 * @param size 파일 사이즈
	 */
	public void setSize(String size) {
		this.size = size;
	}
	
	/**
	 * 
	 * @return 파일 확장자
	 */
	public String getExt() {
		return ext;
	}
	
	/**
	 * 
	 * @param ext 파일 확장자
	 */
	public void setExt(String ext) {
		this.ext = ext;
	}
	
	/**
	 * 
	 * @return 정렬 순서
	 */
	public int getSortNo() {
		return sortNo;
	}
	
	/**
	 * 
	 * @param sortNo 정렬 순서
	 */
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
}
