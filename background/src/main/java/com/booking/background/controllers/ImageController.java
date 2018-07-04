package com.booking.background.controllers;

import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.impl.CommonService;
import com.opdar.platform.annotations.Editor;
import com.opdar.platform.annotations.ErrorHandler;
import com.opdar.platform.annotations.Request;
import com.opdar.platform.core.base.Context;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Set;

/**
 * CommonController
 *
 * @author kai.liu
 * @date 2018/03/29
 */
@Controller
@ErrorHandler(ErrCodeHandler.class)
public class ImageController {
    Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    CommonService commonService;

    @Value("${proxy.context}")
    private String proxyContext;

    @Request(value = "/image/uploadAvatar", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public String uploadAvatar(FileItem[] file) {
        try {
            return commonService.uploadAvatar(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Request(value = "/image/getAvatar", format = Request.Format.STREAM)
    public byte[] getAvatar(String avatarName) {
        byte[] result = null;
        try {
            result = commonService.getAvatar(avatarName);
        } catch (IOException e) {
        }
        return result;
    }

    @Request(value = "/image/listAllAvatarNames", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Set<String> listAllAvatarNames() {
        try {
            return commonService.listAllAvatarNames();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Request(value = "/image/deleteImage", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Boolean deleteImage(String imageName) {
        try {
            return commonService.deleteImage(imageName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Request(value = "/image/uploadView", format = Request.Format.VIEW)
    public String uploadView() {
        Context.putAttribute("context", Context.getRequest().getContextPath());
        Context.putAttribute("navList",new String[]{"图片管理","批量上传"});
        Context.putAttribute("proxyContext", proxyContext);
        return "image/uploadView";
    }

    @Request(value = "/image/seeImg", format = Request.Format.VIEW)
    public String seeImg() {
        Context.putAttribute("context", Context.getRequest().getContextPath());
        Context.putAttribute("navList",new String[]{"图片管理","图片查看"});
        Context.putAttribute("proxyContext", proxyContext);
        return "image/seeImg";
    }

}
