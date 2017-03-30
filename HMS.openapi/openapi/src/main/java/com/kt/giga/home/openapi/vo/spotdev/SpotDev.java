package com.kt.giga.home.openapi.vo.spotdev;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kt.giga.home.openapi.vo.row.GenlSetupData;
import com.kt.giga.home.openapi.vo.row.SclgSetupData;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotDev extends SpotDevBas
{
	/** 외부시스템(앱)과 연동에 사용하는 장치 유니크 아이디 */
	private String devUUID;
	/** 외부시스템(앱)과 연동에 사용하는 장치 제어에 대한 트랜잭션 아이디*/
	private String contlOccId;
	/** 외부시스템(앱)과 연동에 사용하는 장치 제어에 대한 트랜잭션 상태*/
	private String contlTrtSttusCd;

	private String oprtSttusCd;

	private String devSttusCd;

	private String svcContId;

    /**
     * 테스트용 단말 여부
     */
    @JsonIgnore
    private boolean beta;

	/** 멤버 시퀀스 */
	@JsonIgnore
	private Long mbrSeq;

	/** 멤버 아이디 */
	@JsonIgnore
	private String mbrId;

	/** Credential ID */
	@JsonIgnore
	private String credentialId;

	/** 현장장치상세 */
	private List<SpotDevDtl> spotDevDtls = new ArrayList<SpotDevDtl>();
	/** 일반설정데이터(80) */
	private List<GenlSetupData> genlSetupDatas = new ArrayList<GenlSetupData>();
	/** 스케줄설정데이터(81) */
	private List<SclgSetupData> sclgSetupDatas = new ArrayList<SclgSetupData>();

	private HashMap<String, String> spotDevDtlMap = null;


	public SpotDev() {

	}

	public SpotDev(Long svcTgtSeq, Long spotDevSeq) {
		super(svcTgtSeq, spotDevSeq);
	}

	public SpotDev(Long svcTgtSeq, Long spotDevSeq, String spotDevId) {
		super(svcTgtSeq, spotDevSeq, spotDevId);
	}

	public List<SpotDevDtl> getSpotDevDtls() {
		return spotDevDtls;
	}

	public void setSpotDevDtls(List<SpotDevDtl> spotDevDtls) {
		this.spotDevDtls = spotDevDtls;
	}

	public void addSpotDevDtl(String atribNm, String atribVal) {
		if(spotDevDtls == null) {
			spotDevDtls = new ArrayList<SpotDevDtl>();
		}

		spotDevDtls.add(new SpotDevDtl(atribNm, atribVal));
	}

	public HashMap<String, String> getSpotDevDtlMap() {
		if(spotDevDtlMap == null) {
			spotDevDtlMap = new HashMap<String, String>();

			for(SpotDevDtl spotDevDtl : spotDevDtls) {

				if(spotDevDtl == null) {
					continue;
				}

				String name = spotDevDtl.getAtribNm();
				String value = spotDevDtl.getAtribVal();

				if(StringUtils.isBlank(name)) {
					continue;
				}

				spotDevDtlMap.put(name, value);
			}
		}

		return spotDevDtlMap;
	}

	public String getSpotDevDtlVal(String atribNm) {
		return getSpotDevDtlMap().get(atribNm);
	}

	public List<GenlSetupData> getGenlSetupDatas() {
		return genlSetupDatas;
	}

	public void setGenlSetupDatas(List<GenlSetupData> genlSetupDatas) {
		this.genlSetupDatas = genlSetupDatas;
	}

	public void addGenlSetupData(GenlSetupData genlSetupData) {

		if(genlSetupDatas == null) {
			genlSetupDatas = new ArrayList<GenlSetupData>();
		}

		genlSetupDatas.add(genlSetupData);
	}

	public List<SclgSetupData> getSclgSetupDatas() {
		return sclgSetupDatas;
	}

	public void setSclgSetupDatas(List<SclgSetupData> sclgSetupDatas) {
		this.sclgSetupDatas = sclgSetupDatas;
	}

	public String getDevUUID() {
		return devUUID;
	}

	public void setDevUUID(String devUUID) {
		this.devUUID = devUUID;
	}

	public String getContlOccId() {
		return contlOccId;
	}

	public void setContlOccId(String contlOccId) {
		this.contlOccId = contlOccId;
	}

	public String getContlTrtSttusCd() {
		return contlTrtSttusCd;
	}

	public void setContlTrtSttusCd(String contlTrtSttusCd) {
		this.contlTrtSttusCd = contlTrtSttusCd;
	}

	public String getOprtSttusCd() {
		return oprtSttusCd;
	}

	public void setOprtSttusCd(String oprtSttusCd) {
		this.oprtSttusCd = oprtSttusCd;
	}

	public String getDevSttusCd() {
		return devSttusCd;
	}

	public void setDevSttusCd(String devSttusCd) {
		this.devSttusCd = devSttusCd;
	}

	public Long getMbrSeq() {
		return mbrSeq;
	}

	public void setMbrSeq(Long mbrSeq) {
		this.mbrSeq = mbrSeq;
	}

	public String getMbrId() {
		return mbrId;
	}

	public void setMbrId(String mbrId) {
		this.mbrId = mbrId;
	}

	public String getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	public String getSvcContId() {
		return svcContId;
	}

	public void setSvcContId(String svcContId) {
		this.svcContId = svcContId;
    }

    public boolean isBeta() {
        return beta;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
