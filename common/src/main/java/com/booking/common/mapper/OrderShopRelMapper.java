package com.booking.common.mapper;

import com.booking.common.entity.OrderEntity;
import com.booking.common.entity.OrderShopRelEntity;
import com.opdar.plugins.mybatis.core.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AuthorityMapper
 *
 * @author kai.liu
 * @date 2017/12/28
 */
public interface OrderShopRelMapper extends IBaseMapper<OrderShopRelEntity> {

    List<OrderEntity> selectOrderListPushedButNotHandled(@Param("shopId") String shopId);
}
