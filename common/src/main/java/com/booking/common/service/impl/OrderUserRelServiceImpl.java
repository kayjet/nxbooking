package com.booking.common.service.impl;

import com.booking.common.entity.OrderUserRelEntity;
import com.booking.common.mapper.OrderUserRelMapper;
import com.booking.common.service.IOrderUserRelService;
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
* OrderUserRelServiceImpl
*
* @author kai.liu
* @date 2018/01/02
*/
@Service
public class OrderUserRelServiceImpl implements IOrderUserRelService {
    Logger logger = LoggerFactory.getLogger(OrderUserRelServiceImpl.class);

    @Autowired
    OrderUserRelMapper orderUserRelMapper;

    @Override
    public List<OrderUserRelEntity> listAll() {
        return orderUserRelMapper.selectList(null);
    }

    @Override
    public List<OrderUserRelEntity> listOrderUserRel(OrderUserRelEntity orderUserRelEntity) {
        return orderUserRelMapper.selectList(orderUserRelEntity);
    }

    @Override
    public Page<List<OrderUserRelEntity>> listOrderUserRelPage(OrderUserRelEntity orderUserRelEntity, Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Integer countSize =  orderUserRelMapper.likeCount(orderUserRelEntity);
        Page<List<OrderUserRelEntity>> page = new Page<List<OrderUserRelEntity>>(pageSize, pageNo, countSize);
        PageInterceptor.setPage(pageNo, pageSize);
        List<OrderUserRelEntity> result =  orderUserRelMapper.selectLikeList(orderUserRelEntity);
        page.setResult(result);
        return page;
    }
    @Override
    public OrderUserRelEntity getOrderUserRel(String id) {
        OrderUserRelEntity orderUserRelEntity = new OrderUserRelEntity();
        orderUserRelEntity.setId(id);
        return orderUserRelMapper.selectOne(orderUserRelEntity);
    }

    @Override
    public int addOrderUserRel(OrderUserRelEntity orderUserRelEntity) {
        orderUserRelEntity.setId(UUID.randomUUID().toString());
        return orderUserRelMapper.insert(orderUserRelEntity);
    }

    @Override
    public int addOrderUserRel(List<OrderUserRelEntity> orderUserRelEntitys) {
        int result = 0;
        for (OrderUserRelEntity entity : orderUserRelEntitys) {
            result += orderUserRelMapper.insert(entity);
        }
        return result;
    }

    @Override
    public int removeOrderUserRel(String id) {
        if(StringUtils.isEmpty(id)){
            throw new RuntimeException("删除操作时，主键不能为空！");
        }
        OrderUserRelEntity orderUserRelEntity = new OrderUserRelEntity();
        orderUserRelEntity.setId(id);
        return orderUserRelMapper.delete(orderUserRelEntity);
    }

    @Override
    public int removeOrderUserRel(List<String> ids) {
        int result = 0;
        for (String id : ids) {
            if(StringUtils.isEmpty(id)){
                throw new RuntimeException("删除操作时，主键不能为空！");
            }
            OrderUserRelEntity orderUserRelEntity = new OrderUserRelEntity();
            orderUserRelEntity.setId(id);
            result += orderUserRelMapper.delete(orderUserRelEntity);
        }
        return result;
    }

    @Override
    public int updateById(OrderUserRelEntity orderUserRelEntity, String id) {
        OrderUserRelEntity where = new OrderUserRelEntity();
        where.setId(id);
        return this.update(orderUserRelEntity, where);
    }

    @Override
    public int update(OrderUserRelEntity orderUserRelEntity, OrderUserRelEntity where) {
        orderUserRelEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return orderUserRelMapper.update(orderUserRelEntity, where);
    }
}
