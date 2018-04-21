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
    private ConcurrentHashMap<String, Value> tasks = new ConcurrentHashMap<String, Value>();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Logger logger = LoggerFactory.getLogger("SessionManager");
    private AtomicBoolean isDestory = new AtomicBoolean(false);

    public DefaultCacheManager() {
    }

    @Override
    public void set(String key, Object value, long timeout) {
        DefaultCacheManager.Value task = null;
        if(timeout == -1){
            task = new Value2(value);
        }else{
            task = new DefaultCacheManager.ValueTask(key, value, timeout);
            this.executorService.execute((Runnable) task);
        }

        this.tasks.put(key, task);
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
        Value task = (this.tasks.get(key));
        if(task instanceof ValueTask){
            ((ValueTask)task).clearTimeout();
        }
    }

    @Override
    public void destory() {
        this.executorService.shutdown();
    }

    @Override
    public void remove(String key) {
        try {
            this.tasks.remove(key);
        } catch (Exception e) {

        }
    }

    public interface Value{
        public Object getValue();
    }

     static class Value2 implements Value {

         private Object value;

         public Value2(Object value) {
             this.value = value;
         }

         @Override
         public Object getValue() {
             return this.value;
         }

     }

    class ValueTask implements Value,Runnable {
        private String key;
        private Object value;
        private long timeout = 60000L;
        private long lastRefreshTime = System.currentTimeMillis();
        private AtomicBoolean isRunning = new AtomicBoolean(true);

        @Override
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
