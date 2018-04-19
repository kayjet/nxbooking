package com.booking.common.entity;

import com.booking.common.mapper.OrderMapper;
import com.booking.common.mapper.OrderShopRelMapper;
import com.booking.common.mapper.ShopMapper;
import com.opdar.plugins.mybatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

/**
* AuthorityEntity
*
* @author kai.liu
* @date 2017/12/28
*/
@Namespace(value= OrderShopRelMapper.class)
public class OrderShopRelEntity {
    private String shopId;
    private Timestamp updateTime;
    private String id;
    @Sort(type = Sort.SortType.DESC)
    private Timestamp createTime;
    private String orderId;
    @Collection(mapper = OrderMapper.class,select = "selectList",values = {
            @Value(key = "id",value = "orderId")
    })
    private List<OrderEntity> orderList;

    @Collection(mapper = ShopMapper.class,select = "selectList",values = {
            @Value(key = "id",value = "shopId")
    })
    private List<ShopEntity> shopList;

    public List<ShopEntity> getShopList() {
        return shopList;
    }

    public void setShopList(List<ShopEntity> shopList) {
        this.shopList = shopList;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<OrderEntity> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderEntity> orderList) {
        this.orderList = orderList;
    }
}
