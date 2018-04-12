package com.booking.common.dto;

import com.booking.common.entity.OrderEntity;
import com.booking.common.entity.OrderUserRelEntity;
import com.booking.common.entity.ShopEntity;
import com.booking.common.mapper.OrderMapper;
import com.opdar.plugins.mybatis.annotations.Collection;
import com.opdar.plugins.mybatis.annotations.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.List;

/**
 * OrderUserRelDto
 *
 * @author kai.liu
 * @date 2018/04/07
 */
public class OrderUserRelDto {

    private Timestamp updateTime;
    private String id;
    private Timestamp createTime;
    private String userId;
    private String orderId;
    private List<OrderEntity> orderList;
    private List<ShopEntity> shopList;

    public OrderUserRelDto() {
    }

    public OrderUserRelDto(OrderUserRelEntity orderUserRelEntity) {
        this.id = orderUserRelEntity.getId();
        this.updateTime = orderUserRelEntity.getUpdateTime();
        this.createTime = orderUserRelEntity.getCreateTime();
        this.userId = orderUserRelEntity.getUserId();
        this.orderId = orderUserRelEntity.getOrderId();
        this.orderList = orderUserRelEntity.getOrderList();
    }

    public List<ShopEntity> getShopList() {
        return shopList;
    }

    public void setShopList(List<ShopEntity> shopList) {
        this.shopList = shopList;
    }

    public List<OrderEntity> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderEntity> orderList) {
        this.orderList = orderList;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
