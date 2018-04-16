package com.booking.background.controllers;

import com.booking.common.entity.ShopEntity;
import com.booking.common.entity.ShopTagRelEntity;
import com.booking.common.entity.TagEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.interceptor.TimeQueryInterceptor;
import com.booking.common.resp.Page;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.IShopTagRelService;
import com.booking.common.service.ITagService;
import com.opdar.platform.core.base.Context;
import com.opdar.platform.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * TagController
 *
 * @author kai.liu
 * @date 2017/12/29
 */
@Controller
@ErrorHandler(ErrCodeHandler.class)
@Interceptor(TimeQueryInterceptor.class)
public class TagController {
    private static final Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    ITagService tagService;
    @Autowired
    IShopTagRelService shopTagRelService;

    @Request(value = "/tag/add")
    @Editor(ResultEditor.class)
    public TagEntity addTag(@JSON TagEntity tag) {
        TagEntity ret = tagService.addTag(tag);
        if (!CollectionUtils.isEmpty(tag.getRequestAddShopList())) {
            for (ShopEntity shopEntity : tag.getRequestAddShopList()) {
                ShopTagRelEntity shopTagRelEntity = new ShopTagRelEntity();
                shopTagRelEntity.setShopId(shopEntity.getId());
                shopTagRelEntity.setTagId(ret.getId());
                shopTagRelService.addShopTagRel(shopTagRelEntity);
            }
        }
        return ret;
    }

    @Request(value = "/tag/remove")
    @Editor(ResultEditor.class)
    public int removeTag(String id) {
        return tagService.removeTag(id);
    }

    @Request(value = "/tag/removeList")
    @Editor(ResultEditor.class)
    public int removeTagList(@JSON List<String> ids) {
        return tagService.removeTag(ids);
    }

    @Request(value = "/tag/update")
    @Editor(ResultEditor.class)
    public int updateTag(@JSON TagEntity tag) {
        int ret = tagService.updateById(tag, tag.getId());
        ShopTagRelEntity query = new ShopTagRelEntity();
        query.setTagId(tag.getId());
        List<ShopTagRelEntity> result = shopTagRelService.listShopTagRel(query);
        if (CollectionUtils.isEmpty(result) && !CollectionUtils.isEmpty(tag.getRequestAddShopList())) {
            for (ShopEntity shopEntity : tag.getRequestAddShopList()) {
                ShopTagRelEntity rel = new ShopTagRelEntity();
                rel.setTagId(tag.getId());
                rel.setShopId(shopEntity.getId());
                shopTagRelService.addShopTagRel(rel);
            }
        } else if (!CollectionUtils.isEmpty(result) && !CollectionUtils.isEmpty(tag.getRequestAddShopList())) {
            for (ShopTagRelEntity ShopTagRelEntity : result) {
                shopTagRelService.removeShopTagRel(ShopTagRelEntity.getId());
            }
            for (ShopEntity shopEntity : tag.getRequestAddShopList()) {
                ShopTagRelEntity rel = new ShopTagRelEntity();
                rel.setTagId(tag.getId());
                rel.setShopId(shopEntity.getId());
                shopTagRelService.addShopTagRel(rel);
            }
        }
        return ret;
    }

    @Request(value = "/tag/get", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public TagEntity getTag(String id) {
        return tagService.getTag(id);
    }

    @Request(value = "/tag/list", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public List<TagEntity> listTag(TagEntity tagEntity) {
        return tagService.listTag(tagEntity);
    }

    @Request(value = "/tag/listPage", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Page<List<TagEntity>> listTagPage(TagEntity tagEntity, Integer pageNo, Integer pageSize) {
        return tagService.listTagPage(tagEntity, pageNo, pageSize);
    }

    @Request(value = "/tag/view", format = Request.Format.VIEW)
    public String view() {
        logger.info("访问view页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        Context.putAttribute("navList",new String[]{"产品管理","标签"});
        return "tag/view";
    }
}
