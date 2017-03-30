package com.kt.giga.home.openapi.cms.domain;


/**
 * 약관 클래스
 * 
 * @author 조상현
 *
 */
public class Terms {
	private String	pk_terms;				// 약관목록 pk        
	private String	fk_cp_code;				// 약관구분 코드 fk   
	private String	fk_terms_code;			// cp 코드 fk         
	private String	fd_terms_ver;			// 약관 버전          
	private String	fd_terms_title;			// 약관 제목          
	private String	fd_terms_contents;		// 약관 내용          
	private String	fd_terms_start;			// 약관 시행일        
	private String	fd_terms_mandatory_yn;	// 약관동의 필수여부YN
	private String	fd_writer_id;			// 등록자 id          
	private String	fd_writer_ip;			// 등록자 ip          
	private String	fd_regdate;				// 등록일             
	private String	fd_modifier_id;			// 수정자 id          
	private String	fd_modifier_ip;			// 수정자 ip          
	private String	fd_update_date;			// 수정일             

	private String	fd_agree_yn;			// 약관 동의 여부 yn


	/**
	 * @return 약관목록 pk
	 */
	public String getPk_terms() {
		return pk_terms;
	}
	/**
	 * @param 약관목록 pk
	 */
	public void setPk_terms(String pk_terms) {
		this.pk_terms = pk_terms;
	}
	/**
	 * @return 약관구분 코드 fk 
	 */
	public String getFk_cp_code() {
		return fk_cp_code;
	}
	/**
	 * @param 약관구분 코드 fk 
	 */
	public void setFk_cp_code(String fk_cp_code) {
		this.fk_cp_code = fk_cp_code;
	}
	/**
	 * @return cp 코드 fk
	 */
	public String getFk_terms_code() {
		return fk_terms_code;
	}
	/**
	 * @param cp 코드 fk
	 */
	public void setFk_terms_code(String fk_terms_code) {
		this.fk_terms_code = fk_terms_code;
	}
	/**
	 * @return 약관 버전
	 */
	public String getFd_terms_ver() {
		return fd_terms_ver;
	}
	/**
	 * @param 약관 버전
	 */
	public void setFd_terms_ver(String fd_terms_ver) {
		this.fd_terms_ver = fd_terms_ver;
	}
	/**
	 * @return 약관 제목
	 */
	public String getFd_terms_title() {
		return fd_terms_title;
	}
	/**
	 * @param 약관 제목
	 */
	public void setFd_terms_title(String fd_terms_title) {
		this.fd_terms_title = fd_terms_title;
	}
	/**
	 * @return 약관 내용 
	 */
	public String getFd_terms_contents() {
		return fd_terms_contents;
	}
	/**
	 * @param 약관 내용 
	 */
	public void setFd_terms_contents(String fd_terms_contents) {
		this.fd_terms_contents = fd_terms_contents;
	}
	/**
	 * @return 약관 시행일
	 */
	public String getFd_terms_start() {
		return fd_terms_start;
	}
	/**
	 * @param 약관 시행일
	 */
	public void setFd_terms_start(String fd_terms_start) {
		this.fd_terms_start = fd_terms_start;
	}
	/**
	 * @return 약관동의 필수여부YN
	 */
	public String getFd_terms_mandatory_yn() {
		return fd_terms_mandatory_yn;
	}
	/**
	 * @param 약관동의 필수여부YN
	 */
	public void setFd_terms_mandatory_yn(String fd_terms_mandatory_yn) {
		this.fd_terms_mandatory_yn = fd_terms_mandatory_yn;
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
	 * @return 약관 동의 여부 yn
	 */
	public String getFd_agree_yn() {
		return fd_agree_yn;
	}
	/**
	 * @param 약관 동의 여부 yn
	 */
	public void setFd_agree_yn(String fd_agree_yn) {
		this.fd_agree_yn = fd_agree_yn;
	}


}
