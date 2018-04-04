package com.booking.common.entity;

import com.booking.common.mapper.ShopMapper;
import com.booking.common.mapper.UserFavShopRelMapper;
import com.booking.common.mapper.UserMapper;
import com.opdar.plugins.mybatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * UserFavShopRelEntity
 *
 * @author kai.liu
 * @date 2017/12/28
 */
@Namespace(value = UserFavShopRelMapper.class)
public class UserFavShopRelEntity {
    private String id;
    private String fkUserId;
    private String fkShopId;
    private Timestamp createTime;
    private Timestamp updateTime;
    @Collection(
            mapper = ShopMapper.class,
            select = "selectList",
            values = {
                    @Value(key = "id", value = "fkShopId")
            }
    )
    private List<ShopEntity> shopList;
    @Collection(
            mapper = UserMapper.class,
            select = "selectList",
            values = {
                    @Value(key = "id", value = "fkUserId")
            }
    )
    private List<UserEntity> userList;

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

    public UserFavShopRelEntity() {
    }

    public UserFavShopRelEntity(String fkUserId, String fkShopId) {
        this.fkUserId = fkUserId;
        this.fkShopId = fkShopId;
    }

    public List<UserEntity> getUserList() {
        return userList;
    }

    public void setUserList(List<UserEntity> userList) {
        this.userList = userList;
    }

    public List<ShopEntity> getShopList() {
        return shopList;
    }

    public void setShopList(List<ShopEntity> shopList) {
        this.shopList = shopList;
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

    public String getFkShopId() {
        return fkShopId;
    }

    public void setFkShopId(String fkShopId) {
        this.fkShopId = fkShopId;
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

    public String getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(String fkUserId) {
        this.fkUserId = fkUserId;
    }


}
