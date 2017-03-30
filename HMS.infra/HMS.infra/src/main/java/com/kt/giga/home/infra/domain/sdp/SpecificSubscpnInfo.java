/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.sdp;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 7. 3.
 */
@XmlRootElement(name="item", namespace="http://kt.com/sdp_SpecificSubs")
public class SpecificSubscpnInfo {

	/** 계약 ID */
	private String subscpnId;
	/** 계약유형코드 */
	private String subscpnTypeCd;
	/** 계약상태코드 */
	private String subscpnStatusCd;
	/** BIT BSS 원천계약ID */
	private String bitSvcCntrNo;
	/** ICIS 원천계약ID */
	private String icisSaId;
	/** NSTEP의 CN */
	private String nstepCn;
	/** 원천결합ID */
	private String juiceNcn;
	/** NSTEP 원천계약ID */
	private String nstepOpenCn;
	/** 서비스개통일 */
	private String startDate;
	/** 서비스해지일 */
	private String endDate;
	/** 번호전송유형코드 */
	private String numberTransferTypeCd;
	/** 벤더코드 */
	private String vandorCd;
	/** 이용목적코드 */
	private String usageReasonCd;
	/** 처리유형코드 */
	private String processTypeCd;
	/** 최종수정일 */
	private String updatedDate;
	/** 요금제코드 */
	private String ppCd;
	/** 요금제명칭 */
	private String ppName;
	/** 부가상품 */
	private List<SpecificSubscpnInfoSSV> subscpnInfoSSVs;
	
	public String getSubscpnId() {
		return subscpnId;
	}
	@XmlElement(name="Subscpn_Id", namespace="http://kt.com/sdp_SpecificSubs")
	public void setSubscpnId(String subscpnId) {
		this.subscpnId = subscpnId;
	}
	public String getSubscpnTypeCd() {
		return subscpnTypeCd;
	}
	@XmlElement(name="Subscpn_Type_Cd", namespace="http://kt.com/sdp_SpecificSubs")
	public void setSubscpnTypeCd(String subscpnTypeCd) {
		this.subscpnTypeCd = subscpnTypeCd;
	}
	public String getSubscpnStatusCd() {
		return subscpnStatusCd;
	}
	@XmlElement(name="Subscpn_Status_Cd", namespace="http://kt.com/sdp_SpecificSubs")
	public void setSubscpnStatusCd(String subscpnStatusCd) {
		this.subscpnStatusCd = subscpnStatusCd;
	}
	public String getBitSvcCntrNo() {
		return bitSvcCntrNo;
	}
	@XmlElement(name="Bit_Svc_Cntr_No", namespace="http://kt.com/sdp_SpecificSubs")
	public void setBitSvcCntrNo(String bitSvcCntrNo) {
		this.bitSvcCntrNo = bitSvcCntrNo;
	}
	public String getIcisSaId() {
		return icisSaId;
	}
	@XmlElement(name="Icis_Sa_Id", namespace="http://kt.com/sdp_SpecificSubs")
	public void setIcisSaId(String icisSaId) {
		this.icisSaId = icisSaId;
	}
	public String getNstepCn() {
		return nstepCn;
	}
	@XmlElement(name="Nstep_Cn", namespace="http://kt.com/sdp_SpecificSubs")
	public void setNstepCn(String nstepCn) {
		this.nstepCn = nstepCn;
	}
	public String getJuiceNcn() {
		return juiceNcn;
	}
	@XmlElement(name="Juice_Ncn", namespace="http://kt.com/sdp_SpecificSubs")
	public void setJuiceNcn(String juiceNcn) {
		this.juiceNcn = juiceNcn;
	}
	public String getNstepOpenCn() {
		return nstepOpenCn;
	}
	@XmlElement(name="Nstep_Open_Cn", namespace="http://kt.com/sdp_SpecificSubs")
	public void setNstepOpenCn(String nstepOpenCn) {
		this.nstepOpenCn = nstepOpenCn;
	}
	public String getStartDate() {
		return startDate;
	}
	@XmlElement(name="Start_Date", namespace="http://kt.com/sdp_SpecificSubs")
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	@XmlElement(name="End_Date", namespace="http://kt.com/sdp_SpecificSubs")
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getNumberTransferTypeCd() {
		return numberTransferTypeCd;
	}
	@XmlElement(name="Number_Transfer_Type_Cd", namespace="http://kt.com/sdp_SpecificSubs")
	public void setNumberTransferTypeCd(String numberTransferTypeCd) {
		this.numberTransferTypeCd = numberTransferTypeCd;
	}
	public String getVandorCd() {
		return vandorCd;
	}
	@XmlElement(name="Vendor_Cd", namespace="http://kt.com/sdp_SpecificSubs")
	public void setVandorCd(String vandorCd) {
		this.vandorCd = vandorCd;
	}
	public String getUsageReasonCd() {
		return usageReasonCd;
	}
	@XmlElement(name="Usage_Reason_Cd", namespace="http://kt.com/sdp_SpecificSubs")
	public void setUsageReasonCd(String usageReasonCd) {
		this.usageReasonCd = usageReasonCd;
	}
	public String getProcessTypeCd() {
		return processTypeCd;
	}
	@XmlElement(name="Process_Type_Cd", namespace="http://kt.com/sdp_SpecificSubs")
	public void setProcessTypeCd(String processTypeCd) {
		this.processTypeCd = processTypeCd;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	@XmlElement(name="Updated_Date", namespace="http://kt.com/sdp_SpecificSubs")
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getPpCd() {
		return ppCd;
	}
	@XmlElement(name="PP_Cd", namespace="http://kt.com/sdp_SpecificSubs")
	public void setPpCd(String ppCd) {
		this.ppCd = ppCd;
	}
	public String getPpName() {
		return ppName;
	}
	@XmlElement(name="PP_Name", namespace="http://kt.com/sdp_SpecificSubs")
	public void setPpName(String ppName) {
		this.ppName = ppName;
	}
	public List<SpecificSubscpnInfoSSV> getSubscpnInfoSSVs() {
		return subscpnInfoSSVs;
	}
	@XmlElementWrapper(name="ListofSSV", namespace="http://kt.com/sdp_SpecificSubs")
	@XmlElementRefs({
		@XmlElementRef(name="item", namespace="http://kt.com/sdp_SpecificSubs", type=SpecificSubscpnInfoSSV.class)
	})
	public void setSubscpnInfoSSVs(List<SpecificSubscpnInfoSSV> subscpnInfoSSVs) {
		this.subscpnInfoSSVs = subscpnInfoSSVs;
	}
}
