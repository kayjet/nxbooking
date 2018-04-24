package com.booking.background.controllers;

import com.booking.common.entity.ShopTagRelEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.Page;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.IShopTagRelService;
import com.opdar.platform.core.base.Context;
import com.opdar.platform.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * ShopTagRelController
 *
 * @author kai.liu
 * @date 2017/12/29
 */
@Controller
@ErrorHandler(ErrCodeHandler.class)
public class ShopTagRelController {
    private static final Logger logger = LoggerFactory.getLogger(ShopTagRelController.class);

    @Autowired
    IShopTagRelService shopTagRelService;

    @Value("${proxy.context}")
    private String proxyContext;

    @Request(value = "/shopTagRel/add")
    @Editor(ResultEditor.class)
    public int addShopTagRel(@JSON ShopTagRelEntity shopTagRel) {
        return shopTagRelService.addShopTagRel(shopTagRel);
    }

    @Request(value = "/shopTagRel/remove")
    @Editor(ResultEditor.class)
    public int removeShopTagRel(String id) {
        return shopTagRelService.removeShopTagRel(id);
    }

    @Request(value = "/shopTagRel/removeList")
    @Editor(ResultEditor.class)
    public int removeShopTagRelList(@JSON List<String> ids) {
        return shopTagRelService.removeShopTagRel(ids);
    }

    @Request(value = "/shopTagRel/update")
    @Editor(ResultEditor.class)
    public int updateShopTagRel(@JSON ShopTagRelEntity shopTagRel) {
        return shopTagRelService.updateById(shopTagRel, shopTagRel.getId());
    }

    @Request(value = "/shopTagRel/get", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public ShopTagRelEntity getShopTagRel(String id) {
        return shopTagRelService.getShopTagRel(id);
    }

    @Request(value = "/shopTagRel/list", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public List<ShopTagRelEntity> listShopTagRel(ShopTagRelEntity shopTagRelEntity) {
        return shopTagRelService.listShopTagRel(shopTagRelEntity);
    }

    @Request(value = "/shopTagRel/listTag", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public List<ShopTagRelEntity> listShopTagRelTag(ShopTagRelEntity shopTagRelEntity) {
        return shopTagRelService.listShopTagRelTag(shopTagRelEntity);
    }

    @Request(value = "/shopTagRel/listPage", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Page<List<ShopTagRelEntity>> listShopTagRelPage(ShopTagRelEntity shopTagRelEntity, Integer pageNo, Integer pageSize) {
        return shopTagRelService.listShopTagRelPage(shopTagRelEntity, pageNo, pageSize);
    }

    @Request(value = "/shopTagRel/view", format = Request.Format.VIEW)
    public String view() {
        logger.info("访问view页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        Context.putAttribute("proxyContext", proxyContext);
        return "shopTagRel/view";
    }
}
