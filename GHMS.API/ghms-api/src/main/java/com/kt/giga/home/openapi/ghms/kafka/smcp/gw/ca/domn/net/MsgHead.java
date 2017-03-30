package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.net;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.CmpreType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.EcodType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.EncdngType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.HdrType;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.comm.GwCode.MsgExchPtrn;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.CorePrcssData;

/**
 * 메세지헤더
 * @since	: 2014. 11. 26.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 26. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class MsgHead extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 103185912184282799L;

	/** 프로토콜 버전 */
	private String protVer;
	/** 헤더 타입 */
	private HdrType hdrType;
	/** 헤더 길이 */
	private Short hdrLen;
	/** 메시지 유형 */
	private String msgType;
	/** 메시지 교환 패턴 */
	private MsgExchPtrn msgExchPtrn;
	/** 기능 유형 */
	private String methodType;
	/** 전송트랜잭션아이디 */
	private String trmTransacId;
	/** 통신채널인증토큰 */
	private String commChAthnNo;
	/** 암호화 유형 */
	private EcodType ecodType;
	/** 압축 유형 */
	private CmpreType cmpreType;
	/** 인코딩 타입 */
	private EncdngType encdngType;

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

	public String getProtVer() {
		return protVer;
	}

	public void setProtVer(String protVer) {
		this.protVer = protVer;
	}

	public HdrType getHdrType() {
		return hdrType;
	}

	public void setHdrType(HdrType hdrType) {
		this.hdrType = hdrType;
	}

	public Short getHdrLen() {
		return hdrLen;
	}

	public void setHdrLen(Short hdrLen) {
		this.hdrLen = hdrLen;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public MsgExchPtrn getMsgExchPtrn() {
		return msgExchPtrn;
	}

	public void setMsgExchPtrn(MsgExchPtrn msgExchPtrn) {
		this.msgExchPtrn = msgExchPtrn;
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getTrmTransacId() {
		return trmTransacId;
	}

	public void setTrmTransacId(String trmTransacId) {
		this.trmTransacId = trmTransacId;
	}

	public String getCommChAthnNo() {
		return commChAthnNo;
	}

	public void setCommChAthnNo(String commChAthnNo) {
		this.commChAthnNo = commChAthnNo;
	}

	public EcodType getEcodType() {
		return ecodType;
	}

	public void setEcodType(EcodType ecodType) {
		this.ecodType = ecodType;
	}

	public CmpreType getCmpreType() {
		return cmpreType;
	}

	public void setCmpreType(CmpreType cmpreType) {
		this.cmpreType = cmpreType;
	}

	public EncdngType getEncdngType() {
		return encdngType;
	}

	public void setEncdngType(EncdngType encdngType) {
		this.encdngType = encdngType;
	}



}
