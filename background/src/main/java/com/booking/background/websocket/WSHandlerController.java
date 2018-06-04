package com.booking.background.websocket;

import com.alibaba.fastjson.JSONObject;
import com.booking.background.service.NotHandledOrderService;
import com.booking.background.utils.WebsocketUtils;
import com.booking.common.base.Constants;
import com.booking.common.dto.OrderDetailDto;
import com.booking.common.dto.WsHeartBeatDto;
import com.booking.common.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.*;

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
public class WSHandlerController implements WebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(WSHandlerController.class);
    public static final Integer WS_SUCCESS_CODE = 1;
    public static final Integer WS_HANDLE_MESSAGE = 2;
    private static final ConcurrentMap<String, WebSocketSession> WEB_SOCKET_SESSION_CONCURRENT_MAP = new ConcurrentHashMap<String, WebSocketSession>();
    private static final ConcurrentMap<String, String> WEB_SOCKET_SESSION_SHOP_ID_REL_MAP = new ConcurrentHashMap<String, String>();
    public static final ConcurrentMap<String, ConcurrentLinkedQueue<OrderEntity>>
            ORDER_QUEUE_CONCURRENT_MAP = new ConcurrentHashMap<String, ConcurrentLinkedQueue<OrderEntity>>();


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void getAllShopNotHandledOrderList() {
        String selectAllShopSql = "SELECT * FROM booking.t_shop";
        List<ShopEntity> shops = jdbcTemplate.query(selectAllShopSql, new Object[]{}, new BeanPropertyRowMapper(ShopEntity.class));
        for (ShopEntity shop : shops) {
            String shopId = shop.getId();
            String selectOrderListPushedButNotHandled =
                    String.format(" SELECT rel.shop_id,ord.* FROM `t_order_shop_rel` rel LEFT JOIN t_order ord ON ord.id = rel.order_id\n" +
                            "            WHERE ord.is_handler = 1 AND ord.is_pushed = 2 AND rel.shop_id = '%s'", shopId);
            List<OrderEntity> orderList = jdbcTemplate.query(selectOrderListPushedButNotHandled, new Object[]{}, new BeanPropertyRowMapper(OrderEntity.class));
            if (!CollectionUtils.isEmpty(orderList)) {
                setOrderDetail(orderList);
                if (ORDER_QUEUE_CONCURRENT_MAP.containsKey(shopId)) {
                    ConcurrentLinkedQueue<OrderEntity> queue = WSHandlerController.ORDER_QUEUE_CONCURRENT_MAP.get(shopId);
                    for (OrderEntity orderEntity : orderList) {
                        if (!queue.contains(orderEntity)) {
                            queue.add(orderEntity);
                        }
                    }
                } else {
                    ConcurrentLinkedQueue<OrderEntity> concurrentLinkedQueue = new ConcurrentLinkedQueue<OrderEntity>();
                    concurrentLinkedQueue.addAll(orderList);
                    WSHandlerController.ORDER_QUEUE_CONCURRENT_MAP.put(shopId, concurrentLinkedQueue);
                }
            }

        }
    }

    private void setOrderDetail(OrderEntity order) {
        String selectOrderDetailList =
                String.format("  SELECT\n" +
                        "        T.*,spec.name as spec_name\n" +
                        "        FROM\n" +
                        "        (\n" +
                        "        SELECT DISTINCT\n" +
                        "        ord.id AS order_id,\n" +
                        "        ord.order_no,\n" +
                        "        order_product_rel.id AS order_product_rel_id,\n" +
                        "        product.title AS product_name,\n" +
                        "        product.id as product_id\n" +
                        "        FROM\n" +
                        "        t_order ord\n" +
                        "        LEFT JOIN t_order_product_rel order_product_rel ON order_product_rel.order_id = ord.id\n" +
                        "        LEFT JOIN t_product product ON order_product_rel.product_id = product.id\n" +
                        "        ORDER BY ord.order_no\n" +
                        "        ) T\n" +
                        "        LEFT JOIN t_order_product_spec_rel spec_rel ON spec_rel.order_product_rel_id = T.order_product_rel_id\n" +
                        "        LEFT JOIN t_product_spec spec ON spec.id = spec_rel.spec_id\n" +
                        "        WHERE  T.order_no = '%s'", order.getOrderNo());
        List<OrderDetailEntity> orderDetailEntities = jdbcTemplate.query(selectOrderDetailList, new Object[]{}, new BeanPropertyRowMapper(OrderDetailEntity.class));
        //orderMapper.selectOrderDetailList(order.getOrderNo());
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
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("connect to the websocket success......");
        logger.info("webSocketSession.getId() = " + webSocketSession.getId());
        WEB_SOCKET_SESSION_CONCURRENT_MAP.put(webSocketSession.getId(), webSocketSession);
        Object resultData = null;
        String query = webSocketSession.getUri().getQuery();
        if (!StringUtils.isEmpty(query)) {
            getAllShopNotHandledOrderList();
            String[] params = query.split("=");
            String shopId = params[1];
            logger.info("query shopId = " + shopId);
            if (ORDER_QUEUE_CONCURRENT_MAP.containsKey(shopId)) {
                resultData = ORDER_QUEUE_CONCURRENT_MAP.get(shopId);
            }
            logger.info("ORDER_QUEUE_CONCURRENT_MAP = " + ORDER_QUEUE_CONCURRENT_MAP);
            if (!WEB_SOCKET_SESSION_SHOP_ID_REL_MAP.containsKey(shopId)) {
                WEB_SOCKET_SESSION_SHOP_ID_REL_MAP.put(shopId, webSocketSession.getId());
            }
            logger.info("WEB_SOCKET_SESSION_SHOP_ID_REL_MAP = " + WEB_SOCKET_SESSION_SHOP_ID_REL_MAP);
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

    public synchronized static WebSocketSession getSession(String shopId) {
        if (WEB_SOCKET_SESSION_SHOP_ID_REL_MAP.containsKey(shopId)) {
            String wsSessionId = WEB_SOCKET_SESSION_SHOP_ID_REL_MAP.get(shopId);
            return WEB_SOCKET_SESSION_CONCURRENT_MAP.get(wsSessionId);
        }
        return null;
    }
}
