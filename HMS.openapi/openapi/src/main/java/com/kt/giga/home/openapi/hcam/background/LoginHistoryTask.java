package com.kt.giga.home.openapi.hcam.background;

import com.google.common.collect.Queues;
import com.kt.giga.home.openapi.hcam.dao.UserDao;
import com.kt.giga.home.openapi.hcam.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by cecil on 2015-07-20.
 */
public class LoginHistoryTask implements Runnable {

    private Logger log = LoggerFactory.getLogger(getClass());

    private List<User> loginSuccessHistories;

    private BlockingQueue<User> loginSuccessQueue;

    private UserDao userDao;

    public LoginHistoryTask() {

        loginSuccessHistories = new ArrayList<>();

    }

    @Required
    public void setLoginSuccessQueue(BlockingQueue<User> loginSuccessQueue) {

        this.loginSuccessQueue = loginSuccessQueue;
    }

    @Required
    public void setUserDao(UserDao userDao) {

        this.userDao = userDao;
    }

    public void insertHistories(int itemcount, long timeout) throws Exception {

        Queues.drain(loginSuccessQueue, loginSuccessHistories, itemcount, timeout, TimeUnit.MILLISECONDS);

        for (User user : loginSuccessHistories) {
            log.debug("Inserting a login history with user : {}", user.toString());
            try {
                userDao.insertLoginHistory(user);
            } catch (Exception e) {
                log.error("Failed to insert a login history with user : {}.", user.toString());
            }
        }

        loginSuccessHistories.clear();

    }

    @Override
    public void run() {

        while (true) {
            try {
                insertHistories(10, 100);
            } catch (InterruptedException e) {
                log.info("Inserting remaining login histories.");

                if (loginSuccessQueue.size() > 0) {
                    try {
                        insertHistories(loginSuccessQueue.size(), 0);
                    } catch (Exception ee) {
                        log.info("Failed to insert remaining login histories.");
                    }

                }

                log.info("Stopping login history worker...");

                return;

            } catch (Exception e) {
                log.info("Failed to insert login histories due to an exception of {}.", e.getMessage());
            }
        }
    }

}
