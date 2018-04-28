package com.booking.background.controllers;

import com.booking.background.interceptor.LoginSessionInterceptor;
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

    @Request(value = "/common/login/action")
    @Editor(ResultEditor.class)
    public void loginAction(String username, String password) {
        String contextPath = Context.getRequest().getContextPath();
        boolean ret = backgroundUserService.login(username, password);
        String redirectUrl = "";
        if (ret) {
            redirectUrl = contextPath + "/user/view";
            if (!StringUtils.isEmpty(proxyContext)) {
                redirectUrl = proxyContext + redirectUrl;
            }
        } else {
            redirectUrl = contextPath + "/common/login";
            if (!StringUtils.isEmpty(proxyContext)) {
                redirectUrl = proxyContext + redirectUrl;
            }
        }
        logger.info("loginAction login result = " + ret);
        logger.info("sendRedirect url =" + redirectUrl);

        try {
            Context.getResponse().sendRedirect(redirectUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Request(value = "/common/logout/action")
    public void logoutAction() {
        Context.getRequest().getSession().removeAttribute(LoginSessionInterceptor.LOGIN_USER);
        String contextPath = Context.getRequest().getContextPath();
        String redirectUrl = contextPath + "/common/login";
        if (!StringUtils.isEmpty(proxyContext)) {
            redirectUrl = proxyContext + redirectUrl;
        }
        try {
            Context.getResponse().sendRedirect(redirectUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Request(value = "/index", format = Request.Format.VIEW)
    public String index() {
        String contextPath = Context.getRequest().getContextPath();

        String wsLogin = contextPath + "/websocket/login";
        String commonLogin = contextPath + "/common/login";
        if (!StringUtils.isEmpty(proxyContext)) {
            wsLogin = proxyContext + wsLogin;
            commonLogin = proxyContext + commonLogin;
        }
        Context.putAttribute("commonLogin", commonLogin);
        Context.putAttribute("wsLogin", wsLogin);
        return "index";
    }
}
