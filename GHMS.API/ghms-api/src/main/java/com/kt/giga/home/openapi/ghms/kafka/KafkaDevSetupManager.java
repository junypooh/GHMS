package com.kt.giga.home.openapi.ghms.kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.util.StringUtils;

import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaDevSetupGroup;
import com.kt.giga.home.openapi.ghms.kafka.type.MicroTimestampType;

public class KafkaDevSetupManager extends HashMap<String, KafkaDevSetupGroup>{

	private static final long serialVersionUID = 1L;
	
	/**
	 * @param transacId : root 또는 sub 트랜잭션 ID
	 * @return KafkaDevSetupGroup
	 */
	public KafkaDevSetupGroup getDevSetupGroup(String transacId){
		/** null인경우 새로 생성 */
		if(StringUtils.isEmpty(transacId)){
			return new KafkaDevSetupGroup();
		}
		
		KafkaDevSetupGroup output = this.get(transacId);
		/** root transacId */
		if(output != null){
			return output;
		}
		
		/** sub transacId */
		for(KafkaDevSetupGroup group : this.values()){
			if(group.getTransacIds().contains(transacId)){
				return group;
			}
		}
		
		/** default : 새로 생성 */
		return new KafkaDevSetupGroup();
		
	}
	
	/**
	 * @param needTransacIdCnt : 생성할 트랜잭션ID 갯수
	 * @param controlCnt : 카프카 제어 갯수
	 * @param mode : mode 상태(true : 외출/재택, false : 단순제어)
	 * @return 제어할 장치의 갯수만큼의 트랜잭션 ID 리스트를 리턴, 리스트의 0번째 값은 root 트랜잭션 ID임.
	 */
	public List<String> create(int needTransacIdCnt, int kafkaSendCnt, boolean mode){
		List<String> transacIds = new ArrayList<String>();
		for(int idx=0; idx<needTransacIdCnt; idx++){
			transacIds.add(MicroTimestampType.TRANSAC_ID.getTransacId());
		}
		
		KafkaDevSetupGroup group = new KafkaDevSetupGroup();
		group.setControl(true);
		group.setMode(mode);
		group.setTransacIds(transacIds);
		group.setKafkaSendCnt(kafkaSendCnt);
		
		this.put(transacIds.get(0), group);
		return transacIds;
	}
}
