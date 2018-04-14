package com.booking.common.mapper;

import com.booking.common.entity.OrderDetailEntity;
import com.booking.common.entity.OrderEntity;
import com.opdar.plugins.mybatis.core.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* AuthorityMapper
*
* @author kai.liu
* @date 2017/12/28
*/
public interface OrderMapper extends IBaseMapper<OrderEntity> {
    int updatePayStatusWithLock(OrderEntity order);

    int updatePushStatusWithLock(OrderEntity order);

    List<OrderDetailEntity> selectOrderDetailList(@Param("orderNo") String orderNo);

}
