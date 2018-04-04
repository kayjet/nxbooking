package com.booking.api.controllers;

import com.booking.common.base.ICacheManager;
import com.booking.common.entity.OrderEntity;
import com.booking.common.entity.OrderUserRelEntity;
import com.booking.common.entity.ProductEntity;
import com.booking.common.entity.ShopTagRelForWebEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.*;
import com.booking.common.service.impl.WeChatService;
import com.opdar.platform.annotations.Editor;
import com.opdar.platform.annotations.ErrorHandler;
import com.opdar.platform.annotations.JSON;
import com.opdar.platform.annotations.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Sprint1Controller
 *
 * @author kai.liu
 * @date 2018/03/27
 */
@Controller
@ErrorHandler(ErrCodeHandler.class)
public class Sprint3Controller {
    Logger logger = LoggerFactory.getLogger(Sprint3Controller.class);

    @Autowired
    IOrderService orderService;

    @Autowired
    ICacheManager cacheManager;

    @Autowired
    IOrderUserRelService orderUserRelService;

    @Request(value = "/sp3/order/makeOrder")
    @Editor(ResultEditor.class)
    public OrderEntity makeOrder(String shopId, String userId, String concatPhone, String totalPrice, String orderType, @JSON List<List<ProductEntity>> products) {
        return orderService.makeOrder(shopId, userId, concatPhone, totalPrice, orderType, products);
    }

    @Request(value = "/sp3/order/getOrder")
    @Editor(ResultEditor.class)
    public List<OrderUserRelEntity> getOrder(String userId) {
        OrderUserRelEntity orderUserRelEntity = new OrderUserRelEntity();
        orderUserRelEntity.setUserId(userId);
        return orderUserRelService.listOrderUserRel(orderUserRelEntity);
    }

}
