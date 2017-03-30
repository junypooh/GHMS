package com.kt.giga.home.openapi.hcam.user.domain;

public class AppUser {
	private int svcTgtSeq;
	private int mbrsSeq;
	private String strMbrsSeq;
	private String deviceId;
	private String userId;
	private String pnsRegId;
	private String appVer;
	private String telNo;
	private String custId;
	private String credentialId;
	private String dstrCd;
	private String svcThemeCd;
	private String unitSvcCd;
	private String connTermlId;
	private String mbrsId;
	private String age;
	private String subsYn;
	private String svcCode;
	private int expireTimeMinute;
	private String athnToknVal;
	private String termsYn;
	private String kidsCheckOption;
	private String loginResult;
	private String svcSttusCd;
	private String oprtSttusCd;
	private int cnt;
	
	
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getOprtSttusCd() {
		return oprtSttusCd;
	}
	public void setOprtSttusCd(String oprtSttusCd) {
		this.oprtSttusCd = oprtSttusCd;
	}
	public String getSvcSttusCd() {
		return svcSttusCd;
	}
	public void setSvcSttusCd(String svcSttusCd) {
		this.svcSttusCd = svcSttusCd;
	}
	public String getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}
	public String getStrMbrsSeq() {
		return strMbrsSeq;
	}
	public void setStrMbrsSeq(String strMbrsSeq) {
		this.strMbrsSeq = strMbrsSeq;
	}
	public String getTermsYn() {
		return termsYn;
	}
	public void setTermsYn(String termsYn) {
		this.termsYn = termsYn;
	}
	public String getKidsCheckOption() {
		return kidsCheckOption;
	}
	public void setKidsCheckOption(String kidsCheckOption) {
		this.kidsCheckOption = kidsCheckOption;
	}
	public String getAthnToknVal() {
		return athnToknVal;
	}
	public void setAthnToknVal(String athnToknVal) {
		this.athnToknVal = athnToknVal;
	}
	public int getExpireTimeMinute() {
		return expireTimeMinute;
	}
	public void setExpireTimeMinute(int expireTimeMinute) {
		this.expireTimeMinute = expireTimeMinute;
	}
	public String getCredentialId() {
		return credentialId;
	}
	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}
	public String getSvcCode() {
		return svcCode;
	}
	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}
	public String getSubsYn() {
		return subsYn;
	}
	public void setSubsYn(String subsYn) {
		this.subsYn = subsYn;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getMbrsId() {
		return mbrsId;
	}
	public void setMbrsId(String mbrsId) {
		this.mbrsId = mbrsId;
	}
	public String getConnTermlId() {
		return connTermlId;
	}
	public void setConnTermlId(String connTermlId) {
		this.connTermlId = connTermlId;
	}
	public int getMbrsSeq() {
		return mbrsSeq;
	}
	public void setMbrsSeq(int mbrsSeq) {
		this.mbrsSeq = mbrsSeq;
	}
	public String getDstrCd() {
		return dstrCd;
	}
	public void setDstrCd(String dstrCd) {
		this.dstrCd = dstrCd;
	}
	public String getSvcThemeCd() {
		return svcThemeCd;
	}
	public void setSvcThemeCd(String svcThemeCd) {
		this.svcThemeCd = svcThemeCd;
	}
	public String getUnitSvcCd() {
		return unitSvcCd;
	}
	public void setUnitSvcCd(String unitSvcCd) {
		this.unitSvcCd = unitSvcCd;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	private String authToken;

	public int getSvcTgtSeq() {
		return svcTgtSeq;
	}
	public void setSvcTgtSeq(int svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPnsRegId() {
		return pnsRegId;
	}
	public void setPnsRegId(String pnsRegId) {
		this.pnsRegId = pnsRegId;
	}
	public String getAppVer() {
		return appVer;
	}
	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}
