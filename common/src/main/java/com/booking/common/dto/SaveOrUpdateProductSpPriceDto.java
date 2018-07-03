package com.booking.common.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * SaveOrUpdateProductSpPriceDto
 *
 * @author kai.liu
 * @date 2018/07/03
 */
public class SaveOrUpdateProductSpPriceDto {
    private String shopId;
    private String tagId;
    private List<SaveOrUpdateProductSpPriceDtoItem> prices;

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

    public List<SaveOrUpdateProductSpPriceDtoItem> getPrices() {
        return prices;
    }

    public void setPrices(List<SaveOrUpdateProductSpPriceDtoItem> prices) {
        this.prices = prices;
    }

    public static class SaveOrUpdateProductSpPriceDtoItem {
        private Double spPrice;
        private String productId;

        public Double getSpPrice() {
            return spPrice;
        }

        public void setSpPrice(Double spPrice) {
            this.spPrice = spPrice;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }
    }
}
