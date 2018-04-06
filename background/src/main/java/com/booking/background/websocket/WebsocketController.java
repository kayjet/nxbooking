package com.booking.background.websocket;

import com.booking.common.entity.OrderEntity;
import com.booking.common.entity.ShopEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.Page;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.IOrderService;
import com.booking.common.service.IOrderShopRelService;
import com.booking.common.service.IShopService;
import com.opdar.platform.annotations.Editor;
import com.opdar.platform.annotations.ErrorHandler;
import com.opdar.platform.annotations.JSON;
import com.opdar.platform.annotations.Request;
import com.opdar.platform.core.base.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

/**
 * OrderController
 *
 * @author kai.liu
 * @date 2017/12/29
 */
public class WebsocketController implements WebSocketHandler, Runnable, ApplicationListener {
    private static final Logger logger = LoggerFactory.getLogger(WebsocketController.class);

    @Autowired
    IOrderService orderService;

    @Autowired
    IOrderShopRelService orderShopRelService;

    private static final Executor executor = Executors.newCachedThreadPool();

    private static final ConcurrentMap<String, WebSocketSession> WEB_SOCKET_SESSION_CONCURRENT_MAP = new ConcurrentHashMap<String, WebSocketSession>();

    private static final ConcurrentMap<String, ConcurrentLinkedQueue>
            LINKED_QUEUE_CONCURRENT_MAP = new ConcurrentHashMap<String, ConcurrentLinkedQueue>();


    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("connect to the websocket success......");
        WEB_SOCKET_SESSION_CONCURRENT_MAP.put(webSocketSession.getId(), webSocketSession);
        webSocketSession.sendMessage(new TextMessage("ws session id = " + webSocketSession.getId()));
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        TextMessage returnMessage = new TextMessage(webSocketMessage.getPayload()
                + " received at server");
        webSocketSession.sendMessage(new TextMessage("收到消息"));
        webSocketSession.sendMessage(returnMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        logger.info("websocket connection closed......");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        WEB_SOCKET_SESSION_CONCURRENT_MAP.remove(webSocketSession.getId());
        System.out.println("websocket connection closed......");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    @Override
    public void run() {
        while (true){
            try {
                for(WebSocketSession webSocketSession : WEB_SOCKET_SESSION_CONCURRENT_MAP.values()){
                    webSocketSession.sendMessage(new TextMessage("定时任务"));
                }
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        executor.execute(this);
    }
}
