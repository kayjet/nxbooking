package com.booking.common.service;

import com.booking.common.dto.AddProductForShopQueryDto;
import com.booking.common.entity.ProductEntity;
import com.booking.common.resp.Page;
import org.apache.commons.fileupload.FileItem;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
* IProductService
*
* @author kai.liu
* @date 2017/12/28
*/
public interface IProductService {

    List<ProductEntity> listAll();

    List<ProductEntity> listProduct(ProductEntity product);

    Page<List<ProductEntity>> listProductPage(ProductEntity product, Integer pageNo, Integer pageSize);

    Page<List<ProductEntity>> listProductPageForAdd(AddProductForShopQueryDto queryDto, Integer pageNo, Integer pageSize);

    ProductEntity getProduct(String id);

    @Transactional(rollbackFor = Throwable.class)
    ProductEntity addProduct(ProductEntity productEntity);

    int addProduct(List<ProductEntity> productEntity);

    @Transactional(rollbackFor = Throwable.class)
    int removeProduct(String id);

    @Transactional(rollbackFor = Throwable.class)
    int removeProduct(List<String> ids);

    int updateById(ProductEntity productEntity, String id);

    int update(ProductEntity productEntity, ProductEntity where);

    boolean importExcel(FileItem[] file) throws Exception;

    Workbook exportExcelTemplate() throws Exception;
}
