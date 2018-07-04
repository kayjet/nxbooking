package com.booking.common.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.booking.common.base.Constants;
import com.booking.common.dto.AddProductForShopQueryDto;
import com.booking.common.dto.ImportExcelProductDto;
import com.booking.common.entity.OrderEntity;
import com.booking.common.entity.ProductEntity;
import com.booking.common.entity.ProductSpecEntity;
import com.booking.common.entity.ProductSpecRelEntity;
import com.booking.common.mapper.ProductMapper;
import com.booking.common.mapper.ProductSpecRelMapper;
import com.booking.common.service.IProductService;
import com.booking.common.resp.Page;
import com.booking.common.resp.PageInterceptor;
import com.booking.common.utils.NetTool;
import org.apache.commons.fileupload.FileItem;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;


import java.io.*;
import java.sql.Timestamp;
import java.util.*;


/**
 * ProductServiceImpl
 *
 * @author kai.liu
 * @date 2018/01/02
 */
@Service
public class ProductServiceImpl implements IProductService {
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductSpecRelMapper productSpecRelMapper;

    @Override
    public List<ProductEntity> listAll() {
        return productMapper.selectList(null);
    }

    @Override
    public List<ProductEntity> listProduct(ProductEntity productEntity) {
        return productMapper.selectList(productEntity);
    }

    @Override
    public Page<List<ProductEntity>> listProductPage(ProductEntity productEntity, Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Integer countSize = productMapper.likeCount(productEntity);
        Page<List<ProductEntity>> page = new Page<List<ProductEntity>>(pageSize, pageNo, countSize);
        PageInterceptor.setPage(pageNo, pageSize);
        List<ProductEntity> result = null;
        if (productEntity != null && !StringUtils.isEmpty(productEntity.getTagId())) {
            result = productMapper.selectListByTagId(productEntity.getTagId());
        } else {
            result = productMapper.selectLikeList(productEntity);
        }
        selectSpec(result);
        page.setResult(result);
        return page;
    }

    @Override
    public Page<List<ProductEntity>> listProductPageForAdd(AddProductForShopQueryDto queryDto, Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Integer countSize = productMapper.countListForAddProduct(queryDto);
        Page<List<ProductEntity>> page = new Page<List<ProductEntity>>(pageSize, pageNo, countSize);
        PageInterceptor.setPage(pageNo, pageSize);
        List<ProductEntity> result = productMapper.selectListForAddProduct(queryDto);
        selectSpec(result);
        page.setResult(result);
        return page;
    }

    private void selectSpec(List<ProductEntity> result) {
        if (!CollectionUtils.isEmpty(result)) {
            for (ProductEntity product : result) {
                List<ProductSpecEntity> productSpecEntities = productSpecRelMapper.selectProductRelSpecList(product.getId());
                if (!CollectionUtils.isEmpty(productSpecEntities)) {
                    product.setProductSpecList(productSpecEntities);
                }
            }
        }
    }

    @Override
    public ProductEntity getProduct(String id) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id);
        return productMapper.selectOne(productEntity);
    }

    @Override
    public ProductEntity addProduct(ProductEntity productEntity) {
        productEntity.setId(UUID.randomUUID().toString());
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        productEntity.setCreateTime(ts);
        productEntity.setUpdateTime(ts);
        productMapper.insert(productEntity);
        return productEntity;
    }

    @Override
    public int addProduct(List<ProductEntity> productEntitys) {
        int result = 0;
        for (ProductEntity entity : productEntitys) {
            result += productMapper.insert(entity);
        }
        return result;
    }

    @Override
    public int removeProduct(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("删除操作时，主键不能为空！");
        }
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id);
        try {
            ProductSpecRelEntity relEntity = new ProductSpecRelEntity();
            relEntity.setPid(id);
            productSpecRelMapper.delete(relEntity);
        } catch (Exception e) {
        }
        return productMapper.delete(productEntity);
    }

    @Override
    public int removeProduct(List<String> ids) {
        int result = 0;
        for (String id : ids) {
            if (StringUtils.isEmpty(id)) {
                throw new RuntimeException("删除操作时，主键不能为空！");
            }
            ProductEntity productEntity = new ProductEntity();
            productEntity.setId(id);
            result += productMapper.delete(productEntity);
        }
        return result;
    }

    @Override
    public int updateById(ProductEntity productEntity, String id) {
        ProductEntity where = new ProductEntity();
        where.setId(id);
        return this.update(productEntity, where);
    }

    @Override
    public int update(ProductEntity productEntity, ProductEntity where) {
        productEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return productMapper.update(productEntity, where);
    }

    @Override
    public boolean importExcel(FileItem[] file) throws Exception {
        if (file.length > 0) {
            FileItem item = file[0];
            InputStream inputStream = item.getInputStream();
            ImportParams params = new ImportParams();
            List<ImportExcelProductDto> list = ExcelImportUtil.importExcel(inputStream, ImportExcelProductDto.class, params);
            for (ImportExcelProductDto excelProductDto : list) {
                ProductEntity productEntity = new ProductEntity();
                productEntity.setTitle(excelProductDto.getTitle().trim());
                productEntity = productMapper.selectOne(productEntity);
                if(productEntity==null){
                    productEntity = new ProductEntity();
                    productEntity.setTitle(excelProductDto.getTitle().trim());
                    productEntity.setDetail(excelProductDto.getDetail().trim());
                    productEntity.setPrice(excelProductDto.getPrice());
                    productEntity.setIsOnSale(Constants.ProductSaleStatus.ON_SALE);
                    this.addProduct(productEntity);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Workbook exportExcelTemplate() throws Exception {
        List<ImportExcelProductDto> list = new ArrayList<ImportExcelProductDto>();
        list.add(new ImportExcelProductDto(1.0D, "测试介绍", "标题而已"));
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(),
                ImportExcelProductDto.class, list);
        return workbook;
    }

}
