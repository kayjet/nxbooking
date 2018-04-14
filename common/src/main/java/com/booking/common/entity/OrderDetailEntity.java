package com.booking.common.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OrderDetailEntity
 *
 * @author kai.liu
 * @date 2018/04/14
 */
public class OrderDetailEntity {

    private String orderId;
    private String orderNo;
    private String orderProductRelId;
    private String productName;
    private String productId;
    private String specName;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getOrderProductRelId() {
        return orderProductRelId;
    }

    public void setOrderProductRelId(String orderProductRelId) {
        this.orderProductRelId = orderProductRelId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
