package com.kt.giga.home.openapi.hcam.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.kt.giga.home.openapi.common.APIEnv;
import com.kt.giga.home.openapi.kpns.domain.ActionTask;
import com.kt.giga.home.openapi.kpns.domain.ComplexEvent;
import com.kt.giga.home.openapi.kpns.domain.EventAction;
import com.kt.giga.home.openapi.kpns.domain.EventInformation;
import com.kt.giga.home.openapi.util.JsonUtils;

/**
 * Event Core 연동 서비스
 * 3MP 에서 제공한 샘플 기반으로 작성함
 *
 * @author
 *
 */
@Service("HCam.EventEmitService")
public class EventEmitService {

	/**
	 * 로거
	 */
	private Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * OpenAPI 환경 프라퍼티
	 */
	@Autowired
	private APIEnv env;

	private Producer<String, byte[]> producer;
	private final EncoderFactory avroEncoderFactory = EncoderFactory.get();
	private final SpecificDatumWriter<EventAction> avroEventWriter = new SpecificDatumWriter<EventAction>(EventAction.SCHEMA$);
	private final SpecificDatumWriter<ComplexEvent> avroComplexEventWriter = new SpecificDatumWriter<ComplexEvent>(ComplexEvent.SCHEMA$);

	/**
	 * Producer 생성 메쏘드
	 *
	 * @return						Producer
	 */
	private Producer<String, byte[]> createProducer() {

		String broker = env.getProperty("eventEmitter.broker");

		Properties props = new Properties();
		props.put("metadata.broker.list",	broker);
		props.put("serializer.class",		"kafka.serializer.DefaultEncoder");
		props.put("partitioner.class",		"kafka.producer.DefaultPartitioner");

		props.put("request.required.acks",	"0");									// 기다리지 않음
		props.put("producer.type",			"async");								// 비동기 모드
		props.put("queue.buffering.max.ms",	"100");									// 0.1초간 버퍼링
		props.put("batch.num.messages",		"1000");								// 1000 개 버퍼링

		return new Producer<String, byte[]>(new ProducerConfig(props));
	}

	/**
	 * Producer GET 메쏘드. 널인 경우 신규 생성
	 *
	 * @return						Producer
	 */
	private Producer<String, byte[]> getProducer() {

		if(producer == null) {
			producer = createProducer();
		}

		return producer;
	}

	/**
	 * 이벤트 발송 메쏘드
	 *
	 * @param event					이벤트
	 * @param topic					토픽 이름
	 */
	private void send(EventAction event, String topic) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		BinaryEncoder binaryEncoder = avroEncoderFactory.binaryEncoder(stream, null);

		try {

			avroEventWriter.write(event, binaryEncoder);
			binaryEncoder.flush();
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
		}

		IOUtils.closeQuietly(stream);

		topic = StringUtils.defaultIfBlank(topic, env.getProperty("eventEmitter.topic"));

		getProducer().send(new KeyedMessage<String, byte[]>(topic, stream.toByteArray()));
	}

	/**
	 * 복합 이벤트 발송 메쏘드
	 *
	 * @param event					복합 이벤트
	 * @param topic					토픽 이름
	 */
	private void send(ComplexEvent event, String topic) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		BinaryEncoder binaryEncoder = avroEncoderFactory.binaryEncoder(stream, null);

		try {

			avroComplexEventWriter.write(event, binaryEncoder);
			binaryEncoder.flush();
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
		}

		IOUtils.closeQuietly(stream);

		log.info("# Send Event to msg : \n{}", JsonUtils.toPrettyJson(event));
		topic = StringUtils.defaultIfBlank(topic, env.getProperty("eventEmitter.topic"));

		getProducer().send(new KeyedMessage<String, byte[]>(topic, stream.toByteArray()));
	}

	/**
	 * 복합 이벤트 발송 메쏘드
	 *
	 * @param event					복합 이벤트
	 */
	private void send(ComplexEvent event) {
		send(event, null);
	}

	/**
	 * 이벤트 아이디 생성 메쏘드
	 *
	 * @param unitSvcCd				단위 서비스 코드
	 * @param setupCd				사용자 설정 코드
	 * @return						이벤트 아이디
	 */
	private String getEventId(String unitSvcCd, String setupCd) {

		String eventId = env.getDstrCd() + env.getSvcThemeCd() + unitSvcCd + "E" + setupCd;

		return eventId;
	}

	/**
	 * Complex 이벤트 Push 발송 메쏘드
	 *
	 * @param unitSvcCd				단위 서비스 코드
	 * @param setupCd				사용자 설정 코드
	 * @param pnsRegId				PNS Registration ID
	 * @param svcTgtSeq				서비스 대상 일련번호
	 */
	public void sendComplexEventPush(String unitSvcCd, String setupCd, String pnsRegId, long svcTgtSeq, Map<String, Object> items) {
		StringBuffer sb = new StringBuffer();
		Iterator<String> iter = items.keySet().iterator();
		while(iter.hasNext()) {
			String key = iter.next();
			sb.append(" " + key + "[" + items.get(key) + "]");
		}
		
		log.debug("## sendComplexEventPush : unitSvcCd[{}] setupCd[{}] svcTgtSeq[{}] pnsRegId[{}]" + sb.toString(), unitSvcCd, setupCd, svcTgtSeq, pnsRegId);

		items.put("pnsRegId", pnsRegId);
		String eventId = getEventId(unitSvcCd, setupCd);
		send(new ComplexEvent(svcTgtSeq, null, eventId, items));
	}
}
