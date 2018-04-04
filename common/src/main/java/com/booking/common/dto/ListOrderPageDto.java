package com.booking.common.dto;

import com.booking.common.entity.OrderEntity;

import java.util.List;

/**
 * ListOrderPageDto
 *
 * @author kai.liu
 * @date 2018/04/04
 */
public class ListOrderPageDto {
    private List<OrderEntity> orderList;
    private Double totalPrice;
}
