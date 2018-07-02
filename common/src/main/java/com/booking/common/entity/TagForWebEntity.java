package com.booking.common.entity;

import com.booking.common.mapper.ShopTagRelMapper;
import com.booking.common.mapper.TagForWebMapper;
import com.booking.common.mapper.TagMapper;
import com.booking.common.mapper.TagProductRelMapper;
import com.opdar.plugins.mybatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * AuthorityEntity
 *
 * @author kai.liu
 * @date 2017/12/28
 */
@Namespace(value = TagForWebMapper.class, tableName = "tag")
public class TagForWebEntity {
    private String id;
    private String pic;
    private String title;
    private String remark;
    private Timestamp createTime;
    private Timestamp updateTime;

    @Field(insert = false, update = false, delete = false, select = true)
    @Collection(mapper = ShopTagRelMapper.class, select = "selectProductByTag", values = {
            @Value(key = "tagId", value = "id")
    })
    private List<ProductEntity> productList;


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ProductEntity> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductEntity> productList) {
        this.productList = productList;
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
