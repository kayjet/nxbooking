package com.booking.background.controllers;

import com.booking.background.service.BackgroundUserService;
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
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
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

    @Autowired
    BackgroundUserService backgroundUserService;

    @Request(value = "/common/login", format = Request.Format.VIEW)
    public String login() {
        Context.putAttribute("context", Context.getRequest().getContextPath());
        return "login/view";
    }

    @Request(value = "/common/login/action", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public void loginAction(String username, String password) {
        if (backgroundUserService.login(username, password)) {
            try {
                Context.getResponse().sendRedirect(Context.getRequest().getContextPath() + "/user/view");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Context.getRequest().getRequestDispatcher(Context.getRequest().getContextPath() + "/common/login").forward(Context.getRequest(), Context.getResponse());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Request(value = "/common/logout/action", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public Boolean logoutAction() {
        Context.putAttribute("context", Context.getRequest().getContextPath());
        return true;
    }
}
