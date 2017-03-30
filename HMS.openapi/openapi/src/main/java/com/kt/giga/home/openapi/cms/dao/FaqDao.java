package com.kt.giga.home.openapi.cms.dao;

import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.cms.domain.Faq;

public interface FaqDao {
	public List<Faq> 	getFaqList(Map<String, Object> map);
	public int		 	getFaqAllCnt(Map<String, Object> map);
}
