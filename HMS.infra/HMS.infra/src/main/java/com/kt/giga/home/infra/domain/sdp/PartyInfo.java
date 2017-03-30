package com.kt.giga.home.infra.domain.sdp;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="arrayofparty", namespace="http://kt.com/sdp_myolleh2")
public class PartyInfo {

	private String partyDetailTypeCd;
	private String birthDate;
	private String subscpnOverYn;
	private List<PartyMapInfo> partyMap;
	private List<Subscription> subscriptionInfo;
	
	public String getPartyDetailTypeCd() {
		return partyDetailTypeCd;
	}

	@XmlElement(name="Party_Detail_Type_Cd", namespace="http://kt.com/sdp_myolleh2")
	public void setPartyDetailTypeCd(String partyDetailTypeCd) {
		this.partyDetailTypeCd = partyDetailTypeCd;
	}

	public String getBirthDate() {
		return birthDate;
	}

	@XmlElement(name="Birth_Date", namespace="http://kt.com/sdp_myolleh2")
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getSubscpnOverYn() {
		return subscpnOverYn;
	}

	@XmlElement(name="Subscpn_Over_Yn", namespace="http://kt.com/sdp_myolleh2")
	public void setSubscpnOverYn(String subscpnOverYn) {
		this.subscpnOverYn = subscpnOverYn;
	}

	public List<PartyMapInfo> getPartyMap() {
		return partyMap;
	}

	@XmlElementWrapper(name="ListofPartyMap", namespace="http://kt.com/sdp_myolleh2")
	@XmlElementRefs({
		@XmlElementRef(name="listofpartymap", namespace="http://kt.com/sdp_myolleh2", type=PartyMapInfo.class)
	})
	public void setPartyMap(List<PartyMapInfo> partyMap) {
		this.partyMap = partyMap;
	}

	public List<Subscription> getSubscriptionInfo() {
		return subscriptionInfo;
	}

	@XmlElementWrapper(name="ListofSubscription", namespace="http://kt.com/sdp_myolleh2")
	@XmlElementRefs({
		@XmlElementRef(name="listofsubscription", namespace="http://kt.com/sdp_myolleh2", type=Subscription.class)
	})
	public void setSubscriptionInfo(List<Subscription> subscriptionInfo) {
		this.subscriptionInfo = subscriptionInfo;
	}
}
