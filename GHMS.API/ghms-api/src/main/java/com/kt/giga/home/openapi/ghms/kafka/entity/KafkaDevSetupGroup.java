package com.kt.giga.home.openapi.ghms.kafka.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.domn.CorePrcssData;

public class KafkaDevSetupGroup {	
	private Log logger = LogFactory.getLog(this.getClass());
	
	/** manager에 의해 관리여부를 체크하는 flag */
	private boolean control = false;
	
	/** db 저장여부를 체크하는 flag */
	private boolean saved = false;
	
	/** 제어 모드에 대한 플래그 */
	private boolean mode = false;
	
	/** 인크루전/익스클루전 성공실패유무 */
	private boolean inexClusion = false;
	
	/** 확장가능한 필요한 값 */
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	/** 트랜잭션 도착시 처리될 콜백 인터페이스 */
	private KafkaCallback callback = new KafkaCallback() {
		@Override
		public void callback(CorePrcssData coreData) {
		}
	};
	
	/** key is svcTgtSeq + spotDevSeq */
	private Map<String, KafkaDevSetup> devSetup = new HashMap<String, KafkaDevSetup>();
	
	/** 여러개의 제어를 보내고 하나의 트랜잭션으로 관리하기 위한 리스트 */
	private List<String> transacIds = new ArrayList<String>(); 
	
	/** 카프카로 보낸명령의 갯수 */
	private int kafkaSendCnt = 0;
	
	public Map<String, KafkaDevSetup> getDevSetup() {
		return devSetup;
	}
	public void setDevSetup(Map<String, KafkaDevSetup> devSetup) {
		this.devSetup = devSetup;
	}

	public boolean isFull(){ /** db 저장이 안된 경우는 Full이 아님. */
		if(!saved){
			return false;
		}
		return isReturnAll();
	}
	public boolean isReturnAll(){	/** 장치로부터 return 된 갯수를 체크. */
		logger.info("***************************************************************");
		logger.info("devSetup.size() : "+devSetup.size());
		logger.info("transacIds.size() : "+transacIds.size());
		logger.info("kafkaSendCnt : "+kafkaSendCnt);
		for(String transacId : transacIds){
			logger.info("transacId : "+transacId);
		}
		logger.info("***************************************************************");
		return ((kafkaSendCnt != 0) && (devSetup.size() >= kafkaSendCnt));
	}
	
	public boolean isInexClusion() {
		return inexClusion;
	}
	public void setInexClusion(boolean inexClusion) {
		this.inexClusion = inexClusion;
	}
	public boolean isControl() {
		return control;
	}
	public void setControl(boolean control) {
		this.control = control;
	}
	public boolean isSaved() {
		return saved;
	}
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	public List<String> getTransacIds() {
		return transacIds;
	}
	public void setTransacIds(List<String> transacIds) {
		this.transacIds = transacIds;
	}
	public boolean isMode() {
		return mode;
	}
	public void setMode(boolean mode) {
		this.mode = mode;
	}
	public int getKafkaSendCnt() {
		return kafkaSendCnt;
	}
	public void setKafkaSendCnt(int kafkaSendCnt) {
		this.kafkaSendCnt = kafkaSendCnt;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public KafkaCallback getCallback() {
		return callback;
	}
	public void setCallback(KafkaCallback callback) {
		this.callback = callback;
	}
	
	@Override
	public String toString() {
		return "KafkaDevSetupGroup [logger=" + logger + ", control=" + control + ", saved=" + saved + ", mode=" + mode
				+ ", inexClusion=" + inexClusion + ", attributes=" + attributes + ", callback=" + callback
				+ ", devSetup=" + devSetup + ", transacIds=" + transacIds + ", kafkaSendCnt=" + kafkaSendCnt + "]";
	}
	
}
