package com.booking.background.interceptor;

import com.booking.common.base.ICacheManager;
import com.opdar.platform.core.base.Context;
import com.opdar.platform.core.base.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import java.io.IOException;

/**
 * LoginSessionInterceptor
 *
 * @author kai.liu
 * @date 2018/04/18
 */
public class LoginSessionInterceptor implements Interceptor {
    Logger logger = LoggerFactory.getLogger(LoginSessionInterceptor.class);
    public static final String LOGIN_USER = "LOGIN_USER";
    @Autowired
    ICacheManager cacheManager;

    @Value("${proxy.context}")
    private String proxyContext;

    @Override
    public boolean before() {
        Object loginUser = Context.getRequest().getSession().getAttribute(LOGIN_USER);
        if (loginUser != null) {
            return true;
        } else {
            try {
                String contextPath = Context.getRequest().getContextPath();
                String redirectUrl = contextPath + "/common/login";
                if (!StringUtils.isEmpty(proxyContext)) {
                    redirectUrl = proxyContext + redirectUrl;
                }
                Context.getResponse().sendRedirect(redirectUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean after() {
        return true;
    }

    @Override
    public void finish() {

    }
}
