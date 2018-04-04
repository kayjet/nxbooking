package com.booking.common.base;

/**
 * Created by shiju on 2018/1/31.
 */
public interface ICacheManager {
    void set(String key, Object value, long timeout);

    String set(Object value, long timeout);

    Object get(String key);

    void clearTimeout(String key);

    void destory();

    void remove(String key);
}
