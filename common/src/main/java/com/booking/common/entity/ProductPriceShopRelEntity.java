package com.booking.common.entity;

import com.booking.common.mapper.ProductPriceShopRelMapper;
import com.opdar.plugins.mybatis.annotations.Namespace;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ProductPriceShopRelEntity
 *
 * @author kai.liu
 * @date 2018/07/01
 */
@Namespace(value = ProductPriceShopRelMapper.class)
public class ProductPriceShopRelEntity  implements Serializable {
    private String id;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Double spPrice;
    private String shopTagRelId;
    private String productId;

    public ProductPriceShopRelEntity() {
    }

    public ProductPriceShopRelEntity(String id) {
        this.id = id;
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

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getSpPrice() {
        return spPrice;
    }

    public void setSpPrice(Double spPrice) {
        this.spPrice = spPrice;
    }

    public String getShopTagRelId() {
        return shopTagRelId;
    }

    public void setShopTagRelId(String shopTagRelId) {
        this.shopTagRelId = shopTagRelId;
    }
}
