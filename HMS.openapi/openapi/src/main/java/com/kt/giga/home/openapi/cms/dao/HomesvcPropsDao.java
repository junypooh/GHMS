package com.kt.giga.home.openapi.cms.dao;

import com.kt.giga.home.openapi.cms.domain.HomesvcProps;

public interface HomesvcPropsDao {
	public void		updateHomesvcProps(HomesvcProps homesvcProps);
	public void		insertHomesvcProps(HomesvcProps homesvcProps);
	public int		getHomesvcPropsCnt(HomesvcProps homesvcProps);

}
