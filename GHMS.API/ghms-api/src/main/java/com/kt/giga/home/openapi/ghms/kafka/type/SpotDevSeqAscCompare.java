package com.kt.giga.home.openapi.ghms.kafka.type;

import java.util.Comparator;

import com.kt.giga.home.openapi.ghms.kafka.entity.HbaseControlHistory;

public class SpotDevSeqAscCompare implements Comparator<HbaseControlHistory> {
	 
	/**
	 * 내림차순(DESC)
	 */
	@Override
	public int compare(HbaseControlHistory arg0, HbaseControlHistory arg1) {
		String uuid1 = arg0.getSpotDevSeq();
		String uuid2 = arg1.getSpotDevSeq();
		if(uuid1 == null){
			uuid1 = "0";
		}
		if(uuid2 == null){
			uuid2 = "0";
		}
		return uuid1.compareTo(uuid2);
	}

}