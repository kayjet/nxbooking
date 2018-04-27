package com.booking.background.controllers;

import com.booking.background.service.BackgroundUserService;
import com.booking.common.exceptions.ErrCodeHandler;
import com.booking.common.resp.ResultEditor;
import com.opdar.platform.annotations.Editor;
import com.opdar.platform.annotations.ErrorHandler;
import com.opdar.platform.annotations.Request;
import com.opdar.platform.core.base.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * CommonController
 *
 * @author kai.liu
 * @date 2018/03/29
 */
@Controller
@ErrorHandler(ErrCodeHandler.class)
public class WebSocketLoginController {
    Logger logger = LoggerFactory.getLogger(WebSocketLoginController.class);

    @Autowired
    BackgroundUserService backgroundUserService;

    @Value("${proxy.context}")
    private String proxyContext;

    @Request(value = "/websocket/login", format = Request.Format.VIEW)
    public String login() {
        Context.putAttribute("context", Context.getRequest().getContextPath());
        Context.putAttribute("proxyContext", proxyContext);
        return "login/wsview";
    }

    @Request(value = "/websocket/login/action", format = Request.Format.JSON)
    @Editor(ResultEditor.class)
    public void loginAction(String username, String password) {
        String contextPath = Context.getRequest().getContextPath();
        boolean ret = backgroundUserService.wsLogin(username, password);
        String redirectUrl = "";
        if (ret) {
            redirectUrl = contextPath + "/websocket/view";
            if (!StringUtils.isEmpty(proxyContext)) {
                redirectUrl = proxyContext + redirectUrl;
            }

        } else {
            redirectUrl = contextPath + "/websocket/login";
            if (!StringUtils.isEmpty(proxyContext)) {
                redirectUrl = proxyContext + redirectUrl;
            }
        }
        logger.info("ws loginAction login result = " + ret);
        logger.info("sendRedirect url =" + redirectUrl);
        try {
            Context.getResponse().sendRedirect(redirectUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
