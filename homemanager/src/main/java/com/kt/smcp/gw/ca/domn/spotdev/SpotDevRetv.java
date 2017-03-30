package com.kt.smcp.gw.ca.domn.spotdev;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kt.smcp.gw.ca.comm.GwCode.UseYn;
import com.kt.smcp.gw.ca.domn.CorePrcssData;
import com.kt.smcp.gw.ca.domn.cnvy.ItgCnvyData;
import com.kt.smcp.gw.ca.domn.row.CmdData;

public class SpotDevRetv extends CorePrcssData implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -7134024988141842107L;

	/** 게이트웨이연결아이디 */
	private String gwCnctId;
	/**요청API서버아이디  */
	private String reqApiSrvrId;
	/**요청EC서버아이디  */
	private String reqEcSrvrId;
	/** 트랜잭션아이디(상위) */
	private String transacId;
	/** 하위트랜잭션아이디(하위) */
	private String lowTransacId;
	/** 단위서비스코드 */
	private String unitSvcCd;
	/** 포함현장장치 */
	private List<SpotDevKey> inclSpotDevKeys = new ArrayList<SpotDevKey>();
	/** 배타현장장치 */
	private List<SpotDevKey> excluSpotDevKeys = new ArrayList<SpotDevKey>();
	/** 명령어데이터리스트(31) */
	private List<CmdData> cmdDatas = new ArrayList<CmdData>();
	/** 모델명 */
	private String modelNm;
	/** 사용여부 */
	private UseYn useYn;
	/** 생성일시시작 */
	private Date cretDtSt;
	/** 생성일시종료 */
	private Date cretDtFns;
	/** 수정일시시작 */
	private Date amdDtSt;
	/** 수정일시종료 */
	private Date amdDtFns;

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

	public void setSpotDevRetv(SpotDevRetv spotDevRetv)
	{
		/** 게이트웨이연결아이디 */
		this.gwCnctId = spotDevRetv.getGwCnctId();
		/**요청API서버아이디  */
		this.reqApiSrvrId = spotDevRetv.getReqApiSrvrId();
		/**요청EC서버아이디  */
		this.reqEcSrvrId = spotDevRetv.getReqEcSrvrId();
		/** 트랜잭션아이디(상위) */
		this.transacId = spotDevRetv.getTransacId();
		/** 하위트랜잭션아이디(하위) */
		this.lowTransacId = spotDevRetv.getLowTransacId();
		/** 단위서비스코드 */
		this.unitSvcCd = spotDevRetv.getUnitSvcCd();
		/** 명령어데이터리스트(31) */
		if(spotDevRetv.getCmdDatas() != null)
		{
			cmdDatas.addAll(spotDevRetv.getCmdDatas());
		}
		/** 모델명 */
		this.modelNm = spotDevRetv.getModelNm();
		/** 사용여부 */
		this.useYn = spotDevRetv.getUseYn();
		/** 생성일시시작 */
		this.cretDtSt = spotDevRetv.getCretDtSt();
		/** 생성일시종료 */
		this.cretDtFns = spotDevRetv.getCretDtFns();
		/** 수정일시시작 */
		this.amdDtSt = spotDevRetv.getAmdDtSt();
		/** 수정일시종료 */
		this.amdDtFns = spotDevRetv.getAmdDtFns();
	}

	@Override
	public boolean isEmptyData()
	{
		return false;
	}

	public String getGwCnctId() {
		return gwCnctId;
	}

	public void setGwCnctId(String gwCnctId) {
		this.gwCnctId = gwCnctId;
	}

	public String getReqApiSrvrId() {
		return reqApiSrvrId;
	}

	public void setReqApiSrvrId(String reqApiSrvrId) {
		this.reqApiSrvrId = reqApiSrvrId;
	}

	public String getReqEcSrvrId() {
		return reqEcSrvrId;
	}

	public void setReqEcSrvrId(String reqEcSrvrId) {
		this.reqEcSrvrId = reqEcSrvrId;
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

	public String getUnitSvcCd() {
		return unitSvcCd;
	}

	public void setUnitSvcCd(String unitSvcCd) {
		this.unitSvcCd = unitSvcCd;
	}

	public List<SpotDevKey> getInclSpotDevKeys() {
		return inclSpotDevKeys;
	}

	public void setInclSpotDevKeys(List<SpotDevKey> inclSpotDevKeys) {
		this.inclSpotDevKeys = inclSpotDevKeys;
	}

	public List<SpotDevKey> getExcluSpotDevKeys() {
		return excluSpotDevKeys;
	}

	public void setExcluSpotDevKeys(List<SpotDevKey> excluSpotDevKeys) {
		this.excluSpotDevKeys = excluSpotDevKeys;
	}

	public List<CmdData> getCmdDatas() {
		return cmdDatas;
	}

	public void setCmdDatas(List<CmdData> cmdDatas) {
		this.cmdDatas = cmdDatas;
	}

	public String getModelNm() {
		return modelNm;
	}

	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}

	public UseYn getUseYn() {
		return useYn;
	}

	public void setUseYn(UseYn useYn) {
		this.useYn = useYn;
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

	public Date getAmdDtSt() {
		return amdDtSt;
	}

	public void setAmdDtSt(Date amdDtSt) {
		this.amdDtSt = amdDtSt;
	}

	public Date getAmdDtFns() {
		return amdDtFns;
	}

	public void setAmdDtFns(Date amdDtFns) {
		this.amdDtFns = amdDtFns;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
