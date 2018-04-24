package com.booking.background.controllers;

import com.booking.common.service.IOrderUserRelService;
import com.booking.common.entity.OrderUserRelEntity;
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
* OrderUserRelController
*
* @author kai.liu
* @date 2017/12/29
*/
@Controller
@ErrorHandler(ErrCodeHandler.class)
public class OrderUserRelController {
    private static final Logger logger = LoggerFactory.getLogger(OrderUserRelController.class);

    @Autowired
    IOrderUserRelService orderUserRelService;

    @Value("${proxy.context}")
    private String proxyContext;

    @Request(value = "/orderUserRel/add")
    @Editor(ResultEditor.class)
    public int addOrderUserRel(@JSON OrderUserRelEntity orderUserRel) {
        return orderUserRelService.addOrderUserRel(orderUserRel);
    }

    @Request(value = "/orderUserRel/remove")
    @Editor(ResultEditor.class)
    public int removeOrderUserRel(String id) {
        return orderUserRelService.removeOrderUserRel(id);
    }

    @Request(value = "/orderUserRel/removeList")
    @Editor(ResultEditor.class)
    public int removeOrderUserRelList(@JSON List<String> ids) {
        return orderUserRelService.removeOrderUserRel(ids);
    }

    @Request(value = "/orderUserRel/update")
    @Editor(ResultEditor.class)
    public int updateOrderUserRel(@JSON OrderUserRelEntity orderUserRel) {
        return  orderUserRelService.updateById(orderUserRel, orderUserRel.getId());
    }

    @Request(value = "/orderUserRel/get", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public OrderUserRelEntity getOrderUserRel(String id) {
        return orderUserRelService.getOrderUserRel(id);
    }

    @Request(value = "/orderUserRel/list", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public List<OrderUserRelEntity> listOrderUserRel(OrderUserRelEntity orderUserRelEntity) {
        return orderUserRelService.listOrderUserRel(orderUserRelEntity);
    }

    @Request(value = "/orderUserRel/listPage", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Page<List<OrderUserRelEntity>> listOrderUserRelPage(OrderUserRelEntity orderUserRelEntity, Integer pageNo, Integer pageSize) {
        return orderUserRelService.listOrderUserRelPage(orderUserRelEntity, pageNo , pageSize);
    }

    @Request(value = "/orderUserRel/view", format = Request.Format.VIEW)
    public String view() {
        logger.info("访问view页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        Context.putAttribute("proxyContext", proxyContext);
        return "orderUserRel/view";
    }
}
