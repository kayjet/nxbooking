package com.booking.common.service.impl;

import com.booking.common.base.Constants;
import com.booking.common.resp.Page;
import com.booking.common.resp.PageInterceptor;
import com.booking.common.entity.ProductSpecEntity;
import com.booking.common.mapper.ProductSpecMapper;
import com.booking.common.service.IProductSpecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


/**
 * ProductSpecServiceImpl
 *
 * @author kai.liu
 * @date 2018/01/02
 */
@Service
public class ProductSpecServiceImpl implements IProductSpecService {
    Logger logger = LoggerFactory.getLogger(ProductSpecServiceImpl.class);

    @Autowired
    ProductSpecMapper productSpecMapper;

    @Override
    public List<ProductSpecEntity> listAll() {
        return productSpecMapper.selectList(null);
    }

    @Override
    public List<ProductSpecEntity> listProductSpec(ProductSpecEntity productSpecEntity) {
        return productSpecMapper.selectList(productSpecEntity);
    }

    @Override
    public List<ProductSpecEntity> listAllParent() {
        return productSpecMapper.selectParentSpec();
    }

    @Override
    public Page<List<ProductSpecEntity>> listProductSpecPage(ProductSpecEntity productSpecEntity, Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Integer countSize = productSpecMapper.likeCount(productSpecEntity);
        Page<List<ProductSpecEntity>> page = new Page<List<ProductSpecEntity>>(pageSize, pageNo, countSize);
        PageInterceptor.setPage(pageNo, pageSize);
        List<ProductSpecEntity> result = productSpecMapper.selectLikeList(productSpecEntity);
        page.setResult(result);
        return page;
    }

    @Override
    public ProductSpecEntity getProductSpec(String id) {
        ProductSpecEntity productSpecEntity = new ProductSpecEntity();
        productSpecEntity.setId(id);
        return productSpecMapper.selectOne(productSpecEntity);
    }

    public static void main(String[] args) {
        System.out.println(Integer.valueOf("02"));
    }

    @Override
    public int addProductSpec(ProductSpecEntity productSpecEntity) {
        productSpecEntity.setId(UUID.randomUUID().toString());
        if (!StringUtils.isEmpty(productSpecEntity.getIsParentCode())) {
            if (productSpecEntity.getIsParentCode().equals(Constants.IsParentCode.YES)) {
                String maxCode = productSpecMapper.selectMaxParentCode();
                Integer max = Integer.valueOf(maxCode);
                max = max + 1;
                if (max < 10) {
                    productSpecEntity.setCode("0" + max);
                } else {
                    productSpecEntity.setCode("" + max);
                }
                productSpecEntity.setParentCode(null);
            } else {
                String parentCode = productSpecEntity.getParentCode();
                String maxChildCode = productSpecMapper.selectMaxChildCode(parentCode);
                if (StringUtils.isEmpty(maxChildCode)) {
                    maxChildCode = "00";
                }
                Integer max = Integer.valueOf(maxChildCode);
                max = max + 1;
                if (max < 10) {
                    productSpecEntity.setCode(parentCode + "0" + max);
                } else {
                    if (Integer.valueOf(parentCode) < 10) {
                        productSpecEntity.setCode("0" + max);
                    } else {
                        productSpecEntity.setCode("" + max);
                    }
                }
            }
        }
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        productSpecEntity.setCreateTime(ts);
        productSpecEntity.setUpdateTime(ts);
        return productSpecMapper.insert(productSpecEntity);
    }

    @Override
    public int addProductSpec(List<ProductSpecEntity> productSpecEntitys) {
        int result = 0;
        for (ProductSpecEntity entity : productSpecEntitys) {
            result += productSpecMapper.insert(entity);
        }
        return result;
    }

    @Override
    public int removeProductSpec(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("删除操作时，主键不能为空！");
        }
        ProductSpecEntity productSpecEntity = new ProductSpecEntity();
        productSpecEntity.setId(id);
        return productSpecMapper.delete(productSpecEntity);
    }

    @Override
    public int removeProductSpec(List<String> ids) {
        int result = 0;
        for (String id : ids) {
            if (StringUtils.isEmpty(id)) {
                throw new RuntimeException("删除操作时，主键不能为空！");
            }
            ProductSpecEntity productSpecEntity = new ProductSpecEntity();
            productSpecEntity.setId(id);
            result += productSpecMapper.delete(productSpecEntity);
        }
        return result;
    }

    @Override
    public int updateById(ProductSpecEntity productSpecEntity, String id) {
        ProductSpecEntity where = new ProductSpecEntity();
        where.setId(id);
        return this.update(productSpecEntity, where);
    }

    @Override
    public int update(ProductSpecEntity productSpecEntity, ProductSpecEntity where) {
        productSpecEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return productSpecMapper.update(productSpecEntity, where);
    }
}
