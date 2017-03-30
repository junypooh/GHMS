package com.kt.giga.home.cms.inquiry.dao;

import java.util.*;

public interface MemberStatisticalChartDao {
	List<Map<String, Object>> getList(Map<String, Object >searchInfo);
}
