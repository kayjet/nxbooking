package com.booking.common.entity;

import com.booking.common.mapper.OrderMapper;
import com.booking.common.mapper.OrderProductRelMapper;
import com.booking.common.mapper.ProductMapper;
import com.opdar.plugins.mybatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

/**
* AuthorityEntity
*
* @author kai.liu
* @date 2017/12/28
*/
@Namespace(value= OrderProductRelMapper.class)
public class OrderProductRelEntity {
    private Timestamp updateTime;
    private String id;
    private Timestamp createTime;
    private String orderId;
    private String productId;

    @Collection(mapper = OrderMapper.class,select = "selectList",values = {
            @Value(key = "id",value = "orderId")
    })
    private List<OrderEntity> orderList;

    @Collection(mapper = ProductMapper.class,select = "selectList",values = {
            @Value(key = "id",value = "productId")
    })
    private List<ProductEntity> productList;

    public OrderProductRelEntity() {
    }

    public List<OrderEntity> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderEntity> orderList) {
        this.orderList = orderList;
    }

    public List<ProductEntity> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductEntity> productList) {
        this.productList = productList;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }



}
