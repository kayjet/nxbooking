package com.booking.background.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.booking.common.dto.WsHeartBeatDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * WebsocketUtils
 *
 * @author kai.liu
 * @date 2018/04/19
 */
public class WebsocketUtils {
    private static final Logger logger = LoggerFactory.getLogger(WebsocketUtils.class);
    public static final Integer WS_SUCCESS_CODE = 1;
    public static final Integer WS_HANDLE_MESSAGE = 2;


    public static void sendHeartBeat(WebSocketSession webSocketSession, Integer code, Object o) throws IOException {
        logger.info("sending hearbeat");
        WsHeartBeatDto fromEndPoint = new WsHeartBeatDto();
        fromEndPoint.setCode(code);
        if (o != null) {
            logger.info("发送带有数据的心跳，" + JSON.toJSONString(o));
            fromEndPoint.setData(o);
        }
        webSocketSession.sendMessage(new TextMessage(JSONObject.toJSONString(fromEndPoint)));
    }
}
