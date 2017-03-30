package com.kt.giga.home.openapi.cms.dao;

import java.util.List;

import com.kt.giga.home.openapi.cms.domain.Code;

public interface CodeDao {
	public Code 	getCode(String code);
	public void		updateCode(Code code);
	public List<Code> 	getCodeList(String fd_up_code);
}
