package com.kt.giga.home.openapi.cms.dao;

import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.cms.domain.NoticePm;

public interface NoticePmDao {
	public List<NoticePm> 	getNoticePmList(Map<String, Object> map);
	public int			 	getNoticePmAllCnt(Map<String, Object> map);
	public NoticePm		 	getNoticePmNow(Map<String, Object> map);
}
