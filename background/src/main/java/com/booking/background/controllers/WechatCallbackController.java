package com.booking.background.controllers;

import com.booking.background.service.OrderCallbackService;
import com.booking.common.base.Constants;
import com.booking.common.base.ICacheManager;
import com.booking.common.entity.WechatPayCallbackEntity;
import com.booking.common.service.IOrderService;
import com.booking.common.service.impl.WeChatService;
import com.booking.common.utils.NetTool;
import com.booking.common.utils.WxPayUtil;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.opdar.platform.annotations.Request;
import com.opdar.platform.core.base.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

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


    @Request(value = "/sp3/order/payCallback", format = Request.Format.XML)
    public String payCallback() {
        String xml = null;
        try {
            xml = new String(NetTool.read(Context.getRequest().getInputStream()), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (xml == null) {
            return WxPayUtil.setXML("FAIL", "xml转换失败");
        }
        System.out.println("---------------------XML from wx callback -----------------");
        //TODO:微信sign校验
        System.out.println(xml);
        System.out.println("--------------------XML from wx callback -----------------");
        XmlMapper mapper = new XmlMapper();
        WechatPayCallbackEntity wechatPayCallbackEntity = null;
        try {
            wechatPayCallbackEntity = mapper.readValue(xml, WechatPayCallbackEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (wechatPayCallbackEntity == null) {
            return WxPayUtil.setXML("FAIL", "XML读取失败");
        }
        if (wechatPayCallbackEntity.getResult_code().equals(Constants.WechatPayErrorCode.SUCCESS)) {
            String orderNo = wechatPayCallbackEntity.getOut_trade_no();
            String transaction_id = wechatPayCallbackEntity.getTransaction_id();
            orderService.updatePayStatus(orderNo, transaction_id);
            weChatService.savePayCallbackResult(wechatPayCallbackEntity);
            orderCallbackService.handle(wechatPayCallbackEntity);
            cacheManager.remove(orderNo);
        }

        return WxPayUtil.setXML("SUCCESS", "OK");
    }
}
