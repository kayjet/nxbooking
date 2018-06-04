package com.booking.background.service;

import com.booking.background.websocket.WSHandlerController;
import com.booking.common.dto.OrderDetailDto;
import com.booking.common.entity.OrderDetailEntity;
import com.booking.common.entity.OrderEntity;
import com.booking.common.entity.ShopEntity;
import com.booking.common.mapper.OrderMapper;
import com.booking.common.mapper.OrderShopRelMapper;
import com.booking.common.service.IShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * NotHandledOrderService
 *
 * @author kai.liu
 * @date 2018/05/08
 */
@Service
public class NotHandledOrderService {
    Logger logger = LoggerFactory.getLogger(NotHandledOrderService.class);

    @Autowired
    private IShopService shopService;

    @Autowired
    private OrderShopRelMapper orderShopRelMapper;

    @Autowired
    private OrderMapper orderMapper;



    public void getAllShopNotHandledOrderList() {
        List<ShopEntity> shops = shopService.listAll();
        for (ShopEntity shop : shops) {
            String shopId = shop.getId();
            List<OrderEntity> orderList = orderShopRelMapper.selectOrderListPushedButNotHandled(shopId);
            if (!CollectionUtils.isEmpty(orderList)) {
                setOrderDetail(orderList);
                ConcurrentLinkedQueue<OrderEntity> concurrentLinkedQueue = new ConcurrentLinkedQueue<OrderEntity>();
                concurrentLinkedQueue.addAll(orderList);
                WSHandlerController.ORDER_QUEUE_CONCURRENT_MAP.put(shopId, concurrentLinkedQueue);
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
}
