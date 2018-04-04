package com.booking.common.dto;

import com.booking.common.entity.ProductEntity;

import java.sql.Timestamp;

/**
 * ProductDto
 *
 * @author kai.liu
 * @date 2018/04/04
 */
public class ProductDto {
    private Timestamp updateTime;
    private Timestamp createTime;
    private Double price;
    private String id;
    private String pic;
    private String detail;
    private String title;
    private Integer sum;

    public ProductDto() {
    }

    public ProductDto(ProductEntity productEntity) {
        this.updateTime = productEntity.getUpdateTime();
        this.createTime = productEntity.getCreateTime();
        this.price  = productEntity.getPrice();
        this.id  = productEntity.getId();
        this.pic  = productEntity.getPic();
        this.detail  = productEntity.getDetail();
        this.title  = productEntity.getTitle();
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
