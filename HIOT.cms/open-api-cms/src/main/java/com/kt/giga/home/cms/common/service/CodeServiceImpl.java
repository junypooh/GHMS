package com.kt.giga.home.cms.common.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.common.dao.*;
import com.kt.giga.home.cms.common.domain.*;

/**
 * 기초코드 서비스
 * @author 김용성
 *
 */
@Service
public class CodeServiceImpl implements CodeService {
	
	@Autowired
	private CodeDao codeDao;

	@Override
	public Code get(String code) {
		return codeDao.get(code);
	}

	@Override
	public List<Code> getList(String upCode) {
		return codeDao.getList(upCode);
	}

	@Override
	public String getServiceName(String svcCode) {
		return codeDao.getServiceName(svcCode);
	}

	@Override
	public String getUrl(String pkCode) {
		return codeDao.getUrl(pkCode);
	}

}
