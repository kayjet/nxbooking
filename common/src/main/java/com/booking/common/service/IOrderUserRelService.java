package com.booking.common.service;

import com.booking.common.entity.OrderUserRelEntity;
import org.springframework.transaction.annotation.Transactional;
import com.booking.common.resp.Page;

import java.util.List;

/**
* IOrderUserRelService
*
* @author kai.liu
* @date 2017/12/28
*/
public interface IOrderUserRelService {

    List<OrderUserRelEntity> listAll();

    List<OrderUserRelEntity> listOrderUserRel(OrderUserRelEntity orderUserRel);

    Page<List<OrderUserRelEntity>> listOrderUserRelPage(OrderUserRelEntity orderUserRel, Integer pageNo, Integer pageSize);

    OrderUserRelEntity getOrderUserRel(String id);

    int addOrderUserRel(OrderUserRelEntity orderUserRelEntity);

    int addOrderUserRel(List<OrderUserRelEntity> orderUserRelEntity);

    @Transactional(rollbackFor = Throwable.class)
    int removeOrderUserRel(String id);

    @Transactional(rollbackFor = Throwable.class)
    int removeOrderUserRel(List<String> ids);

    int updateById(OrderUserRelEntity orderUserRelEntity, String id);

    int update(OrderUserRelEntity orderUserRelEntity, OrderUserRelEntity where);
}
