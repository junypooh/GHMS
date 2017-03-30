package com.kt.giga.home.openapi.cms.dao;

import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.cms.domain.Notice;

public interface NoticeDao {
	public List<Notice> 	getNoticeList(Map<String, Object> map);
	public int			 	getNoticeAllCnt(Map<String, Object> map);
}
