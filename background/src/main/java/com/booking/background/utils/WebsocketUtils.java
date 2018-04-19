package com.booking.background.utils;

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

    public static void sendHeartBeat(WebSocketSession webSocketSession, Integer code, Object o) throws IOException {
        WsHeartBeatDto fromEndPoint = new WsHeartBeatDto();
        fromEndPoint.setCode(code);
        if (o != null) {
            fromEndPoint.setData(o);
        }
        webSocketSession.sendMessage(new TextMessage(JSONObject.toJSONString(fromEndPoint)));
    }
}