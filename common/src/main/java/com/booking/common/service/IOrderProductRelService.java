package com.booking.common.service;

import com.booking.common.entity.OrderProductRelEntity;
import org.springframework.transaction.annotation.Transactional;
import com.booking.common.resp.Page;

import java.util.List;

/**
* IOrderProductRelService
*
* @author kai.liu
* @date 2017/12/28
*/
public interface IOrderProductRelService {

    List<OrderProductRelEntity> listAll();

    List<OrderProductRelEntity> listOrderProductRel(OrderProductRelEntity orderProductRel);

    Page<List<OrderProductRelEntity>> listOrderProductRelPage(OrderProductRelEntity orderProductRel, Integer pageNo, Integer pageSize);

    OrderProductRelEntity getOrderProductRel(String id);

    int addOrderProductRel(OrderProductRelEntity orderProductRelEntity);

    int addOrderProductRel(List<OrderProductRelEntity> orderProductRelEntity);

    @Transactional(rollbackFor = Throwable.class)
    int removeOrderProductRel(String id);

    @Transactional(rollbackFor = Throwable.class)
    int removeOrderProductRel(List<String> ids);

    int updateById(OrderProductRelEntity orderProductRelEntity, String id);

    int update(OrderProductRelEntity orderProductRelEntity, OrderProductRelEntity where);
}
