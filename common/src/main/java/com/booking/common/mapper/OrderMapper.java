package com.booking.common.mapper;

import com.booking.common.entity.OrderEntity;
import com.opdar.plugins.mybatis.core.IBaseMapper;

/**
* AuthorityMapper
*
* @author kai.liu
* @date 2017/12/28
*/
public interface OrderMapper extends IBaseMapper<OrderEntity> {
    int updatePayStatusWithLock(OrderEntity order);

    int updatePushStatusWithLock(OrderEntity order);
}
