package com.kt.giga.home.openapi.cms.domain;


/**
 * 웰컴가이드 클래스
 * 
 * @author 조상현
 *
 */
public class GuideWelcome {
	private String	pk_guide_welcome;		// 웰컴가이드 pk        
	private String	fd_cp_code;				// 서비스 구분코드
	private String	fd_ver_num;				// 버전정보
	private String	fd_open_yn;				// 게시여부 YN
	private String	fd_writer_id;			// 등록자 id          
	private String	fd_writer_ip;			// 등록자 ip          
	private String	fd_regdate;				// 등록일             
	private String	fd_modifier_id;			// 수정자 id          
	private String	fd_modifier_ip;			// 수정자 ip          
	private String	fd_update_date;			// 수정일  
	
	private String	fd_file_path_1;			// 이미지 경로 1 
	private String	fd_file_name_1;			// 이미지 이름 1 

	private String	fd_file_path_2;			// 이미지 경로 2  
	private String	fd_file_name_2;			// 이미지 이름 2  
	
	private String	fd_file_path_3;			// 이미지 경로 3  
	private String	fd_file_name_3;			// 이미지 이름 3  

	
	/**
	 * @return 웰컴가이드 pk
	 */
	public String getPk_guide_welcome() {
		return pk_guide_welcome;
	}
	/**
	 * @param 웰컴가이드 pk
	 */
	public void setPk_guide_welcome(String pk_guide_welcome) {
		this.pk_guide_welcome = pk_guide_welcome;
	}
	/**
	 * @return 서비스 구분코드
	 */
	public String getFd_cp_code() {
		return fd_cp_code;
	}
	/**
	 * @param 서비스 구분코드
	 */
	public void setFd_cp_code(String fd_cp_code) {
		this.fd_cp_code = fd_cp_code;
	}
	/**
	 * @return 버전정보
	 */
	public String getFd_ver_num() {
		return fd_ver_num;
	}
	/**
	 * @param 버전정보
	 */
	public void setFd_ver_num(String fd_ver_num) {
		this.fd_ver_num = fd_ver_num;
	}
	/**
	 * @return 게시여부 YN
	 */
	public String getFd_open_yn() {
		return fd_open_yn;
	}
	/**
	 * @param 게시여부 YN
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
	 * @return 이미지 경로 1
	 */
	public String getFd_file_path_1() {
		return fd_file_path_1;
	}
	/**
	 * @param 이미지 경로 1
	 */
	public void setFd_file_path_1(String fd_file_path_1) {
		this.fd_file_path_1 = fd_file_path_1;
	}
	/**
	 * @return 이미지 이름 1
	 */
	public String getFd_file_name_1() {
		return fd_file_name_1;
	}
	/**
	 * @param 이미지 이름 1
	 */
	public void setFd_file_name_1(String fd_file_name_1) {
		this.fd_file_name_1 = fd_file_name_1;
	}
	/**
	 * @return 이미지 경로 2
	 */
	public String getFd_file_path_2() {
		return fd_file_path_2;
	}
	/**
	 * @param 이미지 경로 2
	 */
	public void setFd_file_path_2(String fd_file_path_2) {
		this.fd_file_path_2 = fd_file_path_2;
	}
	/**
	 * @return 이미지 이름 2
	 */
	public String getFd_file_name_2() {
		return fd_file_name_2;
	}
	/**
	 * @param 이미지 이름 2
	 */
	public void setFd_file_name_2(String fd_file_name_2) {
		this.fd_file_name_2 = fd_file_name_2;
	}
	/**
	 * @return 이미지 경로 3
	 */
	public String getFd_file_path_3() {
		return fd_file_path_3;
	}
	/**
	 * @param 이미지 경로 3
	 */
	public void setFd_file_path_3(String fd_file_path_3) {
		this.fd_file_path_3 = fd_file_path_3;
	}
	/**
	 * @return 이미지 이름 3
	 */
	public String getFd_file_name_3() {
		return fd_file_name_3;
	}
	/**
	 * @param 이미지 이름 3
	 */
	public void setFd_file_name_3(String fd_file_name_3) {
		this.fd_file_name_3 = fd_file_name_3;
	}
	
}
