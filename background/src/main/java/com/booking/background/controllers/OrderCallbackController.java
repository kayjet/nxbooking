package com.booking.background.controllers;

import com.booking.background.interceptor.LoginSessionInterceptor;
import com.booking.background.service.OrderCallbackService;
import com.booking.common.entity.OrderEntity;
import com.booking.common.entity.ShopEntity;
import com.booking.common.entity.WechatPayCallbackEntity;
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
public class OrderCallbackController {
    private static final Logger logger = LoggerFactory.getLogger(OrderCallbackController.class);

    @Autowired
    OrderCallbackService orderCallbackService;

    @Request(value = "/order/callback", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public boolean callback(@JSON WechatPayCallbackEntity wechatPayCallbackEntity) {
        logger.info("订单回掉");
        return orderCallbackService.handle(wechatPayCallbackEntity);
    }
}
