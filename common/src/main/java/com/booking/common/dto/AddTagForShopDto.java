package com.booking.common.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * AddTagForShopDto
 *
 * @author kai.liu
 * @date 2018/07/02
 */
public class AddTagForShopDto {
    private List<String> tagIds;
    private String shopId;

    public List<String> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<String> tagIds) {
        this.tagIds = tagIds;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
