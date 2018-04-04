package com.booking.common.base;

import com.booking.common.utils.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by shiju on 2018/1/31.
 */
public class DefaultCacheManager implements ICacheManager {
    private ConcurrentHashMap<String, ValueTask> tasks = new ConcurrentHashMap<String, ValueTask>();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Logger logger = LoggerFactory.getLogger("SessionManager");
    private AtomicBoolean isDestory = new AtomicBoolean(false);

    public DefaultCacheManager() {
    }

    @Override
    public void set(String key, Object value, long timeout) {
        DefaultCacheManager.ValueTask task = null;
        this.tasks.put(key, task = new DefaultCacheManager.ValueTask(key, value, timeout));
        this.executorService.execute(task);
    }

    @Override
    public String set(Object value, long timeout) {
        String key = UUID.randomUUID().toString();
        this.set(key, value, timeout);
        return key;
    }

    @Override
    public Object get(String key) {
        return this.tasks.containsKey(key) ? (this.tasks.get(key)).getValue() : null;
    }

    @Override
    public void clearTimeout(String key) {
        (this.tasks.get(key)).clearTimeout();
    }

    @Override
    public void destory() {
        this.executorService.shutdown();
    }

    @Override
    public void remove(String key) {
        this.tasks.remove(key);
    }

    private class ValueTask implements Runnable {
        private String key;
        private Object value;
        private long timeout = 60000L;
        private long lastRefreshTime = System.currentTimeMillis();
        private AtomicBoolean isRunning = new AtomicBoolean(true);

        public Object getValue() {
            return value;
        }

        ValueTask(String key, Object value, long timeout) {
            this.key = key;
            this.value = value;
            this.timeout = timeout;
        }

        void clearTimeout() {
            this.lastRefreshTime = System.currentTimeMillis();
        }

        @Override
        public void run() {
            while (this.isRunning.get() && !DefaultCacheManager.this.isDestory.get()) {
                long t = System.currentTimeMillis() - this.lastRefreshTime;
                if (t >= this.timeout) {
                    this.isRunning.set(false);
                    DefaultCacheManager.this.remove(this.key);
                } else {
                    long nextScheduleTime = this.timeout - t;

                    try {
                        Thread.sleep(nextScheduleTime);
                    } catch (InterruptedException var6) {
                        var6.printStackTrace();
                    }
                }
            }

        }
    }
}
