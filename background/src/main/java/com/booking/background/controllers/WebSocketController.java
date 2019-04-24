package com.booking.background.controllers;

import com.booking.background.interceptor.LoginSessionInterceptor;
import com.booking.background.interceptor.WSLoginSessionInterceptor;
import com.booking.common.entity.OrderEntity;
import com.booking.common.entity.ShopEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.interceptor.TimeQueryInterceptor;
import com.booking.common.resp.Page;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.IOrderService;
import com.booking.common.service.IShopService;
import com.opdar.platform.annotations.*;
import com.opdar.platform.core.base.Context;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * OrderController
 *
 * @author kai.liu
 * @date 2017/12/29
 */
@Controller
@ErrorHandler(ErrCodeHandler.class)
@Interceptor({TimeQueryInterceptor.class, WSLoginSessionInterceptor.class})
public class WebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    IOrderService orderService;

    @Autowired
    IShopService shopService;

    @Value("${proxy.context}")
    private String proxyContext;

    @Value("${ws.address}")
    private String wsAddress;

    @Request(value = "/websocket/view", format = Request.Format.VIEW)
    public String websocket() {
        logger.info("访问websocket页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        Context.putAttribute("shopList", shopService.listAll());
        Context.putAttribute("navList", new String[]{"即时订单"});
        Context.putAttribute("proxyContext", proxyContext);
        Context.putAttribute("wsAddress", wsAddress);
        return "websocket/view";
    }
}
