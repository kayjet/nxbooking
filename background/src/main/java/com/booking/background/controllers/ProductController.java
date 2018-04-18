package com.booking.background.controllers;

import com.booking.background.interceptor.LoginSessionInterceptor;
import com.booking.common.entity.*;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.interceptor.TimeQueryInterceptor;
import com.booking.common.resp.Page;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.IProductService;
import com.booking.common.service.IProductSpecRelService;
import com.booking.common.service.ITagProductRelService;
import com.booking.common.service.ITagService;
import com.opdar.platform.core.base.Context;
import com.opdar.platform.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ProductController
 *
 * @author kai.liu
 * @date 2017/12/29
 */
@Controller
@ErrorHandler(ErrCodeHandler.class)
@Interceptor({TimeQueryInterceptor.class,LoginSessionInterceptor.class})
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    IProductService productService;

    @Autowired
    ITagProductRelService tagProductRelService;

    @Autowired
    IProductSpecRelService productSpecRelService;

    @Request(value = "/product/add")
    @Editor(ResultEditor.class)
    public String addProduct(@JSON ProductEntity product) {
        ProductEntity productRet = productService.addProduct(product);
        if (!CollectionUtils.isEmpty(product.getRequestAddTagList())) {
            List<TagProductRelEntity> tagProductRelEntities = new ArrayList<TagProductRelEntity>();
            for (ShopTagRelEntity tagEntity : product.getRequestAddTagList()) {
                TagProductRelEntity tagProductRelEntity = new TagProductRelEntity();
                tagProductRelEntity.setPid(productRet.getId());
                tagProductRelEntity.setTid(tagEntity.getTagId());
                tagProductRelEntities.add(tagProductRelEntity);
            }
            tagProductRelService.addTagProductRel(tagProductRelEntities);
        }
        if (!CollectionUtils.isEmpty(product.getProductSpecList())) {
            List<ProductSpecRelEntity> productSpecRelEntities = new ArrayList<ProductSpecRelEntity>();
            for (ProductSpecEntity specEntity : product.getProductSpecList()) {
                ProductSpecRelEntity tagProductRelEntity = new ProductSpecRelEntity();
                tagProductRelEntity.setPid(productRet.getId());
                tagProductRelEntity.setSpecId(specEntity.getId());
                productSpecRelEntities.add(tagProductRelEntity);
            }
            productSpecRelService.addProductSpecRel(productSpecRelEntities);
        }
        return productRet.getId();
    }

    @Request(value = "/product/remove")
    @Editor(ResultEditor.class)
    public int removeProduct(String id) {
        return productService.removeProduct(id);
    }

    @Request(value = "/product/removeList")
    @Editor(ResultEditor.class)
    public int removeProductList(@JSON List<String> ids) {
        return productService.removeProduct(ids);
    }

    @Request(value = "/product/update")
    @Editor(ResultEditor.class)
    public int updateProduct(@JSON ProductEntity product) {
        int ret = productService.updateById(product, product.getId());
        TagProductRelEntity query = new TagProductRelEntity();
        query.setPid(product.getId());
        List<TagProductRelEntity> result = tagProductRelService.listTagProductRel(query);
        if (CollectionUtils.isEmpty(result) && !CollectionUtils.isEmpty(product.getRequestAddTagList())) {
            insertShopTagRel(product);
        } else if (!CollectionUtils.isEmpty(result) && !CollectionUtils.isEmpty(product.getRequestAddTagList())) {
            for (TagProductRelEntity tagProductRelEntity : result) {
                tagProductRelService.removeTagProductRel(tagProductRelEntity.getId());
            }
            insertShopTagRel(product);
        }

        ProductSpecRelEntity queryProductSpecRel = new ProductSpecRelEntity();
        queryProductSpecRel.setPid(product.getId());
        List<ProductSpecRelEntity> specRelEntities = productSpecRelService.listProductSpecRel(queryProductSpecRel);
        if (!CollectionUtils.isEmpty(specRelEntities) && !CollectionUtils.isEmpty(product.getProductSpecList())) {
            for (ProductSpecRelEntity specRelEntity : specRelEntities) {
                productSpecRelService.removeProductSpecRel(specRelEntity.getId());
            }
            insertProductSpecRel(product);
        } else if(CollectionUtils.isEmpty(specRelEntities) && !CollectionUtils.isEmpty(product.getProductSpecList())){
            insertProductSpecRel(product);
        }
        return ret;
    }

    private void insertShopTagRel(@JSON ProductEntity product) {
        for (ShopTagRelEntity tagEntity : product.getRequestAddTagList()) {
            TagProductRelEntity rel = new TagProductRelEntity();
            rel.setPid(product.getId());
            rel.setTid(tagEntity.getTagId());
            tagProductRelService.addTagProductRel(rel);
        }
    }

    private void insertProductSpecRel(@JSON ProductEntity product) {
        for (ProductSpecEntity specEntity : product.getProductSpecList()) {
            ProductSpecRelEntity relEntity = new ProductSpecRelEntity();
            relEntity.setPid(product.getId());
            relEntity.setSpecId(specEntity.getId());
            productSpecRelService.addProductSpecRel(relEntity);
        }
    }

    @Request(value = "/product/get", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public ProductEntity getProduct(String id) {
        return productService.getProduct(id);
    }

    @Request(value = "/product/list", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public List<ProductEntity> listProduct(ProductEntity productEntity) {
        return productService.listProduct(productEntity);
    }

    @Request(value = "/product/listPage", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Page<List<ProductEntity>> listProductPage(ProductEntity productEntity, Integer pageNo, Integer pageSize) {
        return productService.listProductPage(productEntity, pageNo, pageSize);
    }

    @Request(value = "/product/view", format = Request.Format.VIEW)
    public String view() {
        logger.info("访问view页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        Context.putAttribute("navList", new String[]{"产品管理", "详情记录"});
        return "product/view";
    }
}
