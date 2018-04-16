package com.booking.background.controllers;

import com.booking.common.service.IUserService;
import com.booking.common.entity.UserEntity;
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
* UserController
*
* @author kai.liu
* @date 2017/12/29
*/
@Controller
@ErrorHandler(ErrCodeHandler.class)
@Interceptor(TimeQueryInterceptor.class)
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    IUserService userService;

    @Request(value = "/user/add")
    @Editor(ResultEditor.class)
    public int addUser(@JSON UserEntity user) {
        return userService.addUser(user);
    }

    @Request(value = "/user/remove")
    @Editor(ResultEditor.class)
    public int removeUser(String id) {
        return userService.removeUser(id);
    }

    @Request(value = "/user/removeList")
    @Editor(ResultEditor.class)
    public int removeUserList(@JSON List<String> ids) {
        return userService.removeUser(ids);
    }

    @Request(value = "/user/update")
    @Editor(ResultEditor.class)
    public int updateUser(@JSON UserEntity user) {
        return  userService.updateById(user, user.getId());
    }

    @Request(value = "/user/get", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public UserEntity getUser(String id) {
        return userService.getUser(id);
    }

    @Request(value = "/user/list", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public List<UserEntity> listUser(UserEntity userEntity) {
        return userService.listUser(userEntity);
    }

    @Request(value = "/user/listPage", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Page<List<UserEntity>> listShopPage(UserEntity userEntity, Integer pageNo, Integer pageSize) {
        return userService.listShopPage(userEntity, pageNo, pageSize);
    }

    @Request(value = "/user/view", format = Request.Format.VIEW)
    public String view() {
        logger.info("访问view页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        Context.putAttribute("navList",new String[]{"用户记录"});
        return "user/view";
    }
}
