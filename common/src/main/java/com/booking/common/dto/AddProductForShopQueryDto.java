package com.booking.common.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AddProductForShopQueryDto
 *
 * @author kai.liu
 * @date 2018/07/03
 */
public class AddProductForShopQueryDto {
    private String shopId;
    private String tagId;
    private String title;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
