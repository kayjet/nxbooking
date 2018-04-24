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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

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

    @Value("${proxy.context}")
    private String proxyContext;

    @Request(value = "/common/login", format = Request.Format.VIEW)
    public String login() {
        Context.putAttribute("context", Context.getRequest().getContextPath());
        Context.putAttribute("proxyContext", proxyContext);
        return "login/view";
    }

    @Request(value = "/common/login/action", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public void loginAction(String username, String password) {
        String contextPath = Context.getRequest().getContextPath();
        boolean ret = backgroundUserService.login(username, password);
        logger.info("loginAction login result = " + ret);
        logger.info("loginAction username = " + username + ", password=" + password);
        if (ret) {
            try {
                String redirectUrl = contextPath + "/user/view";
                if (!StringUtils.isEmpty(proxyContext)) {
                    redirectUrl = proxyContext + redirectUrl;
                }
                logger.info("sendRedirect url =" + redirectUrl);
                Context.getResponse().sendRedirect(redirectUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                String redirectUrl = contextPath + "/common/login";
                if (!StringUtils.isEmpty(proxyContext)) {
                    redirectUrl = proxyContext + redirectUrl;
                }
                logger.info("getRequestDispatcher url = " + redirectUrl);
                Context.getRequest().getRequestDispatcher(redirectUrl).forward(Context.getRequest(), Context.getResponse());
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
