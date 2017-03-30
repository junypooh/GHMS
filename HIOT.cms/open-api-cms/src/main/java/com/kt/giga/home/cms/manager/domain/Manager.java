package com.kt.giga.home.cms.manager.domain;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.kt.giga.home.cms.manager.service.*;

/**
 * 관리자
 * @author 김용성
 *
 */
public class Manager {
	
	@Autowired
	private ManagerSvcService managerSvcService;

	private String id;
	private String pw;
	private String name;
	private String company;
	private String team;
	private String statusCd;
	private String phone;
	private String mobile;
	private String email;
	private String memo;
	private Date regDate;
	private String loginIp;
	private Date loginDate;
	private Date changePwDate;
	private String pwTmp;	
	private Date pwTmpDate;	
	private int role;	
	private String roleName;	
	private List<Map<String, Object>> svcList;
	
	/**
	 * 아이디
	 * @return 아이디
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 아이디
	 * @param id 아이디
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 패스워드
	 * @return 패스워드
	 */
	public String getPw() {
		return pw;
	}
	
	/**
	 * 패스워드
	 * @param pw 패스워드
	 */
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	/**
	 * 이름
	 * @return 이름
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 이름
	 * @param name 이름
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 회사명
	 * @return 회사명
	 */
	public String getCompany() {
		return company;
	}
	
	/**
	 *회사명
	 * @param company 회사명
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	
	/**
	 * 부서명
	 * @return 부서명
	 */
	public String getTeam() {
		return team;
	}
	
	/**
	 * 부서명
	 * @param team 부서명
	 */
	public void setTeam(String team) {
		this.team = team;
	}
	
	/**
	 * 계정상태 코드
	 * @return 계정상태 코드
	 */
	public String getStatusCd() {
		return statusCd;
	}
	
	/**
	 * 계정상태 코드
	 * @param statusCd 계정상태 코드
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}
	
	/**
	 * 유선전화 번호
	 * @return 유선전화 번호
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * 유선전화 번호
	 * @param phone 유선전화 번호
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * 휴대전화 번호
	 * @return 휴대전화 번호
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * 휴대전화 번호
	 * @param mobile 휴대전화 번호
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * 이메일
	 * @return 이메일
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * 이메일
	 * @param email 이메일
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 메모
	 * @return 메모
	 */
	public String getMemo() {
		return memo;
	}
	
	/**
	 * 메모
	 * @param memo 메모
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	/**
	 * 등록일
	 * @return 등록일
	 */
	public Date getRegDate() {
		return regDate;
	}
	
	/**
	 * 등록일
	 * @param regDate 등록일
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	/**
	 * 최종 로그인 ip
	 * @return 최종 로그인 ip
	 */
	public String getLoginIp() {
		return loginIp;
	}
	
	/**
	 * 최종 로그인 ip
	 * @param loginIp 최종 로그인 ip
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	/**
	 * 최종 로그인 일시
	 * @return 최종 로그인 일시
	 */
	public Date getLoginDate() {
		return loginDate;
	}
	
	/**
	 * 최종 로그인 일시
	 * @param loginDate 최종 로그인 일시
	 */
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	/**
	 * 최종 pw 변경일시
	 * @return 최종 pw 변경일시
	 */
	public Date getChangePwDate() {
		return changePwDate;
	}
	
	/**
	 * 최종 pw 변경일시
	 * @param changePwDate 최종 pw 변경일시
	 */
	public void setChangePwDate(Date changePwDate) {
		this.changePwDate = changePwDate;
	}
	
	/**
	 * 임시 인증번호
	 * @return 임시 인증번호
	 */
	public String getPwTmp() {
		return pwTmp;
	}
	
	/**
	 * 임시 인증번호
	 * @param pwTmp 임시 인증번호
	 */
	public void setPwTmp(String pwTmp) {
		this.pwTmp = pwTmp;
	}

	/**
	 * @return 임시 인증번호 발급일시
	 */
	public Date getPwTmpDate() {
		return pwTmpDate;
	}

	/**
	 * @param pwTmpDate 임시 인증번호 발급일시
	 */
	public void setPwTmpDate(Date pwTmpDate) {
		this.pwTmpDate = pwTmpDate;
	}

	/**
	 * @return the 역할 이름
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName 역할 이름
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return 역할
	 */
	public int getRole() {
		return role;
	}

	/**
	 * @param role 역할
	 */
	public void setRole(int role) {
		this.role = role;
	}
	
	/**
	 * @return 관리 상품 리스트
	 */
	public List<Map<String, Object>> getSvcList() {
		return svcList;
	}	
	
	/**
	 * @param svcList 관리 상품 리스트
	 */
	public void setSvcList(List<Map<String, Object>> svcList) {
		this.svcList = svcList;
	}
}
