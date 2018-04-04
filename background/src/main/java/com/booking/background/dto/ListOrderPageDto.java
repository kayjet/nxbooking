package com.booking.background.dto;

import com.booking.common.entity.OrderEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
