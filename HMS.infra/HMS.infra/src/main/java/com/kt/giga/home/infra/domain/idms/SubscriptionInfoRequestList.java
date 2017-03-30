package com.kt.giga.home.infra.domain.idms;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.kt.giga.home.infra.domain.commons.HttpRestObjectRequest;
import com.kt.giga.home.infra.domain.sdp.PartyInfo;

public class SubscriptionInfoRequestList extends HttpRestObjectRequest {

	private List<SubscriptionInfoRequest> list;

	public List<SubscriptionInfoRequest> getList() {
		return list;
	}

	public void setList(List<SubscriptionInfoRequest> list) {
		this.list = list;
	}	
}
