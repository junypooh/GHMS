package com.kt.giga.home.openapi.dao;

import com.kt.giga.home.openapi.domain.SvcTgtBas;

public interface SvcTgtBasDao {
	public SvcTgtBas getSvcTgtBas(String svcTgtSeq);
	public int getSvcTgtSeq(String svcTgtId);
}
