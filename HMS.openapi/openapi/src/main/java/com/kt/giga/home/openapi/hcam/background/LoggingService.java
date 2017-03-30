package com.kt.giga.home.openapi.hcam.background;

import com.kt.giga.home.openapi.hcam.dao.UserDao;
import com.kt.giga.home.openapi.hcam.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;

/**
 * Created by cecil on 2015-07-20.
 */

@Component
public class LoggingService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource(name = "loginHistoryQueue")
    private BlockingQueue<User> loginHistoryQueue;

    @Autowired
    private UserDao userDao;

    private LoginHistoryTask loginHistoryTask;

    private Thread loginHistoryThread;

    @PostConstruct
    public void startProcessors() {

        log.info("Starting login success history worker...");


        loginHistoryTask = new LoginHistoryTask();
        loginHistoryTask.setLoginSuccessQueue(loginHistoryQueue);
        loginHistoryTask.setUserDao(userDao);

        loginHistoryThread = new Thread(loginHistoryTask);

        loginHistoryThread.start();

    }

    @PreDestroy
    public void stopProcessors() {

        loginHistoryThread.interrupt();
    }
}
