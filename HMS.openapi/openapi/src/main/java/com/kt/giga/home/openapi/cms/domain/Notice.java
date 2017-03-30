package com.kt.giga.home.openapi.cms.domain;


/**
 * 공지사항 클래스
 * 
 * @author 조상현
 *
 */

public class Notice {
	private String	pk_notice;				// 공지사항 pk
	private String	fd_cp_code;				// 서비스 구분 코드
	private String	fd_title;				// 제목       
	private String	fd_contents;			// 내용
	private String	fd_open_yn;				// 게시 여부 yn
	private String	fd_writer_id;			// 등록자 id
	private String	fd_writer_ip;			// 등록자 ip
	private String	fd_regdate;				// 등록일
	private String	fd_modifier_id;			// 수정자 id          
	private String	fd_modifier_ip;			// 수정자 ip          
	private String	fd_update_date;			// 수정일
	private String	fd_preorder_yn;			// 우선노출 여부YN
	
	
	/**
	 * @return 공지사항 pk
	 */
	public String getPk_notice() {
		return pk_notice;
	}
	/**
	 * @param 공지사항 pk
	 */
	public void setPk_notice(String pk_notice) {
		this.pk_notice = pk_notice;
	}
	/**
	 * @return 서비스 구분 코드
	 */
	public String getFd_cp_code() {
		return fd_cp_code;
	}
	/**
	 * @param 서비스 구분 코드
	 */
	public void setFd_cp_code(String fd_cp_code) {
		this.fd_cp_code = fd_cp_code;
	}
	/**
	 * @return 제목
	 */
	public String getFd_title() {
		return fd_title;
	}
	/**
	 * @param 제목
	 */
	public void setFd_title(String fd_title) {
		this.fd_title = fd_title;
	}
	/**
	 * @return 내용
	 */
	public String getFd_contents() {
		return fd_contents;
	}
	/**
	 * @param 내용
	 */
	public void setFd_contents(String fd_contents) {
		this.fd_contents = fd_contents;
	}
	/**
	 * @return 게시 여부 yn
	 */
	public String getFd_open_yn() {
		return fd_open_yn;
	}
	/**
	 * @param 게시 여부 yn
	 */
	public void setFd_open_yn(String fd_open_yn) {
		this.fd_open_yn = fd_open_yn;
	}
	/**
	 * @return 등록자 id
	 */
	public String getFd_writer_id() {
		return fd_writer_id;
	}
	/**
	 * @param 등록자 id
	 */
	public void setFd_writer_id(String fd_writer_id) {
		this.fd_writer_id = fd_writer_id;
	}
	/**
	 * @return 등록자 ip
	 */
	public String getFd_writer_ip() {
		return fd_writer_ip;
	}
	/**
	 * @param 등록자 ip
	 */
	public void setFd_writer_ip(String fd_writer_ip) {
		this.fd_writer_ip = fd_writer_ip;
	}
	/**
	 * @return 등록일
	 */
	public String getFd_regdate() {
		return fd_regdate;
	}
	/**
	 * @param 등록일
	 */
	public void setFd_regdate(String fd_regdate) {
		this.fd_regdate = fd_regdate;
	}
	/**
	 * @return 수정자 id 
	 */
	public String getFd_modifier_id() {
		return fd_modifier_id;
	}
	/**
	 * @param 수정자 id 
	 */
	public void setFd_modifier_id(String fd_modifier_id) {
		this.fd_modifier_id = fd_modifier_id;
	}
	/**
	 * @return 수정자 ip
	 */
	public String getFd_modifier_ip() {
		return fd_modifier_ip;
	}
	/**
	 * @param 수정자 ip
	 */
	public void setFd_modifier_ip(String fd_modifier_ip) {
		this.fd_modifier_ip = fd_modifier_ip;
	}
	/**
	 * @return 수정일
	 */
	public String getFd_update_date() {
		return fd_update_date;
	}
	/**
	 * @param 수정일
	 */
	public void setFd_update_date(String fd_update_date) {
		this.fd_update_date = fd_update_date;
	}
	/**
	 * @return 우선노출 여부YN
	 */
	public String getFd_preorder_yn() {
		return fd_preorder_yn;
	}
	/**
	 * @param 우선노출 여부YN
	 */
	public void setFd_preorder_yn(String fd_preorder_yn) {
		this.fd_preorder_yn = fd_preorder_yn;
	}
	
}
