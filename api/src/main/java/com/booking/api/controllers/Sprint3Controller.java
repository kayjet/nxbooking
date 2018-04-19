package com.booking.api.controllers;

import com.booking.api.service.MakeOrderCacadeService;
import com.booking.common.base.ICacheManager;
import com.booking.common.dto.*;
import com.booking.common.entity.*;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.mapper.OrderShopRelMapper;
import com.booking.common.mapper.ShopMapper;
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
import org.springframework.util.CollectionUtils;

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

    @Autowired
    OrderShopRelMapper orderShopRelMapper;

    @Autowired
    IAdvertisementService advertisementService;

    @Autowired
    WeChatService weChatService;

    @Autowired
    IUserService userService;

    @Autowired
    MakeOrderCacadeService makeOrderCacadeService;


//    @Autowired
//    ProducerService producerService;

    @Autowired
    ShopMapper shopMapper;

    @Request(value = "/sp3/order/makeOrder")
    @Editor(ResultEditor.class)
    public MakeOrderDto makeOrder(String shopId, String userId, String concatPhone, String totalPrice, String orderType,
                                  String orderTime, @JSON List<List<ProductEntity>> products) {
        return makeOrderCacadeService.makeOrder(shopId, userId, concatPhone, totalPrice, orderType,
                orderTime, products);
    }

    @Request(value = "/sp3/order/getOrder")
    @Editor(ResultEditor.class)
    public List<OrderUserRelEntity> getOrder(String userId) {
        OrderUserRelEntity query = new OrderUserRelEntity();
        query.setUserId(userId);
        List<OrderUserRelEntity> orderUserRelList = orderUserRelService.listOrderUserRel(query);
        if (!CollectionUtils.isEmpty(orderUserRelList)) {
            for (OrderUserRelEntity entity : orderUserRelList) {
                List<ShopEntity> shops = shopMapper.selectShopByOrderId(entity.getOrderId());
                entity.setShopList(shops);
                if (!CollectionUtils.isEmpty(entity.getOrderList())) {
                    for (OrderEntity orderEntity : entity.getOrderList()) {
                        ProductListDto productListDto = orderService.getOrderProductList(orderEntity.getId());
                        orderEntity.setProductListDto(productListDto);
                    }
                }
            }
        }
        return orderUserRelList;
    }

    @Request(value = "/sp3/order/reMakeOrder")
    @Editor(ResultEditor.class)
    public MakeOrderDto reMakeOrder(String orderNo) {
        return (MakeOrderDto) cacheManager.get(orderNo);
    }


    @Request(value = "/sp3/advertisement/getAdvertisementList")
    @Editor(ResultEditor.class)
    public List<AdvertisementEntity> getAdvertisementList() {
        return advertisementService.listAll();
    }





}
