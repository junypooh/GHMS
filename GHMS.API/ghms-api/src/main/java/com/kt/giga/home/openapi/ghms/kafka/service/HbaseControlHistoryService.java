package com.kt.giga.home.openapi.ghms.kafka.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.giga.home.openapi.ghms.devices.dao.InitDeviceAddDao;
import com.kt.giga.home.openapi.ghms.devices.domain.key.SpotDevBasKey;
import com.kt.giga.home.openapi.ghms.kafka.KafkaDevSetupManager;
import com.kt.giga.home.openapi.ghms.kafka.dao.HbaseControlHistoryDao;
import com.kt.giga.home.openapi.ghms.kafka.dao.HbaseEventHistoryDao;
import com.kt.giga.home.openapi.ghms.kafka.dao.HbaseModeHistoryDao;
import com.kt.giga.home.openapi.ghms.kafka.entity.HbaseControlHistory;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaDevSetup;
import com.kt.giga.home.openapi.ghms.kafka.entity.KafkaDevSetupGroup;
import com.kt.giga.home.openapi.ghms.kafka.type.KafkaDevTrtSttusType;
import com.kt.giga.home.openapi.ghms.kafka.type.MicroTimestampType;
import com.kt.giga.home.openapi.ghms.kafka.type.SpotDevSeqAscCompare;
import com.kt.giga.home.openapi.ghms.util.string.StringUtils;
import com.kt.iot.reader.action.contl_occ_hist.ActionContlOccHist;

@Service
public class HbaseControlHistoryService {

	
	public static Log logger = LogFactory.getLog(HbaseControlHistoryService.class);
	
	@Resource(name = "hbaseConfiguration")
	private Configuration config;
	
	@Autowired private HbaseControlHistoryDao hbaseContlDao;
	@Autowired private HbaseEventHistoryDao hbaseEventDao;
	@Autowired private HbaseModeHistoryDao hbaseModeDao;

	@Autowired public KafkaDevSetupManager kafkaDevSetupManager;
	@Autowired public KafkaSpotDevService kafkaSpotDevService;
	@Autowired public KafkaMbrPwdService KafkaMbrPwdService;
    @Autowired private InitDeviceAddDao initDeviceAddDao;
	
	private Gson gson = new Gson();

//    private HConnection connection = null;
//    private HBaseClient client = null;	
//    private HBaseAdmin admin = null;
//	
//	@PostConstruct
//	public void init() throws IOException{
//		admin = new HBaseAdmin(config);
//		connection = admin.getConnection();
//		client = new HBaseClient(connection);
//	}
//	
//	@PreDestroy
//	public void destroy() throws IOException{
//		client.close();
//	}
	
	
	/** 
	 * 제어값 기록
	 * @param hist
	 */
	public void insert(HbaseControlHistory hist){
		String transacId = hist.getEventSeq();
		KafkaDevSetupGroup group = kafkaDevSetupManager.getDevSetupGroup(transacId);
		Map<String, Object> attr = group.getAttributes();
		attr.put("rowKey", hist.getRowKey());
		hbaseContlDao.insert(hist);
	}
	
	
	

	/**
	 * 제어 값을 업데이트
	 * @param transacId
	 * @param svcTgtSeq
	 * @param svcUnitCd
	 * @param colNm
	 * @param colVal
	 */
	@Transactional
	public void update(String svcUnitCd, KafkaDevSetup setup, String transacId, String colNm, String colVal){
		String svcTgtSeq = String.valueOf(setup.getSvcTgtSeq());
		String spotDevSeq = String.valueOf(setup.getSpotDevSeq());
		
		String rowKey = StringUtils.getHbaseRowKey(svcUnitCd, svcTgtSeq, spotDevSeq, transacId);
		try {
			rowKey = ActionContlOccHist.makeRowKeyContlOccHist(svcUnitCd, setup.getSvcTgtSeq(), setup.getSpotDevSeq(), MicroTimestampType.TRANSAC_ID.getTimestamp(transacId));
		} catch (Exception e) {
			e.printStackTrace();
		}		
		hbaseContlDao.update(rowKey, colNm, colVal);
	}
	
	/**
	 * 초기 : 제어 mode를 기록
	 * 2015,06, 08 : 모드의 경우 히스토리테이블을 따로 관리하기로 하였음(제어성공의 경우에만 모드정보를 기록하는 것으로 함).
	 * @param transacId
	 * @param svcTgtSeq
	 * @param svcUnitCd
	 * @param snsnCd
	 * @param snsnVal
	 * @param mbrSeq
	 */
	public void insert(String svcUnitCd, String svcTgtSeq, String snsnCd, String snsnVal, String transacId, String mbrSeq){

		KafkaDevSetupGroup group = kafkaDevSetupManager.getDevSetupGroup(transacId);

		if(group.isMode()){
			Map<String, Object> attr = group.getAttributes();
			attr.put("svcTgtSeq", svcTgtSeq);
			attr.put("modeVal", snsnVal);
			if(group.getKafkaSendCnt() == 0){
				insertMode(group);
			}
		}
		
	}
	
	public void insertMode(KafkaDevSetupGroup group){
		String rootTransacId = group.getTransacIds().get(0);
		
		Map<String, Object> attr = group.getAttributes();
		String svcTgtSeq = (String) attr.get("svcTgtSeq");
		String modeVal = (String) attr.get("modeVal");
		
		String rowKey = (Long.MAX_VALUE-Long.parseLong(svcTgtSeq)) + "-" +svcTgtSeq + "-" + MicroTimestampType.TRANSAC_ID.getReverseTimestamp(rootTransacId);
		
		hbaseModeDao.insert(rowKey, rootTransacId, svcTgtSeq, modeVal);
	}
	
	/**
	 * 제어 데이터 업데이트.(findList(transacid)와 함께 쓰임.)
	 * @param rowKey
	 * @param colNm
	 * @param colVal
	 */
	@Async
	public void update(String rowKey, String colNm, String colVal){
		hbaseContlDao.update(rowKey, colNm, colVal);
	}	

	
	/**
	 * @param transacId
	 * @return List<HbaseControlHistory>
	 */
	public List<HbaseControlHistory> findList(String transacId){
		SingleColumnValueFilter eventSeqFilter = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("contlSeq"), CompareOp.EQUAL, new SubstringComparator(transacId));
		eventSeqFilter.setFilterIfMissing(true);
		Long maxTimestamp = System.currentTimeMillis();
		Long minTimestamp = maxTimestamp - 2592000000L; /** 1개월전 */
		return hbaseContlDao.findControlHistoryList(minTimestamp, maxTimestamp, eventSeqFilter);
	}
	
	public void update(String transacId){
		for(HbaseControlHistory hist: findList(transacId)){
			logger.debug("update 11");
			String rowKey = hist.getRowKey();
			update(rowKey, "contlTrtSttusCd", KafkaDevTrtSttusType.SUCCESS.getValue());
		}
	}
	
	
	/**
	 * @param limit(가저올 그룹 row 제한)
	 * @param svcTgtSeq(서비스 타겟 일련번호:게이트웨이)
	 * @param spotDevSeq(현장장치 일련번호 spotDevSeq값 대체)
	 * @return Map<String, Object>
	 */
	public Map<String, Object> findList(int limit, Long svcTgtSeq, Long spotDevSeq){
		SpotDevBasKey spotDevBasKey = new SpotDevBasKey();
		spotDevBasKey.setSvcTgtSeq(svcTgtSeq);
		if(spotDevSeq != null){
			spotDevBasKey.setSpotDevSeq(spotDevSeq);
		}
		Long maxTimestamp = System.currentTimeMillis();
		
		Map<String, Object> output = new HashMap<String, Object>();
		List<Map<String, Object>> eventSeqList = new ArrayList<Map<String,Object>>();

		
		List<HbaseControlHistory> orgList = new ArrayList<>();
		for(Map<String,Object> scan : initDeviceAddDao.selectSpotDevsLastMtnDt(spotDevBasKey)){
			
			Long inSpotDevSeq = (Long) scan.get("spot_dev_seq");
			Object lastMtnDt = scan.get("last_mtn_dt");
			Long minTimestamp = maxTimestamp - 2592000000L; /** 1개월전 */
			if(lastMtnDt != null){
				if(minTimestamp < (Long) lastMtnDt){	/** 공초한 기간이 1개월내 일경우 */
					minTimestamp = (Long) lastMtnDt;					
				}
			}
			
			
//			SingleColumnValueFilter svcTgtSeqFilter = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("svcTgtSeq"), CompareOp.EQUAL, Bytes.toBytes(String.valueOf(svcTgtSeq)));
//			svcTgtSeqFilter.setFilterIfMissing(true);
//			SingleColumnValueFilter spotDevSeqFilter = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("spotDevSeq"), CompareOp.EQUAL, Bytes.toBytes(String.valueOf(inSpotDevSeq)));
//			spotDevSeqFilter.setFilterIfMissing(true);
//			List<HbaseControlHistory> list = hbaseContlDao.findControlHistoryList(minTimestamp, maxTimestamp, svcTgtSeqFilter, spotDevSeqFilter);
			
			List<HbaseControlHistory> list = hbaseContlDao.findControlHistoryList(svcTgtSeq, inSpotDevSeq, minTimestamp, maxTimestamp);
			
			if(list != null){
				orgList.addAll(list);
			}
		}
		
		/** scan for event out */
		List<HbaseControlHistory> orgListCE = new ArrayList<>();
		for(Map<String,Object> scan : initDeviceAddDao.selectSpotDevsLastMtnDt(spotDevBasKey)){
			Long inSpotDevSeq = (Long) scan.get("spot_dev_seq");
			Object lastMtnDt = scan.get("last_mtn_dt");
			Long minTimestamp = maxTimestamp - 2592000000L; /** 1개월전 */
			if(lastMtnDt != null){
				if(minTimestamp < (Long) lastMtnDt){	/** 공초한 기간이 1개월내 일경우 */
					minTimestamp = (Long) lastMtnDt;	
				}
			}

//			SingleColumnValueFilter svcTgtSeqFilter = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("svcTgtSeq"), CompareOp.EQUAL, Bytes.toBytes(String.valueOf(svcTgtSeq)));
//			svcTgtSeqFilter.setFilterIfMissing(true);
//			SingleColumnValueFilter spotDevSeqFilter = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("spotDevSeq"), CompareOp.EQUAL, Bytes.toBytes(String.valueOf(inSpotDevSeq)));
//			spotDevSeqFilter.setFilterIfMissing(true);
//			List<HbaseControlHistory> list = hbaseEventDao.findEventOutList(minTimestamp, maxTimestamp, svcTgtSeqFilter, spotDevSeqFilter);
			
			List<HbaseControlHistory> list = hbaseEventDao.findEventOutList(svcTgtSeq, inSpotDevSeq, minTimestamp, maxTimestamp);
			
			if(list != null){
				orgListCE.addAll(list);			
			}
		}
		
		
		if(orgListCE.size() > 0){
			orgList.addAll(orgListCE);
		}
				
		/** scan for mode history */
		SingleColumnValueFilter svcTgtSeqFilterForMode = new SingleColumnValueFilter(Bytes.toBytes("m"), Bytes.toBytes("svcTgtSeq"), CompareOp.EQUAL, new SubstringComparator(String.valueOf(svcTgtSeq)));
		svcTgtSeqFilterForMode.setFilterIfMissing(true);
		List<HbaseControlHistory> modeList = hbaseModeDao.findModeList(svcTgtSeqFilterForMode);
		if(modeList.size() > 0){
			orgList.addAll(modeList);
		}
		
		
		/** grouping eventSeq for all */
		Map<String, List<HbaseControlHistory>>  group = new HashMap<String, List<HbaseControlHistory>>();		
		for (HbaseControlHistory hbaseControlHistory : orgList) {
			String key  = hbaseControlHistory.getEventSeq();
			String trm = hbaseControlHistory.getContlTrtSttusCd();
			
			if(org.springframework.util.StringUtils.isEmpty(key)){
				continue;
			}
			
			if(!"01".equals(trm)){
				continue;
			}
			
			if(group.containsKey(key)){
		        List<HbaseControlHistory> list = group.get(key);
		        list.add(hbaseControlHistory);
		    }else{
		        List<HbaseControlHistory> list = new ArrayList<HbaseControlHistory>();
		        list.add(hbaseControlHistory);
		        group.put(key, list);
		    }
		}
		
		/** sort by key */
		TreeMap<String, List<HbaseControlHistory>> tm = new TreeMap<>(Collections.reverseOrder());
		tm.putAll(group);
		
		/** create output */
		String nextRowKey = "";
		
		int rowSize = tm.size();
		if(rowSize <= limit){
			nextRowKey = "end";
		}
		
		int idx = 0;
		for(String key : tm.keySet()){	/** current key */
			if(idx == limit){ /** end of loop*/
				break;
			}
			
			List<HbaseControlHistory> list = tm.get(key);	/** current list */
			List<Map<String, Object>> inputList = new ArrayList<>();	/** Map의 형태로 저장하기위한 변수 */
			Collections.sort(list, new SpotDevSeqAscCompare());
			for(HbaseControlHistory hist : list){
				if(!org.springframework.util.StringUtils.isEmpty(hist.getSpotDevSeq())){	/** mode(외출, 재택) 설정이 아닌경우 매핑*/
					HbaseControlHistory input = kafkaSpotDevService.selectControlHistory(svcTgtSeq, Long.parseLong(hist.getSpotDevSeq()), 0);
					hist.setDevTypeCd(input.getDevTypeCd());
					hist.setDevNm(input.getDevNm());
				}
				
				String attributes = hist.getAttributes();
				
				if(!org.springframework.util.StringUtils.isEmpty(attributes)){	/**	doorlock 열림시 사용자 매핑  */
					Map<String, Object> attr = gson.fromJson(attributes, new TypeToken<Map<String, Object>>(){}.getType());
					
					Object obj = attr.get("50997105");			
					if(obj != null){
						
						ArrayList<Double> arr = (ArrayList)obj;
						byte[] snsnVal = new byte[arr.size()];
						
						int arrIdx = 0;
						for(Double d : arr){
							snsnVal[arrIdx++] = d.byteValue();
						}
						
						if(snsnVal[4] == (byte)0x06){
							if(snsnVal[5] == (byte)0x02){ /** 안에서 열림 */
								hist.setEventId("001HIT003D0002-01");
							}else if(snsnVal[5] == (byte)0x06 && snsnVal[7] == (byte)0x63 && snsnVal[8] == (byte)0x03){	/** 밖에서 열림 */	
								if(snsnVal[9] == (byte)0x00){	/** 마스터로 열림 */
									hist.setEventId("001HIT003D0002-00");
								}else{
									String userNm = KafkaMbrPwdService.selectMbrPwdTxnNm(svcTgtSeq, Long.parseLong(hist.getSpotDevSeq()), Long.valueOf(snsnVal[9]));
									hist.setCustNm(userNm);
									if("RFID".equals(userNm)){	/** RFID로 열림 */
										hist.setEventId("001HIT003D0002-02");
									}else{	/** 사용자 비밀번호로 열림 */
										hist.setEventId("001HIT003D0002-03");
									}
								}
							}
						}
						
						
					}
				}
				
				inputList.add(hist.toMap());
				
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("eventSeq", key);
			map.put("eventList", inputList);
			eventSeqList.add(map);
			idx++;
			
		}
		
		output.put("totalCnt", eventSeqList.size());
		output.put("nextRowKey", nextRowKey);
		output.put("eventSeqList", eventSeqList);
		
		return output;
	}
	
	
}
	





























