package com.kt.giga.home.infra.dao.silk;

import java.util.List;

import com.kt.giga.home.infra.domain.silk.OllehRtrmmb;

public interface OllehRtrmmbDao {

	List<OllehRtrmmb> selectOllehRtrmmb(OllehRtrmmb ollehRtrmmb);
	
	void updateOllehRtrmmb(OllehRtrmmb ollehRtrmmb);
}
