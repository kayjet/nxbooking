package com.booking.common.exceptions;
import com.alibaba.fastjson.JSON;
import com.booking.common.resp.Result;
import com.opdar.platform.core.base.Context;
import com.opdar.platform.core.base.ErrorHandler;

import java.io.IOException;

/**
 * Created by shiju on 2017/10/15.
 */
public class ErrCodeHandler implements ErrorHandler {

    @Override
    public void handle(Throwable e) {
        byte[] msg = produceErrJSON(0xFFFF,"unknown error.",null);
        if(e instanceof ErrCodeException){
            msg = produceErrJSON(((ErrCodeException) e).getCode(),((ErrCodeException) e).getMsg(),((ErrCodeException) e).getData());
        }
        try {
            Context.getResponse().getOutputStream().write(msg);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private byte[] produceErrJSON(int code, String msg, Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return JSON.toJSONBytes(result);

    }
}
