package com.booking.common.service.impl;

import com.booking.common.entity.ShopEntity;
import com.booking.common.entity.ShopTagRelForWebEntity;
import com.booking.common.mapper.ShopMapper;
import com.booking.common.mapper.ShopTagRelWebMapper;
import com.booking.common.service.IShopService;
import com.booking.common.resp.Page;
import com.booking.common.resp.PageInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


/**
 * ShopServiceImpl
 *
 * @author kai.liu
 * @date 2018/01/02
 */
@Service
public class ShopServiceImpl implements IShopService {
    Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Autowired
    ShopMapper shopMapper;
    @Autowired
    ShopTagRelWebMapper shopTagRelWebMapper;

    @Override
    public List<ShopEntity> listAll() {
        return shopMapper.selectList(null);
    }

    @Override
    public List<ShopEntity> listShop(ShopEntity shopEntity) {
        return shopMapper.selectList(shopEntity);
    }

    @Override
    public ShopEntity getShop(String id) {
        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setId(id);
        return shopMapper.selectOne(shopEntity);
    }

    @Override
    public int addShop(ShopEntity shopEntity) {
        shopEntity.setId(UUID.randomUUID().toString());
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        shopEntity.setCreateTime(ts);
        shopEntity.setUpdateTime(ts);
        return shopMapper.insert(shopEntity);
    }

    @Override
    public int addShop(List<ShopEntity> shopEntitys) {
        int result = 0;
        for (ShopEntity entity : shopEntitys) {
            result += shopMapper.insert(entity);
        }
        return result;
    }

    @Override
    public int removeShop(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("删除操作时，主键不能为空！");
        }
        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setId(id);
        return shopMapper.delete(shopEntity);
    }

    @Override
    public int removeShop(List<String> ids) {
        int result = 0;
        for (String id : ids) {
            if (StringUtils.isEmpty(id)) {
                throw new RuntimeException("删除操作时，主键不能为空！");
            }
            ShopEntity shopEntity = new ShopEntity();
            shopEntity.setId(id);
            result += shopMapper.delete(shopEntity);
        }
        return result;
    }

    @Override
    public int updateById(ShopEntity shopEntity, String id) {
        ShopEntity where = new ShopEntity();
        where.setId(id);
        return this.update(shopEntity, where);
    }

    @Override
    public int update(ShopEntity shopEntity, ShopEntity where) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        shopEntity.setUpdateTime(ts);
        return shopMapper.update(shopEntity, where);
    }

    @Override
    public Page<List<ShopEntity>> listShopPage(ShopEntity shopEntity, Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Integer countSize = shopMapper.likeCount(shopEntity);
        Page<List<ShopEntity>> page = new Page<List<ShopEntity>>(pageSize, pageNo, countSize);
        PageInterceptor.setPage(pageNo, pageSize);
        List<ShopEntity> result = shopMapper.selectLikeList(shopEntity);
        page.setResult(result);
        return page;
    }

    @Override
    public List<ShopTagRelForWebEntity> listProducts(String shopId) {
        ShopTagRelForWebEntity query = new ShopTagRelForWebEntity();
        query.setShopId(shopId);
        List<ShopTagRelForWebEntity> result = shopTagRelWebMapper.selectList(query);
        Iterator<ShopTagRelForWebEntity> iterator = result.iterator();
        while (iterator.hasNext()) {
            ShopTagRelForWebEntity entity = iterator.next();
            if (CollectionUtils.isEmpty(entity.getTagList().get(0).getProductList())) {
                iterator.remove();
            }
        }
        return result;
    }
}
