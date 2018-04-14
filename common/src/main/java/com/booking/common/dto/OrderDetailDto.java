package com.booking.common.dto;

import com.booking.common.entity.OrderDetailEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderDetailDto
 *
 * @author kai.liu
 * @date 2018/04/15
 */
public class OrderDetailDto {
    private String orderId;
    private String orderNo;
    private String orderProductRelId;
    private String productId;
    private String productName;
    private List<String> productSpecList = new ArrayList<String>();

    public OrderDetailDto() {
    }

    public OrderDetailDto(OrderDetailEntity orderDetailEntity) {
        this.orderId = orderDetailEntity.getOrderId();
        this.orderNo = orderDetailEntity.getOrderNo();
        this.orderProductRelId = orderDetailEntity.getOrderProductRelId();
        this.productId = orderDetailEntity.getProductId();
        this.productName = orderDetailEntity.getProductName();
        if (!StringUtils.isEmpty(orderDetailEntity.getSpecName())) {
            productSpecList.add(orderDetailEntity.getSpecName());
        }
    }

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<String> getProductSpecList() {
        return productSpecList;
    }

    public void setProductSpecList(List<String> productSpecList) {
        this.productSpecList = productSpecList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetailDto that = (OrderDetailDto) o;

        if (getOrderId() != null ? !getOrderId().equals(that.getOrderId()) : that.getOrderId() != null) return false;
        if (getOrderNo() != null ? !getOrderNo().equals(that.getOrderNo()) : that.getOrderNo() != null) return false;
        if (getOrderProductRelId() != null ? !getOrderProductRelId().equals(that.getOrderProductRelId()) : that.getOrderProductRelId() != null)
            return false;
        if (getProductId() != null ? !getProductId().equals(that.getProductId()) : that.getProductId() != null)
            return false;
        return getProductName() != null ? getProductName().equals(that.getProductName()) : that.getProductName() == null;
    }

    @Override
    public int hashCode() {
        int result = getOrderId() != null ? getOrderId().hashCode() : 0;
        result = 31 * result + (getOrderNo() != null ? getOrderNo().hashCode() : 0);
        result = 31 * result + (getOrderProductRelId() != null ? getOrderProductRelId().hashCode() : 0);
        result = 31 * result + (getProductId() != null ? getProductId().hashCode() : 0);
        result = 31 * result + (getProductName() != null ? getProductName().hashCode() : 0);
        return result;
    }
}
