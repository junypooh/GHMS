package com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.commnagnt;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CommnAgntSetupDtl  implements Serializable {

	private String commnAgntId;
	private String setupDivCd;
	private String setupCtgCd;
	private Integer intVal;
	private Double rlNumVal;
	private String binYn;
	private String strValTxn;
	private byte[] binValTxn;
	private String cretrId;
	private Date cretDt;
	private String amdrId;
	private Date amdDt;


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * @return the commnAgntId
	 */
	public String getCommnAgntId() {
		return commnAgntId;
	}
	/**
	 * @param commnAgntId the commnAgntId to set
	 */
	public void setCommnAgntId(String commnAgntId) {
		this.commnAgntId = commnAgntId;
	}
	/**
	 * @return the setupDivCd
	 */
	public String getSetupDivCd() {
		return setupDivCd;
	}
	/**
	 * @param setupDivCd the setupDivCd to set
	 */
	public void setSetupDivCd(String setupDivCd) {
		this.setupDivCd = setupDivCd;
	}
	/**
	 * @return the setupCtgCd
	 */
	public String getSetupCtgCd() {
		return setupCtgCd;
	}
	/**
	 * @param setupCtgCd the setupCtgCd to set
	 */
	public void setSetupCtgCd(String setupCtgCd) {
		this.setupCtgCd = setupCtgCd;
	}
	/**
	 * @return the intVal
	 */
	public Integer getIntVal() {
		return intVal;
	}
	/**
	 * @param intVal the intVal to set
	 */
	public void setIntVal(Integer intVal) {
		this.intVal = intVal;
	}
	/**
	 * @return the rlNumVal
	 */
	public Double getRlNumVal() {
		return rlNumVal;
	}
	/**
	 * @param rlNumVal the rlNumVal to set
	 */
	public void setRlNumVal(Double rlNumVal) {
		this.rlNumVal = rlNumVal;
	}
	/**
	 * @return the binYn
	 */
	public String getBinYn() {
		return binYn;
	}
	/**
	 * @param binYn the binYn to set
	 */
	public void setBinYn(String binYn) {
		this.binYn = binYn;
	}
	/**
	 * @return the strValTxn
	 */
	public String getStrValTxn() {
		return strValTxn;
	}
	/**
	 * @param strValTxn the strValTxn to set
	 */
	public void setStrValTxn(String strValTxn) {
		this.strValTxn = strValTxn;
	}
	/**
	 * @return the binValTxn
	 */
	public byte[] getBinValTxn() {
		return binValTxn;
	}
	/**
	 * @param binValTxn the binValTxn to set
	 */
	public void setBinValTxn(byte[] binValTxn) {
		this.binValTxn = binValTxn;
	}
	/**
	 * @return the cretrId
	 */
	public String getCretrId() {
		return cretrId;
	}
	/**
	 * @param cretrId the cretrId to set
	 */
	public void setCretrId(String cretrId) {
		this.cretrId = cretrId;
	}
	/**
	 * @return the cretDt
	 */
	public Date getCretDt() {
		return cretDt;
	}
	/**
	 * @param cretDt the cretDt to set
	 */
	public void setCretDt(Date cretDt) {
		this.cretDt = cretDt;
	}
	/**
	 * @return the amdrId
	 */
	public String getAmdrId() {
		return amdrId;
	}
	/**
	 * @param amdrId the amdrId to set
	 */
	public void setAmdrId(String amdrId) {
		this.amdrId = amdrId;
	}
	/**
	 * @return the amdDt
	 */
	public Date getAmdDt() {
		return amdDt;
	}
	/**
	 * @param amdDt the amdDt to set
	 */
	public void setAmdDt(Date amdDt) {
		this.amdDt = amdDt;
	}

}
