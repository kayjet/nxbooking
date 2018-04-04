package com.booking.common.service.impl;

import com.booking.common.entity.OrderShopRelEntity;
import com.booking.common.mapper.OrderShopRelMapper;
import com.booking.common.service.IOrderShopRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.booking.common.resp.Page;
import com.booking.common.resp.PageInterceptor;


import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


/**
* OrderShopIdServiceImpl
*
* @author kai.liu
* @date 2018/01/02
*/
@Service
public class OrderShopRelServiceImpl implements IOrderShopRelService {
    Logger logger = LoggerFactory.getLogger(OrderShopRelServiceImpl.class);

    @Autowired
    OrderShopRelMapper orderShopRelMapper;

    @Override
    public List<OrderShopRelEntity> listAll() {
        return orderShopRelMapper.selectList(null);
    }

    @Override
    public List<OrderShopRelEntity> listOrderShopId(OrderShopRelEntity orderShopRelEntity) {
        return orderShopRelMapper.selectList(orderShopRelEntity);
    }

    @Override
    public Page<List<OrderShopRelEntity>> listOrderShopIdPage(OrderShopRelEntity orderShopRelEntity, Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Integer countSize =  orderShopRelMapper.likeCount(orderShopRelEntity);
        Page<List<OrderShopRelEntity>> page = new Page<List<OrderShopRelEntity>>(pageSize, pageNo, countSize);
        PageInterceptor.setPage(pageNo, pageSize);
        List<OrderShopRelEntity> result =  orderShopRelMapper.selectLikeList(orderShopRelEntity);
        page.setResult(result);
        return page;
    }
    @Override
    public OrderShopRelEntity getOrderShopId(String id) {
        OrderShopRelEntity orderShopRelEntity = new OrderShopRelEntity();
        orderShopRelEntity.setId(id);
        return orderShopRelMapper.selectOne(orderShopRelEntity);
    }

    @Override
    public int addOrderShopId(OrderShopRelEntity orderShopRelEntity) {
        orderShopRelEntity.setId(UUID.randomUUID().toString());
        return orderShopRelMapper.insert(orderShopRelEntity);
    }

    @Override
    public int addOrderShopId(List<OrderShopRelEntity> orderShopRelEntities) {
        int result = 0;
        for (OrderShopRelEntity entity : orderShopRelEntities) {
            result += orderShopRelMapper.insert(entity);
        }
        return result;
    }

    @Override
    public int removeOrderShopId(String id) {
        if(StringUtils.isEmpty(id)){
            throw new RuntimeException("删除操作时，主键不能为空！");
        }
        OrderShopRelEntity orderShopRelEntity = new OrderShopRelEntity();
        orderShopRelEntity.setId(id);
        return orderShopRelMapper.delete(orderShopRelEntity);
    }

    @Override
    public int removeOrderShopId(List<String> ids) {
        int result = 0;
        for (String id : ids) {
            if(StringUtils.isEmpty(id)){
                throw new RuntimeException("删除操作时，主键不能为空！");
            }
            OrderShopRelEntity orderShopRelEntity = new OrderShopRelEntity();
            orderShopRelEntity.setId(id);
            result += orderShopRelMapper.delete(orderShopRelEntity);
        }
        return result;
    }

    @Override
    public int updateById(OrderShopRelEntity orderShopRelEntity, String id) {
        OrderShopRelEntity where = new OrderShopRelEntity();
        where.setId(id);
        return this.update(orderShopRelEntity, where);
    }

    @Override
    public int update(OrderShopRelEntity orderShopRelEntity, OrderShopRelEntity where) {
        orderShopRelEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return orderShopRelMapper.update(orderShopRelEntity, where);
    }
}
