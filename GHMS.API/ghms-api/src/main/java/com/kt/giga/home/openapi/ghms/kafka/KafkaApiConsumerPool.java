package com.kt.giga.home.openapi.ghms.kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kt.giga.home.openapi.ghms.devices.dao.InitDeviceAddDao;
import com.kt.giga.home.openapi.ghms.devices.service.InitDeviceAddService;
import com.kt.giga.home.openapi.ghms.kafka.service.HbaseControlHistoryService;
import com.kt.giga.home.openapi.ghms.kafka.service.KafkaMbrPwdService;
import com.kt.giga.home.openapi.ghms.kafka.service.KafkaSpotDevService;
import com.kt.giga.home.openapi.ghms.kafka.type.MicroTimestampType;
import com.kt.giga.home.openapi.ghms.ktInfra.domain.key.PushInfoRequest;
import com.kt.giga.home.openapi.ghms.ktInfra.service.KPNSService;
import com.kt.giga.home.openapi.ghms.ktInfra.service.KthMsgApiService;
import com.kt.giga.home.openapi.ghms.util.exception.APIException;


/**
 * @author jnam
 */
@Service
@Scope("singleton")
public class KafkaApiConsumerPool {
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	private ConsumerConnector consumer;
	private ConsumerConnector consumer332;
	private ConsumerConnector collectEventConsumer;
	private ConsumerConnector eventActionConsumer;

	private ExecutorService executor;
	private ExecutorService executor332;
	private ExecutorService collectEventExecutor;	
	private ExecutorService eventActionExecutor;	
	
    @Value("${consumer.api.topic}") public String topic;
    @Value("${consumer.api.topic.collect}") public String collectEventTopic;
    @Value("${consumer.api.topic.eventaction}") public String eventActionTopic;
    
	@Value("${consumer.api.threadcount}") public int threadCount;
    @Value("${consumer.api.zookeeper.connect}") public String zookeeperConnect;
    @Value("${consumer.api.group.id}") public String groupId;
    @Value("${devsetup.timeout}") public long timeout;
    @Value("${hbase.svc.code}") public String unitSvcCode;
	
	@Autowired public InitDeviceAddService initDeviceAddService;
	@Autowired public KafkaSpotDevService kafkaSpotDevService;
	@Autowired public KafkaMbrPwdService KafkaMbrPwdService;
    @Autowired public KafkaDevSetupManager kafkaDevSetupManager;
    @Autowired public HbaseControlHistoryService hbaseControlHistoryService;
    @Autowired public KPNSService kpnsService; 
    @Autowired public KthMsgApiService kthMsgApiService; 
    @Autowired public InitDeviceAddDao initDeviceAddDao;
    
    /**
     * @see 5분에 한번씩 돌면서 3분이 지난 제어 트랜잭션은 삭제한다. 
     */
    @Scheduled(fixedDelay=300000)
    public void removeKafkaDevSetup(){
    	logger.info("*********************************************************");
    	logger.info("***** Scheduler : kafka device setup to remove **********");
    	logger.info("*********************************************************");
    	long systemTime = System.currentTimeMillis();
    	List<String> removeKeyList = new ArrayList<String>();
    	for (String transacId : kafkaDevSetupManager.keySet()) { /** select to remove targets */
    		try{
	        	Long timestamp = MicroTimestampType.TRANSAC_ID.getTimestamp(transacId);
	        	if(timestamp != null && 	/** not null */
	        			(systemTime > (timestamp.longValue()+timeout)) ){ /** and timeout */				        		
	        		removeKeyList.add(transacId);
	        	}
    		}catch(Exception e){
    			removeKeyList.add(transacId);
    		}
		}
    	
		for(String key : removeKeyList){	/** remove targets */
			if(kafkaDevSetupManager.containsKey(key)){
				kafkaDevSetupManager.remove(key);	
				logger.info("**** removed transacId :: "+key);
			}
		}
    	
    }
    
	@PreDestroy
	public void shutdown() {
		if (consumer != null) consumer.shutdown();
		if (executor != null) executor.shutdown();
		if (consumer332 != null) consumer332.shutdown();
		if (executor332 != null) executor332.shutdown();
		if (collectEventConsumer != null) collectEventConsumer.shutdown();
		if (collectEventExecutor != null) collectEventExecutor.shutdown();
		if (eventActionConsumer != null) eventActionConsumer.shutdown();
		if (eventActionExecutor != null) eventActionExecutor.shutdown();
	}
	
	/**
	 * @see kafka 컨슈머를 구동한다.
	 */
	@PostConstruct
	public void run() {
		Properties prop = new Properties();
		prop.put("zookeeper.connect", zookeeperConnect);
		prop.put("group.id", groupId);
		ConsumerConfig consumerConfig = new ConsumerConfig(prop);

		Properties propOne = new Properties();
		propOne.put("zookeeper.connect", zookeeperConnect);
		propOne.put("group.id", "GHMS_API_GP_ONE");
		ConsumerConfig consumerConfig2 = new ConsumerConfig(propOne);

		
		/** comsumer for EC_SND_ITG_001HIT003 */
		consumer = Consumer.createJavaConsumerConnector(consumerConfig);		
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, threadCount);
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> topicListeners = consumerMap.get(topic);
		executor = Executors.newFixedThreadPool(threadCount);
		for(Integer i = 0; i < threadCount; i++ ){
			KafkaStream<byte[], byte[]> stream =  topicListeners.get(i);
			executor.submit(new KafkaApiConsumer(stream, i, this));
		}
		
		
		/** comsumer for COLLECT_EVENT_001HIT003 */
		collectEventConsumer = Consumer.createJavaConsumerConnector(consumerConfig2);		
		Map<String, Integer> topicCountMap2 = new HashMap<String, Integer>();
		topicCountMap2.put(collectEventTopic, threadCount);
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap2 = collectEventConsumer.createMessageStreams(topicCountMap2);
		List<KafkaStream<byte[], byte[]>> topicListeners2 = consumerMap2.get(collectEventTopic);
		collectEventExecutor = Executors.newFixedThreadPool(threadCount);
		for(Integer i = 0; i < threadCount; i++ ){
			KafkaStream<byte[], byte[]> stream =  topicListeners2.get(i);
			collectEventExecutor.submit(new KafkaApiConsumerForCollectEvent(stream, i, this));
		}
		
		/** comsumer for PROPAGATION_ACTION_001HIT003 */
		eventActionConsumer = Consumer.createJavaConsumerConnector(consumerConfig2);		
		Map<String, Integer> topicCountMap3 = new HashMap<String, Integer>();
		topicCountMap3.put(eventActionTopic, threadCount);
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap3 = eventActionConsumer.createMessageStreams(topicCountMap3);
		List<KafkaStream<byte[], byte[]>> topicListeners3 = consumerMap3.get(eventActionTopic);
		eventActionExecutor = Executors.newFixedThreadPool(threadCount);
		for(Integer i = 0; i < threadCount; i++ ){
			KafkaStream<byte[], byte[]> stream =  topicListeners3.get(i);
			eventActionExecutor.submit(new KafkaApiConsumerForEventAction(stream, i, this));
		}		
		
		/** comsumer332 for EC_SND_ITG_001HIT003 */
		consumer332 = Consumer.createJavaConsumerConnector(consumerConfig2);		
		Map<String, Integer> topicCountMap4 = new HashMap<String, Integer>();
		topicCountMap4.put(topic, threadCount);
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap4 = consumer332.createMessageStreams(topicCountMap4);
		List<KafkaStream<byte[], byte[]>> topicListeners4 = consumerMap4.get(topic);
		executor332 = Executors.newFixedThreadPool(threadCount);
		for(Integer i = 0; i < threadCount; i++ ){
			KafkaStream<byte[], byte[]> stream =  topicListeners4.get(i);
			executor332.submit(new KafkaApiConsumerFor332(stream, i, this));
		}	
		
	}

}
