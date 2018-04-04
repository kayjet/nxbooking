package com.booking.common.resp;

/**
 * Created by shiju on 2017/10/16.
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;
    private long systemTime = System.currentTimeMillis();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }
}
