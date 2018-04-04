package com.booking.background.controllers;

import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.ResultEditor;
import com.booking.common.service.impl.CommonService;
import com.opdar.platform.annotations.Editor;
import com.opdar.platform.annotations.ErrorHandler;
import com.opdar.platform.annotations.Request;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * CommonController
 *
 * @author kai.liu
 * @date 2018/03/29
 */
@Controller
@ErrorHandler(ErrCodeHandler.class)
public class CommonController {
    Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    CommonService commonService;

    @Request(value = "/common/uploadAvatar", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public String uploadAvatar(FileItem[] file) {
        try {
            return commonService.uploadAvatar(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Request(value = "/common/getAvatar", format = Request.Format.STREAM)
    public byte[] getAvatar(String avatarName) {
        byte[] result = null;
        try {
            result = commonService.getAvatar(avatarName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
