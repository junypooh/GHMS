package com.kt.giga.home.cms.common.domain;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

/**
 * 로그인 정보
 * @author 김용성
 *
 */
public class LoginInfo {

	private String id;
	private String name;
	private String mobile;
	private String email;
	private String roleName;
	private String remoteAddress;
	private String hostName;
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
	 * 핸드폰
	 * @return 핸드폰
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * 핸드폰
	 * @param mobile 핸드폰
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
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return 역할 이름
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
	 * @return 접속 IP
	 */
	public String getRemoteAddress() {
		return remoteAddress;
	}

	/**
	 * @param remoteAddress 접속 IP
	 */
	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}	
	
	/**
	 * @return 관리 상품 리스트
	 */
	public List<Map<String, Object>> getSvcList() {
		return svcList;
	}
	
	/**
	 * 
	 * @param svcList 관리 상품 리스트
	 */
	public void setSvcList(List<Map<String, Object>> svcList) {
		this.svcList = svcList;
	}

	public LoginInfo() {
		this("", "", "", "", "", "");
	}
	
	/**
	 * 
	 * @param id 아이디
	 * @param name 이름
	 * @param mobile 핸드폰
	 * @param email 이메일
	 * @param roleName 역할 이름
	 * @param remoteAddress 접속 IP
	 */
	public LoginInfo(String id, String name, String mobile, String email, String roleName, String remoteAddress) {
		this.id 			= id;
		this.name 			= name;
		this.mobile 		= mobile;
		this.email 			= email;		
		this.roleName 		= roleName;		
		this.remoteAddress 	= remoteAddress;	

		//접속된 서버 호스트명
		try{
			this.hostName 	= InetAddress.getLocalHost().getHostName();	
		}catch(Exception e){
			this.hostName 	= "";
		}
	}

	/**
	 * @return 접속된 서버 호스트명
	 */
	public String getHostName() {
		return this.hostName;
	}

}
