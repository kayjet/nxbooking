package com.booking.background.controllers;

import com.booking.common.service.IOrderProductRelService;
import com.booking.common.entity.OrderProductRelEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.Page;
import com.booking.common.resp.ResultEditor;
import com.opdar.platform.core.base.Context;
import com.opdar.platform.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
* OrderProductRelController
*
* @author kai.liu
* @date 2017/12/29
*/
@Controller
@ErrorHandler(ErrCodeHandler.class)
public class OrderProductRelController {
    private static final Logger logger = LoggerFactory.getLogger(OrderProductRelController.class);

    @Autowired
    IOrderProductRelService orderProductRelService;

    @Request(value = "/orderProductRel/add")
    @Editor(ResultEditor.class)
    public int addOrderProductRel(@JSON OrderProductRelEntity orderProductRel) {
        return orderProductRelService.addOrderProductRel(orderProductRel);
    }

    @Request(value = "/orderProductRel/remove")
    @Editor(ResultEditor.class)
    public int removeOrderProductRel(String id) {
        return orderProductRelService.removeOrderProductRel(id);
    }

    @Request(value = "/orderProductRel/removeList")
    @Editor(ResultEditor.class)
    public int removeOrderProductRelList(@JSON List<String> ids) {
        return orderProductRelService.removeOrderProductRel(ids);
    }

    @Request(value = "/orderProductRel/update")
    @Editor(ResultEditor.class)
    public int updateOrderProductRel(@JSON OrderProductRelEntity orderProductRel) {
        return  orderProductRelService.updateById(orderProductRel, orderProductRel.getId());
    }

    @Request(value = "/orderProductRel/get", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public OrderProductRelEntity getOrderProductRel(String id) {
        return orderProductRelService.getOrderProductRel(id);
    }

    @Request(value = "/orderProductRel/list", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public List<OrderProductRelEntity> listOrderProductRel(OrderProductRelEntity orderProductRelEntity) {
        return orderProductRelService.listOrderProductRel(orderProductRelEntity);
    }

    @Request(value = "/orderProductRel/listPage", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Page<List<OrderProductRelEntity>> listOrderProductRelPage(OrderProductRelEntity orderProductRelEntity, Integer pageNo, Integer pageSize) {
        return orderProductRelService.listOrderProductRelPage(orderProductRelEntity, pageNo , pageSize);
    }

    @Request(value = "/orderProductRel/view", format = Request.Format.VIEW)
    public String view() {
        logger.info("访问view页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        return "orderProductRel/view";
    }
}
