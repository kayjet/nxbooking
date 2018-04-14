package com.booking.background.websocket;

import com.alibaba.fastjson.JSONObject;
import com.booking.background.service.ConsumerService;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.*;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * OrderController
 *
 * @author kai.liu
 * @date 2017/12/29
 */
public class WebsocketController implements WebSocketHandler, MessageListener, ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(WebsocketController.class);

    @Autowired
    IOrderService orderService;

    @Autowired
    IOrderShopRelService orderShopRelService;

    @Autowired
    ConsumerService consumerService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    IShopService shopService;

    @Autowired
    OrderShopRelMapper orderShopRelMapper;

    private static final Integer WS_SUCCESS_CODE = 1;


    private static final ConcurrentMap<String, WebSocketSession> WEB_SOCKET_SESSION_CONCURRENT_MAP = new ConcurrentHashMap<String, WebSocketSession>();

    private static final ConcurrentMap<String, ConcurrentLinkedQueue<OrderEntity>>
            LINKED_QUEUE_CONCURRENT_MAP = new ConcurrentHashMap<String, ConcurrentLinkedQueue<OrderEntity>>();


    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("connect to the websocket success......");
        WEB_SOCKET_SESSION_CONCURRENT_MAP.put(webSocketSession.getId(), webSocketSession);
        Object resultData = null;
        String query = webSocketSession.getUri().getQuery();
        if (!StringUtils.isEmpty(query)) {
            String[] params = query.split("=");
            String shopId = params[1];
            if (LINKED_QUEUE_CONCURRENT_MAP.containsKey(shopId)) {
                resultData = LINKED_QUEUE_CONCURRENT_MAP.get(shopId);
            }
        }
        sendHeartBeat(webSocketSession, resultData);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        logger.info("handleMessage......");
        String data = String.valueOf(webSocketMessage.getPayload());
        WsHeartBeatDto fromWeb = JSONObject.parseObject(data, WsHeartBeatDto.class);
        if (fromWeb.getCode().equals(WS_SUCCESS_CODE)) {
            sendHeartBeat(webSocketSession, null);
        }
    }

    private void sendHeartBeat(WebSocketSession webSocketSession, Object o) throws IOException {
        WsHeartBeatDto fromEndPoint = new WsHeartBeatDto();
        fromEndPoint.setCode(WS_SUCCESS_CODE);
        if (o != null) {
            fromEndPoint.setData(o);
        }
        webSocketSession.sendMessage(new TextMessage(JSONObject.toJSONString(fromEndPoint)));
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
            WEB_SOCKET_SESSION_CONCURRENT_MAP.remove(webSocketSession.getId());
        }
        throwable.printStackTrace();
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


    private void getAllShopNotHandledOrderList() {
        if (isInited) {
            return;
        } else {
            isInited = true;
        }
        List<ShopEntity> shops = shopService.listAll();
        for (ShopEntity shop : shops) {
            String shopId = shop.getId();
            List<OrderEntity> orderList = orderShopRelMapper.selectOrderListPushedButNotHandled(shopId);
            if (!CollectionUtils.isEmpty(orderList)) {
                setOrderDetail(orderList);
                ConcurrentLinkedQueue<OrderEntity> concurrentLinkedQueue = new ConcurrentLinkedQueue<OrderEntity>();
                concurrentLinkedQueue.addAll(orderList);
                LINKED_QUEUE_CONCURRENT_MAP.put(shopId, concurrentLinkedQueue);
            }

        }
    }

    private void setOrderDetail(OrderEntity order) {
        List<OrderDetailEntity> orderDetailEntities = orderMapper.selectOrderDetailList(order.getOrderNo());
        List<OrderDetailDto> reuslt = new ArrayList<OrderDetailDto>();

        for (OrderDetailEntity detailEntity : orderDetailEntities) {
            OrderDetailDto dto = new OrderDetailDto(detailEntity);
            if (reuslt.contains(dto)) {
                if (!StringUtils.isEmpty(detailEntity.getSpecName())) {
                    reuslt.get(reuslt.indexOf(dto)).getProductSpecList().add(detailEntity.getSpecName());
                }
            } else {
                reuslt.add(dto);
            }
        }
        order.setOrderDetailList(reuslt);
    }

    private void setOrderDetail(List<OrderEntity> orderList) {
        for (OrderEntity orderEntity : orderList) {
            setOrderDetail(orderEntity);
        }

    }

    @Override
    public void onMessage(Message message) {
        javax.jms.TextMessage tm = (javax.jms.TextMessage) message;
        for (WebSocketSession webSocketSession : WEB_SOCKET_SESSION_CONCURRENT_MAP.values()) {
            String txtMsg = null;
            try {
                txtMsg = tm.getText();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            if (!StringUtils.isEmpty(txtMsg)) {
                System.out.println("QueueMessageListener监听到了文本消息：\t" + txtMsg);
                WechatPayCallbackEntity callbackEntity = JSONObject.parseObject(txtMsg, WechatPayCallbackEntity.class);

                OrderEntity query = new OrderEntity();
                query.setTransactionId(callbackEntity.getTransaction_id());
                query.setOrderNo(callbackEntity.getOut_trade_no());
                OrderEntity ret = orderMapper.selectOne(query);
                setOrderDetail(ret);

                if (ret.getIsPushed().equals(Constants.OrderPushStatus.NOT_PUSH)) {
                    try {
                        WsHeartBeatDto<OrderEntity> fromEndPoint = new WsHeartBeatDto<OrderEntity>();
                        fromEndPoint.setCode(WS_SUCCESS_CODE);
                        fromEndPoint.setData(ret);
                        TextMessage wsMsg = new TextMessage(JSONObject.toJSONString(fromEndPoint));
                        webSocketSession.sendMessage(wsMsg);
                        ret.setIsPushed(Constants.OrderPushStatus.PUSHED);
                        orderMapper.updatePushStatusWithLock(ret);

                        OrderShopRelEntity queryShop = new OrderShopRelEntity();
                        queryShop.setOrderId(ret.getId());
                        String shopId = orderShopRelMapper.selectOne(queryShop).getShopId();

                        if (LINKED_QUEUE_CONCURRENT_MAP.containsKey(shopId)) {
                            if (!LINKED_QUEUE_CONCURRENT_MAP.get(shopId).contains(ret)) {
                                LINKED_QUEUE_CONCURRENT_MAP.get(shopId).add(ret);
                            }
                        } else {
                            ConcurrentLinkedQueue<OrderEntity> concurrentLinkedQueue = new ConcurrentLinkedQueue<OrderEntity>();
                            concurrentLinkedQueue.add(ret);
                            LINKED_QUEUE_CONCURRENT_MAP.put(shopId, concurrentLinkedQueue);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static boolean isInited = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            getAllShopNotHandledOrderList();
        }
    }
}
