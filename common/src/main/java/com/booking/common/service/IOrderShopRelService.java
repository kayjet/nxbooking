package com.booking.common.service;

import com.booking.common.entity.OrderShopRelEntity;
import org.springframework.transaction.annotation.Transactional;
import com.booking.common.resp.Page;

import java.util.List;

/**
* IOrderShopRelService
*
* @author kai.liu
* @date 2017/12/28
*/
public interface IOrderShopRelService {

    List<OrderShopRelEntity> listAll();

    List<OrderShopRelEntity> listOrderShopRel(OrderShopRelEntity orderShopId);

    Page<List<OrderShopRelEntity>> listOrderShopRelPage(OrderShopRelEntity orderShopId, Integer pageNo, Integer pageSize);

    OrderShopRelEntity getOrderShopId(String id);

    int addOrderShopId(OrderShopRelEntity orderShopRelEntity);

    int addOrderShopId(List<OrderShopRelEntity> orderShopRelEntity);

    @Transactional(rollbackFor = Throwable.class)
    int removeOrderShopId(String id);

    @Transactional(rollbackFor = Throwable.class)
    int removeOrderShopId(List<String> ids);

    int updateById(OrderShopRelEntity orderShopRelEntity, String id);

    int update(OrderShopRelEntity orderShopRelEntity, OrderShopRelEntity where);
}
