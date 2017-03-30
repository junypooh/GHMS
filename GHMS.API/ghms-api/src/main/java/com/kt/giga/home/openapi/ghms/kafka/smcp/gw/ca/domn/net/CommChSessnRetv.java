package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.net;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.CorePrcssData;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommChSessnRetv extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -7550203393643700655L;
	/**접속서버아이디  */
	protected String connSrvrId;
	/** 상위시스템아이디 */
	protected String upSysId;
	/** 게이트웨이연결아이디 */
	protected String gwCnctId;
	/** 서비스대상일련번호 */
	protected Long svcTgtSeq;
	/** 현장장치일련번호 */
	protected Long spotDevSeq;
	/** 현장장치아이디 */
	protected String spotDevId;
	/** 통신채널아이디 */
	protected String commChId;
	/** 수신시스템주소 */
	protected String rcvSysAdr;
	/** 세션 Key  */
	protected String sessionKey;
	/** 생성일시시작  */
	protected Date cretDtSt;
	/** 생성일시종료  */
	protected Date cretDtFns;
	/** 접속일시시작  */
	protected Date connDtSt;
	/** 접속일시종료  */
	protected Date connDtFns;
	/** 인증일시시작  */
	protected Date athnDtSt;
	/** 인증일시종료  */
	protected Date athnDtFns;
	/** 최종수신일시시작 */
	protected Date lastRcvDtSt;
	/** 최종수신일시종료 */
	protected Date lastRcvDtFns;
	/** 최종송신일시시작  */
	protected Date lastSndDtSt;
	/** 최종송신일시종료  */
	protected Date lastSndDtFns;

	/** 현재페이지  */
	protected Integer curPage;
	/** 페이지사이즈  */
	protected Integer pageSize;

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	@Override
	public boolean isEmptyData()
	{
		return false;
	}


	public CommChSessnRetv() {

	}

	public String getConnSrvrId() {
		return connSrvrId;
	}

	public void setConnSrvrId(String connSrvrId) {
		this.connSrvrId = connSrvrId;
	}

	public String getUpSysId() {
		return upSysId;
	}

	public void setUpSysId(String upSysId) {
		this.upSysId = upSysId;
	}

	public String getGwCnctId() {
		return gwCnctId;
	}

	public void setGwCnctId(String gwCnctId) {
		this.gwCnctId = gwCnctId;
	}

	public Long getSvcTgtSeq() {
		return svcTgtSeq;
	}

	public void setSvcTgtSeq(Long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}

	public Long getSpotDevSeq() {
		return spotDevSeq;
	}

	public void setSpotDevSeq(Long spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}

	public String getSpotDevId() {
		return spotDevId;
	}

	public void setSpotDevId(String spotDevId) {
		this.spotDevId = spotDevId;
	}

	public String getCommChId() {
		return commChId;
	}

	public void setCommChId(String commChId) {
		this.commChId = commChId;
	}



	public String getRcvSysAdr() {
		return rcvSysAdr;
	}

	public void setRcvSysAdr(String rcvSysAdr) {
		this.rcvSysAdr = rcvSysAdr;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public Date getCretDtSt() {
		return cretDtSt;
	}

	public void setCretDtSt(Date cretDtSt) {
		this.cretDtSt = cretDtSt;
	}

	public Date getCretDtFns() {
		return cretDtFns;
	}

	public void setCretDtFns(Date cretDtFns) {
		this.cretDtFns = cretDtFns;
	}

	public Date getConnDtSt() {
		return connDtSt;
	}

	public void setConnDtSt(Date connDtSt) {
		this.connDtSt = connDtSt;
	}

	public Date getConnDtFns() {
		return connDtFns;
	}

	public void setConnDtFns(Date connDtFns) {
		this.connDtFns = connDtFns;
	}

	public Date getAthnDtSt() {
		return athnDtSt;
	}

	public void setAthnDtSt(Date athnDtSt) {
		this.athnDtSt = athnDtSt;
	}

	public Date getAthnDtFns() {
		return athnDtFns;
	}

	public void setAthnDtFns(Date athnDtFns) {
		this.athnDtFns = athnDtFns;
	}

	public Date getLastRcvDtSt() {
		return lastRcvDtSt;
	}

	public void setLastRcvDtSt(Date lastRcvDtSt) {
		this.lastRcvDtSt = lastRcvDtSt;
	}

	public Date getLastRcvDtFns() {
		return lastRcvDtFns;
	}

	public void setLastRcvDtFns(Date lastRcvDtFns) {
		this.lastRcvDtFns = lastRcvDtFns;
	}

	public Date getLastSndDtSt() {
		return lastSndDtSt;
	}

	public void setLastSndDtSt(Date lastSndDtSt) {
		this.lastSndDtSt = lastSndDtSt;
	}

	public Date getLastSndDtFns() {
		return lastSndDtFns;
	}

	public void setLastSndDtFns(Date lastSndDtFns) {
		this.lastSndDtFns = lastSndDtFns;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}


}
