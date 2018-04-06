package com.booking.api.controllers;

import com.booking.common.base.Constants;
import com.booking.common.base.ICacheManager;
import com.booking.common.dto.MakeOrderDto;
import com.booking.common.dto.ProductDto;
import com.booking.common.dto.ProductListDto;
import com.booking.common.dto.WechatPayResultDto;
import com.booking.common.entity.*;
import com.booking.common.exceptions.ErrCodeException;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.*;
import com.booking.common.service.impl.WeChatService;
import com.booking.common.utils.WxPayUtil;
import com.opdar.platform.annotations.Editor;
import com.opdar.platform.annotations.ErrorHandler;
import com.opdar.platform.annotations.JSON;
import com.opdar.platform.annotations.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

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
    IAdvertisementService advertisementService;

    @Autowired
    WeChatService weChatService;

    @Autowired
    IUserService userService;

    @Request(value = "/sp3/order/makeOrder")
    @Editor(ResultEditor.class)
//    @Transactional(rollbackFor = Throwable.class)
    public MakeOrderDto makeOrder(String shopId, String userId, String concatPhone, String totalPrice, String orderType,
                                  String orderTime, @JSON List<List<ProductEntity>> products) {
        MakeOrderDto makeOrderDto = new MakeOrderDto();
        OrderEntity result = orderService.makeOrder(shopId, userId, concatPhone, totalPrice, orderType, orderTime, products);
        UserEntity user = userService.listUser(new UserEntity(userId)).get(0);
        String ip = "123.206.217.60";
        String openId = user.getOpenid();
        WechatPayResultDto wechatPayResultDto = weChatService.prepareSmallAppOrder(result, ip, openId);
        if (wechatPayResultDto.getResult_code().equals(Constants.WechatPayErrorCode.SUCCESS)
                && wechatPayResultDto.getResult_code().equals(Constants.WechatPayErrorCode.SUCCESS)) {
            String nonceStr = WxPayUtil.createNoncestr();
            long currentTimeMillis = System.currentTimeMillis();
            String paySign = weChatService.createSmallAppPaySign(wechatPayResultDto.getPrepay_id(), currentTimeMillis, nonceStr);
            makeOrderDto.setOrderEntity(result);
            makeOrderDto.setPaySign(paySign);
            makeOrderDto.setTimeStamp(currentTimeMillis + "");
            makeOrderDto.setNonceStr(nonceStr);
            makeOrderDto.setPrepay_id("prepay_id=" + wechatPayResultDto.getPrepay_id());
            return makeOrderDto;
        } else {
            throw new ErrCodeException(1, "创建订单失败");
        }
    }

    @Request(value = "/sp3/order/getOrder")
    @Editor(ResultEditor.class)
    public List<OrderUserRelEntity> getOrder(String userId) {
        OrderUserRelEntity orderUserRelEntity = new OrderUserRelEntity();
        orderUserRelEntity.setUserId(userId);
        return orderUserRelService.listOrderUserRel(orderUserRelEntity);
    }

    @Request(value = "/sp3/order/getOrderProductList")
    @Editor(ResultEditor.class)
    public ProductListDto getOrderProductList(String orderId) {
        return orderService.getOrderProductList(orderId);
    }


    @Request(value = "/sp3/advertisement/getAdvertisementList")
    @Editor(ResultEditor.class)
    public List<AdvertisementEntity> getAdvertisementList() {
        return advertisementService.listAll();
    }
}
