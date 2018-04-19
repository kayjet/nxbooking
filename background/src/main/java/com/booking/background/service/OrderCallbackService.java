package com.booking.background.service;

import com.booking.background.utils.WebsocketUtils;
import com.booking.background.websocket.WebsocketController;
import com.booking.common.base.Constants;
import com.booking.common.dto.OrderDetailDto;
import com.booking.common.entity.OrderDetailEntity;
import com.booking.common.entity.OrderEntity;
import com.booking.common.entity.OrderShopRelEntity;
import com.booking.common.entity.WechatPayCallbackEntity;
import com.booking.common.mapper.OrderMapper;
import com.booking.common.mapper.OrderShopRelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * OrderCallbackService
 *
 * @author kai.liu
 * @date 2018/04/19
 */
@Service
public class OrderCallbackService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderShopRelMapper orderShopRelMapper;

    public boolean handle(WechatPayCallbackEntity callbackEntity) {
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
                WebSocketSession webSocketSession = WebsocketController.getSession(shopId);
                if (webSocketSession != null) {
                    try {
                        WebsocketUtils.sendHeartBeat(webSocketSession, WebsocketController.WS_SUCCESS_CODE, ret);
                        ret.setIsPushed(Constants.OrderPushStatus.PUSHED);
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (!WebsocketController.ORDER_QUEUE_CONCURRENT_MAP.get(shopId).contains(ret)) {
                            WebsocketController.ORDER_QUEUE_CONCURRENT_MAP.get(shopId).add(ret);
                        } else {
                            ConcurrentLinkedQueue<OrderEntity> concurrentLinkedQueue = new ConcurrentLinkedQueue<OrderEntity>();
                            concurrentLinkedQueue.add(ret);
                            WebsocketController.ORDER_QUEUE_CONCURRENT_MAP.put(shopId, concurrentLinkedQueue);
                        }
                    }
                    orderMapper.updatePushStatusWithLock(ret);
                    return true;
                } else {
                    if (!WebsocketController.ORDER_QUEUE_CONCURRENT_MAP.containsKey(shopId)) {
                        WebsocketController.ORDER_QUEUE_CONCURRENT_MAP.put(shopId, new ConcurrentLinkedQueue<OrderEntity>());
                    }
                    WebsocketController.ORDER_QUEUE_CONCURRENT_MAP.get(shopId).add(ret);
                    return true;
                }
            }
        }
        return false;
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
}
