package com.booking.common.entity;

import com.booking.common.mapper.ProductDelMapper;
import com.opdar.plugins.mybatis.annotations.*;

import java.sql.Timestamp;

/**
 * AuthorityEntity
 *
 * @author kai.liu
 * @date 2017/12/28
 */
@Namespace(value = ProductDelMapper.class)
public class ProductDelEntity {
    @Sort
    @Id
    private String id;
    private Double price;
    private String pic;
    private String detail;
    private String title;
    private Integer isOnSale;
    private Timestamp updateTime;
    private Timestamp createTime;
    private String delTime;

    public ProductDelEntity() {
    }

    public ProductDelEntity(ProductEntity productEntity) {
        this.id = productEntity.getId();
        this.price = productEntity.getPrice();
        this.pic = productEntity.getPic();
        this.detail = productEntity.getDetail();
        this.title = productEntity.getTitle();
        this.isOnSale = productEntity.getIsOnSale();
        this.updateTime = productEntity.getUpdateTime();
        this.createTime = productEntity.getCreateTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Integer isOnSale) {
        this.isOnSale = isOnSale;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getDelTime() {
        return delTime;
    }

    public void setDelTime(String delTime) {
        this.delTime = delTime;
    }
}
