package com.booking.common.service.impl;

import com.booking.common.entity.ProductPriceShopRelEntity;
import com.booking.common.mapper.ProductPriceShopRelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ProductDelegateService
 *
 * @author kai.liu
 * @date 2018/07/01
 */
@Service
public class ProductAdditionalService {
    private Logger logger = LoggerFactory.getLogger(ProductAdditionalService.class);

    @Autowired
    private ProductPriceShopRelMapper productPriceShopRelMapper;

    public int addProductSpPrice(String shopId, Double spPrice, String productId) {
        String uuid = UUID.randomUUID().toString();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ProductPriceShopRelEntity priceShopRelEntity = new ProductPriceShopRelEntity();
        priceShopRelEntity.setId(uuid);
        priceShopRelEntity.setShopId(shopId);
        priceShopRelEntity.setSpPrice(spPrice);
        priceShopRelEntity.setProductId(productId);
        priceShopRelEntity.setCreateTime(timestamp);
        priceShopRelEntity.setUpdateTime(timestamp);
        return productPriceShopRelMapper.insert(priceShopRelEntity);
    }

    public int changeProductSpPrice(String shopId, Double newSpPrice, String productId) {
        ProductPriceShopRelEntity priceShopRelEntity = new ProductPriceShopRelEntity();
        priceShopRelEntity.setProductId(productId);
        priceShopRelEntity.setShopId(shopId);
        ProductPriceShopRelEntity ret = productPriceShopRelMapper.selectOne(priceShopRelEntity);
        if (!ret.getSpPrice().equals(newSpPrice) && newSpPrice != null) {
            ret.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            ret.setSpPrice(newSpPrice);
            return productPriceShopRelMapper.update(ret, priceShopRelEntity);
        }
        return 0;
    }

    public int removeSpPrice(String shopId, String productId) {
        ProductPriceShopRelEntity priceShopRelEntity = new ProductPriceShopRelEntity();
        priceShopRelEntity.setProductId(productId);
        priceShopRelEntity.setShopId(shopId);
        return productPriceShopRelMapper.delete(priceShopRelEntity);
    }

    public Double findProductSpPrice(String shopId, String productId) {
        ProductPriceShopRelEntity priceShopRelEntity = new ProductPriceShopRelEntity();
        priceShopRelEntity.setProductId(productId);
        priceShopRelEntity.setShopId(shopId);
        ProductPriceShopRelEntity ret = productPriceShopRelMapper.selectOne(priceShopRelEntity);
        if (ret != null) {
            return ret.getSpPrice();
        }
        return 0D;
    }
}
