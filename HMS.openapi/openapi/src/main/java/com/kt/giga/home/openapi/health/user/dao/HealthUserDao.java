package com.kt.giga.home.openapi.health.user.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kt.giga.home.openapi.health.user.domain.HealthUser;

public interface HealthUserDao {

	int getUserCnt(String telNo);

	int getUserMbrsSeq(String telNo);

	int getMbrSeq();

	void insertMbrsBas(HealthUser user);

	void insertSvcMbrsRel(HealthUser user);

	//void insertSvcTgtAccsBas(HealthUser user);

	int getSvcTgtSeq();

	void insertSvcTgtBas(HealthUser user);

	int getSpotDevSeq(int svcTgtSeq);

	void insertSpotDevBas(HealthUser user);

	int getTermsYn(Map<String, Object> map);

	//void updateSvcTgtAccsBas(Map<String, Object> updateMap);

	void insertHealthProfTxn(Map<String, Object> proMap);

	void updateSvcTgtConnBas(Map<String, Object> updateMap);

	void insertSvcTgtConnBas(HealthUser user);

	int getDevModelSeq(String devTypeCd);

	List<String> getSnsnTagCd(int devModelSeq);

	void insertSpotDevBySnsnTagRel(Map<String, Object> tagMap);

	ArrayList<HashMap<String, String>> getInitProps(String svcCode);

	void updateSvcMbrRelMemWihtdraw(HealthUser user);

	void updateSvcTgtBasMemWihtdraw(HealthUser user);

	int cntSvcTgtConnAthnTxn(Map<String, Object> athnTxnMap);

	void insertSvcTgtConnAthnTxn(Map<String, Object> athnTxnMap);

	void updateSvcTgtConnAthnTxn(Map<String, Object> athnTxnMap);

	void removeApplLoSetupTxn(Map<String, Object> removeMap);

	void removeOtvPairingBas(Map<String, Object> removeMap);

	void removeHealthcareProfTxn(int mbrsSeq);

	ArrayList<HashMap<String, String>> getHomesvcProps(String svcCode);
	
	String getSvcTgtConnBasDeviceId(Map<String, Object> map);

}
