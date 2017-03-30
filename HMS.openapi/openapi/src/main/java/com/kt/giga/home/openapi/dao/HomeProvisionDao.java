package com.kt.giga.home.openapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kt.giga.home.openapi.domain.SpotDevBas;
import com.kt.giga.home.openapi.domain.SpotDevExpnsnBas;
import com.kt.giga.home.openapi.domain.SvcTgtBas;

public interface HomeProvisionDao {

	SvcTgtBas getSvcTgtBasBySvcContId(String svcContId);
	
	SpotDevBas getSpotDevBas(SpotDevBas spotDevBas);
	
	SpotDevBas getSpotDevBasBySpotDevId(SpotDevBas spotDevBas);
	
	List<SpotDevBas> getSpotDevBasListByUse(int svcTgtSeq);

	int getSvcTgtSeq();
	
	int getSpotDevSeq(int svcTgtSeq);
	
	int getDevModelSeq(String string);

	Map<String, Object> getDevModelBas(String cctvModelCd);

	HashMap<String, Object> getMbrBas(int svcTgtSeq);

	
	void insertSvcTgtBas(SvcTgtBas stb);
	
	void insertSpotDevBas(SpotDevBas spotBas);

	void insertSpotDevExpnsnBas(SpotDevExpnsnBas sdeb);


	void updateSpotDevBas(Map<String, Object> map);

	void updateSvcTgtBas(Map<String, Object> map);

	void updateSpotDevExpnsnBas(SpotDevExpnsnBas expn);
	
	
	void deleteSvcTgtBas(Map<String, Object> map);
	
	void deleteSpotDevBas(Map<String, Object> map);
	
	void deleteSpotDevConnBas(Map<String, Object> map);
	
	void deleteSpotDevSetupBas(Map<String, Object> map);
	
	void deleteSpotDevScdulSetupTxn(Map<String, Object> map);
	
	void deleteSpotDevGenlSetupTxn(Map<String, Object> map);
	
	void deleteSpotDevExpnsnBas(Map<String, Object> map);
}
