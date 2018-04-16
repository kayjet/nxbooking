package com.booking.background.controllers;

import com.booking.common.entity.OrderShopRelEntity;
import com.booking.common.entity.ShopEntity;
import com.booking.common.interceptor.TimeQueryInterceptor;
import com.booking.common.service.IOrderService;
import com.booking.common.entity.OrderEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.Page;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.IShopService;
import com.opdar.platform.core.base.Context;
import com.opdar.platform.annotations.*;
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
@Interceptor(TimeQueryInterceptor.class)
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    IOrderService orderService;

    @Autowired
    IShopService shopService;

    @Request(value = "/order/add")
    @Editor(ResultEditor.class)
    public int addOrder(@JSON OrderEntity order) {
        return orderService.addOrder(order);
    }

    @Request(value = "/order/remove")
    @Editor(ResultEditor.class)
    public int removeOrder(String id) {
        return orderService.removeOrder(id);
    }

    @Request(value = "/order/removeList")
    @Editor(ResultEditor.class)
    public int removeOrderList(@JSON List<String> ids) {
        return orderService.removeOrder(ids);
    }

    @Request(value = "/order/update")
    @Editor(ResultEditor.class)
    public int updateOrder(@JSON OrderEntity order) {
        return orderService.updateById(order, order.getId());
    }

    @Request(value = "/order/get", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public OrderEntity getOrder(String id) {
        return orderService.getOrder(id);
    }

    @Request(value = "/order/list", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public List<OrderEntity> listOrder(OrderEntity orderEntity) {
        return orderService.listOrder(orderEntity);
    }

    @Request(value = "/order/listPage", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Page<List<OrderEntity>> listOrderPage(OrderEntity orderEntity, Integer pageNo, Integer pageSize) {
        return orderService.listOrderPage(orderEntity, pageNo, pageSize);
    }

    @Request(value = "/order/exportExcel", format = Request.Format.JSON)
    public void exportExcel(OrderEntity orderEntity) {
        try {
            OutputStream outputStream = Context.getResponse().getOutputStream();
            Workbook workbook = orderService.exportExcel(orderEntity);
            if (workbook != null) {
                workbook.write(outputStream);
                Context.getResponse().setContentType("application/vnd.ms-excel");
                String xlsName = "订单" + System.currentTimeMillis();
                Context.getResponse().addHeader("Content-Disposition", "attachment; filename=" + xlsName + ".xls");
                outputStream.flush();
                outputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Request(value = "/order/view", format = Request.Format.VIEW)
    public String view() {
        logger.info("访问view页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        List<ShopEntity> shops = shopService.listAll();
        Context.putAttribute("shops", shops);
        Context.putAttribute("navList", new String[]{"订单", "订单详情"});
        return "order/view";
    }

    @Request(value = "/websocket/view", format = Request.Format.VIEW)
    public String websocket() {
        logger.info("访问view页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        Context.putAttribute("shopList", shopService.listAll());
        Context.putAttribute("navList", new String[]{"即时订单"});
        return "websocket/view";
    }
}
