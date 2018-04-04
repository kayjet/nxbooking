package com.booking.common.utils;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * ThreadUtil
 *
 * @author kai.liu
 * @date 2018/01/26
 */
public class ThreadUtil {
    private static ExecutorService es = null;

    public static ExecutorService getExecutorInstance() {
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("workerthread-%d")
                .daemon(true)
                .priority(Thread.MAX_PRIORITY)
                .build();
        if (es == null) {
            es = new ScheduledThreadPoolExecutor(1, factory);
        }
        return es;
    }

}
