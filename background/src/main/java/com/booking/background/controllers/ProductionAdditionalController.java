package com.booking.background.controllers;

import com.booking.background.interceptor.LoginSessionInterceptor;
import com.booking.common.entity.*;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.interceptor.TimeQueryInterceptor;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.*;
import com.booking.common.service.impl.ProductAdditionalService;
import com.opdar.platform.annotations.*;
import com.opdar.platform.core.base.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ProductionAdditionalController
 *
 * @author kai.liu
 * @date 2018/07/01
 */
@Controller
@ErrorHandler(ErrCodeHandler.class)
@Interceptor({TimeQueryInterceptor.class, LoginSessionInterceptor.class})
public class ProductionAdditionalController {
    Logger logger = LoggerFactory.getLogger(ProductionAdditionalController.class);

    @Value("${proxy.context}")
    private String proxyContext;

    @Autowired
    private IProductService productService;

    @Autowired
    private ITagProductRelService tagProductRelService;

    @Autowired
    private ProductAdditionalService productAdditionalService;

    @Autowired
    private IShopTagRelService shopTagRelService;

    @Autowired
    private ITagService tagService;

    @Autowired
    private IShopService shopService;

   /* @Request(value = "/productionAdditional/add")
    @Editor(ResultEditor.class)
    public String addProduct(@JSON ProductEntity product) {
//        ProductEntity productRet = productService.addProduct(product);
        if (!CollectionUtils.isEmpty(product.getRequestAddTagList())) {
            List<TagProductRelEntity> tagProductRelEntities = new ArrayList<TagProductRelEntity>();
            for (ShopTagRelEntity tagEntity : product.getRequestAddTagList()) {
                if (!StringUtils.isEmpty(tagEntity.getTagId())) {
                    TagProductRelEntity tagProductRelEntity = new TagProductRelEntity();
                    tagProductRelEntity.setPid(productRet.getId());
                    tagProductRelEntity.setTid(tagEntity.getTagId());
                    tagProductRelEntities.add(tagProductRelEntity);
                }
            }
            tagProductRelService.addTagProductRel(tagProductRelEntities);
        }
        if (!StringUtils.isEmpty(product.getShopId()) && product.getSpPrice() != null) {
            productAdditionalService.addProductSpPrice(product.getShopId(), product.getSpPrice(), product.getId());
        }
        return productRet.getId();
    }*/

    @Request(value = "/productionAdditional/listShopTags")
    @Editor(ResultEditor.class)
    public List<TagForWebEntity> listShopTags(String shopId) {
        List<TagForWebEntity> result = getTagForWebEntities(shopId);
        return result;
    }

    private List<TagForWebEntity> getTagForWebEntities(String shopId) {
        List<TagForWebEntity> result = new ArrayList<TagForWebEntity>();
        List<ShopTagRelForWebEntity> relForWebEntities = shopService.listProducts(shopId);
        for (ShopTagRelForWebEntity webEntity : relForWebEntities) {
            if (!CollectionUtils.isEmpty(webEntity.getTagList())) {
                result.addAll(webEntity.getTagList());
            }
        }
        return result;
    }

    @Request(value = "/productionAdditional/listTagForAdding")
    @Editor(ResultEditor.class)
    public List<TagEntity> listTagForAdding(String shopId) {
        List<TagEntity> allTags = tagService.listAll();
        List<ShopTagRelForWebEntity> relForWebEntities = shopService.listProducts(shopId);
        if (CollectionUtils.isEmpty(allTags)) {
            return new ArrayList<TagEntity>();
        }
        Iterator<TagEntity> it = allTags.iterator();
        while (it.hasNext()) {
            TagEntity tag = it.next();
            for (ShopTagRelForWebEntity tagRelForWebEntity : relForWebEntities) {
                if (tag.getId().equals(tagRelForWebEntity.getTagId())) {
                    it.remove();
                }
            }
        }
        return allTags;
    }

    @Request(value = "/productionAdditional/addTagForShop")
    @Editor(ResultEditor.class)
    public Boolean addTagForShop(String tagId, String shopId) {
        ShopTagRelEntity shopTagRelEntity = new ShopTagRelEntity();
        shopTagRelEntity.setShopId(shopId);
        shopTagRelEntity.setTagId(tagId);
        return shopTagRelService.addShopTagRel(shopTagRelEntity) > 0;
    }

//    @Request(value = "/productionAdditional/addProductForShop")
//    @Editor(ResultEditor.class)
//    public Boolean addProductForShop(String tagId, String shopId,String productId) {
//
//        return shopTagRelService.addShopTagRel(shopTagRelEntity) > 0;
//    }

    @Request(value = "/productionAdditional/view", format = Request.Format.VIEW)
    public String view() {
        logger.info("访问view页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        Context.putAttribute("proxyContext", proxyContext);
        Context.putAttribute("shopList", shopService.listAll());
        Context.putAttribute("navList", new String[]{"门店管理", "商品详情"});
        return "productionAdditional/view";
    }
}
