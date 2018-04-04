package com.booking.common.service;

import com.booking.common.resp.Page;
import com.booking.common.entity.ProductSpecRelEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* IProductSpecRelService
*
* @author kai.liu
* @date 2017/12/28
*/
public interface IProductSpecRelService {

    List<ProductSpecRelEntity> listAll();

    List<ProductSpecRelEntity> listProductSpecRel(ProductSpecRelEntity productSpecRel);

    Page<List<ProductSpecRelEntity>> listProductSpecRelPage(ProductSpecRelEntity productSpecRel, Integer pageNo, Integer pageSize);

    ProductSpecRelEntity getProductSpecRel(String id);

    int addProductSpecRel(ProductSpecRelEntity productSpecRelEntity);

    int addProductSpecRel(List<ProductSpecRelEntity> productSpecRelEntity);

    @Transactional(rollbackFor = Throwable.class)
    int removeProductSpecRel(String id);

    @Transactional(rollbackFor = Throwable.class)
    int removeProductSpecRel(List<String> ids);

    int updateById(ProductSpecRelEntity productSpecRelEntity, String id);

    int update(ProductSpecRelEntity productSpecRelEntity, ProductSpecRelEntity where);
}
