package com.booking.background.controllers;

import com.booking.background.service.OrderCallbackService;
import com.booking.common.base.Constants;
import com.booking.common.base.ICacheManager;
import com.booking.common.entity.WechatPayCallbackEntity;
import com.booking.common.quartz.MyQuartzExecutorDelegate;
import com.booking.common.service.IOrderService;
import com.booking.common.service.impl.WeChatService;
import com.booking.common.utils.WxPayUtil;
import com.opdar.platform.annotations.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

/**
 * WechatCallbackController
 *
 * @author kai.liu
 * @date 2018/04/19
 */
@Controller
public class WechatCallbackController {
    Logger logger = LoggerFactory.getLogger(WechatCallbackController.class);

    @Autowired
    ICacheManager cacheManager;

    @Autowired
    IOrderService orderService;

    @Autowired
    WeChatService weChatService;

    @Autowired
    OrderCallbackService orderCallbackService;

    @Autowired
    MyQuartzExecutorDelegate quartzExecutorDelegate;


    @Request(value = "/sp3/order/payCallback", format = Request.Format.XML)
    public String payCallback() {
        logger.info("通知支付成功回调 start ！");
        WechatPayCallbackEntity wechatPayCallbackEntity = orderCallbackService.readCallbackXmlFromWechat();
        if (wechatPayCallbackEntity == null) {
            return WxPayUtil.setXML(Constants.WechatPayErrorCode.FAIL, "XML读取失败");
        }
        if (!StringUtils.isEmpty(wechatPayCallbackEntity.getResult_code()) &&
                wechatPayCallbackEntity.getResult_code().equals(Constants.WechatPayErrorCode.SUCCESS)) {
            String orderNo = wechatPayCallbackEntity.getOut_trade_no();
            String transactionId = wechatPayCallbackEntity.getTransaction_id();
            Double totalPrice = Double.valueOf(wechatPayCallbackEntity.getTotal_fee()) / 100D;
            if (weChatService.validateSign(wechatPayCallbackEntity) && orderService.validateOrderPrice(orderNo, totalPrice)) {
                orderService.updatePayStatus(orderNo, transactionId);
                weChatService.savePayCallbackResult(wechatPayCallbackEntity);
                boolean result = orderCallbackService.handleAndSendMessage(wechatPayCallbackEntity.getTransaction_id(),wechatPayCallbackEntity.getOut_trade_no());
                if (result) {
                    cacheManager.remove(orderNo);
                    try {
                        quartzExecutorDelegate.removeCloseOrderJob(orderNo);
                    } catch (Exception e) {
                        logger.error("删除定时任务出错");
                        logger.error(e.getMessage());
                        logger.error("-------------");
                    }
                    logger.info("通知支付成功回调 end ！");
                    return WxPayUtil.setXML(Constants.WechatPayErrorCode.SUCCESS, "OK");
                }
            } else {
                logger.info("验证sign失败，或订单价格不符");
                logger.info("通知支付成功回调 end ！");
                return WxPayUtil.setXML(Constants.WechatPayErrorCode.FAIL, "验证sign失败，或订单价格不符");
            }
        }
        logger.info("通知支付成功回调 end ！");
        return WxPayUtil.setXML(Constants.WechatPayErrorCode.FAIL, "未知错误");
    }
}
