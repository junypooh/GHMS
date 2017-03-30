package com.kt.giga.home.openapi.cms.dao;

import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.cms.domain.Terms;

public interface TermsDao {
	public List<Terms> 	getTermsList(Map<String, Object> map);
}
