package com.booking.api.controllers;

import com.booking.common.base.ICacheManager;
import com.booking.common.entity.ShopEntity;
import com.booking.common.entity.UserEntity;
import com.booking.common.entity.UserFavShopRelEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.Page;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.IShopService;
import com.booking.common.service.IUserFavShopRelService;
import com.booking.common.service.IUserService;
import com.booking.common.service.impl.WeChatService;
import com.opdar.platform.annotations.Editor;
import com.opdar.platform.annotations.ErrorHandler;
import com.opdar.platform.annotations.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Sprint1Controller
 *
 * @author kai.liu
 * @date 2018/03/27
 */
@Controller
@ErrorHandler(ErrCodeHandler.class)
public class Sprint1Controller {
    Logger logger = LoggerFactory.getLogger(Sprint1Controller.class);

    @Autowired
    IShopService shopService;

    @Autowired
    IUserFavShopRelService userFavShopRelService;

    @Autowired
    IUserService userService;

    @Autowired
    WeChatService weChatService;

    @Autowired
    ICacheManager cacheManager;

    @Request(value = "/sp1/shop/listPage")
    @Editor(ResultEditor.class)
    public Page<List<ShopEntity>> listPage(String lat, String lng, String name, Integer pageNo, Integer pageSize) {
        ShopEntity searchEntity = new ShopEntity();
        if (!StringUtils.isEmpty(lat) && !StringUtils.isEmpty(lng)) {
            searchEntity.setLat(Double.valueOf(lat));
            searchEntity.setLng(Double.valueOf(lng));
        }
        if (!StringUtils.isEmpty(name)) {
            searchEntity.setName(name);
        }
        pageSize = 1000;
        return shopService.listShopPage(searchEntity, pageNo, pageSize);
    }

    @Request(value = "/sp1/user/addFavShop")
    @Editor(ResultEditor.class)
    public Boolean addFavShop(String fkUserId, String fkShopId) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        UserFavShopRelEntity userFavShopRelEntity = new UserFavShopRelEntity(fkUserId, fkShopId);
        userFavShopRelEntity.setCreateTime(ts);
        userFavShopRelEntity.setUpdateTime(ts);
        return userFavShopRelService.addUserFavShopRel(userFavShopRelEntity) > 0;
    }

    @Request(value = "/sp1/user/deleteFavShop")
    @Editor(ResultEditor.class)
    public Boolean deleteFavShop(String fkUserId, String fkShopId) {
        UserFavShopRelEntity userFavShopRelEntity = new UserFavShopRelEntity(fkUserId, fkShopId);
        String id = userFavShopRelService.listUserFavShopRel(userFavShopRelEntity).get(0).getId();
        return userFavShopRelService.removeUserFavShopRel(id) > 0;
    }

    @Request(value = "/sp1/user/getFavShopList")
    @Editor(ResultEditor.class)
    public List<ShopEntity> getFavShopList(String fkUserId) {
        UserFavShopRelEntity userFavShopRelEntity = new UserFavShopRelEntity();
        userFavShopRelEntity.setFkUserId(fkUserId);
        List<UserFavShopRelEntity> result = userFavShopRelService.listUserFavShopRel(userFavShopRelEntity);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        List<ShopEntity> shopList = new ArrayList<ShopEntity>();
        for (UserFavShopRelEntity ret : result) {
            shopList.addAll(ret.getShopList());
        }
        return shopList;
    }


    @Request(value = "/sp1/user/bindUser")
    @Editor(ResultEditor.class)
    public UserEntity bindUser(String jsCode, String nikeName, Integer gender, String avatarUrl) {
        WeChatService.OpenidResult result = null;
        if (cacheManager.get(jsCode) != null) {
            result = (WeChatService.OpenidResult) cacheManager.get(jsCode);
        } else {
            result = weChatService.getUserOpenId(jsCode);
            if (result != null) {
                cacheManager.set(jsCode, result, result.getExpires_in());
            }
        }
        UserEntity query = new UserEntity();
        query.setOpenid(result.getOpenid());
        List<UserEntity> ret = userService.listUser(query);
        if (!CollectionUtils.isEmpty(ret)) {
            UserEntity userEntity = ret.get(0);
            userEntity.setOpenid(null);
            return userEntity;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setNickName(nikeName);
        userEntity.setGender(gender);
        userEntity.setAvatarUrl(avatarUrl);
        userEntity.setOpenid(query.getOpenid());
        userService.addUser(userEntity);
        return userEntity;
    }
}
