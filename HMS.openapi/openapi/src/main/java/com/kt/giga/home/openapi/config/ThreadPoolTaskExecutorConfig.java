package com.kt.giga.home.openapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolTaskExecutorConfig {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Value("${threadpool.corePoolSize}")
	private int corePoolSize;

	@Value("${threadpool.maxPoolSize}")
	private int maxPoolSize;

	@Value("${threadpool.queueCapacity}")
	private int queueCapacity;

	@Bean
	public ThreadPoolTaskExecutor getTaskExecutor() {
		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setCorePoolSize(corePoolSize);
		pool.setMaxPoolSize(maxPoolSize);
		pool.setQueueCapacity(queueCapacity);
		pool.setWaitForTasksToCompleteOnShutdown(true);
		return pool;
	}
}
