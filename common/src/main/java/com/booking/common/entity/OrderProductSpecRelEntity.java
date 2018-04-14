package com.booking.common.entity;

import com.booking.common.mapper.OrderProductSpecRelMapper;
import com.opdar.plugins.mybatis.annotations.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

/**
 * OrderProductSpecRelEntity
 *
 * @author kai.liu
 * @date 2018/04/14
 */
@Namespace(OrderProductSpecRelMapper.class)
public class OrderProductSpecRelEntity {
    private String id;
    private String orderProductRelId;
    private String specId;
    private Timestamp createTime;
    private Timestamp updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderProductRelId() {
        return orderProductRelId;
    }

    public void setOrderProductRelId(String orderProductRelId) {
        this.orderProductRelId = orderProductRelId;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }


    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

}
