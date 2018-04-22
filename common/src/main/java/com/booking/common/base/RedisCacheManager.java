package com.booking.common.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * RedisCacheManager
 *
 * @author kai.liu
 * @date 2018/04/22
 */
public class RedisCacheManager implements ICacheManager {
    Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, Object value, long timeout) {
        try {
            if (timeout > 0) {
                redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            } else {
                set(value, timeout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String set(Object value, long timeout) {
        String key = UUID.randomUUID().toString();
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return key;
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void clearTimeout(String key) {

    }

    @Override
    public void destory() {

    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }
}
