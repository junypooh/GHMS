package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.net;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.CorePrcssData;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommChSessn extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 297404626779364054L;
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
	/** 생성일시  */
	protected Date cretDt = new Date();
	/** 접속일시  */
	protected Date connDt;
	/** 인증일시  */
	protected Date athnDt;
	/** 최종수신일시 */
	protected Date lastRcvDt;
	/** 최종송신일시  */
	protected Date lastSndDt;
	/** 세션값  */
	protected HashMap<String, Object> hmSessionValue =  new HashMap<String, Object>();

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
		if(sessionKey == null)
		{
			return true;
		}

		if(hmSessionValue.size() == 0)
		{
			return true;
		}
		return false;
	}


	public CommChSessn() {

	}

	public CommChSessn(CommChSessn commChSessn) {
		this.connSrvrId = commChSessn.connSrvrId;
		this.upSysId = commChSessn.upSysId;
		this.gwCnctId = commChSessn.gwCnctId;
		this.svcTgtSeq = commChSessn.svcTgtSeq;
		this.spotDevSeq = commChSessn.spotDevSeq;
		this.spotDevId = commChSessn.spotDevId;
		this.commChId = commChSessn.commChId;
		this.rcvSysAdr = commChSessn.rcvSysAdr;
		this.sessionKey = commChSessn.sessionKey;
		this.cretDt = commChSessn.cretDt;
		this.connDt = commChSessn.connDt;
		this.athnDt = commChSessn.athnDt;
		this.lastRcvDt = commChSessn.lastRcvDt;
		this.lastSndDt = commChSessn.lastSndDt;
		this.hmSessionValue.putAll(commChSessn.getHmSessionValue());
	}

	/**
	 * <PRE>
	 *  MethodName putValue
	 * </PRE>
	 * @brief: 세션값 입력
	 * @author CBJ
	 * @date 2014. 10. 22. 오전 8:24:26
	 * @param key: Key
	 * @param value: 값
	 * @return Key에 값이 존재할 경우 이전 값
	 */
	public Object putValue(String key, Object value)
	{
		return hmSessionValue.put(key, value);
	}

	/**
	 * <PRE>
	 *  MethodName removeValue
	 * </PRE>
	 * @brief: 세션값 삭제
	 * @author CBJ
	 * @date 2014. 10. 22. 오전 8:24:49
	 * @param key: Key
	 * @return Key에 값이 존재할 경우 삭제 값
	 */
	public Object removeValue(String key)
	{
		return hmSessionValue.remove(key);
	}

	/**
	 * <PRE>
	 *  MethodName getValue
	 * </PRE>
	 * @brief: 세션값 검색
	 * @author CBJ
	 * @date 2014. 10. 22. 오전 8:25:51
	 * @param key: Key
	 * @return Key에 값이 존재할 경우 검색 값
	 */
	public Object getValue(String key)
	{
		return hmSessionValue.get(key);
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

	public Date getCretDt() {
		return cretDt;
	}

	public Date getConnDt() {
		return connDt;
	}

	public void setConnDt(Date connDt) {
		this.connDt = connDt;
	}

	public Date getAthnDt() {
		return athnDt;
	}

	public void setAthnDt(Date athnDt) {
		this.athnDt = athnDt;
	}

	public void setCretDt(Date cretDt) {
		this.cretDt = cretDt;
	}

	public Date getLastRcvDt() {
		return lastRcvDt;
	}

	public void setLastRcvDt(Date lastRcvDt) {
		this.lastRcvDt = lastRcvDt;
	}

	public Date getLastSndDt() {
		return lastSndDt;
	}

	public void setLastSndDt(Date lastSndDt) {
		this.lastSndDt = lastSndDt;
	}

	public HashMap<String, Object> getHmSessionValue() {
		return hmSessionValue;
	}

	public void setHmSessionValue(HashMap<String, Object> hmSessionValue) {
		this.hmSessionValue = hmSessionValue;
	}


}
