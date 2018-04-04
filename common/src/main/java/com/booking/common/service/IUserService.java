package com.booking.common.service;

import com.booking.common.entity.UserEntity;
import com.booking.common.resp.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* IUserService
*
* @author kai.liu
* @date 2017/12/28
*/
public interface IUserService {

    List<UserEntity> listAll();

    List<UserEntity> listUser(UserEntity user);

    UserEntity getUser(String id);

    int addUser(UserEntity userEntity);

    int addUser(List<UserEntity> userEntity);

    @Transactional(rollbackFor = Throwable.class)
    int removeUser(String id);

    @Transactional(rollbackFor = Throwable.class)
    int removeUser(List<String> ids);

    int updateById(UserEntity userEntity, String id);

    int update(UserEntity userEntity, UserEntity where);

    Page<List<UserEntity>> listShopPage(UserEntity userEntity, Integer pageNo, Integer pageSize);

}
