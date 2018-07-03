package com.booking.common.service.impl;

import com.booking.common.dto.SaveOrUpdateProductSpPriceDto;
import com.booking.common.entity.ProductPriceShopRelEntity;
import com.booking.common.entity.ShopTagRelEntity;
import com.booking.common.mapper.ProductPriceShopRelMapper;
import com.booking.common.mapper.ShopTagRelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    private ShopTagRelMapper shopTagRelMapper;

    public int saveOrUpdateProductSpPrice(SaveOrUpdateProductSpPriceDto saveOrUpdateProductSpPriceDto) {
        String shopTagRelId = null;
        ShopTagRelEntity query = new ShopTagRelEntity();
        String tagId = saveOrUpdateProductSpPriceDto.getTagId();
        String shopId = saveOrUpdateProductSpPriceDto.getShopId();
        query.setTagId(tagId);
        query.setShopId(shopId);
        query = shopTagRelMapper.selectOne(query);
        if (query != null) {
            shopTagRelId = query.getId();
        }

        if (StringUtils.isEmpty(shopTagRelId)) {
            return 0;
        }

        int result = 0;
        for (SaveOrUpdateProductSpPriceDto.SaveOrUpdateProductSpPriceDtoItem item : saveOrUpdateProductSpPriceDto.getPrices()) {
            ProductPriceShopRelEntity priceShopRelEntity = new ProductPriceShopRelEntity();
            priceShopRelEntity.setShopTagRelId(shopTagRelId);
            String productId = item.getProductId();
            Double spPrice = item.getSpPrice();
            priceShopRelEntity.setProductId(productId);
            priceShopRelEntity = productPriceShopRelMapper.selectOne(priceShopRelEntity);
            if (priceShopRelEntity != null && !priceShopRelEntity.equals(spPrice)) {
                priceShopRelEntity.setSpPrice(spPrice);
                result += productPriceShopRelMapper.update(priceShopRelEntity, new ProductPriceShopRelEntity(priceShopRelEntity.getId()));
            } else if (priceShopRelEntity == null) {
                String uuid = UUID.randomUUID().toString();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                priceShopRelEntity = new ProductPriceShopRelEntity();
                priceShopRelEntity.setId(uuid);
                priceShopRelEntity.setShopTagRelId(shopTagRelId);
                priceShopRelEntity.setSpPrice(spPrice);
                priceShopRelEntity.setProductId(productId);
                priceShopRelEntity.setCreateTime(timestamp);
                priceShopRelEntity.setUpdateTime(timestamp);
                result += productPriceShopRelMapper.insert(priceShopRelEntity);
            }
        }
        return result;
    }

    public int removeSpPrice(String shopId, String productId, String tagId) {
        String shopTagRelId = null;
        ShopTagRelEntity query = new ShopTagRelEntity();
        query.setTagId(tagId);
        query.setShopId(shopId);
        query = shopTagRelMapper.selectOne(query);
        if (query != null) {
            shopTagRelId = query.getId();
        }
        if (StringUtils.isEmpty(shopTagRelId)) {
            return 0;
        }
        return this.removeSpPrice(productId,shopTagRelId);
    }

    public int removeSpPrice(String productId, String shopTagReId) {
        ProductPriceShopRelEntity delete = new ProductPriceShopRelEntity();
        delete.setProductId(productId);
        delete.setShopTagRelId(shopTagReId);
        return productPriceShopRelMapper.delete(delete);
    }

    public Double findProductSpPrice(String shopId, String productId, String tagId) {
        String shopTagRelId = null;
        ShopTagRelEntity query = new ShopTagRelEntity();
        query.setTagId(tagId);
        query.setShopId(shopId);
        query = shopTagRelMapper.selectOne(query);
        if (query != null) {
            shopTagRelId = query.getId();
        }
        if (StringUtils.isEmpty(shopTagRelId)) {
            return 0D;
        }
        ProductPriceShopRelEntity priceShopRelEntity = new ProductPriceShopRelEntity();
        priceShopRelEntity.setProductId(productId);
        priceShopRelEntity.setShopTagRelId(shopTagRelId);
        ProductPriceShopRelEntity ret = productPriceShopRelMapper.selectOne(priceShopRelEntity);
        if (ret != null) {
            return ret.getSpPrice();
        }
        return 0D;
    }
}
