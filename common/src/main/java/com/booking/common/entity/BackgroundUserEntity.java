package com.booking.common.entity;

import com.booking.common.mapper.BackgroundUserMapper;
import com.opdar.plugins.mybatis.annotations.Field;
import com.opdar.plugins.mybatis.annotations.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

/**
 * BackgroundUserEntity
 *
 * @author kai.liu
 * @date 2018/04/20
 */
@Namespace(BackgroundUserMapper.class)
public class BackgroundUserEntity {
    private String id;
    private String username;
    private String password;
    private String salt;
    private Timestamp createTime;
    private Timestamp updateTime;

    @Field(resultmap = false, insert = false, update = false, delete = false)
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
