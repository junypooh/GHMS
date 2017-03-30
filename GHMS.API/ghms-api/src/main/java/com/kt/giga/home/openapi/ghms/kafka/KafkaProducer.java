package com.kt.giga.home.openapi.ghms.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import com.kt.giga.home.openapi.ghms.kafka.type.KafkaMsgType;

public interface KafkaProducer {
	public void sendJson(String topic, KafkaMsgType type, String json);
	public void sendAvro(String topic, SpecificRecordBase event);
}
