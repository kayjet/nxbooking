package com.booking.background.controllers;

import com.booking.common.service.IUserFavShopRelService;
import com.booking.common.entity.UserFavShopRelEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.interceptor.TimeQueryInterceptor;
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
* UserFavShopRelController
*
* @author kai.liu
* @date 2017/12/29
*/
@Controller
@ErrorHandler(ErrCodeHandler.class)
@Interceptor(TimeQueryInterceptor.class)
public class UserFavShopRelController {
    private static final Logger logger = LoggerFactory.getLogger(UserFavShopRelController.class);

    @Autowired
    IUserFavShopRelService userFavShopRelService;

    @Request(value = "/userFavShopRel/add")
    @Editor(ResultEditor.class)
    public int addUserFavShopRel(@JSON UserFavShopRelEntity userFavShopRel) {
        return userFavShopRelService.addUserFavShopRel(userFavShopRel);
    }

    @Request(value = "/userFavShopRel/remove")
    @Editor(ResultEditor.class)
    public int removeUserFavShopRel(String id) {
        return userFavShopRelService.removeUserFavShopRel(id);
    }

    @Request(value = "/userFavShopRel/removeList")
    @Editor(ResultEditor.class)
    public int removeUserFavShopRelList(@JSON List<String> ids) {
        return userFavShopRelService.removeUserFavShopRel(ids);
    }

    @Request(value = "/userFavShopRel/update")
    @Editor(ResultEditor.class)
    public int updateUserFavShopRel(@JSON UserFavShopRelEntity userFavShopRel) {
        return  userFavShopRelService.updateById(userFavShopRel, userFavShopRel.getId());
    }

    @Request(value = "/userFavShopRel/get", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public UserFavShopRelEntity getUserFavShopRel(String id) {
        return userFavShopRelService.getUserFavShopRel(id);
    }

    @Request(value = "/userFavShopRel/list", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public List<UserFavShopRelEntity> listUserFavShopRel(UserFavShopRelEntity userFavShopRelEntity) {
        return userFavShopRelService.listUserFavShopRel(userFavShopRelEntity);
    }

    @Request(value = "/userFavShopRel/listPage", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Page<List<UserFavShopRelEntity>> listUserFavShopRelPage(UserFavShopRelEntity userFavShopRelEntity,Integer pageNo, Integer pageSize) {
        return userFavShopRelService.listUserFavShopRelPage(userFavShopRelEntity,pageNo ,pageSize);
    }

    @Request(value = "/userFavShopRel/view", format = Request.Format.VIEW)
    public String view() {
        logger.info("访问view页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        return "userFavShopRel/view";
    }
}
