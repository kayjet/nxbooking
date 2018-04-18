package com.booking.common.entity;

import com.booking.common.mapper.ShopMapper;
import com.opdar.plugins.mybatis.annotations.*;

import java.sql.Timestamp;

/**
 * ShopEntity
 *
 * @author kai.liu
 * @date 2017/12/28
 */
@Namespace(value = ShopMapper.class)
public class ShopEntity {
    private String id;
    private String name;
    private String address;
    private Double lat;
    private Double lng;
    //费率%
    private Double payRate;
    private String openTime;
    private String closeTime;

    private Timestamp updateTime;
    private Timestamp createTime;

    public ShopEntity() {
    }

    public ShopEntity(String id) {
        this.id = id;
    }

    @Field(insert = false, update = false, delete = false)
    private Double distance;
    @Field(insert = false, update = false, delete = false,select = false)
    private Boolean open = true;

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

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public Double getPayRate() {
        return payRate;
    }

    public void setPayRate(Double payRate) {
        this.payRate = payRate;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }


}
