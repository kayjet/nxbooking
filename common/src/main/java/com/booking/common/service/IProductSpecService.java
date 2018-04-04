package com.booking.common.service;

import com.booking.common.resp.Page;
import com.booking.common.entity.ProductSpecEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* IProductSpecService
*
* @author kai.liu
* @date 2017/12/28
*/
public interface IProductSpecService {

    List<ProductSpecEntity> listAll();

    List<ProductSpecEntity> listProductSpec(ProductSpecEntity productSpec);

    Page<List<ProductSpecEntity>> listProductSpecPage(ProductSpecEntity productSpec, Integer pageNo, Integer pageSize);

    ProductSpecEntity getProductSpec(String id);

    int addProductSpec(ProductSpecEntity productSpecEntity);

    int addProductSpec(List<ProductSpecEntity> productSpecEntity);

    @Transactional(rollbackFor = Throwable.class)
    int removeProductSpec(String id);

    @Transactional(rollbackFor = Throwable.class)
    int removeProductSpec(List<String> ids);

    int updateById(ProductSpecEntity productSpecEntity, String id);

    int update(ProductSpecEntity productSpecEntity, ProductSpecEntity where);
}
