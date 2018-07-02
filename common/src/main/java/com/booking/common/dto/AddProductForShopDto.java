package com.booking.common.dto;

import java.util.List;

/**
 * AddTagForShopDto
 *
 * @author kai.liu
 * @date 2018/07/02
 */
public class AddProductForShopDto {
    private List<String> productIds;
    private String shopId;
    private String tagId;

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
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
}
