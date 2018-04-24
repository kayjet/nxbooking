package com.booking.background.controllers;

import com.booking.background.interceptor.LoginSessionInterceptor;
import com.booking.common.service.IShopService;
import com.booking.common.entity.ShopEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.interceptor.TimeQueryInterceptor;
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
* ShopController
*
* @author kai.liu
* @date 2017/12/29
*/
@Controller
@ErrorHandler(ErrCodeHandler.class)
@Interceptor({TimeQueryInterceptor.class,LoginSessionInterceptor.class})
public class ShopController {
    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    IShopService shopService;

    @Value("${proxy.context}")
    private String proxyContext;

    @Request(value = "/shop/add")
    @Editor(ResultEditor.class)
    public int addShop(@JSON ShopEntity shop) {
        return shopService.addShop(shop);
    }

    @Request(value = "/shop/remove")
    @Editor(ResultEditor.class)
    public int removeShop(String id) {
        return shopService.removeShop(id);
    }

    @Request(value = "/shop/removeList")
    @Editor(ResultEditor.class)
    public int removeShopList(@JSON List<String> ids) {
        return shopService.removeShop(ids);
    }

    @Request(value = "/shop/update")
    @Editor(ResultEditor.class)
    public int updateShop(@JSON ShopEntity shop) {
        return  shopService.updateById(shop, shop.getId());
    }

    @Request(value = "/shop/get", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public ShopEntity getShop(String id) {
        return shopService.getShop(id);
    }

    @Request(value = "/shop/list", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public List<ShopEntity> listShop(ShopEntity shopEntity) {
        return shopService.listShop(shopEntity);
    }

    @Request(value = "/shop/listPage", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Page<List<ShopEntity>> listShopPage(ShopEntity shopEntity, Integer pageNo, Integer pageSize) {
        return shopService.listShopPage(shopEntity, pageNo, pageSize);
    }

    @Request(value = "/shop/view", format = Request.Format.VIEW)
    public String view() {
        logger.info("访问view页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        Context.putAttribute("navList",new String[]{"门店管理","详情记录"});
        Context.putAttribute("proxyContext", proxyContext);
        return "shop/view";
    }
}
