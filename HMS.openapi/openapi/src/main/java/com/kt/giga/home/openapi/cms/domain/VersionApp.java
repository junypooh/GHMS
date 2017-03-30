package com.kt.giga.home.openapi.cms.domain;


/**
 * App버전 클래스
 * 
 * @author 조상현
 *
 */
public class VersionApp {
	private String	pk_version_app;			// App 버전관리 pk        
	private String	fd_cp_code;				// 서비스 구분코드
	private String	fd_os_code;				// OS 구분코드
	private String	fd_phone_type_code;		// 단말형태 구분코드         
	private String	fd_ver_num;				// App 버전  
	private String	fd_ver_title;			// App 버전 제목
	private String	fd_ver_memo;			// App 버전 설명
	private String	fd_mandatory_yn;		// 업데이트 필수여부YN
	private String	fd_writer_id;			// 등록자 id          
	private String	fd_writer_ip;			// 등록자 ip          
	private String	fd_regdate;				// 등록일             
	private String	fd_modifier_id;			// 수정자 id          
	private String	fd_modifier_ip;			// 수정자 ip          
	private String	fd_update_date;			// 수정일  


	/**
	 * @return App 버전관리 pk
	 */
	public String getPk_version_app() {
		return pk_version_app;
	}
	/**
	 * @param App 버전관리 pk
	 */
	public void setPk_version_app(String pk_version_app) {
		this.pk_version_app = pk_version_app;
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
	 * @return OS 구분코드
	 */
	public String getFd_os_code() {
		return fd_os_code;
	}
	/**
	 * @param OS 구분코드
	 */
	public void setFd_os_code(String fd_os_code) {
		this.fd_os_code = fd_os_code;
	}
	/**
	 * @return 단말형태 구분코드
	 */
	public String getFd_phone_type_code() {
		return fd_phone_type_code;
	}
	/**
	 * @param 단말형태 구분코드
	 */
	public void setFd_phone_type_code(String fd_phone_type_code) {
		this.fd_phone_type_code = fd_phone_type_code;
	}
	/**
	 * @return App 버전
	 */
	public String getFd_ver_num() {
		return fd_ver_num;
	}
	/**
	 * @param App 버전
	 */
	public void setFd_ver_num(String fd_ver_num) {
		this.fd_ver_num = fd_ver_num;
	}
	/**
	 * @return App 버전 제목
	 */
	public String getFd_ver_title() {
		return fd_ver_title;
	}
	/**
	 * @param App 버전 제목
	 */
	public void setFd_ver_title(String fd_ver_title) {
		this.fd_ver_title = fd_ver_title;
	}
	/**
	 * @return App 버전 설명
	 */
	public String getFd_ver_memo() {
		return fd_ver_memo;
	}
	/**
	 * @param App 버전 설명
	 */
	public void setFd_ver_memo(String fd_ver_memo) {
		this.fd_ver_memo = fd_ver_memo;
	}
	/**
	 * @return 업데이트 필수여부YN
	 */
	public String getFd_mandatory_yn() {
		return fd_mandatory_yn;
	}
	/**
	 * @param 업데이트 필수여부YN
	 */
	public void setFd_mandatory_yn(String fd_mandatory_yn) {
		this.fd_mandatory_yn = fd_mandatory_yn;
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

}
