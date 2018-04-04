package com.booking.api.controllers;

import com.booking.common.base.ICacheManager;
import com.booking.common.entity.*;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.*;
import com.booking.common.service.impl.WeChatService;
import com.opdar.platform.annotations.Editor;
import com.opdar.platform.annotations.ErrorHandler;
import com.opdar.platform.annotations.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Sprint1Controller
 *
 * @author kai.liu
 * @date 2018/03/27
 */
@Controller
@ErrorHandler(ErrCodeHandler.class)
public class Sprint2Controller {
    Logger logger = LoggerFactory.getLogger(Sprint2Controller.class);

    @Autowired
    IShopService shopService;

    @Autowired
    IUserFavShopRelService userFavShopRelService;

    @Autowired
    IUserService userService;

    @Autowired
    IShopTagRelService shopTagRelService;

    @Autowired
    WeChatService weChatService;

    @Autowired
    ICacheManager cacheManager;

    @Request(value = "/sp2/shop/listProducts")
    @Editor(ResultEditor.class)
    public List<ShopTagRelForWebEntity> listPage(String shopId) {
        return shopService.listProducts(shopId);
    }
}
