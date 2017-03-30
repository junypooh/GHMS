package com.kt.hiot.homemanager.kafka.console;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.kt.hiot.homemanager.kafka.type.KafkaEncdngType;
import com.kt.smcp.gw.ca.avro.CollectEvent;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class KafkaConsumer extends Thread{
	private Log logger = LogFactory.getLog(this.getClass());
	public static final String API_TOPIC = "API_SND_ITG_001HIT003";
	public static final String EC_TOPIC = "EC_SND_ITG_001HIT003";
	
	
	private KafkaDevSetupManager manager;
	private String topic = null;
	ConsumerConnector consumer = null;
	
    private KafkaStream<byte[], byte[]> kafkaStream;
	
    Gson gson = new Gson();
    
	public KafkaConsumer(String topic, String zookeeperConnection, String groupId, KafkaDevSetupManager manager){
		this.topic = topic;
		this.manager = manager;
		Properties properties = new Properties();

		properties.put("zookeeper.connect", zookeeperConnection);
		properties.put("group.id", groupId);
		
		consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
		
	}

	@Override
	public void interrupt() {
		if (consumer != null) consumer.shutdown();
		super.interrupt();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void run() {
		Map<String, Integer> topicCount = new HashMap<String, Integer>();
		topicCount.put(topic, new Integer(1));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = consumer.createMessageStreams(topicCount);
		List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(topic);
		for (final KafkaStream stream : streams) {
			ConsumerIterator<byte[], byte[]> consumerIte = stream.iterator();
			while (consumerIte.hasNext()) {
				byte[] message = consumerIte.next().message();
				ByteArrayInputStream in = new ByteArrayInputStream(message);
				try {
					//header 인코딩타입(1byte)+reserved(1byte)+메세지유형(2byte)+바디길이(4byte)
					byte[] encBuffer  = new byte[1];
					byte[] resvdBuffer  = new byte[1];
					byte[] msgTypeBuffer  = new byte[2];
					byte[] bodySizeBuffer  = new byte[4];

			        in.read(encBuffer, 0, 1);
			        in.read(resvdBuffer, 0, 1);
			        in.read(msgTypeBuffer, 0, 2);
			        in.read(bodySizeBuffer, 0, 4);
			        
			        short msgType = (short) (((msgTypeBuffer[0] << 8)) | ((msgTypeBuffer[1] & 0xff)));
			        int bodySize = ((bodySizeBuffer[0]&0xff)<<24) | ((bodySizeBuffer[1]&0xff)<<16) | ((bodySizeBuffer[2]&0xff)<< 8) | (bodySizeBuffer[3]&0xff);
					
			        //header 8 byte를 제외한 나머지는 모두 body
					byte[] body = new byte[message.length-8];

					logger.debug("*************************Received Message*************************************");
					logger.debug("header ::{encodingType:"+encBuffer[0]+",reserved:"+resvdBuffer[0]+",msgType:"+msgType+",bodySize:"+bodySize+"}");

					
//					int read = 0;
//			        while(read < bodySize){
//			        	int diff = bodySize-read;
//			            read += in.read(body, read, diff);
//			        }
					in.read(body);
					
					
					switch(KafkaEncdngType.fromByte(encBuffer[0])){
					case AVRO: 
						BinaryDecoder binaryDecoder = DecoderFactory.get().binaryDecoder(body, null);
						SpecificDatumReader<CollectEvent> specificDatumReader = new SpecificDatumReader<CollectEvent>(CollectEvent.class);
						CollectEvent event = specificDatumReader.read(null, binaryDecoder);
						logger.debug("body ::" + event.toString());
						break;
					default : 
				        String json = new String(body, "UTF-8");
						logger.debug("body ::" + json);
					}			        
					manager.clear();
					logger.debug("*********************************************************************");						

					
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					IOUtils.closeQuietly(in);
				}
				
				
				
			}
		}
		if (consumer != null) consumer.shutdown();
		
	}
	
	
}
