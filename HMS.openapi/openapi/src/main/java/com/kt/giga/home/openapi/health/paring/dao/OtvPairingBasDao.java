package com.kt.giga.home.openapi.health.paring.dao;

import java.util.List; 
import java.util.Map;

import com.kt.giga.home.openapi.health.paring.domain.OtvPairingBas;

public interface OtvPairingBasDao {
	public int 					getSaidCnt(OtvPairingBas otvPairingBas);
	public int 					getParingCnt(Map<String, Object> map);
	public int 					getMemberCnt(Map<String, Object> map);
	public void					setOtvPairingBas(OtvPairingBas otvPairingBas);
	public int 					getOtvPairingBasCnt(OtvPairingBas otvPairingBas);
	public int 					modifyOtvPairingBasCnt(OtvPairingBas otvPairingBas);
	public void 				modifyOtvPairingBas(OtvPairingBas otvPairingBas);
	public List<OtvPairingBas> 	getOtvPairingBasList(OtvPairingBas otvPairingBas);
	public void 				delPairingStb(OtvPairingBas otvPairingBas);
	
}
