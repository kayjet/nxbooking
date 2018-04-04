package com.booking.common.service.impl;

import com.booking.common.entity.UserEntity;
import com.booking.common.mapper.UserMapper;
import com.booking.common.service.IUserService;
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
 * UserServiceImpl
 *
 * @author kai.liu
 * @date 2018/01/02
 */
@Service
public class UserServiceImpl implements IUserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    @Override
    public List<UserEntity> listAll() {
        return userMapper.selectList(null);
    }

    @Override
    public List<UserEntity> listUser(UserEntity userEntity) {
        return userMapper.selectList(userEntity);
    }

    @Override
    public UserEntity getUser(String id) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        return userMapper.selectOne(userEntity);
    }

    @Override
    public int addUser(UserEntity userEntity) {
        userEntity.setId(UUID.randomUUID().toString());
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        userEntity.setCreateTime(ts);
        userEntity.setUpdateTime(ts);
        return userMapper.insert(userEntity);
    }

    @Override
    public int addUser(List<UserEntity> userEntitys) {
        int result = 0;
        for (UserEntity entity : userEntitys) {
            result += userMapper.insert(entity);
        }
        return result;
    }

    @Override
    public int removeUser(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("删除操作时，主键不能为空！");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        return userMapper.delete(userEntity);
    }

    @Override
    public int removeUser(List<String> ids) {
        int result = 0;
        for (String id : ids) {
            if (StringUtils.isEmpty(id)) {
                throw new RuntimeException("删除操作时，主键不能为空！");
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setId(id);
            result += userMapper.delete(userEntity);
        }
        return result;
    }

    @Override
    public int updateById(UserEntity userEntity, String id) {
        UserEntity where = new UserEntity();
        where.setId(id);
        return this.update(userEntity, where);
    }

    @Override
    public int update(UserEntity userEntity, UserEntity where) {
        userEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return userMapper.update(userEntity, where);
    }

    @Override
    public Page<List<UserEntity>> listShopPage(UserEntity userEntity, Integer pageNo, Integer pageSize) {
        Integer countSize = userMapper.likeCount(userEntity);
        Page<List<UserEntity>> page = new Page<List<UserEntity>>(pageSize, pageNo, countSize);
        PageInterceptor.setPage(pageNo, pageSize);
        List<UserEntity> result = userMapper.selectLikeList(userEntity);
        page.setResult(result);
        return page;
    }
}
