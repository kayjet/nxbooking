package com.booking.common.service;

import com.booking.common.entity.UserFavShopRelEntity;
import com.booking.common.resp.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* IUserFavShopRelService
*
* @author kai.liu
* @date 2017/12/28
*/
public interface IUserFavShopRelService {

    List<UserFavShopRelEntity> listAll();

    List<UserFavShopRelEntity> listUserFavShopRel(UserFavShopRelEntity userFavShopRel);

    UserFavShopRelEntity getUserFavShopRel(String id);

    int addUserFavShopRel(UserFavShopRelEntity userFavShopRelEntity);

    int addUserFavShopRel(List<UserFavShopRelEntity> userFavShopRelEntity);

    @Transactional(rollbackFor = Throwable.class)
    int removeUserFavShopRel(String id);

    @Transactional(rollbackFor = Throwable.class)
    int removeUserFavShopRel(List<String> ids);

    int updateById(UserFavShopRelEntity userFavShopRelEntity, String id);

    int update(UserFavShopRelEntity userFavShopRelEntity, UserFavShopRelEntity where);

    Page<List<UserFavShopRelEntity>> listUserFavShopRelPage(UserFavShopRelEntity userFavShopRelEntity, Integer pageNo, Integer pageSize);

}
