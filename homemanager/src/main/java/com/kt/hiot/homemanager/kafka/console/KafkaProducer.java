package com.kt.hiot.homemanager.kafka.console;

import com.kt.hiot.homemanager.kafka.type.KafkaMsgType;

public interface KafkaProducer {
	public void sendJson(String topic, KafkaMsgType type, String json);
	public void close();
}
