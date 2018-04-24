package com.booking.background.controllers;

import com.booking.common.service.IOrderShopRelService;
import com.booking.common.entity.OrderShopRelEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.Page;
import com.booking.common.resp.ResultEditor;
import com.opdar.platform.core.base.Context;
import com.opdar.platform.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
* OrderShopIdController
*
* @author kai.liu
* @date 2017/12/29
*/
@Controller
@ErrorHandler(ErrCodeHandler.class)
public class OrderShopRelController {
    private static final Logger logger = LoggerFactory.getLogger(OrderShopRelController.class);

    @Autowired
    IOrderShopRelService orderShopIdService;
    @Value("${proxy.context}")
    private String proxyContext;

    @Request(value = "/orderShopId/add")
    @Editor(ResultEditor.class)
    public int addOrderShopId(@JSON OrderShopRelEntity orderShopId) {
        return orderShopIdService.addOrderShopId(orderShopId);
    }

    @Request(value = "/orderShopId/remove")
    @Editor(ResultEditor.class)
    public int removeOrderShopId(String id) {
        return orderShopIdService.removeOrderShopId(id);
    }

    @Request(value = "/orderShopId/removeList")
    @Editor(ResultEditor.class)
    public int removeOrderShopIdList(@JSON List<String> ids) {
        return orderShopIdService.removeOrderShopId(ids);
    }

    @Request(value = "/orderShopId/update")
    @Editor(ResultEditor.class)
    public int updateOrderShopId(@JSON OrderShopRelEntity orderShopId) {
        return  orderShopIdService.updateById(orderShopId, orderShopId.getId());
    }

    @Request(value = "/orderShopId/get", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public OrderShopRelEntity getOrderShopId(String id) {
        return orderShopIdService.getOrderShopId(id);
    }

    @Request(value = "/orderShopId/list", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public List<OrderShopRelEntity> listOrderShopId(OrderShopRelEntity orderShopRelEntity) {
        return orderShopIdService.listOrderShopRel(orderShopRelEntity);
    }

    @Request(value = "/orderShopId/listPage", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Page<List<OrderShopRelEntity>> listOrderShopIdPage(OrderShopRelEntity orderShopRelEntity, Integer pageNo, Integer pageSize) {
        return orderShopIdService.listOrderShopRelPage(orderShopRelEntity, pageNo , pageSize);
    }

    @Request(value = "/orderShopId/view", format = Request.Format.VIEW)
    public String view() {
        logger.info("访问view页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        Context.putAttribute("proxyContext", proxyContext);
        return "orderShopId/view";
    }
}
