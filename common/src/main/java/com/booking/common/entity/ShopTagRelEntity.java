package com.booking.common.entity;

import com.booking.common.mapper.ShopTagRelMapper;
import com.opdar.plugins.mybatis.annotations.*;

import java.sql.Timestamp;

/**
* AuthorityEntity
*
* @author kai.liu
* @date 2017/12/28
*/
@Namespace(value= ShopTagRelMapper.class)
public class ShopTagRelEntity {
    private String id;
    private String shopId;
    private String tagId;
    private Timestamp updateTime;
    private Timestamp createTime;

    private String shopName;
    private String tagName;


    @Field(resultmap = false, insert = false, update = false, delete = false)
    @Where(value = " and create_time >= {}", plain = false)
    private Timestamp createTimeStart;
    @Field(resultmap = false, insert = false, update = false, delete = false)
    @Where(value = " and create_time <= {}", plain = false)
    private Timestamp createTimeEnd;

    @Field(resultmap = false, insert = false, update = false, delete = false)
    @Where(value = " and update_time >= {}", plain = false)
    private Timestamp updateTimeStart;

    @Field(resultmap = false, insert = false, update = false, delete = false)
    @Where(value = " and update_time <= {}", plain = false)
    private Timestamp updateTimeEnd;

    @Field(resultmap = false, insert = false, update = false, delete = false, select = false)
    private String createTimeSearch;

    @Field(resultmap = false, insert = false, update = false, delete = false, select = false)
    private String updateTimeSearch;


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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
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


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
