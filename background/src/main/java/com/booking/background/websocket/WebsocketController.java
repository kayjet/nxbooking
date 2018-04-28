package com.booking.background.websocket;

import com.alibaba.fastjson.JSONObject;
import com.booking.background.utils.WebsocketUtils;
import com.booking.common.base.Constants;
import com.booking.common.dto.OrderDetailDto;
import com.booking.common.dto.WsHeartBeatDto;
import com.booking.common.entity.*;
import com.booking.common.mapper.OrderMapper;
import com.booking.common.mapper.OrderShopRelMapper;
import com.booking.common.service.IOrderService;
import com.booking.common.service.IOrderShopRelService;
import com.booking.common.service.IShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * OrderController
 *
 * @author kai.liu
 * @date 2017/12/29
 */
public class WebsocketController implements WebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebsocketController.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static final Integer WS_SUCCESS_CODE = 1;
    public static final Integer WS_HANDLE_MESSAGE = 2;


    private static final ConcurrentMap<String, WebSocketSession> WEB_SOCKET_SESSION_CONCURRENT_MAP = new ConcurrentHashMap<String, WebSocketSession>();

    private static final ConcurrentMap<String, String> WEB_SOCKET_SESSION_SHOP_ID_REL_MAP = new ConcurrentHashMap<String, String>();


    public static final ConcurrentMap<String, ConcurrentLinkedQueue<OrderEntity>>
            ORDER_QUEUE_CONCURRENT_MAP = new ConcurrentHashMap<String, ConcurrentLinkedQueue<OrderEntity>>();


    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("connect to the websocket success......");
        WEB_SOCKET_SESSION_CONCURRENT_MAP.put(webSocketSession.getId(), webSocketSession);
        Object resultData = null;
        String query = webSocketSession.getUri().getQuery();
        if (!StringUtils.isEmpty(query)) {
            String[] params = query.split("=");
            String shopId = params[1];
            if (ORDER_QUEUE_CONCURRENT_MAP.containsKey(shopId)) {
                resultData = ORDER_QUEUE_CONCURRENT_MAP.get(shopId);
            }
            if (!WEB_SOCKET_SESSION_SHOP_ID_REL_MAP.containsKey(shopId)) {
                WEB_SOCKET_SESSION_SHOP_ID_REL_MAP.put(shopId, webSocketSession.getId());
            }
        }
        WebsocketUtils.sendHeartBeat(webSocketSession, WS_SUCCESS_CODE, resultData);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        String data = String.valueOf(webSocketMessage.getPayload());
        WsHeartBeatDto fromWeb = JSONObject.parseObject(data, WsHeartBeatDto.class);
        if (fromWeb.getCode().equals(WS_SUCCESS_CODE)) {
            WebsocketUtils.sendHeartBeat(webSocketSession, WS_SUCCESS_CODE, null);
        } else if (fromWeb.getCode().equals(WS_HANDLE_MESSAGE)) {
            handleOrder(webSocketSession, fromWeb);
        }
    }


    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
            WEB_SOCKET_SESSION_CONCURRENT_MAP.remove(webSocketSession.getId());
            removeSessionRel(webSocketSession.getId());
        }
        throwable.printStackTrace();
        logger.info("websocket connection closed......");
    }

    private void removeSessionRel(String wsSessionId) {
        Iterator<String> shopIdIterator = WEB_SOCKET_SESSION_SHOP_ID_REL_MAP.keySet().iterator();
        while (shopIdIterator.hasNext()) {
            String shopId = shopIdIterator.next();
            if (WEB_SOCKET_SESSION_SHOP_ID_REL_MAP.get(shopId).equals(wsSessionId)) {
                shopIdIterator.remove();
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        WEB_SOCKET_SESSION_CONCURRENT_MAP.remove(webSocketSession.getId());
        removeSessionRel(webSocketSession.getId());
        logger.info("websocket connection closed......");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void handleOrder(WebSocketSession webSocketSession, WsHeartBeatDto fromWeb) throws IOException {
        JSONObject jsonObject = (JSONObject) fromWeb.getData();
        OrderEntity waitingHandleOrder = JSONObject.parseObject(jsonObject.toJSONString(), OrderEntity.class);
        waitingHandleOrder.setIsHandler(Constants.OrderPushHandlerStatus.HANDLERED);
        jdbcTemplate.update(" update `booking`.`t_order`\n" +
                "            set is_handler=2, lock_version= lock_version + 1\n" +
                "            where id='" + waitingHandleOrder.getId() + "'  and lock_version=" + waitingHandleOrder.getLockVersion());
        ConcurrentLinkedQueue<OrderEntity> orderEntities = ORDER_QUEUE_CONCURRENT_MAP.get(waitingHandleOrder.getShopId());
        Iterator<OrderEntity> iterator = orderEntities.iterator();
        while (iterator.hasNext()) {
            OrderEntity orderEntity = iterator.next();
            if (orderEntity.getId().equals(waitingHandleOrder.getId())) {
                iterator.remove();
            }
        }
        WebsocketUtils.sendHeartBeat(webSocketSession, WS_HANDLE_MESSAGE, waitingHandleOrder.getId());
    }

    public static WebSocketSession getSession(String shopId) {
        if (WEB_SOCKET_SESSION_SHOP_ID_REL_MAP.containsKey(shopId)) {
            String wsSessionId = WEB_SOCKET_SESSION_SHOP_ID_REL_MAP.get(shopId);
            return WEB_SOCKET_SESSION_CONCURRENT_MAP.get(wsSessionId);
        }
        return null;
    }
}
