package com.booking.background.controllers;

import com.booking.common.entity.AdvertisementEntity;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.Page;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.IAdvertisementService;
import com.opdar.platform.core.base.Context;
import com.opdar.platform.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* AdvertisementController
*
* @author kai.liu
* @date 2017/12/29
*/
@Controller
@ErrorHandler(ErrCodeHandler.class)
public class AdvertisementController {
    private static final Logger logger = LoggerFactory.getLogger(AdvertisementController.class);

    @Autowired
    IAdvertisementService advertisementService;

    @Request(value = "/advertisement/add")
    @Editor(ResultEditor.class)
    public int addAdvertisement(@JSON AdvertisementEntity advertisement) {
        return advertisementService.addAdvertisement(advertisement);
    }

    @Request(value = "/advertisement/remove")
    @Editor(ResultEditor.class)
    public int removeAdvertisement(String id) {
        return advertisementService.removeAdvertisement(id);
    }

    @Request(value = "/advertisement/removeList")
    @Editor(ResultEditor.class)
    public int removeAdvertisementList(@JSON List<String> ids) {
        return advertisementService.removeAdvertisement(ids);
    }

    @Request(value = "/advertisement/update")
    @Editor(ResultEditor.class)
    public int updateAdvertisement(@JSON AdvertisementEntity advertisement) {
        return  advertisementService.updateById(advertisement, advertisement.getId());
    }

    @Request(value = "/advertisement/get", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public AdvertisementEntity getAdvertisement(String id) {
        return advertisementService.getAdvertisement(id);
    }

    @Request(value = "/advertisement/list", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public List<AdvertisementEntity> listAdvertisement(AdvertisementEntity advertisementEntity) {
        return advertisementService.listAdvertisement(advertisementEntity);
    }

    @Request(value = "/advertisement/listPage", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Page<List<AdvertisementEntity>> listAdvertisementPage(AdvertisementEntity advertisementEntity, Integer pageNo, Integer pageSize) {
        return advertisementService.listAdvertisementPage(advertisementEntity, pageNo , pageSize);
    }

    @Request(value = "/advertisement/view", format = Request.Format.VIEW)
    public String view() {
        logger.info("访问view页");
        Context.putAttribute("context", Context.getRequest().getContextPath());
        return "advertisement/view";
    }
}
