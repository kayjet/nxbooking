package com.booking.background.controllers;

import com.booking.common.entity.TagProductRelEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.Page;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.ITagProductRelService;
import com.opdar.platform.core.base.Context;
import com.opdar.platform.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* TagProductRelController
*
* @author kai.liu
* @date 2017/12/29
*/
@Controller
@ErrorHandler(ErrCodeHandler.class)
public class TagProductRelController {
    private static final Logger logger = LoggerFactory.getLogger(TagProductRelController.class);

    @Autowired
    ITagProductRelService tagProductRelService;

    @Request(value = "/tagProductRel/add")
    @Editor(ResultEditor.class)
    public int addTagProductRel(@JSON TagProductRelEntity tagProductRel) {
        return tagProductRelService.addTagProductRel(tagProductRel);
    }

    @Request(value = "/tagProductRel/remove")
    @Editor(ResultEditor.class)
    public int removeTagProductRel(String id) {
        return tagProductRelService.removeTagProductRel(id);
    }

    @Request(value = "/tagProductRel/removeList")
    @Editor(ResultEditor.class)
    public int removeTagProductRelList(@JSON List<String> ids) {
        return tagProductRelService.removeTagProductRel(ids);
    }

    @Request(value = "/tagProductRel/update")
    @Editor(ResultEditor.class)
    public int updateTagProductRel(@JSON TagProductRelEntity tagProductRel) {
        return  tagProductRelService.updateById(tagProductRel, tagProductRel.getId());
    }

    @Request(value = "/tagProductRel/get", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public TagProductRelEntity getTagProductRel(String id) {
        return tagProductRelService.getTagProductRel(id);
    }

    @Request(value = "/tagProductRel/list", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public List<TagProductRelEntity> listTagProductRel(TagProductRelEntity tagProductRelEntity) {
        return tagProductRelService.listTagProductRel(tagProductRelEntity);
    }

    @Request(value = "/tagProductRel/listPage", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Page<List<TagProductRelEntity>> listTagProductRelPage(TagProductRelEntity tagProductRelEntity, Integer pageNo, Integer pageSize) {
        return tagProductRelService.listTagProductRelPage(tagProductRelEntity, pageNo , pageSize);
    }

    @Request(value = "/tagProductRel/view", format = Request.Format.VIEW)
    public String view() {
        logger.info("访问view页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        return "tagProductRel/view";
    }
}
