package com.booking.common.dto;

import java.util.List;

/**
 * AddTagForShopDto
 *
 * @author kai.liu
 * @date 2018/07/02
 */
public class AddProductForShopDto {
    private List<String> productId;
    private String shopId;
    private String tagId;

    public List<String> getProductId() {
        return productId;
    }

    public void setProductId(List<String> productId) {
        this.productId = productId;
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
