package com.booking.common.service;

import com.booking.common.dto.ProductDto;
import com.booking.common.dto.ProductListDto;
import com.booking.common.entity.OrderEntity;
import com.booking.common.entity.ProductEntity;
import com.booking.common.resp.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * IOrderService
 *
 * @author kai.liu
 * @date 2017/12/28
 */
public interface IOrderService {

    List<OrderEntity> listAll();

    List<OrderEntity> listOrder(OrderEntity order);

    Page<List<OrderEntity>> listOrderPage(OrderEntity order, Integer pageNo, Integer pageSize);


    OrderEntity getOrder(String id);

    int addOrder(OrderEntity orderEntity);

    int addOrder(List<OrderEntity> orderEntity);

    @Transactional(rollbackFor = Throwable.class)
    int removeOrder(String id);

    @Transactional(rollbackFor = Throwable.class)
    int removeOrder(List<String> ids);

    int updateById(OrderEntity orderEntity, String id);

    int update(OrderEntity orderEntity, OrderEntity where);

    OrderEntity makeOrder(String shopId, String userId, String concatPhone, String totalPrice, String orderType,
                          String orderTime, List<List<ProductEntity>> products);

    ProductListDto getOrderProductList(String orderId);

    void updatePayStatus(String orderNo, String transactionId);
}
