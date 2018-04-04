package com.booking.common.service.impl;

import com.booking.common.entity.UserFavShopRelEntity;
import com.booking.common.mapper.UserFavShopRelMapper;
import com.booking.common.service.IUserFavShopRelService;
import com.booking.common.resp.Page;
import com.booking.common.resp.PageInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


/**
* UserFavShopRelServiceImpl
*
* @author kai.liu
* @date 2018/01/02
*/
@Service
public class UserFavShopRelServiceImpl implements IUserFavShopRelService {
    Logger logger = LoggerFactory.getLogger(UserFavShopRelServiceImpl.class);

    @Autowired
    UserFavShopRelMapper userFavShopRelMapper;

    @Override
    public List<UserFavShopRelEntity> listAll() {
        return userFavShopRelMapper.selectList(null);
    }

    @Override
    public List<UserFavShopRelEntity> listUserFavShopRel(UserFavShopRelEntity userFavShopRelEntity) {
        return userFavShopRelMapper.selectList(userFavShopRelEntity);
    }

    @Override
    public UserFavShopRelEntity getUserFavShopRel(String id) {
        UserFavShopRelEntity userFavShopRelEntity = new UserFavShopRelEntity();
        userFavShopRelEntity.setId(id);
        return userFavShopRelMapper.selectOne(userFavShopRelEntity);
    }

    @Override
    public int addUserFavShopRel(UserFavShopRelEntity userFavShopRelEntity) {
        userFavShopRelEntity.setId(UUID.randomUUID().toString());
        return userFavShopRelMapper.insert(userFavShopRelEntity);
    }

    @Override
    public int addUserFavShopRel(List<UserFavShopRelEntity> userFavShopRelEntitys) {
        int result = 0;
        for (UserFavShopRelEntity entity : userFavShopRelEntitys) {
            result += userFavShopRelMapper.insert(entity);
        }
        return result;
    }

    @Override
    public int removeUserFavShopRel(String id) {
        if(StringUtils.isEmpty(id)){
            throw new RuntimeException("删除操作时，主键不能为空！");
        }
        UserFavShopRelEntity userFavShopRelEntity = new UserFavShopRelEntity();
        userFavShopRelEntity.setId(id);
        return userFavShopRelMapper.delete(userFavShopRelEntity);
    }

    @Override
    public int removeUserFavShopRel(List<String> ids) {
        int result = 0;
        for (String id : ids) {
            if(StringUtils.isEmpty(id)){
                throw new RuntimeException("删除操作时，主键不能为空！");
            }
            UserFavShopRelEntity userFavShopRelEntity = new UserFavShopRelEntity();
            userFavShopRelEntity.setId(id);
            result += userFavShopRelMapper.delete(userFavShopRelEntity);
        }
        return result;
    }

    @Override
    public int updateById(UserFavShopRelEntity userFavShopRelEntity, String id) {
        UserFavShopRelEntity where = new UserFavShopRelEntity();
        where.setId(id);
        return this.update(userFavShopRelEntity, where);
    }

    @Override
    public int update(UserFavShopRelEntity userFavShopRelEntity, UserFavShopRelEntity where) {
        userFavShopRelEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return userFavShopRelMapper.update(userFavShopRelEntity, where);
    }

    @Override
    public Page<List<UserFavShopRelEntity>> listUserFavShopRelPage(UserFavShopRelEntity userFavShopRelEntity, Integer pageNo, Integer pageSize) {
        Integer countSize = userFavShopRelMapper.likeCount(userFavShopRelEntity);
        Page<List<UserFavShopRelEntity>> page = new Page<List<UserFavShopRelEntity>>(pageSize, pageNo, countSize);
        PageInterceptor.setPage(pageNo, pageSize);
        List<UserFavShopRelEntity> result = userFavShopRelMapper.selectLikeList(userFavShopRelEntity);
        page.setResult(result);
        return page;
    }
}
