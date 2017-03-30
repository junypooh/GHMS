package com.kt.giga.home.openapi.vo.spotdev;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.kt.giga.home.openapi.vo.GwCode.UseYn;
import com.kt.giga.home.openapi.vo.row.CmdData;

public class SpotDevRetv
{
	/** 트랜잭션아이디(상위) */
	private String transacId;
	/** 하위트랜잭션아이디(상위) */
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
	/** 요청 API서버 아이디 */
	private String reqApiSrvrId;
	/** 요청 EC서버 아이디 */
	private String reqEcSrvrId;


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
	
	public SpotDevKey getFirstInclSpotDevKey() {
		
		return CollectionUtils.isEmpty(inclSpotDevKeys) ? null : inclSpotDevKeys.get(0);
	}

	public void setInclSpotDevKeys(List<SpotDevKey> inclSpotDevKeys) {
		this.inclSpotDevKeys = inclSpotDevKeys;
	}
	
	public void addInclSpotDevKey(SpotDevKey spotDevKey) {
		if(inclSpotDevKeys == null) {
			inclSpotDevKeys = new ArrayList<SpotDevKey>();
		}
		
		inclSpotDevKeys.add(spotDevKey);
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
	
	public CmdData getFirstCmdData() {
		
		return CollectionUtils.isEmpty(cmdDatas) ? null : cmdDatas.get(0);
	}
	
	public String getFirstCmdDataSnsnTagCd() {
		
		CmdData cmdData = getFirstCmdData();
		return cmdData == null ? null : cmdData.getSnsnTagCd();
	}

	public void setCmdDatas(List<CmdData> cmdDatas) {
		this.cmdDatas = cmdDatas;
	}
	
	public void addCmdData(CmdData cmdData) {

		if(cmdDatas == null) {
			cmdDatas = new ArrayList<CmdData>();
		}
		
		cmdDatas.add(cmdData);
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

}
