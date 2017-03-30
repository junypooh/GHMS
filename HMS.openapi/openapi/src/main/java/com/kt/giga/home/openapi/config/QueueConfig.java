package com.kt.giga.home.openapi.config;

import com.kt.giga.home.openapi.hcam.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by cecil on 2015-07-20.
 */

@Configuration
public class QueueConfig {

    @Bean(name = "loginHistoryQueue")
    public BlockingQueue<User> getLoginHistoryQueue() {
        return new ArrayBlockingQueue<>(100);
    }
}
