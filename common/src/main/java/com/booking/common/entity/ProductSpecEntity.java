package com.booking.common.entity;

import com.booking.common.mapper.ProductSpecMapper;
import com.opdar.plugins.mybatis.annotations.*;

import java.sql.Timestamp;

/**
* AuthorityEntity
*
* @author kai.liu
* @date 2017/12/28
*/
@Namespace(value= ProductSpecMapper.class)
public class ProductSpecEntity {
    private Timestamp updateTime;
    private String code;
    private Timestamp createTime;
    private String name;
    private String parentCode;
    private String id;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
