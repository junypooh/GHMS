package com.kt.giga.home.infra.client.event;

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
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kt.giga.home.infra.domain.event.ActionTask;
import com.kt.giga.home.infra.domain.event.EventAction;
import com.kt.giga.home.infra.domain.event.EventInformation;
import com.kt.giga.home.infra.domain.kpns.PushInfoRequest;
import com.kt.giga.home.infra.service.kpns.KPNSService;

@Component
public class EventSubscribe {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private ConsumerConnector consumer;
    private ExecutorService executor;
    
    @Value("#{system['kpns.kafka.useyn']}")
 	private String useYn;
    
    // kafka 토픽
 	@Value("#{system['kpns.kafka.topic']}")
 	private String TOPIC;
 	
 	// kafka 그룹
  	@Value("#{system['kpns.kafka.group']}")
  	private String GROUP;
 	
 	// kafka 호스트 정보
    @Value("#{system['kpns.kafka.host']}")
	private String ZOOKEEPER_CONNECTION;
    
    @Autowired
	private KPNSService kpnsService;
 
    @PreDestroy
    public void shutdown() {
        if (consumer != null) consumer.shutdown();
        if (executor != null) executor.shutdown();
    }
 
    public void run(int _threadNumber) {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(TOPIC, new Integer(_threadNumber));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(TOPIC);
 
        executor = Executors.newFixedThreadPool(_threadNumber);
 
        int threadNumber = 0;
        for (final KafkaStream stream : streams) {
            executor.submit(new EventConsumer(stream, threadNumber, kpnsService));
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
		if("Y".equals(useYn)) {
			consumer = Consumer.createJavaConsumerConnector(createConsumerConfig(ZOOKEEPER_CONNECTION, GROUP));
	        this.run(1);
		}
	}
}
