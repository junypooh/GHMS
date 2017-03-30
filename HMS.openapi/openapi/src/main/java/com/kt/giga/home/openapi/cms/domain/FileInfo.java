package com.kt.giga.home.openapi.cms.domain;


/**
 * 업로드 이미지및 파일 정보 클래스
 * 
 * @author 한란민
 *
 */

public class FileInfo {
	private String	pk_file;				/*일련번호*/
	private String	fk_table;				/*테이블명*/
	private String	fk_unique;			/*해당 테이블 PK*/
	private String	fd_description;		/*설명*/
	private String	fd_url_path;		/*URL 경로*/
	private String fd_virutal_path;	/*서버 저장 경로*/
	private String fd_real_path;		/*업로드하기전 단말기 경로*/
	private String fd_org_name;		/*업로드하기전 파일명*/
	private String fd_save_name;	/*서버 저장 파일명*/
	private String fd_size;				/*파일 크기*/
	private String fd_ext;				/*파일 확장명*/
	private String fd_sort_no;			/*업로드순서*/
	
	/**
	 * 
	 * @return pk_file
	 */
	public String getPk_file() {
		return pk_file;
	}
	
	/**
	 * 
	 * @param pk_file
	 */
	public void setPk_file(String pk_file) {
		this.pk_file = pk_file;
	}
	
	/**
	 * 
	 * @return fk_table
	 */
	public String getFk_table() {
		return fk_table;
	}
	
	/**
	 * 
	 * @param fk_table
	 */
	public void setFk_table(String fk_table) {
		this.fk_table = fk_table;
	}
	
	/**
	 * 
	 * @return fk_unique
	 */
	public String getFk_unique() {
		return fk_unique;
	}
	
	/**
	 * 
	 * @param fk_unique
	 */
	public void setFk_unique(String fk_unique) {
		this.fk_unique = fk_unique;
	}
	
	/**
	 * 
	 * @return fd_description
	 */
	public String getFd_description() {
		return fd_description;
	}
	
	/**
	 * 
	 * @param fd_description
	 */
	public void setFd_description(String fd_description) {
		this.fd_description = fd_description;
	}
	
	/**
	 * 
	 * @return fd_url_path
	 */
	public String getFd_url_path() {
		return fd_url_path;
	}
	
	/**
	 * 
	 * @param fd_url_path
	 */
	public void setFd_url_path(String fd_url_path) {
		this.fd_url_path = fd_url_path;
	}
	
	/**
	 * 
	 * @return fd_virutal_path
	 */
	public String getFd_virutal_path() {
		return fd_virutal_path;
	}
	
	/**
	 * 
	 * @param fd_virutal_path
	 */
	public void setFd_virutal_path(String fd_virutal_path) {
		this.fd_virutal_path = fd_virutal_path;
	}
	
	/**
	 * 
	 * @return fd_real_path
	 */
	public String getFd_real_path() {
		return fd_real_path;
	}
	
	/**
	 * 
	 * @param fd_real_path
	 */
	public void setFd_real_path(String fd_real_path) {
		this.fd_real_path = fd_real_path;
	}
	
	/**
	 * 
	 * @return fd_org_name
	 */
	public String getFd_org_name() {
		return fd_org_name;
	}
	
	/**
	 * 
	 * @param fd_org_name
	 */
	public void setFd_org_name(String fd_org_name) {
		this.fd_org_name = fd_org_name;
	}
	
	/**
	 * 
	 * @return fd_save_name
	 */
	public String getFd_save_name() {
		return fd_save_name;
	}
	
	/**
	 * 
	 * @param fd_save_name
	 */
	public void setFd_save_name(String fd_save_name) {
		this.fd_save_name = fd_save_name;
	}
	
	/**
	 * 
	 * @return fd_size
	 */
	public String getFd_size() {
		return fd_size;
	}
	
	/**
	 * 
	 * @param fd_size
	 */
	public void setFd_size(String fd_size) {
		this.fd_size = fd_size;
	}
	
	/**
	 * 
	 * @return fd_ext
	 */
	public String getFd_ext() {
		return fd_ext;
	}
	
	/**
	 * 
	 * @param fd_ext
	 */
	public void setFd_ext(String fd_ext) {
		this.fd_ext = fd_ext;
	}
	
	/**
	 * 
	 * @return fd_sort_no
	 */
	public String getFd_sort_no() {
		return fd_sort_no;
	}
	
	/**
	 * 
	 * @param fd_sort_no
	 */
	public void setFd_sort_no(String fd_sort_no) {
		this.fd_sort_no = fd_sort_no;
	}
	
	
	


}
