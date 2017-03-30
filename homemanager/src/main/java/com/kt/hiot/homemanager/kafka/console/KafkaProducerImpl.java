package com.kt.hiot.homemanager.kafka.console;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kt.hiot.homemanager.kafka.type.KafkaEncdngType;
import com.kt.hiot.homemanager.kafka.type.KafkaMsgType;
import com.kt.smcp.gw.ca.comm.GwCode;

public class KafkaProducerImpl implements KafkaProducer{
	private static final Logger logger = LoggerFactory.getLogger(KafkaProducerImpl.class);

	private Producer<String, byte[]> producer;
	
	public KafkaProducerImpl(String broker) throws Exception {
		
		Properties props = new Properties();
		props.put("metadata.broker.list", broker);
		props.put("serializer.class", "kafka.serializer.DefaultEncoder");
		props.put("partitioner.class", "kafka.producer.DefaultPartitioner");
		props.put("request.required.acks", "0");
		producer = new Producer<String, byte[]>(new ProducerConfig(props));
	}
	
	@Override
	public void sendJson(String topic, KafkaMsgType msgType, String json){
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		short type = msgType.getValue();
		int length = json.length(); 
		
		//header 인코딩타입(1byte)+reserved(1byte)+메세지유형(2byte)+바디길이(4byte)
		byte[] header = new byte[8];
		header[0] = KafkaEncdngType.JSON.getValue();
		header[1] = 0;
		
		header[2] = (byte) ((type & 0xff00) >> 8);
		header[3] = (byte) (type & 0xff);
		
		header[4] = (byte) ((length>>24)&0xff);
		header[5] = (byte) ((length>>16)&0xff);
		header[6] = (byte) ((length>>8)&0xff);
		header[7] = (byte) ((length)&0xff);

		logger.debug("*************************Send Message****************************************************");
		logger.debug("header ::{encodingType:"+KafkaEncdngType.JSON.getValue()+",reserved:"+0+",msgType:"+type+",bodySize:"+length+"}");
		logger.debug("body ::" + json);
		logger.debug("*************************************************************************************************************************");
		
		try {
			stream.write(header);
			stream.write(json.getBytes());

			KeyedMessage<String, byte[]> data = new KeyedMessage<String, byte[]>(topic, stream.toByteArray());
			producer.send(data);

		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			IOUtils.closeQuietly(stream);
		}

	}

	public void close() {
		producer.close();
	}


}
