package com.kt.giga.home.openapi.kpns.kafka;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.hcam.dao.DeviceDao;
import com.kt.giga.home.openapi.health.paring.service.KthMsgApiService;
import com.kt.giga.home.openapi.kpns.service.KPNSService;

@Component
public class EventSubscribe {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ConsumerConnector consumer;
    private ExecutorService executor;
    
    @Autowired
	private APIEnv env;
    
    @Autowired
	private KPNSService kpnsService;
    
    @Autowired
	private KthMsgApiService kthMsgApiService;
    
    @Autowired
	private DeviceDao deviceDao;
 
    @PreDestroy
    public void shutdown() {
        if(consumer != null) consumer.shutdown();
        if(executor != null) executor.shutdown();
    }
 
    public void run(int _threadNumber) {
    	String reportUrl = env.getProperty("kpns.report.url");
    	
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(env.getProperty("kpns.kafka.topic"), new Integer(_threadNumber));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(env.getProperty("kpns.kafka.topic"));
 
        executor = Executors.newFixedThreadPool(_threadNumber);
 
        int threadNumber = 0;
        for (final KafkaStream stream : streams) {
            executor.submit(new EventConsumer(stream, threadNumber, kpnsService, kthMsgApiService, reportUrl, env.splitProperty("service.admin.telNoList")));
            threadNumber++;
        }
    }
 
    private static ConsumerConfig createConsumerConfig(String zookeeper, String groupId) {
        Properties props = new Properties();
        props.put("zookeeper.connect", zookeeper);
        props.put("group.id", groupId);
        props.put("zookeeper.session.timeout.ms", "500");
        props.put("zookeeper.sync.time.ms", "250");
        props.put("auto.commit.interval.ms", "1000");
 
        return new ConsumerConfig(props);
    }
	
    @PostConstruct
	public void init() { 
    	if("Y".equals(env.getProperty("kpns.kafka.useyn"))) {
    		consumer = Consumer.createJavaConsumerConnector(createConsumerConfig(env.getProperty("kpns.kafka.host"), env.getProperty("kpns.kafka.group")));
    		this.run(1);
    	}
	}
}
