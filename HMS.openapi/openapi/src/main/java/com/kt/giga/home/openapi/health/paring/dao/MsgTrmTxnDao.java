package com.kt.giga.home.openapi.health.paring.dao;

import java.util.Map;

import com.kt.giga.home.openapi.domain.SvcTgtBas;
import com.kt.giga.home.openapi.health.paring.domain.MsgTrmTxn;


public interface MsgTrmTxnDao {
	public void				setMsgTrmTxn(MsgTrmTxn msgTrmTxn);
	public MsgTrmTxn		getMsgTrmTxn(Map<String, Object> map);
	public SvcTgtBas		getSvcTgtBas(Map<String, Object> map);
	public String			getSvcTgtConnBas(Map<String, Object> map);
}
