package com.booking.common.service.impl;

import com.booking.common.entity.ShopTagRelEntity;
import com.booking.common.mapper.ShopTagRelMapper;
import com.booking.common.resp.Page;
import com.booking.common.resp.PageInterceptor;
import com.booking.common.service.IShopTagRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


/**
* ShopTagRelServiceImpl
*
* @author kai.liu
* @date 2018/01/02
*/
@Service
public class ShopTagRelServiceImpl implements IShopTagRelService {
    Logger logger = LoggerFactory.getLogger(ShopTagRelServiceImpl.class);

    @Autowired
    ShopTagRelMapper shopTagRelMapper;

    @Override
    public List<ShopTagRelEntity> listAll() {
        return shopTagRelMapper.selectList(null);
    }

    @Override
    public List<ShopTagRelEntity> listShopTagRel(ShopTagRelEntity shopTagRelEntity) {
        return shopTagRelMapper.selectList(shopTagRelEntity);
    }

    @Override
    public Page<List<ShopTagRelEntity>> listShopTagRelPage(ShopTagRelEntity shopTagRelEntity, Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Integer countSize =  shopTagRelMapper.likeCount(shopTagRelEntity);
        Page<List<ShopTagRelEntity>> page = new Page<List<ShopTagRelEntity>>(pageSize, pageNo, countSize);
        PageInterceptor.setPage(pageNo, pageSize);
        List<ShopTagRelEntity> result =  shopTagRelMapper.selectLikeList(shopTagRelEntity);
        page.setResult(result);
        return page;
    }
    @Override
    public ShopTagRelEntity getShopTagRel(String id) {
        ShopTagRelEntity shopTagRelEntity = new ShopTagRelEntity();
        shopTagRelEntity.setId(id);
        return shopTagRelMapper.selectOne(shopTagRelEntity);
    }

    @Override
    public int addShopTagRel(ShopTagRelEntity shopTagRelEntity) {
        shopTagRelEntity.setId(UUID.randomUUID().toString());
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        shopTagRelEntity.setCreateTime(ts);
        shopTagRelEntity.setUpdateTime(ts);
        return shopTagRelMapper.insert(shopTagRelEntity);
    }

    @Override
    public int addShopTagRel(List<ShopTagRelEntity> shopTagRelEntitys) {
        int result = 0;
        for (ShopTagRelEntity entity : shopTagRelEntitys) {
            result += this.addShopTagRel(entity);
        }
        return result;
    }

    @Override
    public int removeShopTagRel(String id) {
        if(StringUtils.isEmpty(id)){
            throw new RuntimeException("删除操作时，主键不能为空！");
        }
        ShopTagRelEntity shopTagRelEntity = new ShopTagRelEntity();
        shopTagRelEntity.setId(id);
        return shopTagRelMapper.delete(shopTagRelEntity);
    }

    @Override
    public int removeShopTagRel(List<String> ids) {
        int result = 0;
        for (String id : ids) {
            if(StringUtils.isEmpty(id)){
                throw new RuntimeException("删除操作时，主键不能为空！");
            }
            ShopTagRelEntity shopTagRelEntity = new ShopTagRelEntity();
            shopTagRelEntity.setId(id);
            result += shopTagRelMapper.delete(shopTagRelEntity);
        }
        return result;
    }

    @Override
    public int updateById(ShopTagRelEntity shopTagRelEntity, String id) {
        ShopTagRelEntity where = new ShopTagRelEntity();
        where.setId(id);
        return this.update(shopTagRelEntity, where);
    }

    @Override
    public int update(ShopTagRelEntity shopTagRelEntity, ShopTagRelEntity where) {
        shopTagRelEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return shopTagRelMapper.update(shopTagRelEntity, where);
    }

    @Override
    public List<ShopTagRelEntity> listShopTagRelTag(ShopTagRelEntity shopTagRelEntity) {
        return shopTagRelMapper.selectJoinTagList(shopTagRelEntity);
    }
}
