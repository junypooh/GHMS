package com.kt.giga.home.openapi.vo.spotdev;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kt.giga.home.openapi.vo.row.CmdData;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class SpotDevRetvReslt
{
	/** 트랜잭션아이디(상위) */
	private String transacId;
	/** 하위트랜잭션아이디(상위) */
	private String lowTransacId;
	/** 트랜잭션아이디(상위) 상태 */
	private String transacStatus;
	/** 요청 API서버 아이디 */
	private String reqApiSrvrId;
	/** 요청 EC서버 아이디 */
	private String reqEcSrvrId;

	private List<SpotDev> spotDevs = null;
	private List<CmdData> cmdDatas = null;

	public SpotDevRetvReslt() {
	}

	public SpotDevRetvReslt(List<SpotDev> spotDevs) {
		this.spotDevs = spotDevs;
	}

	public SpotDevRetvReslt(SpotDev spotDev) {
		this.spotDevs = new ArrayList<SpotDev>();
		this.spotDevs.add(spotDev);
	}

	public void addSpotDev(SpotDev spotDev) {
		if(spotDevs == null) {
			spotDevs = new ArrayList<SpotDev>();
		}
		spotDevs.add(spotDev);
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

	public List<SpotDev> getSpotDevs() {
		return spotDevs;
	}

    public void setSpotDevs(List<SpotDev> spotDevs) {
        this.spotDevs = spotDevs;
    }

	@JsonIgnore
	public SpotDev getFirstSpotDev() {
		if(spotDevs == null || spotDevs.isEmpty()) {
			return null;
		}

		return spotDevs.get(0);
	}

	public String getTransacStatus() {
		return transacStatus;
	}

	public void setTransacStatus(String transacStatus) {
		this.transacStatus = transacStatus;
	}

	public List<CmdData> getCmdDatas() {
		return cmdDatas;
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

}
