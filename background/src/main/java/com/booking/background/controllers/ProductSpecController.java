package com.booking.background.controllers;

import com.booking.common.entity.ProductSpecEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.Page;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.IProductSpecService;
import com.opdar.platform.core.base.Context;
import com.opdar.platform.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* ProductSpecController
*
* @author kai.liu
* @date 2017/12/29
*/
@Controller
@ErrorHandler(ErrCodeHandler.class)
public class ProductSpecController {
    private static final Logger logger = LoggerFactory.getLogger(ProductSpecController.class);

    @Autowired
    IProductSpecService productSpecService;

    @Request(value = "/productSpec/add")
    @Editor(ResultEditor.class)
    public int addProductSpec(@JSON ProductSpecEntity productSpec) {
        return productSpecService.addProductSpec(productSpec);
    }

    @Request(value = "/productSpec/remove")
    @Editor(ResultEditor.class)
    public int removeProductSpec(String id) {
        return productSpecService.removeProductSpec(id);
    }

    @Request(value = "/productSpec/removeList")
    @Editor(ResultEditor.class)
    public int removeProductSpecList(@JSON List<String> ids) {
        return productSpecService.removeProductSpec(ids);
    }

    @Request(value = "/productSpec/update")
    @Editor(ResultEditor.class)
    public int updateProductSpec(@JSON ProductSpecEntity productSpec) {
        return  productSpecService.updateById(productSpec, productSpec.getId());
    }

    @Request(value = "/productSpec/get", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public ProductSpecEntity getProductSpec(String id) {
        return productSpecService.getProductSpec(id);
    }

    @Request(value = "/productSpec/list", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public List<ProductSpecEntity> listProductSpec(ProductSpecEntity productSpecEntity) {
        return productSpecService.listProductSpec(productSpecEntity);
    }

    @Request(value = "/productSpec/listAllParent", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public List<ProductSpecEntity> listAllParent(ProductSpecEntity productSpecEntity) {
        return productSpecService.listAllParent();
    }


    @Request(value = "/productSpec/listPage", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Page<List<ProductSpecEntity>> listProductSpecPage(ProductSpecEntity productSpecEntity, Integer pageNo, Integer pageSize) {
        return productSpecService.listProductSpecPage(productSpecEntity, pageNo , pageSize);
    }

    @Request(value = "/productSpec/view", format = Request.Format.VIEW)
    public String view() {
        logger.info("访问view页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        return "productSpec/view";
    }
}
