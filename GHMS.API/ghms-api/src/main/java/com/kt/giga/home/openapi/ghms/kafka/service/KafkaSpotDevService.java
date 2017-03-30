package com.kt.giga.home.openapi.ghms.kafka.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import kafka.consumer.ConsumerConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.giga.home.openapi.ghms.devices.service.InitDeviceAddService;
import com.kt.giga.home.openapi.ghms.kafka.KafkaApiConsumerPool;
import com.kt.giga.home.openapi.ghms.kafka.KafkaDevSetupManager;
import com.kt.giga.home.openapi.ghms.kafka.dao.KafkaSpotDevDao;
import com.kt.giga.home.openapi.ghms.kafka.entity.HbaseControlHistory;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaDevSetup;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaDevSetupGroup;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaSnsnTag;
import com.kt.giga.home.openapi.ghms.kafka.smcp.gw.ca.avro.ComplexEvent;
import com.kt.giga.home.openapi.ghms.kafka.type.KafkaDevTrtSttusType;
import com.kt.giga.home.openapi.ghms.kafka.type.MicroTimestampType;
import com.kt.giga.home.openapi.ghms.user.domain.vo.SnsnTagBasVo;
import com.kt.giga.home.openapi.ghms.user.service.UserService;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;
import com.kt.giga.home.openapi.ghms.util.string.StringUtils;
import com.kt.iot.reader.action.contl_occ_hist.ActionContlOccHist;

/**
 * @author jnam
 */
@Service
public class KafkaSpotDevService{

	public static Log logger = LogFactory.getLog(KafkaSpotDevService.class);
	

    @Value("${consumer.api.topic}") public String topic;
	@Value("${consumer.api.threadcount}") public int threadCount;
    @Value("${consumer.api.zookeeper.connect}") public String zookeeperConnect;
    @Value("${consumer.api.group.id}") public String groupId;
	
	@Autowired public KafkaSpotDevDao kafkaSpotDevDao;
	@Autowired public KafkaDevSetupManager kafkaDevSetupManager;
	@Autowired public UserService userService;
	
	/**
	 * @param input SpotDevGenlSetupTxn 데이터를 저장한다.
	 */
	public void saveSpotDevGenlSetupTxn(Map<String, Object> input){
		int count = kafkaSpotDevDao.selectSpotDevGenlSetupTxnCnt(input);
		if(count > 0){
			kafkaSpotDevDao.updateSpotDevGenlSetupTxn(input);
		}else{
			kafkaSpotDevDao.insertSpotDevGenlSetupTxn(input);
		}
	}
	
	@Transactional
	public void saveSpotDevGenlSetupTxn(KafkaDevSetup setup) throws APIException{
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("svcTgtSeq", setup.getSvcTgtSeq());
		input.put("spotDevSeq", setup.getSpotDevSeq());
		input.put("delYn", "N");
		input.put("cretrId", "KAFKA");
		input.put("amdrId", "KAFKA");
		
		for (KafkaSnsnTag snsnTag : setup.getSnsnTagList()) {
			String snsnTagCd = snsnTag.getSnsnTagCd();
			
			for(SnsnTagBasVo snsnTagBasVo : userService.getSnsTagBasList()){	/** 등록된 센싱태그캐시의 경우에만 SAVE */
				if(snsnTagCd.equals(snsnTagBasVo.getSnsn_tag_cd())){	
					input.put("snsnTagCd", snsnTagCd);
					input.put("rlNumVal", snsnTag.getRlNumVal());
					saveSpotDevGenlSetupTxn(input);
				}
			}
			
			
		}
	}
	
	/**
	 * @param spotDevSeq
	 * @param svcTgtSeq
	 * @param mbrSeq
	 * @param svcUnitCode
	 * @param transactionId
	 * @return 센싱태그코드와 값을 제외한 HbaseControlHistory을 리턴
	 */
	public HbaseControlHistory selectHbaseControlHistory(String svcUnitCode, long svcTgtSeq, long spotDevSeq, String snsnCd, String snsnVal, long mbrSeq, String transacId){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		long timestamp = System.currentTimeMillis();
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("spotDevSeq", spotDevSeq);
		input.put("svcTgtSeq", svcTgtSeq);
		input.put("mbrSeq", mbrSeq);
		
		HbaseControlHistory output = kafkaSpotDevDao.selectHbaseControlHistory(input); 
		String rowKey = StringUtils.getHbaseRowKey(svcUnitCode, String.valueOf(svcTgtSeq), String.valueOf(spotDevSeq), transacId);
		try {
			rowKey = ActionContlOccHist.makeRowKeyContlOccHist(svcUnitCode, svcTgtSeq, spotDevSeq, timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		output.setRowKey(rowKey);
		output.setEventSeq(transacId);
		output.setContDt(format.format(timestamp));
		output.setContlCd(snsnCd);
		
		/** 침입감지이면서 싱글제어인 경우 return 값음  성공 (01)로 설정 */
		KafkaDevSetupGroup group = kafkaDevSetupManager.getDevSetupGroup(transacId);
		if("99990000".equals(snsnCd) && !group.isMode()){
			output.setContlTrtSttusCd(KafkaDevTrtSttusType.SUCCESS.getValue());
		}
		
		/** 간편모드이면서 침입감지만 설정하는 경우 리턴값이 없기때문에 성공으로 설정. */
		if("99990000".equals(snsnCd) && group.isMode() && group.getKafkaSendCnt() == 0){
			output.setContlTrtSttusCd(KafkaDevTrtSttusType.SUCCESS.getValue());
		}
		
		output.setContlVal(snsnVal);
		return output;
	}
	
	/**
	 * @param svcTgtSeq
	 * @param spotDevSeq
	 * @param mbrSeq
	 * @return spot_dev_bas 테이블의 기본정보
	 */
	public HbaseControlHistory selectControlHistory(long svcTgtSeq, long spotDevSeq, long mbrSeq){
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("spotDevSeq", spotDevSeq);
		input.put("svcTgtSeq", svcTgtSeq);
		input.put("mbrSeq", mbrSeq);
		return kafkaSpotDevDao.selectHbaseControlHistory(input);
	}
	
	/**
	 * @param svcTgtSeq
	 * @param spotDevSeq
	 * @return List<Map<String, Object>> : 침입감지센서 On 리스트
	 */
	public List<Map<String, Object>> selectOpenAndCloseSensorOnList(Long svcTgtSeq, Long spotDevSeq){
		
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("snsnTagCd", "99990000");
		input.put("snsnTagVal", "01");
		input.put("svcTgtSeq", svcTgtSeq);
		input.put("spotDevSeq", spotDevSeq);
		
		/** ComplexEvent 형태의 객체 타입으로 맞쳐주기위해 rownum 삭제 */
		List<Map<String, Object>> output = kafkaSpotDevDao.selectSensorList(input);
		for(Map<String, Object> map : output){
			if(map.containsKey("rownum")){
				map.remove("rownum");
			}
		}
		
		return output;
	}
	
	public String selectSpotDevNm(Long svcTgtSeq, Long spotDevSeq){
		Map<String, Object> input = new HashMap<String, Object>();
		
		input.put("svcTgtSeq", svcTgtSeq);
		input.put("spotDevSeq", spotDevSeq);
		
		return kafkaSpotDevDao.selectSpotDevNm(input);
	}
	
	public Long selectSpotDevSeq(Long svcTgtSeq, String spotDevId){
		Map<String, Object> input = new HashMap<String, Object>();
		
		input.put("svcTgtSeq", svcTgtSeq);
		input.put("spotDevId", spotDevId);
		
		return kafkaSpotDevDao.selectSpotDevSeq(input);
	}
}





















