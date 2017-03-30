package com.kt.hiot.homemanager.kafka.console;


public class Main {

	public static void main(String[] args) {
		new KafkaSimulator();
		Thread th = new Thread(new KafkaSimulator());
		th.start();
	}

}
