package com.booking.api.service;

import com.booking.common.quartz.MyQuartzExecutorDelegate;
import com.booking.common.base.Constants;
import com.booking.common.base.ICacheManager;
import com.booking.common.dto.MakeOrderDto;
import com.booking.common.dto.WechatPayResultDto;
import com.booking.common.entity.OrderEntity;
import com.booking.common.entity.ProductEntity;
import com.booking.common.entity.UserEntity;
import com.booking.common.exceptions.ErrCodeException;
import com.booking.common.service.IAdvertisementService;
import com.booking.common.service.IOrderService;
import com.booking.common.service.IOrderUserRelService;
import com.booking.common.service.IUserService;
import com.booking.common.service.impl.WeChatService;
import com.booking.common.utils.WxPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * MakeOrderCacadeService
 *
 * @author kai.liu
 * @date 2018/04/07
 */
@Service
public class MakeOrderCacadeService {
    Logger logger = LoggerFactory.getLogger(MakeOrderCacadeService.class);


    @Autowired
    IOrderService orderService;

    @Autowired
    IOrderUserRelService orderUserRelService;

    @Autowired
    IAdvertisementService advertisementService;

    @Autowired
    WeChatService weChatService;

    @Autowired
    IUserService userService;

    @Autowired
    MyQuartzExecutorDelegate quartzExecutorDelegate;

    @Autowired
    ICacheManager cacheManager;

    @Transactional(rollbackFor = Throwable.class)
    public MakeOrderDto makeOrder(String shopId, String userId, String concatPhone, String totalPrice, String orderType,
                                  String orderTime, List<List<ProductEntity>> products) {
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
            cacheManager.set(result.getOrderNo(), makeOrderDto, 60000 * 14);
            quartzExecutorDelegate.addCloseOrderJob(result);
            return makeOrderDto;
        } else {
            throw new ErrCodeException(1, "创建订单失败");
        }
    }
}
