package com.booking.common.entity;

import com.booking.common.mapper.ProductMapper;
import com.booking.common.mapper.ProductSpecMapper;
import com.booking.common.mapper.ProductSpecRelMapper;
import com.opdar.plugins.mybatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * AuthorityEntity
 *
 * @author kai.liu
 * @date 2017/12/28
 */
@Namespace(value = ProductSpecRelMapper.class)
public class ProductSpecRelEntity {
    private String id;
    private String pid;
    private String specId;
    @Collection(mapper = ProductMapper.class, select = "selectList", values = {
            @Value(key = "id", value = "pid")
    })
    private List<ProductEntity> productList;
    @Collection(mapper = ProductSpecMapper.class, select = "selectList", values = {
            @Value(key = "id", value = "specId")
    })
    private List<ProductSpecEntity> specList;

    private Timestamp createTime;
    private Timestamp updateTime;

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

    public List<ProductEntity> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductEntity> productList) {
        this.productList = productList;
    }

    public List<ProductSpecEntity> getSpecList() {
        return specList;
    }

    public void setSpecList(List<ProductSpecEntity> specList) {
        this.specList = specList;
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }


}
