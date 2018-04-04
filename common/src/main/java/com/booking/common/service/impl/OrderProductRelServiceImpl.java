package com.booking.common.service.impl;

import com.booking.common.entity.OrderProductRelEntity;
import com.booking.common.mapper.OrderProductRelMapper;
import com.booking.common.service.IOrderProductRelService;
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
* OrderProductRelServiceImpl
*
* @author kai.liu
* @date 2018/01/02
*/
@Service
public class OrderProductRelServiceImpl implements IOrderProductRelService {
    Logger logger = LoggerFactory.getLogger(OrderProductRelServiceImpl.class);

    @Autowired
    OrderProductRelMapper orderProductRelMapper;

    @Override
    public List<OrderProductRelEntity> listAll() {
        return orderProductRelMapper.selectList(null);
    }

    @Override
    public List<OrderProductRelEntity> listOrderProductRel(OrderProductRelEntity orderProductRelEntity) {
        return orderProductRelMapper.selectList(orderProductRelEntity);
    }

    @Override
    public Page<List<OrderProductRelEntity>> listOrderProductRelPage(OrderProductRelEntity orderProductRelEntity, Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Integer countSize =  orderProductRelMapper.likeCount(orderProductRelEntity);
        Page<List<OrderProductRelEntity>> page = new Page<List<OrderProductRelEntity>>(pageSize, pageNo, countSize);
        PageInterceptor.setPage(pageNo, pageSize);
        List<OrderProductRelEntity> result =  orderProductRelMapper.selectLikeList(orderProductRelEntity);
        page.setResult(result);
        return page;
    }
    @Override
    public OrderProductRelEntity getOrderProductRel(String id) {
        OrderProductRelEntity orderProductRelEntity = new OrderProductRelEntity();
        orderProductRelEntity.setId(id);
        return orderProductRelMapper.selectOne(orderProductRelEntity);
    }

    @Override
    public int addOrderProductRel(OrderProductRelEntity orderProductRelEntity) {
        orderProductRelEntity.setId(UUID.randomUUID().toString());
        return orderProductRelMapper.insert(orderProductRelEntity);
    }

    @Override
    public int addOrderProductRel(List<OrderProductRelEntity> orderProductRelEntitys) {
        int result = 0;
        for (OrderProductRelEntity entity : orderProductRelEntitys) {
            result += orderProductRelMapper.insert(entity);
        }
        return result;
    }

    @Override
    public int removeOrderProductRel(String id) {
        if(StringUtils.isEmpty(id)){
            throw new RuntimeException("删除操作时，主键不能为空！");
        }
        OrderProductRelEntity orderProductRelEntity = new OrderProductRelEntity();
        orderProductRelEntity.setId(id);
        return orderProductRelMapper.delete(orderProductRelEntity);
    }

    @Override
    public int removeOrderProductRel(List<String> ids) {
        int result = 0;
        for (String id : ids) {
            if(StringUtils.isEmpty(id)){
                throw new RuntimeException("删除操作时，主键不能为空！");
            }
            OrderProductRelEntity orderProductRelEntity = new OrderProductRelEntity();
            orderProductRelEntity.setId(id);
            result += orderProductRelMapper.delete(orderProductRelEntity);
        }
        return result;
    }

    @Override
    public int updateById(OrderProductRelEntity orderProductRelEntity, String id) {
        OrderProductRelEntity where = new OrderProductRelEntity();
        where.setId(id);
        return this.update(orderProductRelEntity, where);
    }

    @Override
    public int update(OrderProductRelEntity orderProductRelEntity, OrderProductRelEntity where) {
        orderProductRelEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return orderProductRelMapper.update(orderProductRelEntity, where);
    }
}
