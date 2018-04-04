package com.booking.common.service.impl;

import com.booking.common.resp.Page;
import com.booking.common.resp.PageInterceptor;
import com.booking.common.entity.ProductSpecRelEntity;
import com.booking.common.mapper.ProductSpecRelMapper;
import com.booking.common.service.IProductSpecRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


/**
* ProductSpecRelServiceImpl
*
* @author kai.liu
* @date 2018/01/02
*/
@Service
public class ProductSpecRelServiceImpl implements IProductSpecRelService {
    Logger logger = LoggerFactory.getLogger(ProductSpecRelServiceImpl.class);

    @Autowired
    ProductSpecRelMapper productSpecRelMapper;

    @Override
    public List<ProductSpecRelEntity> listAll() {
        return productSpecRelMapper.selectList(null);
    }

    @Override
    public List<ProductSpecRelEntity> listProductSpecRel(ProductSpecRelEntity productSpecRelEntity) {
        return productSpecRelMapper.selectList(productSpecRelEntity);
    }

    @Override
    public Page<List<ProductSpecRelEntity>> listProductSpecRelPage(ProductSpecRelEntity productSpecRelEntity, Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Integer countSize =  productSpecRelMapper.likeCount(productSpecRelEntity);
        Page<List<ProductSpecRelEntity>> page = new Page<List<ProductSpecRelEntity>>(pageSize, pageNo, countSize);
        PageInterceptor.setPage(pageNo, pageSize);
        List<ProductSpecRelEntity> result =  productSpecRelMapper.selectLikeList(productSpecRelEntity);
        page.setResult(result);
        return page;
    }
    @Override
    public ProductSpecRelEntity getProductSpecRel(String id) {
        ProductSpecRelEntity productSpecRelEntity = new ProductSpecRelEntity();
        productSpecRelEntity.setId(id);
        return productSpecRelMapper.selectOne(productSpecRelEntity);
    }

    @Override
    public int addProductSpecRel(ProductSpecRelEntity productSpecRelEntity) {
        productSpecRelEntity.setId(UUID.randomUUID().toString());
        return productSpecRelMapper.insert(productSpecRelEntity);
    }

    @Override
    public int addProductSpecRel(List<ProductSpecRelEntity> productSpecRelEntitys) {
        int result = 0;
        for (ProductSpecRelEntity entity : productSpecRelEntitys) {
            result += productSpecRelMapper.insert(entity);
        }
        return result;
    }

    @Override
    public int removeProductSpecRel(String id) {
        if(StringUtils.isEmpty(id)){
            throw new RuntimeException("删除操作时，主键不能为空！");
        }
        ProductSpecRelEntity productSpecRelEntity = new ProductSpecRelEntity();
        productSpecRelEntity.setId(id);
        return productSpecRelMapper.delete(productSpecRelEntity);
    }

    @Override
    public int removeProductSpecRel(List<String> ids) {
        int result = 0;
        for (String id : ids) {
            if(StringUtils.isEmpty(id)){
                throw new RuntimeException("删除操作时，主键不能为空！");
            }
            ProductSpecRelEntity productSpecRelEntity = new ProductSpecRelEntity();
            productSpecRelEntity.setId(id);
            result += productSpecRelMapper.delete(productSpecRelEntity);
        }
        return result;
    }

    @Override
    public int updateById(ProductSpecRelEntity productSpecRelEntity, String id) {
        ProductSpecRelEntity where = new ProductSpecRelEntity();
        where.setId(id);
        where.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return this.update(productSpecRelEntity, where);
    }

    @Override
    public int update(ProductSpecRelEntity productSpecRelEntity, ProductSpecRelEntity where) {
        return productSpecRelMapper.update(productSpecRelEntity, where);
    }
}
