package com.kt.giga.home.openapi.kpns.dao;

import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.domain.SpotDevBas;
import com.kt.giga.home.openapi.domain.SpotDevExpnsnBas;
import com.kt.giga.home.openapi.kpns.domain.Event;

public interface EventDao {
	public SpotDevBas getSpotDevBas(Map<String, Object> map);
	public SpotDevExpnsnBas getSpotDevExpnsnBas(Map<String, Object> map);
	public List<Event> selectMsgTrmForwardTxn();
	public Event selectMsgTrmForwardTxnByMsgId(Event event);
	public long selectMsgTrmForwardTxnSeq();
	public void insertMsgTrmForwardTxn(Event event);
	public void updateMsgTrmForwardTxn(Event event);
	public void insertSpotDevExpnsnBas(SpotDevExpnsnBas spotDevExpnsnBas);
	public void updateSpotDevExpnsnBas(SpotDevExpnsnBas spotDevExpnsnBas);
}
