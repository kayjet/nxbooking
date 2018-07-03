package com.booking.background.controllers;

import com.booking.background.interceptor.LoginSessionInterceptor;
import com.booking.common.base.Constants;
import com.booking.common.dto.AddProductForShopDto;
import com.booking.common.dto.AddProductForShopQueryDto;
import com.booking.common.dto.AddTagForShopDto;
import com.booking.common.dto.RemoveProductForShopDto;
import com.booking.common.entity.*;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.interceptor.TimeQueryInterceptor;
import com.booking.common.mapper.ShopTagRelMapper;
import com.booking.common.mapper.TagProductRelMapper;
import com.booking.common.resp.Page;
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

    @Autowired
    private ShopTagRelMapper shopTagRelMapper;

    @Autowired
    private TagProductRelMapper tagProductRelMapper;

    @Request(value = "/productionAdditional/listProductForShop")
    @Editor(ResultEditor.class)
    public Page<List<ProductEntity>> listProductForShop(AddProductForShopQueryDto query, Integer pageNo, Integer pageSize, String tagId) {
        if (query == null) {
            query = new AddProductForShopQueryDto();
        }
        return productService.listProductPageForAdd(query, pageNo, pageSize);
    }

    @Request(value = "/productionAdditional/listShopTags")
    @Editor(ResultEditor.class)
    public List<TagForWebEntity> listShopTags(String shopId) {
        List<TagForWebEntity> result = new ArrayList<TagForWebEntity>();
        List<ShopTagRelForWebEntity> relForWebEntities = shopService.listProducts(shopId, false);
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
        List<ShopTagRelForWebEntity> relForWebEntities = shopService.listProducts(shopId, false);
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
    public Boolean addTagForShop(@JSON AddTagForShopDto addTagForShopDto) {
        String shopId = addTagForShopDto.getShopId();
        List<ShopTagRelEntity> ret = new ArrayList<ShopTagRelEntity>();
        for (String tagId : addTagForShopDto.getTagIds()) {
            ShopTagRelEntity shopTagRelEntity = new ShopTagRelEntity();
            shopTagRelEntity.setShopId(shopId);
            shopTagRelEntity.setTagId(tagId);
            ret.add(shopTagRelEntity);
        }

        return shopTagRelService.addShopTagRel(ret) > 0;
    }

    @Request(value = "/productionAdditional/addProductForShop")
    @Editor(ResultEditor.class)
    public Boolean addProductForShop(@JSON AddProductForShopDto addProductForShopDto) {
        String shopId = addProductForShopDto.getShopId();
        String tagId = addProductForShopDto.getTagId();
        List<TagProductRelEntity> resultList = new ArrayList<TagProductRelEntity>();
        for (String productId : addProductForShopDto.getProductIds()) {
            ShopTagRelEntity query = new ShopTagRelEntity();
            query.setShopId(shopId);
            query.setTagId(tagId);
            query = shopTagRelMapper.selectOne(query);
            if (query != null) {
                String id = query.getId();
                TagProductRelEntity tagProductRelEntity = new TagProductRelEntity();
                tagProductRelEntity.setPid(productId);
                tagProductRelEntity.setTid(id);
                resultList.add(tagProductRelEntity);
            }
        }
        return tagProductRelService.addTagProductRel(resultList) > 0;
    }

    @Request(value = "/productionAdditional/removeProductForShop")
    @Editor(ResultEditor.class)
    public Boolean removeProductForShop(@JSON RemoveProductForShopDto removeProductForShopDto) {
        String shopId = removeProductForShopDto.getShopId();
        String tagId = removeProductForShopDto.getTagId();
        int result = 0;
        for (String productId : removeProductForShopDto.getProductIds()) {
            ShopTagRelEntity query = new ShopTagRelEntity();
            query.setShopId(shopId);
            query.setTagId(tagId);
            query = shopTagRelMapper.selectOne(query);
            if (query != null) {
                String id = query.getId();
                TagProductRelEntity tagProductRelEntity = new TagProductRelEntity();
                tagProductRelEntity.setPid(productId);
                tagProductRelEntity.setTid(id);
                result += tagProductRelMapper.delete(tagProductRelEntity);
            }
        }
        return result > 0;
    }

    @Request(value = "/productionAdditional/removeTagForShop")
    @Editor(ResultEditor.class)
    public Boolean removeTagForShop(@JSON AddTagForShopDto addTagForShopDto) {
        int result = 0;
        String shopId = addTagForShopDto.getShopId();
        for (String tagId : addTagForShopDto.getTagIds()) {
            ShopTagRelEntity shopTagRelEntity = new ShopTagRelEntity();
            shopTagRelEntity.setShopId(shopId);
            shopTagRelEntity.setTagId(tagId);
            ShopTagRelEntity ret = shopTagRelMapper.selectOne(shopTagRelEntity);
            if (ret != null) {
                String id = ret.getId();
                TagProductRelEntity delete = new TagProductRelEntity();
                delete.setTid(id);
                tagProductRelMapper.delete(delete);
            }
            result += shopTagRelMapper.delete(shopTagRelEntity);
        }

        return result > 0;
    }

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
