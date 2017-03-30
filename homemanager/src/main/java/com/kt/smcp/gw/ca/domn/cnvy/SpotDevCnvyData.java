package com.kt.smcp.gw.ca.domn.cnvy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.comm.GwCode.DevCnctType;
import com.kt.smcp.gw.ca.comm.GwCode.UseYn;
import com.kt.smcp.gw.ca.domn.CorePrcssData;

/**
 * 현장장치전달데이터 클래스
 * @since	: 2014. 11. 2.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 11. 2. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class SpotDevCnvyData extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = 3318501925906623051L;

	/** 서비스대상일련번호 */
	private Long svcTgtSeq;
	/** 현장장치일련번호 */
	private Long spotDevSeq;
	/** M2M 서비스 번호 */
	private Integer m2mSvcNo;
	/** 게이트웨이연결아이디 */
	private String gwCnctId;
	/** 현장장치아이디 */
	private String spotDevId;
	/** 상위현장장치아이디 */
	private String upSpotDevId;
	/** 모델명 */
	private String modelNm;
	/** 단위서비스코드 */
	private String unitSvcCd;
	/** 접속여부 */
	private UseYn connYn;
	/**세션아이디  */
	private String sessnId;
	/**소속서버아이디  */
	private String posSrvrId;
	/**접속서버아이디  */
	private String connSrvrId;
	/**장치연결유형코드  */
	private DevCnctType devConnTypeCd;
	/** 트랜잭션아이디(상위) */
	private String transacId;
	/** 하위트랜잭션아이디(하위) */
	private String lowTransacId;

	/** 전달행 리스트 */
	private List<CnvyRow> cnvyRows = new ArrayList<CnvyRow>();

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
		if(cnvyRows == null || cnvyRows.size() == 0)
		{
			return true;
		}
		for(CnvyRow cnvyRow : cnvyRows)
		{
			//빈객체가 아니면
			if(!cnvyRow.isEmptyData())
			{
				return false;
			}
		}
		return true;
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

	public Integer getM2mSvcNo() {
		return m2mSvcNo;
	}

	public void setM2mSvcNo(Integer m2mSvcNo) {
		this.m2mSvcNo = m2mSvcNo;
	}

	public String getSpotDevId() {
		return spotDevId;
	}

	public void setSpotDevId(String spotDevId) {
		this.spotDevId = spotDevId;
	}

	public String getGwCnctId() {
		return gwCnctId;
	}

	public void setGwCnctId(String gwCnctId) {
		this.gwCnctId = gwCnctId;
	}



	public String getModelNm() {
		return modelNm;
	}

	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}

	public String getUnitSvcCd() {
		return unitSvcCd;
	}

	public void setUnitSvcCd(String unitSvcCd) {
		this.unitSvcCd = unitSvcCd;
	}


	public DevCnctType getDevConnTypeCd() {
		return devConnTypeCd;
	}

	public void setDevConnTypeCd(DevCnctType devConnTypeCd) {
		this.devConnTypeCd = devConnTypeCd;
	}

	public List<CnvyRow> getCnvyRows() {
		return cnvyRows;
	}

	public void setCnvyRows(List<CnvyRow> cnvyRows) {
		this.cnvyRows = cnvyRows;
	}

	public String getTransacId() {
		return transacId;
	}

	public void setTransacId(String transacId) {
		this.transacId = transacId;
	}

	public String getLowTransacId() {
		return lowTransacId;
	}

	public void setLowTransacId(String lowTransacId) {
		this.lowTransacId = lowTransacId;
	}

	public String getUpSpotDevId() {
		return upSpotDevId;
	}

	public void setUpSpotDevId(String upSpotDevId) {
		this.upSpotDevId = upSpotDevId;
	}

	public UseYn getConnYn() {
		return connYn;
	}

	public void setConnYn(UseYn connYn) {
		this.connYn = connYn;
	}

	public String getSessnId() {
		return sessnId;
	}

	public void setSessnId(String sessnId) {
		this.sessnId = sessnId;
	}

	public String getPosSrvrId() {
		return posSrvrId;
	}

	public void setPosSrvrId(String posSrvrId) {
		this.posSrvrId = posSrvrId;
	}

	public String getConnSrvrId() {
		return connSrvrId;
	}

	public void setConnSrvrId(String connSrvrId) {
		this.connSrvrId = connSrvrId;
	}


}
