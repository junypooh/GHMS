/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.infra.domain.checker;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 6. 29.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IoTGWInfo {

	@JsonIgnore
	private Long svcTgtSeq;

	@JsonIgnore
	private Long spotDevSeq;

	@JsonProperty("MacAddress")
	private String macAddress;

	@JsonProperty("Manufacturer")
	private String manufacturer;

	@JsonProperty("Remark")
	private String remark;

	@JsonProperty("IoTDeviceInfo")
	private List<IoTDeviceInfo> ioTDeviceInfos;

	/**
	 * @return TODO
	 */
	public Long getSvcTgtSeq() {
		return svcTgtSeq;
	}

	/**
	 * @param svcTgtSeq TODO
	 */
	public void setSvcTgtSeq(Long svcTgtSeq) {
		this.svcTgtSeq = svcTgtSeq;
	}

	/**
	 * @return TODO
	 */
	public Long getSpotDevSeq() {
		return spotDevSeq;
	}

	/**
	 * @param spotDevSeq TODO
	 */
	public void setSpotDevSeq(Long spotDevSeq) {
		this.spotDevSeq = spotDevSeq;
	}

	/**
	 * @return TODO
	 */
	public String getMacAddress() {
		return macAddress;
	}

	/**
	 * @param macAddress TODO
	 */
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	/**
	 * @return TODO
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacturer TODO
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return TODO
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark TODO
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return TODO
	 */
	public List<IoTDeviceInfo> getIoTDeviceInfos() {
		return ioTDeviceInfos;
	}

	/**
	 * @param ioTDeviceInfos TODO
	 */
	public void setIoTDeviceInfos(List<IoTDeviceInfo> ioTDeviceInfos) {
		this.ioTDeviceInfos = ioTDeviceInfos;
	}

}
