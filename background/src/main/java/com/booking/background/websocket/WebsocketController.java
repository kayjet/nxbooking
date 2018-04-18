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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.*;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

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

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final Integer WS_SUCCESS_CODE = 1;
    private static final Integer WS_HANDLE_MESSAGE = 2;


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
        sendHeartBeat(webSocketSession, WS_SUCCESS_CODE, resultData);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        logger.info("handleMessage......");
        String data = String.valueOf(webSocketMessage.getPayload());
        WsHeartBeatDto fromWeb = JSONObject.parseObject(data, WsHeartBeatDto.class);
        if (fromWeb.getCode().equals(WS_SUCCESS_CODE)) {
            sendHeartBeat(webSocketSession, WS_SUCCESS_CODE, null);
        } else if (fromWeb.getCode().equals(WS_HANDLE_MESSAGE)) {
            JSONObject jsonObject = (JSONObject) fromWeb.getData();
            OrderEntity waitingHandleOrder = JSONObject.parseObject(jsonObject.toJSONString(), OrderEntity.class);
            waitingHandleOrder.setIsHandler(Constants.OrderPushHandlerStatus.HANDLERED);
            jdbcTemplate.update(" update `booking`.`t_order`\n" +
                    "            set is_handler=2, lock_version= lock_version + 1\n" +
                    "            where id='" + waitingHandleOrder.getId() + "'  and lock_version=" + waitingHandleOrder.getLockVersion());
            ConcurrentLinkedQueue<OrderEntity> orderEntities = LINKED_QUEUE_CONCURRENT_MAP.get(waitingHandleOrder.getShopId());
            Iterator<OrderEntity> iterator = orderEntities.iterator();
            while (iterator.hasNext()) {
                OrderEntity orderEntity = iterator.next();
                if (orderEntity.getId().equals(waitingHandleOrder.getId())) {
                    iterator.remove();
                }
            }
            sendHeartBeat(webSocketSession, WS_HANDLE_MESSAGE, waitingHandleOrder.getId());
        }
    }

    private void sendHeartBeat(WebSocketSession webSocketSession, Integer code, Object o) throws IOException {
        WsHeartBeatDto fromEndPoint = new WsHeartBeatDto();
        fromEndPoint.setCode(code);
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
                OrderShopRelEntity orderShopRel = new OrderShopRelEntity();
                orderShopRel.setOrderId(ret.getId());
                orderShopRel = orderShopRelMapper.selectOne(orderShopRel);
                String shopId = orderShopRel.getShopId();
                if (!StringUtils.isEmpty(shopId)) {
                    WebSocketSession webSocketSession = WEB_SOCKET_SESSION_CONCURRENT_MAP.get(shopId);
                    if (webSocketSession != null) {
                        try {
                            sendHeartBeat(webSocketSession, WS_SUCCESS_CODE, ret);
                            ret.setIsPushed(Constants.OrderPushStatus.PUSHED);
                        } catch (IOException e) {
                            e.printStackTrace();
                            if (!LINKED_QUEUE_CONCURRENT_MAP.get(shopId).contains(ret)) {
                                LINKED_QUEUE_CONCURRENT_MAP.get(shopId).add(ret);
                            } else {
                                ConcurrentLinkedQueue<OrderEntity> concurrentLinkedQueue = new ConcurrentLinkedQueue<OrderEntity>();
                                concurrentLinkedQueue.add(ret);
                                LINKED_QUEUE_CONCURRENT_MAP.put(shopId, concurrentLinkedQueue);
                            }
                        }
                        orderMapper.updatePushStatusWithLock(ret);
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
