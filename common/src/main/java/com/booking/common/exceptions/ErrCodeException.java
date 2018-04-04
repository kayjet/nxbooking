package com.booking.common.exceptions;

/**
 * Created by shiju on 2017/10/15.
 */
public class ErrCodeException extends RuntimeException {
    private int code;
    private String msg;
    private Object data;

    public ErrCodeException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

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
}
