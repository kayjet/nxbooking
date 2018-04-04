package com.booking.common.entity;

import com.booking.common.mapper.AdvertisementMapper;
import com.opdar.plugins.mybatis.annotations.*;

import java.sql.Timestamp;

/**
* AuthorityEntity
*
* @author kai.liu
* @date 2017/12/28
*/
@Namespace(value= AdvertisementMapper.class)
public class AdvertisementEntity {
    private Timestamp updateTime;
    private String id;
    private String pic;
    private String detail;
    private Timestamp createTime;

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }



}
