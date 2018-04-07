package com.booking.api.controllers;

import com.alibaba.fastjson.JSONObject;
import com.booking.api.service.MakeOrderCacadeService;
import com.booking.api.service.ProducerService;
import com.booking.common.base.Constants;
import com.booking.common.base.ICacheManager;
import com.booking.common.dto.*;
import com.booking.common.entity.*;
import com.booking.common.exceptions.ErrCodeException;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.*;
import com.booking.common.service.impl.WeChatService;
import com.booking.common.utils.NetTool;
import com.booking.common.utils.WxPayUtil;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.opdar.platform.annotations.Editor;
import com.opdar.platform.annotations.ErrorHandler;
import com.opdar.platform.annotations.JSON;
import com.opdar.platform.annotations.Request;
import com.opdar.platform.core.base.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
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

    @Autowired
    MakeOrderCacadeService makeOrderCacadeService;

    @Autowired
    ProducerService producerService;

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


    @Request(value = "/sp3/order/payCallback", format = Request.Format.XML)
    public String payCallback() {
        try {
            String xml = new String(NetTool.read(Context.getRequest().getInputStream()), "UTF-8");
            System.out.println("--------------------- wx callback -----------------");
            System.out.println(xml);
            System.out.println("-------------------- wx callback -----------------");
            XmlMapper mapper = new XmlMapper();
            WechatPayCallbackEntity wechatPayCallbackEntity = mapper.readValue(xml, WechatPayCallbackEntity.class);
            if (wechatPayCallbackEntity.getResult_code().equals(Constants.WechatPayErrorCode.SUCCESS)) {
                String orderNo = wechatPayCallbackEntity.getOut_trade_no();
                String transaction_id = wechatPayCallbackEntity.getTransaction_id();
                orderService.updatePayStatus(orderNo, transaction_id);
                weChatService.savePayCallbackResult(wechatPayCallbackEntity);
                producerService.sendMessage(JSONObject.toJSONString(wechatPayCallbackEntity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WxPayUtil.setXML("SUCCESS", "OK");
    }
}
