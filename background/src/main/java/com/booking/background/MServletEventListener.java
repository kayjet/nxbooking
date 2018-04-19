package com.booking.background;

import com.booking.background.websocket.WebsocketController;
import com.booking.common.dto.OrderDetailDto;
import com.booking.common.entity.OrderDetailEntity;
import com.booking.common.entity.OrderEntity;
import com.booking.common.entity.ShopEntity;
import com.booking.common.mapper.OrderMapper;
import com.booking.common.mapper.OrderShopRelMapper;
import com.booking.common.service.IShopService;
import com.opdar.platform.core.base.Context;
import com.opdar.platform.core.base.ServletEventListener;
import com.opdar.plugins.mybatis.core.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.ServletContextEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * MServletEventListener
 *
 * @author kai.liu
 * @date 2018/02/22
 */
public class MServletEventListener extends ServletEventListener {
    Logger logger = LoggerFactory.getLogger(MServletEventListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Context.putResourceMapping("/dist", "/dist");
        super.contextInitialized(servletContextEvent);
        ApplicationContext applicationContext = (ApplicationContext) servletContextEvent.getServletContext().getAttribute("applicationContext");
        getAllShopNotHandledOrderList(applicationContext);
    }

    private void getAllShopNotHandledOrderList(ApplicationContext applicationContext) {
        IShopService shopService = applicationContext.getBean(IShopService.class);
        OrderShopRelMapper orderShopRelMapper = applicationContext.getBean(OrderShopRelMapper.class);
        List<ShopEntity> shops = shopService.listAll();
        for (ShopEntity shop : shops) {
            String shopId = shop.getId();
            List<OrderEntity> orderList = orderShopRelMapper.selectOrderListPushedButNotHandled(shopId);
            if (!CollectionUtils.isEmpty(orderList)) {
                setOrderDetail(orderList,applicationContext);
                ConcurrentLinkedQueue<OrderEntity> concurrentLinkedQueue = new ConcurrentLinkedQueue<OrderEntity>();
                concurrentLinkedQueue.addAll(orderList);
                WebsocketController.ORDER_QUEUE_CONCURRENT_MAP.put(shopId, concurrentLinkedQueue);
            }

        }
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        super.contextDestroyed(servletContextEvent);

    }

    private void setOrderDetail(OrderEntity order, ApplicationContext applicationContext) {
        OrderMapper orderMapper = applicationContext.getBean(OrderMapper.class);
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

    private void setOrderDetail(List<OrderEntity> orderList, ApplicationContext applicationContext) {
        for (OrderEntity orderEntity : orderList) {
            setOrderDetail(orderEntity, applicationContext);
        }

    }
}
