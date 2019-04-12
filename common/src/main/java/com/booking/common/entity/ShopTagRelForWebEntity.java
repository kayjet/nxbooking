package com.booking.common.entity;

import com.booking.common.mapper.*;
import com.opdar.plugins.mybatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

/**
* AuthorityEntity
*
* @author kai.liu
* @date 2017/12/28
*/
@Namespace(value= ShopTagRelWebMapper.class,tableName = "shop_tag_rel")
public class ShopTagRelForWebEntity {
    private String id;
    private String shopId;
    private String tagId;

    //查出该tag下的所有产品
    @Collection(mapper = TagForWebMapper.class,select = "selectList",values = {
            @Value(key = "id",value = "tagId")
    })
    private List<TagForWebEntity> tagList;

    public List<TagForWebEntity> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagForWebEntity> tagList) {
        this.tagList = tagList;
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
