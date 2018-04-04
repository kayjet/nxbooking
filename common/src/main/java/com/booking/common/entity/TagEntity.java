package com.booking.common.entity;

import com.booking.common.mapper.ShopTagRelMapper;
import com.booking.common.mapper.TagMapper;
import com.opdar.plugins.mybatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

/**
* AuthorityEntity
*
* @author kai.liu
* @date 2017/12/28
*/
@Namespace(value= TagMapper.class)
public class TagEntity {
    private Timestamp updateTime;
    private String id;
    private String pic;
    private Timestamp createTime;
    private String title;
    private String remark;

    @Field(insert = false,update = false,delete = false,select = true)
    @Collection(mapper = ShopTagRelMapper.class,select = "selectJoinShopList",values = {
            @Value(key = "tagId",value = "id")
    })
    private List<ShopTagRelEntity> shopTagRelList;

    @Field(resultmap = false,insert = false,update = false,delete = false,select = false)
    private List<ShopEntity> requestAddShopList;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ShopTagRelEntity> getShopTagRelList() {
        return shopTagRelList;
    }

    public void setShopTagRelList(List<ShopTagRelEntity> shopTagRelList) {
        this.shopTagRelList = shopTagRelList;
    }

    public List<ShopEntity> getRequestAddShopList() {
        return requestAddShopList;
    }

    public void setRequestAddShopList(List<ShopEntity> requestAddShopList) {
        this.requestAddShopList = requestAddShopList;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
