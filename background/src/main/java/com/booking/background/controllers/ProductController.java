package com.booking.background.controllers;

import com.booking.common.entity.ProductEntity;
import com.booking.common.entity.ShopTagRelEntity;
import com.booking.common.entity.TagEntity;
import com.booking.common.entity.TagProductRelEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.Page;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.IProductService;
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
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    IProductService productService;

    @Autowired
    ITagProductRelService tagProductRelService;

    @Request(value = "/product/add")
    @Editor(ResultEditor.class)
    public String addProduct(@JSON ProductEntity product) {
        ProductEntity ret = productService.addProduct(product);
        if (!CollectionUtils.isEmpty(product.getRequestAddTagList())) {
            List<TagProductRelEntity> tagProductRelEntities = new ArrayList<TagProductRelEntity>();
            for (ShopTagRelEntity tagEntity : product.getRequestAddTagList()) {
                TagProductRelEntity tagProductRelEntity = new TagProductRelEntity();
                tagProductRelEntity.setPid(ret.getId());
                tagProductRelEntity.setTid(tagEntity.getTagId());
                tagProductRelEntities.add(tagProductRelEntity);
            }
            tagProductRelService.addTagProductRel(tagProductRelEntities);
        }
        return ret.getId();
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
            for (ShopTagRelEntity tagEntity : product.getRequestAddTagList()) {
                TagProductRelEntity rel = new TagProductRelEntity();
                rel.setPid(product.getId());
                rel.setTid(tagEntity.getTagId());
                tagProductRelService.addTagProductRel(rel);
            }
        } else if (!CollectionUtils.isEmpty(result) && !CollectionUtils.isEmpty(product.getRequestAddTagList())) {
            for (TagProductRelEntity tagProductRelEntity : result) {
                tagProductRelService.removeTagProductRel(tagProductRelEntity.getId());
            }
            for (ShopTagRelEntity tagEntity : product.getRequestAddTagList()) {
                TagProductRelEntity rel = new TagProductRelEntity();
                rel.setPid(product.getId());
                rel.setTid(tagEntity.getTagId());
                tagProductRelService.addTagProductRel(rel);
            }
        }
        return ret;
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
        return "product/view";
    }
}
