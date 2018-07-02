package com.booking.common.entity;

import com.booking.common.dto.ProductSpecDto;
import com.booking.common.mapper.ProductMapper;
import com.booking.common.mapper.TagProductRelMapper;
import com.opdar.plugins.mybatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

/**
* AuthorityEntity
*
* @author kai.liu
* @date 2017/12/28
*/
@Namespace(value= ProductMapper.class)
public class ProductEntity {
    @Sort
    private String id;
    private Double price;
    private String pic;
    private String detail;
    private String title;
    private Integer isOnSale;
    private Timestamp updateTime;
    private Timestamp createTime;
    @Field(insert = false,update = false,delete = false,select = true)
    @Collection(mapper = TagProductRelMapper.class,select = "selectJoinTagList",values = {
            @Value(key = "pid",value = "id")
    })
    private List<TagProductRelEntity> tagProductRelList;


    @Field(resultmap = false,insert = false,update = false,delete = false,select = false)
    private List<ShopTagRelEntity> requestAddTagList;

    //用于修改数据
    @Field(resultmap = false,insert = false,update = false,delete = false,select = false)
    private List<ProductSpecEntity> productSpecList;

    //用于返回给前段数据
    @Field(resultmap = false,insert = false,update = false,delete = false,select = false)
    private List<ProductSpecDto> relSpecList;

    //用于前段数据请求
    @Field(resultmap = false,insert = false,update = false,delete = false,select = false)
    private List<ProductSpecDto> requestSpecList;


    //用于前段数据请求
    @Field(resultmap = false,insert = false,update = false,delete = false,select = false)
    private String tagId;

    //用于前段数据请求
    //每个门店的自定义价格
    @Field(resultmap = false,insert = false,update = false,delete = false,select = false)
    private Double spPrice;

    //用于前段数据请求
    //每个门店id
    @Field(resultmap = false,insert = false,update = false,delete = false,select = false)
    private String shopId;

    @Field(resultmap = false,insert = false,update = false,delete = false)
    @Where(value = " and create_time >= {}",plain = false)
    private Timestamp createTimeStart;
    @Field(resultmap = false,insert = false,update = false,delete = false)
    @Where(value = " and create_time <= {}",plain = false)
    private Timestamp createTimeEnd;

    @Field(resultmap = false,insert = false,update = false,delete = false)
    @Where(value = " and update_time >= {}",plain = false)
    private Timestamp updateTimeStart;

    @Field(resultmap = false,insert = false,update = false,delete = false)
    @Where(value = " and update_time <= {}",plain = false)
    private Timestamp updateTimeEnd;

    @Field(resultmap = false,insert = false,update = false,delete = false,select = false)
    private String createTimeSearch;

    @Field(resultmap = false,insert = false,update = false,delete = false,select = false)
    private String updateTimeSearch;

    public List<ProductSpecEntity> getProductSpecList() {
        return productSpecList;
    }

    public void setProductSpecList(List<ProductSpecEntity> productSpecList) {
        this.productSpecList = productSpecList;
    }

    public Integer getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Integer isOnSale) {
        this.isOnSale = isOnSale;
    }

    public ProductEntity() {
    }

    public ProductEntity(String id) {
        this.id = id;
    }

    public List<TagProductRelEntity> getTagProductRelList() {
        return tagProductRelList;
    }

    public void setTagProductRelList(List<TagProductRelEntity> tagProductRelList) {
        this.tagProductRelList = tagProductRelList;
    }

    public List<ShopTagRelEntity> getRequestAddTagList() {
        return requestAddTagList;
    }

    public void setRequestAddTagList(List<ShopTagRelEntity> requestAddTagList) {
        this.requestAddTagList = requestAddTagList;
    }


    public Timestamp getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Timestamp createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Timestamp getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Timestamp createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public Timestamp getUpdateTimeStart() {
        return updateTimeStart;
    }

    public void setUpdateTimeStart(Timestamp updateTimeStart) {
        this.updateTimeStart = updateTimeStart;
    }

    public Timestamp getUpdateTimeEnd() {
        return updateTimeEnd;
    }

    public void setUpdateTimeEnd(Timestamp updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }

    public String getCreateTimeSearch() {
        return createTimeSearch;
    }

    public void setCreateTimeSearch(String createTimeSearch) {
        this.createTimeSearch = createTimeSearch;
    }

    public String getUpdateTimeSearch() {
        return updateTimeSearch;
    }

    public void setUpdateTimeSearch(String updateTimeSearch) {
        this.updateTimeSearch = updateTimeSearch;
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

    public List<ProductSpecDto> getRelSpecList() {
        return relSpecList;
    }

    public void setRelSpecList(List<ProductSpecDto> relSpecList) {
        this.relSpecList = relSpecList;
    }

    public List<ProductSpecDto> getRequestSpecList() {
        return requestSpecList;
    }

    public void setRequestSpecList(List<ProductSpecDto> requestSpecList) {
        this.requestSpecList = requestSpecList;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public Double getSpPrice() {
        return spPrice;
    }

    public void setSpPrice(Double spPrice) {
        this.spPrice = spPrice;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
