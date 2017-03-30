package com.kt.giga.home.openapi.cms.dao;

import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.cms.domain.NoticePopup;

public interface NoticePopupDao {
	public List<NoticePopup> 	getNoticePopupList(Map<String, Object> map);
	public int				 	getNoticePopupAllCnt(Map<String, Object> map);
	public NoticePopup		 	getNoticePopupLast(Map<String, Object> map);
	public int					getNoticePopupViewCnt(Map<String, Object> map);
}
