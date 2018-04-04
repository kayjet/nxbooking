package com.booking.background.controllers;

import com.booking.common.entity.ProductSpecRelEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.Page;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.IProductSpecRelService;
import com.opdar.platform.core.base.Context;
import com.opdar.platform.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* ProductSpecRelController
*
* @author kai.liu
* @date 2017/12/29
*/
@Controller
@ErrorHandler(ErrCodeHandler.class)
public class ProductSpecRelController {
    private static final Logger logger = LoggerFactory.getLogger(ProductSpecRelController.class);

    @Autowired
    IProductSpecRelService productSpecRelService;

    @Request(value = "/productSpecRel/add")
    @Editor(ResultEditor.class)
    public int addProductSpecRel(@JSON ProductSpecRelEntity productSpecRel) {
        return productSpecRelService.addProductSpecRel(productSpecRel);
    }

    @Request(value = "/productSpecRel/remove")
    @Editor(ResultEditor.class)
    public int removeProductSpecRel(String id) {
        return productSpecRelService.removeProductSpecRel(id);
    }

    @Request(value = "/productSpecRel/removeList")
    @Editor(ResultEditor.class)
    public int removeProductSpecRelList(@JSON List<String> ids) {
        return productSpecRelService.removeProductSpecRel(ids);
    }

    @Request(value = "/productSpecRel/update")
    @Editor(ResultEditor.class)
    public int updateProductSpecRel(@JSON ProductSpecRelEntity productSpecRel) {
        return  productSpecRelService.updateById(productSpecRel, productSpecRel.getId());
    }

    @Request(value = "/productSpecRel/get", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public ProductSpecRelEntity getProductSpecRel(String id) {
        return productSpecRelService.getProductSpecRel(id);
    }

    @Request(value = "/productSpecRel/list", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public List<ProductSpecRelEntity> listProductSpecRel(ProductSpecRelEntity productSpecRelEntity) {
        return productSpecRelService.listProductSpecRel(productSpecRelEntity);
    }

    @Request(value = "/productSpecRel/listPage", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Page<List<ProductSpecRelEntity>> listProductSpecRelPage(ProductSpecRelEntity productSpecRelEntity, Integer pageNo, Integer pageSize) {
        return productSpecRelService.listProductSpecRelPage(productSpecRelEntity, pageNo , pageSize);
    }

    @Request(value = "/productSpecRel/view", format = Request.Format.VIEW)
    public String view() {
        logger.info("访问view页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        return "productSpecRel/view";
    }
}
